// GENERATED CODE - DO NOT MODIFY BY HAND

part of '../../dto/GetItemsResponse.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

GetItemsResponse _$GetItemsResponseFromJson(Map<String, dynamic> json) =>
    GetItemsResponse(
      (json['entries'] as List<dynamic>)
          .map((e) => Entry.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$GetItemsResponseToJson(GetItemsResponse instance) =>
    <String, dynamic>{
      'entries': instance.entries,
    };

Map<String, dynamic> _$EntryToJson(Entry instance) => <String, dynamic>{};

ParentEntry _$ParentEntryFromJson(Map<String, dynamic> json) => ParentEntry(
      json['id'] as String,
      json['name'] as String,
      (json['items'] as List<dynamic>)
          .map((e) => ItemEntry.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$ParentEntryToJson(ParentEntry instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'items': instance.items,
    };

ItemEntry _$ItemEntryFromJson(Map<String, dynamic> json) => ItemEntry(
      json['id'] as String,
      json['name'] as String,
      json['currentStock'] as int,
    );

Map<String, dynamic> _$ItemEntryToJson(ItemEntry instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'currentStock': instance.currentStock,
    };
