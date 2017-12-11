# cordova-plugin-baidu-push

百度推送 Cordova 插件

## 安装

    $ cordova plugin add https://github.com/SyaOS/cordova-plugin-baidu-push.git --variable BAIDU_PUSH_ANDROID_API_KEY= --variable BAIDU_PUSH_IOS_API_KEY=

## 使用

```javascript
window.BaiduPush.bind(function (notify, active) {
    /* 推送回调，有可能先于绑定回调调用 */
    /* active 为 true 时为 iOS 前台推送自动回调 */
}, function (error, result) {
    /* 绑定回调 */
});
```

## 备注

- iOS 目前无论采用 debug 构建还是 release 构建，均推送到生产环境。
