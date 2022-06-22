//
//  AuthenticationViewController.h
//  SendOTPFramework
//
//  Created by Hussain Chhatriwala on 05/01/16.
//  Copyright © 2016 Walkover. All rights reserved.
//

#import <UIKit/UIKit.h>
@protocol SendOTPAuthenticationViewControllerDelegate
-(void)canceledAuthentication;
-(void)authenticationisSuccessfulForMobileNumber:(NSString*)mobNo withCountryCode:(NSString*)countryCode;
-(void)authenticationisFailedForMobileNumber:(NSString*)mobNo withCountryCode:(NSString*)countryCode;
@end
@interface AuthenticationViewController : UIViewController
@property (strong, nonatomic) UIColor *navBarColor;
@property (strong, nonatomic) UIColor *navBarTitleColor;
@property (strong, nonatomic) NSString * authkey;
@property (strong, nonatomic) NSString * customMessage;

@property (nonatomic, assign) BOOL accessTypeShouldBeHTTP;
@property (strong, nonatomic) NSString * mobileNumber;
@property (strong, nonatomic) UIImage *companyImage;
@property (strong, nonatomic) NSString * senderId;
@property (strong, nonatomic) id <SendOTPAuthenticationViewControllerDelegate> delegate;
@end
