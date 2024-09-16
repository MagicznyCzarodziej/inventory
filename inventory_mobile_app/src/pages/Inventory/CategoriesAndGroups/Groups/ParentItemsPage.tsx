import React from 'react';
import { SectionList, StyleSheet, Text, View } from 'react-native';
import { Page } from '../../../../layouts/Page';
import { useGetParentItems } from '../../../../api/parentItem/useGetParentItems';
import { Spinner } from '../../../../components/Spinner';
import { ParentItemEntry } from './ParentItemEntry';
import { ParentItem } from '../../../../api/common';
import { Colors } from '../../../../app/Theme';

export const ParentItemsPage = () => {
  const parentItemsQuery = useGetParentItems()

  if (parentItemsQuery.isPending) {
    return <Page safeArea={false} style={{ paddingTop: 128 }}>
      <Spinner />
    </Page>
  }

  if (!parentItemsQuery.isSuccess) {
    return null
  }

  // Group parent items by category
  const data = Object.entries(parentItemsQuery.data.parentItems.reduce(
    (categoriesToParentItems, currentValue) => {
      const category = currentValue.category.name
      if (!categoriesToParentItems[category]) {
        categoriesToParentItems[category] = []
      }
      categoriesToParentItems[category].push(currentValue)
      return categoriesToParentItems
    },
    {} as Record<string, ParentItem[]>
  )).map(([categoryName, parentItems]) => ({
    title: categoryName,
    data: parentItems
  }))

  return <Page safeArea={false}>
    <SectionList
      sections={data}
      extraData={parentItemsQuery.dataUpdatedAt}
      contentContainerStyle={styles.container}
      renderSectionHeader={(list) => (
        <View style={styles.sectionHeader}>
          <Text key={list.section.title} style={styles.categoryName}>{list.section.title}</Text>
          <View style={styles.divider} />
        </View>
      )}
      renderItem={({ item }) => (
        <ParentItemEntry parentItem={item} key={item.id} />
      )}
      renderSectionFooter={() => <View style={styles.sectionFooter} />}
    />
  </Page>
}

const styles = StyleSheet.create({
  container: {
    padding: 16,
    paddingTop: 8,
  },
  divider: {
    height: 1,
    flexGrow: 1,
    marginTop: 3,
    backgroundColor: "lightgray",
  },
  sectionHeader: {
    display: "flex",
    flexDirection: "row",
    alignItems: "center",
    gap: 20,
    paddingTop: 16,
    paddingBottom: 8,
    paddingLeft: 8,
    paddingRight: 8,
  },
  sectionFooter: {
    marginTop: 16
  },
  categoryName: {
    fontSize: 20,
    color: Colors.text.gray,
  }
})