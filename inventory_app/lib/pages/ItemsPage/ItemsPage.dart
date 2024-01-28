import 'package:flutter/material.dart';
import 'package:inventory_app/api/HttpClient.dart';
import 'package:inventory_app/pages/AddItemPage/AddItemPage.dart';
import 'package:inventory_app/pages/ItemsPage/ItemWidget.dart';
import 'package:inventory_app/routes/simpleRoute.dart';

import 'ParentItemWidget.dart';
import '../../dto/GetItemsResponse.dart';

Future<GetItemsResponse> getItems() async {
  var response = await HttpClient.getJson('/items');
  return GetItemsResponse.fromJson(response);
}

class ItemsPage extends StatefulWidget {
  const ItemsPage({super.key, required this.title});

  final String title;

  @override
  State<ItemsPage> createState() => _ItemsPageState();
}

class _ItemsPageState extends State<ItemsPage> {
  late Future<GetItemsResponse> itemsResponse;

  @override
  void initState() {
    super.initState();
    itemsResponse = getItems();
  }

  FutureBuilder builder() => FutureBuilder<GetItemsResponse>(
        future: itemsResponse,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            var entries = snapshot.data!.entries;

            return ListView.builder(
              itemCount: entries.length,
              itemBuilder: (BuildContext context, int index) {
                var entry = entries[index];

                return switch (entry) {
                  ItemEntry() => ItemWidget(item: entry),
                  ParentEntry() => ParentItemWidget(parentItem: entry),
                };
              },
            );
          } else {
            return const Text("cos sie zepsuło");
          }
        },
      );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: builder(),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.push(
            context,
            simpleRoute(const AddItemPage()),
          );
        },
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }
}
