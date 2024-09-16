import { api } from '../api';
import { useQuery } from '@tanstack/react-query';
import { Category, ParentItem } from '../common';

const getItem = (itemId: string) => () => api.get<GetItemResponse>(`/items/${itemId}`);

export const useGetItem = (itemId: string) => useQuery({
  queryFn: getItem(itemId),
  queryKey: ['getItem', itemId],
});

export interface GetItemResponse {
  id: string;
  name: string;
  description: string | null;
  category: Category;
  parentItem: ParentItem | null;
  brand: string | null;
  currentStock: number;
  desiredStock: number;
  lastRestockedAt: string | null;
  photoUrl: string | null;
  barcode: string | null;
}
