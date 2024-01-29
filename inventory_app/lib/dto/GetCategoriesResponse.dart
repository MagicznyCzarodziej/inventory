import 'package:json_annotation/json_annotation.dart';

part '../generated/dto/GetCategoriesResponse.g.dart';

@JsonSerializable()
class GetCategoriesResponse {
  List<ParentItem> categories;

  GetCategoriesResponse(this.categories);

  factory GetCategoriesResponse.fromJson(Map<String, dynamic> json) =>
      _$GetCategoriesResponseFromJson(json);
}

@JsonSerializable()
class ParentItem {
  String id;
  String name;

  ParentItem(this.id, this.name);

  factory ParentItem.fromJson(Map<String, dynamic> json) =>
      _$ParentItemFromJson(json);
}
