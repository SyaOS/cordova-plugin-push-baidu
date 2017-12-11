package cn.org.sya.plugins;

import android.content.Context;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushSettings;

public class BaiduPush extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("bind")) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    Context context = cordova.getActivity().getApplicationContext();
                    String apiKey = preferences.getString("BAIDU_PUSH_ANDROID_API_KEY", null);
                    PushSettings.enableDebugMode(context, true);
                    PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY, apiKey);
                }
            });
            BaiduPushReceiver.bindCallbackContext = callbackContext;
            BaiduPushReceiver.webView = webView;
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        BaiduPushReceiver.bindCallbackContext = null;
        BaiduPushReceiver.webView = null;
    }
}
