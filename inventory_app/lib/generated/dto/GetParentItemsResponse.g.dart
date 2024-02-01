// GENERATED CODE - DO NOT MODIFY BY HAND

part of '../../dto/GetParentItemsResponse.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

GetParentItemsResponse _$GetParentItemsResponseFromJson(
        Map<String, dynamic> json) =>
    GetParentItemsResponse(
      (json['parentItems'] as List<dynamic>)
          .map((e) => ParentItem.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$GetParentItemsResponseToJson(
        GetParentItemsResponse instance) =>
    <String, dynamic>{
      'parentItems': instance.parentItems,
    };

ParentItem _$ParentItemFromJson(Map<String, dynamic> json) => ParentItem(
      json['id'] as String,
      json['name'] as String,
    );

Map<String, dynamic> _$ParentItemToJson(ParentItem instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
    };
