import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { CategoriesAndParentItemsTabsParamsList } from '../../../navigation/navigationTypes';
import React, { useState } from 'react';
import { useNavigation } from '@react-navigation/native';
import { StyleSheet, Text, View } from 'react-native';
import { Button } from '../../../components/Button';
import { Page } from '../../../layouts/Page';
import { TextField } from '../../../components/TextInput';
import { GetCategoryResponse } from '../../../api/category/useGetCategory';
import { useRemoveCategory } from '../../../api/category/useRemoveCategory';
import { useEditCategory } from '../../../api/category/useEditCategory';
import { Colors } from '../../../app/Theme';

interface Props {
  category: GetCategoryResponse;
}

type Navigation = NativeStackNavigationProp<CategoriesAndParentItemsTabsParamsList, 'CATEGORIES'>

export const CategoryEditor = (props: Props) => {
  const { category } = props;

  const { navigate } = useNavigation<Navigation>()

  const [isFormDirty, setIsFormDirty] = useState(false);
  const [name, setName] = useState<string>(category.name)

  const hasItems = category.itemsCount > 0

  const editCategoryMutation = useEditCategory()
  const removeCategoryMutation = useRemoveCategory()

  const isValid = name.trim().length > 0

  const handleRemove = async () => {
    await removeCategoryMutation.mutateAsync(category.id)
    navigate("CATEGORIES")
  }

  const handleSave = async () => {
    if (!isValid) {
      return
    }

    await editCategoryMutation.mutateAsync({
      categoryId: category.id,
      request: {
        name,
      }
    })

    navigate("CATEGORIES")
  }

  return <Page safeArea={false} style={styles.page}>
    <TextField
      label="Nazwa"
      value={name}
      onChangeText={(value) => {
        setName(value)
        setIsFormDirty(true)
      }}
      error={isFormDirty && name.trim().length < 1 ? "Nazwa nie może być pusta" : undefined}
    />

    {hasItems
      ? <Text style={styles.hint}>
        {`Kategoria zawiera ${category.itemsCount} ${category.itemsCount === 1
          ? "produkt"
          : category.itemsCount < 5
            ? "produkty"
            : "produktów"
        }`}
      </Text>
      : <Text style={styles.hint}>
        Kategoria nie zawiera żadnych produktów
      </Text>
    }

    <View style={styles.controls}>
      <Button
        title="Usuń"
        onPress={() => {
          handleRemove()
        }}
        fullWidth
        disabled={hasItems}
        spinner={removeCategoryMutation.isPending}
      />
      <Button
        title="Zapisz"
        onPress={() => {
          handleSave()
        }}
        disabled={!isValid}
        spinner={editCategoryMutation.isPending}
        fullWidth
      />
    </View>
  </Page>
}

const styles = StyleSheet.create({
  page: {
    padding: 16,
  },
  controls: {
    display: 'flex',
    flexDirection: 'row',
    gap: 16,
    marginTop: "auto"
  },
  hint: {
    color: Colors.text.main
  }
})