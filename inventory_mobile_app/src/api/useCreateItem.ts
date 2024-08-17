import { api } from './api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

const createItem = (request: CreateItemRequest) => api.post<undefined, CreateItemRequest>(`/items`, request);

export const useCreateItem = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: createItem,
    onSuccess: (data, variables) => {
      queryClient.invalidateQueries({ queryKey: ["getItems"] })
    }
  });
}

interface CreateItemRequest {
  itemType: 'ITEM' | 'SUB_ITEM',
  name: string,
  description?: string,
  categoryId?: string,
  parentId?: string,
  brand?: string,
  currentStock: number,
  desiredStock: number,
  photoId?: string,
  barcode?: string,
}
