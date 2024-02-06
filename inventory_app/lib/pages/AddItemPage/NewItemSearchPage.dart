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
        child: Container(
          color: const Color.fromRGBO(250, 250, 250, 1),
          child: Column(
            children: [
              Padding(
                padding: const EdgeInsets.only(
                  top: 20,
                  left: 16,
                  right: 16,
                ),
                child: TextField(
                  autofocus: true,
                  textCapitalization: TextCapitalization.sentences,
                  decoration: InputDecoration(
                    filled: true,
                    fillColor: Theme.of(context).colorScheme.primaryContainer,
                    contentPadding: const EdgeInsets.symmetric(horizontal: 16),
                    border: const OutlineInputBorder(
                      borderSide: BorderSide.none,
                      borderRadius: BorderRadius.all(
                        Radius.circular(8),
                      ),
                    ),
                    hintText: "Co dodać?",
                    hintStyle: TextStyle(
                      color: Theme.of(context).colorScheme.onPrimaryContainer,
                      fontWeight: FontWeight.normal,
                    ),
                  ),
                  onChanged: (value) {
                    setState(() {
                      query = value;
                    });
                  },
                ),
              ),
              Padding(
                padding: const EdgeInsets.only(top: 8, bottom: 8,left: 16, right: 16),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    FilledButton.icon(
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
                      label: const Text("dodaj jako produkt"),
                      icon: const Icon(
                        Icons.add,
                        size: 16,
                      ),
                    ),
                    FilledButton.icon(
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
                      label: const Text("dodaj jako grupę"),
                      icon: const Icon(CupertinoIcons.arrow_turn_right_up, size: 12),
                    ),
                  ],
                ),
              ),
              const Padding(
                padding: EdgeInsets.symmetric(horizontal: 16.0),
                child: Divider(
                  color: Color.fromRGBO(0, 0, 0, 0.08),
                  height: 2,
                ),
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
                                    ItemEntry() => Column(
                                        children: [
                                          Row(
                                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                            children: [
                                              Text(
                                                entry.name,
                                                style: const TextStyle(
                                                  fontSize: 16,
                                                ),
                                              ),
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
                                          const Divider(
                                            color: Color.fromRGBO(0, 0, 0, 0.08),
                                            endIndent: 4,
                                          ),
                                        ],
                                      ),
                                    ParentEntry() => Column(
                                        children: [
                                          Row(
                                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                            crossAxisAlignment: CrossAxisAlignment.start,
                                            children: [
                                              Padding(
                                                padding: const EdgeInsets.only(top: 14.0),
                                                child: Column(
                                                  crossAxisAlignment: CrossAxisAlignment.start,
                                                  children: [
                                                    Text(
                                                      entry.name,
                                                      style: const TextStyle(
                                                        fontSize: 16,
                                                      ),
                                                    ),
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
                                          const Divider(
                                            color: Color.fromRGBO(0, 0, 0, 0.08),
                                            endIndent: 4,
                                          ),
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
      ),
    );
  }
}
