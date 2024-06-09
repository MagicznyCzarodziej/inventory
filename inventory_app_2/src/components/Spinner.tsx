import { ActivityIndicator } from 'react-native';
import { Colors } from '../app/Theme';

export const Spinner = () => {
  return <ActivityIndicator size={64} color={Colors.primary} />;
};
