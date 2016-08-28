package com.rncardio;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

/**
 * Created by jeremygreer on 8/26/16.
 */
public class RNCardIOModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private static final int SCAN_REQUEST_CODE = 9876;

    private Callback _callback = null;

    public RNCardIOModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "RNCardIO";
    }

    @ReactMethod
    public void scan(final Callback callback) {
        Activity currentActivity = this.getReactApplicationContext().getCurrentActivity();
        _callback = callback;

        try {
            Intent scanIntent = new Intent(currentActivity, CardIOActivity.class);

            scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true);
            scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false);
            scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false);

            if (currentActivity != null) {
                currentActivity.startActivityForResult(scanIntent, SCAN_REQUEST_CODE);
            }
        } catch (Exception e) {
            this.sendResult("sorry, it didn't work" + e);
        }
    }

    private void sendResult(String result) {
        if (this._callback != null) {
            this._callback.invoke(result);
            this._callback = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            } else {
                resultDisplayStr = "Scan was canceled.";
            }

            this.sendResult(resultDisplayStr);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
