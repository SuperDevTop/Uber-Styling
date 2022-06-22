//
//  PickUpClientVC.swift
//  Arrive5Driver
//
//  Created by Joy on 02/05/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces

class PickUpClientVC: UIViewController,CLLocationManagerDelegate {

    @IBOutlet weak var btnPickUpClient: UIButton!
    @IBOutlet weak var vwDropUpMap: GMSMapView!
    @IBOutlet weak var lblArriveTime: UILabel!
    var locationManager:CLLocationManager!
    
    var DropDownLocation : CLLocationCoordinate2D!
    var PickUpLocation : CLLocationCoordinate2D!
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    let usrId  = UserDefaults.standard.value(forKey: "user_id") as! String
//    var bookingId : Int = 0
    var userId : String = ""
  var bookingId : String = ""
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
        
        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
      
       
                self.bookingId = appDelegate.userInfo["gcm.notification.booking_rand"] as! String
                 self.userId = appDelegate.userInfo["gcm.notification.user_id"] as! String
                
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    @IBAction func btnPickUpClientAction(_ sender: UIButton) {
        self.callApiVal()
    }
    
    @IBAction func btnRiderInfo(_ sender: UIButton) {
        let RideOverviewVC = self.storyboard?.instantiateViewController(withIdentifier: "RideOverviewVC") as! RideOverviewVC
        
        self.navigationController?.pushViewController(RideOverviewVC, animated: true)
    }
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    func callApiVal(){
        
        let aStrApi = "\(Constant.API.kArrived)"
        let aDictParam : [String:Any]!
        aDictParam = ["driver_id":usrId,
                      "user_id":self.userId,
                      "booking_id":self.bookingId]
        
        APIManager.requestPOSTURL(aStrApi, params: aDictParam as [String : AnyObject]?, headers: nil, success: {(json) in
            
            print("tap on on forground app",json)
            if json["status"].rawString() == "true"{
                
                self.DropOffOtpScreen()
                
                
            }else{
                self.view.makeToast(json["message"].rawString())
            }
            
        }, failure: { (error) in
            
         

            
            self.view.makeToast(error.localizedDescription)
            
        })
        
        
    }
    
    func DropOffOtpScreen(){
        let DropOffClientOtpVC = self.storyboard?.instantiateViewController(withIdentifier: "DropOffClientOtpVC") as! DropOffClientOtpVC
        
        
        
        addChild(DropOffClientOtpVC)
        
        DropOffClientOtpVC.didMove(toParent: self)
        
        DropOffClientOtpVC.view.frame = CGRect(x: 0,
                                             y: self.view.frame.size.height,
                                             width: self.view.frame.size.width,
                                             height: self.view.frame.size.height)
        self.view.addSubview(DropOffClientOtpVC.view)
        
        UIView.animate(withDuration: 0.3) {
            DropOffClientOtpVC.view.frame = self.view.bounds
        }
    }
    
    func markerDrawing(){
        let locValue:CLLocationCoordinate2D = locationManager.location!.coordinate
        vwDropUpMap.animate(toLocation: CLLocationCoordinate2D(latitude: (self.locationManager.location?.coordinate.latitude)! , longitude: (self.locationManager.location?.coordinate.longitude)!))
        
        vwDropUpMap.setMinZoom(4.6, maxZoom: 20)
        let camera = GMSCameraPosition.camera(withLatitude: (self.locationManager.location?.coordinate.latitude)!, longitude: (self.locationManager.location?.coordinate.longitude)!, zoom: 16.0)
        let mapView = GMSMapView.map(withFrame: self.vwDropUpMap.bounds, camera: camera)
        vwDropUpMap.animate(to: camera)
        mapView.isMyLocationEnabled = true
//        let Current = GMSMarker()
//        Current.position = CLLocationCoordinate2D(latitude: (self.PickUpLocation.latitude) , longitude: (self.PickUpLocation.longitude))
//        Current.icon = UIImage(named: "pin-1")
//        Current.map = vwDropUpMap
        
        let DropDown = GMSMarker()
        DropDown.position = CLLocationCoordinate2D(latitude: (self.locationManager.location?.coordinate.latitude)! , longitude: (self.locationManager.location?.coordinate.longitude)!)
        DropDown.icon = UIImage(named: "car")
        DropDown.map = vwDropUpMap
    }
    
    
    func PolylineDraw(_ MarkerType : Int ){
        
        
        let origin = "\(self.PickUpLocation.latitude),\(self.PickUpLocation.longitude)"
        let destination = "\(self.DropDownLocation.latitude),\(self.DropDownLocation.longitude)"
        let url = "https://maps.googleapis.com/maps/api/directions/json?origin=\(origin)&destination=\(destination)&mode=driving&key=AIzaSyAc3ku-xOE3bfjaQtcdNBn4Sqqjfu5LsHg"
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
                    polyline.strokeColor = .black
                    polyline.strokeWidth = 2.0
                    polyline.map = self.vwDropUpMap
                }else{
                    let polyline = GMSPolyline(path: path)
                    let red = UIColor.red
                    //                        let darkGrey = UIColor(hexString: "#f7acba")
                    polyline.strokeColor = red
                    polyline.strokeWidth = 2.0
                    polyline.map = self.vwDropUpMap
                }
                
                
            }
        }, failure: {(error) in
            
        })
        
    }
    
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if (status == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
            self.PickUpLocation = locationManager.location?.coordinate
            self.markerDrawing()
            
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
