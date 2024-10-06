import React from 'react';
import { Image, Pressable, StyleSheet, Text, } from 'react-native';
import { useGetPhoto } from '../../../api/photo/useGetPhoto';

interface Props {
  photoUri: string
  closePreview: () => void
}

export const PhotoFullSizePreview = (props: Props) => {
  const { photoUri, closePreview } = props

  const photoQuery = useGetPhoto(photoUri)

  if (photoQuery.isError) {
    return <Text>Error</Text>
  }

  return <Pressable
    onPress={closePreview}
    style={styles.container}
  >
    <Image
      source={{ uri: photoQuery.data }}
      style={styles.photo}
    />
  </Pressable>
}

const styles = StyleSheet.create({
  container: {
    position: "absolute",
    left: 0,
    right: 0,
    bottom: 0,
    top: 0,
    zIndex: 10,
    backgroundColor: "rgba(0,0,0,0.9)"
  },
  photo: {
    width: "100%",
    aspectRatio: 0.5,
    resizeMode: "contain"
  },
})