//
//  RequestRideVC.swift
//  Arrive5
//
//  Created by Joy on 17/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces
import SVProgressHUD

class RequestRideVC: UIViewController,GMSMapViewDelegate,CLLocationManagerDelegate{
    
    // MARK: - Outlets
    // MARK: -

    @IBOutlet weak var vwGoogleMap: GMSMapView!
    @IBOutlet weak var btnEditSelectedLoc: UIButton!
    @IBOutlet weak var btnEditCurrentLocFrst: UIButton!
    @IBOutlet weak var tfSelectedLocation: UITextField!
    @IBOutlet weak var tfCurrentLocation: UITextField!
    
    // MARK: - Properties
    // MARK: -
    var Driver: [[String:Any]] = [[:]]
    var destinationMarker : GMSMarker!
    var locationManager:CLLocationManager!
    var PathValue : String!
    var SelectedLocation : String!
    var selectedCoord : CLLocationCoordinate2D!
    var PickUpLocation : CLLocationCoordinate2D!
    
    // MARK: - VCCycles
    // MARK: -
    
    override func viewDidLoad() {
        super.viewDidLoad()
        locationManager = CLLocationManager()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        let authorizationStatus = CLLocationManager.authorizationStatus()
        if (authorizationStatus == CLAuthorizationStatus.notDetermined) {
            locationManager.requestWhenInUseAuthorization()
        }
        else if (authorizationStatus == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
            markerDrawing()
        }
        self.vwGoogleMap.delegate = self
        self.tfSelectedLocation.text = self.SelectedLocation
        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        self.btnEditSelectedLoc.isHidden = true
        self.btnEditCurrentLocFrst.isHidden = true
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Button Actions
    // MARK: -
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func tfCurrentLocationAction(_ sender: UITextField) {
        self.btnEditCurrentLocFrst.isHidden = true
    }
    
    @IBAction func tfSelectedLocationAction(_ sender: UITextField) {
        self.btnEditSelectedLoc.isHidden = true
    }
    
    @IBAction func tfCurrentLocBegin(_ sender: UITextField) {
        self.btnEditSelectedLoc.isHidden = true
        self.btnEditCurrentLocFrst.isHidden = true
        let acController = GMSAutocompleteViewController()
        PathValue = "Current"
        acController.delegate = self
        self.present(acController, animated: true, completion: nil)
    }
    
    @IBAction func tfSelectedLocBegin(_ sender: UITextField) {
        self.btnEditSelectedLoc.isHidden = true
        self.btnEditCurrentLocFrst.isHidden = true
        let acController = GMSAutocompleteViewController()
        PathValue = "Selected"
        acController.delegate = self
        self.present(acController, animated: true, completion: nil)
    }
    
    @IBAction func btnEditCurrentLocAction(_ sender: UIButton) {
        tfCurrentLocation.becomeFirstResponder()
        tfCurrentLocation.text = ""
    }
    
    @IBAction func btnEditSelectedLocAction(_ sender: UIButton) {
        tfSelectedLocation.becomeFirstResponder()
        tfSelectedLocation.text = ""
    }
    
    @IBAction func btnConfirmAction(_ sender: UIButton) {
        
        if self.tfCurrentLocation.text == ""{
            self.view.makeToast("Please Enter Current Location")
        }
        else{
            if self.tfSelectedLocation.text == ""{
                self.view.makeToast("Please Enter Drop Down Location")
            }
            else{
                let ConfirmBookingVC = self.storyboard?.instantiateViewController(withIdentifier: "ConfirmBookingVC") as! ConfirmBookingVC
                ConfirmBookingVC.PickUpLocation = self.PickUpLocation
                ConfirmBookingVC.DropDownLocation = self.selectedCoord
                ConfirmBookingVC.PickUpAddress = self.tfCurrentLocation.text
                ConfirmBookingVC.DropUpAddress = self.tfSelectedLocation.text
                self.navigationController?.pushViewController(ConfirmBookingVC, animated: true)
            }
        }
    }
    
    // MARK: - Methods
    // MARK: -
    
    func markerDrawing(){
        
        print("\(locationManager.location!.coordinate.latitude)")
        print("\(locationManager.location!.coordinate.longitude)")

        let locValue:CLLocationCoordinate2D = locationManager.location!.coordinate
        vwGoogleMap.animate(toLocation: CLLocationCoordinate2D(latitude: locValue.latitude, longitude: locValue.longitude))
        
        vwGoogleMap.setMinZoom(4.6, maxZoom: 20)
        let camera = GMSCameraPosition.camera(withLatitude: locValue.latitude, longitude: locValue.longitude, zoom: 16.0)
        let mapView = GMSMapView.map(withFrame: self.vwGoogleMap.bounds, camera: camera)
        vwGoogleMap.animate(to: camera)
        mapView.isMyLocationEnabled = true
        let Current = GMSMarker()
        Current.position = CLLocationCoordinate2D(latitude: locValue.latitude , longitude: locValue.longitude )
        //        Current.title = "User location"
        Current.icon = UIImage(named: "pin")
        Current.map = vwGoogleMap
    }
    
    func FindingDriver(){
        let aStrApi = "\(Constant.API.kDriverSearch)"
        let dictData : [String : AnyObject]!
        dictData = ["lat" : locationManager.location?.coordinate.latitude as AnyObject,
                    "lng":locationManager.location?.coordinate.longitude as AnyObject] as [String : AnyObject]
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            if json["status"].rawString() == "true"{
                self.Driver = json["details"].rawValue as! [[String:Any]]
                for driverVal in self.Driver{
                    let Warehouse = GMSMarker()
                    let driverLat = driverVal["latitude"] as! String
                    let driverLong = driverVal["longitude"] as! String
                    let lat = Double(driverLat)
                    let long = Double(driverLong)
                    Warehouse.position = CLLocationCoordinate2D(latitude: lat!, longitude: long!)
                    //        Warehouse.title = "Driver 2"
                    Warehouse.icon = UIImage(named: "car")
                    Warehouse.map = self.vwGoogleMap
                }
            }
            else{
                self.view.makeToast(json["msg"].rawString())
            }
        }, failure: {(error) in
            
        })
    }
    
    func getAddressFromLatLon(pdblLatitude: String, withLongitude pdblLongitude: String) {
    
        var center : CLLocationCoordinate2D = CLLocationCoordinate2D()
        let lat: Double = Double("\(pdblLatitude)")!
        //21.228124
        let lon: Double = Double("\(pdblLongitude)")!
        //72.833770
        let ceo: CLGeocoder = CLGeocoder()
        center.latitude = lat
        center.longitude = lon
        
        let loc: CLLocation = CLLocation(latitude:center.latitude, longitude: center.longitude)
        
        ceo.reverseGeocodeLocation(loc, completionHandler:
            {(placemarks, error) in
                if (error != nil)
                {
                    print("reverse geodcode fail: \(error!.localizedDescription)")
                    self.view.makeToast(error?.localizedDescription)
                }
                else{
                    let pm = placemarks! as [CLPlacemark]
                    
                    if pm.count > 0 {
                        let pm = placemarks![0]
                        var addressString : String = ""
                        if pm.subLocality != nil {
                            addressString = addressString + pm.subLocality! + ", "
                        }
                        if pm.thoroughfare != nil {
                            addressString = addressString + pm.thoroughfare! + ", "
                        }
                        if pm.locality != nil {
                            addressString = addressString + pm.locality! + " "
                        }
                        self.tfCurrentLocation.text = addressString
                        print(addressString)
                    }
                }
        })
    }
    
    func updateLocationoordinates(coordinates:CLLocationCoordinate2D) {
        if destinationMarker == nil
        {
            destinationMarker = GMSMarker()
            destinationMarker.position = CLLocationCoordinate2D(latitude: (self.PickUpLocation.latitude) , longitude: (self.PickUpLocation.longitude))
            let image = UIImage(named:"pin")
            destinationMarker.icon = image
            destinationMarker.map = vwGoogleMap
            destinationMarker.appearAnimation = .pop
        }
        else
        {
            CATransaction.begin()
            CATransaction.setAnimationDuration(1.0)
            destinationMarker.position =  coordinates
            CATransaction.commit()
            Address(coordinates)
        }
    }
    
    func Address(_ PickUpLocation : CLLocationCoordinate2D! ){
//        let url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=\(PickUpLocation.latitude),\(PickUpLocation.longitude)&key=AIzaSyA_ErWhZguYEK3cQA_lb0pb8rA_dfJ0ZRM"
        
//         let url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=\(PickUpLocation.latitude),\(PickUpLocation.longitude)&key=AIzaSyBNM-OE-KJPz_KEkY5uLjMbwMhLZ0uoBiM"
        
         let url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=\(PickUpLocation.latitude),\(PickUpLocation.longitude)&key=AIzaSyBNM-OE-KJPz_KEkY5uLjMbwMhLZ0uoBiM"
        
       
        
        APIManager.requestGetURL(url, success: {(json) in
            
            if json["status"].stringValue == "OK"{
                let routes = json["results"].arrayValue
                for route in routes
                {
                    print(route)
                    if routes.count > 0{
                        let AddressVal = routes[0].dictionaryValue
                        let formAddress = AddressVal["formatted_address"]?.stringValue
                        self.tfCurrentLocation.text = formAddress
                    }
                    else{
                    }
                }
            }
            else{
                self.view.makeToast("Error")
            }
        }, failure: {(error) in
            
        })
    }
    
    
    // MARK: - Delegates
    // MARK: -
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if (status == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
            self.PickUpLocation = locationManager.location?.coordinate
            getAddressFromLatLon(pdblLatitude: "\(locationManager.location?.coordinate.latitude ?? 29.229)", withLongitude: "\(locationManager.location?.coordinate.longitude ?? 79.225)")
        }
    }
    // Camera change Position this methods will call every time
    
//    func mapView(_ mapView: GMSMapView, didChange position: GMSCameraPosition) {
//        
//    }
    
    func mapView(_ mapView: GMSMapView, idleAt position: GMSCameraPosition){
        var destinationLocation = CLLocation()
        destinationLocation = CLLocation(latitude: position.target.latitude,  longitude: position.target.longitude)
//        PickUpLocation = destinationLocation.coordinate
//        updateLocationoordinates(coordinates: PickUpLocation)
    }
    
}

// MARK: - Extension
// MARK: -

extension RequestRideVC: GMSAutocompleteViewControllerDelegate {
    
    func viewController(_ viewController: GMSAutocompleteViewController, didAutocompleteWith place: GMSPlace) {
        
        print("Place name: \(place.name)")
        print("Place address: \(place.formattedAddress ?? "null")")
        if PathValue == "Current"{
            self.tfCurrentLocation.text = "\(place.formattedAddress!)"
            self.PickUpLocation = place.coordinate
        }
        else{
            self.tfSelectedLocation.text = "\(place.formattedAddress!)"
            self.selectedCoord = place.coordinate
        }
        
        print("Place attributions: \(String(describing: place.attributions))")
        
        self.dismiss(animated: true, completion: nil)
    }
    
    func viewController(_ viewController: GMSAutocompleteViewController, didFailAutocompleteWithError error: Error) {
        self.dismiss(animated: true, completion: nil)
    }
    
    func wasCancelled(_ viewController: GMSAutocompleteViewController) {
        print("Autocomplete was cancelled.")
        self.dismiss(animated: true, completion: nil)
    }
}
