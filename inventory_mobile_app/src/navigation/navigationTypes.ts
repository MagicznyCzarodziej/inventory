export type RootStackParamList = {
  INVENTORY: InventoryTabsParamList;
  SPONGES: undefined;
  ACCOUNT: undefined;
  SETTINGS: undefined;
  CAMERA: { from: 'ADD_ITEM' | 'EDIT_ITEM' }
  BARCODE_SCANNER: { from: 'ADD_ITEM' | 'EDIT_ITEM' };
}

export type InventoryTabsParamList = {
  INVENTORY_NAVIGATION: InventoryStackParamList;
  SHOPPING_LIST: undefined;
  CATEGORIES_AND_PARENT_ITEMS_STACK_NAVIGATION: CategoriesAndParentItemsStackParamsList;
}

export type InventoryStackParamList = {
  INVENTORY_LIST: undefined;
  INVENTORY_MANAGER: undefined;
  ITEM: { itemId: string };
  ADD_ITEM: {
    parent?: {
      parentId: string,
      parentName: string,
    }
    nameDraft?: string,
    barcode?: string,
    photoPath?: string,
  };
  EDIT_ITEM: {
    itemId?: string,
    photoPath?: string
    barcode?: string,
  };
  ADD_PARENT_ITEM: {
    nameDraft?: string,
  };
}

export type CategoriesAndParentItemsStackParamsList = {
  CATEGORIES_AND_PARENT_ITEMS_TABS_NAVIGATION: CategoriesAndParentItemsTabsParamsList;
  EDIT_PARENT_ITEM: {
    parentItemId: string,
  };
}

export type CategoriesAndParentItemsTabsParamsList = {
  CATEGORIES: undefined;
  PARENT_ITEMS_LIST: undefined;
}
