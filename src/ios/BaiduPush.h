#import <Cordova/CDVPlugin.h>

@interface BaiduPush : CDVPlugin

+ (void) launchOptions:(NSDictionary*)launchOptions;

+ (void) notify:(NSDictionary *)userInfo active:(BOOL)active;

- (void) bind:(CDVInvokedUrlCommand*)command;

@end
