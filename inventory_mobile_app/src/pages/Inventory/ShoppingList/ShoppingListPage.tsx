import { Text } from 'react-native';
import { Page } from '../../../layouts/Page';
import { Colors } from '../../../app/Theme';

export const ShoppingListPage = () => {
  return <Page safeArea={false}>
    <Text style={{
      textAlign: 'center',
      marginTop: 64,
      color: Colors.text.main
    }}>Tu będzie lista zakupów</Text>
  </Page>
}