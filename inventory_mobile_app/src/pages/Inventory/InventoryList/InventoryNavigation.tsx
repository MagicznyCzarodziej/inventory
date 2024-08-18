import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { InventoryPage } from './InventoryPage';
import { ItemPreviewPage } from '../ItemPreview/ItemPreviewPage';
import { InventoryManagerPage } from '../InventoryManager/InventoryManagerPage';
import { ItemCreatorPage } from '../InventoryManager/ItemCreator/ItemCreatorPage';
import { ItemEditorPage } from '../ItemEditor/ItemEditorPage';
import { ParentItemCreatorPage } from '../InventoryManager/ParentItemCreator/ParentItemCreatorPage';
import { InventoryStackParamList } from '../../../navigation/navigationTypes';

const Stack = createNativeStackNavigator<InventoryStackParamList>()

export const InventoryNavigation = () => {
  return <Stack.Navigator initialRouteName="INVENTORY_LIST">
    <Stack.Screen options={{ headerShown: false }} name="INVENTORY_LIST" component={InventoryPage} />
    <Stack.Screen options={{ headerShown: false }} name="INVENTORY_MANAGER" component={InventoryManagerPage} />
    <Stack.Screen options={{ headerShown: false }} name="ITEM" component={ItemPreviewPage} />
    <Stack.Screen options={{ headerShown: false, animation: 'fade' }} name="ADD_ITEM" component={ItemCreatorPage} />
    <Stack.Screen options={{ headerShown: false, animation: 'none' }} name="EDIT_ITEM" component={ItemEditorPage} />
    <Stack.Screen
      options={{ headerShown: false, animation: 'none' }}
      name="ADD_PARENT_ITEM"
      component={ParentItemCreatorPage}
    />
  </Stack.Navigator>
}