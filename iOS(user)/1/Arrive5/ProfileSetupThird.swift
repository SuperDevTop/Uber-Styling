//
//  ProfileSetupThird.swift
//  Arrive5
//
//  Created by Rahul on 20/07/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class ProfileSetupThird: UIViewController {
    
    var validStr : String!
    
    var validEmail : String!
    var validOption : String!


    override func viewDidLoad() {
        super.viewDidLoad()
        weeklyBtn.isOn = true
        validStr = "1"

    }



    @IBAction func backBtn(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBOutlet var weeklyBtn: UISwitch!
    @IBAction func weeklypress(_ sender: Any) {
        if weeklyBtn.isOn {
            validStr = "1"
            mothlyBtn.isOn = false
        } else{
            mothlyBtn.isOn = true
            validStr = "2"
        }
    }
    
    @IBOutlet var mothlyBtn: UISwitch!
    @IBAction func monthlypress(_ sender: Any) {
        if mothlyBtn.isOn {
            validStr = "2"
            weeklyBtn.isOn = false

        } else{
            weeklyBtn.isOn = true
            validStr = "1"
        }
    }
    
    @IBAction func nextAction(_ sender: Any) {

        let url = "http://arrive5.pcthepro.com/webservice/user/add_business_profile"
        let user_id = UserDefaults.standard.string(forKey: "user_id")
        let dictData : [String : AnyObject]!
        dictData = ["user_id" : user_id,
                    "email": validEmail,
                    "payment_method_type": validOption,
                    "type": "2",
                    "report_status" : validStr] as [String : AnyObject]
        print(dictData)
        
        APIManager.requestPOSTURL(url, params: dictData, headers: nil, success: {(json) in
            print(json)
            if json["status"].rawString() == "true"{
                self.view.makeToast(json["message"].rawString())
                DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
                    let storyboard = UIStoryboard(name: "Main", bundle: nil)
                    let vc = storyboard .instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                    self.navigationController? .pushViewController(vc, animated: true)
                }
                
            }
            else{
                self.view.makeToast(json["message"].rawString())
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
        })
    }
    
}
