import React from 'react';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { Page } from '../../../layouts/Page';
import { Spinner } from '../../../components/Spinner';
import { CategoryEditor } from './CategoryEditor';
import { useGetCategory } from '../../../api/category/useGetCategory';
import { CategoriesAndParentItemsStackParamsList } from '../../../navigation/navigationTypes';

type Props = NativeStackScreenProps<CategoriesAndParentItemsStackParamsList, "EDIT_CATEGORY">

export const CategoryEditorPage = (props: Props) => {
  const { categoryId } = props.route.params;

  const categoryQuery = useGetCategory(categoryId)

  if (categoryQuery.isSuccess) {
    return <CategoryEditor
      category={categoryQuery.data}
    />
  }

  return <Page safeArea={false}>
    <Spinner />
  </Page>
}