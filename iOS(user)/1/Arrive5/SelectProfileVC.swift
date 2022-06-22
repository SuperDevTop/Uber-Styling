//
//  SelectProfileVC.swift
//  Arrive5
//
//  Created by Rahul Saxena on 24/10/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import SwiftyJSON

protocol SelectProfileDelegate {
    func SelectProfile(_ selectedName : String)
}

class SelectProfileVC: UIViewController, UITableViewDelegate, UITableViewDataSource{
    @IBOutlet weak var tblProfileSelectionVal: UITableView!
    
    var arrProfile = [JSON]()
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    var delegate : SelectProfileDelegate?
    override func viewDidLoad() {
        super.viewDidLoad()
        businessProfileAPI()
    }

 
    func removingSuperVIeW(){
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
    
    @IBAction func btnSelectPaymentMethods(_ sender: UIButton) {
    }
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.removingSuperVIeW()
    }
    
   
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return arrProfile.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let tblProfileSelection = tableView.dequeueReusableCell(withIdentifier: "tblProfileSelection", for: indexPath) as! tblProfileSelection
        let aDict = self.arrProfile[indexPath.row].dictionary
        
        
        let strType = aDict!["type"]?.string
        if strType == "1"{
            tblProfileSelection.lblProfileHeader.text = "Personal"
        }else{
            tblProfileSelection.lblProfileHeader.text = "Business"
        }
        
        
        let strPayment = aDict!["payment_method_type"]?.string
        if strPayment == "1"{
            tblProfileSelection.lblProfileSubtype.text = "Credit Card"
        }else{
            tblProfileSelection.lblProfileSubtype.text = "Cash"
        }

        let indxx = UserDefaults.standard.integer(forKey: "selectBusinessProfile")
        
        if indexPath.row == indxx{
            tblProfileSelection.btnSelectedVal.isHidden = false
            ///appDelegate.arrCount.removeAll()
        }
        else{
            tblProfileSelection.btnSelectedVal.isHidden = true
        }

        return tblProfileSelection
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let aDict = self.arrProfile[indexPath.row].dictionary
        let strType = aDict!["type"]?.string
        if strType == "1"{
            appDelegate.profileType = "Personal"
            delegate?.SelectProfile("Personal")
            
        }else{
            delegate?.SelectProfile("Business")
            appDelegate.profileType = "Business"
        }
        
        UserDefaults.standard.set(indexPath.row, forKey: "selectBusinessProfile")
        UserDefaults.standard.synchronize()

        self.tblProfileSelectionVal.reloadData()
 
    }
    
    
    func businessProfileAPI()  {
        
          let url = "\(Constant.API.profilelistss)"
        
//        let url = "http://arrive5.pcthepro.com/webservice/user/businessProfileList"
        let user_id = UserDefaults.standard.string(forKey: "user_id")
        let dictData : [String : AnyObject]!
        dictData = ["user_id" : user_id] as [String : AnyObject]
        print(dictData)
        APIManager.requestPOSTURL(url, params: dictData, headers: nil, success: {(json) in
            print(json)
            if json["status"].rawString() == "true"{
                self.arrProfile = json["business_profile_list"].arrayValue
                self.tblProfileSelectionVal.reloadData()
                
            }
            else{
                self.view.makeToast(json["message"].rawString())
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
        })
        
    }
    
    
    
}





















/*

import UIKit
import GoogleMaps

protocol SelectProfileDelegate {
    func SelectProfile(_ selectedName : String)
}

class SelectProfileVC: UIViewController, UITableViewDelegate, UITableViewDataSource,CLLocationManagerDelegate{
    @IBOutlet weak var tblProfileSelectionVal: UITableView!
    
    var arrProfile : [[String:Any]] = [["profileMainName":"Business","profileMainType":"Credit Card","profileMainImage":"user_blue"],["profileMainName":"Personal","profileMainType":"Credit Card","profileMainImage":"bag"]]
    
    var locationManager:CLLocationManager!
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    var delegate : SelectProfileDelegate?
    var arrCount : [Int] = [1]
    override func viewDidLoad() {
        super.viewDidLoad()
        
        locationManager = CLLocationManager()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        let authorizationStatus = CLLocationManager.authorizationStatus()
        if (authorizationStatus == CLAuthorizationStatus.notDetermined) {
            locationManager.requestWhenInUseAuthorization()
        } else if (authorizationStatus == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
        }
        //        vwGoogleMapView.isMyLocationEnabled = true
        //        vwGoogleMapView.settings.myLocationButton = true
        
        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func removingSuperVIeW(){
        UIView.animate(withDuration: 0.3, animations: {
            self.view.frame = CGRect(x: 0,
                                     y: self.view.frame.size.height,
                                     width: self.view.frame.size.width,
                                     height: self.view.frame.size.height)
        }) { (bool) in
            self.willMove(toParentViewController: nil)
            //            let showOutfitVC = self.storyboard?.instantiateViewController(withIdentifier: Constant.ViewControllerID.kShowOutfitVC) as! ShowOutfitVC
            //            self.navigationController?.popViewController(animated: true)
            self.view.reloadInputViews()
            self.view.removeFromSuperview()
            self.removeFromParentViewController()
        }
    }
    
    @IBAction func btnSelectPaymentMethods(_ sender: UIButton) {
    }
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.removingSuperVIeW()
    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if (status == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.arrProfile.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let tblProfileSelection = tableView.dequeueReusableCell(withIdentifier: "tblProfileSelection", for: indexPath) as! tblProfileSelection
        let aDict = self.arrProfile[indexPath.row]
        tblProfileSelection.lblProfileHeader.text = aDict["profileMainName"] as? String
        tblProfileSelection.lblProfileSubtype.text = aDict["profileMainType"] as? String
        tblProfileSelection.ivProfileVIew.image = UIImage(named: aDict["profileMainImage"] as! String)
        
        if appDelegate.arrCount.contains(indexPath.row){
            tblProfileSelection.btnSelectedVal.isHidden = false
            appDelegate.arrCount.removeAll()
        }
        else{
            tblProfileSelection.btnSelectedVal.isHidden = true
        }
        return tblProfileSelection
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        //        appDelegate.arrCount.append(indexPath.row)
        if indexPath.row == 0{
            delegate?.SelectProfile("Business")
            appDelegate.profileType = "Business"
        }else{
            appDelegate.profileType = "Personal"
            delegate?.SelectProfile("Personal")
        }
        appDelegate.arrCount.append(indexPath.row)
        self.tblProfileSelectionVal.reloadData()
        //        self.removingSuperVIeW()
    }
 
}

*/
