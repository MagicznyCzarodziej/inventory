import React from 'react';
import { Text, View, StyleSheet, Pressable } from 'react-native';
import { Entry } from '../../../api/item/useGetItems';
import { isParentEntry } from '../utils/itemsUtils';
import {  Icon } from 'react-native-paper';
import Theme, { Colors } from '../../../app/Theme';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { InventoryStackParamList } from '../../../navigation/navigationTypes';

interface Props {
  entry: Entry;
  searchPhrase: string;
}

export const ListEntry = (props: Props) => {
  const { entry, searchPhrase } = props;

  const isParentItem = isParentEntry(entry)

  return <>
    <View style={styles.entry}>
      <View style={styles.row}>
        <Text style={styles.itemName}>{entry.name}</Text>
        <View>
          {isParentItem
            ? <AddSubItemButton itemId={entry.id} itemName={entry.name} searchPhrase={searchPhrase} />
            : <SeeItemButton itemId={entry.id} />}
        </View>
      </View>
      {isParentItem && entry.items.map(item => (
        <Text
          key={item.id}
          style={styles.subItemName}
        >
          {item.name}
        </Text>)
      )}
    </View>
  </>
}

interface AddSubItemButtonProps {
  itemId: string;
  itemName: string;
  searchPhrase: string;
}

const AddSubItemButton = (props: AddSubItemButtonProps) => {
  const { itemId, itemName, searchPhrase } = props;
  const { navigate } = useNavigation<NavigationProp<InventoryStackParamList>>()

  return <Pressable
    style={styles.button}
    onPress={() => {
      navigate({
        name: "ADD_ITEM",
        params: { parent: { parentId: itemId, parentName: itemName }, nameDraft: searchPhrase },
        merge: true
      })
    }}
  >
    <Text style={styles.buttonText}>Dodaj podprodukt</Text>
    <View style={{ marginTop: 3, marginRight: 3 }}>
      <Icon size={15} source="arrow-down-right" color={Theme.colors.text.gray} />
    </View>
  </Pressable>
}

interface SeeItemProps {
  itemId: string;
}

const SeeItemButton = (props: SeeItemProps) => {
  const { itemId } = props;
  const { navigate } = useNavigation<NavigationProp<InventoryStackParamList>>()

  return <Pressable
    style={styles.button}
    onPress={() => {
      navigate("ITEM", { itemId })
    }}
  >
    <Text style={styles.buttonText}>Zobacz produkt</Text>
    <Icon size={20} source="chevron-right" color={Theme.colors.text.gray} />
  </Pressable>
}

const styles = StyleSheet.create({
  entry: {
    padding: 16,
    marginTop: 8,
    backgroundColor: Colors.secondary,
  },
  row: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'baseline'
  },
  subItemName: {
    fontSize: 18,
    // marginLeft: 8,
    marginTop: 4,
    color: Colors.text.main,
  },
  itemName: {
    color: Colors.text.main,
    fontSize: 18,
    fontWeight:"bold"
  },
  button: {
    display: 'flex',
    flexDirection: 'row',
    gap: 6,
  },
  buttonText: {
    color: Colors.text.gray
  }
})