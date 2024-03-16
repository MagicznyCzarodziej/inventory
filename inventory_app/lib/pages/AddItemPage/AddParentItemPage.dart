import 'package:flutter/material.dart';
import 'package:inventory_app/api/Categories.dart';
import 'package:inventory_app/api/ParentItems.dart';
import 'package:inventory_app/dto/GetCategoriesResponse.dart';

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
                style: const TextStyle(
                  fontSize: 24,
                ),
                decoration: const InputDecoration(
                  label: Text("Nazwa grupy"),
                ),
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
            ],
          ),
        ),
      ),
      floatingActionButton: IconButton(
        onPressed: () async {
          await addParentItem(
            {
              "name": name.trim(),
              "categoryId": categoryId,
            },
          );

          if (!mounted) return;
          Navigator.popUntil(context, (route) => route.isFirst);
        },
        icon: const Icon(
          Icons.check,
          size: 48,
        ),
        color: Theme.of(context).colorScheme.onPrimary,
        style: IconButton.styleFrom(
          backgroundColor: Theme.of(context).colorScheme.primary,
        ),
      ),
    );
  }
}
