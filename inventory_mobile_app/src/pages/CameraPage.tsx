import { CameraView, useCameraPermissions } from 'expo-camera';
import { CompositeScreenProps } from '@react-navigation/native';
import React, { useEffect, useRef, useState } from 'react';
import { Page } from '../layouts/Page';
import { IconButton } from 'react-native-paper';
import { StyleSheet, View } from 'react-native';
import { Colors } from '../app/Theme';
import { MissingCameraPermissions } from './Inventory/InventoryManager/ItemCreator/MissingCameraPermissions';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { InventoryStackParamList, RootStackParamList } from '../navigation/navigationTypes';

type Props = CompositeScreenProps<
  NativeStackScreenProps<RootStackParamList, "CAMERA">,
  CompositeScreenProps<
    NativeStackScreenProps<InventoryStackParamList, 'ADD_ITEM'>,
    NativeStackScreenProps<InventoryStackParamList, 'EDIT_ITEM'>
  >
>

export const CameraPage = (props: Props) => {
  const { from } = props.route.params;
  const navigation = props.navigation

  const [cameraPermission, requestPermission] = useCameraPermissions();
  const camera = useRef<CameraView>(null)

  const [isTorchEnabled, setIsTorchEnabled] = useState(false)

  useEffect(() => {
    requestPermission()
  }, []);

  if (!cameraPermission?.granted) {
    return <MissingCameraPermissions />
  }

  return <Page safeArea={false}>
    <CameraView
      ref={camera}
      enableTorch={isTorchEnabled}
      style={styles.cameraView}
      facing="back"
    />
    <View style={styles.controls}>
      <IconButton
        icon="close"
        onPress={() => {
          navigation.navigate({
            name: from,
            params: {},
            merge: true,
          })
        }}
      />
      <IconButton
        icon={isTorchEnabled ? "flash" : "flash-off"}
        onPress={() => {
          setIsTorchEnabled(previous => !previous)
        }}
      />
      <IconButton
        style={{
          marginLeft: 'auto'
        }}
        icon="check"
        onPress={async () => {
          if (!camera.current) {
            return
          }

          const photo = await camera.current?.takePictureAsync({
            quality: 0
          })

          if (!photo?.uri) {
            return;
          }

          navigation.navigate({
            name: from,
            params: { photoPath: photo.uri },
            merge: true,
          })
        }}
      />
    </View>
  </Page>
}

const styles = StyleSheet.create({
  cameraView: {
    flex: 1
  },
  controls: {
    backgroundColor: Colors.primary,
    padding: 8,
    display: "flex",
    flexDirection: "row",
    elevation: 20,
    zIndex: 1,
    alignItems: "center",
  },
})