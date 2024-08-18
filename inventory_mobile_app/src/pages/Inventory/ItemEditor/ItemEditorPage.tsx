import React from 'react';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { useGetItem } from '../../../api/useGetItem';
import { useGetCategories } from '../../../api/useGetCategories';
import { CompositeScreenProps } from '@react-navigation/native';
import { ItemEditor } from './ItemEditor';
import { Page } from '../../../layouts/Page';
import { Spinner } from '../../../components/Spinner';
import { InventoryStackParamList, RootStackParamList } from '../../../navigation/navigationTypes';

type Props = CompositeScreenProps<
  NativeStackScreenProps<InventoryStackParamList, 'EDIT_ITEM'>,
  NativeStackScreenProps<RootStackParamList, "BARCODE_SCANNER">
>

export const ItemEditorPage = (props: Props) => {
  const itemId = props.route.params.itemId!
  const { photoPath, barcode } = props.route.params

  const itemQuery = useGetItem(itemId)
  const categoriesQuery = useGetCategories();

  if (itemQuery.isSuccess && categoriesQuery.isSuccess) {
    return <ItemEditor
      item={itemQuery.data}
      categories={categoriesQuery.data.categories}
      newPhotoPath={photoPath}
      newBarcode={barcode}
    />
  }

  return <Page>
    <Spinner />
  </Page>
}
