//
//  AppDelegate.swift
//  Arrive5Driver
//
//  Created by Joy on 09/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces
import IQKeyboardManagerSwift
import Firebase
import UserNotifications
//import Fabric
//import Crashlytics
//shahnawaz
extension UIApplication {
    
    var statusBarView: UIView? {
        return value(forKey: "statusBar") as? UIView
    }
    
}
@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var deviceTokenString : String!
    var window: UIWindow?
    var ivFlagValue : UIImage!
    var lblPhoneCode : String!
    var tfPhoneNo : String!
    var userDetail : [String:Any] = [:]
    var userInfo : [String:Any] = [:]
    var booking_id : String!
    var devToken: String?
    internal func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
//        Fabric.sharedSDK().debug = true
//        UIApplication.shared.statusBarView?.backgroundColor = UIColor(red: 0/255.0, green: 176/255.0, blue: 236/255.0, alpha: 1.0)
        
        
        
        
        
        if #available(iOS 13.0, *) {
                   let statusBar = UIView(frame: UIApplication.shared.keyWindow?.windowScene?.statusBarManager?.statusBarFrame ?? CGRect.zero)
                    statusBar.backgroundColor = UIColor.init(red: 0/255.0, green: 176/255.0, blue: 236/255.0, alpha: 1.0)
                    UIApplication.shared.keyWindow?.addSubview(statusBar)
               } else {
                    UIApplication.shared.statusBarView?.backgroundColor = UIColor.init(red: 0/255.0, green: 176/255.0, blue: 236/255.0, alpha: 1.0)
               }
        
        
        
        
//        if #available(iOS 10.0, *) {
//            let center = UNUserNotificationCenter.current()
        
//            center.delegate = self
//            center.requestAuthorization(options:[.badge, .alert, .sound]) { (granted, error) in
//                if granted{
//                    application.registerForRemoteNotifications()
//                }else{
//                    self.getAlert("Please Turn On The Notification Access")
//                }
//                // Enable or disable features based on authorization.
//            }
//        } else {
//            // Fallback on earlier versions
//        }
        FirebaseApp.configure()
//        IQKeyboardManager.sharedManager().enable = true
//        IQKeyboardManager.sharedManager().keyboardDistanceFromTextField = 100.0
//        GMSServices.provideAPIKey("AIzaSyCxmB6um5hRUAtluHP8kszv9qn0Li4VmQI")
//        GMSPlacesClient.provideAPIKey("AIzaSyCxmB6um5hRUAtluHP8kszv9qn0Li4VmQI")
        
        IQKeyboardManager.shared.enable = true
        IQKeyboardManager.shared.keyboardDistanceFromTextField = 100.0
        GMSPlacesClient.provideAPIKey("AIzaSyA1yFj539rsjnQwYPRZpP6cSUcG5NXjg-c")
        GMSServices.provideAPIKey("AIzaSyA1yFj539rsjnQwYPRZpP6cSUcG5NXjg-c")
        
        
//            if #available(iOS 10, *) {
//                let center = UNUserNotificationCenter.current()
//                center.requestAuthorization(options: [.alert, .badge, .sound], completionHandler: { (granted, error) in
//                    DispatchQueue.main.async {
//                        UIApplication.shared.registerForRemoteNotifications()
//                    }
//                })
//            } else {
//                let settings = UIUserNotificationSettings(types: [.alert, .badge, .sound], categories: nil)
//                UIApplication.shared.registerUserNotificationSettings(settings)
//                UIApplication.shared.registerForRemoteNotifications()
//            }
//
//        }
            
            if #available(iOS 10.0, *) {
                // For iOS 10 display notification (sent via APNS)
                UNUserNotificationCenter.current().delegate = self as! UNUserNotificationCenterDelegate
                
                let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
                UNUserNotificationCenter.current().requestAuthorization(
                    options: authOptions,
                    completionHandler: {_, _ in })
            } else {
                let settings: UIUserNotificationSettings =
                    UIUserNotificationSettings(types: [.alert, .badge, .sound], categories: nil)
                application.registerUserNotificationSettings(settings)
            }
            
            application.registerForRemoteNotifications()
            
//        }
        // Override point for customization after application launch.
        return true
    }
    
    func addNotification(content:UNNotificationContent,trigger:UNNotificationTrigger?, indentifier:String){
        let request = UNNotificationRequest(identifier: indentifier, content: content, trigger: trigger)
        UNUserNotificationCenter.current().add(request, withCompletionHandler: {
            (errorObject) in
            if let error = errorObject{
                print("Error \(error.localizedDescription) in notification \(indentifier)")
            }
        })
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }
    
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data)
    {
        // Convert token to string
//        deviceTokenString = deviceToken.reduce("", {$0 + String(format: "%02X", $1)})
        
        deviceTokenString = InstanceID.instanceID().token()

           print("Received token data! \(deviceTokenString)")
        let tokenDefaults = UserDefaults.standard
        if deviceTokenString == nil{
            tokenDefaults.set("C649C67E66101C370344466BE9B2388C86DEF9641F2F655B5D526653EEF1BBFA", forKey: "deviceTokenString")
        }else{
            tokenDefaults.set(deviceTokenString, forKey: "deviceTokenString")
        }
        tokenDefaults.synchronize()
        Auth.auth().setAPNSToken((deviceToken as NSData) as Data, type: AuthAPNSTokenType.sandbox)
        
        InstanceID.instanceID().setAPNSToken(deviceToken, type: InstanceIDAPNSTokenType.sandbox)
        
        InstanceID.instanceID().setAPNSToken(deviceToken, type: InstanceIDAPNSTokenType.prod)

    }
    
    func getAlert(_ message:String){
        window?.makeToast(message)
    }

}




func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: @escaping () -> Void) {
    let notificationData = response.notification.request.content.userInfo
    // Use this data to present a view controller based
    // on the type of notification received
    completionHandler()
}

extension AppDelegate: UNUserNotificationCenterDelegate {
    
    //MARK: - Delegates for Notifications
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                willPresent notification: UNNotification,
                                withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
//        let action = response.actionIdentifier
//        let request = response.notification.request
        let content = notification.request.content.userInfo
        
        
        let ApiData = content["aps"] as! [String:Any]
        let PushTag = content["gcm.notification.push_tag"] as! String
        print(content["aps"] as! [String:Any])
     
        
        let bookingId = content["gcm.notification.booking_id"] as! String
        booking_id = "\(bookingId)"
        
        let myInt1 = (booking_id as NSString).integerValue

        
        print(booking_id)
//         let PushTag = "booking"
        let end_point = content["gcm.notification.end_point"] as! String
        let user_rating = content["gcm.notification.user_rating"] as! String
        let duration = content["gcm.notification.duration"] as! String
        let vehicleSubTypeName = content["gcm.notification.vehicleSubTypeName"] as! String
        
        //    ["booking_id": 179, "push_tag": booking, "alert": You have a booking, "userName": joy deep, "userImg": http://arrive5.pcthepro.com/uploads/users/UserImage.jpg, "sound": default, "end_point": Gujarat, India, "content-available": 1, "user_rating": 4.2, "user_id": 48, "start_point": Kalkaji, New Delhi , "duration": 18 hours 13 mins, "vehicleSubTypeName": Executive Luxury]
        
        if PushTag == "booking"{
            
            let rootViewController = self.window!.rootViewController as! UINavigationController
            let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
            let TimerVC = mainStoryboard.instantiateViewController(withIdentifier: "TimerVC") as! TimerVC
            TimerVC.bookingId = booking_id
            TimerVC.end_point = end_point
            TimerVC.user_rating = user_rating
            TimerVC.duration = duration
            TimerVC.vehicleSubTypeName = vehicleSubTypeName
            self.userInfo = notification.request.content.userInfo as! [String : Any]
            
            rootViewController.pushViewController(TimerVC, animated: false)
        }else{
        }
        print(PushTag)
//        completionHandler()
    }
    
    // called if app is running in foreground
//    func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent
//        notification: UNNotification, withCompletionHandler completionHandler:
//        @escaping (UNNotificationPresentationOptions) -> Void) {
//
//        // show alert while app is running in foreground
//        return completionHandler([UNNotificationPresentationOptions.alert,
//                                  UNNotificationPresentationOptions.sound,
//                                  UNNotificationPresentationOptions.badge])
//    }
    
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                didReceive response: UNNotificationResponse,
                                withCompletionHandler completionHandler: @escaping () -> Void) {
        let content = response.notification.request.content.userInfo
        
        
        
        let ApiData = content["aps"] as! [String:Any]
        let PushTag = content["gcm.notification.push_tag"] as! String
        print(content["aps"] as! [String:Any])
        
        
        let bookingId = content["gcm.notification.booking_id"] as! String
        booking_id = "\(bookingId)"
        
        let myInt1 = (booking_id as NSString).integerValue
        
        
        print(booking_id)
        //         let PushTag = "booking"
        let end_point = content["gcm.notification.end_point"] as! String
        let user_rating = content["gcm.notification.user_rating"] as! String
        let duration = content["gcm.notification.duration"] as! String
        let vehicleSubTypeName = content["gcm.notification.vehicleSubTypeName"] as! String
        
        //    ["booking_id": 179, "push_tag": booking, "alert": You have a booking, "userName": joy deep, "userImg": http://arrive5.pcthepro.com/uploads/users/UserImage.jpg, "sound": default, "end_point": Gujarat, India, "content-available": 1, "user_rating": 4.2, "user_id": 48, "start_point": Kalkaji, New Delhi , "duration": 18 hours 13 mins, "vehicleSubTypeName": Executive Luxury]
        
        if PushTag == "booking"{
            
            let rootViewController = self.window!.rootViewController as! UINavigationController
            let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
            let TimerVC = mainStoryboard.instantiateViewController(withIdentifier: "TimerVC") as! TimerVC
            TimerVC.bookingId = booking_id
            TimerVC.end_point = end_point
            TimerVC.user_rating = user_rating
            TimerVC.duration = duration
            TimerVC.vehicleSubTypeName = vehicleSubTypeName
            self.userInfo = response.notification.request.content.userInfo as! [String : Any]
            
            rootViewController.pushViewController(TimerVC, animated: false)
        }else{
        }
        print(PushTag)
        //        completionHandler()
        
        
        // Print full message.
        print("tap on on forground app",userInfo)
        
        completionHandler()
    }
}

