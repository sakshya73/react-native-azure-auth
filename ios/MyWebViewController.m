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
       self.activityIndicator = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
       self.activityIndicator.center = self.view.center;
       self.activityIndicator.transform = CGAffineTransformMakeScale(2.0, 2.0);
       self.activityIndicator.hidesWhenStopped = YES;
       [self.view addSubview:self.activityIndicator];
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
    [self.activityIndicator stopAnimating];
    // Handle page loaded event
}

- (void)webView:(WKWebView *)webView didStartProvisionalNavigation:(WKNavigation *)navigation {
    [self.activityIndicator startAnimating];
}

- (void)webView:(WKWebView *)webView didFailNavigation:(WKNavigation *)navigation withError:(NSError *)error {
    // Handle page load failure
}

- (void)webView:(WKWebView *)webView decidePolicyForNavigationAction:(WKNavigationAction *)navigationAction decisionHandler:(void (^)(WKNavigationActionPolicy))decisionHandler {
    NSURL *url = navigationAction.request.URL;
    if ([url.absoluteString hasPrefix:self.redirectUri] || [url.absoluteString hasSuffix:@"/home"]) {
        if (self.completionHandler) {
            self.completionHandler(YES, url.absoluteString);
        }
        decisionHandler(WKNavigationActionPolicyCancel);
        return;
    }
    
    decisionHandler(WKNavigationActionPolicyAllow);
}

@end
