import { ParentItemEditorPage } from '../ParentItemEditor/ParentItemEditorPage';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import {
  CategoriesAndParentItemsTabsNavigation,
} from './CategoriesAndParentItemsTabsNavigation';
import { CategoriesAndParentItemsStackParamsList } from '../../../navigation/navigationTypes';
import { CategoryEditorPage } from '../CategoryEditor/CategoryEditorPage';
import { CategoryCreatorPage } from './CategoryCreator/CategoryCreatorPage';
import { Colors } from '../../../app/Theme';

const Stack = createNativeStackNavigator<CategoriesAndParentItemsStackParamsList>()

export const CategoriesAndParentItemsStackNavigation = () => {
  return (
    <Stack.Navigator
      screenOptions={{
        headerShadowVisible: false,
        headerShown: true,
        headerStyle: {
          backgroundColor: Colors.secondary,
        },
        headerTintColor: Colors.white,
      }}
    >
      <Stack.Screen
        options={{
          title: "Kategorie i grupy",
          animation: 'none'
        }}
        name="CATEGORIES_AND_PARENT_ITEMS_TABS_NAVIGATION"
        component={CategoriesAndParentItemsTabsNavigation}
      />
      <Stack.Screen
        options={{ title: "Edytuj grupę", animation: 'none' }}
        name="EDIT_PARENT_ITEM"
        component={ParentItemEditorPage}
      />
      <Stack.Screen
        options={{ title: "Nowa kategoria", animation: 'none' }}
        name="ADD_CATEGORY"
        component={CategoryCreatorPage}
      />
      <Stack.Screen
        options={{ title: "Edytuj kategorię", animation: 'none' }}
        name="EDIT_CATEGORY"
        component={CategoryEditorPage}
      />
    </Stack.Navigator>
  )
}

