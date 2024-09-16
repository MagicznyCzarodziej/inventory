import React, { useEffect, useState } from 'react';
import { GetItemResponse } from '../../../api/item/useGetItem';
import { Category } from '../../../api/common';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { CompositeNavigationProp, useNavigation } from '@react-navigation/native';
import { useUploadPhoto } from '../../../api/photo/useUploadPhoto';
import { useEditItem } from '../../../api/item/useEditItem';
import { Page } from '../../../layouts/Page';
import { EditablePhoto } from './EditablePhoto';
import { TextField } from '../../../components/TextInput';
import { Select } from '../../../components/Select';
import { TextInput } from 'react-native-paper';
import { Button } from '../../../components/Button';
import { StyleSheet, View, Text } from 'react-native';
import { ParallaxScrollView } from '../../../components/ParallaxScrollView';
import { Colors } from '../../../app/Theme';
import { WheelPicker } from '../../../components/WheelPicker';
import { useRemoveItem } from '../../../api/item/useRemoveItem';
import { mapCategoriesToCategorySelectItems } from '../utils/categoryUtils';
import { InventoryStackParamList, RootStackParamList } from '../../../navigation/navigationTypes';

interface ItemEditorProps {
  item: GetItemResponse
  categories: Category[]
  newPhotoPath?: string
  newBarcode?: string
}

type Navigation = CompositeNavigationProp<
  NativeStackNavigationProp<RootStackParamList, 'BARCODE_SCANNER'>,
  NativeStackNavigationProp<InventoryStackParamList, 'ITEM'>
>

export const ItemEditor = (props: ItemEditorProps) => {
  const { item, categories, newPhotoPath, newBarcode } = props
  const { navigate } = useNavigation<Navigation>()

  const categorySelectItems = mapCategoriesToCategorySelectItems(categories)

  const [name, setName] = useState<string>(item.name)
  const [description, setDescription] = useState<string | null>(item.description)
  const [brand, setBrand] = useState<string | null>(item.brand)
  const [categoryId, setCategoryId] = useState<string | null>(item.category.id)
  const [barcode, setBarcode] = useState<string | null>(newBarcode ?? item.barcode)
  const [currentStock, setCurrentStock] = useState<number>(item.currentStock)
  const [desiredStock, setDesiredStock] = useState<number>(item.desiredStock)
  const photoId = item.photoUrl?.split('/').pop() ?? null // TODO pass photoId from backend or something

  const uploadPhotoMutation = useUploadPhoto()

  useEffect(() => {
    if (newBarcode !== undefined) {
      setBarcode(newBarcode)
    }
  }, [newBarcode]);

  useEffect(() => {
    if (newPhotoPath) {
      uploadPhotoMutation.mutate({ path: newPhotoPath })
    }
  }, [newPhotoPath]);

  const editItemMutation = useEditItem()
  const removeItemMutation = useRemoveItem()

  const isValid = !uploadPhotoMutation.isPending

  const handleSave = async () => {
    if (!isValid) {
      return;
    }

    await editItemMutation.mutateAsync({
      itemId: item.id,
      request: {
        name,
        description,
        brand,
        barcode,
        categoryId: item.parentItem ? null : categoryId,
        currentStock,
        desiredStock,
        photoId: uploadPhotoMutation.data?.photoId ?? photoId,
      }
    })

    navigate("ITEM", { itemId: item.id })
  }

  const handleRemove = async () => {
    await removeItemMutation.mutateAsync(item.id)
    navigate("INVENTORY_LIST")
  }

  return <Page safeArea={false}>
    <ParallaxScrollView
      parallaxHeaderHeight={240}
      parallaxHeaderContent={
        <EditablePhoto
          originScreen="EDIT_ITEM"
          isLoading={uploadPhotoMutation.isPending}
          remotePhotoUrl={item.photoUrl}
          localPhotoPath={newPhotoPath}
        />
      }
    >
      <View style={styles.paddedContainer}>
        {item.parentItem &&
          <TextField
            label="Produkt nadrzędny"
            disabled
            value={item.parentItem.name}
          />
        }
        <TextField
          label="Nazwa"
          value={name}
          onChangeText={(value) => {
            setName(value)
          }}
        />
        <TextField
          label="Opis"
          value={description ?? undefined}
          onChangeText={(value) => {
            setDescription(value)
          }}
        />
        <TextField
          label="Firma/Producent"
          value={brand ?? undefined}
          onChangeText={(value) => {
            setBrand(value)
          }}
        />

        {!item.parentItem &&
          <Select value={categoryId ?? undefined} onValueChange={setCategoryId} items={categorySelectItems} />
        }

        <TextField
          label="Kod kreskowy"
          value={barcode ?? undefined}
          onChangeText={(value) => {
            setBarcode(value);
          }}
          right={
            <TextInput.Icon
              icon="barcode-scan"
              onPress={() => navigate("BARCODE_SCANNER", { from: 'EDIT_ITEM' })}
            />
          }
        />

        <View style={styles.stockControls}>
          <WheelPicker
            selectedIndex={currentStock}
            onChange={(index) => setCurrentStock(index)}
          />
          <Text style={styles.stockControlsDivider}>/</Text>
          <WheelPicker
            selectedIndex={desiredStock}
            onChange={(index) => setDesiredStock(index)}
          />
        </View>

        <View style={styles.controls}>
          <Button
            onPress={() => {
              handleRemove()
            }}
            title="Usuń"
            spinner={removeItemMutation.isPending}
            fullWidth
          />
          <Button
            onPress={() => {
              handleSave()
            }}
            title="Zapisz"
            disabled={!isValid}
            spinner={editItemMutation.isPending}
            fullWidth
          />
        </View>
      </View>
    </ParallaxScrollView>
  </Page>
}

const styles = StyleSheet.create({
  paddedContainer: {
    backgroundColor: Colors.background,
    padding: 16,
    flex: 1,
    display: "flex",
    elevation: 10,
  },
  controls: {
    display: 'flex',
    flexDirection: 'row',
    gap: 16,
    marginTop: "auto"
  },
  stockControls: {
    display: 'flex',
    flexDirection: 'row',
    gap: 16,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: "auto",
    marginBottom: "auto",
    paddingBottom: 20
  },
  stockControlsDivider: {
    fontSize: 40,
    color: Colors.gray.light,
  },
})