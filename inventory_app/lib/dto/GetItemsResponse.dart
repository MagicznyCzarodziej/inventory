import 'package:json_annotation/json_annotation.dart';

part '../generated/dto/GetItemsResponse.g.dart';

@JsonSerializable()
class GetItemsResponse {
  List<Entry> entries;

  GetItemsResponse(this.entries);

  factory GetItemsResponse.fromJson(Map<String, dynamic> json) =>
      _$GetItemsResponseFromJson(json);
}

@JsonSerializable(createFactory: false)
sealed class Entry {
  Entry();

  factory Entry.fromJson(Map<String, dynamic> json) => json.containsKey("items")
      ? ParentEntry.fromJson(json)
      : ItemEntry.fromJson(json);
}

@JsonSerializable()
class ParentEntry implements Entry {
  String id;
  String name;
  List<ItemEntry> items;

  ParentEntry(this.id, this.name, this.items);

  factory ParentEntry.fromJson(Map<String, dynamic> json) =>
      _$ParentEntryFromJson(json);
}

@JsonSerializable()
class ItemEntry implements Entry {
  String id;
  String name;
  int currentStock;

  ItemEntry(this.id, this.name, this.currentStock);

  factory ItemEntry.fromJson(Map<String, dynamic> json) =>
      _$ItemEntryFromJson(json);
}
