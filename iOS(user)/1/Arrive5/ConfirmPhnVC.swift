//
//  ConfirmPhnVC.swift
//  Arrive5
//
//  Created by Joy on 04/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import FirebaseAuth
import MRCountryPicker
import SVProgressHUD

class ConfirmPhnVC: UIViewController,UITextFieldDelegate, MRCountryPickerDelegate {
    
    @IBOutlet weak var tfMobileNumber: UITextField!
    @IBOutlet weak var btnEditVal: UIButton!
    @IBOutlet weak var ivCountryFlag: UIImageView!
    @IBOutlet weak var lblCountryCode: UILabel!
    @IBOutlet weak var confirmPhoneNumber: UIButton!
    @IBOutlet weak var lblTermsAndCond: UILabel!
    @IBOutlet var vwCountryPickerView: UIView!
    @IBOutlet weak var pkCountryCode: MRCountryPicker!
    
    
    var phnString : String!
    let appDelegate = UIApplication.shared.delegate as! AppDelegate

    override func viewDidLoad() {
        super.viewDidLoad()
        
      
        print(phnString)
        self.btnEditVal.isHidden = true
//        self.tfMobileNumber.attributedPlaceholder = NSAttributedString(string: "Enter Your Password", attributes: [NSForegroundColorAttributeName: UIColor.white])
        
        pkCountryCode.countryPickerDelegate = self
        pkCountryCode.showPhoneNumbers = true
        pkCountryCode.setCountry("SI")
        pkCountryCode.setCountryByName("India")
        
let wholeStr = "By signing up, You agree to our terms & conditions, privacy policy Arbitration of disputes and waiver of class action claims"
      //let rangeToUnderLine = (wholeStr as NSString).range(of: "Register")
      let rangeToUnderLine = NSRange(location: 32, length: 92)
      let underLineTxt = NSMutableAttributedString(string: wholeStr, attributes: [NSAttributedString.Key.font:UIFont.boldSystemFont(ofSize: 10.0),NSAttributedString.Key.foregroundColor: UIColor(hexString: "#2abbe7")])
      underLineTxt.addAttribute(NSAttributedString.Key.underlineStyle, value: NSUnderlineStyle.single.rawValue, range: rangeToUnderLine)
      lblTermsAndCond.attributedText = underLineTxt
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
    
    @IBAction func btnPickerVal(_ sender: UIButton) {
        
        view.addSubview(vwCountryPickerView)
        AlertViewData.animate(withDuration: 0.0, delay: 0.0, usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwCountryPickerView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY+UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            AlertViewData.animate(withDuration: 0.5, delay: 0.4, usingSpringWithDamping: 1.0,
                                  initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwCountryPickerView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY-UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)}, completion: { (finished: Bool) in
            })
        })
        
    }
    @IBAction func btnEditCross(_ sender: UIButton) {
        self.tfMobileNumber.text = ""
    }
    @IBAction func btnConfirmNumberAction(_ sender: UIButton) {
        
        appDelegate.lblPhoneCode = self.lblCountryCode.text
        appDelegate.ivFlagValue = self.ivCountryFlag.image
        appDelegate.tfPhoneNo = self.tfMobileNumber.text
        
        tfMobileNumber.resignFirstResponder()
        
        if self.tfMobileNumber.text == ""{
            self.view.makeToast("Enter Your Mobile number")
        }else{
            self.verifyApi("\(self.lblCountryCode.text!)\(self.tfMobileNumber.text!)")
        }
        
        
    }
    @IBAction func btnBackToLoginAction(_ sender: UIButton) {
        
        self.navigationController?.popViewController(animated: true)
        
    }
    
    @IBAction func btnDoneAction(_ sender: UIButton) {
        
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwCountryPickerView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
        
    }
    @IBAction func btnCancelAction(_ sender: UIButton) {
        
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwCountryPickerView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
        
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        guard let text = textField.text else { return true }
        let newLength = text.count + string.count - range.length
        return newLength <= 15 // Bool
    }
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if textField == tfMobileNumber{
            self.btnEditVal.isHidden = false
        }else{
            self.btnEditVal.isHidden = true
        }
    }
    func textFieldDidEndEditing(_ textField: UITextField) {
        self.btnEditVal.isHidden = true
    }
    
    func verifyApi(_ phoneNo:String!){
        let aStrApi = "\(Constant.API.kVerify)"
        let dictData : [String : AnyObject]!
        dictData = ["mobileno" : phoneNo] as [String : AnyObject]
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            if json["status"].rawString() == "true"{
                self.view.makeToast(json["message"].rawString())
            }else{
                SVProgressHUD.show(withStatus: "Please Wait")
//            self.verifyWithFirebase(phonenumber: "\(self.lblCountryCode.text!)\(self.tfMobileNumber.text!)")
                //rahul add
                
                let otp = json["otp"] as Any
                print(otp)
                let UserDefaulVerify = UserDefaults.standard
                UserDefaulVerify.set("\(otp)", forKey: "verificationVal")
                UserDefaulVerify.synchronize()
                let aOtpVC = self.storyboard?.instantiateViewController(withIdentifier: "OtpVC") as! OtpVC
                aOtpVC.pathValue = "SignUp"
                aOtpVC.PhoneNumber = "\(self.lblCountryCode.text!)\(self.tfMobileNumber.text!)"
                self.navigationController?.pushViewController(aOtpVC, animated: true)
 
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
    func verifyWithFirebase(phonenumber : String)
    {
        PhoneAuthProvider.provider().verifyPhoneNumber(phonenumber, uiDelegate: nil) { (verificationID, error) in
            if let error = error {
                SVProgressHUD.dismiss()
                self.view.makeToast(error.localizedDescription)
                print(error.localizedDescription)
                
                //                self.showMessagePrompt(error.localizedDescription)
                return
            }
            SVProgressHUD.dismiss()
            let aOtpVC = self.storyboard?.instantiateViewController(withIdentifier: "OtpVC") as! OtpVC
            aOtpVC.pathValue = "SignUp"
            aOtpVC.PhoneNumber = "\(self.lblCountryCode.text!)\(self.tfMobileNumber.text!)"
            self.navigationController?.pushViewController(aOtpVC, animated: true)
            
            
            let UserDefaulVerify = UserDefaults.standard
            UserDefaulVerify.set(verificationID!, forKey: "verificationVal")
            UserDefaulVerify.synchronize()
            
            // Sign in using the verificationID and the code sent to the user
            // ...
        }
    }
    
    // a picker item was selected
    
    func countryPhoneCodePicker(_ picker: MRCountryPicker, didSelectCountryWithName name: String, countryCode: String, phoneCode: String, flag: UIImage) {
        self.lblCountryCode.text = phoneCode
        self.ivCountryFlag.image = flag
    }
    
}

class AlertViewData: UIView {
    
    override func `self`() -> Self {
        frame = CGRect(x: 0, y: 0, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        return self
    }
}
