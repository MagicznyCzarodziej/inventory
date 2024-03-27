import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import { CategoriesPage } from './Categories/CategoriesPage';
import { GroupsPage } from './Groups/GroupsPage';
import { SafeAreaView } from 'react-native-safe-area-context';

export type CategoriesAndGroupsParamsList = {
  CATEGORIES: undefined;
  GROUPS: undefined;
}

const Tab = createMaterialTopTabNavigator<CategoriesAndGroupsParamsList>();

export const CategoriesAndGroupsTabNavigation = () => {
  return (
    <SafeAreaView style={{ flex: 1 }}>
      <Tab.Navigator>
        <Tab.Screen options={{ tabBarLabel: "Kategorie" }} name="CATEGORIES" component={CategoriesPage} />
        <Tab.Screen options={{ tabBarLabel: "Grupy" }} name="GROUPS" component={GroupsPage} />
      </Tab.Navigator>
    </SafeAreaView>
  )
}
