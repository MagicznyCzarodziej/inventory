import React from 'react';
import { Text } from 'react-native';
import { useGetPhoto } from '../../api/photo/useGetPhoto';
import { PhotoPreview } from './PhotoPreview';

interface Props {
  photoUrl: string
}

export const RemoteItemPhoto = React.memo((props: Props) => {
  const { photoUrl } = props

  const photoQuery = useGetPhoto(photoUrl)

  if (photoQuery.isError) {
    return <Text>Error</Text>
  }

  return <PhotoPreview photoUri={photoQuery.data} isBlurred={false} isLoading={photoQuery.isPending} />
})

