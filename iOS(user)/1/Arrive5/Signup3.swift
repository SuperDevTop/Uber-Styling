//
//  Signup3.swift
//  Arrive5
//
//  Created by Rahul on 28/09/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class Signup3: UIViewController {

    @IBOutlet var txtPassword: UITextField!
    
    @IBOutlet var Confirmpassword: UITextField!
    let appDelegate = UIApplication.shared.delegate as! AppDelegate

    override func viewDidLoad() {
        super.viewDidLoad()

    }
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    @IBAction func nextPress(_ sender: Any) {
        if self.txtPassword.text == ""{
            self.view.makeToast("Enter Password")
            
        }
            
        else if self.Confirmpassword.text == ""{
            self.view.makeToast("Enter Confirmpasword")
            
        }
        else if self.Confirmpassword.text != txtPassword.text
        {
            self.view.makeToast("Password and confirm Password are not match")
            
        }
            
            
        
        else{
            appDelegate.tfPassword = self.txtPassword.text
            let signup4 = self.storyboard?.instantiateViewController(withIdentifier: "Signup4") as! Signup4
             signup4.statuss = "no"
            self.navigationController?.pushViewController(signup4, animated: true)
        }
        
    }
    

}
