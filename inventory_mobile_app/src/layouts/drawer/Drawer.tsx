import { CompositeNavigationProp, NavigationProp, useNavigation } from '@react-navigation/native';
import { Text } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { Drawer as PaperDrawer } from 'react-native-paper';
import { InventoryStackParamList, InventoryTabsParamList, RootStackParamList } from '../../navigation/navigationTypes';

interface Props {
  closeDrawer: () => void
}

type Navigation =
  CompositeNavigationProp<
    CompositeNavigationProp<
      NavigationProp<RootStackParamList, 'INVENTORY'>,
      NavigationProp<InventoryTabsParamList, 'INVENTORY_NAVIGATION'>
    >,
    NavigationProp<InventoryStackParamList, 'INVENTORY_LIST'>
  >

export const Drawer = (props: Props) => {
  const { closeDrawer } = props;
  const { navigate } = useNavigation<Navigation>()

  return <SafeAreaView>
    <Text
      style={{
        paddingHorizontal: 24,
        marginBottom: 32,
        fontSize: 32,
        fontWeight: "bold"
      }}>
      Inventory
    </Text>
    <PaperDrawer.Section>
      <PaperDrawer.Item
        label="Konto"
        icon="account"
        onPress={() => {
          navigate("ACCOUNT")
          closeDrawer()
        }}
      />
      <PaperDrawer.Item
        label="Ustawienia"
        icon="cog"
        onPress={() => {
          navigate("SETTINGS")
          closeDrawer()
        }}
      />
    </PaperDrawer.Section>
    <PaperDrawer.Section
      showDivider={false}>
      <PaperDrawer.Item
        label="Inwentarz"
        icon="clipboard-list-outline"
        onPress={() => {
          navigate("INVENTORY_LIST")
          closeDrawer()
        }}
      />
      <PaperDrawer.Item
        label="Lista zakupów"
        icon="basket-outline"
        onPress={() => {
          navigate("SHOPPING_LIST")
          closeDrawer()
        }}
      />
      <PaperDrawer.Item
        label="Zmywaki"
        icon="mirror-rectangle"
        onPress={() => {
          navigate("SPONGES")
          closeDrawer()
        }}
      />
    </PaperDrawer.Section>
  </SafeAreaView>
}
