import { Text } from 'react-native';
import { Colors } from '../../../app/Theme';
import React from 'react';

interface lProps {
  focused: boolean;
  children: string
}

export const NavigationLabel = (props: lProps) => {
  const { focused, children } = props;
  return <Text style={{
    color: Colors.white,
    fontSize: 14,
    marginBottom: 8,
    marginTop: -8,
    fontWeight: focused ? "bold" : "normal",
  }}>
    {children}
  </Text>;
}