import { ActivityIndicator, StyleProp } from 'react-native';
import { Colors } from '../app/Theme';
import { ViewStyle } from 'react-native/Libraries/StyleSheet/StyleSheetTypes';

interface Props {
  style?: StyleProp<ViewStyle>
}

export const Spinner = (props: Props) => {
  const { style } = props;

  return <ActivityIndicator style={style} size={64} color={Colors.primary} />;
};
