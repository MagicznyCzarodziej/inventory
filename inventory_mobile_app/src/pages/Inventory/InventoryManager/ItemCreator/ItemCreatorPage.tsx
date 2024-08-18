import { Page } from '../../../../layouts/Page';
import { StyleSheet, View } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { InventoryStackParamList } from '../../InventoryList/InventoryNavigation';
import { TextField } from '../../../../components/TextInput';
import { Button } from '../../../../components/Button';
import { Text, TextInput } from 'react-native-paper';
import React, { useEffect, useRef, useState } from 'react';
import { Select } from '../../../../components/Select';
import { useCreateItem } from '../../../../api/useCreateItem';
import { CompositeScreenProps } from '@react-navigation/native';
import { InventoryTabsParamList } from '../../InventoryTabNavigation';
import { Colors } from '../../../../app/Theme';
import { useUploadPhoto } from '../../../../api/useUploadPhoto';
import { PhotoUpload } from './PhotoUpload';
import { WheelPicker } from '../../../../components/WheelPicker';
import { RootStackParamList } from '../../../../app/Root';
import { ParallaxScrollView } from '../../../../components/ParallaxScrollView';
import { useCategorySelect } from '../../utils/categoryUtils';

type Props = CompositeScreenProps<
  CompositeScreenProps<
    NativeStackScreenProps<InventoryStackParamList, 'ADD_ITEM'>,
    NativeStackScreenProps<InventoryTabsParamList, "INVENTORY_NAVIGATION">
  >,
  NativeStackScreenProps<RootStackParamList, "BARCODE_SCANNER">
>

export const ItemCreatorPage = (props: Props) => {
  const { nameDraft, barcode: scannedBarcode, photoPath, parent } = props.route.params;
  const { navigate } = props.navigation

  const descriptionRef = useRef(null);
  const brandRef = useRef(null);
  const barcodeRef = useRef(null);

  const [name, setName] = useState<string>(nameDraft ?? "");
  const [description, setDescription] = useState<string>();
  const [brand, setBrand] = useState<string>();
  const [currentStock, setCurrentStock] = useState<number>(1);
  const [desiredStock, setDesiredStock] = useState<number>(1);
  const [barcode, setBarcode] = useState<string>(scannedBarcode ?? "");
  const [photo, setPhoto] = useState<string | undefined>(photoPath);

  const { categoryId, setCategoryId, categorySelectItems } = useCategorySelect()

  const uploadPhotoMutation = useUploadPhoto()
  // Set photo after taking a photo
  useEffect(() => {
    if (photoPath !== undefined) {
      setPhoto(photoPath)
      uploadPhotoMutation.mutate({ path: photoPath })
    }
  }, [photoPath]);

  // Set barcode after scanning
  useEffect(() => {
    if (scannedBarcode !== undefined) {
      setBarcode(scannedBarcode)
    }
  }, [scannedBarcode]);

  const isValid =
    name.trim().length > 0 &&
    !uploadPhotoMutation.isPending

  const createItemMutation = useCreateItem();
  const handleCreateItem = async () => {
    if (!isValid) {
      return undefined;
    }

    try {
      await createItemMutation.mutateAsync({
        itemType: !!parent ? 'SUB_ITEM' : 'ITEM',
        name: name!,
        description: description!,
        brand: brand!,
        categoryId: parent ? undefined : categoryId,
        parentId: parent?.parentId,
        currentStock,
        desiredStock,
        barcode,
        photoId: uploadPhotoMutation.data?.photoId,
      });

      navigate("INVENTORY_LIST")
    } catch (error) {
      console.log(error)
    }
  }

  return <Page style={styles.page} safeArea={false}>
    <ParallaxScrollView
      parallaxHeaderHeight={240}
      parallaxHeaderContent={
        <PhotoUpload photoPath={photo} isPhotoUploaded={uploadPhotoMutation.isSuccess} />
      }>
      <View style={styles.paddedContainer}>
        {parent &&
          <TextField
            label="Produkt nadrzędny"
            disabled
            value={parent.parentName}
          />
        }
        <TextField
          label="Nazwa"
          value={name}
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
          nextTextFieldRef={barcodeRef}
          onChangeText={(value) => {
            setBrand(value)
          }}
        />

        {!parent &&
          <Select value={categoryId} onValueChange={setCategoryId} items={categorySelectItems} />
        }

        <TextField
          label="Kod kreskowy"
          value={barcode}
          ref={barcodeRef}
          onChangeText={(value) => {
            setBarcode(value);
          }}
          right={
            <TextInput.Icon
              icon="barcode-scan"
              onPress={() => navigate("BARCODE_SCANNER", { from: 'ADD_ITEM' })}
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

        <View style={styles.saveButton}>
          <Button
            onPress={() => {
              handleCreateItem()
            }}
            title="Zapisz"
            disabled={!isValid}
          />
        </View>

      </View>
    </ParallaxScrollView>
  </Page>
}

const styles = StyleSheet.create({
  page: {},
  paddedContainer: {
    backgroundColor: Colors.background,
    flexGrow: 1,
    padding: 16,
    elevation: 10,
  },
  header: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    borderBlockColor: Colors.gray.light,
    borderBottomWidth: 1,
  },
  headerText: {
    fontSize: 16,
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
  saveButton: {
    marginTop: "auto"
  },
})