import { PropsWithChildren, useState } from 'react';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { Drawer as DrawerLayout } from 'react-native-drawer-layout';
import { Drawer } from '../layouts/drawer/Drawer';
import { SpongesPage } from '../pages/Sponges/SpongesPage';
import { InventoryTabNavigation, InventoryTabsParamList } from '../pages/Inventory/InventoryTabNavigation';
import { DrawerContext } from '../context/DrawerContext';
import { LoginPage } from '../pages/Account/LoginPage';
import { StatusBar } from 'expo-status-bar';
import { WrapWithQueryClient } from './QueryClientProvider';
import { CameraPage } from '../pages/Inventory/InventoryManager/ItemCreator/CameraPage';
import { BarcodeScannerPage } from '../pages/Inventory/InventoryManager/ItemCreator/BarcodeScannerPage';

export type RootStackParamList = {
  INVENTORY: { screen: keyof InventoryTabsParamList };
  SPONGES: undefined;
  ACCOUNT: undefined;
  SETTINGS: undefined;
  INVENTORY_ADD_ITEM_CAMERA: undefined;
  INVENTORY_ADD_ITEM_BARCODE_SCANNER: undefined;
}

const Stack = createNativeStackNavigator<RootStackParamList>()

export const Root = () => {
  return (
    <SafeAreaProvider>
      <NavigationContainer>
        <WrapWithQueryClient>
          <WrapWithDrawer>
            <Stack.Navigator initialRouteName="INVENTORY">
              <Stack.Screen options={{ headerShown: false }} name="INVENTORY" component={InventoryTabNavigation} />
              <Stack.Screen options={{ headerShown: false }} name="SPONGES" component={SpongesPage} />
              <Stack.Screen options={{ headerShown: false }} name="ACCOUNT" component={LoginPage} />
              <Stack.Screen options={{ headerShown: false }} name="INVENTORY_ADD_ITEM_CAMERA" component={CameraPage} />
              <Stack.Screen
                options={{ headerShown: false }}
                name="INVENTORY_ADD_ITEM_BARCODE_SCANNER"
                component={BarcodeScannerPage}
              />
            </Stack.Navigator>
          </WrapWithDrawer>
        </WrapWithQueryClient>
      </NavigationContainer>
    </SafeAreaProvider>
  );
}

const WrapWithDrawer = ({ children }: PropsWithChildren) => {
  const [isDrawerOpen, setIsDrawerOpen] = useState(false);

  return <DrawerContext.Provider value={{
    isOpen: isDrawerOpen,
    openDrawer: () => {
      setIsDrawerOpen(true)
    },
    closeDrawer: () => {
      setIsDrawerOpen(false)
    }
  }}>
    <StatusBar
      style="dark"
      translucent
    />
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
      {children}
    </DrawerLayout>
  </DrawerContext.Provider>
}
