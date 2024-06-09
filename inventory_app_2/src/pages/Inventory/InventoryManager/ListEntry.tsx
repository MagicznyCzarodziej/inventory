import React, { PropsWithChildren } from 'react';
import { Text, View, StyleSheet, Pressable } from 'react-native';
import { Entry } from '../../../api/useGetItems';
import { isParentEntry } from '../itemsUtils';
import { Divider, Icon } from 'react-native-paper';
import Theme from '../../../app/Theme';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { InventoryStackParamList } from '../InventoryList/InventoryNavigation';

interface Props {
  entry: Entry;
  searchPhrase: string;
}

export const ListEntry = (props: Props) => {
  const { entry, searchPhrase } = props;

  const isParentItem = isParentEntry(entry)

  return <>
    <Divider />
    <View style={styles.entry}>
      <View style={styles.row}>
        <Text style={styles.itemName}>{entry.name}</Text>
        <View>
          {isParentItem
            ? <AddSubItemButton itemId={entry.id} searchPhrase={searchPhrase} />
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
  searchPhrase: string;
}

const AddSubItemButton = (props: AddSubItemButtonProps) => {
  const { itemId, searchPhrase } = props;
  const { navigate } = useNavigation<NavigationProp<InventoryStackParamList>>()

  return <Pressable
    style={styles.button}
    onPress={() => {
      navigate("ADD_ITEM", { parentItemId: itemId, name: searchPhrase })
    }}
  >
    <Text style={styles.buttonText}>Dodaj podprodukt</Text>
    <View style={{ marginTop: 3, marginRight: 3 }}>
      <Icon size={15} source="arrow-down-right" color={Theme.colors.accent} />
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
    <Icon size={20} source="chevron-right" color={Theme.colors.accent} />
  </Pressable>
}

const styles = StyleSheet.create({
  entry: {
    paddingVertical: 16,
  },
  row: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'baseline'
  },
  parentItemName: {
    fontSize: 18
  },
  subItemName: {
    fontSize: 16,
    marginLeft: 8,
    marginTop: 4,
  },
  itemName: {
    fontSize: 18
  },
  button: {
    display: 'flex',
    flexDirection: 'row',
    gap: 6,
  },
  buttonText: {
    color: Theme.colors.accent
  }
})