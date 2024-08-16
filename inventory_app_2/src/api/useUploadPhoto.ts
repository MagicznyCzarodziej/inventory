import { api } from './api';
import { useMutation } from '@tanstack/react-query';

const uploadPhoto = ({ path }: {
  path: string
}) => {
  const formData = new FormData();
  // @ts-ignore
  formData.append('file', {
    uri: path,
    name: 'photo.jpg',
    type: 'image/jpg'
  });

  return api.uploadImage<UploadPhotoResponse, FormData>(`/photos`,
    formData
  )
};


export const useUploadPhoto = () => {
  return useMutation({
    mutationFn: uploadPhoto,
  });
}

interface UploadPhotoResponse {
  photoId: string
}