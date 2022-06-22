//
//  PromoCodeViewController.swift
//  Arrive5
//
//  Created by Parangat Air 1 on 5/30/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class PromoCodeViewController: UIViewController {
    @IBOutlet weak var txtPromo : UITextField!

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    @IBAction func btnBackPressed (_sender : Any){
        self.navigationController? . popViewController(animated: true)
    }
    
    @IBAction func btnsubmit (_sender : Any){
        if txtPromo.text == ""{
            self.view.makeToast("Please Enter Promo Code")
        }else{
            applyPromo()
        }
    }
    
    func applyPromo(){
        let aStrApi = "\(Constant.API.KPromo)"
        let id = UserDefaults.standard.string(forKey: "user_id")
        let promo = txtPromo.text
        let dictData : [String : AnyObject]!
        dictData = ["id" : id,
                    "promo":promo] as [String : AnyObject]
        
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
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
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}
