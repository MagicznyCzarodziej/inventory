import 'package:flutter/material.dart';

Color getCurrentStockColor(int currentStock, int desiredStock) {
  if (currentStock < 1 && desiredStock > 0)
    return Colors.redAccent[100]!;
  else if (currentStock < desiredStock)
    return Colors.orangeAccent[100]!;
  else
    return Colors.green;
}

extension SearchInString on String? {
  bool containsIgnoreCase(String query) => this == null ? false : this!.toLowerCase().contains(query.toLowerCase());
}
