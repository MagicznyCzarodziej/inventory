import { api } from '../api';
import { useMutation } from '@tanstack/react-query';
import { Image } from 'react-native-compressor';

const uploadPhoto = async ({ path }: {
  path: string
}) => {
  const compressedImagePath = await Image.compress(path)

  const formData = new FormData();
  // @ts-ignore
  formData.append('file', {
    uri: compressedImagePath,
    name: 'photo.jpg',
    type: 'image/jpg'
  });

  return api.uploadImage<UploadPhotoResponse, FormData>(`/photos`, formData)
};

export const useUploadPhoto = () => {
  return useMutation({
    mutationFn: uploadPhoto,
  });
}

interface UploadPhotoResponse {
  photoId: string
}