import { FlatList, Text, View } from 'react-native';
import { Entry, useGetItems } from '../../../api/useGetItems';
import { Page } from '../../../layouts/Page';
import { Spinner } from '../../../components/Spinner';
import { TextInput } from 'react-native-paper';
import { Colors } from '../../../app/Theme';
import { Button } from '../../../components/Button';
import { ListEntry } from './ListEntry';
import { useState } from 'react';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { InventoryStackParamList } from '../InventoryList/InventoryNavigation';

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

  return <Page>
    <TextInput
      style={{
        marginHorizontal: 16
      }}
      label="Co chcesz utworzyć?"
      outlineColor={Colors.input.outline}
      activeOutlineColor={Colors.secondary}
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
      paddingHorizontal: 16,
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
        title="Utwórz produkt"
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
        title="Utwórz grupę"
      />
    </View>

    <FlatList<Entry>
      style={{
        marginTop: 16,
        paddingHorizontal: 16,
        flexBasis: 0, // fix for hiding under menu
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