import { ParentItem } from '../../../../api/useGetParentItems';
import { View, Text } from 'react-native';

interface Props {
  parentItem: ParentItem;
}

export const ParentItemEntry = (props: Props) => {
  const { parentItem } = props;

  return <View>
    <Text>{parentItem.name}</Text>
  </View>
}