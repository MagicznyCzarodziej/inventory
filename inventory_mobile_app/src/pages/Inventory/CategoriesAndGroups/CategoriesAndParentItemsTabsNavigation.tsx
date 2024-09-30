import React from 'react';
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import { Page } from '../../../layouts/Page';
import { Colors } from '../../../app/Theme';
import { CategoriesPage } from './Categories/CategoriesPage';
import { ParentItemsPage } from './Groups/ParentItemsPage';
import { CategoriesAndParentItemsTabsParamsList } from '../../../navigation/navigationTypes';

const Tab = createMaterialTopTabNavigator<CategoriesAndParentItemsTabsParamsList>();

export const CategoriesAndParentItemsTabsNavigation = () => {
  return (
    <Page safeArea={false} style={{ flex: 1, backgroundColor: Colors.secondary }}>
      <Tab.Navigator
        screenOptions={{
          tabBarStyle: {
            backgroundColor: Colors.secondary
          },
          tabBarPressColor: Colors.secondary,
          tabBarLabelStyle: {
            fontSize: 14,
          },
          tabBarIndicatorStyle: {
            backgroundColor: Colors.primary
          },
          tabBarActiveTintColor: Colors.white,
          tabBarInactiveTintColor: Colors.white,
        }}
        backBehavior="none"
      >
        <Tab.Screen options={{ tabBarLabel: "Kategorie" }} name="CATEGORIES" component={CategoriesPage} />
        <Tab.Screen options={{ tabBarLabel: "Grupy" }} name="PARENT_ITEMS_LIST" component={ParentItemsPage} />
      </Tab.Navigator>
    </Page>
  )
}