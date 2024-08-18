import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import { CategoriesPage } from './Categories/CategoriesPage';
import { ParentItemsPage } from './Groups/ParentItemsPage';
import { Page } from '../../../layouts/Page';
import { Colors } from '../../../app/Theme';

export type CategoriesAndGroupsParamsList = {
  CATEGORIES: undefined;
  GROUPS: undefined;
}

const Tab = createMaterialTopTabNavigator<CategoriesAndGroupsParamsList>();

export const CategoriesAndGroupsTabNavigation = () => {
  return (
    <Page style={{ flex: 1, backgroundColor: Colors.primary }}>
      <Tab.Navigator
        screenOptions={{
          tabBarStyle: {
            backgroundColor: Colors.primary
          },
          tabBarLabelStyle:{
            fontSize: 18
          },
          tabBarIndicatorStyle: {
            backgroundColor: Colors.secondary
          },
          tabBarActiveTintColor: Colors.secondary,
          tabBarInactiveTintColor: Colors.secondary,
        }}
      >
        <Tab.Screen options={{ tabBarLabel: "Kategorie" }} name="CATEGORIES" component={CategoriesPage} />
        <Tab.Screen options={{ tabBarLabel: "Grupy" }} name="GROUPS" component={ParentItemsPage} />
      </Tab.Navigator>
    </Page>
  )
}
