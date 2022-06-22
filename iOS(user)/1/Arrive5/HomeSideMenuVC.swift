//
//  HomeSideMenuVC.swift
//  Arrive5
//
//  Created by Joy on 05/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import FirebaseAuth
extension UIApplication {
    
    var statusBarView: UIView? {
        return value(forKey: "statusBar") as? UIView
    }
    
}
class HomeSideMenuVC: UIViewController,UITableViewDelegate, UITableViewDataSource{

    @IBOutlet weak var lblUserName: UILabel!
    
    @IBOutlet weak var tblHomeSideMenu: UITableView!
    @IBOutlet weak var ivUserProfile: UIImageView!
    @IBOutlet weak var lblUserPhnNumber: UILabel!
    @IBOutlet var vwSideMenuHeader: UIView!
    
    var PathDirection : String!
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
//    var arrMenu : [[String:Any]] = [["Name":"Home","Image":"home.png"],["Name":"Your Rides","Image":"your_rides.png"],["Name":"Free Rides","Image":"free_rides.png"],["Name":"Add Payment","Image":"add_payment.png"],["Name":"Notifications","Image":"notification-1.png"],["Name":"Rating & Reviews","Image":"rating.png"],["Name":"Promos","Image":"promos.png"],["Name":"My Rewards","Image":"my_reward.png"],["Name":"Help","Image":"help.png"],["Name":"Settings","Image":"settings.png"],["Name":"Logout","Image":"logout.png"],["Name":"Sign up to Drive","Image":"signup_to_driver.png"]]
    
        var arrMenu : [[String:Any]] = [["Name":"Home","Image":"home.png"],["Name":"Your Rides","Image":"your_rides.png"],["Name":"Free Rides","Image":"free_rides.png"],["Name":"Notifications","Image":"notification-1.png"],["Name":"Rating & Reviews","Image":"rating.png"],["Name":"Help","Image":"help.png"],["Name":"Settings","Image":"settings.png"],["Name":"Logout","Image":"logout.png"],["Name":"Sign up to Drive","Image":"signup_to_driver.png"]]
    
    var arrMenuSelection : [Int] = []
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.ivUserProfile.layer.masksToBounds = true
        self.ivUserProfile.layer.cornerRadius = self.ivUserProfile.frame.height/2
        self.lblUserName.text = UserDefaults.standard.string(forKey: "first_name")
        self.lblUserPhnNumber.text = UserDefaults.standard.string(forKey: "mobile")!
        let aImgUrl = UserDefaults.standard.string(forKey: "img_url")!
        APIManager.requestImage(path: aImgUrl, completionHandler: {(image) in
            self.ivUserProfile.image = image
        })
        //appDelegate.userDetail
        
//        UIApplication.shared.statusBarView?.backgroundColor = UIColor(red: 0/255.0, green: 176/255.0, blue: 236/255.0, alpha: 1.0)
        // Do any additional setup after loading the view.
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
        menuCell.ivSIdeMenu.image = UIImage(named: (aDict["Image"] as? String)!)
        if self.arrMenuSelection.contains(indexPath.row){
            self.arrMenuSelection.remove(at: 0)
            menuCell.lblSideMenu.textColor = UIColor.white
//            menuCell.vwHighlighted.backgroundColor = UIColor(hexString: "2ABBE7")
            menuCell.vwHighlighted.backgroundColor = UIColor.lightGray;
        }else{
//            menuCell.lblSideMenu.textColor = UIColor(hexString: "2ABBE7")
            menuCell.lblSideMenu.textColor = UIColor.black
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
        }else if indexPath.row == 1{
            let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "YourRidesViewController") as! YourRidesViewController
            self.navigationController?.pushViewController(homeVc, animated: true)
            
        }else if indexPath.row == 2{
            let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "FreeRideViewController") as! FreeRideViewController
            self.navigationController?.pushViewController(homeVc, animated: true)
            
        } else if indexPath.row == 3{
            let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "NotificationViewController") as! NotificationViewController
            self.navigationController?.pushViewController(homeVc, animated: true)
            
        }else if indexPath.row == 4{
            let ratingReviewVC = self.storyboard?.instantiateViewController(withIdentifier: "ARRatingReviewVC") as! ARRatingReviewVC
            self.navigationController?.pushViewController(ratingReviewVC, animated: true)
        }
            
    else if indexPath.row == 5{
    let ratingReviewVC = self.storyboard?.instantiateViewController(withIdentifier: "HelpViewController") as! HelpViewController
    self.navigationController?.pushViewController(ratingReviewVC, animated: true)
    }
            
            
            
            
//        else if indexPath.row == 6{
//            let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "PromoCodeViewController") as! PromoCodeViewController
//            self.navigationController?.pushViewController(homeVc, animated: true)
        
//        }
//    else if indexPath.row == 7{
//            let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "MyRewardViewController") as! MyRewardViewController
//            self.navigationController?.pushViewController(homeVc, animated: true)
//
//        }
        else if indexPath.row == 6{
            let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "SettingsViewController") as! SettingsViewController
            self.navigationController?.pushViewController(homeVc, animated: true)
            
        } else if indexPath.row == 7{
            self.signOut()
            
        }else if indexPath.row == 8
        {
          
            
            let appURLScheme = "ariive5driver://"
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
