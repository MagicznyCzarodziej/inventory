import { api } from '../api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

const editItem = ({ itemId, request }: {
  itemId: string,
  request: EditItemRequest
}) => api.put<undefined, EditItemRequest>(`/items/${itemId}`, request);

export const useEditItem = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: editItem,
    onSuccess: (data, variables) => {
      queryClient.invalidateQueries({ queryKey: ["getItems"] })
      queryClient.invalidateQueries({ queryKey: ["getItem", variables.itemId], exact: true })
      queryClient.invalidateQueries({ queryKey: ["getCategory"] })
    }
  });
}

interface EditItemRequest {
  name: string
  description: string | null
  categoryId: string | null
  brand: string | null
  currentStock: number
  desiredStock: number
  photoId: string | null
  barcode: string | null
}