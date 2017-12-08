#import <Cordova/CDVPlugin.h>
#import "BaiduPush.h"
#import "BPush.h"

@implementation BaiduPush

static NSDictionary* _launchOptions = nil;
static BaiduPush* _instance = nil;
static NSDictionary* _userInfo = nil;

+ (void) launchOptions:(NSDictionary *)launchOptions {
    _launchOptions = launchOptions;
}

+ (void) notify:(NSDictionary *)userInfo active:(BOOL)active {
    if (_instance != nil) {
        [_instance notify:userInfo active:active];
    } else {
        if (_userInfo != nil) NSLog(@"BaiduPush: _userInfo is not empty.");
        _userInfo = userInfo;
    }
}

- (void) pluginInitialize {
    NSDictionary* settings = [self.commandDelegate settings];
    NSString* apiKey = [settings objectForKey:@"baidu_push_ios_api_key"];

    [BPush registerChannel:_launchOptions
                    apiKey:apiKey
                  pushMode:BPushModeProduction
           withFirstAction:nil
          withSecondAction:nil
              withCategory:nil
      useBehaviorTextInput:NO
#ifdef DEBUG
                   isDebug:YES];
#else
                   isDebug:NO];
#endif
    [BPush disableLbs];
    _launchOptions = nil;

    if (_instance != nil) NSLog(@"BaiduPush: _instance is not empty.");
    _instance = self;
}

- (void) dispose {
    _launchOptions = nil;
    _instance = nil;
    _userInfo = nil;
}

- (void) bind:(CDVInvokedUrlCommand *)command {
    [BPush bindChannelWithCompleteHandler:^(id result, NSError *error) {
        CDVPluginResult* pluginResult = nil;
        if (error) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR
                                          messageAsNSInteger:[error code]];
        } else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                                         messageAsDictionary:result];
        }
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
    if (_userInfo != nil) {
        [self notify:_userInfo active:NO];
        _userInfo = nil;
    }
}

- (void) notify:(NSDictionary *)userInfo active:(BOOL)active {
    NSError* error = nil;
    NSData* data = [NSJSONSerialization dataWithJSONObject:userInfo options:0 error:&error];

    if (error != nil) {
        NSLog(@"BaiduPush: userInfo serialized failed(%@)", error);
        return;
    }

    NSString* format = @"void (typeof BaiduPush.notify === 'function' && BaiduPush.notify(%@, %@));";
    NSString* javascript = [NSString stringWithFormat:format,
                            [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding],
                            active ? @"true" : @"false"];
    [[self commandDelegate] evalJs:javascript];
}

@end
