import React from 'react';
import { createMaterialBottomTabNavigator } from 'react-native-paper/react-navigation';
import { InventoryPage } from './InventoryList/InventoryPage';
import { ShoppingListPage } from './ShoppingList/ShoppingListPage';
import {
  CategoriesAndGroupsParamsList,
  CategoriesAndGroupsTabNavigation
} from './CategoriesAndGroups/CategoriesAndGroupsTabNavigation';

export type InventoryTabsParamList = {
  INVENTORY_LIST: undefined;
  SHOPPING_LIST: undefined;
  CATEGORIES_AND_GROUPS: { screen: keyof CategoriesAndGroupsParamsList };
  ITEM: undefined;
  ADD_ITEM: undefined;
}

const Tab = createMaterialBottomTabNavigator<InventoryTabsParamList>();

export const InventoryTabNavigation = () => {
  return <Tab.Navigator initialRouteName="INVENTORY_LIST">
    <Tab.Screen
      options={{ tabBarIcon: "basket-outline", tabBarLabel: "Lista zakupów" }}
      name="SHOPPING_LIST"
      component={ShoppingListPage}
    />
    <Tab.Screen
      options={{ tabBarIcon: "clipboard-list-outline", tabBarLabel: "Inwentarz" }}
      name="INVENTORY_LIST"
      component={InventoryPage}
    />
    <Tab.Screen
      options={{ tabBarIcon: "shape-outline", tabBarLabel: "Kategorie i grupy" }}
      name="CATEGORIES_AND_GROUPS"
      component={CategoriesAndGroupsTabNavigation}
    />
  </Tab.Navigator>
}