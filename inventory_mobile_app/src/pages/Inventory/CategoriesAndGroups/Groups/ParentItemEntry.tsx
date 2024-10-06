import { ParentItem } from '../../../../api/common';
import { View, Text, StyleSheet } from 'react-native';
import { Colors } from '../../../../app/Theme';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { CategoriesAndParentItemsStackParamsList } from '../../../../navigation/navigationTypes';

interface Props {
  parentItem: ParentItem;
}

export const ParentItemEntry = (props: Props) => {
  const { parentItem } = props;

  const { navigate } = useNavigation<NavigationProp<CategoriesAndParentItemsStackParamsList>>()

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
    paddingTop: 8,
    paddingLeft: 16,
    paddingRight: 16,
    paddingBottom: 16,
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    backgroundColor: Colors.secondary,
  },
  name: {
    fontSize: 18,
    color: Colors.text.main
  },
  edit: {
    fontSize: 14,
    color: Colors.text.gray,
  }
})