//
//  Constant.swift
//  Arrive5
//
//  Created by Joy on 06/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import Foundation
import UIKit

class Constant: NSObject {
    
    
    struct USRData {
        
    }
    
    struct API {
        
        //static let kBaseUrl         = "http://arrive5.pcthepro.com/api/"
//        static let kBaseUrl         = "http://arrive5.pcthepro.com/webservice/"
//        static let kBaseUrl2         = "http://arrive5.pcthepro.com/webservice/"
//        static let kSecBaseUrl      = "http://arrive5.pcthepro.com/webservice/"
        
        
        static let kBaseUrl         = "https://maestrosinfotech.com/arrive/webservice/"
        static let kBaseUrl2         = "https://maestrosinfotech.com/arrive/webservice/"
        static let kSecBaseUrl      = "https://maestrosinfotech.com/arrive/webservice/"

        
        
        static let kLogin           = "\(kBaseUrl)user/login"
           static let profilelistss           = "\(kBaseUrl)user/businessProfileList"
        
        static let kVerify          = "\(kBaseUrl)user/verify_mobile"
        static let kChange_Password = "\(kBaseUrl)user/change_password"
        static let kAddProfile      = "\(kBaseUrl)user/signup"
       // static let kVehicleType     = "\(kBaseUrl)getVehcledetails"
        static let kVehicleType     = "\(kBaseUrl)Booking/getVehcletollpricedetails"

        static let kDriverSearch    = "\(kBaseUrl)user/driversearch"
         static let reportreasonlist    = "\(kBaseUrl)user/reportReasonList"
        static let submitreason    = "\(kBaseUrl)user/submitReason"
        static let kBookingVal      = "\(kSecBaseUrl)Booking/booking"
        
        static let bookingdetail      = "\(kSecBaseUrl)Booking/booking_detail"

        
       // static let kLatLongUpdate   = "\(kBaseUrl)locationUpdate"
        static let kNotification    = "\(kBaseUrl2)Booking/notification_list"
         static let lasttripe    = "\(kBaseUrl2)Booking/user_last_trip"
        static let kPastRide        = "\(kBaseUrl2)Booking/booking_list"
        static let kCancelRide      = "\(kBaseUrl2)Booking/cancel_booking"
        static let KScheduleLater   = "\(kBaseUrl2)Booking/schedule_later_booking"
        static let KPromo           =        "\(kBaseUrl2)Booking/checkPromo"
        static let KDonate          =        "\(kBaseUrl2)Ngo/ngoDonation"
        static let KReviewList      =  "\(kBaseUrl2)" + "Booking/reviewList"
        static let kBookedDriverList =  "\(kBaseUrl2)" + "user/bookedDriverList"
        static let KEditUserPofile  =  "\(kBaseUrl2)" + "/user/editUserPofile"
        
        
        static let KEstimatePrice  =  "\(kBaseUrl2)" + "Booking/fare_estimation"
        static let kVehicleDetails     = "\(kBaseUrl2)" + "Booking/getVehcledetails"
        static let kPromoCode            = "\(kBaseUrl2)" + "User/promoCode"
        static let kcheckPromoCode            = "\(kBaseUrl2)" + "User/Promo_code_check"

        
        //        static let kBaseUrl2         = "http://arrive5.pcthepro.com/webservice/"

        //http://arrive5.pcthepro.com/webservice/User/Promo_code_check
        //http://arrive5.pcthepro.com/webservice/User/promoCode
        //http://arrive5.pcthepro.com/webservice/Booking/
//fare_estimation
        /*
         http://arrive5.pcthepro.com/webservice/Booking/getVehcledetails
         */
    }
    
    struct DeviceType {
        
        static let IS_IPHONE_4_OR_LESS  = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH <= 568.0
        static let IS_IPHONE_5          = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH == 568.0
        static let IS_IPHONE_6_7        = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH == 667.0
        static let IS_IPHONE_6P_7P      = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH == 736.0
        static let IS_IPAD              = UIDevice.current.userInterfaceIdiom == .pad && ScreenSize.SCREEN_MAX_LENGTH == 1024.0
        static let IS_IPAD_PRO          = UIDevice.current.userInterfaceIdiom == .pad && ScreenSize.SCREEN_MAX_LENGTH == 1366.0
    }
    
    struct ScreenSize {
        
        static let kHeight  = UIScreen.main.bounds.height
        static let kWidth   = UIScreen.main.bounds.width
        static let SCREEN_MAX_LENGTH    = max(ScreenSize.kWidth, ScreenSize.kHeight)
        static let SCREEN_MIN_LENGTH    = min(ScreenSize.kWidth, ScreenSize.kHeight)
    }
    
}
