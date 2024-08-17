import React from 'react';
import { createMaterialBottomTabNavigator } from 'react-native-paper/react-navigation';
import { ShoppingListPage } from './ShoppingList/ShoppingListPage';
import {
  CategoriesAndGroupsParamsList,
  CategoriesAndGroupsTabNavigation
} from './CategoriesAndGroups/CategoriesAndGroupsTabNavigation';
import { InventoryNavigation, InventoryStackParamList } from './InventoryList/InventoryNavigation';
import { Colors } from '../../app/Theme';

export type InventoryTabsParamList = {
  INVENTORY_NAVIGATION: InventoryStackParamList;
  SHOPPING_LIST: undefined;
  CATEGORIES_AND_GROUPS: CategoriesAndGroupsParamsList;
}

const Tab = createMaterialBottomTabNavigator<InventoryTabsParamList>();

export const InventoryTabNavigation = () => {
  return <Tab.Navigator
    initialRouteName="INVENTORY_NAVIGATION"
    shifting
    activeColor={Colors.accent}
    inactiveColor={Colors.white}
    barStyle={{
      height: 70,
      backgroundColor: Colors.secondary,
    }}
    activeIndicatorStyle={{
      backgroundColor: Colors.primary,
    }}
    sceneAnimationType="shifting"
  >
    <Tab.Screen
      options={{ tabBarIcon: "basket-outline", tabBarLabel: "Lista zakupów" }}
      name="SHOPPING_LIST"
      component={ShoppingListPage}
    />
    <Tab.Screen
      options={{ tabBarIcon: "clipboard-list-outline", tabBarLabel: "Inwentarz" }}
      name="INVENTORY_NAVIGATION"
      component={InventoryNavigation}
    />
    <Tab.Screen
      options={{ tabBarIcon: "shape-outline", tabBarLabel: "Kategorie i grupy" }}
      name="CATEGORIES_AND_GROUPS"
      component={CategoriesAndGroupsTabNavigation}
    />
  </Tab.Navigator>
}