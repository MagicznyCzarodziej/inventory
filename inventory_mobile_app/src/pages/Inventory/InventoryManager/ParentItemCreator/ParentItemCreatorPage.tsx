import React, { useState } from 'react';
import { Page } from '../../../../layouts/Page';
import { Button } from '../../../../components/Button';
import { StyleSheet, View } from 'react-native';
import { TextField } from '../../../../components/TextInput';
import { Select } from '../../../../components/Select';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { useCreateParentItem } from '../../../../api/useCreateParentItem';
import { useCategorySelect } from '../../utils/categoryUtils';
import { InventoryStackParamList } from '../../../../navigation/navigationTypes';

type Props = NativeStackScreenProps<InventoryStackParamList, 'ADD_PARENT_ITEM'>

export const ParentItemCreatorPage = (props: Props) => {
  const { nameDraft } = props.route.params;
  const { navigate } = props.navigation

  const [isFormDirty, setIsFormDirty] = useState(false);
  const [name, setName] = useState<string>(nameDraft ?? "");

  const { categoryId, setCategoryId, categorySelectItems } = useCategorySelect()

  const createParentItemMutation = useCreateParentItem()

  const isValid = name.trim().length > 0 && categoryId !== undefined;

  const handleCreateParentItem = async () => {
    if (!isValid) {
      return
    }

    await createParentItemMutation.mutateAsync({
      name,
      categoryId
    })

    navigate("INVENTORY_MANAGER")
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

      <Select value={categoryId} onValueChange={setCategoryId} items={categorySelectItems} />

      <View style={styles.saveButton}>
        <Button
          onPress={() => {
            handleCreateParentItem()
          }}
          title="Zapisz"
          disabled={!isValid}
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