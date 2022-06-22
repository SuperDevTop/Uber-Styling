//
//  ViewController.swift
//  Arrive5
//
//  Created by Joy on 04/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import MRCountryPicker
import SendOTPFramework

class ViewController: UIViewController, UITextFieldDelegate, MRCountryPickerDelegate{//}, SendOTPAuthenticationViewControllerDelegate {
    
    
    
    @IBOutlet weak var tfMobileNumber: UITextField!
    @IBOutlet weak var tfPassword: UITextField!
    @IBOutlet weak var lblTermsAndCOnd: UILabel!
    @IBOutlet weak var btnCrsValFirst: UIButton!
    @IBOutlet weak var btnCrsValSec: UIButton!
    @IBOutlet var vwPickerval: UIView!
    @IBOutlet weak var lblCountryCode: UILabel!
    @IBOutlet weak var pkValue: MRCountryPicker!
    
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
   

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.btnCrsValFirst.isHidden = true
        self.btnCrsValSec.isHidden = true
        pkValue.countryPickerDelegate = self
        pkValue.showPhoneNumbers = true
        pkValue.setCountry("SI")
        pkValue.setCountryByName("India")
        
        let wholeStr = "By signing up, You agree to our terms & conditions, privacy policy Arbitration of disputes and waiver of class action claims"
        //let rangeToUnderLine = (wholeStr as NSString).range(of: "Register")
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
        
//        if let countryCode = (Locale.current as NSLocale).object(forKey: .countryCode) as? String {
//            print(countryCode)
//            lblCountryCode.text = countryDictionary[countryCode]
//        }

        
        
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
        
    @IBAction func Termsandconditions(_ sender: UIButton)
       {
           let optionMenu = UIAlertController(title: nil, message: "Choose Option", preferredStyle: .actionSheet)

                    let saveAction = UIAlertAction(title: "Terms & Condition", style: .default, handler:
                    {
                        (alert: UIAlertAction!) -> Void in
                      
                      let TermsandicondictionViewController = self.storyboard?.instantiateViewController(withIdentifier: "TermsandicondictionViewController") as! TermsandicondictionViewController
                      TermsandicondictionViewController.statusss = "terms"
                      self.navigationController?.pushViewController(TermsandicondictionViewController, animated: true)
                      
                    })

                    let deleteAction = UIAlertAction(title: "Privacy Policy", style: .default, handler:
                    {
                        (alert: UIAlertAction!) -> Void in
                      
                      let TermsandicondictionViewController = self.storyboard?.instantiateViewController(withIdentifier: "TermsandicondictionViewController") as! TermsandicondictionViewController
                       TermsandicondictionViewController.statusss = "privacy"
                                 self.navigationController?.pushViewController(TermsandicondictionViewController, animated: true)
                       
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
    
    
//        let frameworkBundle = Bundle(identifier: "com.walkover.SendOTPFramework")
//        let authViewControler :AuthenticationViewController = AuthenticationViewController.init(nibName: "AuthenticationViewController", bundle: frameworkBundle)
//        authViewControler.delegate = self
//        if #available(iOS 11.0, *) {
//            authViewControler.navBarColor = UIColor(named: "neela")
//        } else {
//            authViewControler.navBarColor = UIColor.blue
//        }
//        authViewControler.navBarTitleColor = UIColor.white
//        authViewControler.authkey = "224395AcZxiSrJoH5b3de509"
//        authViewControler.companyImage = #imageLiteral(resourceName: "logo")
//        authViewControler.customMessage = "Your verification code is ##OTP##."
//        authViewControler.senderId = "OTPSMS"
//        self.present(authViewControler, animated: true, completion: nil)
//
//    }
//
//
//    func authenticationisSuccessful(forMobileNumber mobNo: String!, withCountryCode countryCode: String!) {
//        print(mobNo)
//        print("Success")
//        let profileEdit = self.storyboard?.instantiateViewController(withIdentifier: "EditProfileVC") as! EditProfileVC
//        self.navigationController?.pushViewController(profileEdit, animated: true)
//    }
//
//    func authenticationisFailed(forMobileNumber mobNo: String!, withCountryCode countryCode: String!) {
//        print(mobNo)
//        print("Failure")
//        self.view.makeToast("Failure")
//    }
//
//    func canceledAuthentication() {
//        print("Failure")
//        self.view.makeToast("Failure")
//
//    }
//
    
    
    

    @IBAction func btnLoginAction(_ sender: UIButton) {
        if self.tfMobileNumber.text == ""{
            self.view.makeToast("Please Enter Mobile Number")
        }
        else if self.tfPassword.text == ""{
            self.view.makeToast("Please Enter Password")
        }
        else{
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
                let email = userDetail["email"] as? String
                let invite_code = userDetail["invite_code"] as? String
                
                let userDefaultId = UserDefaults.standard
                userDefaultId.set(invite_code, forKey: "invite_code")
                userDefaultId.set(email, forKey: "email")
                userDefaultId.set(userId, forKey: "user_id")
                userDefaultId.set(aImgUrl, forKey: "img_url")
                userDefaultId.set(aPhn, forKey: "mobile")
                userDefaultId.set(userDetail["first_name"] as! String, forKey: "first_name")
                userDefaultId.set(userDetail["last_name"] as! String, forKey: "last_name")
                userDefaultId.set(userDetail["fav_music"] as! String, forKey: "fav_music")
                userDefaultId.set(userDetail["about_me"] as! String, forKey: "about_me")
                userDefaultId.set(userDetail["city"], forKey: "city")
                userDefaultId.set(userDetail["join_date"], forKey: "join_date")
                userDefaultId.set(userDetail["total_points"], forKey: "total_points")
                userDefaultId.set(userDetail["used_point"], forKey: "used_point")
                userDefaultId.set(userDetail["cancelled_point"], forKey: "cancelled_point")
                userDefaultId.set(userDetail["points_available"], forKey: "points_available")

                userDefaultId.synchronize()
                let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                self.navigationController?.pushViewController(homeVc, animated: true)
            }else{
                self.view.makeToast(json["msg"].rawString())
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
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
