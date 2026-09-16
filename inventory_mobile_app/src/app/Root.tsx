import { PropsWithChildren, RefObject, useEffect, useRef, useState } from 'react';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import { NavigationContainer, NavigationContainerRef } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { Drawer as DrawerLayout } from 'react-native-drawer-layout';
import { Drawer } from '../layouts/drawer/Drawer';
import { SpongesPage } from '../pages/Sponges/SpongesPage';
import { BottomTabNavigation } from '../pages/Inventory/BottomTabNavigation/BottomTabNavigation';
import { DrawerContext } from '../context/DrawerContext';
import { LoginPage } from '../pages/Account/LoginPage';
import { StatusBar } from 'expo-status-bar';
import { WrapWithQueryClient } from './QueryClientProvider';
import { CameraPage } from '../pages/CameraPage';
import { BarcodeScannerPage } from '../pages/BarcodeScannerPage';
import { RootStackParamList } from '../navigation/navigationTypes';
import { Colors } from './Theme';
import { AuthContextProvider, useAuth } from '../context/AuthContext';
import { ActivityIndicator, View } from 'react-native';
import { AccountPage } from '../pages/Account/AccountPage';

const Stack = createNativeStackNavigator<RootStackParamList>();

export const Root = () => {
  const navigationRef = useRef<NavigationContainerRef<RootStackParamList>>(null);

  return (
    <SafeAreaProvider>
      <WrapWithQueryClient>
        <NavigationContainer ref={navigationRef}>
          <AuthContextProvider>
            <AppNavigator navigationRef={navigationRef} />
          </AuthContextProvider>
        </NavigationContainer>
      </WrapWithQueryClient>
    </SafeAreaProvider>
  );
};

interface AppNavigatorProps {
  navigationRef: RefObject<NavigationContainerRef<RootStackParamList>>;
}

const AppNavigator = (props: AppNavigatorProps) => {
  const { navigationRef } = props;
  const { isAuthenticated, isLoading } = useAuth();
  const wasAuthenticatedRef = useRef(isAuthenticated);

  useEffect(() => {
    if (isLoading) {
      return;
    }

    const justLoggedIn = isAuthenticated && !wasAuthenticatedRef.current;
    wasAuthenticatedRef.current = isAuthenticated;

    if (justLoggedIn && navigationRef.current?.isReady()) {
      navigationRef.current.reset({
        index: 0,
        routes: [{ name: 'INVENTORY' }],
      });
    }
  }, [isAuthenticated, isLoading, navigationRef]);

  if (isLoading) {
    return <View
      style={{
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: Colors.background,
      }}
    >
      <ActivityIndicator size="large" color={Colors.primary} />
    </View>;
  }

  const stack = (
    <Stack.Navigator
      key={isAuthenticated ? 'authenticated' : 'unauthenticated'}
      initialRouteName={isAuthenticated ? 'INVENTORY' : 'LOGIN'}
      screenOptions={{
        headerShadowVisible: false,
        headerShown: false,
        headerStyle: {
          backgroundColor: Colors.secondary,
        },
        headerTintColor: Colors.white,
      }}
    >
      {isAuthenticated ? (
        <>
          <Stack.Screen
            options={{ headerShown: false }}
            name="INVENTORY"
            component={BottomTabNavigation}
          />
          <Stack.Screen
            options={{ headerShown: true, title: 'Zmywaki', headerBackVisible: false }}
            name="SPONGES"
            component={SpongesPage}
          />
          <Stack.Screen
            options={{ headerShown: false }}
            name="ACCOUNT"
            component={AccountPage}
          />
          <Stack.Screen
            options={{ headerShown: false }}
            name="CAMERA"
            component={CameraPage}
          />
          <Stack.Screen
            options={{ headerShown: false }}
            name="BARCODE_SCANNER"
            component={BarcodeScannerPage}
          />
        </>
      ) : (
        <Stack.Screen name="LOGIN" component={LoginPage} />
      )}
    </Stack.Navigator>
  );

  if (!isAuthenticated) {
    return stack;
  }

  return <WrapWithDrawer>{stack}</WrapWithDrawer>;
};

const WrapWithDrawer = ({ children }: PropsWithChildren) => {
  const [isDrawerOpen, setIsDrawerOpen] = useState(false);

  return <DrawerContext.Provider value={{
    isOpen: isDrawerOpen,
    openDrawer: () => {
      setIsDrawerOpen(true);
    },
    closeDrawer: () => {
      setIsDrawerOpen(false);
    },
  }}>
    <StatusBar
      style="light"
      translucent
    />
    <DrawerLayout
      open={isDrawerOpen}
      onOpen={() => setIsDrawerOpen(true)}
      onClose={() => setIsDrawerOpen(false)}
      drawerStyle={{
        backgroundColor: Colors.secondary,
      }}
      renderDrawerContent={() => {
        return <Drawer
          closeDrawer={() => {
            setIsDrawerOpen(false);
          }}
        />;
      }}
    >
      {children}
    </DrawerLayout>
  </DrawerContext.Provider>;
};
