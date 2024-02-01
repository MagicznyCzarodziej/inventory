import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:inventory_app/api/HttpClient.dart';
import 'package:inventory_app/dto/GetCategoriesResponse.dart';

Future<GetCategoriesResponse> getCategories() async {
  var response = await HttpClient.getJson('/categories');
  return GetCategoriesResponse.fromJson(response);
}

class AddParentItemPage extends StatefulWidget {
  const AddParentItemPage({super.key, required this.name});

  final String name;

  @override
  State<AddParentItemPage> createState() => _AddParentItemPageState();
}

class _AddParentItemPageState extends State<AddParentItemPage> {
  String name = "";
  String? categoryId;
  late Future<GetCategoriesResponse> categoriesResponse;

  void fetchData() {
    setState(() {
      categoriesResponse = getCategories();
    });
  }

  @override
  void initState() {
    super.initState();
    name = widget.name;
    categoriesResponse = getCategories();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            children: [
              TextFormField(
                initialValue: name,
                onChanged: (value) {
                  setState(() {
                    name = value;
                  });
                },
              ),
              FutureBuilder(
                future: categoriesResponse,
                builder: (context, snapshot) {
                  return DropdownMenu(
                    label: const Text("Kategoria"),
                    textStyle: const TextStyle(
                      fontSize: 24,
                    ),
                    expandedInsets: EdgeInsets.zero,
                    inputDecorationTheme: const InputDecorationTheme(
                      contentPadding: EdgeInsets.symmetric(vertical: 16),
                      filled: true,
                      fillColor: Colors.transparent,
                    ),
                    onSelected: (value) {
                      setState(() {
                        categoryId = value;
                      });
                    },
                    dropdownMenuEntries:
                        snapshot.data?.categories.map((e) => DropdownMenuEntry(value: e.id, label: e.name)).toList() ??
                            const Iterable<DropdownMenuEntry>.empty().toList(),
                  );
                },
              ),
              FilledButton(
                onPressed: () async {
                  await HttpClient.postJson(
                    "/parent-items",
                    jsonEncode({
                      "name": name,
                      "categoryId": categoryId,
                    }),
                  );

                  if (!mounted) return;
                  Navigator.popUntil(context, (route) => route.isFirst);
                },
                child: const Text("Dodaj"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}