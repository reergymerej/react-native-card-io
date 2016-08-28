package com.rncardio;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by jeremygreer on 8/26/16.
 */
public class RNCardIOUtilitiesModule extends ReactContextBaseJavaModule {
    public RNCardIOUtilitiesModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNCardIOUtilities";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("canReadCardWithCamera", true);
        return constants;
    }
}
