//
//  ArriveClientVC.swift
//  Arrive5Driver
//
//  Created by Joy on 02/05/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces

class ArriveClientVC: UIViewController , CLLocationManagerDelegate{

    @IBOutlet weak var btnArriveClient: UIButton!
    @IBOutlet weak var vwDropUpMap: GMSMapView!
    @IBOutlet weak var lblDropUpLocation: UILabel!
    
    var DropDownLocation : CLLocationCoordinate2D!
    var PickUpLocation : CLLocationCoordinate2D!
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    var StartCoord : String = ""
    var locationManager:CLLocationManager!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.StartCoord = self.appDelegate.userInfo["gcm.notification.start_point"] as! String
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
        self.lblDropUpLocation.text = self.appDelegate.userInfo["gcm.notification.start_point"] as? String
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    @IBAction func btnArriveClientAction(_ sender: UIButton) {
        self.ConfirmPopScreen()
    }
    
    @IBAction func btnRiderInfo(_ sender: UIButton) {
        let RideOverviewVC = self.storyboard?.instantiateViewController(withIdentifier: "RideOverviewVC") as! RideOverviewVC
        
        self.navigationController?.pushViewController(RideOverviewVC, animated: true)
    }
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popToRootViewController(animated: false)
    }
    
    @IBAction func btnNavigateAction(_ sender: UIButton) {
        
        if (UIApplication.shared.canOpenURL(URL(string:"comgooglemaps://")!)) {
            UIApplication.shared.openURL(NSURL(string:
                "comgooglemaps://?saddr=\(self.PickUpLocation.latitude),\(self.PickUpLocation.longitude)&daddr=\(self.DropDownLocation.latitude),\(self.DropDownLocation.longitude)&directionsmode=driving")! as URL)
            
        } else {
            // if GoogleMap App is not installed
            UIApplication.shared.openURL(NSURL(string:
                "https://www.google.co.in/maps/dir/?saddr=\(self.PickUpLocation.latitude),\(self.PickUpLocation.longitude)&daddr=\(self.DropDownLocation.latitude),\(self.DropDownLocation.longitude)&directionsmode=driving")! as URL)
        }
        
    }
    
    func ConfirmPopScreen(){
        let ConfirmArrivalVC = self.storyboard?.instantiateViewController(withIdentifier: "ConfirmArrivalVC") as! ConfirmArrivalVC
        
        ConfirmArrivalVC.pathValue = "ArriveClient"
        
        addChild(ConfirmArrivalVC)
        
        ConfirmArrivalVC.didMove(toParent: self)
        
        ConfirmArrivalVC.view.frame = CGRect(x: 0,
                                           y: self.view.frame.size.height,
                                           width: self.view.frame.size.width,
                                           height: self.view.frame.size.height)
        self.view.addSubview(ConfirmArrivalVC.view)
        
        UIView.animate(withDuration: 0.3) {
            ConfirmArrivalVC.view.frame = self.view.bounds
        }
    }
    func PickUpCoord(){
        let geoCoder = CLGeocoder()
        geoCoder.geocodeAddressString(self.StartCoord, completionHandler: {(placemarks, error) -> Void in
            if((error) != nil){
                print("Error", error ?? "")
            }
            if let placemark = placemarks?.first {
                let coordinates:CLLocationCoordinate2D = placemark.location!.coordinate
                self.DropDownLocation = coordinates
                print("Lat: \(coordinates.latitude) -- Long: \(coordinates.longitude)")
                self.markerDrawing()
                self.PolylineDraw(2)
            }
        })
    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if (status == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
            self.PickUpLocation = locationManager.location?.coordinate
            self.PickUpCoord()
            
        }
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
    
    func markerDrawing(){
        let locValue:CLLocationCoordinate2D = locationManager.location!.coordinate
        vwDropUpMap.animate(toLocation: CLLocationCoordinate2D(latitude: (self.PickUpLocation.latitude) , longitude: (self.PickUpLocation.longitude)))
        
        vwDropUpMap.setMinZoom(4.6, maxZoom: 20)
        let camera = GMSCameraPosition.camera(withLatitude: (self.PickUpLocation.latitude), longitude: (self.PickUpLocation.longitude), zoom: 16.0)
        let mapView = GMSMapView.map(withFrame: self.vwDropUpMap.bounds, camera: camera)
        vwDropUpMap.animate(to: camera)
        mapView.isMyLocationEnabled = true
        let Current = GMSMarker()
        Current.position = CLLocationCoordinate2D(latitude: (self.PickUpLocation.latitude) , longitude: (self.PickUpLocation.longitude))
        Current.icon = UIImage(named: "car")
        Current.map = vwDropUpMap
        
        let DropDown = GMSMarker()
        DropDown.position = CLLocationCoordinate2D(latitude: (self.DropDownLocation.latitude) , longitude: (self.DropDownLocation.longitude) )
        DropDown.icon = UIImage(named: "pin-1")
        DropDown.map = vwDropUpMap
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
