import React from 'react';
import { Text, Pressable, StyleSheet } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { ItemPhoto } from '../../../components/Photo/ItemPhoto';
import { Icon } from 'react-native-paper';
import { Colors } from '../../../app/Theme';
import { RootStackParamList } from '../../../navigation/navigationTypes';

interface Props {
  originScreen: "ADD_ITEM" | "EDIT_ITEM"
  remotePhotoUrl: string | null
  localPhotoPath?: string
  isLoading: boolean
}

type Navigation = NativeStackNavigationProp<RootStackParamList, "CAMERA">

export const EditablePhoto = (props: Props) => {
  const { originScreen, remotePhotoUrl, localPhotoPath, isLoading } = props
  const { navigate } = useNavigation<Navigation>()

  if (!localPhotoPath && !remotePhotoUrl) {
    return <Pressable
      style={styles.photoContainer}
      onPress={() => {
        navigate("CAMERA", { from: originScreen })
      }}>
      <Icon source="camera-plus-outline" size={64} color={Colors.secondary} />
      <Text style={styles.photoHint}>Dodaj zdjęcie</Text>
    </Pressable>
  }

  return <Pressable onPress={() => {
    navigate("CAMERA", { from: originScreen })
  }}>
    <ItemPhoto
      localPhotoPath={localPhotoPath}
      remotePhotoUrl={remotePhotoUrl}
      isUploading={isLoading}
    />
  </Pressable>
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
})