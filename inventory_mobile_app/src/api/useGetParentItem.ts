import { api } from './api';
import { useQuery } from '@tanstack/react-query';
import { ParentItem } from './common';

const getParentItem = (parentItemId: string) => () => api.get<GetParentItemResponse>(`/parent-items/${parentItemId}`);

export const useGetParentItem = (parentItemId: string) => useQuery({
  queryFn: getParentItem(parentItemId),
  queryKey: ['getParentItem', parentItemId],
});

export type GetParentItemResponse = ParentItem;