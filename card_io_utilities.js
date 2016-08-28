import { Platform, NativeModules } from 'react-native';
const { CardIOUtilities } = NativeModules;
const CardIOUtilitiesAndroid = {
  preload() {},
};

export default Platform.OS === 'ios'
  ? CardIOUtilities
  : CardIOUtilitiesAndroid;
