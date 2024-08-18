import { api } from './api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

const removeParentItem = (parentItemId: string) => api.delete<undefined>(`/parent-items/${parentItemId}`);

export const useRemoveParentItem = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: removeParentItem,
    onSuccess: (data, variables) => {
      queryClient.invalidateQueries({ queryKey: ["getParentItems"] })
    }
  });
}
