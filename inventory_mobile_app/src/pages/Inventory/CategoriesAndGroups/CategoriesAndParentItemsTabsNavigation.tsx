import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import { Page } from '../../../layouts/Page';
import { Colors } from '../../../app/Theme';
import { CategoriesPage } from './Categories/CategoriesPage';
import { ParentItemsPage } from './Groups/ParentItemsPage';
import { CategoriesAndParentItemsTabsParamsList } from '../../../navigation/navigationTypes';

const Tab = createMaterialTopTabNavigator<CategoriesAndParentItemsTabsParamsList>();

export const CategoriesAndParentItemsTabsNavigation = () => {
  return (
    <Page style={{ flex: 1, backgroundColor: Colors.primary }}>
      <Tab.Navigator
        screenOptions={{
          tabBarStyle: {
            backgroundColor: Colors.primary
          },
          tabBarLabelStyle: {
            fontSize: 18
          },
          tabBarIndicatorStyle: {
            backgroundColor: Colors.secondary
          },
          tabBarActiveTintColor: Colors.secondary,
          tabBarInactiveTintColor: Colors.secondary,
        }}
        backBehavior="none"
      >
        <Tab.Screen options={{ tabBarLabel: "Kategorie" }} name="CATEGORIES" component={CategoriesPage} />
        <Tab.Screen options={{ tabBarLabel: "Grupy" }} name="PARENT_ITEMS_LIST" component={ParentItemsPage} />
      </Tab.Navigator>
    </Page>
  )
}