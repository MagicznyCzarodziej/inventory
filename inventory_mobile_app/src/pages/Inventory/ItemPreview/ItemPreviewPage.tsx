import { Pressable, StyleSheet, Text, View } from 'react-native';
import { useGetItem } from '../../../api/item/useGetItem';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { IconButton } from 'react-native-paper';
import { Colors } from '../../../app/Theme';
import { Spinner } from '../../../components/Spinner';
import { Page } from '../../../layouts/Page';
import { ItemEntry } from '../../../api/item/useGetItems';
import { ItemPreviewBarcode } from './ItemPreviewBarcode';
import { Button } from "../../../components/Button";
import { useUpdateCurrentStock } from '../../../api/item/useUpdateCurrentStock';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { RemoteItemPhoto } from '../../../components/Photo/RemoteItemPhoto';
import { InventoryStackParamList } from '../../../navigation/navigationTypes';
import { PhotoFullSizePreview } from './PhotoFullSizePreview';
import { useState } from 'react';

type Props = NativeStackScreenProps<InventoryStackParamList, "ITEM">;

export const ItemPreviewPage = (props: Props) => {
  const itemId = props.route.params.itemId
  const [isPhotoPreviewOpen, setIsPhotoPreviewOpen] = useState(false)

  const itemQuery = useGetItem(itemId)

  const { navigate } = useNavigation<NavigationProp<InventoryStackParamList>>()

  const photoUrl = itemQuery.data?.photoUrl ?? null
  const hasPhoto = photoUrl !== null


  const updateCurrentStockMutation = useUpdateCurrentStock();

  if (!itemQuery.isSuccess) {
    return <Page style={{
      paddingTop: 128
    }}>
      <Spinner />
    </Page>
  }

  return <Page safeArea={!hasPhoto} style={styles.page}>
    {(isPhotoPreviewOpen && hasPhoto) && <PhotoFullSizePreview
      photoUri={photoUrl}
      closePreview={() => {
        setIsPhotoPreviewOpen(false)
      }}
    />}
    <Pressable
      onPress={() => {
        setIsPhotoPreviewOpen(true)
      }}
    >
      {hasPhoto && <RemoteItemPhoto photoUrl={photoUrl} />}
    </Pressable>

    <View style={{
      ...styles.card,
      elevation: hasPhoto ? 10 : 0,
    }}>
      <View style={styles.categoryAndEdit}>
        <Text style={styles.category}>{itemQuery.data.category.name}</Text>
        <IconButton
          icon="pencil"
          style={styles.editButton}
          iconColor={Colors.text.gray}
          onPress={() => {
            navigate("EDIT_ITEM", { itemId })
          }}
        />
      </View>

      {itemQuery.data.parentItem &&
        <Text style={styles.parentItemName}>
          {itemQuery.data.parentItem.name}
        </Text>
      }

      <View style={styles.header}>
        <Text
          style={styles.name}>
          {itemQuery.data.name}
        </Text>
        <Text
          style={styles.brand}>
          {itemQuery.data.brand}
        </Text>
      </View>

      <Text
        style={styles.description}>
        {itemQuery.data.description}
      </Text>

      <View style={styles.stock}>
        <Text
          style={{
            ...styles.currentStock,
            color: getColor(itemQuery.data)
          }}
        >
          {itemQuery.data.currentStock}
        </Text>
        <Text style={styles.desiredStock}>
          /{itemQuery.data.desiredStock}
        </Text>
      </View>

      <ItemPreviewBarcode barcode={itemQuery.data.barcode} />
    </View>

    <View style={styles.stockControls}>
      {itemQuery.data.currentStock > 0 &&
        <>
          <IconButton
            icon="minus"
            iconColor={Colors.secondary}
            onPress={() => {
              updateCurrentStockMutation.mutate({
                itemId: itemQuery.data.id,
                stockChange: -1
              })
            }}
          />
          <Button
            title="ZERO"
            variant='text'
            onPress={() => {
              updateCurrentStockMutation.mutate({
                itemId: itemQuery.data.id,
                stockChange: -itemQuery.data.currentStock,
              })
            }}
          />
        </>
      }
      <IconButton
        icon="plus"
        iconColor={Colors.secondary}
        style={styles.incrementStockButton}
        onPress={() => {
          updateCurrentStockMutation.mutate({
            itemId: itemQuery.data.id,
            stockChange: 1
          })
        }}
      />
    </View>
  </Page>
}

export const getColor = (entry: ItemEntry) => {
  if (entry.currentStock < 1 && entry.desiredStock > 0)
    return Colors.status.lightRed
  else if (entry.currentStock < entry.desiredStock)
    return Colors.status.lightOrange
  else
    return Colors.status.lightGreen
}

const styles = StyleSheet.create({
  page: {
    backgroundColor: Colors.secondary,
  },
  card: {
    flexGrow: 1,
    padding: 24,
    backgroundColor: Colors.secondary,
    borderRadius: 0,
    display: "flex",
    flexDirection: "column",
  },
  categoryAndEdit: {
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  editButton: {
    margin: 0,
  },
  category: {
    fontSize: 20,
    color: Colors.text.main,
    paddingBottom: 8,
  },
  header: {
    display: 'flex',
    flexDirection: 'row',
    flexWrap: "wrap",
    gap: 8,
  },
  name: {
    fontSize: 32,
    fontWeight: 'bold',
    color: Colors.text.main,
  },
  brand: {
    fontSize: 32,
    color: Colors.text.main,
  },
  description: {
    marginTop: 8,
    fontSize: 22,
    color: Colors.text.main,
  },
  parentItemName: {
    fontSize: 40,
    color: Colors.text.main,
  },
  stock: {
    flexGrow: 1,
    display: "flex",
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "baseline",
  },
  currentStock: {
    fontSize: 200,
  },
  desiredStock: {
    fontSize: 70,
    color: Colors.text.gray,
  },
  stockControls: {
    backgroundColor: Colors.primary,
    padding: 8,
    display: "flex",
    flexDirection: "row",
    justifyContent: 'flex-end',
    elevation: 20,
    zIndex: 1,
  },
  incrementStockButton: {}
})

