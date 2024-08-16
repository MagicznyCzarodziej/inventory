import { CameraView, useCameraPermissions } from 'expo-camera';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { InventoryStackParamList } from '../../InventoryList/InventoryNavigation';
import React, { useEffect, useRef, useState } from 'react';
import { Page } from '../../../../layouts/Page';
import { IconButton } from 'react-native-paper';
import { StyleSheet, View } from 'react-native';
import { Colors } from '../../../../app/Theme';
import { MissingCameraPermissions } from './MissingCameraPermissions';

export const CameraPage = () => {
  const [cameraPermission, requestPermission] = useCameraPermissions();
  const { navigate } = useNavigation<NavigationProp<InventoryStackParamList>>()
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
          navigate("ADD_ITEM", {})
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
            console.log("Błąd")
            return;
          }

          navigate("ADD_ITEM", { photoPath: photo.uri })
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