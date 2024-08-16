import { Image, Pressable, StyleSheet, View } from 'react-native';
import { Spinner } from '../../../../components/Spinner';
import { Icon, Text } from 'react-native-paper';
import React from 'react';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { RootStackParamList } from '../../../../app/Root';
import { Colors } from '../../../../app/Theme';

interface Props {
  photoPath?: string;
  isPhotoUploaded: boolean;
}

export const Photo = (props: Props) => {
  const { photoPath, isPhotoUploaded } = props;
  const { navigate: rootNavigate } = useNavigation<NavigationProp<RootStackParamList>>()

  if (photoPath) {
    return (
      <View>
        <Image
          source={{ uri: photoPath }}
          style={{ height: 240 }}
          blurRadius={isPhotoUploaded ? 0 : 30}
        />
        {!isPhotoUploaded && <Spinner style={styles.photoSpinner} />}
      </View>
    )
  }

  return (
    <Pressable
      style={styles.photoContainer}
      onPress={() => {
        rootNavigate("INVENTORY_ADD_ITEM_CAMERA")
      }}>
      <Icon source="camera-plus-outline" size={64} color={Colors.secondary}/>
      <Text style={styles.photoHint}>Dodaj zdjęcie</Text>
    </Pressable>
  )
}

const styles = StyleSheet.create({
  photoContainer: {
    height: 240,
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.1)',
  },
  photoHint: {
    fontSize: 20,
    marginTop: 8
  },
  photoSpinner: {
    position: 'absolute',
    top: 0,
    bottom: 0,
    left: 0,
    right: 0,
  },
})
