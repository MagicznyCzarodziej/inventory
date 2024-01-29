import 'package:json_annotation/json_annotation.dart';

part '../generated/dto/UploadPhotoResponse.g.dart';

@JsonSerializable()
class UploadPhotoResponse {
  String photoId;

  UploadPhotoResponse(this.photoId);

  factory UploadPhotoResponse.fromJson(Map<String, dynamic> json) => _$UploadPhotoResponseFromJson(json);
}