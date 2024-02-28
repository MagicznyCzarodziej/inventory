import 'package:flutter/material.dart';
import 'package:inventory_app/api/Items.dart';
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

Color getColor(ItemsListEntry entry) {
  if (entry.currentStock < 1 && entry.desiredStock > 0)
    return Colors.redAccent[200]!;
  else if (entry.currentStock < entry.desiredStock)
    return Colors.orangeAccent[200]!;
  else
    return Colors.green;
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
      onTap: () => Navigator.push(
          context,
          simpleRoute(ItemPageWrapper(
            child: ItemPage(itemId: entry.id),
          ))).then((value) => refetchItems()),
      child: Container(
        decoration: BoxDecoration(
          border: BorderDirectional(
            start: BorderSide(color: getColor(entry), width: 8),
          ),
        ),
        child: Padding(
          padding: const EdgeInsets.only(left: 16, top: 4, bottom: 4),
          child: Row(
            children: [
              SizedBox(
                width: 60,
                child: Row(
                  textBaseline: TextBaseline.alphabetic,
                  crossAxisAlignment: CrossAxisAlignment.baseline,
                  children: [
                    Text(
                      entry.currentStock.toString(),
                      style: TextStyle(
                        fontSize: 24,
                        color: getCurrentStockColor(entry.currentStock, entry.desiredStock),
                      ),
                    ),
                    Text(
                      "/${entry.desiredStock}",
                      style: const TextStyle(
                        color: Colors.grey,
                        fontSize: 16,
                      ),
                    ),
                  ],
                ),
              ),
              Expanded(
                child: Baseline(
                  baselineType: TextBaseline.alphabetic,
                  baseline: 20,
                  child: Wrap(
                    children: [
                      Text(
                        entry.name,
                        style: TextStyle(
                          fontSize: 18,
                          color: Theme.of(context).colorScheme.onSurface,
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              if (entry.currentStock > 0)
                IconButton(
                  onPressed: () async {
                    await updateCurrentStock(entry.id, -1);
                    refetchItems();
                  },
                  icon: const Icon(Icons.remove),
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
        ),
      ),
    );
  }
}
