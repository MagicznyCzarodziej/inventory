import 'package:flutter/material.dart';
import 'package:inventory_app/pages/ItemPage/ItemPage.dart';
import 'package:inventory_app/routes/simpleRoute.dart';
import 'package:inventory_app/utils.dart';

class ItemsListEntry {
  String id;
  String name;
  int currentStock;
  int desiredStock;

  ItemsListEntry({
    required this.id,
    required this.name,
    required this.currentStock,
    required this.desiredStock,
  });
}

class ItemEntryWidget extends StatelessWidget {
  const ItemEntryWidget({
    super.key,
    required this.entry,
    required this.refetchItems,
  });

  final ItemsListEntry entry;
  final Function refetchItems;

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () =>
          Navigator.push(context, simpleRoute(ItemPage(itemId: entry.id)))
              .then((value) => refetchItems()),
      child: Padding(
        padding: const EdgeInsets.only(bottom: 12.0),
        child: Container(
          color: Theme.of(context).colorScheme.surface,
          child: Padding(
            padding: const EdgeInsets.only(left: 16, top: 4, bottom: 4),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  entry.name,
                  style: const TextStyle(
                    fontSize: 18,
                  ),
                ),
                Row(
                  children: [
                    if (entry.currentStock > 0)
                      IconButton(
                        onPressed: () async {
                          await updateCurrentStock(entry.id, -1);
                          refetchItems();
                        },
                        icon: const Icon(Icons.remove),
                      ),
                    Text(
                      entry.currentStock.toString(),
                      style: TextStyle(
                        fontSize: 24,
                        color: getCurrentStockColor(
                            entry.currentStock, entry.desiredStock),
                      ),
                    ),
                    IconButton(
                      onPressed: () async {
                        await updateCurrentStock(entry.id, 1);
                        refetchItems();
                      },
                      icon: const Icon(Icons.add),
                    )
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
