import 'package:camera/camera.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:inventory_app/api/HttpClient.dart';
import 'package:inventory_app/dto/GetCategoriesResponse.dart';
import 'package:inventory_app/dto/GetItemsResponse.dart';
import 'package:inventory_app/dto/GetParentItemsResponse.dart';
import 'package:inventory_app/pages/AddItemPage/AddItemPage.dart';
import 'package:inventory_app/pages/ItemPage/ItemPage.dart';
import 'package:inventory_app/routes/simpleRoute.dart';
import 'package:inventory_app/utils.dart';

// Future<GetParentItemsResponse> getParentItems() async {
//   var response = await HttpClient.getJson('/parent-items');
//   return GetParentItemsResponse.fromJson(response);
// }

Future<GetItemsResponse> getItems() async {
  var response = await HttpClient.getJson('/items');
  return GetItemsResponse.fromJson(response);
}

// Future<GetCategoriesResponse> getCategories() async {
//   var response = await HttpClient.getJson('/categories');
//   return GetCategoriesResponse.fromJson(response);
// }

class ChooseItemRootPage extends StatefulWidget {
  const ChooseItemRootPage({super.key, required this.camera});

  final CameraDescription camera;

  @override
  State<ChooseItemRootPage> createState() => _ChooseItemRootPageState();
}

class _ChooseItemRootPageState extends State<ChooseItemRootPage> {
  // late Future<GetCategoriesResponse> categoriesResponse;
  // late Future<GetParentItemsResponse> parentItemsResponse;
  late Future<GetItemsResponse> itemsResponse;

  void fetchData() {
    setState(() {
      // categoriesResponse = getCategories();
      // parentItemsResponse = getParentItems();
      itemsResponse = getItems();
    });
  }

  String productName = "";

  @override
  void initState() {
    super.initState();
    fetchData();
  }

  String nameForEntry(Entry entry) {
    return switch (entry) {
      ItemEntry() => entry.name,
      ParentEntry() => entry.name,
    };
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: FutureBuilder(
          future: itemsResponse,
          builder: (context, snapshot) {
            if (!snapshot.hasData) {
              return const Text("Loading");
            }

            var entries = snapshot.data!.entries;

            return Autocomplete<Entry>(
              fieldViewBuilder: (
                context,
                textEditingController,
                focusNode,
                onFieldSubmitted,
              ) {
                return Padding(
                  padding: const EdgeInsets.only(left: 16, top: 16, right: 16),
                  child: TextField(
                    textCapitalization: TextCapitalization.sentences,
                    controller: textEditingController,
                    focusNode: focusNode,
                    onSubmitted: (value) {
                      Navigator.push(
                        context,
                        simpleRoute(
                          AddItemPage(
                            camera: widget.camera,
                            itemType: "ITEM",
                            name: value,
                          ),
                        ),
                      );
                    },
                  ),
                );
              },
              displayStringForOption: (entry) => nameForEntry(entry),
              initialValue: const TextEditingValue(),
              optionsBuilder: (TextEditingValue textEditingValue) {
                setState(() {
                  productName = textEditingValue.text;
                });

                if (textEditingValue.text.length < 2) {
                  return const Iterable<Entry>.empty();
                }

                return entries.where((entry) => nameForEntry(entry)
                    .containsIgnoreCase(textEditingValue.text));
              },
              optionsViewBuilder: (context, onSelected, entries) {
                var indexOfLastParentItem = entries.indexed
                        .where((element) => element.$2 is ParentEntry)
                        .lastOrNull
                        ?.$1 ??
                    entries.length;

                return Material(
                  child: ListView.separated(
                    padding: const EdgeInsets.only(top: 16),
                    itemBuilder: (context, index) {
                      var entry = entries.elementAt(index);
                      var row = switch (entry) {
                        ItemEntry() => GestureDetector(
                            onTap: () {
                              Navigator.push(
                                context,
                                  CupertinoPageRoute(builder:  (_) {
                                  return ItemPage(itemId: entry.id);
                                })

                              );
                            },
                            child: Padding(
                              padding: const EdgeInsets.all(16),
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                children: [
                                  Text(entry.name),
                                  const Row(
                                    children: [
                                      Text("przejdź do produktu"),
                                      Icon(Icons.chevron_right),
                                    ],
                                  )
                                ],
                              ),
                            ),
                          ),
                        ParentEntry() => GestureDetector(
                            onTap: () {
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

                          // TODO: Allow to expand row to show subitems list preview (in order to avoid adding already existing item)
                            child: Padding(
                              padding: const EdgeInsets.all(16),
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                children: [
                                  Text(entry.name),
                                  const Row(
                                    children: [
                                      Text("dodaj podprodukt"),
                                      Padding(
                                        padding: EdgeInsets.only(left: 8.0, right: 4),
                                        child: Icon(CupertinoIcons.arrow_turn_right_down, size: 16,),
                                      )
                                    ],
                                  )
                                ],
                              ),
                            ),
                          ),
                      };
                      return row;
                    },
                    separatorBuilder: (_, index) =>
                        index == indexOfLastParentItem
                            ? const Divider()
                            : Container(),
                    itemCount: entries.length,
                  ),
                );
              },
            );
          },
        ),
      ),
    );
  }

// @override
// Widget build(BuildContext context) {
//   return Scaffold(
//     body: SafeArea(
//       child: FutureBuilder(
//         future: Future.wait(
//           [categoriesResponse, parentItemsResponse],
//           eagerError: true,
//         ),
//         builder: (context, snapshot) {
//           if (!snapshot.hasData) {
//             return const Text("Loading");
//           }
//
//           var [
//             categoriesResponse as GetCategoriesResponse,
//             parentItemsResponse as GetParentItemsResponse,
//           ] = snapshot.data!;
//
//           return Column(
//             children: [
//               const Text("Wybierz produkt nadrzędny"),
//               Autocomplete<ParentItem>(
//                 displayStringForOption: (parentItem) => parentItem.name,
//                 initialValue: const TextEditingValue(), // Shows all options on focus
//                 // optionsViewOpenDirection: OptionsViewOpenDirection.up,
//                 optionsBuilder: (TextEditingValue textEditingValue) {
//                   if (textEditingValue.text.isEmpty) {
//                     return parentItemsResponse.parentItems;
//                   }
//
//                   return parentItemsResponse.parentItems.where((parentItem) =>
//                       parentItem.name
//                           .containsIgnoreCase(textEditingValue.text));
//                 },
//                 onSelected: (ParentItem selected) {
//                   Navigator.push(
//                     context,
//                     simpleRoute(
//                       AddItemPage(
//                         camera: widget.camera,
//                         parentId: selected.id,
//                         itemType: "SUB_ITEM",
//                       ),
//                     ),
//                   );
//                 },
//               ),
//               const Text("lub kategorię"),
//               Expanded(
//                 child: SizedBox(
//                   height: 400,
//                   child: ListView.builder(
//                     itemCount: categoriesResponse.categories.length,
//                     itemBuilder: (BuildContext context, int index) {
//                       var category = categoriesResponse.categories[index];
//                       return GestureDetector(
//                         onTap: () {
//                           Navigator.push(
//                             context,
//                             simpleRoute(
//                               AddItemPage(
//                                 camera: widget.camera,
//                                 categoryId: category.id,
//                                 itemType: "ITEM",
//                               ),
//                             ),
//                           );
//                         },
//                         child: Container(
//                           margin:
//                               const EdgeInsets.only(top: 16, left: 16, right: 16),
//                           padding: const EdgeInsets.all(16),
//                           color: Theme.of(context).colorScheme.surface,
//                           child: Text(
//                             category.name,
//                             style: const TextStyle(
//                               fontSize: 24,
//                             ),
//                           ),
//                         ),
//                       );
//                     },
//                   ),
//                 ),
//               ),
//             ],
//           );
//         },
//       ),
//     ),

// );
// }
}
