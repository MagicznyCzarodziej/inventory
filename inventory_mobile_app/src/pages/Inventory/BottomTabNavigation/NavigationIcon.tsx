import { View } from 'react-native';
import { Colors } from '../../../app/Theme';
import { Icon } from 'react-native-paper';
import React from 'react';

interface Props {
  icon: string;
  isFocused: boolean;
}

export const NavigationIcon = (props: Props) => {
  const { icon, isFocused } = props

  if (isFocused) {
    return <View style={{
      backgroundColor: Colors.primary,
      padding: 2,
    }}>
      <Icon size={18} source={icon} color={"#000000"} />
    </View>
  }

  return <Icon size={18} source={icon} color={Colors.white} />
}