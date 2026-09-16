import { NavigationProp, useNavigation } from '@react-navigation/native';
import { Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { Drawer as PaperDrawer } from 'react-native-paper';
import { RootStackParamList } from '../../navigation/navigationTypes';
import { Colors } from '../../app/Theme';
import { useAuth } from '../../context/AuthContext';

interface Props {
  closeDrawer: () => void;
}

export const Drawer = (props: Props) => {
  const { closeDrawer } = props;
  const { navigate } = useNavigation<NavigationProp<RootStackParamList>>();
  const { isAuthenticated, username } = useAuth();

  return <SafeAreaView>
    <Text
      style={{
        paddingHorizontal: 24,
        marginBottom: 16,
        fontSize: 32,
        fontWeight: 'bold',
        color: Colors.text.main,
      }}
    >
      Inventory
    </Text>
    {isAuthenticated && username && (
      <View
        style={{
          paddingHorizontal: 24,
          marginBottom: 16,
          flexDirection: 'row',
          alignItems: 'center',
        }}
      >
        <View
          style={{
            backgroundColor: Colors.background,
            borderRadius: 16,
            width: 32,
            height: 32,
            justifyContent: 'center',
            alignItems: 'center',
            marginRight: 10,
          }}
        >
          <Text style={{ fontSize: 16, color: Colors.primary, fontWeight: 'bold' }}>
            {username.charAt(0).toUpperCase()}
          </Text>
        </View>
        <Text style={{ color: Colors.text.gray, fontSize: 14 }}>
          {username}
        </Text>
      </View>
    )}
    <PaperDrawer.Section>
      <PaperDrawer.Item
        label={isAuthenticated ? 'Konto' : 'Zaloguj'}
        icon="account"
        theme={DRAWER_ITEM_THEME}
        onPress={() => {
          navigate('ACCOUNT');
          closeDrawer();
        }}
      />
      <PaperDrawer.Item
        label="Ustawienia"
        icon="cog"
        theme={DRAWER_ITEM_THEME}
        onPress={() => {
          navigate('SETTINGS');
          closeDrawer();
        }}
      />
    </PaperDrawer.Section>
    <PaperDrawer.Section
      showDivider={false}>
      <PaperDrawer.Item
        label="Inwentarz"
        icon="clipboard-list-outline"
        theme={DRAWER_ITEM_THEME}
        onPress={() => {
          navigate({
            name: 'INVENTORY',
            params: {
              screen: 'INVENTORY_NAVIGATION',
              params: { screen: 'INVENTORY_LIST' },
            },
            merge: true,
          });
          closeDrawer();
        }}
      />
      <PaperDrawer.Item
        label="Lista zakupów"
        icon="basket-outline"
        theme={DRAWER_ITEM_THEME}
        onPress={() => {
          navigate({
            name: 'INVENTORY',
            params: { screen: 'SHOPPING_LIST' },
            merge: true,
          });
          closeDrawer();
        }}
      />
      <PaperDrawer.Item
        label="Zmywaki"
        icon="mirror-rectangle"
        theme={DRAWER_ITEM_THEME}
        onPress={() => {
          navigate('SPONGES');
          closeDrawer();
        }}
      />
    </PaperDrawer.Section>
  </SafeAreaView>;
};

const DRAWER_ITEM_THEME = {
  colors: {
    onSurfaceVariant: Colors.text.main, // Makes text white
  },
  roundness: 0,
};
