import { api } from './api';
import { useQuery } from '@tanstack/react-query';
import { isParentEntry } from '../pages/Inventory/utils/itemsUtils';

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
  if (isParentEntry(entry)) {
    return entry.items.some(entryNameContainsPhrase(searchPhrase)) || entry.name.toLowerCase().includes(searchPhrase.toLowerCase())
  }

  return entry.name.toLowerCase().includes(searchPhrase.toLowerCase())
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
  currentStock: number;
  desiredStock: number;
}