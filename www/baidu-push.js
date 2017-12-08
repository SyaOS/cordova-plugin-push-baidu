/**
 * Author: Samily Wang
 * Create-Date: 2015-06-24
 * Description: the Js part of BaiduPush plugin
 */
var exec = require('cordova/exec');

exports.bind = function (notify, callback) {
  exports.notify = notify
  exec(function (result) {
    if (typeof callback === 'function') callback(null, result)
  }, function (error) {
    if (typeof callback === 'function') callback(error)
  }, 'BaiduPush', 'bind');
};
