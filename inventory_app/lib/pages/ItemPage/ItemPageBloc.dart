import 'dart:typed_data';

import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:inventory_app/api/Items.dart' as itemApi;
import 'package:inventory_app/api/Photo.dart' as photoApi;
import 'package:inventory_app/dto/GetItemResponse.dart';

part 'ItemPageBloc.freezed.dart';

enum ItemState {
  idle,
  fetching,
  fetched,
  error,
}

enum PhotoState {
  idle,
  fetching,
  fetched,
  error,
}

@freezed
class ItemPageState with _$ItemPageState {
  const factory ItemPageState({
    required ItemState itemState,
    required GetItemResponse? item,
    required PhotoState photoState,
    required Uint8List? photoBytes,
  }) = _ItemPageState;

  factory ItemPageState.initial() {
    return const ItemPageState(
      itemState: ItemState.idle,
      item: null,
      photoState: PhotoState.idle,
      photoBytes: null,
    );
  }
}

@freezed
class ItemPageEvent with _$ItemPageEvent {
  const factory ItemPageEvent.fetchItem(String itemId) = _FetchItem;

  const factory ItemPageEvent.updateCurrentStock(String itemId, int stockChange) = _UpdateCurrentStock;

  const factory ItemPageEvent.fetchPhoto(String photoUrl) = _FetchPhoto;
}

class ItemPageBloc extends Bloc<ItemPageEvent, ItemPageState> {
  ItemPageBloc() : super(ItemPageState.initial()) {
    on<ItemPageEvent>((event, emit) async {
      await event.map(
        fetchItem: (payload) async => await _fetchItem(emit, payload.itemId),
        updateCurrentStock: (payload) async => await _updateCurrentStock(emit, payload.itemId, payload.stockChange),
        fetchPhoto: (payload) async => await _fetchPhoto(emit, payload.photoUrl),
      );
    });
  }

  _fetchItem(Emitter<ItemPageState> emit, String itemId) async {
    emit(state.copyWith(
      itemState: ItemState.fetching,
    ));

    try {
      var item = await itemApi.getItem(itemId);
      emit(state.copyWith(
        itemState: ItemState.fetched,
        item: item,
      ));
      if (item.photoUrl != null) {
        add(ItemPageEvent.fetchPhoto(item.photoUrl!));
      }
    } catch (error) {
      print(error);
      emit(state.copyWith(
        itemState: ItemState.error,
      ));
    }
  }

  _updateCurrentStock(Emitter<ItemPageState> emit, String itemId, int stockChange) async {
    await itemApi.updateCurrentStock(itemId, stockChange);
    var item = await itemApi.getItem(itemId);
    emit(state.copyWith(
      itemState: ItemState.fetched,
      item: item,
    ));
  }

  _fetchPhoto(Emitter<ItemPageState> emit, String photoUrl) async {
    emit(state.copyWith(
      photoState: PhotoState.fetching,
    ));

    try {
      var photoBytes = await photoApi.getPhoto(photoUrl);
      emit(state.copyWith(
        photoState: PhotoState.fetched,
        photoBytes: photoBytes,
      ));
    } catch (error) {
      print(error);
      emit(state.copyWith(
        photoState: PhotoState.error,
      ));
    }
  }
}
