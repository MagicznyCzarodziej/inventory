import React from 'react';
import { ItemEntry } from '../../../api/item/useGetItems';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { getColor } from './inventoryListUtils';
import { IconButton } from 'react-native-paper';
import { useUpdateCurrentStock } from '../../../api/item/useUpdateCurrentStock';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { Colors } from '../../../app/Theme';
import { InventoryStackParamList } from '../../../navigation/navigationTypes';

interface Props {
  entry: ItemEntry;
}

const arePropsEqual = (prev: Props, next: Props) =>
  prev.entry.currentStock === next.entry.currentStock &&
  prev.entry.desiredStock === next.entry.desiredStock &&
  prev.entry.name === next.entry.name

export const ItemListEntry = React.memo((props: Props) => {
  const { entry } = props

  const { navigate } = useNavigation<NavigationProp<InventoryStackParamList>>()
  const updateCurrentStockMutation = useUpdateCurrentStock();

  return <Pressable
    onPress={() => {
      navigate("ITEM", { itemId: entry.id })
    }}
  >
    <View>
      <View style={{ ...styles.itemEntry, borderLeftColor: getColor(entry), borderLeftWidth: 8 }}>
        <View style={styles.stockContainer}>
          <Text style={{ ...styles.currentStock, color: getColor(entry) }}>{entry.currentStock}</Text>
          <Text style={styles.desiredStock}> / {entry.desiredStock}</Text>
        </View>
        <Text style={styles.name}>{entry.name}</Text>
        <View style={styles.controls}>
          {entry.currentStock > 0 && <IconButton
            icon="minus"
            style={styles.controlButton}
            iconColor={Colors.gray.light}
            onPress={() => {
              updateCurrentStockMutation.mutate({
                itemId: entry.id,
                stockChange: -1
              })
            }}
            size={24}
          />}
          <IconButton
            icon="plus"
            style={styles.controlButton}
            iconColor={Colors.gray.light}
            onPress={() => {
              updateCurrentStockMutation.mutate({
                itemId: entry.id,
                stockChange: 1
              })
            }}
            size={24}
          />
        </View>
      </View>
    </View>
  </Pressable>
}, arePropsEqual)

const styles = StyleSheet.create({
  itemEntry: {
    marginTop: 8,
    paddingLeft: 16,
    flexDirection: "row",
    alignItems: "baseline",
    backgroundColor: Colors.secondary,
  },
  stockContainer: {
    paddingVertical: 16,
    flexDirection: "row",
    alignItems: "baseline",
    width: 60,
  },
  currentStock: {
    fontSize: 24
  },
  desiredStock: {
    fontSize: 16,
    color: Colors.gray.light,
  },
  name: {
    fontSize: 18,
    color: Colors.text.main,
  },
  controls: {
    flexDirection: "row",
    alignItems: "center",
    marginLeft: "auto",
  },
  controlButton: {
    borderRadius: 8,
    marginVertical: 0
  },
})