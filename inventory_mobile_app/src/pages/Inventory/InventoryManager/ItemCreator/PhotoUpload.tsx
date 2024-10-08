import { Pressable, StyleSheet, View } from 'react-native';
import { Icon, Text } from 'react-native-paper';
import React from 'react';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { Colors } from '../../../../app/Theme';
import { PhotoPreview } from '../../../../components/Photo/PhotoPreview';
import { RootStackParamList } from '../../../../navigation/navigationTypes';

interface Props {
  photoPath?: string;
  isPhotoUploaded: boolean;
}

export const PhotoUpload = (props: Props) => {
  const { photoPath, isPhotoUploaded } = props;
  const { navigate: rootNavigate } = useNavigation<NavigationProp<RootStackParamList>>()

  return (
    <Pressable
      onPress={() => {
        rootNavigate("CAMERA", { from: "ADD_ITEM" })
      }}
    >
      {photoPath ? (
        <PhotoPreview
          photoUri={photoPath}
          isLoading={!isPhotoUploaded}
          isBlurred={!isPhotoUploaded}
        />
      ) : (
        <View style={styles.uploadPhoto}>
          <Icon source="camera-plus-outline" size={64} color={Colors.text.gray} />
          <Text style={styles.photoHint}>Dodaj zdjęcie</Text>
        </View>
      )}
    </Pressable>
  )
}

const styles = StyleSheet.create({
  uploadPhoto: {
    height: 240,
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(255, 255, 255, 0.1)',
  },
  photoHint: {
    fontSize: 20,
    marginTop: 8,
    color: Colors.text.gray
  },
})
