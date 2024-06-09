import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { InventoryPage } from './InventoryPage';
import { ItemPreviewPage } from '../ItemPreview/ItemPreviewPage';
import { InventoryManagerPage } from '../InventoryManager/InventoryManagerPage';


export type InventoryStackParamList = {
  INVENTORY_LIST: undefined;
  INVENTORY_MANAGER: undefined;
  ITEM: { itemId: string };
  ADD_ITEM: { parentItemId?: string, name?: string };
}

const Stack = createNativeStackNavigator<InventoryStackParamList>()

export const InventoryNavigation = () => {
  return <Stack.Navigator initialRouteName="INVENTORY_LIST">
    <Stack.Screen options={{ headerShown: false }} name="INVENTORY_LIST" component={InventoryPage} />
    <Stack.Screen options={{ headerShown: false }} name="INVENTORY_MANAGER" component={InventoryManagerPage} />
    <Stack.Screen options={{ headerShown: false }} name="ITEM" component={ItemPreviewPage} />
  </Stack.Navigator>
}