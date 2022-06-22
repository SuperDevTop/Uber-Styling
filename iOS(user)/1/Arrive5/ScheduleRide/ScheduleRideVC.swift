//
//  ScheduleRideVC.swift
//  Arrive5
//
//  Created by Parangat Air 1 on 5/31/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces
class ScheduleRideVC: UIViewController,GMSMapViewDelegate, changeVehicleTypeDelegate {
    
    @IBOutlet weak var viewPicker : UIView!
    @IBOutlet weak var datePicker : UIDatePicker!
    @IBOutlet weak var lblPickupDate : UILabel!
    @IBOutlet weak var lblPickupTime : UILabel!
    @IBOutlet weak var txtPickUpAddress : UITextField!
    @IBOutlet weak var txtDropAddress : UITextField!
    @IBOutlet weak var btnScheduleLater : UIButton!
    @IBOutlet weak var lblVehicleModelName : UILabel!

    var PickLocationCordinate = CLLocationCoordinate2D()
    var dropLocationCordinate = CLLocationCoordinate2D()
    var isPickUp : Bool!
    var amountStr : String!

    var vehicle_sub_type_id: String = ""
    var arrDeviceType : [Any]=[]

    override func viewDidLoad() {
        super.viewDidLoad()
        btnScheduleLater.layer.cornerRadius = btnScheduleLater.frame.height / 2
        btnScheduleLater.clipsToBounds = true
        btnScheduleLater.layer.borderWidth = 2.0
        btnScheduleLater.layer.borderColor = UIColor.lightGray.cgColor

        isPickUp = false
        lblVehicleModelName.text = ""
        self.callApiData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
//        btnScheduleLater.layer.cornerRadius = 20.0
//        btnScheduleLater.clipsToBounds = true
//        btnScheduleLater.layer.borderWidth = 1.0
//        btnScheduleLater.layer.borderColor = UIColor.darkGray.cgColor
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnActionChangeVehicleType(_ sender: UIButton) {
        changeVehicleType()
    }
    
    @IBAction func btnBackPressed(_sender:Any){
        self.navigationController?.popViewController(animated: true)
    }
    
   //rahul
    @IBOutlet var lblPrice: UILabel!
    @IBAction func estimateAction(_ sender: Any) {
        callEstimatelApi()
    }
 
    
    
    func callEstimatelApi(){
        let url = "\(Constant.API.KEstimatePrice)"
        let dictData : [String : AnyObject]!
        dictData = ["start_point_lat" : PickLocationCordinate.latitude,
                    "start_point_long":PickLocationCordinate.longitude,
                    "end_point_lat":dropLocationCordinate.latitude,
                    "end_point_long":dropLocationCordinate.longitude,
                    "vehicle_sub_type_id":vehicle_sub_type_id] as [String : AnyObject]
        print(dictData)
        APIManager.requestPOSTURL(url, params: dictData, headers: nil, success: {(json) in
            print(json)
            
            if json["status"].rawString() == "true"{
                self.amountStr = json["result"].stringValue
                self.lblPrice.text = "$" + json["result"].stringValue
               // self.view.makeToast(json["message"].rawString())
                
            }else{
                self.view.makeToast(json["message"].rawString())
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
/*
     
     n schedule booking API add parameter amount
     
     http://arrive5.pcthepro.com/webservice/Booking/schedule_later_booking
     
     
     amount
     */
    
    
    
    
    
    
    @IBAction func btnPickUpDatePressed(_sender:Any){
        datePicker.datePickerMode = UIDatePicker.Mode.date
        datePicker.addTarget(self, action: #selector(ScheduleRideVC.datePickerValueChanged), for: UIControl.Event.valueChanged)
        showPickerView()
    }
    
    @IBAction func btnPickUpTimePressed(_sender:Any){
        
        datePicker.datePickerMode = UIDatePicker.Mode.time
        datePicker.addTarget(self, action: #selector(ScheduleRideVC.timePickerValueChanged(sender:)), for: UIControl.Event.valueChanged)
        showPickerView()
    }
    
    @objc func datePickerValueChanged(sender:UIDatePicker) {
        let inputFormatter = DateFormatter()
        inputFormatter.dateFormat = "dd-MM-yyyy"
        let strDate = inputFormatter.string(from: sender.date)
        let showDate = inputFormatter.date(from:strDate )
        inputFormatter.dateFormat = "yyyy-MM-dd"
        let resultString = inputFormatter.string(from: showDate!)
        print(resultString)
        lblPickupDate.text = resultString
    }
    
    @objc func timePickerValueChanged(sender:UIDatePicker) {
        let inputFormatter = DateFormatter()
        inputFormatter.dateFormat = "HH:mm"
        let strDate = inputFormatter.string(from: sender.date)
        let showDate = inputFormatter.date(from:strDate )
        inputFormatter.dateFormat = "HH:mm"
        let resultString = inputFormatter.string(from: showDate!)
        print(resultString)
        lblPickupTime.text = resultString
    }

    @IBAction func btnDoneAction(_ sender: UIButton) {
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.viewPicker.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
    }
    
    func showPickerView(){
        view.addSubview(viewPicker)
        AlertViewData.animate(withDuration: 0.0, delay: 0.0, usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.viewPicker.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY+UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            AlertViewData.animate(withDuration: 0.5, delay: 0.4, usingSpringWithDamping: 1.0,
                                  initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.viewPicker.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY-UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)}, completion: { (finished: Bool) in
            })
        })
    }
    

    @IBAction func btnPickUpAddress(_sender:Any){
        isPickUp = true
        let acController = GMSAutocompleteViewController()
        acController.delegate = self
        self.present(acController, animated: true, completion: nil)
    }
    
    @IBAction func BtnDropAdddress(_sender:Any){
        isPickUp = false
        let acController = GMSAutocompleteViewController()
        acController.delegate = self
        self.present(acController, animated: true, completion: nil)
    }
    
    func changeVehicleType(){
        let getDeviceTypeVC = self.storyboard?.instantiateViewController(withIdentifier: "ARGetDeviceTypeVC") as! ARGetDeviceTypeVC
//        getDeviceTypeVC.arrDeviceType = arrDeviceType
        getDeviceTypeVC.delegate = self
        
            getDeviceTypeVC.pickLocationCordinate = PickLocationCordinate
            getDeviceTypeVC.dropLocationCordinate = dropLocationCordinate

        addChild(getDeviceTypeVC)
        
        getDeviceTypeVC.didMove(toParent: self)
        
        getDeviceTypeVC.view.frame = CGRect(x: 0,
                                           y: self.view.frame.size.height,
                                           width: self.view.frame.size.width,
                                           height: self.view.frame.size.height)
        self.view.addSubview(getDeviceTypeVC.view)
        
        UIView.animate(withDuration: 0.3) {
            getDeviceTypeVC.view.frame = self.view.bounds
        }
    }
    
    func changeVehicleTypeSelected(_ selectedId: String, selectedVehicleName: String) {
        print("changeVehicleTypeSelected: \(selectedId)")
        vehicle_sub_type_id = selectedId
        lblVehicleModelName.text = selectedVehicleName
    }
    
//    func callApiData(){
//        let aStrApi = "\(Constant.API.kVehicleType)"
//
//
//        APIManager.requestGETURL(aStrApi, success: {(json) in
//            if json["status"].rawString() == "true"{
//                self.arrDeviceType = json["details"].rawValue as! [Any]
//                let aDict:[String:Any] = self.arrDeviceType[0] as! [String : Any]
//                if let arrVehicleType : [[String:Any]] = aDict["vehicleType"] as? [[String:Any]]
//                {
//                    let dict:[String:Any] = arrVehicleType[0]
//                    self.vehicle_sub_type_id = dict["id"] as! String
//                    let vehicle_sub_type_modelName: String = dict["vehicle_model"] as! String
//                    self.lblVehicleModelName.text = vehicle_sub_type_modelName
//                }
//            }
//            else{
//            }
//        }, failure: {(error) in
//        })
//    }

    
    
    func callApiData(){
        let url = "\(Constant.API.kVehicleType)"
        let dictData : [String : AnyObject]!
        dictData = ["start_point_lat" : PickLocationCordinate.latitude,
                    "start_point_long":PickLocationCordinate.longitude,
                    "end_point_lat":dropLocationCordinate.latitude,
                    "end_point_long":dropLocationCordinate.longitude] as [String : AnyObject]
        print(dictData)
        APIManager.requestPOSTURL(url, params: dictData, headers: nil, success: {(json) in
            print(json)
            
            if json["status"].rawString() == "true"{
                self.arrDeviceType = json["result"].rawValue as! [Any]
                let aDict:[String:Any] = self.arrDeviceType[0] as! [String : Any]
                if let arrVehicleType : [[String:Any]] = aDict["vehicleType"] as? [[String:Any]]
                {
                    let dict:[String:Any] = arrVehicleType[0]
                    self.vehicle_sub_type_id = dict["id"] as! String
                    let vehicle_sub_type_modelName: String = dict["vehicle_model"] as! String
                    self.lblVehicleModelName.text = vehicle_sub_type_modelName
                }
            }
            else{
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
    
    
    
    
    
    
}

extension ScheduleRideVC: GMSAutocompleteViewControllerDelegate {
    
    func viewController(_ viewController: GMSAutocompleteViewController, didAutocompleteWith place: GMSPlace) {
        
        print("Place name: \(place.name)")
        print("Place address: \(place.formattedAddress ?? "null")")
        if isPickUp == true{
            PickLocationCordinate = place.coordinate
            txtPickUpAddress.text = place.formattedAddress
        }
        else{
            dropLocationCordinate = place.coordinate
            txtDropAddress.text = place.formattedAddress
        }
        self.dismiss(animated: true, completion: nil)
    }
    
    func viewController(_ viewController: GMSAutocompleteViewController, didFailAutocompleteWithError error: Error) {
        self.dismiss(animated: true, completion: nil)
    }
    
    func wasCancelled(_ viewController: GMSAutocompleteViewController) {
        print("Autocomplete was cancelled.")
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func btnScheduleLaterPressed(_sender:Any){
        if lblPickupDate.text == "Pickup Date"{
            self.view.makeToast("Please Select Pickup Date")
        
        }else if lblPickupTime.text == "Pickup Time"{
            self.view.makeToast("Please Select Pickup Time")
            
        }else if txtPickUpAddress.text == ""{
            self.view.makeToast("Please Choose Pickup Address")
        
        }else if txtDropAddress.text == ""{
            self.view.makeToast("Please Choose Drop Address")
        
        }else{
//            ScheduleRide()
            navigateToARSelectDriverVC()
        }
    }
    
    func navigateToARSelectDriverVC()
    {
        let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
        let controller: ARSelectDriverVC = storyboard.instantiateViewController(withIdentifier: "ARSelectDriverVC") as! ARSelectDriverVC
        controller.amount = amountStr!
        controller.startPoint = txtPickUpAddress.text!
        controller.endPoint = txtDropAddress.text!
        controller.startPointLat = "\(PickLocationCordinate.latitude)"
        controller.startPointLong = "\(PickLocationCordinate.longitude)"
        controller.endPointLat = "\(dropLocationCordinate.latitude)"
        controller.endPointLong = "\(dropLocationCordinate.longitude)"
        controller.scheduleDate = lblPickupDate.text!
        controller.scheduleTime = lblPickupTime.text!
        controller.vehicleSubTypeId = vehicle_sub_type_id
        
        self.navigationController?.pushViewController(controller, animated: true)
    }
}
