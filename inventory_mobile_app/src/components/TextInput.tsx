import React, { ForwardedRef, forwardRef, ReactNode } from "react";
import { StyleSheet, Text, View } from "react-native";
import { TextInput } from 'react-native-paper';
import { TextInput as NativeTextInput } from 'react-native';
import Theme, { Colors } from '../app/Theme';

interface Props {
  label: string;
  value?: string;
  right?: ReactNode
  onChangeText?: (text: string) => void;
  secureTextEntry?: boolean;
  error?: string;
  autofocus?: boolean;
  nextTextFieldRef?: any;
  autoCapitalize?: 'none' | 'sentences' | 'words' | 'characters' | undefined;
  disabled?: boolean;
}

export const TextField = forwardRef((props: Props, ref: ForwardedRef<NativeTextInput>) => {
  const {
    label,
    value,
    right,
    onChangeText,
    secureTextEntry,
    error,
    autofocus = false,
    nextTextFieldRef,
    autoCapitalize,
    disabled = false,
  } = props;

  const handleFocusToNextField = () => {
    if (!nextTextFieldRef?.current) {
      return;
    }
    nextTextFieldRef.current.focus();
  }

  return (
    <View>
      <TextInput
        autoCapitalize={autoCapitalize}
        activeOutlineColor={Colors.primary}
        outlineColor={Colors.input.outline}
        outlineStyle={{
          borderRadius: Theme.shapes.inputRadius,
        }}
        style={{ backgroundColor: Colors.background }}
        textColor={Colors.text.main}
        theme={{
          colors: {
            onSurfaceVariant: Colors.gray.light // Label text color
          }
        }}
        mode="outlined"
        label={label}
        right={right}
        value={value}
        onChangeText={onChangeText}
        secureTextEntry={secureTextEntry}
        error={!!error}
        autoFocus={autofocus}
        ref={ref}
        returnKeyType={nextTextFieldRef ? "next" : 'default'}
        blurOnSubmit={!nextTextFieldRef} // Prevent hiding keyboard when refocusing between fields
        onSubmitEditing={handleFocusToNextField}
        disabled={disabled}
      />
      <Text style={styles.error}>{error}</Text>
    </View>
  );
});

const styles = StyleSheet.create({
  error: {
    marginTop: 4,
    color: Colors.text.error,
  }
});