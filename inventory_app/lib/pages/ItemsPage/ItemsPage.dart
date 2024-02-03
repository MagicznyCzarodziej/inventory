import 'package:camera/camera.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:inventory_app/api/HttpClient.dart';
import 'package:inventory_app/pages/AddItemPage/NewItemSearchPage.dart';
import 'package:inventory_app/pages/ItemsPage/ItemEntryWidget.dart';
import 'package:inventory_app/pages/ItemsPage/HideOnScroll.dart';
import 'package:inventory_app/utils.dart';

import '../../dto/GetItemsResponse.dart';

Future<GetItemsResponse> getItems() async {
  var response = await HttpClient.getJson('/items');
  return GetItemsResponse.fromJson(response);
}

class ItemsPage extends StatefulWidget {
  const ItemsPage({super.key, required this.camera});

  final CameraDescription camera;

  @override
  State<ItemsPage> createState() => _ItemsPageState();
}

class _ItemsPageState extends State<ItemsPage> {
  late Future<GetItemsResponse> itemsResponse;

  String query = "";
  final searchController = TextEditingController();
  final ScrollController _scrollController = ScrollController();

  void fetchItems() {
    setState(() {
      itemsResponse = getItems();
    });
  }

  @override
  void initState() {
    super.initState();
    itemsResponse = getItems();
  }

  @override
  void dispose() {
    super.dispose();
    _scrollController.dispose();
  }

  FutureBuilder builder() => FutureBuilder<GetItemsResponse>(
        future: itemsResponse,
        builder: (context, snapshot) {
          if (!snapshot.hasData) return Text("Ładowanie");

          var entries = snapshot.data!.entries
              .where((element) => switch (element) {
                    ItemEntry() => element.name.containsIgnoreCase(query) || element.brand.containsIgnoreCase(query),
                    ParentEntry() => element.name.containsIgnoreCase(query) ||
                        element.items
                            .any((item) => item.name.containsIgnoreCase(query) || item.brand.containsIgnoreCase(query)),
                  })
              .expand((e) => switch (e) {
                    ItemEntry() => [
                        ItemsListEntry(
                          id: e.id,
                          name: e.name,
                          currentStock: e.currentStock,
                          desiredStock: e.desiredStock,
                        )
                      ],
                    ParentEntry() => e.items.map((i) => ItemsListEntry(
                          id: i.id,
                          name: "${e.name} ${i.name}",
                          currentStock: i.currentStock,
                          desiredStock: i.desiredStock,
                        )),
                  })
              .toList()
            ..sort((a, b) {
              if (a.currentStock < a.desiredStock && b.currentStock < b.desiredStock) {
                if (a.currentStock == b.currentStock) {
                  return b.desiredStock - a.desiredStock;
                } else if (a.currentStock < b.currentStock) {
                  return -1;
                } else {
                  return 1;
                }
              } else if (a.currentStock < a.desiredStock) {
                return -1;
              } else if (b.currentStock < b.desiredStock) {
                return 1;
              } else {
                if (a.desiredStock == 0) return 1;
                if (b.desiredStock == 0) return -1;

                return a.currentStock - b.currentStock;
              }
            });

          if (snapshot.hasData) {
            return Scaffold(
              resizeToAvoidBottomInset: true,
              body: Stack(
                children: [
                  SafeArea(
                    child: ListView.builder(
                      controller: _scrollController,
                      padding: const EdgeInsets.only(
                          // left: 16, right: 16, top: 16,
                          // bottom: 64,
                          ),
                      itemCount: entries.length,
                      itemBuilder: (BuildContext context, int index) {
                        var entry = entries[index];
                        return Column(
                          children: [
                            ItemEntryWidget(
                              entry: entry,
                              refetchItems: fetchItems,
                            ),
                          ],
                        );
                      },
                    ),
                  ),
                  Column(
                    children: [
                      Spacer(),
                      HideOnScroll(
                        scrollController: _scrollController,
                        height: 66,
                        child: Container(
                          margin: const EdgeInsets.only(left: 22, right: 16, bottom: 16),
                          child: Row(
                            children: [
                              Expanded(
                                child: Container(
                                  decoration: const BoxDecoration(
                                    color: Color.fromRGBO(230, 230, 230, 1),
                                    borderRadius: BorderRadius.all(
                                      Radius.circular(10),
                                    ),
                                  ),
                                  child: Row(
                                    children: [
                                      Padding(
                                        padding: const EdgeInsets.only(left: 2.0, right: 8),
                                        child: IconButton(
                                          onPressed: () => {},
                                          icon: const Icon(
                                            Icons.manage_search,
                                            // size: 30,
                                          ),
                                        ),
                                      ),
                                      Expanded(
                                        child: TextField(
                                          onTapOutside: (_) => FocusManager.instance.primaryFocus?.unfocus(),
                                          controller: searchController,
                                          onChanged: (String value) => {setState(() => query = value)},
                                          style: const TextStyle(
                                            fontSize: 16,
                                          ),
                                          decoration: InputDecoration(
                                            suffixIconConstraints: BoxConstraints(maxHeight: 66,maxWidth: 66),
                                            suffixIcon: query.isEmpty
                                                ? null
                                                : IconButton(
                                                    iconSize: 16,
                                                    style: IconButton.styleFrom(
                                                      foregroundColor: const Color(0xFFa9bcc7),
                                                      shape: const RoundedRectangleBorder(
                                                        borderRadius: BorderRadius.all(
                                                          Radius.circular(8),
                                                        ),
                                                      ),
                                                    ),
                                                    onPressed: () {
                                                      searchController.clear();
                                                      setState(() {
                                                        query = "";
                                                      });
                                                    },
                                                    icon: const Icon(Icons.clear),
                                                  ),
                                            border: InputBorder.none,
                                            hintText: "Czego szukasz?",
                                            hintStyle: const TextStyle(
                                              fontWeight: FontWeight.normal,
                                            ),
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                              const SizedBox(
                                width: 16,
                              ),
                              Container(
                                decoration: const BoxDecoration(
                                  color: Color.fromRGBO(230, 230, 230, 1),
                                  borderRadius: BorderRadius.all(
                                    Radius.circular(10),
                                  ),
                                ),
                                child: IconButton(
                                  onPressed: () async {
                                    var res = await Navigator.push(
                                      context,
                                      CupertinoPageRoute(
                                        builder: (_) => NewItemSearchPage(
                                          camera: widget.camera,
                                        ),
                                      ),
                                    );
                                    if (!mounted) return;
                                    setState(() {
                                      itemsResponse = getItems();
                                    });
                                  },
                                  icon: Icon(
                                    Icons.add_shopping_cart_outlined,
                                    // size: 30,
                                    color: Theme.of(context).colorScheme.onSurfaceVariant,
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            );
          } else {
            print(snapshot.error);
            return const Text("cos sie zepsuło");
          }
        },
      );

  @override
  Widget build(BuildContext context) {
    return builder();
  }
}
