//
//  HomeSideMenuVC.swift
//  Arrive5Driver
//
//  Created by Joy on 09/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

import UIKit
import FirebaseAuth

class HomeSideMenuVC: UIViewController,UITableViewDelegate, UITableViewDataSource{
    
    @IBOutlet weak var lblUserName: UILabel!
    
    @IBOutlet weak var tblHomeSideMenu: UITableView!
    @IBOutlet weak var ivUserProfile: UIImageView!
    @IBOutlet weak var lblUserPhnNumber: UILabel!
    @IBOutlet var vwSideMenuHeader: UIView!
    
    var PathDirection : String!
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    var arrMenu : [[String:Any]] = [["Name":"Home","Image":"main.png","selected_image":"main.png"],["Name":"Scheduled Pickups","Image":"piture_time.png","selected_image":"piture_time.png"],["Name":"My Trips","Image":"ridemy.png","selected_image":"ridemy.png"],["Name":"My Vehicle","Image":"cour.png","selected_image":"cour.png"],["Name":"Earnings","Image":"rain.png","selected_image":"rain.png"],["Name":"Notifications","Image":"message_alt.png","selected_image":"message_alt.png"],["Name":"Rating & Reviews","Image":"condisation.png","selected_image":"condisation.png"],["Name":"Help","Image":"you.png","selected_image":"you.png"],["Name":"Settings","Image":"set_profile.png","selected_image":"set_profile.png"],["Name":"Logout","Image":"go.png","selected_image":"go.png"],["Name":"Sign up to Drive","Image":"loginout.png","selected_image":"loginout.png"]]
    
    var arrMenuSelection : [Int] = []
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.ivUserProfile.layer.masksToBounds = true
        self.ivUserProfile.layer.cornerRadius = self.ivUserProfile.frame.height/2
        
        self.lblUserName.text = UserDefaults.standard.value(forKey: "name") as? String
        self.lblUserPhnNumber.text = UserDefaults.standard.value(forKey: "mobile") as? String
        let aImgPath = UserDefaults.standard.value(forKey: "img_url") as? String
        APIManager.requestImage(path: aImgPath!, completionHandler: {(usrImage) in
            self.ivUserProfile.image = usrImage
        })
    
        //appDelegate.userDetail
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        self.ivUserProfile.layer.masksToBounds = true
        self.ivUserProfile.layer.cornerRadius = self.ivUserProfile.frame.height/2
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnProfileView(_ sender: UIButton) {
        
        let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "ProfileVC") as! ProfileVC
        self.navigationController?.pushViewController(homeVc, animated: true)
    }
    
    
    @IBAction func btnHideSideMenu(_ sender: UIButton) {
        self.removingSideMenu()
    }
    
    // MARK: - TableView Delegates
    // MARK: -
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 135
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        return self.vwSideMenuHeader
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 45
    }
    
    // MARK: - TableView DataSource
    // MARK: -
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return self.arrMenu.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let menuCell = tableView.dequeueReusableCell(withIdentifier: "tblHomeSideMenu") as! tblHomeSideMenu
        let aDict = self.arrMenu[indexPath.row]
        menuCell.lblSideMenu.text = aDict["Name"] as? String
       menuCell.lblSideMenu.textColor = UIColor.black
        if self.arrMenuSelection.contains(indexPath.row){
            self.arrMenuSelection.remove(at: 0)
//            menuCell.lblSideMenu.textColor = UIColor.black
            menuCell.vwHighlighted.backgroundColor = UIColor(hexString: "2ABBE7")
            menuCell.ivSIdeMenu.image = UIImage(named: (aDict["selected_image"] as? String)!)
        }else{
//            menuCell.lblSideMenu.textColor = UIColor(hexString: "2ABBE7")
            menuCell.ivSIdeMenu.image = UIImage(named: (aDict["Image"] as? String)!)
            menuCell.vwHighlighted.backgroundColor = UIColor.white
        }
        
        return menuCell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        self.arrMenuSelection.insert(indexPath.row, at: 0)
        if indexPath.row == 0{
            
            if PathDirection == "HomeVc"{
                self.removingSideMenu()
            }else{
                let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                self.navigationController?.pushViewController(homeVc, animated: true)
            }
        }
        
             else if indexPath.row == 1
        {
            let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "ScheduledPickupViewController") as! ScheduledPickupViewController
            self.navigationController?.pushViewController(homeVc, animated: true)
        }
            
            
        else if indexPath.row == 2{
            UIView.animate(withDuration: 0.3, animations: {
                self.view.frame = CGRect(x: -self.view.frame.size.width ,
                                         y: 0,
                                         width: self.view.frame.size.width ,
                                         height: self.view.frame.size.height)
            }) { (bool) in
                self.willMove(toParent: nil)
                
                self.view.reloadInputViews()
                self.view.removeFromSuperview()
                self.removeFromParent()
            }

            let myTripVC = self.storyboard?.instantiateViewController(withIdentifier: "MyTripVC") as! MyTripVC
            self.navigationController?.pushViewController(myTripVC, animated: true)
        }
        
        else if indexPath.row == 3{
            let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "VehicleInfoVC") as! VehicleInfoVC
            homeVc.flagForVC = 1
            self.navigationController?.pushViewController(homeVc, animated: true)
        }
        
        else if indexPath.row == 4{
            let earningVC = self.storyboard?.instantiateViewController(withIdentifier: "EarningVC") as! EarningVC
            self.navigationController?.pushViewController(earningVC, animated: true)
        }


        else if indexPath.row == 6{
            let ratingReviewVC = self.storyboard?.instantiateViewController(withIdentifier: "ARRatingReviewVC") as! ARRatingReviewVC
            self.navigationController?.pushViewController(ratingReviewVC, animated: true)
        }
            
        else if indexPath.row == 5{
            let notificaton = self.storyboard?.instantiateViewController(withIdentifier: "NotificationViewController") as! NotificationViewController
            self.navigationController?.pushViewController(notificaton, animated: true)
        }
            
        else if indexPath.row == 7{
            let help = self.storyboard?.instantiateViewController(withIdentifier: "HelpViewController") as! HelpViewController
            self.navigationController?.pushViewController(help, animated: true)
        }
            
            
        else if indexPath.row == 8{
            let help = self.storyboard?.instantiateViewController(withIdentifier: "SetngssViewController") as! SetngssViewController
            self.navigationController?.pushViewController(help, animated: true)
        }
         
        else if indexPath.row == 9
        {
            self.signOut()
        }
        
         else if indexPath.row == 10{
     
            let appURLScheme = "arrive5user://"
            let appstoreurl = "https://www.apple.com/ios/app-store/"
            
            guard let appURL = URL(string: appURLScheme) else
            {
                return
            }
            
            guard let apsstroe = URL(string: appstoreurl) else
            {
                return
            }
            
            if UIApplication.shared.canOpenURL(appURL) {
                
                if #available(iOS 10.0, *) {
                    UIApplication.shared.open(appURL)
                }
                else
                {
                    UIApplication.shared.openURL(appURL)
                }
            }
            else
            {
                if #available(iOS 10.0, *)
                {
                    UIApplication.shared.open(appURL)
                }
                else
                {
                    UIApplication.shared.openURL(apsstroe)
                }
                
            }
            
        }
    

        self.tblHomeSideMenu.reloadData()
        print(indexPath.row)
    }
    
    func removingSideMenu(){
        UIView.animate(withDuration: 0.3, animations: {
            self.view.frame = CGRect(x: -self.view.frame.size.width ,
                                     y: 0,
                                     width: self.view.frame.size.width ,
                                     height: self.view.frame.size.height)
        }) { (bool) in
            self.willMove(toParent: nil)
            
            self.view.reloadInputViews()
            self.view.removeFromSuperview()
            self.removeFromParent()
        }
    }
    
    func signOut()
    {
        let value = UserDefaults.standard
        value.removeObject(forKey: "user_id")
        value.removeObject(forKey: "UserRegistered")
        
        // VehicleInfo
        UserDefaults.standard.removeObject(forKey: "VehicleInfo_VehicleClass")
        UserDefaults.standard.removeObject(forKey: "VehicleInfo_VehicleType")
        UserDefaults.standard.removeObject(forKey: "VehicleInfo_VehicleYear")
        UserDefaults.standard.removeObject(forKey: "VehicleInfo_VehicleModel")
        UserDefaults.standard.removeObject(forKey: "VehicleInfo_VehicleColour")
        UserDefaults.standard.removeObject(forKey: "VehicleInfo_VehicleManufactureYear")
        UserDefaults.standard.removeObject(forKey: "VehicleInfo_VehicleDoors")
        UserDefaults.standard.removeObject(forKey: "VehicleInfo_VehicleSeatBelts")
        UserDefaults.standard.removeObject(forKey: "VehicleInfo_VehicleImages")

        //DriverInfo
        UserDefaults.standard.removeObject(forKey: "DriverInfo_MiddleName")
        UserDefaults.standard.removeObject(forKey: "DriverInfo_SelectLocation")
        UserDefaults.standard.removeObject(forKey: "DriverInfo_SocialSecurityNumber")
        UserDefaults.standard.removeObject(forKey: "DriverInfo_DOB")
        UserDefaults.standard.removeObject(forKey: "VehicleInfo_LicenceCardNumber")
        UserDefaults.standard.removeObject(forKey: "DriverInfo_ExpirationDate")
        UserDefaults.standard.removeObject(forKey: "DriverInfo_ButtonCheckBoxStatus")
        UserDefaults.standard.removeObject(forKey: "DriverInfo_AddressOne")
        UserDefaults.standard.removeObject(forKey: "DriverInfo_AddressTwo")
        UserDefaults.standard.removeObject(forKey: "cityVal")
        UserDefaults.standard.removeObject(forKey: "DriverInfo_State")
        UserDefaults.standard.removeObject(forKey: "DriverInfo_ZipcodeVal")
        
        //DriverRequirements
        UserDefaults.standard.removeObject(forKey: "licence_plate")
        UserDefaults.standard.removeObject(forKey: "vechile_reg")
        UserDefaults.standard.removeObject(forKey: "adhar_img")
        UserDefaults.standard.removeObject(forKey: "licence_img")
        UserDefaults.standard.removeObject(forKey: "insuarance_img")
        UserDefaults.standard.removeObject(forKey: "vechile_img")

        value.synchronize()
        let firAuth = Auth.auth()
        do
        {
            try firAuth.signOut()
            self.navigationController?.popToRootViewController(animated: true)
        }
        catch
        {
            self.navigationController?.popToRootViewController(animated: true)
            print("Error")
        }
    }
    
}
