import React, { useState } from 'react';
import { Page } from '../../../../layouts/Page';
import { Button } from '../../../../components/Button';
import { StyleSheet, View } from 'react-native';
import { TextField } from '../../../../components/TextInput';
import { Select } from '../../../../components/Select';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import {
  CategoriesAndParentItemsStackParamsList, CategoriesAndParentItemsTabsParamsList,
} from '../../../../navigation/navigationTypes';
import { CompositeScreenProps } from '@react-navigation/native';
import { useCreateCategory } from '../../../../api/category/useCreateCategory';

type Props = CompositeScreenProps<
  NativeStackScreenProps<CategoriesAndParentItemsStackParamsList, "ADD_CATEGORY">,
  NativeStackScreenProps<CategoriesAndParentItemsTabsParamsList, "CATEGORIES">
  >

export const CategoryCreatorPage = (props: Props) => {
  const { navigate } = props.navigation

  const [isFormDirty, setIsFormDirty] = useState(false);
  const [name, setName] = useState<string>("");

  const createCategoryMutation = useCreateCategory()

  const isValid = name.trim().length > 0

  const handleCreateCategory = async () => {
    if (!isValid) {
      return
    }

    await createCategoryMutation.mutateAsync({
      name,
    })

    navigate("CATEGORIES")
  }

  return (
    <Page style={styles.page}>
      <TextField
        label="Nazwa"
        value={name}
        onChangeText={(value) => {
          setName(value)
          setIsFormDirty(true)
        }}
        error={isFormDirty && name.trim().length < 1 ? "Nazwa nie może być pusta" : undefined}
      />

      <View style={styles.saveButton}>
        <Button
          onPress={() => {
            handleCreateCategory()
          }}
          title="Zapisz"
          disabled={!isValid}
          spinner={createCategoryMutation.isPending}
        />
      </View>
    </Page>
  );
}

const styles = StyleSheet.create({
  page: {
    padding: 16,
  },
  saveButton: {
    marginTop: "auto"
  },
})