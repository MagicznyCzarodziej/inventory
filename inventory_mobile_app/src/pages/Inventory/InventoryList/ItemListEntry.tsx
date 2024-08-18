import React from 'react';
import { ItemEntry } from '../../../api/useGetItems';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { getColor } from './inventoryListUtils';
import { Divider, IconButton } from 'react-native-paper';
import { useUpdateCurrentStock } from '../../../api/useUpdateCurrentStock';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { Colors } from '../../../app/Theme';
import { InventoryStackParamList } from '../../../navigation/navigationTypes';

interface Props {
  entry: ItemEntry;
}

const arePropsEqual = (prev: Props, next: Props) =>
  prev.entry.currentStock === next.entry.currentStock &&
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
          <Text style={styles.desiredStock}>/{entry.desiredStock}</Text>
        </View>
        <Text style={styles.name}>{entry.name}</Text>
        <View style={styles.controls}>
          {entry.currentStock > 0 && <IconButton
            icon="minus"
            style={styles.controlButton}
            iconColor={Colors.accent}
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
            iconColor={Colors.accent}
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
      <Divider horizontalInset />
    </View>
  </Pressable>
}, arePropsEqual)

const styles = StyleSheet.create({
  itemEntry: {
    marginVertical: 8,
    paddingLeft: 16,
    flexDirection: "row",
    alignItems: "baseline",
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
    fontSize: 18
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