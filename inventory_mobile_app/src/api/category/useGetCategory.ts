import { api } from '../api';
import { useQuery } from '@tanstack/react-query';

const getCategory = (categoryId: string) => () => api.get<GetCategoryResponse>(`/categories/${categoryId}`);

export const useGetCategory = (categoryId: string) => useQuery({
  queryFn: getCategory(categoryId),
  queryKey: ['getCategory', categoryId],
});

export interface GetCategoryResponse {
  id: string;
  name: string;
  itemsCount: number;
}