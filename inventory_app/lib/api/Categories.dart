import 'package:inventory_app/api/HttpClient.dart';
import 'package:inventory_app/dto/GetCategoriesResponse.dart';

Future<GetCategoriesResponse> getCategories() async {
  var response = await HttpClient().client.get('/categories');

  return GetCategoriesResponse.fromJson(response.data);
}