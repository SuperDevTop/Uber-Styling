//
//  ProfileSetupSecond.swift
//  Arrive5
//
//  Created by Rahul on 20/07/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class ProfileSetupSecond: UIViewController {
    var strEmail :String!
    var strMethod :String!

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

  
    @IBOutlet var creditBtn: UIButton!
    @IBAction func CreditPress(_ sender: Any) {
        creditBtn.setImage(#imageLiteral(resourceName: "radioSelect"), for: .normal)
        cashBtn.setImage(#imageLiteral(resourceName: "RadioCircle"), for: .normal)
        strMethod = "1"
    }
    
    @IBOutlet var cashBtn: UIButton!
    
    @IBAction func CashPress(_ sender: Any) {
        cashBtn.setImage(#imageLiteral(resourceName: "radioSelect"), for: .normal)
        creditBtn.setImage(#imageLiteral(resourceName: "RadioCircle"), for: .normal)
        strMethod = "2"
    }
    
    
    @IBAction func backBtn(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func nextAction(_ sender: Any) {
        if strMethod == "1" || strMethod == "2" {
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let vc = storyboard .instantiateViewController(withIdentifier: "ProfileSetupThird") as! ProfileSetupThird
            vc.validEmail = strEmail
            vc.validOption = strMethod

            self.navigationController? .pushViewController(vc, animated: true)
            
        }else{
            self.view.makeToast("Select Option")
        }
    
    }

}
