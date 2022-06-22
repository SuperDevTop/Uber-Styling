//
//  EnRouteViewController.swift
//  Arrive5
//
//  Created by Parangat Air 1 on 5/30/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces

class MapCell: UITableViewCell {
    @IBOutlet weak var vwGoogleMap: GMSMapView!
    @IBOutlet weak var lblDriverName : UILabel!
    @IBOutlet weak var imgDriver : UIImageView!
    @IBOutlet weak var lblRating : UILabel!
    @IBOutlet weak var lblCarName : UILabel!
    @IBOutlet weak var lblCarNumber : UILabel!
    @IBOutlet weak var lblOTP : UILabel!
    
}
class btnCell: UITableViewCell {
    @IBOutlet weak var btnContact : UIButton!
    @IBOutlet weak var btnFareSplit : UIButton!
    @IBOutlet weak var btnCancel : UIButton!
    @IBOutlet weak var btnShare : UIButton!
    
}
class TripDetailCell: UITableViewCell {
    @IBOutlet weak var lblAddress : UILabel!
    @IBOutlet weak var lblTime : UILabel!
}

class PaymentCardTableViewCell: UITableViewCell {
    @IBOutlet weak var lblAmount : UILabel!
    @IBOutlet weak var lblCardType : UILabel!
}

class EnRouteViewController: UIViewController,UITableViewDataSource,UITableViewDelegate{
    @IBOutlet weak var tblView : UITableView!
    var dict : [String:Any] = [:]
    @IBOutlet weak var lblRemainingTime : UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tblView.rowHeight = UITableView.automaticDimension
        tblView.estimatedRowHeight = 1000
       // lblRemainingTime.text = dict[""] as! String
    }
    
    @IBAction func btnbackPressed (_sender : Any){
        self.navigationController?.popViewController(animated: true)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return 4
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        
        if indexPath.row == 0{
            let cell = tableView.dequeueReusableCell(withIdentifier: "MapCell") as! MapCell
            
            print(dict)
            
            cell.lblDriverName.text = dict ["gcm.notification.driverName"] as? String
            cell.lblCarName.text = dict ["gcm.notification.carType"] as? String
            cell.lblCarNumber.text = dict ["gcm.notification.carNo"] as? String
            cell.lblRating.text = dict ["gcm.notification.rating"] as? String
            cell.lblOTP.text = dict ["gcm.notification.otp"] as? String
            
           //   cell.lblAmount.text = dict ["gcm.notification.otp"] as? String
            
            let aImgUrl = dict ["gcm.notification.driverImg"] as! String
            APIManager.requestImage(path: aImgUrl, completionHandler: {(image) in
                cell.imgDriver.image = image
            })
            return cell
        }
        else if indexPath.row == 1{
            let cell = tableView.dequeueReusableCell(withIdentifier: "btnCell") as! btnCell
            cell.btnShare.tag = indexPath.row
            cell.btnShare .addTarget(self, action: #selector(btnSahrePressed(_sender:)), for: UIControl.Event.touchUpInside)

            cell.btnContact.tag = indexPath.row
            cell.btnContact .addTarget(self, action: #selector(btnContactPressed(_sender:)), for: UIControl.Event.touchUpInside)

            cell.btnCancel.tag = indexPath.row
            cell.btnCancel .addTarget(self, action: #selector(btncancelPressed(_sender:)), for: UIControl.Event.touchUpInside)

            cell.btnFareSplit.tag = indexPath.row
            cell.btnFareSplit .addTarget(self, action: #selector(btnSplitPressed(_sender:)), for: UIControl.Event.touchUpInside)
            
             return cell
            
        }
        else if indexPath.row == 2{
            let cell = tableView.dequeueReusableCell(withIdentifier: "TripDetailCell") as! TripDetailCell
            return cell
        }
        else if indexPath.row == 3{
            let cell = tableView.dequeueReusableCell(withIdentifier: "PaymentCardTableViewCell") as! PaymentCardTableViewCell
            
            let  amount = dict ["gcm.notification.amount"] as? String
            
            cell.lblAmount.text = "$" + amount!
            cell.lblCardType.text = "Current Pay Amount"
            return cell
        }
        else{
            let cell = tableView.dequeueReusableCell(withIdentifier: "TripDetailCell") as! TripDetailCell
            return cell
        }
    }
    
    @objc func btnSahrePressed (_sender:Any){
        
    }
    
    //gautam
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.row == 3{
            let VC = self.storyboard?.instantiateViewController(withIdentifier: "PaymentVC")as! PaymentVC
            VC.dissct = dict;
            self.navigationController?.pushViewController(VC, animated: true)
        }
    }

    
    
    @objc func btnContactPressed (_sender:Any){
        let phoneNum = dict["gcm.notification.driverPhone"] as! String
        
        
        

        if let url = URL(string: "tel://\(phoneNum)") {
            if #available(iOS 10, *) {
                UIApplication.shared.open(url, options: [:], completionHandler: nil)
            } else {
                UIApplication.shared.openURL(url as URL)
            }
        }
    }
    
    @objc func btnSplitPressed (_sender:Any){
    }
    
    @objc func btncancelPressed (_sender:Any){
        let storyBoard = UIStoryboard(name: "Main", bundle: nil)
        let controller = storyBoard.instantiateViewController(withIdentifier: "CancelRideVC") as! CancelRideVC
        controller.dict = dict
        addChild(controller)
        view.addSubview(controller.view)
        controller.didMove(toParent: self)
    }
}
