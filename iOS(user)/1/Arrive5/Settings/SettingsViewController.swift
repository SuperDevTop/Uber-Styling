//
//  SettingsViewController.swift
//  Arrive5
//
//  Created by Parangat Air 1 on 5/31/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import FirebaseAuth

class SettingsViewController: UIViewController {
     @IBOutlet weak var imgUser : UIImageView!
     @IBOutlet weak var lblUserName : UILabel!
     @IBOutlet weak var lblEmail : UILabel!
     @IBOutlet weak var lblPhone : UILabel!
    let appDelegate = UIApplication.shared.delegate as! AppDelegate

    override func viewDidLoad() {
        super.viewDidLoad()
        imgUser.layer.masksToBounds = true
        imgUser.layer.cornerRadius = imgUser.frame.height/2
        
        let aImgUrl = UserDefaults.standard.string(forKey: "img_url")
        APIManager.requestImage(path: aImgUrl!, completionHandler: {(image) in
            self.imgUser.image = image
        })
        lblEmail.text = UserDefaults.standard.string(forKey:"email")
        lblUserName.text = UserDefaults.standard.string(forKey: "name")
        lblPhone.text = UserDefaults.standard.string(forKey: "mobile")
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func btnbackPressed (_sender:Any){
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func btnChangePassword (_sender:Any){
       
    }
    
    @IBAction func btnEveryRidePressed (_sender:Any){
        let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "EveryRideViewController") as! EveryRideViewController
        self.navigationController?.pushViewController(homeVc, animated: true)
    }
    
    @IBAction func btnBusinessProfilePressed (_sender:Any){
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let vc = storyboard .instantiateViewController(withIdentifier: "ProfileSetup") as! ProfileSetup
        self.navigationController? .pushViewController(vc, animated: true)
        
    }
    
    
    
    @IBAction func LogoutBtnAcitons(_ sender: UIButton)
    {
         self.signOut()
    }
    
    
    func signOut()
       {
           let value = UserDefaults.standard
           value.removeObject(forKey: "user_id")
           value.synchronize()
           let firAuth = Auth.auth()
           do
           {
               try firAuth.signOut()
               self.navigationController?.popToRootViewController(animated: true)
           }
           catch
           {
               self.navigationController?.popToRootViewController(animated: true)
               print("Error")
           }
       }
    
}
