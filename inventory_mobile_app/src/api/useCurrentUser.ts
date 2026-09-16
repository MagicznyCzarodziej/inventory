import { api } from './api';
import { useQuery } from '@tanstack/react-query';

interface MeResponse {
  username: string;
}

const fetchMe = (signal?: AbortSignal) => api.get<MeResponse>('/me', signal);

export const useCurrentUser = () => {
  return useQuery({
    queryKey: ['me'],
    queryFn: ({ signal }) => fetchMe(signal),
    retry: false,
    staleTime: Infinity,
  });
};
