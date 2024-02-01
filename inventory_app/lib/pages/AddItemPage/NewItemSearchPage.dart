import 'package:camera/camera.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:inventory_app/api/HttpClient.dart';
import 'package:inventory_app/dto/GetItemsResponse.dart';
import 'package:inventory_app/pages/AddItemPage/AddItemPage.dart';
import 'package:inventory_app/pages/AddItemPage/AddParentItemPage.dart';
import 'package:inventory_app/pages/ItemPage/ItemPage.dart';
import 'package:inventory_app/routes/simpleRoute.dart';
import 'package:inventory_app/utils.dart';

Future<GetItemsResponse> getItems() async {
  var response = await HttpClient.getJson('/items');
  return GetItemsResponse.fromJson(response);
}

String nameForEntry(Entry entry) {
  return switch (entry) {
    ItemEntry() => entry.name,
    ParentEntry() => entry.name,
  };
}

class NewItemSearchPage extends StatefulWidget {
  const NewItemSearchPage({super.key, required this.camera});

  final CameraDescription camera;

  @override
  State<NewItemSearchPage> createState() => _NewItemSearchPageState();
}

class _NewItemSearchPageState extends State<NewItemSearchPage> {
  late Future<GetItemsResponse> itemsResponse;

  void fetchData() {
    setState(() {
      itemsResponse = getItems();
    });
  }

  String query = "";
  String productName = "";

  @override
  void initState() {
    super.initState();
    fetchData();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.all(16),
              child: TextField(
                autofocus: true,
                textCapitalization: TextCapitalization.sentences,
                onChanged: (value) {
                  setState(() {
                    query = value;
                  });
                },
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(left: 4, right: 12),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  TextButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        simpleRoute(
                          AddItemPage(
                            camera: widget.camera,
                            itemType: "ITEM",
                            name: query,
                          ),
                        ),
                      );
                    },
                    child: const Row(
                      children: [
                        Text("dodaj jako produkt"),
                        Padding(
                          padding: EdgeInsets.only(left: 4),
                          child: Icon(
                            Icons.add,
                            size: 20,
                          ),
                        ),
                      ],
                    ),
                  ),
                  TextButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        simpleRoute(
                          AddParentItemPage(
                            name: query,
                          ),
                        ),
                      );
                    },
                    style: TextButton.styleFrom(
                      padding: const EdgeInsets.only(left: 8, top: 4, bottom: 4),
                    ),
                    child: const Row(
                      children: [
                        Text("dodaj jako grupę"),
                        Padding(
                          padding: EdgeInsets.only(left: 8.0, right: 4),
                          child: Icon(CupertinoIcons.arrow_turn_right_up, size: 16),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
            const Padding(
              padding: EdgeInsets.symmetric(horizontal: 16.0),
              child: Divider(color: Colors.white24),
            ),
            FutureBuilder(
              future: itemsResponse,
              builder: (context, snapshot) {
                if (!snapshot.hasData) {
                  return const Text("Loading");
                }

                var entries = snapshot.data!.entries.where((entry) => nameForEntry(entry).containsIgnoreCase(query));

                return Expanded(
                  child: SingleChildScrollView(
                    child: Padding(
                      padding: const EdgeInsets.only(left: 16, right: 12),
                      child: Column(
                        children: entries
                            .map((entry) => switch (entry) {
                                  ItemEntry() => Row(
                                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                      children: [
                                        Text(entry.name),
                                        TextButton(
                                          onPressed: () {
                                            Navigator.push(context, CupertinoPageRoute(builder: (_) {
                                              return ItemPage(itemId: entry.id);
                                            }));
                                          },
                                          style: TextButton.styleFrom(
                                            padding: const EdgeInsets.only(left: 8, top: 4, bottom: 4),
                                          ),
                                          child: const Row(
                                            children: [
                                              Text("zobacz produkt"),
                                              Icon(Icons.chevron_right),
                                            ],
                                          ),
                                        )
                                      ],
                                    ),
                                  ParentEntry() => Row(
                                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                      crossAxisAlignment: CrossAxisAlignment.start,
                                      children: [
                                        Padding(
                                          padding: const EdgeInsets.only(top: 16.0),
                                          child: Column(
                                            crossAxisAlignment: CrossAxisAlignment.start,
                                            children: [
                                              Text(entry.name),
                                              Padding(
                                                padding: const EdgeInsets.only(left: 8),
                                                child: Column(
                                                  crossAxisAlignment: CrossAxisAlignment.start,
                                                  children: entry.items.map((item) => Text(item.name)).toList(),
                                                ),
                                              )
                                            ],
                                          ),
                                        ),
                                        TextButton(
                                          onPressed: () {
                                            Navigator.push(
                                              context,
                                              simpleRoute(
                                                AddItemPage(
                                                  camera: widget.camera,
                                                  parentId: entry.id,
                                                  itemType: "SUB_ITEM",
                                                ),
                                              ),
                                            );
                                          },
                                          style: TextButton.styleFrom(
                                            padding: const EdgeInsets.only(left: 8, top: 4, bottom: 4),
                                          ),
                                          child: const Row(
                                            children: [
                                              Text("dodaj podprodukt"),
                                              Padding(
                                                padding: EdgeInsets.only(left: 8.0, right: 4),
                                                child: Icon(
                                                  CupertinoIcons.arrow_turn_right_down,
                                                  size: 16,
                                                ),
                                              )
                                            ],
                                          ),
                                        )
                                      ],
                                    ),
                                })
                            .toList(),
                      ),
                    ),
                  ),
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
