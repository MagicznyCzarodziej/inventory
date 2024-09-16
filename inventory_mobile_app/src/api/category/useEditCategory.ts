import { api } from '../api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

const editCategory = ({ categoryId, request }: {
  categoryId: string,
  request: EditCategoryRequest
}) => api.put<undefined, EditCategoryRequest>(`/categories/${categoryId}`, request);

export const useEditCategory = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: editCategory,
    onSuccess: (data, variables) => {
      queryClient.invalidateQueries({ queryKey: ["getCategories"] })
      queryClient.invalidateQueries({ queryKey: ["getCategory", variables.categoryId], exact: true })
    }
  });
}

interface EditCategoryRequest {
  name: string
}