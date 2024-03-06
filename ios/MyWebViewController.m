//
//  MyWebViewController.m
//  AzureAuth
//
//  Created by Sakshya Arora on 04/03/24.
//  Copyright © 2024 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MyWebViewController.h"

@interface MyWebViewController () <WKNavigationDelegate>

@end

@implementation MyWebViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.webView = [[WKWebView alloc] initWithFrame:self.view.bounds];
    self.webView.navigationDelegate = self;
    [self.view addSubview:self.webView];
    [self loadUrl];
}

- (void)loadUrl {
    if (self.urlString) {
        NSURL *url = [NSURL URLWithString:self.urlString];
        NSURLRequest *request = [NSURLRequest requestWithURL:url];
        [self.webView loadRequest:request];
    }
}

- (void)webView:(WKWebView *)webView didFinishNavigation:(WKNavigation *)navigation {
    // Handle page loaded event
}

- (void)webView:(WKWebView *)webView didFailNavigation:(WKNavigation *)navigation withError:(NSError *)error {
    // Handle page load failure
}

- (void)webView:(WKWebView *)webView decidePolicyForNavigationAction:(WKNavigationAction *)navigationAction decisionHandler:(void (^)(WKNavigationActionPolicy))decisionHandler {
    NSURL *url = navigationAction.request.URL;
    NSString *prefix = @""; // Check if the URL matches the redirect URL //Change the url based on your
                                        // requirement
    if ([url.absoluteString hasPrefix: prefix]) {
        if (self.completionHandler) {
            self.completionHandler(YES, url.absoluteString);
        }
        decisionHandler(WKNavigationActionPolicyCancel);
        return;
    }
    
    decisionHandler(WKNavigationActionPolicyAllow);
}

@end
