import { RootStackParamList } from './src/app/Root';

declare global {
  namespace ReactNavigation {
    interface RootParamList extends RootStackParamList {}
  }
}