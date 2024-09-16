import { api } from '../api';
import { useQuery } from '@tanstack/react-query';
import { Category } from '../common';

const getCategories = () => api.get<GetCategoriesResponse>(`/categories`);

export const useGetCategories = () => useQuery({
  queryFn: getCategories,
  queryKey: ['getCategories'],
});

export interface GetCategoriesResponse {
  categories: Category[];
}
