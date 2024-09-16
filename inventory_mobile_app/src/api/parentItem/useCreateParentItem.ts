import { api } from '../api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

const createParentItem = (request: CreateParentItemRequest) => api.post<undefined, CreateParentItemRequest>(`/parent-items`, request);

export const useCreateParentItem = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: createParentItem,
    onSuccess: (data, variables) => {
      queryClient.invalidateQueries({ queryKey: ["getParentItems"] })
      queryClient.invalidateQueries({ queryKey: ["getItems"] })
      queryClient.invalidateQueries({ queryKey: ["getCategory"] })
    }
  });
}

interface CreateParentItemRequest {
  name: string,
  categoryId: string,
}
