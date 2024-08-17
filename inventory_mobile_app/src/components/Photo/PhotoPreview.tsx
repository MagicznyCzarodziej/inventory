import { Image, StyleSheet, View } from 'react-native';
import React from 'react';
import { Spinner } from '../Spinner';

interface Props {
  photoUri?: string
  isBlurred?: boolean
  isLoading?: boolean
}

export const PhotoPreview = React.memo((props: Props) => {
  const { photoUri, isBlurred = false, isLoading = false } = props

  return <View style={styles.container}>
    {photoUri &&
      <Image
        source={{ uri: photoUri }}
        style={styles.photo}
        blurRadius={isBlurred ? 30 : 0}
      />
    }

    {isLoading &&
      <Spinner style={styles.photoSpinner} />
    }
  </View>
})

const styles = StyleSheet.create({
  container: {
    height: 240,
    display: "flex",
    justifyContent: "center",
  },
  photo: {
    height: 240,
  },
  photoSpinner: {
    position: 'absolute',
    top: 0,
    bottom: 0,
    left: 0,
    right: 0,
  },
})
