import { ParentItem } from '../../../../api/common';
import { View, Text, StyleSheet } from 'react-native';
import { Colors } from '../../../../app/Theme';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { InventoryStackParamList } from '../../InventoryList/InventoryNavigation';

interface Props {
  parentItem: ParentItem;
}

export const ParentItemEntry = (props: Props) => {
  const { parentItem } = props;

  const { navigate } = useNavigation<NavigationProp<InventoryStackParamList>>()

  return <View style={styles.entry}>
    <Text style={styles.name}>{parentItem.name}</Text>
    <Text
      style={styles.edit}
      onPress={() => {
        navigate("EDIT_PARENT_ITEM", { parentItemId: parentItem.id })
      }}
    >
      edytuj
    </Text>
  </View>
}

const styles = StyleSheet.create({
  entry: {
    padding: 8,
    paddingLeft: 16,
    paddingRight: 16,
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  name: {
    fontSize: 18
  },
  edit: {
    fontSize: 14,
    color: Colors.accent
  }
})