import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:http/http.dart';

import '../utils.dart';

class MediaType {
  static String Json = "application/json;charset=UTF-8";
}

class HttpClient {
  static Future<Response> get(String uri) => http.get(
        Uri.parse(API_URL + uri),
        headers: {"Accept": MediaType.Json},
      );

  static Future getJson(String uri) async {
    var response = await get(uri);
    return jsonDecode(response.body);
  }

  static Future<Response> post(String uri, Object? body) => http.post(
        Uri.parse(API_URL + uri),
        body: body,
        headers: {"Content-Type": MediaType.Json, "Accept": MediaType.Json},
      );

  static Future postJson(String uri, Object? body) async {
    var response = await post(uri, body);
    return jsonDecode(response.body);
  }

  static Future<Response> put(String uri, Object? body) => http.put(
        Uri.parse(API_URL + uri),
        body: body,
        headers: {"Content-Type": MediaType.Json},
      );

  static Future putJson(String uri, Object? body) async {
    var response = await put(uri, body);
    if (response.statusCode == 204) return null;
    return jsonDecode(response.body);
  }

  static Future<Response> delete(String uri) => http.delete(
        Uri.parse(API_URL + uri),
      );
}
