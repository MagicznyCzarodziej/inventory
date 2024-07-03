import { Page } from '../../../../layouts/Page';
import { StyleSheet, View } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { InventoryStackParamList } from '../../InventoryList/InventoryNavigation';
import { TextField } from '../../../../components/TextInput';
import { Button } from '../../../../components/Button';
import { Icon } from 'react-native-paper';
import React, { useRef, useState } from 'react';
import { useGetCategories } from '../../../../api/useGetCategories';
import { Select } from '../../../../components/Select';
import { useCreateItem } from '../../../../api/useCreateItem';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { InventoryTabsParamList } from '../../InventoryTabNavigation';

type Props = NativeStackScreenProps<InventoryStackParamList, "ADD_ITEM">;

export const ItemCreatorPage = (props: Props) => {
  const { parentId, nameDraft } = props.route.params;

  const { navigate } = useNavigation<NavigationProp<InventoryTabsParamList>>()

  const descriptionRef = useRef(null);
  const brandRef = useRef(null);

  const [name, setName] = useState(nameDraft);
  const [description, setDescription] = useState<string>();
  const [brand, setBrand] = useState<string>();
  const [categoryId, setCategoryId] = useState<string>();
  const [currentStock, setCurrentStock] = useState<number>(1);
  const [desiredStock, setDesiredStock] = useState<number>(1);
  const [barcode, setBarcode] = useState<string>();

  const categoriesQuery = useGetCategories();
  const categorySelectItems = categoriesQuery.data?.categories.map(category => ({
    value: category.id,
    label: category.name
  })) ?? []

  const createItemMutation = useCreateItem();
  const handleCreateItem = () => {
    if (name === undefined || description === undefined || brand === undefined) {
      return undefined;
    }

    createItemMutation.mutate({
      itemType: !!parentId ? 'SUB_ITEM' : 'ITEM',
      name,
      description,
      brand,
      categoryId,
      parentId,
      currentStock,
      desiredStock,
      barcode,
      photoId: undefined,
    });

    navigate("INVENTORY_NAVIGATION", { screen: "INVENTORY_LIST" })
  }

  return <Page style={styles.page}>
    <TextField
      label="Nazwa"
      value={name}
      autofocus
      nextTextFieldRef={descriptionRef}
      onChangeText={(value) => {
        setName(value)
      }}
    />
    <TextField
      label="Opis"
      value={description}
      ref={descriptionRef}
      nextTextFieldRef={brandRef}
      onChangeText={(value) => {
        setDescription(value)
      }}
    />
    <TextField
      label="Firma/Producent"
      value={brand}
      ref={brandRef}
      onChangeText={(value) => {
        setBrand(value)
      }}
    />

    {!parentId &&
      <Select value={categoryId} onValueChange={setCategoryId} items={categorySelectItems} />
    }

    <View style={styles.barcodeWrapper}>
      <Icon source="barcode" size={28} />
      <Button onPress={() => {
      }} title="Zeskanuj kod kreskowy" variant="text" small />
    </View>
    <View style={styles.saveButton}>
      <Button onPress={handleCreateItem} title="Zapisz" />
    </View>
  </Page>
}

const styles = StyleSheet.create({
  page: {
    padding: 16,
    display: 'flex',
    flexDirection: 'column'
  },
  barcodeWrapper: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: "center",
    alignItems: "center",
  },
  saveButton: {
    marginTop: "auto"
  },
})