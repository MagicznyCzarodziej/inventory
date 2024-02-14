import 'dart:io';
import 'dart:typed_data';

import 'package:dio/dio.dart';
import 'package:inventory_app/api/HttpClient.dart';
import 'package:inventory_app/dto/UploadPhotoResponse.dart';
import 'package:path/path.dart';

Future<Uint8List> getPhoto(String photoUrl) async {
  var response = await HttpClient().client.get(
        photoUrl,
        options: Options(responseType: ResponseType.bytes),
      );
  return response.data;
}

Future<UploadPhotoResponse> uploadPhoto(File imageFile) async {
  var fileLength = await imageFile.length();
  var multipartFile = MultipartFile.fromStream(
    imageFile.openRead,
    fileLength,
    filename: basename(imageFile.path),
  );

  var data = FormData.fromMap({'file': multipartFile});
  // send
  var response = await HttpClient().client.post(
    '/photos',
    data: data,
    options: Options(
      headers: {"Content-Type": "multipart/form-data"},
    ),
  );

  return UploadPhotoResponse.fromJson(response.data);
}