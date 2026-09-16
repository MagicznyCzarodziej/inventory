import { QueryClient } from '@tanstack/react-query';

export const clearSession = (queryClient: QueryClient) => {
  queryClient.setQueryData(['me'], null);
  queryClient.invalidateQueries();
};
