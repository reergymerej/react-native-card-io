import React, { Component, PropTypes } from 'react';
import {
  Platform,
  requireNativeComponent,
  NativeAppEventEmitter,
  View,
  Text,
} from 'react-native';

class CardIOView extends Component {
  displayName = 'CardIOView';

  statics = {
    cardImageAndNumber: 'cardImageAndNumber',
    cardImageOnly: 'cardImageOnly',
    automatic: 'automatic',
  };

  defaultProps = {
    didScanCard: () => {},
  };

  componentWillMount() {
    this._listener = NativeAppEventEmitter.addListener('didScanCard', this.props.didScanCard);
  }

  componentWillUnmount() {
    if (this._listener) this._listener.remove();
  }

  render() {
    return <RCTCardIOView {...this.props} />;
  }

  _listener = null;
}

CardIOView.propTypes = {
    languageOrLocale: PropTypes.oneOf([
      'ar','da','de','en','en_AU','en_GB','es','es_MX','fr','he','is','it','ja','ko','ms',
      'nb','nl','pl','pt','pt_BR','ru','sv','th','tr','zh-Hans','zh-Hant','zh-Hant_TW',
    ]),
    guideColor: PropTypes.string,
    useCardIOLogo: PropTypes.bool,
    hideCardIOLogo: PropTypes.bool,
    allowFreelyRotatingCardGuide: PropTypes.bool,
    scanInstructions: PropTypes.string,
    scanOverlayView: PropTypes.element,
    scanExpiry: PropTypes.bool,
    scannedImageDuration: PropTypes.number,
    detectionMode: PropTypes.oneOf([
      'cardImageAndNumber',
      'cardImageOnly',
      'automatic',
    ]),
    didScanCard: PropTypes.func,
    hidden: PropTypes.bool,
};

class RCTCardIOViewAndroid extends Component {
  render() {
    return (
      <View>
      <Text>RCTCardIOViewAndroid stub</Text>
      </View>
    );
  }
}

const RCTCardIOView = Platform.OS === 'ios'
  ? requireNativeComponent('RCTCardIOView', CardIOView)
  : RCTCardIOViewAndroid;

export default CardIOView;
