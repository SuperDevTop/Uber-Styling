//
//  ConfirmBookingVC.swift
//  Arrive5
//
//  Created by Joy on 17/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps
import SwiftyJSON
import Alamofire

class ConfirmBookingVC: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout,CLLocationManagerDelegate,clcVehicleTypeCellDelegate,selectingPassengerDelegate,SelectProfileDelegate,sendDataDelegate{
    func myVCDidFinish(text1: String, text: String)
    {
        print(text, text1)
        tfPromoCode.text = text
        strPromoValue = text1
    }
    var strPromoValue : String?

    
    // MARK: - IBOutlets
    // MARK: -
    
    @IBOutlet weak var vwGoogleMapView: GMSMapView!
    
    @IBOutlet weak var lblProfileType: UILabel!
    @IBOutlet weak var lblPassengerNumber: UILabel!
    @IBOutlet weak var clcVehicleType: UICollectionView!
    @IBOutlet weak var tfPromoCode: UITextField!
    
    // MARK: - Properties
    // MARK: -
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    var PickUpAddress : String!
    var DropUpAddress : String!
    var arrDeviceType : [Any]=[]
    var destinationMarker : GMSMarker!
    var locationManager:CLLocationManager!
    var DropDownLocation : CLLocationCoordinate2D!
    var PickUpLocation : CLLocationCoordinate2D!
    var vehicle_type_id : String = "1"
    var vehicle_sub_type_id : String = "1"
    
    // MARK: - VCCycles
    // MARK: -
    
    override func viewDidLoad() {
        super.viewDidLoad()
       // self.callApiData()
        self.apiVehicleDetails()
        
        locationManager = CLLocationManager()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        let authorizationStatus = CLLocationManager.authorizationStatus()
        if (authorizationStatus == CLAuthorizationStatus.notDetermined) {
            locationManager.requestWhenInUseAuthorization()
        } else if (authorizationStatus == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
            self.PolylineDraw(2)
            self.markerDrawing()
        }
//        getCoordinate(addressString: self.PickUpAddress) { (value, error) in
//            
//            self.PickUpLocation = value
//            
//        }
        
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        self.lblProfileType.text = appDelegate.profileType
        self.lblPassengerNumber.text = appDelegate.PassengerNumber
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(true)
    }
    
    // MARK: - Methods
    // MARK: -
    
    func callBooking(){
        
        let userId = UserDefaults.standard.value(forKey: "user_id") as? String
        let start_point = self.PickUpAddress
        let end_point = self.DropUpAddress
        let start_point_lat = self.PickUpLocation.latitude
        let start_point_long = self.PickUpLocation.longitude
        let end_point_lat = self.DropDownLocation.latitude
        let end_point_long = self.DropDownLocation.longitude
        let no_passanger = self.lblPassengerNumber.text!
        let vehicle_type_id = self.vehicle_type_id
        let vehicle_sub_type_id = self.vehicle_sub_type_id
        //let promocode = self.tfPromoCode.text
        let aStrApi = "\(Constant.API.kBookingVal)"
        
        let dictData : [String : AnyObject]!
        
        dictData = ["user_id" : userId!,
                    "start_point":start_point!,
                    "end_point":end_point!,
                    "start_point_lat":start_point_lat,
                    "start_point_long":start_point_long,
                    "end_point_lat":end_point_lat,
                    "end_point_long":end_point_long,
                    "no_passanger":no_passanger,
                    "vehicle_type_id":vehicle_type_id,
                    "vehicle_sub_type_id":vehicle_sub_type_id,
                    "promocode":strPromoValue ?? ""] as [String : AnyObject]
        
        APIManager.requestPOSTURL(aStrApi, params: dictData!, headers: nil, success: {(json) in
            if json["status"].rawString() == "true"{
                self.view.makeToast(json["message"].rawString())
                
               
                //gautam, saving booking id in nsuser defalut
//crash
//Rahul edit 26july2018
                let result = json["result"]
                let bookingId = result["id"].stringValue
                let defaults = UserDefaults.standard
                defaults.set(bookingId, forKey: "bookingId")
//------<
                
                let storyboard  = UIStoryboard(name: "Main", bundle: nil)
                let vc = storyboard .instantiateViewController(withIdentifier: "WaitingDriverViewController") as! WaitingDriverViewController
                self.navigationController?.pushViewController(vc, animated: true)
                
            }else{
                self.view.makeToast(json["message"].rawString())
            }
        }, failure: {(error) in
            print(error.localizedDescription)
        })
        
    }
    
  //rahul
    func apiVehicleDetails(){
        
        
        
        let url = "\(Constant.API.kVehicleDetails)"
        
//          let url = "\(Constant.API.kVehicleDetails)"
        
        print(url)
        let dictData : [String : AnyObject]!
        dictData = ["start_point_lat" : self.PickUpLocation.latitude,
                    "start_point_long":self.PickUpLocation.longitude,
                    "end_point_lat":self.DropDownLocation.latitude,
                    "end_point_long":self.DropDownLocation.longitude] as [String : AnyObject]
        print(dictData)
        
        APIManager.requestPOSTURL(url, params: dictData, headers: nil, success: {(json) in
            print(json)
            
            if json["status"].rawString() == "true"{
                //self.lblPrice.text = "$" + json["result"].stringValue
                // self.view.makeToast(json["message"].rawString())
                self.arrDeviceType = json["result"].rawValue as! [Any]
                self.clcVehicleType.reloadData()
            }else{
                self.view.makeToast(json["message"].rawString())
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
    
   /*
     func callApiData(){
   
        let aStrApi = "\(Constant.API.kVehicleType)"
        APIManager.requestGETURL(aStrApi, success: {(json) in
            if json["status"].rawString() == "true"{
                self.arrDeviceType = json["details"].rawValue as! [Any]
                self.clcVehicleType.reloadData()
            }else{
            }
        }, failure: {(error) in
        })
    }
     */
    
    func clcVehicleTypeSelected(_ selectedId: String, selectedModelName: String) {
        vehicle_sub_type_id = selectedId
    }
    
    func getCoordinate( addressString : String,
                        completionHandler: @escaping(CLLocationCoordinate2D, NSError?) -> Void ) {
        let geocoder = CLGeocoder()
        geocoder.geocodeAddressString(addressString) { (placemarks, error) in
            if error == nil {
                if let placemark = placemarks?[0] {
                    let location = placemark.location!
                    print(location.coordinate.latitude)
                    print(location.coordinate.longitude)
                    completionHandler(location.coordinate, nil)
                    return
                }
            }
            
            completionHandler(kCLLocationCoordinate2DInvalid, error as NSError?)
        }
    }
    
    func PolylineDraw(_ MarkerType : Int ){
        
        
        let origin = "\(self.PickUpLocation.latitude),\(self.PickUpLocation.longitude)"
        let destination = "\(self.DropDownLocation.latitude),\(self.DropDownLocation.longitude)"
//        let url = "https://maps.googleapis.com/maps/api/directions/json?origin=\(origin)&destination=\(destination)&mode=driving&key=AIzaSyAc3ku-xOE3bfjaQtcdNBn4Sqqjfu5LsHg"
        
          let url = "https://maps.googleapis.com/maps/api/directions/json?origin=\(origin)&destination=\(destination)&mode=driving&key=AIzaSyBD62D6SgxMOyTKiEygjHM7zgQGbWs6H9g"
        
        
        
//        https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=AIzaSyA_ErWhZguYEK3cQA_lb0pb8rA_dfJ0ZRM
        APIManager.requestGETURL(url, success: {(json) in
            print(json)
            
            let routes = json["routes"].arrayValue
            for route in routes
            {
                let routeOverviewPolyline = route["overview_polyline"].dictionary
                let points = routeOverviewPolyline?["points"]?.stringValue
                let path = GMSPath.init(fromEncodedPath: points!)
                
                if(MarkerType == 1){
                    let polyline = GMSPolyline(path: path)
                    polyline.strokeColor = UIColor(red: 48.0/255, green: 177.0/255, blue: 236.0/255, alpha: 1.0)
                    polyline.strokeWidth = 5.0
                    polyline.map = self.vwGoogleMapView
                }else{
                    let polyline = GMSPolyline(path: path)
                    let red = UIColor(red: 48.0/255, green: 177.0/255, blue: 236.0/255, alpha: 1.0)
                    //                        let darkGrey = UIColor(hexString: "#f7acba")
                    polyline.strokeColor = red
                    polyline.strokeWidth = 5.0
                    polyline.map = self.vwGoogleMapView
                }
                
                
            }
        }, failure: {(error) in
            
        })
    }
    
    func markerDrawing(){
        
        let locValue:CLLocationCoordinate2D = locationManager.location!.coordinate
        vwGoogleMapView.animate(toLocation: CLLocationCoordinate2D(latitude: (self.PickUpLocation.latitude) , longitude: (self.PickUpLocation.longitude)))
        
        vwGoogleMapView.setMinZoom(4.6, maxZoom: 20)
        let camera = GMSCameraPosition.camera(withLatitude: (self.PickUpLocation.latitude), longitude: (self.PickUpLocation.longitude), zoom: 16.0)
        let mapView = GMSMapView.map(withFrame: self.vwGoogleMapView.bounds, camera: camera)
        vwGoogleMapView.animate(to: camera)
        mapView.isMyLocationEnabled = true
        let Current = GMSMarker()
        Current.position = CLLocationCoordinate2D(latitude: (self.PickUpLocation.latitude) , longitude: (self.PickUpLocation.longitude))
        Current.icon = UIImage(named: "pin")
        Current.map = vwGoogleMapView
        
        let DropDown = GMSMarker()
        DropDown.position = CLLocationCoordinate2D(latitude: (self.DropDownLocation.latitude) , longitude: (self.DropDownLocation.longitude) )
        DropDown.icon = UIImage(named: "car")
        
//        DropDown.layer.masksToBounds = false
//        DropDown.layer.shadowColor = UIColor.gray.cgColor
//        DropDown.layer.shadowOffset =  CGSize.zero
//        DropDown.layer.shadowOpacity = 1
//        DropDown.layer.shadowRadius = 10
        
       
        DropDown.layer.borderColor = UIColor.black.cgColor
        DropDown.layer.borderWidth = 1
         DropDown.layer.masksToBounds = false
         DropDown.layer.shadowColor = UIColor.lightGray.cgColor
          DropDown.layer.shadowOffset =  CGSize(width: 10,height: 10)
         DropDown.layer.shadowOpacity = 3
        DropDown.layer.shadowRadius = 5
        
        DropDown.map = vwGoogleMapView
    }
    
    func updateLocationoordinates(coordinates:CLLocationCoordinate2D) {
        
        if destinationMarker == nil
        {
            destinationMarker = GMSMarker()
            destinationMarker.position = CLLocationCoordinate2D(latitude: (self.PickUpLocation.latitude) , longitude: (self.PickUpLocation.longitude))
            let image = UIImage(named:"pin")
            destinationMarker.icon = image
            destinationMarker.map = vwGoogleMapView
            destinationMarker.appearAnimation = .pop
        }
        else
        {
            CATransaction.begin()
            CATransaction.setAnimationDuration(1.0)
            destinationMarker.position =  coordinates
            CATransaction.commit()
        }
    }
    
    func addProfile(){
        let ScheduleTripVC = self.storyboard?.instantiateViewController(withIdentifier: "SelectProfileVC") as! SelectProfileVC
        ScheduleTripVC.delegate = self
        addChild(ScheduleTripVC)
        
        ScheduleTripVC.didMove(toParent: self)
        
        ScheduleTripVC.view.frame = CGRect(x: 0,
                                           y: self.view.frame.size.height,
                                           width: self.view.frame.size.width,
                                           height: self.view.frame.size.height)
        self.view.addSubview(ScheduleTripVC.view)
        
        UIView.animate(withDuration: 0.3) {
            ScheduleTripVC.view.frame = self.view.bounds
        }
    }
    
    func addPassenger(){
        let ScheduleTripVC = self.storyboard?.instantiateViewController(withIdentifier: "SelectingPassengerVC") as! SelectingPassengerVC
        ScheduleTripVC.delegate = self
        addChild(ScheduleTripVC)
        
        ScheduleTripVC.didMove(toParent: self)
        
        ScheduleTripVC.view.frame = CGRect(x: 0,
                                           y: self.view.frame.size.height,
                                           width: self.view.frame.size.width,
                                           height: self.view.frame.size.height)
        self.view.addSubview(ScheduleTripVC.view)
        
        UIView.animate(withDuration: 0.3) {
            ScheduleTripVC.view.frame = self.view.bounds
        }
    }
    
    func selectingPassengerSelected(_ selectedId: String) {
        print(selectedId)
        self.lblPassengerNumber.text = selectedId
    }
    
    func SelectProfile(_ selectedName : String){
        print(selectedName)
        self.lblProfileType.text = selectedName
    }
    
    // MARK: - ButtonActions
    // MARK: -
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func btnConfirmBooking(_ sender: UIButton) {
        callBooking()
    }
    
    @IBAction func btnSelectProfile(_ sender: UIButton) {
        self.addProfile()
    }
    
    @IBAction func btnSelectPassenger(_ sender: UIButton) {
        self.addPassenger()
    }
    
    @IBAction func promoAction(_ sender: Any) {
        let storyboard  = UIStoryboard(name: "Main", bundle: nil)
        let vc = storyboard .instantiateViewController(withIdentifier: "PromoVC") as! PromoVC
        vc.delegate = self
        self.navigationController?.pushViewController(vc, animated: true)
        
    }
    
   
    
    
    // MARK: - Location Delegate
    // MARK: -
    
    // Camera change Position this methods will call every time
    
    func mapView(_ mapView: GMSMapView, didChange position: GMSCameraPosition) {
        var destinationLocation = CLLocation()
        destinationLocation = CLLocation(latitude: position.target.latitude,  longitude: position.target.longitude)
        PickUpLocation = destinationLocation.coordinate
        updateLocationoordinates(coordinates: PickUpLocation)
    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if (status == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
        }
    }
    
    // MARK: - Collection View Delegate and DataSource
    // MARK: -
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: UIScreen.main.bounds.width, height: 159.0)
    }
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsets(top: 0.0, left: 0.0, bottom: 0.0, right: 0.0)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return arrDeviceType.count
    }
    
    func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        let aDict:[String:Any] = self.arrDeviceType[indexPath.item] as! [String : Any]
        vehicle_type_id = (aDict["vehicleTypeId"] as? String)!
        print(indexPath.item)
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let clcVehicleTypeCell = collectionView.dequeueReusableCell(withReuseIdentifier: "clcVehicleTypeCell", for: indexPath) as! clcVehicleTypeCell
        
        let aDict:[String:Any] = self.arrDeviceType[indexPath.item] as! [String : Any]
        
        print(aDict)
        clcVehicleTypeCell.lblVehicleTypeName.text = aDict["vehicleTypeName"] as? String
        clcVehicleTypeCell.delegate = self
        
        clcVehicleTypeCell.arrDeviceType = aDict["vehicleType"] as! [Any]
        
        
        if clcVehicleTypeCell.arrSelection.count < 1{
            clcVehicleTypeCell.arrSelection.append("1")
            clcVehicleTypeCell.arrSelection.append("4")
            clcVehicleTypeCell.arrSelection.append("6")
        }
        clcVehicleTypeCell.clcVehicleInfo.reloadData()
        return clcVehicleTypeCell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print(indexPath.row)
    }
    
}
