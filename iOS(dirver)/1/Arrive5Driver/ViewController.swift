//
//  ViewController.swift
//  Arrive5Driver
//
//  Created by Joy on 09/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import CoreLocation
import MRCountryPicker

class ViewController: UIViewController, UITextFieldDelegate, MRCountryPickerDelegate {
    
    // MARK: - Outlets
    // MARK: -
    
    @IBOutlet weak var tfMobileNumber: UITextField!
    @IBOutlet weak var tfPassword: UITextField!
    @IBOutlet weak var lblTermsAndCOnd: UILabel!
    @IBOutlet weak var btnCrsValFirst: UIButton!
    @IBOutlet weak var btnCrsValSec: UIButton!
    @IBOutlet var vwPickerval: UIView!
    @IBOutlet weak var lblCountryCode: UILabel!
    @IBOutlet weak var pkValue: MRCountryPicker!
    
    // MARK: - Properties
    // MARK: -
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    // MARK: - VCCycles
    // MARK: -
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        
        self.btnCrsValFirst.isHidden = true
        self.btnCrsValSec.isHidden = true
        pkValue.countryPickerDelegate = self
        pkValue.showPhoneNumbers = true
        pkValue.setCountry("SI")
        pkValue.setCountryByName("India")
        let wholeStr = "By signing up, You agree to our terms & conditions, privacy policy Arbitration of disputes and waiver of class action claims"
        let rangeToUnderLine = NSRange(location: 32, length: 92)
        let underLineTxt = NSMutableAttributedString(string: wholeStr, attributes: [NSAttributedString.Key.font:UIFont.boldSystemFont(ofSize: 10.0),NSAttributedString.Key.foregroundColor: UIColor(hexString: "#2abbe7")])
        underLineTxt.addAttribute(NSAttributedString.Key.underlineStyle, value: NSUnderlineStyle.single.rawValue, range: rangeToUnderLine)
        lblTermsAndCOnd.attributedText = underLineTxt
        
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
        if (UserDefaults.standard.value(forKey: "user_id") != nil){
            let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
            self.navigationController?.pushViewController(homeVc, animated: false)
        }else{
            print("joy")
        }
        
    }
    
    
    @IBAction func btnPickerVal(_ sender: UIButton) {
        view.addSubview(vwPickerval)
        AlertViewData.animate(withDuration: 0.0, delay: 0.0, usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwPickerval.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY+UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            AlertViewData.animate(withDuration: 0.5, delay: 0.4, usingSpringWithDamping: 1.0,
                                  initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwPickerval.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY-UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)}, completion: { (finished: Bool) in
            })
        })
    }
    
    
    @IBAction func TermsAndConditions(_ sender: UIButton)
    {
        let optionMenu = UIAlertController(title: nil, message: "Choose Option", preferredStyle: .actionSheet)

          let saveAction = UIAlertAction(title: "Terms & Condition", style: .default, handler:
          {
              (alert: UIAlertAction!) -> Void in
            
            let TermsandViewController = self.storyboard?.instantiateViewController(withIdentifier: "TermsandViewController") as! TermsandViewController
            TermsandViewController.statusss = "terms"
            self.navigationController?.pushViewController(TermsandViewController, animated: true)
            
          })

          let deleteAction = UIAlertAction(title: "Privacy Policy", style: .default, handler:
          {
              (alert: UIAlertAction!) -> Void in
            
            let TermsandViewController = self.storyboard?.instantiateViewController(withIdentifier: "TermsandViewController") as! TermsandViewController
             TermsandViewController.statusss = "privacy"
                       self.navigationController?.pushViewController(TermsandViewController, animated: true)
             
          })

          let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler:
          {
              (alert: UIAlertAction!) -> Void in
             
          })
          optionMenu.addAction(saveAction)
          optionMenu.addAction(deleteAction)
        
          optionMenu.addAction(cancelAction)
        self.present(optionMenu, animated: true, completion: nil)

    }
    
    
    @IBAction func btnDoneAction(_ sender: UIButton) {
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwPickerval.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
    }
    
    @IBAction func btnCancelAction(_ sender: UIButton) {
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwPickerval.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
    }
    
    
    @IBAction func btnEditFirst(_ sender: UIButton) {
        self.tfMobileNumber.text = ""
    }
    @IBAction func btnEditSec(_ sender: UIButton) {
        self.tfPassword.text = ""
    }
    
    @IBAction func btnForgotPassAction(_ sender: UIButton) {
        
        
        let forgotPassVC = self.storyboard?.instantiateViewController(withIdentifier: "ForgotPasswordVC") as! ForgotPasswordVC
        self.navigationController?.pushViewController(forgotPassVC, animated: true)
        
    }
    
    @IBAction func btnSignUpAction(_ sender: UIButton) {
        let ConfirmPhnVC = self.storyboard?.instantiateViewController(withIdentifier: "ConfirmPhnVC") as! ConfirmPhnVC
        self.navigationController?.pushViewController(ConfirmPhnVC, animated: true)
    }
    
    @IBAction func btnLoginAction(_ sender: UIButton) {
        if self.tfMobileNumber.text == ""{
            self.view.makeToast("Please Enter Mobile Number")
        }else if self.tfPassword.text == ""{
            self.view.makeToast("Please Enter Password")
        }else{
            
            //  lblCountryCode .resignFirstResponder()
              tfMobileNumber .resignFirstResponder()
            
               tfPassword .resignFirstResponder()
            self.loginApi("\(self.lblCountryCode.text ?? "+91")\(self.tfMobileNumber.text!)", self.tfPassword.text!)
            
        }
    }
    func loginApi(_ phoneNo:String!, _ Password: String!){
        let aStrApi = "\(Constant.API.kLogin)"
        let aToken = appDelegate.deviceTokenString ?? "93283232932098231902130843980jndsjn0208"
        let dictData : [String : AnyObject]!
        dictData = ["mobileno" : phoneNo,
                    "password":Password,
                    "token":aToken,
                    "appPlatform":"ios"] as [String : AnyObject]
        
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            
            if json["status"].rawString() == "true"{
                let userDetail = json["details"].rawValue as! [String:Any]
                self.appDelegate.userDetail = userDetail
                let userId = userDetail["id"] as! String
                let name = userDetail["first_name"] as? String
                let aImgUrl = userDetail["image"] as? String
                let aPhn = userDetail["mobile"] as? String
                let aGender = userDetail["gender"] as? String
                let lat = userDetail["latitude"] as? String
                let long = userDetail["longitude"] as? String
                let city = userDetail["city"] as? String
                let country = userDetail["country"] as? String
                let email = userDetail["email"] as? String
                let last_name = userDetail["last_name"] as? String
                let isOnline = userDetail["is_online"] as? String
                 let driveidss = userDetail["driver_id"] as? String
                
                let userDefaultId = UserDefaults.standard
                if aGender == "0"{
                    userDefaultId.set("Female", forKey: "gender")
                }else{
                    userDefaultId.set("Male", forKey: "gender")
                }
                
                userDefaultId.set(name, forKey: "first_name")
                if let middleName = userDetail["middle_name"] as? String
                {
                    userDefaultId.set(middleName, forKey: "middle_name")
                }
                else
                {
                    userDefaultId.set("", forKey: "middle_name")
                }
                
                
                userDefaultId.set(last_name, forKey: "last_name")
                self.getAddressFromLatLon(pdblLatitude: lat!, withLongitude: long!)
                userDefaultId.set(city, forKey: "cityVal")
                userDefaultId.set(email, forKey: "emailId")
                userDefaultId.set(userId, forKey: "user_id")
                userDefaultId.set(aImgUrl, forKey: "img_url")
                userDefaultId.set(aPhn, forKey: "mobile")
                userDefaultId.set(country, forKey: "country")
                userDefaultId.set(name, forKey: "name")
                userDefaultId.set(isOnline, forKey: "is_online")
                userDefaultId.set(driveidss, forKey: "driveidss")
                
                userDefaultId.synchronize()
                let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                self.navigationController?.pushViewController(homeVc, animated: false)
            }else{
                self.view.makeToast(json["msg"].rawString())
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
    func getAddressFromLatLon(pdblLatitude: String, withLongitude pdblLongitude: String) {
        var center : CLLocationCoordinate2D = CLLocationCoordinate2D()
        let lat: Double = Double("\(pdblLatitude)")!
        //21.228124
        let lon: Double = Double("\(pdblLongitude)")!
        //72.833770
        let ceo: CLGeocoder = CLGeocoder()
        center.latitude = lat
        center.longitude = lon
        
        let loc: CLLocation = CLLocation(latitude:center.latitude, longitude: center.longitude)
        
        
        ceo.reverseGeocodeLocation(loc, completionHandler:
            {(placemarks, error) in
                if (error != nil)
                {
                    print("reverse geodcode fail: \(error!.localizedDescription)")
                }
                let pm = placemarks! as [CLPlacemark]
                
                if pm.count > 0 {
                    let pm = placemarks![0]
                    var addressString : String = ""
                    if pm.subLocality != nil {
                        addressString = addressString + pm.subLocality! + ", "
                    }
                    if pm.thoroughfare != nil {
                        addressString = addressString + pm.thoroughfare! + ", "
                    }
                    if pm.locality != nil {
                        addressString = addressString + pm.locality! + " "
                    }
//                    if pm.country != nil {
//                        addressString = addressString + pm.country! + ", "
//                    }
//                    if pm.postalCode != nil {
//                        addressString = addressString + pm.postalCode! + " "
//                    }
                    
                    let userDefaultId = UserDefaults.standard
                    userDefaultId.set(addressString, forKey: "AddressVal")
                    userDefaultId.synchronize()
                    print(addressString)
                }
        })
        
    }
    
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if textField == tfMobileNumber{
            self.btnCrsValFirst.isHidden = false
            self.btnCrsValSec.isHidden = true
        }else if textField == tfPassword{
            self.btnCrsValFirst.isHidden = true
            self.btnCrsValSec.isHidden = false
        }
    }
    func textFieldDidEndEditing(_ textField: UITextField) {
        self.btnCrsValFirst.isHidden = true
        self.btnCrsValSec.isHidden = true
    }
    
    func countryPhoneCodePicker(_ picker: MRCountryPicker, didSelectCountryWithName name: String, countryCode: String, phoneCode: String, flag: UIImage) {
        self.lblCountryCode.text = phoneCode
    }
    
    
}

