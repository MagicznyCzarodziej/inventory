import { FlatList, RefreshControl, View } from 'react-native';
import { ItemEntry, useGetItems } from '../../../api/item/useGetItems';
import { useEffect, useRef, useState } from 'react';
import { flattenParentEntries, sortEntries } from './inventoryListUtils';
import { ItemListEntry } from './ItemListEntry';
import { IconButton, TextInput, } from 'react-native-paper';
import Theme, { Colors } from '../../../app/Theme';
import { Page } from '../../../layouts/Page';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { InventoryStackParamList } from '../../../navigation/navigationTypes';
import { useGetCategories } from '../../../api/category/useGetCategories';
import { Picker } from '@react-native-picker/picker';

export const InventoryPage = () => {
  const { navigate } = useNavigation<NavigationProp<InventoryStackParamList>>()

  const [isManuallyRefreshing, setIsManuallyRefreshing] = useState(false)
  const [searchPhrase, setSearchPhrase] = useState("")
  const [filterByCategory, setFilterByCategory] = useState<string>("ALL")

  const [sortedEntries, setSortedEntries] = useState<ItemEntry[]>([])
  const [finalEntries, setFinalEntries] = useState<ItemEntry[]>([])

  const getItemsQuery = useGetItems(searchPhrase)
  const getCategoriesQuery = useGetCategories()

  const filterByCategoryRef = useRef<Picker<string>>(null)

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

  useEffect(() => {
    if (filterByCategory === "ALL") {
      setFinalEntries(sortedEntries)
      return
    }

    setFinalEntries(sortedEntries.filter(entry => entry.category.id === filterByCategory))
  }, [sortedEntries, filterByCategory]);

  const refreshList = () => {
    getItemsQuery.refetch().then()
  }

  const handleSearch = (value: string) => {
    setSearchPhrase(value)
  }

  const Search = <>
    <View
      style={{
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
        gap: 16,
        backgroundColor: Colors.secondary,
        paddingTop: 4,
        paddingLeft: 16,
        paddingRight: 16,
        paddingBottom: 16,
        borderBottomColor: Colors.gray.dark,
        borderBottomWidth: 1,
      }}
    >
      <TextInput
        style={{
          marginTop: -5,
          flexGrow: 1,
          backgroundColor: Colors.background,
        }}
        outlineStyle={{
          borderRadius: Theme.shapes.inputRadius,
        }}
        label="Szukaj w produktach"
        outlineColor={Colors.gray.light}
        activeOutlineColor={Colors.primary}
        textColor={Colors.text.main}
        cursorColor={Colors.text.main}
        theme={{
          colors: {
            onSurfaceVariant: Colors.text.gray,
          }
        }}
        mode="outlined"
        value={searchPhrase}
        onChangeText={handleSearch}
        left={<TextInput.Icon
          forceTextInputFocus={false}
          color={filterByCategory === "ALL" ? Colors.gray.light : Colors.primary}
          icon={"shape-outline"}
          onPress={() => {
            filterByCategoryRef.current?.focus()
          }}
        />}
        right={searchPhrase.length > 0 && <TextInput.Icon
          color={Colors.gray.light}
          icon="close"
          onPress={() => {
            handleSearch("")
          }}
        />}
      />
      <IconButton
        size={34}
        style={{
          backgroundColor: Colors.primary,
          borderRadius: 0,
          margin: 0,
        }}
        iconColor={Colors.text.button}
        icon="plus"
        onPress={() => {
          navigate("INVENTORY_MANAGER")
        }}
      />
    </View>
  </>

  return <Page safeArea={false} style={{
    backgroundColor: Colors.secondary
  }}>
    {Search}
    <FlatList
      keyboardShouldPersistTaps="always" // Allows pressing buttons without unfocusing the input
      data={finalEntries}
      style={{
        backgroundColor: Colors.background,
        paddingBottom: 8,
      }}
      extraData={getItemsQuery.dataUpdatedAt} // Remember that ListItemEntry has memo with custom `areEqual`
      renderItem={({ item }) => {
        return <ItemListEntry entry={item} key={item.id} />
      }}
      showsVerticalScrollIndicator={false}
      refreshControl={
        <RefreshControl
          refreshing={isManuallyRefreshing}
          onRefresh={() => {
            setIsManuallyRefreshing(true)
            refreshList()
          }}
        />}
    />
    <Picker
      style={{ display: "none" }}
      selectedValue={filterByCategory} ref={filterByCategoryRef}
      onValueChange={(value) => {
        setFilterByCategory(value)
      }}
    >
      <Picker.Item key={"ALL"} label={"Wszystkie kategorie"} value={"ALL"} />
      {getCategoriesQuery.data?.categories.map(x => (
        <Picker.Item key={x.id} label={x.name} value={x.id} />
      ))}
    </Picker>
  </Page>
}
