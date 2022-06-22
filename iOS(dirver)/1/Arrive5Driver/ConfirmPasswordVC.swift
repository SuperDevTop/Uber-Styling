//
//  ConfirmPasswordVC.swift
//  Arrive5Driver
//
//  Created by Joy on 12/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class ConfirmPasswordVC: UIViewController,UITextFieldDelegate {
    
    @IBOutlet weak var tfConfirmPassword: UITextField!
    @IBOutlet weak var tfNewPassword: UITextField!
    @IBOutlet weak var btnEditActionNewPassword: UIButton!
    @IBOutlet weak var btnEditConfirmPassword: UIButton!
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tfConfirmPassword.delegate = self
        self.tfNewPassword.delegate = self
        btnEditActionNewPassword.isHidden = true
        btnEditConfirmPassword.isHidden = true
        
        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    @IBAction func btnEditNewAction(_ sender: UIButton) {
        self.tfNewPassword.text = ""
    }
    
    @IBAction func btnEditConfirmAction(_ sender: UIButton) {
        self.tfConfirmPassword.text = ""
    }
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    @IBAction func btnConfirmPasswordAction(_ sender: UIButton) {
        
        if self.tfNewPassword.text == self.tfConfirmPassword.text{
            let aUserId = UserDefaults.standard.value(forKey: "user_id")!
            self.Change_Password(aUserId as! String, self.tfNewPassword.text!)
            
        }else{
            self.view.makeToast("Phone Number Do Not Match")
        }
    }
    
    func Change_Password(_ UserId:String!,_ Password:String!){
        let aStrApi = "\(Constant.API.kChange_Password)"
        let dictData : [String : AnyObject]!
        dictData = ["id" : UserId,
                    "password":Password] as [String : AnyObject]
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            if json["status"].rawString() == "false"{
                let value = UserDefaults.standard
                value.removeObject(forKey: "user_id")
                value.removeObject(forKey: "UserRegistered")
                value.synchronize()
                self.view.makeToast(json["msg"].rawString())
            }else{
                self.navigationController?.popToRootViewController(animated: true)
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if textField == tfNewPassword{
            self.btnEditActionNewPassword.isHidden = false
            self.btnEditConfirmPassword.isHidden = true
        }else{
            self.btnEditActionNewPassword.isHidden = true
            self.btnEditConfirmPassword.isHidden = false
        }
    }
    func textFieldDidEndEditing(_ textField: UITextField) {
        self.btnEditActionNewPassword.isHidden = true
        self.btnEditConfirmPassword.isHidden = true
    }
    
}
