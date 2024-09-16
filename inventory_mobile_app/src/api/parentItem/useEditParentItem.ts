import { api } from '../api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

const editParentItem = ({ parentItemId, request }: {
  parentItemId: string,
  request: EditParentItemRequest
}) => api.put<undefined, EditParentItemRequest>(`/parent-items/${parentItemId}`, request);

export const useEditParentItem = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: editParentItem,
    onSuccess: (data, variables) => {
      queryClient.invalidateQueries({ queryKey: ["getParentItems"] })
      queryClient.invalidateQueries({ queryKey: ["getParentItem", variables.parentItemId], exact: true })
      queryClient.invalidateQueries({ queryKey: ["getCategory"] })
    }
  });
}

interface EditParentItemRequest {
  name: string
  categoryId: string
}