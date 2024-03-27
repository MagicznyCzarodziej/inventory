import { useState } from 'react';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { Drawer as DrawerLayout } from 'react-native-drawer-layout';
import { Drawer } from '../layouts/drawer/Drawer';
import { SpongesPage } from '../pages/Sponges/SpongesPage';
import { InventoryTabNavigation, InventoryTabsParamList } from '../pages/Inventory/InventoryTabNavigation';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { DrawerContext } from '../context/DrawerContext';
import { LoginPage } from '../pages/Account/LoginPage';

export type RootStackParamList = {
  INVENTORY: { screen: keyof InventoryTabsParamList };
  SPONGES: undefined;
  ACCOUNT: undefined;
  SETTINGS: undefined;
}

const Stack = createNativeStackNavigator<RootStackParamList>()
const queryClient = new QueryClient()

export const Root = () => {
  const [isDrawerOpen, setIsDrawerOpen] = useState(true);

  return (
    <SafeAreaProvider>
      <NavigationContainer>
        <QueryClientProvider client={queryClient}>
          <DrawerContext.Provider value={{
            isOpen: isDrawerOpen,
            openDrawer: () => {
              setIsDrawerOpen(true)
            },
            closeDrawer: () => {
              setIsDrawerOpen(false)
            }
          }}>
            <DrawerLayout
              open={isDrawerOpen}
              onOpen={() => setIsDrawerOpen(true)}
              onClose={() => setIsDrawerOpen(false)}
              renderDrawerContent={() => {
                return <Drawer
                  closeDrawer={() => {
                    setIsDrawerOpen(false)
                  }}
                />
              }}
            >
              <Stack.Navigator initialRouteName="ACCOUNT">
                <Stack.Screen options={{ headerShown: false }} name="INVENTORY" component={InventoryTabNavigation} />
                <Stack.Screen options={{ headerShown: false }} name="SPONGES" component={SpongesPage} />
                <Stack.Screen options={{ headerShown: false }} name="ACCOUNT" component={LoginPage} />
              </Stack.Navigator>
            </DrawerLayout>
          </DrawerContext.Provider>
        </QueryClientProvider>
      </NavigationContainer>
    </SafeAreaProvider>
  );
}
