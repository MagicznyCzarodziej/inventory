import 'package:flutter/material.dart';
import 'package:inventory_app/pages/ItemPage/ItemPage.dart';
import 'package:inventory_app/routes/simpleRoute.dart';

import '../../dto/GetItemsResponse.dart';

class ItemWidget extends StatelessWidget {
  const ItemWidget({super.key, required this.item});

  final ItemEntry item;

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () =>
          Navigator.push(context, simpleRoute(ItemPage(itemId: item.id))),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(item.name),
          Text("x${item.currentStock.toString()}"),
        ],
      ),
    );
  }
}
