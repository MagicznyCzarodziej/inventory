import { FlatList } from 'react-native';
import { Page } from '../../../../layouts/Page';
import { useGetParentItems } from '../../../../api/useGetParentItems';
import { Spinner } from '../../../../components/Spinner';
import { ParentItemEntry } from './ParentItemEntry';

export const GroupsPage = () => {
  const parentItemsQuery = useGetParentItems()

  if (parentItemsQuery.isPending) {
    return <Page safeArea={false} style={{ paddingTop: 128 }}>
      <Spinner />
    </Page>
  }

  if (!parentItemsQuery.isSuccess) {
    return null
  }

  return <Page safeArea={false}>
    <FlatList
      data={parentItemsQuery.data.parentItems}
      renderItem={({ item }) => {
        return <ParentItemEntry parentItem={item} key={item.id} />
      }}
    />
  </Page>
}