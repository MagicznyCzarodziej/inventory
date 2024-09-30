import { Category } from '../../../../api/common';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { CategoriesAndParentItemsStackParamsList } from '../../../../navigation/navigationTypes';
import { StyleSheet, Text, View } from 'react-native';
import { Colors } from '../../../../app/Theme';

interface Props {
  category: Category
}

export const CategoryEntry = (props: Props) => {
  const { category } = props;

  const { navigate } = useNavigation<NavigationProp<CategoriesAndParentItemsStackParamsList>>()

  return <View style={styles.entry}>
    <Text style={styles.name}>{category.name}</Text>
    <Text
      style={styles.edit}
      onPress={() => {
        navigate("EDIT_CATEGORY", { categoryId: category.id })
      }}
    >
      edytuj
    </Text>
  </View>
}

const styles = StyleSheet.create({
  entry: {
    marginTop: 8,
    padding: 16,
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems:'baseline',
    backgroundColor: Colors.secondary
  },
  name: {
    fontSize: 18,
    color: Colors.text.main
  },
  edit: {
    fontSize: 14,
    color: Colors.text.gray
  }
})