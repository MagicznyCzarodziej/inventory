import 'dart:convert';

import 'package:flutter/material.dart';

import 'api/HttpClient.dart';

Color getCurrentStockColor(int currentStock, int desiredStock) {
  if (currentStock < 1) {
    return Colors.red;
  } else if (currentStock < 2 && desiredStock > 1) {
    return Colors.pink;
  } else if (currentStock < desiredStock / 2) {
    return Colors.orange;
  } else if (currentStock > desiredStock) {
    return Colors.teal;
  } else {
    return Colors.green;
  }
}

extension SearchInString on String? {
  bool containsIgnoreCase(String query) =>
      this == null ? false : this!.toLowerCase().contains(query.toLowerCase());
}

Future<void> updateCurrentStock(
    String itemId,
    int stockChange,
    ) {
  return HttpClient.putJson(
    '/items/$itemId/stock/current',
    jsonEncode({"stockChange": stockChange}),
  );
}