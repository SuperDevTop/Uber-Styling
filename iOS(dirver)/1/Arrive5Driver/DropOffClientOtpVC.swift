//
//  DropOffClientOtpVC.swift
//  Arrive5Driver
//
//  Created by Joy on 02/05/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class DropOffClientOtpVC: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var lblUserName: UILabel!
    @IBOutlet weak var ivUserProfile: UIImageView!
    @IBOutlet weak var lblPickUpAddress: UILabel!
    
    @IBOutlet weak var tfFirst: UITextField!
    @IBOutlet weak var tfSecond: UITextField!
    @IBOutlet weak var tfThird: UITextField!
    @IBOutlet weak var tfFourth: UITextField!
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    let usrId  = UserDefaults.standard.value(forKey: "user_id") as! String
//    var bookingId : Int = 0
    var userId : String = ""
     var bookingId : String = ""
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.lblPickUpAddress.text = self.appDelegate.userInfo["gcm.notification.start_point"] as? String
        self.lblUserName.text = self.appDelegate.userInfo["gcm.notification.userName"] as? String
        
        
        tfFirst.delegate = self
        tfSecond.delegate = self
        tfThird.delegate = self
        tfFourth.delegate = self
        
        
//        tfFirst.addTarget(self, action: #selector(DropOffClientOtpVC.textFieldDidChange(_:)), for: .editingChanged)


        
        tfFirst.addTarget(self, action: #selector(DropOffClientOtpVC.textFieldDidChange(textField:)), for: UIControl.Event.editingChanged)
        tfSecond.addTarget(self, action: #selector(DropOffClientOtpVC.textFieldDidChange(textField:)), for: UIControl.Event.editingChanged)
        tfThird.addTarget(self, action: #selector(DropOffClientOtpVC.textFieldDidChange(textField:)), for: UIControl.Event.editingChanged)
        tfFourth.addTarget(self, action: #selector(DropOffClientOtpVC.textFieldDidChange(textField:)), for: UIControl.Event.editingChanged)

        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
        
                let aImgPath = appDelegate.userInfo["gcm.notification.userImg"] as? String
                self.ivUserProfile.layer.cornerRadius = self.ivUserProfile.frame.height/2
                self.ivUserProfile.layer.masksToBounds = true
                self.ivUserProfile.contentMode = .scaleAspectFill
                APIManager.requestImage(path: aImgPath!, completionHandler: {(usrImg) in
                    self.ivUserProfile.image = usrImg
                })
                self.bookingId = appDelegate.userInfo["gcm.notification.booking_rand"] as! String
                self.userId = appDelegate.userInfo["gcm.notification.user_id"] as! String
            }
            
    
        
        
      
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnCloseAction(_ sender: UIButton) {
        
        self.ClosingAction()
        
    }

    @IBAction func btnCancelAction(_ sender: UIButton) {
        self.ClosingAction()
    }
    @IBAction func btnSubmitAction(_ sender: UIButton) {
        
        if tfFirst.text == ""{
            self.view.makeToast("Please Enter All Otp Digits")
        }else{
            if tfSecond.text == ""{
                self.view.makeToast("Please Enter All Otp Digits")
            }else{
                if tfThird.text == ""{
                    self.view.makeToast("Please Enter All Otp Digits")
                }else{
                    if tfFourth.text == ""{
                        self.view.makeToast("Please Enter All Otp Digits")
                    }else{
                        self.checkOtpVC()
                    }
                }
            }
        }
    }
    
    func checkOtpVC(){
        let aStrAPi = "\(Constant.API.kOtpVerification)"
        let aDictParam : [String:Any]!
        aDictParam = ["driver_id":usrId,
                      "user_id":self.userId,
                      "booking_id":self.bookingId,
                      "otp":"\(self.tfFirst.text!)\(self.tfSecond.text!)\(self.tfThird.text!)\(self.tfFourth.text!)"]
        
        APIManager.requestPOSTURL(aStrAPi, params: aDictParam as [String : AnyObject]?, headers: nil, success: {(json) in
            
            if json["status"].rawString() == "true"{
                let DropOffVC = self.storyboard?.instantiateViewController(withIdentifier: "DropOffVC") as! DropOffVC
                
                self.navigationController?.pushViewController(DropOffVC, animated: true)
            }else{
                
                self.view.makeToast(json["message"].rawString())
            }
            
        }, failure: { (error) in
            self.view.makeToast(error.localizedDescription)
            
        })
        
        
//        user_id, booking_id, driver_id			
        
    }
    
    // MARK: - TextFieldDelegates
    // MARK: -
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        guard let text = textField.text else { return true }
        let newLength = text.count + string.count - range.length
        return newLength <= 1 // Bool
    }
    
    @objc func textFieldDidChange(textField: UITextField){
        
        let text = textField.text
        
        if (text?.utf16.count)! >= 1{
            switch textField{
            case tfFirst:
                tfSecond.becomeFirstResponder()
            case tfSecond:
                tfThird.becomeFirstResponder()
            case tfThird:
                tfFourth.becomeFirstResponder()
            case tfFourth:
                tfFourth.resignFirstResponder()
            default:
                break
            }
        }else if (text?.utf16.count)! == 0{
            switch textField{
            case tfFirst:
                print("first")
            case tfSecond:
                tfFirst.becomeFirstResponder()
            case tfThird:
                tfSecond.becomeFirstResponder()
            case tfFourth:
                tfThird.becomeFirstResponder()
            default:
                break
            }
        }
    }
    
    func ClosingAction(){
        UIView.animate(withDuration: 0.3, animations: {
            self.view.frame = CGRect(x: 0,
                                     y: self.view.frame.size.height,
                                     width: self.view.frame.size.width,
                                     height: self.view.frame.size.height)
        }) { (bool) in
            self.willMove(toParent: nil)
            self.view.reloadInputViews()
            self.view.removeFromSuperview()
            self.removeFromParent()
        }
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
