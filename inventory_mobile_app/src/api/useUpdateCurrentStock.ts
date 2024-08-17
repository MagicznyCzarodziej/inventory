import { api } from './api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

const updateCurrentStock = ({ itemId, stockChange }: {
  itemId: string,
  stockChange: number
}) => api.put<undefined, StockUpdateRequest>(`/items/${itemId}/stock/current`,
  {
    stockChange,
  });


export const useUpdateCurrentStock = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: updateCurrentStock,
    onSuccess: (data, variables) => {
      queryClient.invalidateQueries({ queryKey: ["getItems"] })
      queryClient.invalidateQueries({ queryKey: ["getItem", variables.itemId], exact: true })
    }
  });
}

interface StockUpdateRequest {
  stockChange: number;
}