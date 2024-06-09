import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import { CategoriesPage } from './Categories/CategoriesPage';
import { GroupsPage } from './Groups/GroupsPage';
import { SafeAreaView, useSafeAreaInsets } from 'react-native-safe-area-context';
import { Page } from '../../../layouts/Page';
import { Colors } from '../../../app/Theme';

export type CategoriesAndGroupsParamsList = {
  CATEGORIES: undefined;
  GROUPS: undefined;
}

const Tab = createMaterialTopTabNavigator<CategoriesAndGroupsParamsList>();

export const CategoriesAndGroupsTabNavigation = () => {
  const safeArea = useSafeAreaInsets()
  return (
    <Page style={{ flex: 1 }}>
      <Tab.Navigator
        screenOptions={{
          tabBarStyle: {
            backgroundColor: Colors.background
          },
          tabBarLabelStyle:{
            fontSize: 18
          },
          tabBarIndicatorStyle: {
            backgroundColor: Colors.primary
          },
          tabBarActiveTintColor: Colors.accent
        }}
      >
        <Tab.Screen options={{ tabBarLabel: "Kategorie" }} name="CATEGORIES" component={CategoriesPage} />
        <Tab.Screen options={{ tabBarLabel: "Grupy" }} name="GROUPS" component={GroupsPage} />
      </Tab.Navigator>
    </Page>
  )
}
