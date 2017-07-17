/*
 * Copyright (c)
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package io.priyankpatel.rnelm327;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ReactNativeELM327Module extends ReactContextBaseJavaModule {
  private final static String TAG = "ReactNativeELM327Module";
  private ELM327Handler elm327Handler = null;
  private ReactContext mReactContext = null;
  private Arguments mArguments;

  public ReactNativeELM327Module(ReactApplicationContext reactContext) {
    super(reactContext);
    mReactContext = reactContext;
  }

  @Override
  public String getName() {
    return "ReactNativeELM327";
  }


  @ReactMethod
  public void ready() {
    if (elm327Handler == null) {
      elm327Handler = new ELM327Handler(mReactContext);
    }

    mOBD2Handler.ready();
  }

  @ReactMethod
  public void getBluetoothDeviceName(Promise aPromise) {
    if (elm327Handler == null) {
      elm327Handler = new ELM327Handler(mReactContext);
    }

    try {
      Set<BluetoothDevice> pairedDevices = elm327Handler.getBondedDevices();
      WritableArray deviceList = mArguments.createArray();
      if (pairedDevices.size() > 0) {
        for (BluetoothDevice device : pairedDevices) {
          WritableMap map = mArguments.createMap();
          map.putString("name", device.getName());
          map.putString("address", device.getAddress());
          deviceList.pushMap(map);
        }
      }

      aPromise.resolve(deviceList);
    } catch (IOException e) {
      e.printStackTrace();
      aPromise.reject(TAG, e);
    }
  }

  @ReactMethod
  public void setMockUpMode(boolean enabled) {
    if (elm327Handler == null) {
      elm327Handler = new ELM327Handler(mReactContext);
    }

    elm327Handler.setMockUpMode(enabled);
  }

  @ReactMethod
  public void setRemoteDeviceAddress(String remoteDeviceAddress) {
     if (elm327Handler == null) {
      elm327Handler = new ELM327Handler(mReactContext);
    }

    elm327Handler.setRemoteDeviceName(remoteDeviceAddress);
  }

  @ReactMethod
  public void startLiveData() {
    if (elm327Handler == null) {
      elm327Handler = new ELM327Handler(mReactContext);
    }

    elm327Handler.startLiveData();
  }

  @ReactMethod
  public void stopLiveData() {
    if (elm327Handler == null) {
      elm327Handler = new ELM327Handler(mReactContext);
    }

    elm327Handler.stopLiveData();
  }
}