//
//  ConfirmArrivalVC.swift
//  Arrive5Driver
//
//  Created by Joy on 02/05/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class ConfirmArrivalVC: UIViewController {

    @IBOutlet weak var ivDriverVal: UIImageView!
    @IBOutlet weak var lblArrivalPoint: UILabel!
    @IBOutlet weak var btnConfirmPoint: UIButton!
    
    var pathValue : String = ""
     var savedValue : String = ""
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    let usrId  = UserDefaults.standard.value(forKey: "user_id") as! String
//    var bookingId : Int = 0
    var userId : String = ""
     var bookingId :  String = ""

    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
       

        if pathValue == "ArriveClient"{
            self.lblArrivalPoint.text = "Have You Arrived at client's location?"
            self.btnConfirmPoint.setImage(UIImage(named:"confirm_arrival_button"), for: .normal)
            
        }else{
            self.lblArrivalPoint.text = "Did you drop off client?"
            self.btnConfirmPoint.setImage(UIImage(named:"confirm_drop_button"), for: .normal)
        }
//        booking_id
//        self.bookingId = appDelegate.userInfo["booking_id"] as! Int
        
       
                let aImgPath = appDelegate.userInfo["gcm.notification.userImg"] as? String
                self.ivDriverVal.layer.cornerRadius = self.ivDriverVal.frame.height/2
                self.ivDriverVal.layer.masksToBounds = true
                self.ivDriverVal.contentMode = .scaleAspectFill
                APIManager.requestImage(path: aImgPath!, completionHandler: {(usrImg) in
                    self.ivDriverVal.image = usrImg
                    
             
                    
                })
                self.bookingId = appDelegate.userInfo["gcm.notification.booking_rand"] as! String
                self.userId = appDelegate.userInfo["gcm.notification.user_id"] as! String
            }
            
    
        


    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnCancelAction(_ sender: UIButton) {
        self.ClosingAction()
    }

    @IBAction func btnCloseAction(_ sender: UIButton) {
        self.ClosingAction()
    }
    @IBAction func btnConfirmArrivalAction(_ sender: UIButton) {
        if pathValue == "ArriveClient"{
            let PickUpClientVC = self.storyboard?.instantiateViewController(withIdentifier: "PickUpClientVC") as! PickUpClientVC
            
            self.navigationController?.pushViewController(PickUpClientVC, animated: true)
        }else{
            self.callDropOffApiVal()
            
        }
        
    }
    
    
    
    func callDropOffApiVal(){
        
        let aStrApi = "\(Constant.API.kFinishRide)"
        let aDictParam : [String:Any]!
        aDictParam = ["driver_id":usrId,
                      "user_id":self.userId,
                      "booking_id":self.bookingId]
        
        APIManager.requestPOSTURL(aStrApi, params: aDictParam as [String : AnyObject]?, headers: nil, success: {(json) in
            
            if json["status"].rawString() == "true"{
                let BillingDetailVC = self.storyboard?.instantiateViewController(withIdentifier: "BillingDetailVC") as! BillingDetailVC
                
                self.navigationController?.pushViewController(BillingDetailVC, animated: true)
            }else{
                
            }
            
        }, failure: { (error) in
            self.view.makeToast(error.localizedDescription)
            
        })
        
        
    }
    
    
    func ClosingAction(){
        UIView.animate(withDuration: 0.3, animations: {
            self.view.frame = CGRect(x: 0,
                                     y: self.view.frame.size.height,
                                     width: self.view.frame.size.width,
                                     height: self.view.frame.size.height)
        }) { (bool) in
            self.willMove(toParent: nil)
            self.view.reloadInputViews()
            self.view.removeFromSuperview()
            self.removeFromParent()
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
