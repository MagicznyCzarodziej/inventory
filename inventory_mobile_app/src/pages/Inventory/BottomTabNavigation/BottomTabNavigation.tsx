import React from 'react';
import { ShoppingListPage } from '../ShoppingList/ShoppingListPage';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs'
import {
  CategoriesAndParentItemsStackNavigation
} from '../CategoriesAndGroups/CategoriesAndParentItemsStackNavigation';
import { InventoryNavigation } from '../InventoryList/InventoryNavigation';
import { Colors } from '../../../app/Theme';
import { InventoryTabsParamList } from '../../../navigation/navigationTypes';
import { NavigationLabel } from './NavigationLabel';
import { NavigationIcon } from './NavigationIcon';

const Tab = createBottomTabNavigator<InventoryTabsParamList>();

export const BottomTabNavigation = () => {
  return <Tab.Navigator
    backBehavior={"initialRoute"}
    initialRouteName="INVENTORY_NAVIGATION"
    screenOptions={{
      tabBarHideOnKeyboard: true,
      headerShadowVisible: false,
      headerShown: false,
      headerStyle: {
        backgroundColor: Colors.secondary,
      },
      headerTintColor: Colors.white,
      tabBarStyle: {
        backgroundColor: Colors.secondary,
        height: 60
      },
    }}
  >
    <Tab.Screen
      name="SHOPPING_LIST"
      component={ShoppingListPage}
      options={{
        headerShown: true,
        title: "Lista zakupów",
        tabBarLabel: NavigationLabel,
        tabBarIcon: ({ focused }) => <NavigationIcon icon="basket-outline" isFocused={focused} />
      }}
    />
    <Tab.Screen
      name="INVENTORY_NAVIGATION"
      options={{
        title: "Inwentarz",
        headerShadowVisible: false,

        tabBarLabel: NavigationLabel,
        tabBarIcon: ({ focused }) => <NavigationIcon icon="clipboard-list-outline" isFocused={focused} />,
      }}
      component={InventoryNavigation}
    />
    <Tab.Screen
      name="CATEGORIES_AND_PARENT_ITEMS_STACK_NAVIGATION"
      options={{
        title: "Kategorie i grupy",
        tabBarLabel: NavigationLabel,
        tabBarIcon: ({ focused }) => <NavigationIcon icon="shape-outline" isFocused={focused} />
      }}
      component={CategoriesAndParentItemsStackNavigation}
    />
  </Tab.Navigator>
}


