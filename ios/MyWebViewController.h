#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import <WebKit/WebKit.h>

NS_ASSUME_NONNULL_BEGIN

typedef void (^CompletionHandler)(BOOL success, NSString * _Nullable callbackURL);

@interface MyWebViewController : UIViewController <WKNavigationDelegate>

@property (nonatomic, strong) WKWebView *webView;
@property (nonatomic, copy) NSString *urlString;
@property (nonatomic, copy) NSString *redirectUri;
@property (nonatomic, copy) CompletionHandler completionHandler;
@property (nonatomic, strong) UIActivityIndicatorView *activityIndicator;

@end

NS_ASSUME_NONNULL_END
