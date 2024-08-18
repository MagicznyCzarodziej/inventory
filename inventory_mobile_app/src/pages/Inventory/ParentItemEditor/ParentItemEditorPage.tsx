import React from 'react';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { Page } from '../../../layouts/Page';
import { useGetCategories } from '../../../api/useGetCategories';
import { Spinner } from '../../../components/Spinner';
import { ParentItemEditor } from './ParentItemEditor';
import { useGetParentItem } from '../../../api/useGetParentItem';
import { CategoriesAndParentItemsStackParamsList } from '../../../navigation/navigationTypes';

type Props = NativeStackScreenProps<CategoriesAndParentItemsStackParamsList, "EDIT_PARENT_ITEM">

export const ParentItemEditorPage = (props: Props) => {
  const { parentItemId } = props.route.params;

  const parentItemQuery = useGetParentItem(parentItemId)
  const categoriesQuery = useGetCategories();

  if (parentItemQuery.isSuccess && categoriesQuery.isSuccess) {
    return <ParentItemEditor
      parentItem={parentItemQuery.data}
      categories={categoriesQuery.data.categories}
    />
  }

  return <Page>
    <Spinner />
  </Page>
}