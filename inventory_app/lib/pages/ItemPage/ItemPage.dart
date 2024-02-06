import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:inventory_app/api/HttpClient.dart';

import '../../dto/GetItemResponse.dart';
import '../../utils.dart';

Future<GetItemResponse> getItem(String itemId) async {
  var response = await HttpClient.getJson('/items/$itemId');
  return GetItemResponse.fromJson(response);
}

class ItemPage extends StatefulWidget {
  const ItemPage({super.key, required this.itemId});

  final String itemId;

  @override
  State<ItemPage> createState() => _ItemPageState();
}

class _ItemPageState extends State<ItemPage> {
  late Future<GetItemResponse> itemResponse;

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
              resizeToAvoidBottomInset: false,
              body: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  if (item.photoUrl != null)
                    Container(
                      height: 270,
                      decoration: BoxDecoration(
                        image: DecorationImage(
                          fit: BoxFit.cover,
                          image: NetworkImage(
                            item.photoUrl!.startsWith("/") ? "$API_URL${item.photoUrl}" : item.photoUrl!,
                          ),
                        ),
                      ),
                    )
                  else
                    SafeArea(child: Container()),
                  Expanded(
                    child: Container(
                      decoration: const BoxDecoration(
                        color: Color.fromRGBO(250, 250, 250, 1),
                        boxShadow: [
                          BoxShadow(
                            color: Colors.black54,
                            blurRadius: 20,
                            blurStyle: BlurStyle.outer,
                          ),
                        ],
                      ),
                      child: Padding(
                        padding: const EdgeInsets.only(left: 24, right: 16, top: 8),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                              children: [
                                Text(
                                  item.category.name,
                                  style: TextStyle(
                                    color: Theme.of(context).colorScheme.onSurface.withAlpha(100),
                                    fontSize: 20,
                                  ),
                                ),
                                const Spacer(),
                                IconButton(
                                  onPressed: () async {
                                    await HttpClient.delete("/items/${item.id}");
                                    if (!mounted) return;
                                    Navigator.pop(context);
                                  },
                                  icon: Icon(
                                    Icons.delete,
                                    color: Theme.of(context).colorScheme.error,
                                  ),
                                ),
                                IconButton(
                                  onPressed: () => {},
                                  icon: Icon(
                                    Icons.history,
                                    color: Theme.of(context).colorScheme.primary,
                                  ),
                                ),
                                IconButton(
                                  onPressed: () => {},
                                  icon: Icon(
                                    Icons.edit,
                                    color: Theme.of(context).colorScheme.primary,
                                  ),
                                ),
                              ],
                            ),
                            if (item.parentItem != null)
                              Text(
                                item.parentItem!.name,
                                style: const TextStyle(fontSize: 40),
                              ),
                            Wrap(
                              children: [
                                Text(
                                  "${item.name}${item.brand != null ? " " : ""}",
                                  style: const TextStyle(
                                    fontSize: 32,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                if (item.brand != null)
                                  Text(
                                    item.brand!,
                                    style: const TextStyle(fontSize: 32),
                                  ),
                              ],
                            ),
                            if (item.description != null)
                              Padding(
                                padding: const EdgeInsets.only(top: 8),
                                child: Text(
                                  item.description!,
                                  style: TextStyle(
                                    fontSize: 22,
                                    color: Theme.of(context).colorScheme.onSurfaceVariant,
                                  ),
                                ),
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
                                    color: getCurrentStockColor(item.currentStock, item.desiredStock),
                                  ),
                                ),
                                Text(
                                  "/${item.desiredStock.toString()}",
                                  style: TextStyle(fontSize: 70, color: Colors.grey[400]),
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
                                    child: Text(
                                      "Porównaj",
                                      style: TextStyle(
                                        color: Theme.of(context).colorScheme.primary,
                                      ),
                                    ),
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
                padding: const EdgeInsets.symmetric(horizontal: 8),
                height: 70,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    if (item.currentStock > 0)
                      IconButton(
                        onPressed: () => {
                          updateCurrentStock(item.id, -1).then((value) {
                            setState(() {
                              itemResponse = getItem(widget.itemId);
                            });
                          })
                        },
                        icon: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Icon(
                            Icons.remove,
                            color: Theme.of(context).colorScheme.primary,
                          ),
                        ),
                      ),
                    if (item.currentStock > 0)
                      Padding(
                        padding: const EdgeInsets.symmetric(horizontal: 8),
                        child: TextButton(
                          onPressed: () => {
                            updateCurrentStock(item.id, -item.currentStock).then((value) {
                              setState(() {
                                itemResponse = getItem(widget.itemId);
                              });
                            })
                          },
                          child: Padding(
                            padding: const EdgeInsets.all(8),
                            child: Text(
                              "Nie ma",
                              style: TextStyle(
                                color: Theme.of(context).colorScheme.primary,
                              ),
                            ),
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
                      icon: Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Icon(
                          Icons.add,
                          color: Theme.of(context).colorScheme.primary,
                        ),
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
