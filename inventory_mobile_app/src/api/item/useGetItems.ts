import { api } from '../api';
import { useQuery } from '@tanstack/react-query';
import removeAccents from 'remove-accents'
import { isParentEntry } from '../../pages/Inventory/utils/itemsUtils';
import { Category } from '../common';

const getItems = () => api.get<GetItemsResponse>(`/items`);

export const useGetItems = (searchPhrase?: string) => useQuery({
  queryFn: getItems,
  queryKey: ['getItems'],
  select: (data) => {
    if (!searchPhrase) {
      return data
    }

    return {
      ...data,
      entries: data.entries.filter(entryNameContainsPhrase(searchPhrase))
    }
  }
});

const entryNameContainsPhrase = (searchPhrase: string) => (entry: Entry) => {
  const normalizedSearchPhrase = removeAccents(searchPhrase).toLowerCase()

  if (isParentEntry(entry)) {
    return entry.items.some(entryNameContainsPhrase(searchPhrase)) || removeAccents(entry.name).toLowerCase().includes(normalizedSearchPhrase)
  }

  return removeAccents(entry.name).toLowerCase().includes(normalizedSearchPhrase)
}

export interface GetItemsResponse {
  entries: Entry[];
}

export type Entry = ParentEntry | ItemEntry

export interface ParentEntry {
  id: string;
  name: string;
  items: ItemEntry[];
}

export interface ItemEntry {
  id: string;
  name: string;
  brand: string | null;
  category: Category;
  currentStock: number;
  desiredStock: number;
}