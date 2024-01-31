import 'package:json_annotation/json_annotation.dart';

part '../generated/dto/GetCategoriesResponse.g.dart';

@JsonSerializable()
class GetCategoriesResponse {
  List<Category> categories;

  GetCategoriesResponse(this.categories);

  factory GetCategoriesResponse.fromJson(Map<String, dynamic> json) =>
      _$GetCategoriesResponseFromJson(json);
}

@JsonSerializable()
class Category {
  String id;
  String name;

  Category(this.id, this.name);

  factory Category.fromJson(Map<String, dynamic> json) =>
      _$CategoryFromJson(json);
}
