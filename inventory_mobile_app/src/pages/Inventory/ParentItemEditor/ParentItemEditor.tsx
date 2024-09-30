import React, { useState } from 'react';
import { TextField } from '../../../components/TextInput';
import { Category } from '../../../api/common';
import { StyleSheet, Text, View } from 'react-native';
import { Page } from '../../../layouts/Page';
import { Select } from '../../../components/Select';
import { mapCategoriesToCategorySelectItems } from '../utils/categoryUtils';
import { Button } from '../../../components/Button';
import { useEditParentItem } from '../../../api/parentItem/useEditParentItem';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { useRemoveParentItem } from '../../../api/parentItem/useRemoveParentItem';
import { GetParentItemResponse } from '../../../api/parentItem/useGetParentItem';
import { CategoriesAndParentItemsTabsParamsList } from '../../../navigation/navigationTypes';
import { Colors } from '../../../app/Theme';

interface Props {
  parentItem: GetParentItemResponse;
  categories: Category[]
}

type Navigation = NativeStackNavigationProp<CategoriesAndParentItemsTabsParamsList, 'PARENT_ITEMS_LIST'>

export const ParentItemEditor = (props: Props) => {
  const { parentItem, categories } = props;

  const { navigate } = useNavigation<Navigation>()

  const [isFormDirty, setIsFormDirty] = useState(false);
  const [name, setName] = useState<string>(parentItem.name)
  const [categoryId, setCategoryId] = useState<string>(parentItem.category.id)

  const hasSubItems = parentItem.subItemsCount > 0

  const categorySelectItems = mapCategoriesToCategorySelectItems(categories)

  const editParentItemMutations = useEditParentItem()
  const removeParentItemMutations = useRemoveParentItem()

  const isValid = name.trim().length > 0

  const handleRemove = async () => {
    await removeParentItemMutations.mutateAsync(parentItem.id)
    navigate("PARENT_ITEMS_LIST")
  }

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

    navigate("PARENT_ITEMS_LIST")
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

    <Select value={categoryId ?? undefined} onValueChange={setCategoryId} items={categorySelectItems} />

    {hasSubItems
      ? <Text style={styles.hint}>
        {`Grupa zawiera ${parentItem.subItemsCount} ${parentItem.subItemsCount === 1
          ? "produkt"
          : parentItem.subItemsCount < 5
            ? "produkty"
            : "produktów"
        }`}
      </Text>
      : <Text style={styles.hint}>
        Grupa nie zawiera żadnych produktów
      </Text>
    }

    <View style={styles.controls}>
      <Button
        onPress={() => {
          handleRemove()
        }}
        title="Usuń"
        spinner={removeParentItemMutations.isPending}
        fullWidth
        disabled={hasSubItems}
      />
      <Button
        onPress={() => {
          handleSave()
        }}
        title="Zapisz"
        disabled={!isValid}
        spinner={editParentItemMutations.isPending}
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
    color: Colors.text.main,
  }
})