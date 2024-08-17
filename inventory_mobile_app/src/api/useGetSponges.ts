import { api } from './api';
import { useQuery } from '@tanstack/react-query';

const getSponges = () => api.get<GetSpongesResponse>(`/sponges`);

export const useGetSponges = () => useQuery({
  queryFn: getSponges,
  queryKey: ['getSponges'],
});

export interface GetSpongesResponse {
  sponges: Sponge[];
}

interface Sponge {
  id: string;
  color: string;
  purpose: string;
}