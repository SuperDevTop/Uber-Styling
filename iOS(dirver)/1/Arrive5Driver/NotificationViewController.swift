//
//  NotificationViewController.swift
//  Arrive5Driver
//
//  Created by Test on 11/09/1941 Saka.
//  Copyright © 1941 Apple Inc. All rights reserved.
//

import UIKit

import Alamofire
import SwiftyJSON
import SVProgressHUD

class NotificationViewController: UIViewController,UITableViewDelegate, UITableViewDataSource {
    @IBOutlet var tableviews: UITableView!
    
     var ArrVehicleModel : [Any] = []
    var driverId : String!
     var timess : String!
     var datess : String!
     let appDelegate = UIApplication.shared.delegate as! AppDelegate
    override func viewDidLoad() {
        super.viewDidLoad()
        
//        callingAPiData()
        
        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        SVProgressHUD.show()
        gettingUserData()
         self.GetModal()
         self.tableviews.isHidden = true;
    }
   
    

    
        func gettingUserData(){
            let userDetail = appDelegate.userDetail
            self.driverId = UserDefaults.standard.value(forKey: "user_id") as? String
        }
    
@IBAction func btnActionBack(_ sender: UIButton)
{
    let HomeSideMenuVC = self.storyboard?.instantiateViewController(withIdentifier: "HomeSideMenuVC") as! HomeSideMenuVC
    HomeSideMenuVC.PathDirection = "NotificationViewController"
    addChild(HomeSideMenuVC)
    
    HomeSideMenuVC.didMove(toParent: self)
    
    UIView.animate(withDuration: 0.5, delay: 1.0, usingSpringWithDamping: 0.1, initialSpringVelocity: 0.0, options: .transitionFlipFromLeft, animations: {
        HomeSideMenuVC.view.frame = CGRect(x: self.view.frame.minX,
                                           y: 0,
                                           width: self.view.frame.size.width ,
                                           height: self.view.frame.size.height)
    }, completion: {(bool) in
        self.view.addSubview(HomeSideMenuVC.view)
        HomeSideMenuVC.view.frame = self.view.bounds
    })
    }
  
     
    func GetModal(){
        _ = driverId
        let aStrApi = "\(Constant.API.notification)"
        let aToken = appDelegate.deviceTokenString ?? "93283232932098231902130843980jndsjn0208"
        let dictData : [String : AnyObject]!
        dictData = ["driver_id" : driverId,] as [String : AnyObject]
        
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            
            
            if json["status"].rawString() == "true"
            {
                self.ArrVehicleModel = json["result"].rawValue as! [Any]
                if self.ArrVehicleModel.count == 0
                {
                    self.view.makeToast(json["msg"].rawString())
                    SVProgressHUD.dismiss()
                    self.tableviews.isHidden = true;
                }
                else

                {
                    SVProgressHUD.dismiss()
                    self.tableviews.isHidden = false;
                    self.tableviews.reloadData()
                }
                print("Hello Swift")

            }
            else
            {
                print("Hello objective")

                self.view.makeToast(json["msg"].rawString())
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    

    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return ArrVehicleModel.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell = tableviews.dequeueReusableCell(withIdentifier: "NotificationTableViewCell") as! NotificationTableViewCell
        
        let dict: [String : Any] = ArrVehicleModel[indexPath.row] as! [String : Any]
        cell.NotificationLabel.text = dict["notification_msg"] as? String
        self.timess = dict["notification_time"] as? String
        self.datess = dict["notification_date"] as? String
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        let appendString2 = timess + datess

        cell.NotificationDate.text = appendString2
        //UITableView
       
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    }
    
    
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
   

}
