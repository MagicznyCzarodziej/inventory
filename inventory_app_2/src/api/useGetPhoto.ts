import { api } from './api';
import { useQuery } from '@tanstack/react-query';

const getPhoto = (photoUrl: string) => () => api.getImage<ArrayBuffer>(photoUrl);

export const useGetPhoto = (photoUrl: string | null) => useQuery({
  queryFn: getPhoto(photoUrl!),
  queryKey: ['getPhoto', photoUrl],
  enabled: !!photoUrl,
});

