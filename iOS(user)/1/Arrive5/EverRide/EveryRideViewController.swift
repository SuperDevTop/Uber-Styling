//
//  EveryRideViewController.swift
//  Arrive5
//
//  Created by Parangat Air 1 on 5/31/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit


class EveryRideViewController: UIViewController {
    @IBOutlet weak var viewSelectCause : UIView!

    override func viewDidLoad() {
        super.viewDidLoad()
        viewSelectCause.layer.borderWidth = 1.0
        viewSelectCause.layer.borderColor = UIColor(red: 42.0/255.0, green: 260.0/255.0, blue: 231.0/255.0, alpha: 1).cgColor
        viewSelectCause.layer.cornerRadius = 5.0
        viewSelectCause.clipsToBounds = true
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnBackPressed (_sender : Any){
        self.navigationController? . popViewController(animated: true)
    }
    
    @IBAction func btnDonatePressed (_sender : Any){
        let user_id = UserDefaults.standard.string(forKey: "user_id")
        let aStrApi = "\(Constant.API.KDonate)"
        let dictData : [String : AnyObject]!
        dictData = ["user_id" : user_id! ] as [String : AnyObject]
        print(dictData)
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            if json["status"].rawString() == "true"{
                let message = json["message"].rawString()
                let alertController = UIAlertController(title: "Arrive5", message: message, preferredStyle: .alert)
                let OKAction = UIAlertAction(title: "OK", style: .default) { (action:UIAlertAction!) in
                    let storyboard = UIStoryboard(name: "Main", bundle: nil)
                    let vc = storyboard .instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                    self.navigationController? .pushViewController(vc, animated: true)
                    
                }
                alertController.addAction(OKAction)
                self.present(alertController, animated: true, completion:nil)
                
            }else{
                self.view.makeToast(json["msg"].rawString())
            }
        }, failure: {(error) in
            
        })
    }
}
