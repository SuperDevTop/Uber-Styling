//
//  ARSelectDriverVC.swift
//  Arrive5
//
//  Created by parangat2 on 6/12/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import SVProgressHUD
import SDWebImage

class ARSelectDriverVC: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    @IBOutlet weak var tblViewDriverInfo: UITableView!
    
    var arrDriverInfo : [[String:Any]] = [[:]]
    var amount: String = ""

    var startPoint: String = ""
    var endPoint: String = ""
    var startPointLat: String = ""
    var startPointLong: String = ""
    var endPointLat: String = ""
    var endPointLong: String = ""
    var scheduleDate: String = ""
    var scheduleTime: String = ""
    var vehicleSubTypeId: String = ""


    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
//        arrDriverInfo = [["driverName": "John", "carNo" : "DX123WE"],
//        ["driverName": "Rex", "carNo" : "DX435WE"]]
        
        tblViewDriverInfo.isHidden = true
        tblViewDriverInfo.estimatedRowHeight = 70.0
        tblViewDriverInfo.rowHeight = UITableView.automaticDimension
        
        // Call Web Service
        serviceBookedDriverList()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    //MARK:
    //MARK: UITableView Delegate & DataSource
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return arrDriverInfo.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell") as! DriverTableViewCell
        
        let dict: [String : Any] = arrDriverInfo[indexPath.row]
        cell.btnSend.tag = indexPath.row
        cell.lblDriverName.text = dict["name"] as? String
        cell.lblCarCode.text = dict["driver_id"] as? String
        if let driver_img: String = dict["driver_img"] as? String
        {
            cell.imgDriver.sd_setImage(with: URL(string: driver_img), placeholderImage: nil)
        }
        else
        {
//            cell.imgDriver.image =
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
    
    @IBAction func btnActionSend(_ sender: UIButton) {
        
        let dict: [String : Any] = arrDriverInfo[sender.tag]
        let driver_id: String = dict["driver_id"] as! String
        serviceScheduleRide(driverId: driver_id)
    }
    
    //MARK:
    //MARK: Web Service
    
    func serviceBookedDriverList(){
        let aStrApi = "\(Constant.API.kBookedDriverList)"
        let user_id = UserDefaults.standard.string(forKey: "user_id")
        
        SVProgressHUD.show(withStatus: "Please Wait")
        
       // let dictData : [String : AnyObject]!
       let dictData = ["user_id" : user_id] as [String : AnyObject]
        
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            if json["status"].rawString() == "true"{
                
                SVProgressHUD.dismiss()
                let ResultVal = json["result"].arrayObject as! [[String : Any]]
                self.tblViewDriverInfo.isHidden = false
                self.arrDriverInfo.removeAll()
                self.arrDriverInfo = ResultVal
                self.tblViewDriverInfo.reloadData()
                
                if ResultVal.count < 1{
                    self.serviceScheduleRide(driverId: "")
                }
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
    
    func serviceScheduleRide(driverId: String){
        let aStrApi = "\(Constant.API.KScheduleLater)"
        let user_id = UserDefaults.standard.string(forKey: "user_id")
        
        let dictData : [String : AnyObject]!
        dictData = ["user_id" : user_id,
                    "start_point": startPoint,
                    "end_point": endPoint,
                    "start_point_lat" : startPointLat,
                    "start_point_long": startPointLong,
                    "end_point_lat": endPointLat,
                    "end_point_long" : endPointLong,
                    "schedule_date": scheduleDate,
                    "schedule_time": scheduleTime,
                    "amount": amount,
                    "vehicle_sub_type_id" : vehicleSubTypeId,
                    "driver_id": driverId] as [String : AnyObject]
        
        
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            if json["status"].rawString() == "true"{
                self.alertShow(string: json["message"].stringValue)
            }
            else{
                self.view.makeToast(json["msg"].rawString())
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
    func alertShow(string: String)
    {
        let alert = UIAlertController(title: "Alert", message: string, preferredStyle: UIAlertController.Style.alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: { action in
            switch action.style{
            case .default:
                print("default")
                
                let allViewController: [UIViewController] = self.navigationController!.viewControllers as [UIViewController];
                for aViewController : UIViewController in allViewController
                {
                    print(aViewController)
                    if aViewController.isKind(of: HomeVC.self)
                    {
                    self.navigationController?.popToViewController(aViewController, animated: true)
                    }
                }
            case .cancel:
                print("cancel")
                
            case .destructive:
                print("destructive")
                
            }}))
        self.present(alert, animated: true, completion: nil)
    }
    
}
