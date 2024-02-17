import 'dart:io';

import 'package:camera/camera.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:inventory_app/api/Categories.dart';
import 'package:inventory_app/api/Items.dart';
import 'package:inventory_app/api/Photo.dart';
import 'package:inventory_app/dto/GetCategoriesResponse.dart';
import 'package:inventory_app/pages/AddItemPage/TakePhotoPage.dart';
import 'package:inventory_app/pages/BarcodeScannerPage/BarcodeScannerPage.dart';
import 'package:inventory_app/routes/simpleRoute.dart';

class AddItemPage extends StatefulWidget {
  const AddItemPage({
    super.key,
    required this.camera,
    required this.itemType,
    this.parentId,
    this.name,
  });

  final String? itemType;
  final String? parentId;
  final String? name;
  final CameraDescription camera;

  @override
  State<AddItemPage> createState() => _AddItemPageState();
}

class _AddItemPageState extends State<AddItemPage> {
  final _formKey = GlobalKey<FormState>();

  late Future<GetCategoriesResponse> categoriesResponse;

  String name = "";
  String description = "";
  String? brand = "";
  String? categoryId;
  int currentStock = 1;
  int desiredStock = 1;
  String? photoId;
  String? barcode;

  File? photoFile;

  void fetchData() {
    setState(() {
      categoriesResponse = getCategories();
    });
  }

  @override
  void initState() {
    super.initState();
    fetchData();
    name = widget.name?.trim() ?? "";
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Column(
          children: [
            Form(
              key: _formKey,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  if (photoId != null)
                    Container(
                      height: 270,
                      decoration: BoxDecoration(
                        image: DecorationImage(
                          fit: BoxFit.cover,
                          image: FileImage(photoFile!),
                        ),
                      ),
                    )
                  else
                    GestureDetector(
                      behavior: HitTestBehavior.translucent,
                      onTap: () => Navigator.push(
                        context,
                        simpleRoute(
                          TakePhotoPage(camera: widget.camera),
                        ),
                      ).then((path) async {
                        if (!mounted) return;
                        var file = File(path);
                        var response = await uploadPhoto(file);
                        setState(() {
                          photoId = response.photoId;
                          photoFile = file;
                        });
                      }),
                      child: Container(
                        color: Theme.of(context).colorScheme.surface,
                        height: 270,
                        child: Padding(
                          padding: const EdgeInsets.only(top: 24),
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              const Icon(
                                Icons.photo_camera_back,
                                size: 48,
                              ),
                              const SizedBox(
                                height: 16,
                              ),
                              Text(
                                "Zrób zdjęcie",
                                style: TextStyle(
                                  fontSize: 24,
                                  color: Theme.of(context).colorScheme.onSurfaceVariant,
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ),
                  Padding(
                    padding: const EdgeInsets.only(
                      left: 32,
                      right: 32,
                      top: 8,
                      bottom: 0,
                    ),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.stretch,
                      children: [
                        if (widget.parentId != null) Text("ParentId: ${widget.parentId}"),
                        TextFormField(
                          initialValue: name,
                          autofocus: true,
                          style: const TextStyle(
                            fontSize: 24,
                          ),
                          decoration: const InputDecoration(
                            labelText: "Nazwa",
                          ),
                          textCapitalization: TextCapitalization.sentences,
                          onChanged: (value) => setState(() {
                            name = value;
                          }),
                          textInputAction: TextInputAction.next,
                        ),
                        TextFormField(
                          style: const TextStyle(
                            fontSize: 24,
                          ),
                          decoration: const InputDecoration(
                            labelText: "Opis",
                          ),
                          textCapitalization: TextCapitalization.sentences,
                          onChanged: (value) => setState(() {
                            description = value;
                          }),
                          textInputAction: TextInputAction.next,
                        ),
                        TextFormField(
                          style: const TextStyle(
                            fontSize: 24,
                          ),
                          decoration: const InputDecoration(
                            labelText: "Firma / Producent",
                          ),
                          textCapitalization: TextCapitalization.sentences,
                          onChanged: (value) => setState(() {
                            brand = value;
                          }),
                          textInputAction: TextInputAction.done,
                        ),
                        if (widget.itemType == "ITEM")
                          FutureBuilder(
                            future: categoriesResponse,
                            builder: (context, snapshot) {
                              return DropdownMenu(
                                label: const Text("Kategoria"),
                                textStyle: const TextStyle(
                                  fontSize: 24,
                                ),
                                expandedInsets: EdgeInsets.zero,
                                inputDecorationTheme: const InputDecorationTheme(
                                  contentPadding: EdgeInsets.symmetric(vertical: 16),
                                  filled: true,
                                  fillColor: Colors.transparent,
                                ),
                                onSelected: (value) {
                                  setState(() {
                                    categoryId = value;
                                  });
                                },
                                dropdownMenuEntries: snapshot.data?.categories
                                        .map((e) => DropdownMenuEntry(value: e.id, label: e.name))
                                        .toList() ??
                                    const Iterable<DropdownMenuEntry>.empty().toList(),
                              );
                            },
                          ),
                        Padding(
                          padding: const EdgeInsets.only(top: 24, bottom: 8),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              const Padding(
                                padding: EdgeInsets.only(right: 8),
                                child: Icon(
                                  CupertinoIcons.barcode,
                                  size: 32,
                                ),
                              ),
                              Text(
                                barcode ?? "",
                                style: const TextStyle(
                                  fontSize: 18,
                                ),
                              ),
                              TextButton(
                                onPressed: () async {
                                  String? readBarcode = await Navigator.push(
                                    context,
                                    simpleRoute(const BarcodeScannerPage()),
                                  );
                                  setState(() {
                                    barcode = readBarcode;
                                  });
                                },
                                child: Text(
                                  barcode == null ? "Zeskanuj kod kreskowy" : "Skanuj",
                                  style: const TextStyle(
                                    fontSize: 18,
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Row(
                              children: [
                                Column(
                                  children: [
                                    IconButton(
                                      onPressed: () {
                                        setState(() {
                                          currentStock = currentStock + 1;
                                        });
                                      },
                                      icon: const Icon(Icons.add),
                                    ),
                                    if (currentStock > 0)
                                      IconButton(
                                        onPressed: () {
                                          setState(() {
                                            currentStock = currentStock - 1;
                                          });
                                        },
                                        icon: const Icon(Icons.remove),
                                      )
                                    else
                                      const SizedBox(height: 48)
                                  ],
                                ),
                                Text(
                                  currentStock.toString(),
                                  style: const TextStyle(
                                    fontSize: 96,
                                  ),
                                ),
                              ],
                            ),
                            const Text(
                              "/",
                              style: TextStyle(
                                fontSize: 96,
                                color: Colors.black12,
                              ),
                            ),
                            Row(
                              children: [
                                Text(
                                  desiredStock.toString(),
                                  style: const TextStyle(
                                    fontSize: 96,
                                  ),
                                ),
                                Column(
                                  children: [
                                    IconButton(
                                      onPressed: () {
                                        setState(() {
                                          desiredStock = desiredStock + 1;
                                        });
                                      },
                                      icon: const Icon(Icons.add),
                                    ),
                                    if (desiredStock > 0)
                                      IconButton(
                                        onPressed: () {
                                          setState(() {
                                            desiredStock = desiredStock - 1;
                                          });
                                        },
                                        icon: const Icon(Icons.remove),
                                      )
                                    else
                                      const SizedBox(height: 48)
                                  ],
                                ),
                              ],
                            ),
                          ],
                        )
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
      floatingActionButton: IconButton(
        onPressed: () async {
          await addItem(
            {
              "itemType": widget.itemType,
              "name": name.trim(),
              "description": description.trim(),
              "categoryId": categoryId,
              "parentId": widget.parentId,
              "brand": brand?.trim(),
              "currentStock": currentStock,
              "desiredStock": desiredStock,
              "photoId": photoId,
              "barcode": barcode,
            },
          );
          if (!mounted) return;
          Navigator.popUntil(context, (route) => route.isFirst);
        },
        icon: const Icon(
          Icons.check,
          size: 48,
        ),
        color: Theme.of(context).colorScheme.onPrimary,
        style: IconButton.styleFrom(
          backgroundColor: Theme.of(context).colorScheme.primary,
        ),
      ),
    );
  }
}
