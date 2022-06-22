//
//  RideOverviewVC.swift
//  Arrive5Driver
//
//  Created by Joy on 02/05/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class RideOverviewVC: UIViewController {

    @IBOutlet weak var lblUserName: UILabel!
    @IBOutlet weak var ivUserProfile: UIImageView!
    
    @IBOutlet weak var lblDropOffLocVal: UILabel!
    @IBOutlet weak var lblPickUpVal: UILabel!
    @IBOutlet weak var vwRatingVal: CosmosView!
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
        self.lblUserName.text = appDelegate.userInfo["gcm.notification.userName"] as? String
        self.lblDropOffLocVal.text = appDelegate.userInfo["gcm.notification.end_point"] as? String
        self.lblPickUpVal.text = appDelegate.userInfo["gcm.notification.start_point"] as? String
        
        
        if let savedValue = UserDefaults.standard.string(forKey: "check") {
            if(savedValue .elementsEqual("1"))
            {
                
            }
            else
            
            {
                let aImgPath = appDelegate.userInfo["gcm.notification.userImg"] as? String
                self.ivUserProfile.layer.cornerRadius = self.ivUserProfile.frame.height/2
                self.ivUserProfile.layer.masksToBounds = true
                self.ivUserProfile.contentMode = .scaleAspectFill
                APIManager.requestImage(path: aImgPath!, completionHandler: {(usrImg) in
                    self.ivUserProfile.image = usrImg
                })
            }
        
        }
    }
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    @IBAction func btnCallAction(_ sender: UIButton) {
        
    }

    @IBAction func btnCancelTrip(_ sender: UIButton) {
        self.CancelPopScreen()
    }
    
    func CancelPopScreen(){
        let CancelOptionVC = self.storyboard?.instantiateViewController(withIdentifier: "CancelOptionVC") as! CancelOptionVC
        
        addChild(CancelOptionVC)
        
        CancelOptionVC.didMove(toParent: self)
        
        CancelOptionVC.view.frame = CGRect(x: 0,
                                             y: self.view.frame.size.height,
                                             width: self.view.frame.size.width,
                                             height: self.view.frame.size.height)
        self.view.addSubview(CancelOptionVC.view)
        
        UIView.animate(withDuration: 0.3) {
            CancelOptionVC.view.frame = self.view.bounds
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
