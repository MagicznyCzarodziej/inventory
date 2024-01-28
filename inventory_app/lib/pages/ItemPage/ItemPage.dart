import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:inventory_app/api/HttpClient.dart';

import '../../dto/GetItemResponse.dart';

Future<GetItemResponse> getItem(String itemId) async {
  var response = await HttpClient.getJson('/items/$itemId');
  return GetItemResponse.fromJson(response);
}

Future<void> updateCurrentStock(
  String itemId,
  int stockChange,
) {
  return HttpClient.putJson(
    '/items/$itemId/stock/current',
    jsonEncode({"stockChange": stockChange}),
  );
}

class ItemPage extends StatefulWidget {
  const ItemPage({super.key, required this.itemId});

  final String itemId;

  @override
  State<ItemPage> createState() => _ItemPageState();
}

class _ItemPageState extends State<ItemPage> {
  late Future<GetItemResponse> itemResponse;

  Color getCurrentStockColor(int currentStock, int desiredStock) {
    if (currentStock < 1) {
      return Colors.red;
    } else if (currentStock < 2 && desiredStock > 1) {
      return Colors.pink;
    } else if (currentStock < desiredStock / 2) {
      return Colors.orange;
    } else if (currentStock > desiredStock) {
      return Colors.teal;
    } else {
      return Colors.green;
    }
  }

  @override
  void initState() {
    super.initState();
    itemResponse = getItem(widget.itemId);
  }

  FutureBuilder builder() => FutureBuilder<GetItemResponse>(
        future: itemResponse,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            var item = snapshot.data!;

            return Scaffold(
              body: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    height: 270,
                    decoration: BoxDecoration(
                      image: DecorationImage(
                        fit: BoxFit.cover,
                        image: NetworkImage(
                            item.photoUrl != null ? item.photoUrl! : "",
                        ),
                      ),
                    ),
                  ),
                  Expanded(
                    child: Container(
                      decoration: const BoxDecoration(boxShadow: [
                        BoxShadow(
                          color: Colors.black38,
                          blurRadius: 20,
                          blurStyle: BlurStyle.outer,
                        ),
                      ]),
                      child: Padding(
                        padding: const EdgeInsets.symmetric(horizontal: 24),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                              children: [
                                Text(
                                  item.category.name,
                                  style: const TextStyle(
                                      color: Colors.grey, fontSize: 20),
                                ),
                                const Spacer(),
                                IconButton(
                                  onPressed: () => {},
                                  icon: const Icon(Icons.delete),
                                ),
                                IconButton(
                                  onPressed: () => {},
                                  icon: const Icon(Icons.history),
                                ),
                                IconButton(
                                  onPressed: () => {},
                                  icon: const Icon(Icons.edit),
                                ),
                              ],
                            ),
                            if (item.parentItem != null)
                              Text(
                                item.parentItem!.name,
                                style: const TextStyle(fontSize: 40),
                              ),
                            Row(
                              crossAxisAlignment: CrossAxisAlignment.baseline,
                              textBaseline: TextBaseline.alphabetic,
                              children: [
                                Text(
                                  item.name,
                                  style: const TextStyle(
                                    fontSize: 40,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                if (item.brand != null)
                                  Text(
                                    "  ${item.brand!}",
                                    style: const TextStyle(fontSize: 32),
                                  ),
                              ],
                            ),
                            if (item.description != null)
                              Text(
                                item.description!,
                                style: const TextStyle(fontSize: 22),
                              ),
                            const Spacer(),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.baseline,
                              textBaseline: TextBaseline.alphabetic,
                              children: [
                                Text(
                                  item.currentStock.toString(),
                                  style: TextStyle(
                                    height: 0.8,
                                    fontSize: 200,
                                    color: getCurrentStockColor(
                                        item.currentStock, item.desiredStock),
                                  ),
                                ),
                                Text(
                                  "/${item.desiredStock.toString()}",
                                  style: TextStyle(
                                      fontSize: 70, color: Colors.grey[400]),
                                ),
                              ],
                            ),
                            const Spacer(),
                            if (item.barcode != null)
                              Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  const Padding(
                                    padding: EdgeInsets.only(right: 4),
                                    child: Icon(CupertinoIcons.barcode),
                                  ),
                                  Text(item.barcode!),
                                  TextButton(
                                    onPressed: () => {},
                                    child: const Text("Porównaj"),
                                  )
                                ],
                              ),
                          ],
                        ),
                      ),
                    ),
                  ),
                ],
              ),
              bottomNavigationBar: BottomAppBar(
                child: Row(
                  children: [
                    Row(
                      children: [
                        TextButton(
                          onPressed: () => {},
                          child: const Text("Dodaj wiele"),
                        ),
                      ],
                    ),
                    const Spacer(),
                    if (item.currentStock > 0)
                      IconButton(
                        onPressed: () => {
                          updateCurrentStock(item.id, -1).then((value) {
                            setState(() {
                              itemResponse = getItem(widget.itemId);
                            });
                          })
                        },
                        icon: const Padding(
                          padding: EdgeInsets.all(8.0),
                          child: Icon(Icons.remove),
                        ),
                      ),
                    if (item.currentStock > 0)
                      Padding(
                        padding: const EdgeInsets.symmetric(horizontal: 8),
                        child: TextButton(
                          onPressed: () => {
                            updateCurrentStock(item.id, -item.currentStock)
                                .then((value) {
                              setState(() {
                                itemResponse = getItem(widget.itemId);
                              });
                            })
                          },
                          child: const Padding(
                            padding: EdgeInsets.all(8),
                            child: Text("Nie ma"),
                          ),
                        ),
                      ),
                    IconButton(
                      onPressed: () => {
                        updateCurrentStock(item.id, 1).then((value) {
                          setState(() {
                            itemResponse = getItem(widget.itemId);
                          });
                        })
                      },
                      icon: const Padding(
                        padding: EdgeInsets.all(8.0),
                        child: Icon(Icons.add),
                      ),
                    ),
                  ],
                ),
              ),
            );
          } else {
            return const Text("coś nie tak");
          }
        },
      );

  @override
  Widget build(BuildContext context) {
    return builder();
  }
}
