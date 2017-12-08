# cordova-plugin-baidu-push

百度推送 Cordova 插件

## 安装

    $ cordova plugin add https://github.com/SyaOS/cordova-plugin-baidu-push.git

## 使用

```javascript
window.BaiduPush.bind(function (notify) {
    /* 推送回调，有可能先于回调调用 */
}, function (error, result) {
    /* 绑定回调 */
});
```

## 备注

- 目前无论采用 debug 构建还是 release 构建，均推送到生产环境。
