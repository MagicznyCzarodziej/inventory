import { ParentItemEditorPage } from '../ParentItemEditor/ParentItemEditorPage';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import {
  CategoriesAndParentItemsTabsNavigation,
} from './CategoriesAndParentItemsTabsNavigation';
import { CategoriesAndParentItemsStackParamsList } from '../../../navigation/navigationTypes';
import { CategoryEditorPage } from '../CategoryEditor/CategoryEditorPage';
import { CategoryCreatorPage } from './CategoryCreator/CategoryCreatorPage';

const Stack = createNativeStackNavigator<CategoriesAndParentItemsStackParamsList>()

export const CategoriesAndParentItemsStackNavigation = () => {
  return (
    <Stack.Navigator>
      <Stack.Screen
        options={{ headerShown: false, animation: 'none' }}
        name="CATEGORIES_AND_PARENT_ITEMS_TABS_NAVIGATION"
        component={CategoriesAndParentItemsTabsNavigation}
      />
      <Stack.Screen
        options={{ headerShown: false, animation: 'none' }}
        name="EDIT_PARENT_ITEM"
        component={ParentItemEditorPage}
      />
      <Stack.Screen
        options={{ headerShown: false, animation: 'none' }}
        name="ADD_CATEGORY"
        component={CategoryCreatorPage}
      />
      <Stack.Screen
        options={{ headerShown: false, animation: 'none' }}
        name="EDIT_CATEGORY"
        component={CategoryEditorPage}
      />
    </Stack.Navigator>
  )
}

