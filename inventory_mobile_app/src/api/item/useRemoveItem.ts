import { api } from '../api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

const removeItem = (itemId: string) => api.delete<undefined>(`/items/${itemId}`);

export const useRemoveItem = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: removeItem,
    onSuccess: (data, variables) => {
      queryClient.invalidateQueries({ queryKey: ["getItems"] })
      queryClient.invalidateQueries({ queryKey: ["getCategory"] })
    }
  });
}
