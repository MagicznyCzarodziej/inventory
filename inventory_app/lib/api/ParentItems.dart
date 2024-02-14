import 'dart:convert';

import 'package:inventory_app/api/HttpClient.dart';

Future<void> addParentItem(parentItem) async {
  await HttpClient().client.post(
        '/parent-items',
        data: jsonEncode(parentItem),
      );
}
