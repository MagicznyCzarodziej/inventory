import React from 'react';
import { Image, View } from 'react-native';
import { useGetPhoto } from '../../../api/useGetPhoto';
import { Spinner } from '../../../components/Spinner';

global.Buffer = global.Buffer || require('buffer').Buffer

interface Props {
  photoUrl: string | null
}

export const ItemPreviewPhoto = React.memo((props: Props) => {
  const { photoUrl } = props

  const photoQuery = useGetPhoto(photoUrl)

  if (photoUrl === null) {
    return null;
  }

  if (photoQuery.isPending) {
    return <View style={{ height: 270, display: "flex", justifyContent: "center" }}>
      <Spinner />
    </View>
  }

  if (photoQuery.isError) {
    return null;
  }

  return <Image
    style={{
      width: '100%',
      height: 270,
    }}
    source={{
      uri: convertBytesToBase64Image(photoQuery.data)
    }}
  />
})

const convertBytesToBase64Image = (photoBytes: ArrayBuffer): string => {
  const photoBase64 = Buffer.from(photoBytes).toString('base64')
  return `data:image/jpeg;base64,${photoBase64}`
}
