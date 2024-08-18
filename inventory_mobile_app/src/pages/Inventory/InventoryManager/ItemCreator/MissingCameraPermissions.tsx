import { StyleSheet, View } from 'react-native';
import { Text } from 'react-native-paper';
import { Button } from '../../../../components/Button';
import { Page } from '../../../../layouts/Page';
import React from 'react';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { InventoryStackParamList } from '../../../../navigation/navigationTypes';

export const MissingCameraPermissions = () => {
  const { navigate } = useNavigation<NavigationProp<InventoryStackParamList>>()

  return <Page safeArea={false}>
    <View style={styles.cameraPermissions}>
      <Text style={styles.cameraPermissionsHint}>Zezwól na używanie aparatu w ustawieniach aplikacji</Text>
      <Button
        onPress={() => {
          navigate({
            name: "ADD_ITEM",
            params: {},
            merge: true,
          })
        }}
        title="Wróć" />
    </View>
  </Page>
}

const styles = StyleSheet.create({
  cameraPermissions: {
    padding: 32,
    flexGrow: 1,
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    gap: 16,
  },
  cameraPermissionsHint: {
    fontSize: 20,
    textAlign: "center",
  },
})