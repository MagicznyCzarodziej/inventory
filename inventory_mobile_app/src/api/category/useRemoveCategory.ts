import { api } from '../api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

const removeCategory = (categoryId: string) => api.delete<undefined>(`/categories/${categoryId}`);

export const useRemoveCategory = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: removeCategory,
    onSuccess: (data, variables) => {
      queryClient.invalidateQueries({ queryKey: ["getCategories"] })
    }
  });
}
