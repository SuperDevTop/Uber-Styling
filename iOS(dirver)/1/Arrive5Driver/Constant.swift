//
//  Constant.swift
//  Arrive5Driver
//
//  Created by Joy on 09/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import Foundation
import UIKit

class Constant: NSObject {
    
    
    struct USRData {
        
    }
    
    struct API {
        
//        static let kBaseUrl            = "http://arrive5.pcthepro.com/api/"
//        static let kSecBaseUrl         = "http://arrive5.pcthepro.com/webservice/"
//        static let kLogin              = "\(kBaseUrl)driver_login"
//        static let kVerify             = "\(kBaseUrl)driverVerify_mobile"
//        static let kChange_Password    = "\(kBaseUrl)driverChange_password"
//        static let kAddProfile         = "\(kBaseUrl)driverSignup"
//        static let kProfileUpdate      = "\(kBaseUrl)updateDriverinfo"
//        static let kGetColor           = "\(kBaseUrl)getColor"
//        static let kGetModal           = "\(kBaseUrl)getModal"
//        static let kGetVehicletype     = "\(kBaseUrl)getVehicletype"
//        static let kGetVehiclesubtype  = "\(kBaseUrl)getVehiclesubtype?typeid="
//        static let kUpdateDriverInfo   = "\(kBaseUrl)updateDriverVechileinfo"
//        static let kUpdateDriverLat    = "\(kSecBaseUrl)User/update_driver_lat_long"
//        static let kUpdateOnlineStatus = "\(kSecBaseUrl)User/update_driver_online_status"
//        static let kAcceptReject       = "\(kSecBaseUrl)Booking/accept_reject"
//        static let kCancelReason       = "\(kSecBaseUrl)Booking/get_cancel_reason"
//        static let kgetRatingComment   = "\(kSecBaseUrl)Booking/get_rating_comment"
//        static let kArrived            = "\(kSecBaseUrl)Booking/arrived"
//        static let kFinishRide         = "\(kSecBaseUrl)Booking/finish_ride"
//        static let kOtpVerification    = "\(kSecBaseUrl)Booking/otp_verfication"
//        static let kCancelBooking      = "\(kSecBaseUrl)Booking/cancel_booking"
//        static let kBillingDetail      = "\(kSecBaseUrl)Booking/rate"
//        static let kOurServices        = "\(kSecBaseUrl)" + "Booking/ourServices"
//        static let kReportReasonList   = "\(kSecBaseUrl)" + "/user/reportReasonList"
//        static let kSubmitReason       = "\(kSecBaseUrl)" + "/user/submitReason"
//        static let KEditDriverPofile   = "\(kSecBaseUrl)" + "/user/editDriverPofile"
//        static let KDriverEarningFilter   = "\(kSecBaseUrl)" + "user/driverEarningFilter"
//        static let KDriverEarning   = "\(kSecBaseUrl)" + "user/driverEarning"

//        static let KReviewList      =  "\(kBaseUrl2)" + "Booking/reviewList"


        
       
        static let kBaseUrl            = "https://maestrosinfotech.com/arrive/webservice/"
        static let kSecBaseUrl         = "https://maestrosinfotech.com/arrive/webservice/"
        static let kLogin              = "\(kBaseUrl)user/driver_login"
        static let kVerify             = "\(kBaseUrl)user/driverVerify_mobile"
        static let kChange_Password    = "\(kBaseUrl)user/driverChange_password"
        static let kAddProfile         = "\(kBaseUrl)user/driverSignup"
        static let kProfileUpdate      = "\(kBaseUrl)user/updateDriverinfo"
        static let kGetColor           = "\(kBaseUrl)user/getColor"
         static let notification           = "\(kBaseUrl)user/notifacationToDriver"
        static let kGetModal           = "\(kBaseUrl)user/getModal"
        static let kGetVehicletype     = "\(kBaseUrl)user/getVehicletype"
        static let kGetVehiclesubtype  = "\(kBaseUrl)user/getVehiclesubtype?typeid="
        static let kUpdateDriverInfo   = "\(kBaseUrl)user/updateDriverVechileinfo"
        static let kUpdateDriverLat    = "\(kSecBaseUrl)User/update_driver_lat_long"
        static let kUpdateOnlineStatus = "\(kSecBaseUrl)User/update_driver_online_status"
        static let kAcceptReject       = "\(kSecBaseUrl)Booking/accept_reject"
        static let Availablepickup       = "\(kSecBaseUrl)Booking/getSchedulePickupList"
        static let kCancelReason       = "\(kSecBaseUrl)Booking/get_cancel_reason"
        static let kgetRatingComment   = "\(kSecBaseUrl)Booking/get_rating_comment"
        static let kArrived            = "\(kSecBaseUrl)Booking/arrived"
        static let kFinishRide         = "\(kSecBaseUrl)Booking/finish_ride"
        static let removepicku         = "\(kSecBaseUrl)Booking/removeFromMyPickup"
        static let kOtpVerification    = "\(kSecBaseUrl)Booking/otp_verfication"
         static let addmypickups    = "\(kSecBaseUrl)Booking/addToMyPickup"
        static let kCancelBooking      = "\(kSecBaseUrl)Booking/cancel_booking"
        static let kBillingDetail      = "\(kSecBaseUrl)Booking/rate"
        static let kOurServices        = "\(kSecBaseUrl)" + "Booking/ourServices"
        static let kReportReasonList   = "\(kSecBaseUrl)" + "/user/reportReasonList"
        static let kSubmitReason       = "\(kSecBaseUrl)" + "/user/submitReason"
        static let KEditDriverPofile   = "\(kSecBaseUrl)" + "/user/editDriverPofile"
        static let KDriverEarningFilter   = "\(kSecBaseUrl)" + "user/driverEarningFilter"
        static let KDriverEarning   = "\(kSecBaseUrl)" + "user/driverEarning"



        
    }
    
    struct DeviceType {
        
        static let IS_IPHONE_4_OR_LESS  = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH <= 568.0
        static let IS_IPHONE_5          = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH == 568.0
        static let IS_IPHONE_6_7          = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH == 667.0
        static let IS_IPHONE_6P_7P         = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH == 736.0
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
