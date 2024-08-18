import { RefreshControl, SectionList, View } from 'react-native';
import { ItemEntry, useGetItems } from '../../../api/useGetItems';
import { useEffect, useState } from 'react';
import { flattenParentEntries, sortEntries } from './inventoryListUtils';
import { ItemListEntry } from './ItemListEntry';
import { IconButton, TextInput, } from 'react-native-paper';
import { Colors } from '../../../app/Theme';
import { Page } from '../../../layouts/Page';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { InventoryStackParamList } from '../../../navigation/navigationTypes';

export const InventoryPage = () => {
  const { navigate } = useNavigation<NavigationProp<InventoryStackParamList>>()

  const [isManuallyRefreshing, setIsManuallyRefreshing] = useState(false)
  const [searchPhrase, setSearchPhrase] = useState("")

  const [sortedEntries, setSortedEntries] = useState<ItemEntry[]>([])

  const getItemsQuery = useGetItems(searchPhrase)

  useEffect(() => {
    if (!getItemsQuery.isRefetching) {
      setIsManuallyRefreshing(false);
    }

    if (getItemsQuery.isSuccess) {
      setSortedEntries(
        getItemsQuery.data.entries
          .flatMap(flattenParentEntries)
          .sort(sortEntries)
      )
    }
  }, [getItemsQuery.data, getItemsQuery.isRefetching]);

  const refreshList = () => {
    getItemsQuery.refetch().then()
  }

  const handleSearch = (value: string) => {
    setSearchPhrase(value)
  }

  const Search = <View
    style={{
      display: "flex",
      flexDirection: "row",
      alignItems: "center",
      backgroundColor: Colors.background,
      paddingLeft: 32,
      paddingRight: 8,
      paddingBottom: 8
    }}
  >
    <TextInput
      style={{
        marginTop: 0,
        flexGrow: 1,
      }}
      label="Szukaj w produktach"
      outlineColor={Colors.input.outline}
      activeOutlineColor={Colors.secondary}
      mode="outlined"
      onChangeText={handleSearch}
      right={<TextInput.Icon icon="text-search" />}
    />
    <IconButton icon="plus" onPress={() => {
      navigate("INVENTORY_MANAGER")
    }} />
  </View>

  return <Page>
    <SectionList
      sections={[{
        data: sortedEntries
      }]}
      extraData={getItemsQuery.dataUpdatedAt} // Remember that ListItemEntry has memo with custom `areEqual`
      renderSectionHeader={() => Search}
      renderItem={({ item }) => {
        return <ItemListEntry entry={item} key={item.id} />
      }}
      refreshControl={
        <RefreshControl
          refreshing={isManuallyRefreshing}
          onRefresh={() => {
            setIsManuallyRefreshing(true)
            refreshList()
          }}
        />}
    />
  </Page>
}
