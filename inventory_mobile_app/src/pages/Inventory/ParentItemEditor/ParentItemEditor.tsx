import React, { useState } from 'react';
import { TextField } from '../../../components/TextInput';
import { Category, ParentItem } from '../../../api/common';
import { StyleSheet, View } from 'react-native';
import { Page } from '../../../layouts/Page';
import { Select } from '../../../components/Select';
import { mapCategoriesToCategorySelectItems } from '../utils/categoryUtils';
import { Button } from '../../../components/Button';
import { useEditParentItem } from '../../../api/useEditParentItem';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { CategoriesAndGroupsParamsList } from '../CategoriesAndGroups/CategoriesAndGroupsTabNavigation';

interface Props {
  parentItem: ParentItem;
  categories: Category[]
}

type Navigation = NativeStackNavigationProp<CategoriesAndGroupsParamsList, 'GROUPS'>

export const ParentItemEditor = (props: Props) => {
  const { parentItem, categories } = props;

  const { navigate } = useNavigation<Navigation>()

  const [isFormDirty, setIsFormDirty] = useState(false);
  const [name, setName] = useState<string>(parentItem.name)
  const [categoryId, setCategoryId] = useState<string>(parentItem.category.id)

  const categorySelectItems = mapCategoriesToCategorySelectItems(categories)

  const editParentItemMutations = useEditParentItem()

  const isValid = name.trim().length > 0

  const handleSave = async () => {
    if (!isValid) {
      return
    }

    await editParentItemMutations.mutateAsync({
      parentItemId: parentItem.id,
      request: {
        name,
        categoryId,
      }
    })

    navigate("GROUPS")
  }

  return <Page style={styles.page}>
    <TextField
      label="Nazwa"
      value={name}
      onChangeText={(value) => {
        setName(value)
        setIsFormDirty(true)
      }}
      error={isFormDirty && name.trim().length < 1 ? "Nazwa nie może być pusta" : undefined}
    />

    <Select value={categoryId ?? undefined} onValueChange={setCategoryId} items={categorySelectItems} />

    <View style={styles.saveButton}>
      <Button
        onPress={() => {
          handleSave()
        }}
        title="Zapisz"
        disabled={!isValid}
        spinner={editParentItemMutations.isPending}
      />
    </View>
  </Page>
}

const styles = StyleSheet.create({
  page: {
    padding: 16,
  },
  saveButton: {
    marginTop: "auto"
  }
})