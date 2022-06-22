//
//  ProfileSetup.swift
//  Arrive5
//
//  Created by Rahul on 20/07/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
class ProfileSetup: UIViewController {

    @IBOutlet weak var txtemail: UITextField!
    override func viewDidLoad() {
        super.viewDidLoad()

    }

 
    @IBAction func backBtn(_ sender: Any) {
     self.navigationController?.popViewController(animated: true)
    }
    
    func isValidEmail(testStr:String) -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        
        let emailTest = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
        return emailTest.evaluate(with: testStr)
    }
    
    
    @IBAction func nextAction(_ sender: Any) {
        if isValidEmail(testStr: txtemail.text!){
            
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let vc = storyboard .instantiateViewController(withIdentifier: "ProfileSetupSecond") as! ProfileSetupSecond
            vc.strEmail = txtemail.text!
            self.navigationController? .pushViewController(vc, animated: true)
            
        }else{
            self.view.makeToast("Enter Valid Email")
        }
       
        
    }
    

}
