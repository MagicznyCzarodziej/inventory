// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'ItemPageBloc.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$ItemPageState {
  ItemState get itemState => throw _privateConstructorUsedError;
  GetItemResponse? get item => throw _privateConstructorUsedError;
  PhotoState get photoState => throw _privateConstructorUsedError;
  Uint8List? get photoBytes => throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $ItemPageStateCopyWith<ItemPageState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ItemPageStateCopyWith<$Res> {
  factory $ItemPageStateCopyWith(
          ItemPageState value, $Res Function(ItemPageState) then) =
      _$ItemPageStateCopyWithImpl<$Res, ItemPageState>;
  @useResult
  $Res call(
      {ItemState itemState,
      GetItemResponse? item,
      PhotoState photoState,
      Uint8List? photoBytes});
}

/// @nodoc
class _$ItemPageStateCopyWithImpl<$Res, $Val extends ItemPageState>
    implements $ItemPageStateCopyWith<$Res> {
  _$ItemPageStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? itemState = null,
    Object? item = freezed,
    Object? photoState = null,
    Object? photoBytes = freezed,
  }) {
    return _then(_value.copyWith(
      itemState: null == itemState
          ? _value.itemState
          : itemState // ignore: cast_nullable_to_non_nullable
              as ItemState,
      item: freezed == item
          ? _value.item
          : item // ignore: cast_nullable_to_non_nullable
              as GetItemResponse?,
      photoState: null == photoState
          ? _value.photoState
          : photoState // ignore: cast_nullable_to_non_nullable
              as PhotoState,
      photoBytes: freezed == photoBytes
          ? _value.photoBytes
          : photoBytes // ignore: cast_nullable_to_non_nullable
              as Uint8List?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$ItemPageStateImplCopyWith<$Res>
    implements $ItemPageStateCopyWith<$Res> {
  factory _$$ItemPageStateImplCopyWith(
          _$ItemPageStateImpl value, $Res Function(_$ItemPageStateImpl) then) =
      __$$ItemPageStateImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {ItemState itemState,
      GetItemResponse? item,
      PhotoState photoState,
      Uint8List? photoBytes});
}

/// @nodoc
class __$$ItemPageStateImplCopyWithImpl<$Res>
    extends _$ItemPageStateCopyWithImpl<$Res, _$ItemPageStateImpl>
    implements _$$ItemPageStateImplCopyWith<$Res> {
  __$$ItemPageStateImplCopyWithImpl(
      _$ItemPageStateImpl _value, $Res Function(_$ItemPageStateImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? itemState = null,
    Object? item = freezed,
    Object? photoState = null,
    Object? photoBytes = freezed,
  }) {
    return _then(_$ItemPageStateImpl(
      itemState: null == itemState
          ? _value.itemState
          : itemState // ignore: cast_nullable_to_non_nullable
              as ItemState,
      item: freezed == item
          ? _value.item
          : item // ignore: cast_nullable_to_non_nullable
              as GetItemResponse?,
      photoState: null == photoState
          ? _value.photoState
          : photoState // ignore: cast_nullable_to_non_nullable
              as PhotoState,
      photoBytes: freezed == photoBytes
          ? _value.photoBytes
          : photoBytes // ignore: cast_nullable_to_non_nullable
              as Uint8List?,
    ));
  }
}

/// @nodoc

class _$ItemPageStateImpl implements _ItemPageState {
  const _$ItemPageStateImpl(
      {required this.itemState,
      required this.item,
      required this.photoState,
      required this.photoBytes});

  @override
  final ItemState itemState;
  @override
  final GetItemResponse? item;
  @override
  final PhotoState photoState;
  @override
  final Uint8List? photoBytes;

  @override
  String toString() {
    return 'ItemPageState(itemState: $itemState, item: $item, photoState: $photoState, photoBytes: $photoBytes)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ItemPageStateImpl &&
            (identical(other.itemState, itemState) ||
                other.itemState == itemState) &&
            (identical(other.item, item) || other.item == item) &&
            (identical(other.photoState, photoState) ||
                other.photoState == photoState) &&
            const DeepCollectionEquality()
                .equals(other.photoBytes, photoBytes));
  }

  @override
  int get hashCode => Object.hash(runtimeType, itemState, item, photoState,
      const DeepCollectionEquality().hash(photoBytes));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ItemPageStateImplCopyWith<_$ItemPageStateImpl> get copyWith =>
      __$$ItemPageStateImplCopyWithImpl<_$ItemPageStateImpl>(this, _$identity);
}

abstract class _ItemPageState implements ItemPageState {
  const factory _ItemPageState(
      {required final ItemState itemState,
      required final GetItemResponse? item,
      required final PhotoState photoState,
      required final Uint8List? photoBytes}) = _$ItemPageStateImpl;

  @override
  ItemState get itemState;
  @override
  GetItemResponse? get item;
  @override
  PhotoState get photoState;
  @override
  Uint8List? get photoBytes;
  @override
  @JsonKey(ignore: true)
  _$$ItemPageStateImplCopyWith<_$ItemPageStateImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$ItemPageEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String itemId) fetchItem,
    required TResult Function(String itemId, int stockChange)
        updateCurrentStock,
    required TResult Function(String photoUrl) fetchPhoto,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String itemId)? fetchItem,
    TResult? Function(String itemId, int stockChange)? updateCurrentStock,
    TResult? Function(String photoUrl)? fetchPhoto,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String itemId)? fetchItem,
    TResult Function(String itemId, int stockChange)? updateCurrentStock,
    TResult Function(String photoUrl)? fetchPhoto,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_FetchItem value) fetchItem,
    required TResult Function(_UpdateCurrentStock value) updateCurrentStock,
    required TResult Function(_FetchPhoto value) fetchPhoto,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_FetchItem value)? fetchItem,
    TResult? Function(_UpdateCurrentStock value)? updateCurrentStock,
    TResult? Function(_FetchPhoto value)? fetchPhoto,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_FetchItem value)? fetchItem,
    TResult Function(_UpdateCurrentStock value)? updateCurrentStock,
    TResult Function(_FetchPhoto value)? fetchPhoto,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ItemPageEventCopyWith<$Res> {
  factory $ItemPageEventCopyWith(
          ItemPageEvent value, $Res Function(ItemPageEvent) then) =
      _$ItemPageEventCopyWithImpl<$Res, ItemPageEvent>;
}

/// @nodoc
class _$ItemPageEventCopyWithImpl<$Res, $Val extends ItemPageEvent>
    implements $ItemPageEventCopyWith<$Res> {
  _$ItemPageEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$FetchItemImplCopyWith<$Res> {
  factory _$$FetchItemImplCopyWith(
          _$FetchItemImpl value, $Res Function(_$FetchItemImpl) then) =
      __$$FetchItemImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String itemId});
}

/// @nodoc
class __$$FetchItemImplCopyWithImpl<$Res>
    extends _$ItemPageEventCopyWithImpl<$Res, _$FetchItemImpl>
    implements _$$FetchItemImplCopyWith<$Res> {
  __$$FetchItemImplCopyWithImpl(
      _$FetchItemImpl _value, $Res Function(_$FetchItemImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? itemId = null,
  }) {
    return _then(_$FetchItemImpl(
      null == itemId
          ? _value.itemId
          : itemId // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$FetchItemImpl implements _FetchItem {
  const _$FetchItemImpl(this.itemId);

  @override
  final String itemId;

  @override
  String toString() {
    return 'ItemPageEvent.fetchItem(itemId: $itemId)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$FetchItemImpl &&
            (identical(other.itemId, itemId) || other.itemId == itemId));
  }

  @override
  int get hashCode => Object.hash(runtimeType, itemId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$FetchItemImplCopyWith<_$FetchItemImpl> get copyWith =>
      __$$FetchItemImplCopyWithImpl<_$FetchItemImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String itemId) fetchItem,
    required TResult Function(String itemId, int stockChange)
        updateCurrentStock,
    required TResult Function(String photoUrl) fetchPhoto,
  }) {
    return fetchItem(itemId);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String itemId)? fetchItem,
    TResult? Function(String itemId, int stockChange)? updateCurrentStock,
    TResult? Function(String photoUrl)? fetchPhoto,
  }) {
    return fetchItem?.call(itemId);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String itemId)? fetchItem,
    TResult Function(String itemId, int stockChange)? updateCurrentStock,
    TResult Function(String photoUrl)? fetchPhoto,
    required TResult orElse(),
  }) {
    if (fetchItem != null) {
      return fetchItem(itemId);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_FetchItem value) fetchItem,
    required TResult Function(_UpdateCurrentStock value) updateCurrentStock,
    required TResult Function(_FetchPhoto value) fetchPhoto,
  }) {
    return fetchItem(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_FetchItem value)? fetchItem,
    TResult? Function(_UpdateCurrentStock value)? updateCurrentStock,
    TResult? Function(_FetchPhoto value)? fetchPhoto,
  }) {
    return fetchItem?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_FetchItem value)? fetchItem,
    TResult Function(_UpdateCurrentStock value)? updateCurrentStock,
    TResult Function(_FetchPhoto value)? fetchPhoto,
    required TResult orElse(),
  }) {
    if (fetchItem != null) {
      return fetchItem(this);
    }
    return orElse();
  }
}

abstract class _FetchItem implements ItemPageEvent {
  const factory _FetchItem(final String itemId) = _$FetchItemImpl;

  String get itemId;
  @JsonKey(ignore: true)
  _$$FetchItemImplCopyWith<_$FetchItemImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$UpdateCurrentStockImplCopyWith<$Res> {
  factory _$$UpdateCurrentStockImplCopyWith(_$UpdateCurrentStockImpl value,
          $Res Function(_$UpdateCurrentStockImpl) then) =
      __$$UpdateCurrentStockImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String itemId, int stockChange});
}

/// @nodoc
class __$$UpdateCurrentStockImplCopyWithImpl<$Res>
    extends _$ItemPageEventCopyWithImpl<$Res, _$UpdateCurrentStockImpl>
    implements _$$UpdateCurrentStockImplCopyWith<$Res> {
  __$$UpdateCurrentStockImplCopyWithImpl(_$UpdateCurrentStockImpl _value,
      $Res Function(_$UpdateCurrentStockImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? itemId = null,
    Object? stockChange = null,
  }) {
    return _then(_$UpdateCurrentStockImpl(
      null == itemId
          ? _value.itemId
          : itemId // ignore: cast_nullable_to_non_nullable
              as String,
      null == stockChange
          ? _value.stockChange
          : stockChange // ignore: cast_nullable_to_non_nullable
              as int,
    ));
  }
}

/// @nodoc

class _$UpdateCurrentStockImpl implements _UpdateCurrentStock {
  const _$UpdateCurrentStockImpl(this.itemId, this.stockChange);

  @override
  final String itemId;
  @override
  final int stockChange;

  @override
  String toString() {
    return 'ItemPageEvent.updateCurrentStock(itemId: $itemId, stockChange: $stockChange)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$UpdateCurrentStockImpl &&
            (identical(other.itemId, itemId) || other.itemId == itemId) &&
            (identical(other.stockChange, stockChange) ||
                other.stockChange == stockChange));
  }

  @override
  int get hashCode => Object.hash(runtimeType, itemId, stockChange);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$UpdateCurrentStockImplCopyWith<_$UpdateCurrentStockImpl> get copyWith =>
      __$$UpdateCurrentStockImplCopyWithImpl<_$UpdateCurrentStockImpl>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String itemId) fetchItem,
    required TResult Function(String itemId, int stockChange)
        updateCurrentStock,
    required TResult Function(String photoUrl) fetchPhoto,
  }) {
    return updateCurrentStock(itemId, stockChange);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String itemId)? fetchItem,
    TResult? Function(String itemId, int stockChange)? updateCurrentStock,
    TResult? Function(String photoUrl)? fetchPhoto,
  }) {
    return updateCurrentStock?.call(itemId, stockChange);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String itemId)? fetchItem,
    TResult Function(String itemId, int stockChange)? updateCurrentStock,
    TResult Function(String photoUrl)? fetchPhoto,
    required TResult orElse(),
  }) {
    if (updateCurrentStock != null) {
      return updateCurrentStock(itemId, stockChange);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_FetchItem value) fetchItem,
    required TResult Function(_UpdateCurrentStock value) updateCurrentStock,
    required TResult Function(_FetchPhoto value) fetchPhoto,
  }) {
    return updateCurrentStock(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_FetchItem value)? fetchItem,
    TResult? Function(_UpdateCurrentStock value)? updateCurrentStock,
    TResult? Function(_FetchPhoto value)? fetchPhoto,
  }) {
    return updateCurrentStock?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_FetchItem value)? fetchItem,
    TResult Function(_UpdateCurrentStock value)? updateCurrentStock,
    TResult Function(_FetchPhoto value)? fetchPhoto,
    required TResult orElse(),
  }) {
    if (updateCurrentStock != null) {
      return updateCurrentStock(this);
    }
    return orElse();
  }
}

abstract class _UpdateCurrentStock implements ItemPageEvent {
  const factory _UpdateCurrentStock(
      final String itemId, final int stockChange) = _$UpdateCurrentStockImpl;

  String get itemId;
  int get stockChange;
  @JsonKey(ignore: true)
  _$$UpdateCurrentStockImplCopyWith<_$UpdateCurrentStockImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$FetchPhotoImplCopyWith<$Res> {
  factory _$$FetchPhotoImplCopyWith(
          _$FetchPhotoImpl value, $Res Function(_$FetchPhotoImpl) then) =
      __$$FetchPhotoImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String photoUrl});
}

/// @nodoc
class __$$FetchPhotoImplCopyWithImpl<$Res>
    extends _$ItemPageEventCopyWithImpl<$Res, _$FetchPhotoImpl>
    implements _$$FetchPhotoImplCopyWith<$Res> {
  __$$FetchPhotoImplCopyWithImpl(
      _$FetchPhotoImpl _value, $Res Function(_$FetchPhotoImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? photoUrl = null,
  }) {
    return _then(_$FetchPhotoImpl(
      null == photoUrl
          ? _value.photoUrl
          : photoUrl // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$FetchPhotoImpl implements _FetchPhoto {
  const _$FetchPhotoImpl(this.photoUrl);

  @override
  final String photoUrl;

  @override
  String toString() {
    return 'ItemPageEvent.fetchPhoto(photoUrl: $photoUrl)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$FetchPhotoImpl &&
            (identical(other.photoUrl, photoUrl) ||
                other.photoUrl == photoUrl));
  }

  @override
  int get hashCode => Object.hash(runtimeType, photoUrl);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$FetchPhotoImplCopyWith<_$FetchPhotoImpl> get copyWith =>
      __$$FetchPhotoImplCopyWithImpl<_$FetchPhotoImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String itemId) fetchItem,
    required TResult Function(String itemId, int stockChange)
        updateCurrentStock,
    required TResult Function(String photoUrl) fetchPhoto,
  }) {
    return fetchPhoto(photoUrl);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String itemId)? fetchItem,
    TResult? Function(String itemId, int stockChange)? updateCurrentStock,
    TResult? Function(String photoUrl)? fetchPhoto,
  }) {
    return fetchPhoto?.call(photoUrl);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String itemId)? fetchItem,
    TResult Function(String itemId, int stockChange)? updateCurrentStock,
    TResult Function(String photoUrl)? fetchPhoto,
    required TResult orElse(),
  }) {
    if (fetchPhoto != null) {
      return fetchPhoto(photoUrl);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_FetchItem value) fetchItem,
    required TResult Function(_UpdateCurrentStock value) updateCurrentStock,
    required TResult Function(_FetchPhoto value) fetchPhoto,
  }) {
    return fetchPhoto(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_FetchItem value)? fetchItem,
    TResult? Function(_UpdateCurrentStock value)? updateCurrentStock,
    TResult? Function(_FetchPhoto value)? fetchPhoto,
  }) {
    return fetchPhoto?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_FetchItem value)? fetchItem,
    TResult Function(_UpdateCurrentStock value)? updateCurrentStock,
    TResult Function(_FetchPhoto value)? fetchPhoto,
    required TResult orElse(),
  }) {
    if (fetchPhoto != null) {
      return fetchPhoto(this);
    }
    return orElse();
  }
}

abstract class _FetchPhoto implements ItemPageEvent {
  const factory _FetchPhoto(final String photoUrl) = _$FetchPhotoImpl;

  String get photoUrl;
  @JsonKey(ignore: true)
  _$$FetchPhotoImplCopyWith<_$FetchPhotoImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
