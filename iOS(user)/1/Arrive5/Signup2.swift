//
//  Signup2.swift
//  Arrive5
//
//  Created by Rahul on 28/09/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class Signup2: UIViewController {

    @IBOutlet var txtEmail: UITextField!
    let appDelegate = UIApplication.shared.delegate as! AppDelegate

    override func viewDidLoad() {
        super.viewDidLoad()

    }
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    @IBAction func nextPress(_ sender: Any) {
        if self.txtEmail.text == ""{
            self.view.makeToast("Enter Email")
        }else if isValidEmail(testStr: txtEmail.text!) == false{
            self.view.makeToast("Enter Valid Email")
        }else{
            appDelegate.tfEmail = self.txtEmail.text
            let signup3 = self.storyboard?.instantiateViewController(withIdentifier: "Signup3") as! Signup3
            self.navigationController?.pushViewController(signup3, animated: true)
        }
        
    }
    
    func isValidEmail(testStr:String) -> Bool {
        
        print("validate emilId: \(testStr)")
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"
        let emailTest = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
        let result = emailTest.evaluate(with: testStr)
        return result
        
    }
    
    
}
