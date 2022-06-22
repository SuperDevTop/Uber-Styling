//
//  AppDelegate.swift
//  Arrive5
//
//  Created by Joy on 04/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces
import Stripe
import Stripe

import IQKeyboardManagerSwift
import Firebase
import UserNotifications
//import Fabric
//import Crashlytics
//shahnawaz
//6fd6c39a-df17-42c6-bf9e-2d69c460d7ac
//fabric e9f068d156660a4b73b842925013bd2b3e685402
//googleapi = AIzaSyA_ErWhZguYEK3cQA_lb0pb8rA_dfJ0ZRM
//infoplist -  com.googleusercontent.apps.50608357922-dqdevklbcb2oj6673cef34gvqn9m42ij
//com.arrive5user.com
@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate,CLLocationManagerDelegate{

    var window: UIWindow?
    
    
    var ivFlagValue : UIImage!
    var lblPhoneCode : String!
    var tfPhoneNo : String!
    var tfFirstName : String!
    var tfLastName : String!
    var tfEmail : String!
    var tfPassword : String!
    var tfCode: String!
     var tfyear: String!
     var tfmonth: String!
    var tfcvv: String!
    var tfpaypalemail: String!
     var tfpaypalpasword: String!
    var dataImageDataWork : Data!

    
    
    var deviceTokenString : String!
    var profileType : String = "Personal"
    var PassengerNumber:String = "1"
    var userDetail : [String:Any] = [:]
   // var arrCount : [Int] = [0]
    var arrCount : [Int]!

    var WaitingDriver : Bool!
    
    let locationManager = CLLocationManager()
    var lat : Double!
    var long : Double!
    var cordinates = CLLocationCoordinate2D()

    internal func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
       
//        UIApplication.shared.statusBarView?.backgroundColor = UIColor(red: 0/255.0, green: 176/255.0, blue: 236/255.0, alpha: 1.0)
        
        
        if #available(iOS 13.0, *) {
            let statusBar = UIView(frame: UIApplication.shared.keyWindow?.windowScene?.statusBarManager?.statusBarFrame ?? CGRect.zero)
             statusBar.backgroundColor = UIColor.init(red: 0/255.0, green: 176/255.0, blue: 236/255.0, alpha: 1.0)
             UIApplication.shared.keyWindow?.addSubview(statusBar)
        } else {
             UIApplication.shared.statusBarView?.backgroundColor = UIColor.init(red: 0/255.0, green: 176/255.0, blue: 236/255.0, alpha: 1.0)
        }
        
//------------------------------------
        
        
        FirebaseApp.configure()
        IQKeyboardManager.shared.enable = true
        IQKeyboardManager.shared.keyboardDistanceFromTextField = 100.0
        GMSPlacesClient.provideAPIKey("AIzaSyBD62D6SgxMOyTKiEygjHM7zgQGbWs6H9g")
        GMSServices.provideAPIKey("AIzaSyBD62D6SgxMOyTKiEygjHM7zgQGbWs6H9g")
        
        arrCount = [0]
        let indxx = UserDefaults.standard.integer(forKey: "selectBusinessProfile")
        
        if indxx == 0 {
            arrCount = [0]
        }else{
            arrCount = [indxx]
        }
//--------------------------------
        
        
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        let authorizationStatus = CLLocationManager.authorizationStatus()
        if (authorizationStatus == CLAuthorizationStatus.notDetermined) {
            locationManager.requestWhenInUseAuthorization()
        } else if (authorizationStatus == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
        }
        
//        Fabric.sharedSDK().debug = true
//        Fabric.with([Crashlytics.self])
        
//        if #available(iOS 10.0, *) {
//            let center = UNUserNotificationCenter.current()
//
//            center.delegate = self
//            center.requestAuthorization(options:[.badge, .alert, .sound]) { (granted, error) in
//                if granted{
//                    application.registerForRemoteNotifications()
//                }else{
//                    self.getAlert("Please Turn On The Notification Access")
//                }
//            }
//        if #available(iOS 10, *) {
//            let center = UNUserNotificationCenter.current()
//            center.requestAuthorization(options: [.alert, .badge, .sound], completionHandler: { (granted, error) in
//                DispatchQueue.main.async {
//                    UIApplication.shared.registerForRemoteNotifications()
//                }
//            })
//
//        }
//        else {
//            let settings = UIUserNotificationSettings(types: [.alert, .badge, .sound], categories: nil)
//            UIApplication.shared.registerUserNotificationSettings(settings)
//            UIApplication.shared.registerForRemoteNotifications()
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
        
        
          // STPPaymentConfiguration.shared().publishableKey = "pk_test_***************"
//
//
//
//                    STPPaymentConfiguration.shared().appleMerchantIdentifier = "merchant.com.***************"
//
//                    STPPaymentConfiguration.shared().companyName = "Test"
        
        
//       STPAPIClient.shared().publishableKey = "pk_live_Kj3jfNSfhdRNcqHwaEHT9GD4"
        
         STPAPIClient.shared().publishableKey = "pk_test_ihH0ZJS1mPxvcs2AdI2oIddA"
        
        
//                          card.number = paymentTextField.cardNumber
//                          card.expMonth = paymentTextField.expirationMonth
//                          card.expYear =  paymentTextField.expirationYear
//                          card.cvc = paymentTextField.cvc
        
        
        
//        GMSServices.provideAPIKey("AIzaSyBNM-OE-KJPz_KEkY5uLjMbwMhLZ0uoBiM")
//        GMSPlacesClient.provideAPIKey("AIzaSyBNM-OE-KJPz_KEkY5uLjMbwMhLZ0uoBiM")
        
        
        // Override point for customization after application launch.
        return true
    }
    
    func addNotification(content:UNNotificationContent, trigger:UNNotificationTrigger?, indentifier:String){
        let request = UNNotificationRequest(identifier: indentifier, content: content, trigger: trigger)
        UNUserNotificationCenter.current().add(request, withCompletionHandler: {
            (errorObject) in
            if let error = errorObject{
                print("Error \(error.localizedDescription) in notification \(indentifier)")
            }
        })
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        
        let userLocation:CLLocation = locations[0] as CLLocation
        cordinates = CLLocationCoordinate2D(latitude: userLocation.coordinate.latitude,longitude: userLocation.coordinate.longitude)
    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if (status == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
        }
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
    
//    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
//        let tokenChars = (deviceToken as NSData).bytes.bindMemory(to: CChar.self, capacity: deviceToken.count)
//        deviceTokenString = ""
//        for i in 0..<deviceToken.count {
//            deviceTokenString += String(format: "%02.2hhx", arguments: [tokenChars[i]])
//        }
//        print("Received token data! \(deviceTokenString)")
//        let tokenDefaults = UserDefaults.standard
//        if deviceTokenString == nil{
//            tokenDefaults.set("C649C67E66101C370344466BE9B2388C86DEF9641F2F655B5D526653EEF1BBFA", forKey: "deviceTokenString")
//        }
//        else{
//            tokenDefaults.set(deviceTokenString, forKey: "deviceTokenString")
//        }
//        tokenDefaults.synchronize()
//        Auth.auth().setAPNSToken((deviceToken as NSData) as Data, type: AuthAPNSTokenType.sandbox)
//
//        Auth.auth().setAPNSToken(deviceToken, type: AuthAPNSTokenType.sandbox)
//
//        InstanceID.instanceID().setAPNSToken(deviceToken, type: InstanceIDAPNSTokenType.sandbox)
//
//        InstanceID.instanceID().setAPNSToken(deviceToken, type: InstanceIDAPNSTokenType.prod)
//    }
//
//
//    func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
//        print("Couldn't register: \(error)")
//    }
    
    
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data)
    {
        // Convert token to string
//        deviceTokenString = deviceToken.reduce("", {$0 + String(format: "%02X", $1)})
//        print(deviceTokenString)
        
        let tokenChars = (deviceToken as NSData).bytes.bindMemory(to: CChar.self, capacity: deviceToken.count)
        var tokenString = ""
        for i in 0..<deviceToken.count {
            tokenString += String(format: "%02.2hhx", arguments: [tokenChars[i]])
        }
        print("Received token data! \(tokenString)")
//        deviceTokenString = tokenString
       
        
       deviceTokenString = InstanceID.instanceID().token()
        
          print("mytoken \(deviceTokenString)")
        
 print("Received token data! \(tokenString)")
//         print("fcm token data! \(token)")
        let tokenDefaults = UserDefaults.standard
        if deviceTokenString == nil{
            
        tokenDefaults.set("C649C67E66101C370344466BE9B2388C86DEF9641F2F655B5D526653EEF1BBFA", forKey: "deviceTokenString")
        }
        else{
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
    
 
    func messaging(_ messaging: Messaging, didRefreshRegistrationToken fcmToken: String) {
        print("Refreshed Token: \(fcmToken)")
    }
    
    
//    func UserLatLongUpdate(){
//        let userId = UserDefaults.standard.string(forKey: "user_id")
//        let aStrApi = "http://arrive5.pcthepro.com/api/locationUpdate"
//        let lat = "\(cordinates.latitude)"
//        
//        let long = "\(cordinates.longitude)"
//        let dictData : [String : AnyObject]!
//        
//        dictData = ["lat" : lat,
//                    "lng": long,
//                    "type":"ios",
//                    "id" : userId! ] as [String : AnyObject]
//        print(dictData)
//        
//        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
//            print(json)
//            
//            
//////            let rootViewController = self.window!.rootViewController as! UINavigationController
////            let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
////            let homeVC = mainStoryboard.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
////            homeVC.markerDrawing()
//            if json["status"].rawString() == "true"{
//                self.window?.makeToast(json["msg"].rawString())
//                
//            }else{
//                self.window?.makeToast(json["msg"].rawString())
//            }
//        }, failure: {(error) in
//            
//        })
//    }
}

func application(_ application: UIApplication,
                 didReceiveRemoteNotification notification: [AnyHashable : Any],
                 fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void) {
    
    if Auth.auth().canHandleNotification(notification) {
        completionHandler(.noData)
        return
    }
    // This notification is not auth related, developer should handle it.
//    handleNotification(notification)
}

extension AppDelegate: UNUserNotificationCenterDelegate {
    
    //MARK: - Delegates for Notifications
//    func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: @escaping () -> Void) {
//
//        print("Message received.")
//
//
//        let action = response.actionIdentifier
//        let request = response.notification.request
//        let content = request.content.userInfo
    
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                willPresent notification: UNNotification,
                                withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        //        let action = response.actionIdentifier
        //        let request = response.notification.request
        let content = notification.request.content.userInfo
    
        let ApiData = content["aps"] as! [String:Any]
        let PushTag = content["gcm.notification.push_tag"] as! String
        print(content["aps"] as! [String:Any])
        print(PushTag)
      
        
        if PushTag == "accept"{
            if self.WaitingDriver == true {
                NotificationCenter.default.post(name: Notification.Name("RefreshPage"), object: content)
                
            }else{
                let rootViewController = self.window!.rootViewController as! UINavigationController
                let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
                let vc = mainStoryboard.instantiateViewController(withIdentifier: "WaitingDriverViewController") as! WaitingDriverViewController
                print(ApiData)
                rootViewController.pushViewController(vc, animated: false)
            }
        }
            //rahul
        else if (PushTag == "finish_ride"){
            
            let rootViewController = self.window!.rootViewController as! UINavigationController
            let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
            let vc = mainStoryboard.instantiateViewController(withIdentifier: "PaymentVC") as! PaymentVC
               vc.dissct = ApiData;
            print(ApiData)
            rootViewController.pushViewController(vc, animated: false)
            
        }
        
        
//        //gautam
//        if PushTag == "arrived"{
//            let bookingId = ApiData["bookingId"] as! String
//            let defaults = UserDefaults.standard
//            defaults.set(bookingId, forKey: "bookingId")
//        }
//        
        
//        completionHandler()
    }
    
    // called if app is running in foreground
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                didReceive response: UNNotificationResponse,
                                withCompletionHandler completionHandler: @escaping () -> Void) {
        let content = response.notification.request.content.userInfo
        
        let ApiData = content["aps"] as! [String:Any]
        let PushTag = content["gcm.notification.push_tag"] as! String
        print(content["aps"] as! [String:Any])
        print(PushTag)
        
        
        if PushTag == "accept"{
            if self.WaitingDriver == true {
                NotificationCenter.default.post(name: Notification.Name("RefreshPage"), object: content)
                
            }else{
                let rootViewController = self.window!.rootViewController as! UINavigationController
                let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
                let vc = mainStoryboard.instantiateViewController(withIdentifier: "WaitingDriverViewController") as! WaitingDriverViewController
                print(ApiData)
                rootViewController.pushViewController(vc, animated: false)
            }
        }
            //rahul
        else if (PushTag == "finish_ride"){
            
            let rootViewController = self.window!.rootViewController as! UINavigationController
            let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
            let vc = mainStoryboard.instantiateViewController(withIdentifier: "PaymentVC") as! PaymentVC
            print(ApiData)
            rootViewController.pushViewController(vc, animated: false)
            
        }
        
        completionHandler()
    }
}
