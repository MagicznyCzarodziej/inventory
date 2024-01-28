// GENERATED CODE - DO NOT MODIFY BY HAND

part of '../../dto/GetItemResponse.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

GetItemResponse _$GetItemResponseFromJson(Map<String, dynamic> json) =>
    GetItemResponse(
      json['id'] as String,
      json['name'] as String,
      json['description'] as String?,
      Category.fromJson(json['category'] as Map<String, dynamic>),
      json['parentItem'] == null
          ? null
          : ParentItem.fromJson(json['parentItem'] as Map<String, dynamic>),
      json['brand'] as String?,
      json['currentStock'] as int,
      json['desiredStock'] as int,
      json['lastRestockedAt'] as String?,
      json['photoUrl'] as String?,
      json['barcode'] as String?,
    );

Map<String, dynamic> _$GetItemResponseToJson(GetItemResponse instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'description': instance.description,
      'category': instance.category,
      'parentItem': instance.parentItem,
      'brand': instance.brand,
      'currentStock': instance.currentStock,
      'desiredStock': instance.desiredStock,
      'lastRestockedAt': instance.lastRestockedAt,
      'photoUrl': instance.photoUrl,
      'barcode': instance.barcode,
    };

Category _$CategoryFromJson(Map<String, dynamic> json) => Category(
      json['id'] as String,
      json['name'] as String,
    );

Map<String, dynamic> _$CategoryToJson(Category instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
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
