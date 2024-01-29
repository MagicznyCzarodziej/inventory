import 'dart:convert';
import 'dart:io';

import 'package:camera/camera.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:inventory_app/api/HttpClient.dart';
import 'package:inventory_app/dto/GetCategoriesResponse.dart';
import 'package:inventory_app/dto/GetParentItemsResponse.dart';
import 'package:inventory_app/pages/AddItemPage/TakePhotoPage.dart';
import 'package:inventory_app/pages/BarcodeScannerPage/BarcodeScannerPage.dart';
import 'package:inventory_app/routes/simpleRoute.dart';

import '../../utils.dart';

class AddItemPage extends StatefulWidget {
  const AddItemPage({super.key, required this.camera, this.categoryId});

  final String? categoryId;
  final CameraDescription camera;

  @override
  State<AddItemPage> createState() => _AddItemPageState();
}

class _AddItemPageState extends State<AddItemPage> {
  final _formKey = GlobalKey<FormState>();

  late Future<GetParentItemsResponse> parentItemsResponse;
  late Future<GetCategoriesResponse> categoriesResponse;

  String name = "";
  String description = "";
  String? parentId;
  String? brand = "";
  int currentStock = 1;
  int desiredStock = 1;
  String? photoId;
  String? barcode;

  @override
  void initState() {
    super.initState();
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
                          image: NetworkImage(
                            "http://192.168.0.66:8080/photos/$photoId",
                          ),
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
                        var response = await upload(File(path));
                        setState(() {
                          photoId = response.photoId;
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
                                  color: Theme.of(context)
                                      .colorScheme
                                      .onSurfaceVariant,
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ),
                  Padding(
                    padding: const EdgeInsets.all(32),
                    child: Column(
                      children: [
                        TextFormField(
                            autofocus: true,
                            style: const TextStyle(
                              fontSize: 32,
                            ),
                            decoration: const InputDecoration(
                              labelText: "Nazwa",
                            ),
                            textCapitalization: TextCapitalization.sentences,
                            onChanged: (value) => setState(() {
                                  name = value;
                                }),
                            textInputAction: TextInputAction.next),
                        TextFormField(
                            style: const TextStyle(
                              fontSize: 32,
                            ),
                            decoration: const InputDecoration(
                              labelText: "Opis",
                            ),
                            textCapitalization: TextCapitalization.sentences,
                            onChanged: (value) => setState(() {
                                  description = value;
                                }),
                            textInputAction: TextInputAction.next),
                        TextFormField(
                            style: const TextStyle(
                              fontSize: 32,
                            ),
                            decoration: const InputDecoration(
                              labelText: "Firma / Producent",
                            ),
                            textCapitalization: TextCapitalization.sentences,
                            onChanged: (value) => setState(() {
                                  brand = value;
                                }),
                            textInputAction: TextInputAction.done),
                        Padding(
                          padding: const EdgeInsets.only(top: 24),
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
                                  barcode == null
                                      ? "Zeskanuj kod kreskowy"
                                      : "Skanuj",
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
                                        icon: const Icon(Icons.add)),
                                    if (currentStock > 0)
                                      IconButton(
                                          onPressed: () {
                                            setState(() {
                                              currentStock = currentStock - 1;
                                            });
                                          },
                                          icon: const Icon(Icons.remove)),
                                  ],
                                ),
                                Text(
                                  currentStock.toString(),
                                  style: const TextStyle(
                                    fontSize: 128,
                                  ),
                                ),
                              ],
                            ),
                            const Text(
                              "/",
                              style: TextStyle(
                                fontSize: 128,
                                color: Colors.white60,
                              ),
                            ),
                            Row(
                              children: [
                                Text(
                                  desiredStock.toString(),
                                  style: const TextStyle(
                                    fontSize: 128,
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
                                        icon: const Icon(Icons.add)),
                                    if (desiredStock > 0)
                                      IconButton(
                                          onPressed: () {
                                            setState(() {
                                              desiredStock = desiredStock - 1;
                                            });
                                          },
                                          icon: const Icon(Icons.remove)),
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
      bottomNavigationBar: BottomAppBar(
        height: 70,
        child: IconButton(
          onPressed: () async {
            await HttpClient.postJson(
              "/items",
              jsonEncode({
                "itemType": "ITEM",
                "name": name,
                "description": description,
                "categoryId": widget.categoryId,
                "brand": brand,
                "currentStock": currentStock,
                "desiredStock": desiredStock,
                "photoId": photoId,
                "barcode": barcode,
              }),
            );
            if (!mounted) return;
            Navigator.pop(context);
          },
          icon: const Icon(Icons.check),
          style: IconButton.styleFrom(
              alignment: FractionalOffset.centerRight,
              padding: EdgeInsets.only(right: 32)),
        ),
      ),
    );
  }
}
