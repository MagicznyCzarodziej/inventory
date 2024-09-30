import RNPickerSelect from 'react-native-picker-select';
import Theme, { Colors } from '../app/Theme';
import React from 'react';

interface Props {
  value?: string;
  onValueChange: (value: string) => void;
  items: { value: string, label: string }[];
}

export const Select = (props: Props) => {
  const { value, onValueChange, items } = props;

  return <RNPickerSelect
    value={value}
    onValueChange={onValueChange}
    items={items}
    placeholder={{}}
    style={{
      viewContainer: {
        borderStyle: "solid",
        borderWidth: 1,
        borderColor: Colors.input.outline,
        borderRadius: Theme.shapes.inputRadius,
        backgroundColor: Colors.background,
        marginBottom: 18
      },
      inputAndroid: {
        color: Colors.text.main,
      },
      inputIOS: {
        color: Colors.text.main,
      }
    }}
  />
}