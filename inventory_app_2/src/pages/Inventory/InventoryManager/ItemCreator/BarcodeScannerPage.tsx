import { BarcodeType, CameraView, useCameraPermissions } from 'expo-camera';
import React, { useEffect, useState } from 'react';
import { Page } from '../../../../layouts/Page';
import { Icon, IconButton, Text } from 'react-native-paper';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { InventoryStackParamList } from '../../InventoryList/InventoryNavigation';
import { Colors } from '../../../../app/Theme';
import { StyleSheet, View } from 'react-native';
import { MissingCameraPermissions } from './MissingCameraPermissions';

const BARCODE_TYPES: BarcodeType[] = ['upc_a', 'upc_e', 'ean8', 'ean13'];

export const BarcodeScannerPage = () => {
  const [cameraPermission, requestPermission] = useCameraPermissions();
  const { navigate } = useNavigation<NavigationProp<InventoryStackParamList>>()

  const [barcode, setBarcode] = useState<string | undefined>()
  const [isTorchEnabled, setIsTorchEnabled] = useState(true)

  useEffect(() => {
    requestPermission()
  }, []);

  if (!cameraPermission?.granted) {
    return <MissingCameraPermissions />
  }

  return <Page safeArea={false}>
    <View style={styles.barcodeContainer}>
      <Icon source="barcode" size={28} />
      <Text style={styles.barcode}>{barcode}</Text>
    </View>
    <CameraView
      enableTorch={isTorchEnabled}
      style={styles.cameraView}
      barcodeScannerSettings={{
        barcodeTypes: BARCODE_TYPES,
      }}
      facing="back"
      onBarcodeScanned={(scanningResult) => {
        setBarcode(scanningResult.data)
      }}
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
        onPress={() => {
          navigate("ADD_ITEM", { barcode })
        }}
      />
    </View>
  </Page>
}

const styles = StyleSheet.create({
  barcodeContainer: {
    backgroundColor: Colors.primary,
    display: "flex",
    flexDirection: "row",
    justifyContent: "center",
    gap: 8,
    alignItems: "center",
    paddingBottom: 16,
    paddingTop: 56,
    elevation: 20,
    zIndex: 1,
  },
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
  barcode: {
    fontSize: 20,
    textAlign: "center",
  }
})