import { api } from '../api';
import { useQuery } from '@tanstack/react-query';

global.Buffer = global.Buffer || require('buffer').Buffer

const getPhoto = (photoUrl: string) => async () => {
  const response = await api.getImage<ArrayBuffer>(photoUrl);

  return convertBytesToBase64Image(response);
}

export const useGetPhoto = (photoUrl: string | null) => useQuery({
  queryFn: getPhoto(photoUrl!),
  queryKey: ['getPhoto', photoUrl],
  enabled: !!photoUrl,
});

const convertBytesToBase64Image = (photoBytes: ArrayBuffer): string => {
  const photoBase64 = Buffer.from(photoBytes).toString('base64')
  return `data:image/jpeg;base64,${photoBase64}`
}
