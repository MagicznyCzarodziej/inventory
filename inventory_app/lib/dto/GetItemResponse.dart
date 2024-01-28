import 'package:json_annotation/json_annotation.dart';

part '../generated/dto/GetItemResponse.g.dart';

@JsonSerializable()
class GetItemResponse {
  String id;
  String name;
  String? description;
  Category category;
  ParentItem? parentItem;
  String? brand;
  int currentStock;
  int desiredStock;
  String? lastRestockedAt;
  String? photoUrl;
  String? barcode;

  GetItemResponse(
    this.id,
    this.name,
    this.description,
    this.category,
    this.parentItem,
    this.brand,
    this.currentStock,
    this.desiredStock,
    this.lastRestockedAt,
    this.photoUrl,
    this.barcode,
  );

  factory GetItemResponse.fromJson(Map<String, dynamic> json) =>
      _$GetItemResponseFromJson(json);
}

@JsonSerializable()
class Category {
  String id;
  String name;

  Category(this.id, this.name);

  factory Category.fromJson(Map<String, dynamic> json) =>
      _$CategoryFromJson(json);
}

@JsonSerializable()
class ParentItem {
  String id;
  String name;

  ParentItem(this.id, this.name);

  factory ParentItem.fromJson(Map<String, dynamic> json) =>
      _$ParentItemFromJson(json);
}
