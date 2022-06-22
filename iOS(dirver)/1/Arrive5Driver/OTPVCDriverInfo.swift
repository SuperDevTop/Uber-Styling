//
//  OTPVCDriverInfo.swift
//  Arrive5Driver
//
//  Created by Joy on 17/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import FirebaseAuth

class OTPVCDriverInfo: UIViewController,UITextFieldDelegate{
    
    @IBOutlet weak var lblSentToNumber: UILabel!
    @IBOutlet weak var tfOtpFirst: UITextField!
    @IBOutlet weak var tfOtpSecond: UITextField!
    @IBOutlet weak var tfOtpThird: UITextField!
    @IBOutlet weak var tfOtpFourth: UITextField!
    @IBOutlet weak var tfOtpFifth: UITextField!
    @IBOutlet weak var tfOtpSixth: UITextField!
    
    var tfWorking : UITextField!
    var PhoneNumber : String!
    var pathValue : String!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        lblSentToNumber.text = "Sent To \(PhoneNumber!)"
        tfWorking = tfOtpFirst
        
        tfOtpFirst.addTarget(self, action: #selector(OTPVCDriverInfo.textFieldDidChange(textField:)), for: UIControl.Event.editingChanged)
        tfOtpSecond.addTarget(self, action: #selector(OTPVCDriverInfo.textFieldDidChange(textField:)), for: UIControl.Event.editingChanged)
        tfOtpThird.addTarget(self, action: #selector(OTPVCDriverInfo.textFieldDidChange(textField:)), for: UIControl.Event.editingChanged)
        tfOtpFourth.addTarget(self, action: #selector(OTPVCDriverInfo.textFieldDidChange(textField:)), for: UIControl.Event.editingChanged)
        tfOtpFifth.addTarget(self, action: #selector(OTPVCDriverInfo.textFieldDidChange(textField:)), for: UIControl.Event.editingChanged)
        tfOtpSixth.addTarget(self, action: #selector(OTPVCDriverInfo.textFieldDidChange(textField:)), for: UIControl.Event.editingChanged)
        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    // MARK: - TextFieldDelegates
    // MARK: -
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        guard let text = textField.text else { return true }
        let newLength = text.characters.count + string.characters.count - range.length
        return newLength <= 1 // Bool
    }
    
     @objc func textFieldDidChange(textField: UITextField){
        
        let text = textField.text
        
        if (text?.utf16.count)! >= 1{
            switch textField{
            case tfOtpFirst:
                tfOtpSecond.becomeFirstResponder()
            case tfOtpSecond:
                tfOtpThird.becomeFirstResponder()
            case tfOtpThird:
                tfOtpFourth.becomeFirstResponder()
            case tfOtpFourth:
                tfOtpFifth.becomeFirstResponder()
            case tfOtpFifth:
                tfOtpSixth.becomeFirstResponder()
            case tfOtpSixth:
                tfOtpSixth.resignFirstResponder()
            default:
                break
            }
        }else if (text?.utf16.count)! == 0{
            switch textField{
            case tfOtpFirst:
                print("first")
            case tfOtpSecond:
                tfOtpFirst.becomeFirstResponder()
            case tfOtpThird:
                tfOtpSecond.becomeFirstResponder()
            case tfOtpFourth:
                tfOtpThird.becomeFirstResponder()
            case tfOtpFifth:
                tfOtpFourth.becomeFirstResponder()
            case tfOtpSixth:
                tfOtpFifth.becomeFirstResponder()
            default:
                break
            }
        }
    }
    // MARK: - Button Actions
    // MARK: -
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    
    @IBAction func btnResendCode(_ sender: UIButton) {
        
        verifyWithFirebase(phonenumber: PhoneNumber)
        
    }
    @IBAction func btnNextCode(_ sender: UIButton) {
        self.OtpVerification()
        
    }
    
    
    // MARK: - Methods
    // MARK: -
    
    func verifyWithFirebase(phonenumber : String)
    {
        PhoneAuthProvider.provider().verifyPhoneNumber(phonenumber, uiDelegate: nil) { (verificationID, error) in
            if let error = error {
                print(error.localizedDescription)
                self.view.makeToast(error.localizedDescription)
                //                self.showMessagePrompt(error.localizedDescription)
                return
            }
            
            
            let UserDefaulVerify = UserDefaults.standard
            UserDefaulVerify.set(verificationID!, forKey: "verificationVal")
            UserDefaulVerify.synchronize()
            
            // Sign in using the verificationID and the code sent to the user
            // ...
        }
    }
    
    func OtpVerification(){
        let otpText = "\(self.tfOtpFirst.text!)\(self.tfOtpSecond.text!)\(self.tfOtpThird.text!)\(self.tfOtpFourth.text!)\(self.tfOtpFifth.text!)\(self.tfOtpSixth.text!)"
        print(otpText)
        let credentials : PhoneAuthCredential = PhoneAuthProvider.provider().credential(withVerificationID: UserDefaults.standard.value(forKey: "verificationVal") as! String, verificationCode: otpText)
        Auth.auth().signIn(with: credentials) { (user, error) in
            if (error != nil)
            {
                self.view.makeToast(error.debugDescription)
                print(error.debugDescription)
            }
            else
            {
//                if self.pathValue == "SignUp"{
//                    let profileEdit = self.storyboard?.instantiateViewController(withIdentifier: "EditProfileVC") as! EditProfileVC
//                    self.navigationController?.pushViewController(profileEdit, animated: true)
//                }else{
//                    let ConfirmPasswordVC = self.storyboard?.instantiateViewController(withIdentifier: "ConfirmPasswordVC") as! ConfirmPasswordVC
//                    self.navigationController?.pushViewController(ConfirmPasswordVC, animated: true)
//                }
                
                //                self.txtVerifyOtp.resignFirstResponder()
                print("Phone Number :-", user?.phoneNumber! as Any)
                let userinfo = user?.providerData[0]
                print(userinfo?.providerID as Any)
                //                self.view.makeToast("Logged in successfully")
            }
        }
    }
    
}
