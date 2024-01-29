import 'package:json_annotation/json_annotation.dart';

part '../generated/dto/GetParentItemsResponse.g.dart';

@JsonSerializable()
class GetParentItemsResponse {
  List<ParentItem> parentItems;

  GetParentItemsResponse(this.parentItems);

  factory GetParentItemsResponse.fromJson(Map<String, dynamic> json) =>
      _$GetParentItemsResponseFromJson(json);
}

@JsonSerializable()
class ParentItem {
  String id;
  String name;

  ParentItem(this.id, this.name);

  factory ParentItem.fromJson(Map<String, dynamic> json) =>
      _$ParentItemFromJson(json);
}
