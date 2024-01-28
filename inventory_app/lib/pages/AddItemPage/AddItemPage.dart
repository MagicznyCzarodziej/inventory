import 'dart:convert';
import 'dart:ffi';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:inventory_app/api/HttpClient.dart';
import 'package:inventory_app/pages/BarcodeScannerPage/BarcodeScannerPage.dart';
import 'package:inventory_app/routes/simpleRoute.dart';

class AddItemPage extends StatefulWidget {
  const AddItemPage({super.key});

  @override
  State<AddItemPage> createState() => _AddItemPageState();
}

class _AddItemPageState extends State<AddItemPage> {
  final _formKey = GlobalKey<FormState>();

  String name = "";
  String description = "";
  String categoryId = "category-cleaning";
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
      body: SafeArea(
        child: Column(
          children: [
            Form(
              key: _formKey,
              child: Column(
                children: [
                  TextFormField(
                    decoration: const InputDecoration(
                      labelText: "Nazwa",
                    ),
                    onChanged: (value) => setState(() {
                      name = value;
                    }),
                  ),
                  TextFormField(
                    decoration: const InputDecoration(
                      labelText: "Opis",
                    ),
                    onChanged: (value) => setState(() {
                      description = value;
                    }),
                  ),
                  TextFormField(
                    decoration: const InputDecoration(
                      labelText: "Firma / Producent",
                    ),
                    onChanged: (value) => setState(() {
                      brand = value;
                    }),
                  ),
                  TextFormField(
                    decoration: const InputDecoration(
                      labelText: "Oczekiwana ilość zapasu",
                    ),
                    onChanged: (value) => setState(() {
                      desiredStock = int.parse(value);
                    }),
                  ),
                  TextFormField(
                    decoration:
                        const InputDecoration(labelText: "Aktualna ilość"),
                    onChanged: (value) => setState(() {
                      currentStock = int.parse(value);
                    }),
                  ),
                  Text(barcode ?? "Brak kodu"),
                  Row(
                    children: [
                      FilledButton(
                        onPressed: () => null,
                        child: const Text("Zrób lub dodaj zdjęcie"),
                      ),
                      FilledButton.icon(
                        icon: const Icon(CupertinoIcons.barcode),
                        onPressed: () async {
                          String? readBarcode = await Navigator.push(
                              context, simpleRoute(const BarcodeScannerPage()));
                          setState(() {
                            barcode = readBarcode;
                          });
                        },
                        label: const Text("Zeskanuj kod kreskowy"),
                      )
                    ],
                  ),
                  ElevatedButton(
                    onPressed: () {
                      HttpClient.postJson(
                        "/items",
                        jsonEncode({
                          "itemType": "ITEM",
                          "name": name,
                          "description": description,
                          "categoryId": categoryId,
                          "brand": brand,
                          "currentStock": currentStock,
                          "desiredStock": desiredStock,
                          "photoId": photoId,
                          "barcode": barcode,
                        }),
                      );
                    },
                    child: const Text("Zapisz"),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
