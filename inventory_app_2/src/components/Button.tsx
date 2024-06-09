import React from "react";
import { ActivityIndicator, Pressable, StyleSheet, Text } from "react-native";
import { Colors } from '../app/Theme';

interface Props {
  onPress: () => undefined;
  title: string;
  spinner?: boolean
  variant?: 'filled' | 'text'
  small?: boolean
  fullWidth?: boolean
}

export const Button = (props: Props) => {
  const { onPress, title, spinner, variant = 'filled', small = false, fullWidth = false } = props;
  return (
    <Pressable
      style={{
        ...styles.button,
        ...(small ? styles.button_small : {}),
        ...(fullWidth ? { flexGrow: 1 } : {}),
        ...styles[`variant_${variant}`],
      }}
      onPress={onPress}
    >
      {
        spinner
          ? <ActivityIndicator color={Colors.text.main} />
          : <Text style={{
            ...styles.text,
            ...(small ? styles.text_small : {}),
          }}>{title}</Text>
      }
    </Pressable>
  );
}

const styles = StyleSheet.create({
  button: {
    alignItems: 'center',
    justifyContent: 'center',
    paddingVertical: 12,
    paddingHorizontal: 32,
    height: 50,
  },
  button_small: {
    height: 40,
  },
  variant_filled: {
    elevation: 1,
    backgroundColor: Colors.primary,
  },
  variant_text: {},
  text: {
    fontSize: 20,
    fontWeight: "500",
    textTransform: "uppercase",
    color: Colors.text.main,
  },
  text_small: {
    fontSize: 12,
  },
});