import { api } from '../api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

const createCategory = (request: CreateCategoryRequest) => api.post<undefined, CreateCategoryRequest>(`/categories`, request);

export const useCreateCategory = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: createCategory,
    onSuccess: (data, variables) => {
      queryClient.invalidateQueries({ queryKey: ["getCategories"] })
    }
  });
}

interface CreateCategoryRequest {
  name: string,
}
