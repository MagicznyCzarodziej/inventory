import { PropsWithChildren } from 'react';
import { SafeAreaView } from 'react-native-safe-area-context';
import { View, StyleSheet } from 'react-native';
import { Colors } from '../app/Theme';
import { ViewStyle } from 'react-native/Libraries/StyleSheet/StyleSheetTypes';

interface Props extends PropsWithChildren {
  safeArea?: boolean;
  style?: ViewStyle;
}

export const Page = (props: Props) => {
  const { safeArea = true, children, style = {} } = props

  if (safeArea) {
    return <View style={styles.view}>
      <SafeAreaView style={{ ...styles.safeAreaView, ...style }}>
        {children}
      </SafeAreaView>
    </View>
  }

  return <View style={{ ...styles.view, ...style }}>{children}</View>
}

const styles = StyleSheet.create({
  view: {
    backgroundColor: Colors.background,
    height: "100%",
  },
  safeAreaView: {
    marginTop: 8,
    flexGrow: 1,
  },
})