import 'dart:convert';

import 'package:inventory_app/api/HttpClient.dart';
import 'package:inventory_app/dto/GetItemResponse.dart';
import 'package:inventory_app/dto/GetItemsResponse.dart';

Future<GetItemsResponse> getItems() async {
  var response = await HttpClient().client.get('/items');

  return GetItemsResponse.fromJson(response.data);
}

Future<GetItemResponse> getItem(String itemId) async {
  var response = await HttpClient().client.get('/items/$itemId');

  return GetItemResponse.fromJson(response.data);
}

Future<void> addItem(itemDraft) async {
  await HttpClient().client.post(
        '/items',
        data: jsonEncode(itemDraft),
      );
}

Future<void> deleteItem(String itemId) async {
  await HttpClient().client.delete('/items/$itemId');
}

Future<void> updateCurrentStock(
  String itemId,
  int stockChange,
) async {
  await HttpClient().client.put(
        '/items/$itemId/stock/current',
        data: jsonEncode({"stockChange": stockChange}),
      );
}
