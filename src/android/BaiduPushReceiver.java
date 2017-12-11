package cn.org.sya.plugins;

import java.util.List;

import android.content.Context;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.android.pushservice.PushMessageReceiver;

public class BaiduPushReceiver extends PushMessageReceiver {
    static CallbackContext bindCallbackContext = null;
    static CordovaWebView webView = null;

    @Override
    public void onBind(Context context, int errorCode, String appId, String userId, String channelId, String requestId) {
        if (errorCode == 0) {
            try {
                JSONObject message = new JSONObject();
                message.put("appId", appId);
                message.put("userId", userId);
                message.put("channelId", channelId);
                message.put("requestId", requestId);
                bindCallbackContext.success(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            bindCallbackContext.error(errorCode);
        }
        bindCallbackContext = null;
    }

    @Override
    public void onNotificationArrived(Context context, String title, String description, String customContentString) {}

    @Override
    public void onNotificationClicked(Context context, String title, String description, String customContentString) {
        String format = "void (typeof BaiduPush.notify === 'function' && BaiduPush.notify(%s, %s));";
        webView.sendJavascript(String.format(format, customContentString, "false"));
    }

    @Override
    public void onMessage(Context context, String message, String customContentString) {}

    @Override
    public void onListTags(Context context, int errorCode, List<String> tags, String requestId) {}

    @Override
    public void onDelTags(Context context, int errorCode, List<String> successTags, List<String> failTags, String requestId) {}

    @Override
    public void onSetTags(Context context, int errorCode, List<String> successTags, List<String> failTags, String requestId) {}

    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {}
}
