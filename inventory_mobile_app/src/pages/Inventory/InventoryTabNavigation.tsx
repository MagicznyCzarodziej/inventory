import React from 'react';
import { createMaterialBottomTabNavigator } from 'react-native-paper/react-navigation';
import { ShoppingListPage } from './ShoppingList/ShoppingListPage';
import {
  CategoriesAndParentItemsStackNavigation
} from './CategoriesAndGroups/CategoriesAndParentItemsStackNavigation';
import { InventoryNavigation } from './InventoryList/InventoryNavigation';
import { Colors } from '../../app/Theme';
import { InventoryTabsParamList } from '../../navigation/navigationTypes';

const Tab = createMaterialBottomTabNavigator<InventoryTabsParamList>();

export const InventoryTabNavigation = () => {
  return <Tab.Navigator
    backBehavior={"initialRoute"}
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
      name="CATEGORIES_AND_PARENT_ITEMS_STACK_NAVIGATION"
      component={CategoriesAndParentItemsStackNavigation}
    />
  </Tab.Navigator>
}