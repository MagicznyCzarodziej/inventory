import 'package:camera/camera.dart';
import 'package:flutter/material.dart';
import 'package:inventory_app/api/HttpClient.dart';
import 'package:inventory_app/dto/GetCategoriesResponse.dart';
import 'package:inventory_app/dto/GetParentItemsResponse.dart';
import 'package:inventory_app/pages/AddItemPage/AddItemPage.dart';
import 'package:inventory_app/routes/simpleRoute.dart';

Future<GetParentItemsResponse> getParentItems() async {
  var response = await HttpClient.getJson('/parent-items');
  return GetParentItemsResponse.fromJson(response);
}

Future<GetCategoriesResponse> getCategories() async {
  var response = await HttpClient.getJson('/categories');
  return GetCategoriesResponse.fromJson(response);
}

class ChooseItemRootPage extends StatefulWidget {
  const ChooseItemRootPage({super.key, required this.camera});

  final CameraDescription camera;

  @override
  State<ChooseItemRootPage> createState() => _ChooseItemRootPageState();
}

class _ChooseItemRootPageState extends State<ChooseItemRootPage> {
  late Future<GetParentItemsResponse> parentItemsResponse;
  late Future<GetCategoriesResponse> categoriesResponse;

  void fetchData() {
    setState(() {
      parentItemsResponse = getParentItems();
      categoriesResponse = getCategories();
    });
  }

  @override
  void initState() {
    super.initState();
    fetchData();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: FutureBuilder(
        future: categoriesResponse,
        builder: (context, response) {
          if (!response.hasData) {
            return const Text("Loading");
          }

          return ListView.builder(
              itemCount: response.data!.categories.length,
              itemBuilder: (BuildContext context, int index) {
                var category = response.data!.categories[index];
                return GestureDetector(
                  onTap: () {
                    Navigator.push(
                      context,
                      simpleRoute(
                        AddItemPage(
                            camera: widget.camera, categoryId: category.id),
                      ),
                    );
                  },
                  child: Container(
                    margin: EdgeInsets.only(top: 16, left: 16, right: 16),
                    padding: EdgeInsets.all(16),
                    color: Theme.of(context).colorScheme.surface,
                    child: Text(
                      category.name,
                      style: TextStyle(
                        fontSize: 24,
                      ),
                    ),
                  ),
                );
              });
        },
      ),
      // Center(
      // child: Column(
      //   mainAxisAlignment: MainAxisAlignment.center,
      //   crossAxisAlignment: CrossAxisAlignment.center,
      //   children: [
      //     const Text("Wybierz produkt nadrzędny"),
      //     FutureBuilder(
      //       future: parentItemsResponse,
      //       builder: (context, response) {
      //         if (!response.hasData) {
      //           return const DropdownMenu(
      //             dropdownMenuEntries: [],
      //           );
      //         }
      //
      //         return DropdownMenu(
      //           dropdownMenuEntries: [
      //             const DropdownMenuEntry(value: null, label: "Brak"),
      //             ...response.data!.parentItems
      //                 .map((e) => DropdownMenuEntry(
      //                 value: e.id, label: e.name))
      //                 .toList()
      //           ],
      //         );
      //       },
      //     ),
      //     const Padding(
      //       padding: EdgeInsets.symmetric(vertical: 24),
      //       child: Text("lub kategorię"),
      //     ),
      //
      //   ],
      // ),
      // ),
    );
  }
}
