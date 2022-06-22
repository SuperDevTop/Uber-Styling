//
//  SetngssViewController.swift
//  Arrive5Driver
//
//  Created by Test on 12/09/1941 Saka.
//  Copyright © 1941 Apple Inc. All rights reserved.
//

import UIKit
import FirebaseAuth


class SetngssViewController: UIViewController {

    @IBOutlet var DriveProfileImage: UIImageView!
    
    @IBOutlet var DriverName: UILabel!
    
    @IBOutlet var DriverMobile: UILabel!
    
    @IBOutlet var DriverEmail: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.DriveProfileImage.layer.masksToBounds = true
        self.DriveProfileImage.layer.cornerRadius = self.DriveProfileImage.frame.height/2
        
        self.DriverName.text = UserDefaults.standard.value(forKey: "name") as? String
        self.DriverMobile.text = UserDefaults.standard.value(forKey: "mobile") as? String
        self.DriverEmail.text = UserDefaults.standard.value(forKey: "emailId") as? String
        let aImgPath = UserDefaults.standard.value(forKey: "img_url") as? String
        APIManager.requestImage(path: aImgPath!, completionHandler: {(usrImage) in
            self.DriveProfileImage.image = usrImage
 })
        // Do any additional setup after loading the view.
    }
    

    @IBAction func AnytimeBtnAction(_ sender: UIButton)
    {
        let myTripVC = self.storyboard?.instantiateViewController(withIdentifier: "MIleFoundationViewController") as! MIleFoundationViewController
        self.navigationController?.pushViewController(myTripVC, animated: true)
    }
    
    
    @IBAction func LogOutBtn(_ sender: UIButton)
    {
        self.signOut()
    }
    
    
    @IBAction func SideBtn(_ sender: UIButton)
    {
       
            let HomeSideMenuVC = self.storyboard?.instantiateViewController(withIdentifier: "HomeSideMenuVC") as! HomeSideMenuVC
            HomeSideMenuVC.PathDirection = "SetngssViewController"
        addChild(HomeSideMenuVC)
            
        HomeSideMenuVC.didMove(toParent: self)
            
            UIView.animate(withDuration: 0.5, delay: 1.0, usingSpringWithDamping: 0.1, initialSpringVelocity: 0.0, options: .transitionFlipFromLeft, animations: {
                HomeSideMenuVC.view.frame = CGRect(x: self.view.frame.minX,
                                                   y: 0,
                                                   width: self.view.frame.size.width ,
                                                   height: self.view.frame.size.height)
            }, completion: {(bool) in
                self.view.addSubview(HomeSideMenuVC.view)
                HomeSideMenuVC.view.frame = self.view.bounds
            })
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
    
    
    
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
