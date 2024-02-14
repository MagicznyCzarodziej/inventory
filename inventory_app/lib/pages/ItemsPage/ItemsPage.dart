import 'package:camera/camera.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:inventory_app/api/Items.dart';
import 'package:inventory_app/pages/AddItemPage/NewItemSearchPage.dart';
import 'package:inventory_app/pages/ItemsPage/ItemEntryWidget.dart';
import 'package:inventory_app/pages/ItemsPage/HideOnScroll.dart';
import 'package:inventory_app/utils.dart';

import '../../dto/GetItemsResponse.dart';

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
        if (snapshot.hasError) {
          print(snapshot.error);
          return const Text("Coś poszło nie tak");
        } else if (!snapshot.hasData) {
          return const Center(
            child: CircularProgressIndicator(),
          );
        }

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

        return Stack(
          children: [
            SafeArea(
              child: Container(
                alignment: Alignment.center,
                margin: const EdgeInsets.only(top: 8),
                color: const Color.fromRGBO(250, 250, 250, 1),
                child: entries.isEmpty
                    ? const Padding(
                        padding: EdgeInsets.all(16.0),
                        child: Text(
                          "Brak produktów spełniających wymagania",
                          style: TextStyle(
                            fontSize: 16,
                          ),
                        ),
                      )
                    : ListView.separated(
                        controller: _scrollController,
                        separatorBuilder: (context, index) => const Divider(
                          endIndent: 16,
                          indent: 16,
                          color: Color.fromRGBO(0, 0, 0, 0.08),
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
            ),
            Column(
              children: [
                const Spacer(),
                HideOnScroll(
                  scrollController: _scrollController,
                  height: 77,
                  child: Container(
                    margin: const EdgeInsets.only(
                      left: 24,
                      right: 16,
                      bottom: 16,
                    ),
                    child: Stack(
                      alignment: Alignment.center,
                      children: [
                        Container(
                          margin: const EdgeInsets.only(right: 54),
                          decoration: BoxDecoration(
                            color: Theme.of(context).colorScheme.primaryContainer,
                            borderRadius: const BorderRadius.horizontal(
                              left: Radius.circular(10),
                            ),
                          ),
                          child: Row(
                            children: [
                              Padding(
                                padding: const EdgeInsets.only(
                                  left: 4,
                                  right: 8,
                                  // top: 2,
                                  // bottom: 2,
                                ),
                                child: IconButton(
                                  onPressed: () => {},
                                  icon: Icon(
                                    Icons.manage_search,
                                    color: Theme.of(context).colorScheme.onPrimaryContainer,
                                  ),
                                ),
                              ),
                              Expanded(
                                child: TextField(
                                  onTapOutside: (_) => FocusManager.instance.primaryFocus?.unfocus(),
                                  controller: searchController,
                                  onChanged: (String value) => {setState(() => query = value)},
                                  style: TextStyle(
                                    color: Theme.of(context).colorScheme.onPrimaryContainer,
                                  ),
                                  decoration: InputDecoration(
                                    suffixIconConstraints: const BoxConstraints(maxHeight: 76, maxWidth: 76),
                                    suffixIcon: query.isEmpty
                                        ? null
                                        : Padding(
                                            padding: const EdgeInsets.only(right: 6.0),
                                            child: IconButton(
                                              iconSize: 16,
                                              style: IconButton.styleFrom(
                                                foregroundColor: Theme.of(context).colorScheme.onPrimaryContainer,
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
                                          ),
                                    border: InputBorder.none,
                                    hintText: "Czego szukasz?",
                                    hintStyle: TextStyle(
                                      color: Theme.of(context).colorScheme.onPrimaryContainer,
                                      fontWeight: FontWeight.normal,
                                    ),
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                        // const SizedBox(
                        //   width: 16,
                        // ),
                        Row(
                          children: [
                            Spacer(),
                            Container(
                              decoration: BoxDecoration(
                                color: Theme.of(context).colorScheme.primary,
                                borderRadius: const BorderRadius.horizontal(
                                  left: Radius.circular(10),
                                  right: Radius.circular(10),
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
                                icon: Padding(
                                  padding: const EdgeInsets.all(10.0),
                                  child: Icon(
                                    Icons.add,
                                    size: 24,
                                    color: Theme.of(context).colorScheme.onPrimary,
                                  ),
                                ),
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ],
        );
      });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: true,
      body: builder(),
    );
  }
}
