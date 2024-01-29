import 'dart:convert';
import 'dart:io';
import 'package:inventory_app/dto/UploadPhotoResponse.dart';
import 'package:path/path.dart';
import 'package:async/async.dart';
import 'package:http/http.dart' as http;
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

Future<UploadPhotoResponse> upload(File imageFile) async {
  // open a bytestream
  var stream =
      http.ByteStream(DelegatingStream(imageFile.openRead())); //.cast()
  // get file length
  var length = await imageFile.length();

  // string to uri
  var uri = Uri.parse("http://192.168.0.66:8080/photos");

  // create multipart request
  var request = http.MultipartRequest("POST", uri);

  // multipart that takes file
  var multipartFile = http.MultipartFile('file', stream, length,
      filename: basename(imageFile.path));

  // add file to multipart
  request.files.add(multipartFile);

  // send
  var response = await request.send();
  print(response.statusCode);

  // listen for response
  var str = await response.stream.transform(utf8.decoder).join();
  return UploadPhotoResponse.fromJson(jsonDecode(str));
}
