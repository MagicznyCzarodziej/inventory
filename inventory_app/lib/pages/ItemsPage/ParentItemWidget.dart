import 'package:flutter/material.dart';
import 'package:inventory_app/pages/ItemsPage/ItemWidget.dart';

import '../../dto/GetItemsResponse.dart';

class ParentItemWidget extends StatelessWidget {
  const ParentItemWidget({super.key, required this.parentItem});

  final ParentEntry parentItem;

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(parentItem.name),
        Padding(
            padding: const EdgeInsets.only(left: 8),
          child: Column(
            children: parentItem.items.map((item) => ItemWidget(item: item)).toList(),
          ),
        )
      ],
    );
  }
}
