import { api } from './api';
import { useQuery } from '@tanstack/react-query';
import { ParentItem } from './common';

const getParentItems = () => api.get<GetParentItemsResponse>(`/parent-items`);

export const useGetParentItems = () => useQuery({
  queryFn: getParentItems,
  queryKey: ['getParentItems'],
});

export interface GetParentItemsResponse {
  parentItems: ParentItem[];
}
