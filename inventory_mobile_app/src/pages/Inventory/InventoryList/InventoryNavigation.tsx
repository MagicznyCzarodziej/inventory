import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { InventoryPage } from './InventoryPage';
import { ItemPreviewPage } from '../ItemPreview/ItemPreviewPage';
import { InventoryManagerPage } from '../InventoryManager/InventoryManagerPage';
import { ItemCreatorPage } from '../InventoryManager/ItemCreator/ItemCreatorPage';
import { ItemEditorPage } from '../ItemEditor/ItemEditorPage';

export type InventoryStackParamList = {
  INVENTORY_LIST: undefined;
  INVENTORY_MANAGER: undefined;
  ITEM: { itemId: string };
  ADD_ITEM: {
    parent?: {
      parentId: string,
      parentName: string,
    }
    nameDraft?: string,
    barcode?: string,
    photoPath?: string,
  };
  EDIT_ITEM: {
    itemId?: string,
    photoPath?: string
    barcode?: string,
  };
}

const Stack = createNativeStackNavigator<InventoryStackParamList>()

export const InventoryNavigation = () => {
  return <Stack.Navigator initialRouteName="INVENTORY_LIST">
    <Stack.Screen options={{ headerShown: false }} name="INVENTORY_LIST" component={InventoryPage} />
    <Stack.Screen options={{ headerShown: false }} name="INVENTORY_MANAGER" component={InventoryManagerPage} />
    <Stack.Screen options={{ headerShown: false }} name="ITEM" component={ItemPreviewPage} />
    <Stack.Screen options={{ headerShown: false, animation: 'fade' }} name="ADD_ITEM" component={ItemCreatorPage} />
    <Stack.Screen options={{ headerShown: false, animation: 'none' }} name="EDIT_ITEM" component={ItemEditorPage} />
  </Stack.Navigator>
}