import { FlatList, StyleSheet, View } from 'react-native';
import { Page } from '../../../../layouts/Page';
import { Spinner } from '../../../../components/Spinner';
import React from 'react';
import { useGetCategories } from '../../../../api/category/useGetCategories';
import { CategoryEntry } from './CategoryEntry';
import { Button } from '../../../../components/Button';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import {
  CategoriesAndParentItemsStackParamsList,
} from '../../../../navigation/navigationTypes';

export const CategoriesPage = () => {
  const { navigate } = useNavigation<NavigationProp<CategoriesAndParentItemsStackParamsList>>()

  const categoriesItemsQuery = useGetCategories()

  if (categoriesItemsQuery.isPending) {
    return <Page safeArea={false} style={{ paddingTop: 128 }}>
      <Spinner />
    </Page>
  }

  if (!categoriesItemsQuery.isSuccess) {
    return null
  }

  return <Page safeArea={false}>
    <FlatList
      data={categoriesItemsQuery.data.categories}
      extraData={categoriesItemsQuery.dataUpdatedAt}
      contentContainerStyle={styles.container}
      renderItem={({ item }) => (
        <CategoryEntry category={item} />
      )}
    />
    <View style={styles.button}>
      <Button
        title="Utwórz kategorię"
        onPress={() => {
          navigate("ADD_CATEGORY")
        }}
      />
    </View>
  </Page>
}

const styles = StyleSheet.create({
  container: {
    padding: 16,
  },
  button: {
    padding: 16,
  }
})