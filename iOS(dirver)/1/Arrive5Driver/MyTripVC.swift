//
//  MyTripVC.swift
//  Arrive5Driver
//
//  Created by parangat2 on 6/14/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import SVProgressHUD
import SDWebImage

class MyTripVC: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    @IBOutlet weak var tblViewTripInfo: UITableView!
    
    var arrTripInfo : [[String:Any]] = [[:]]

    override func viewDidLoad() {
        super.viewDidLoad()

        tblViewTripInfo.isHidden = true
        tblViewTripInfo.estimatedRowHeight = 120.0
        tblViewTripInfo.rowHeight = UITableView.automaticDimension
        
        // Call Web Service
        serviceOurServices()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    //MARK:
    //MARK: UITableView Delegate & DataSource
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return arrTripInfo.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell") as! MyTripsTableViewCell
        
        let dict: [String : Any] = arrTripInfo[indexPath.row]
        cell.btnOngoing.tag = indexPath.row
        cell.btnRejected.tag = indexPath.row
        cell.btnCompleted.tag = indexPath.row
        cell.btnReportTrip.tag = indexPath.row
        cell.lblCustomerName.text = dict["user_name"] as? String
        cell.lblAddressFrom.text = dict["start_point"] as? String
        cell.lblAddressTo.text = dict["end_point"] as? String
        if let userImg: String = dict["user_img"] as? String
        {
            cell.imgCustomer.sd_setImage(with: URL(string: userImg), placeholderImage: nil)
        }
        else
        {
            //            cell.imgDriver.image =
        }
        
        if let mode: String = dict["mode"] as? String
        {
            if ((mode == "0") || (mode == "1") ||
                (mode == "6") || (mode == "5")
                || (mode == "7")) {
                
                cell.btnOngoing.isHidden = false
                cell.btnRejected.isHidden = true
                cell.btnCompleted.isHidden = true
                cell.btnReportTrip.isHidden = true
                cell.heightStackView.constant = 24.0
            }
            if ((mode == "2") || (mode == "3")
                || (mode == "9")) {
                
                cell.btnOngoing.isHidden = true
                cell.btnRejected.isHidden = false
                cell.btnCompleted.isHidden = true
                cell.btnReportTrip.isHidden = true
                cell.heightStackView.constant = 24.0 // 22.75
            }
            if mode == "4" {
                
                cell.btnOngoing.isHidden = true
                cell.btnRejected.isHidden = true
                cell.btnCompleted.isHidden = false
                cell.btnReportTrip.isHidden = false
                cell.heightStackView.constant = 48.0
            }
        }
        else
        {
            cell.btnOngoing.isHidden = true
            cell.btnRejected.isHidden = true
            cell.btnCompleted.isHidden = true
            cell.btnReportTrip.isHidden = true
        }


        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        
    }
    
    //MARK:
    //MARK: UIButton Action
    
    @IBAction func btnActionBack(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func btnActionReportTrip(_ sender: UIButton) {
        
        let dict: [String : Any] = arrTripInfo[sender.tag]
        let reportDict : [String : Any] = ["user_id": dict["user_id"] as! String,
                                           "booking_id": dict["booking_id"] as! String,
                                           "start_point": dict["start_point"] as! String,
                                           "end_point": dict["end_point"] as! String,
                                           "start_point_lat": dict["start_point_lat"] as! String,
                                           "start_point_long": dict["start_point_long"] as! String,
                                           "end_point_lat": dict["end_point_lat"] as! String,
                                           "end_point_long": dict["end_point_long"] as! String,
                                           "schedule_date": dict["schedule_date"] as! String,
                                           "schedule_time": dict["schedule_time"] as! String]
        
        let selectIssueVC = self.storyboard?.instantiateViewController(withIdentifier: "SelectIssueVC") as! SelectIssueVC
        selectIssueVC.reportDetailsDict = reportDict
        self.navigationController?.pushViewController(selectIssueVC, animated: true)
    }
    
    //MARK:
    //MARK: Web Service
    
    func serviceOurServices(){
        let aStrApi = "\(Constant.API.kOurServices)"
        let driver_id  = UserDefaults.standard.value(forKey: "user_id") as! String

        SVProgressHUD.show(withStatus: "Please Wait")
        
        let dictData : [String : AnyObject]!
        dictData = ["driver_id" : driver_id] as [String : AnyObject]
        
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            if json["status"].rawString() == "true"{
                
                SVProgressHUD.dismiss()
                let ResultVal = json["result"].arrayObject as! [[String : Any]]
                
                self.tblViewTripInfo.isHidden = false
                self.arrTripInfo.removeAll()
                self.arrTripInfo = ResultVal
                self.tblViewTripInfo.reloadData()
            }
            else{
                SVProgressHUD.dismiss()
                self.view.makeToast(json["msg"].rawString())
            }
        }, failure: {(error) in
            SVProgressHUD.dismiss()
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
}
