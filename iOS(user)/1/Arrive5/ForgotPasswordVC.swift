//
//  ForgotPasswordVC.swift
//  Arrive5
//
//  Created by Joy on 06/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import FirebaseAuth
import MRCountryPicker
import SVProgressHUD

class ForgotPasswordVC: UIViewController, MRCountryPickerDelegate,UITextFieldDelegate{
    
    @IBOutlet weak var vwSelectedPicker: MRCountryPicker!
    @IBOutlet var vwCountryPickerView: UIView!
    @IBOutlet weak var btnEditTextField: UIButton!
    @IBOutlet weak var tfMobileNumber: UITextField!
    @IBOutlet weak var ivCountryFlag: UIImageView!
    @IBOutlet weak var lblCountryCode: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.btnEditTextField.isHidden = true
        self.tfMobileNumber.delegate = self
        vwSelectedPicker.countryPickerDelegate = self
        vwSelectedPicker.showPhoneNumbers = true
        vwSelectedPicker.setCountry("SI")
        vwSelectedPicker.setCountryByName("India")
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func btnEditAction(_ sender: UIButton) {
        self.tfMobileNumber.text = ""
    }
    
    @IBAction func btnForgotAction(_ sender: UIButton) {
        
        if self.tfMobileNumber.text == ""{
            self.view.makeToast("Enter Your Mobile number")
        }else{
            self.verifyApi("\(self.lblCountryCode.text!)\(self.tfMobileNumber.text!)")
        }
        
    }
    
    @IBAction func btnCountryView(_ sender: UIButton) {
        
        view.addSubview(vwCountryPickerView)
        AlertViewData.animate(withDuration: 0.0, delay: 0.0, usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwCountryPickerView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY+UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            AlertViewData.animate(withDuration: 0.5, delay: 0.4, usingSpringWithDamping: 1.0,
                                  initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwCountryPickerView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY-UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)}, completion: { (finished: Bool) in
            })
        })
        
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
            self.btnEditTextField.isHidden = false
        }else{
            self.btnEditTextField.isHidden = true
        }
    }
    func textFieldDidEndEditing(_ textField: UITextField) {
        self.btnEditTextField.isHidden = true
    }
    
    func verifyApi(_ phoneNo:String!){
        let aStrApi = "\(Constant.API.kVerify)"
        let dictData : [String : AnyObject]!
        dictData = ["mobileno" : phoneNo] as [String : AnyObject]
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            if json["status"].rawString() == "false"{
                self.view.makeToast(json["msg"].rawString())
            }else{
                
                let userDetail = json["details"].rawValue as! [String:Any]
                let userId = userDetail["id"] as! String
                let userDefaultId = UserDefaults.standard
                userDefaultId.set(userId, forKey: "user_id")
                userDefaultId.synchronize()
                
                SVProgressHUD.show(withStatus: "Please Wait")
                self.verifyWithFirebase(phonenumber: "\(self.lblCountryCode.text!)\(self.tfMobileNumber.text!)")
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
            let UserDefaulVerify = UserDefaults.standard
            UserDefaulVerify.set(verificationID!, forKey: "verificationVal")
            let aOtpVC = self.storyboard?.instantiateViewController(withIdentifier: "OtpVC") as! OtpVC
            aOtpVC.pathValue = "ForgotPassword"
            aOtpVC.PhoneNumber = phonenumber
            self.navigationController?.pushViewController(aOtpVC, animated: true)
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
