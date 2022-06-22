//
//  CancelRideVC.swift
//  Arrive5
//
//  Created by Parangat Air 1 on 5/30/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class classReasoncell: UITableViewCell{
    @IBOutlet weak var lblReason : UILabel!
    @IBOutlet weak var imgRadio : UIImageView!
}

class CancelRideVC: UIViewController,UITableViewDelegate,UITableViewDataSource {
    
    @IBOutlet weak var tblView : UITableView!
    
    var arrayReason : [Any] = []
    var selectedIndex : IndexPath!
    var dict : [String:Any] = [:]
    var reason : String!

    override func viewDidLoad() {
        super.viewDidLoad()
        arrayReason = ["I am far away","Other Reason","Cancellation Reason 1"]
        print(dict)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnCrossPressed (sender:Any){
        self.willMove(toParent: nil)
        self.view.removeFromSuperview()
        self.removeFromParent()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return arrayReason.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell = tableView.dequeueReusableCell(withIdentifier: "classReasoncell") as! classReasoncell
        cell.lblReason.text = arrayReason[indexPath.row] as? String
        
        if (selectedIndex == nil){
            cell.imgRadio.image = UIImage(named: "radio_button1")
        
        }else{
            if selectedIndex.row == indexPath.row{
                cell.imgRadio.image = UIImage(named: "radio_button")
                
            }else{
                cell.imgRadio.image = UIImage(named: "radio_button1")
            }
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        reason = arrayReason[indexPath.row] as! String
        selectedIndex = indexPath
        tblView.reloadData()
    }
    
    @IBAction func btnSubmitPressed (_sender:Any){
        if selectedIndex == nil{
            self.view.makeToast("Please Choose a reason")
        }
        else{
            
            let aStrApi = "\(Constant.API.kCancelRide)"
            let booking_id = dict["bookingId"] as! String
            let cancel_reason = reason
            let type = "user"
            let dictData : [String : AnyObject]!
            dictData = ["booking_id" : booking_id,
                        "cancel_reason":cancel_reason,
                        "type":type ] as [String : AnyObject]
            
            APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
                print(json)
                if json["status"].rawString() == "true"{
                    let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                    self.navigationController?.pushViewController(homeVc, animated: true)
                }else{
                    self.view.makeToast(json["msg"].rawString())
                }
            }, failure: {(error) in
                self.view.makeToast(error.localizedDescription)
                print(error.localizedDescription)
            })
        }
    }
    
}
