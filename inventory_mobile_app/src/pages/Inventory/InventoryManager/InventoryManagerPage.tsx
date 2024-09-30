import { FlatList, View } from 'react-native';
import { Entry, useGetItems } from '../../../api/item/useGetItems';
import { Page } from '../../../layouts/Page';
import { Spinner } from '../../../components/Spinner';
import { Divider, TextInput } from 'react-native-paper';
import Theme, { Colors } from '../../../app/Theme';
import { Button } from '../../../components/Button';
import { ListEntry } from './ListEntry';
import { useState } from 'react';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { InventoryStackParamList } from '../../../navigation/navigationTypes';

export const InventoryManagerPage = () => {
  const [searchPhrase, setSearchPhrase] = useState("")
  const itemsQuery = useGetItems(searchPhrase)

  const { navigate } = useNavigation<NavigationProp<InventoryStackParamList>>()

  if (itemsQuery.isLoading) {
    return <Page safeArea={false} style={{ paddingTop: 128 }}>
      <Spinner />
    </Page>
  }

  if (!itemsQuery.isSuccess) {
    return null
  }

  const sortedItems = sortEntries(itemsQuery.data.entries)

  return <Page safeArea={false} style={{
    backgroundColor: Colors.secondary
  }}>
    <TextInput
      style={{
        marginHorizontal: 16,
        backgroundColor: Colors.background,
      }}
      outlineStyle={{
        borderRadius: Theme.shapes.inputRadius,
      }}
      textColor={Colors.white}
      placeholderTextColor={Colors.white}
      label="Co chcesz dodać?"
      outlineColor={Colors.gray.light}
      activeOutlineColor={Colors.primary}
      theme={{
        colors: {
          onSurfaceVariant: Colors.text.gray
        }
      }}
      mode="outlined"
      onChangeText={(value) => {
        setSearchPhrase(value)
      }}
    />
    <View style={{
      display: "flex",
      flexDirection: "row",
      gap: 16,
      marginTop: 16,
      marginBottom: 16,
      paddingHorizontal: 16,
      backgroundColor: Colors.secondary
    }}>
      <Button
        small
        fullWidth
        onPress={() => {
          navigate({
            name: "ADD_ITEM",
            params: { nameDraft: searchPhrase },
            merge: true
          })
        }}
        title="Nowy produkt"
      />
      <Button
        small
        fullWidth
        onPress={() => {
          navigate({
            name: "ADD_PARENT_ITEM",
            params: { nameDraft: searchPhrase },
            merge: true
          })
        }}
        title="Nowa grupa"
      />
    </View>
    <Divider />
    <FlatList<Entry>
      style={{
        flexBasis: 0, // fix for hiding under menu,
        backgroundColor: Colors.background
      }}
      data={sortedItems}
      renderItem={({ item }) => <ListEntry key={item.id} entry={item} searchPhrase={searchPhrase} />}
    />
  </Page>
}

const sortEntries = (entries: Entry[]) => {
  return entries
    .slice(0) // Clone array, because sort mutates it
    .sort((a, b) => a.name.localeCompare(b.name, "pl-PL"))
}