import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { TextInput } from 'react-native-paper';
import { Colors } from '../app/Theme';

interface Props {
  label: string;
  value?: string;
  right?: React.ReactNode
  onChangeText: (text: string) => void;
  secureTextEntry?: boolean;
  error?: string;
}

export const TextField = (props: Props) => {
  const { label, value, right, onChangeText, secureTextEntry, error } = props;
  return (
    <View>
      <TextInput
        activeOutlineColor={Colors.secondary}
        outlineColor={Colors.input.outline}
        mode="outlined"
        label={label}
        right={right}
        value={value}
        onChangeText={onChangeText}
        secureTextEntry={secureTextEntry}
        error={!!error}
      />
      <Text style={styles.error}>{error}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  error: {
    marginTop: 4,
    color: Colors.text.error,
  }
});