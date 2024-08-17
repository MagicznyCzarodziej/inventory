import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { Icon } from 'react-native-paper';
import { Colors } from '../../../app/Theme';

interface Props {
  barcode: string | null;
}

export const ItemPreviewBarcode = (props: Props) => {
  const { barcode } = props;

  if (barcode === null) {
    return null
  }

  return (
    <View style={styles.wrapper}>
      <Icon source="barcode" size={28} />
      <Text>{barcode}</Text>
      <Text style={styles.compare}>Porównaj</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  wrapper: {
    display: 'flex',
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    gap: 8,
  },
  compare: {
    color: Colors.accent,
    marginLeft: 8
  }
})