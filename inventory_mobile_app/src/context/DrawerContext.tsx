import { createContext } from 'react';

export const DrawerContext = createContext({
  isOpen: false,
  openDrawer: () => {},
  closeDrawer: () => {},
})