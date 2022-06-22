//
//  HomeVC.swift
//  Arrive5
//
//  Created by Joy on 04/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces
import SVProgressHUD

class HomeVC: UIViewController,GMSMapViewDelegate,CLLocationManagerDelegate {
    
    @IBOutlet weak var vwGoogleMap: GMSMapView!
    @IBOutlet weak var lblWhereTo: UILabel!
    var placesClient: GMSPlacesClient!

    var Driver: [[String:Any]] = [[:]]
    var timer: DispatchSourceTimer?
    var appDelegate  = UIApplication.shared.delegate as! AppDelegate
    
//Rahul 26july2018 ---->
    let locationManager = CLLocationManager()
    var currentLocation: CLLocation!

    override func viewDidLoad() {
        super.viewDidLoad()
        placesClient = GMSPlacesClient.shared()

        // Ask for Authorisation from the User.
        self.locationManager.requestAlwaysAuthorization()
        
        // For use in foreground
        self.locationManager.requestWhenInUseAuthorization()
        if CLLocationManager.locationServicesEnabled() {
            locationManager.delegate = self
            locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
            locationManager.startUpdatingLocation()
        }
    }
//<------------------

    
    
    override func viewWillAppear(_ animated: Bool) {
        
        if self.children.count > 0{
            let viewControllers:[UIViewController] = self.children
            for viewContoller in viewControllers{
                viewContoller.willMove(toParent: nil)
                viewContoller.view.removeFromSuperview()
                viewContoller.removeFromParent()
            }
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(true)
        SVProgressHUD.dismiss()
    }
    
  
 
    
    
    
    
    func markerDrawing(){
        //vwGoogleMap.clear()
        vwGoogleMap.animate(toLocation: CLLocationCoordinate2D(latitude: currentLocation.coordinate.latitude, longitude: currentLocation.coordinate.longitude))
        
        vwGoogleMap.setMinZoom(4.6, maxZoom: 20)
        let camera = GMSCameraPosition.camera(withLatitude: currentLocation.coordinate.latitude, longitude: currentLocation.coordinate.longitude, zoom: 16.0)
        let mapView = GMSMapView.map(withFrame: self.vwGoogleMap.bounds, camera: camera)
        vwGoogleMap.animate(to: camera)
        mapView.isMyLocationEnabled = true
    }
    

    
    
    func FindingDriver(){
        let aStrApi = "\(Constant.API.kDriverSearch)"
        let dictData : [String : AnyObject]!
        
        let lat = "\(appDelegate.cordinates.latitude)"
        let long = "\(appDelegate.cordinates.longitude)"
        
        dictData = ["lat" : lat,
                    "lng":long] as [String : AnyObject]
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            if json["status"].rawString() == "true"{
                self.Driver = json["result"].rawValue as! [[String:Any]]
                for driverVal in self.Driver{
                    let marker = GMSMarker()
                    let driverLat = driverVal["latitude"] as! String
                    let driverLong = driverVal["longitude"] as! String
                    let lat = Double(driverLat)
                    let long = Double(driverLong)
                   
                    marker.position = CLLocationCoordinate2D(latitude: lat!, longitude: long!)
                    marker.icon = UIImage(named: "car")
                    marker.map = self.vwGoogleMap
                    
                }
//                for state in states {
//                    let state_marker = GMSMarker()
//                    state_marker.position = CLLocationCoordinate2D(latitude: state.lat, longitude: state.long)
//                    state_marker.title = state.name
//                    state_marker.snippet = "Hey, this is \(state.name)"
//                    state_marker.map = mapView
//                    markerDict[state.name] = state_marker
//                }
                
            }else{
                self.view.makeToast(json["msg"].rawString())
            }
        }, failure: {(error) in
            
        })
    }

    func UserLatLongUpdate(){
        let userId = UserDefaults.standard.string(forKey: "user_id")
        //let aStrApi = "http://arrive5.pcthepro.com/api/locationUpdate"
        let aStrApi = "http://arrive5.pcthepro.com/webservice/user/locationUpdate"

        
        let lat = "\(currentLocation.coordinate.latitude)"
        let long = "\(currentLocation.coordinate.longitude)"
        
        let dictData : [String : AnyObject]!
        dictData = ["lat" : lat,
                    "lng": long,
                    "type":"ios",
                    "id" : userId! ] as [String : AnyObject]
        print(dictData)
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            if json["status"].rawString() == "true"{
                self.view.makeToast(json["msg"].rawString())
                self.vwGoogleMap.isMyLocationEnabled = true
                self.vwGoogleMap.settings.myLocationButton = true
            }else{
                self.view.makeToast(json["msg"].rawString())
            }
        }, failure: {(error) in

        })
    }
    
    @IBAction func btnWhereToAction(_ sender: UIButton) {
        let acController = GMSAutocompleteViewController()
        acController.delegate = self
        self.present(acController, animated: true, completion: nil)
//        placesClient.currentPlace(callback: { (placeLikelihoodList, error) -> Void in
//            if let error = error {
//                print("Current Place error: \(error.localizedDescription)")
//                return
//            }
//
////            self.nameLabel.text = "No current place"
////            self.addressLabel.text = ""
//
//            if let placeLikelihoodList = placeLikelihoodList {
//                let place = placeLikelihoodList.likelihoods.first?.place
//                if let place = place {
////                    self.nameLabel.text = place.name
//                   var str = place.formattedAddress?.components(separatedBy: ", ")
//                        .joined(separator: "\n")
//
//
//                }
//            }
//        })
    }
    
    
    @IBAction func btnScheduleLater(_ sender: UIButton) {
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "ScheduleRideVC") as! ScheduleRideVC
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @IBAction func btnSideMenuShow(_ sender: UIButton) {
        
        let HomeSideMenuVC = self.storyboard?.instantiateViewController(withIdentifier: "HomeSideMenuVC") as! HomeSideMenuVC
        HomeSideMenuVC.PathDirection = "HomeVc"
        addChild(HomeSideMenuVC)
        
        HomeSideMenuVC.didMove(toParent: self)
        
        UIView.animate(withDuration: 0.5, delay: 1.0, usingSpringWithDamping: 0.1, initialSpringVelocity: 0.0, options: .transitionFlipFromLeft, animations: {
            HomeSideMenuVC.view.frame = CGRect(x: self.view.frame.minX,
                                               y: 0,
                                               width: self.view.frame.size.width ,
                                               height: self.view.frame.size.height)
        }, completion: {(bool) in
            self.view.addSubview(HomeSideMenuVC.view)
            HomeSideMenuVC.view.frame = self.view.bounds
        })
    }

}

extension HomeVC: GMSAutocompleteViewControllerDelegate {
    
    func viewController(_ viewController: GMSAutocompleteViewController, didAutocompleteWith place: GMSPlace) {
        
        print("Place name: \(place.name)")
        print("Place address: \(place.formattedAddress ?? "null")")
        
        let latitude: String = "\(place.coordinate.latitude)"
        let longitude: String = "\(place.coordinate.longitude)"

        print("Place coordinate: \(latitude) - \(longitude)")

        self.lblWhereTo.text = place.formattedAddress
        let RequestRideVC = self.storyboard?.instantiateViewController(withIdentifier: "RequestRideVC") as! RequestRideVC
        RequestRideVC.SelectedLocation = place.formattedAddress
        RequestRideVC.selectedCoord = place.coordinate
        self.navigationController?.pushViewController(RequestRideVC, animated: true)
//        print("Place attributions: \(String(describing: place.attributions))")
        
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


extension UIColor {
    convenience init(hexString: String) {
        let hex = hexString.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int = UInt32()
        Scanner(string: hex).scanHexInt32(&int)
        let a, r, g, b: UInt32
        
        switch hex.count {
        case 3: // RGB (12-bit)
            (a, r, g, b) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
        case 6: // RGB (24-bit)
            (a, r, g, b) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
        case 8: // ARGB (32-bit)
            (a, r, g, b) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
        default:
            (a, r, g, b) = (255, 0, 0, 0)
        }
        self.init(red: CGFloat(r) / 255, green: CGFloat(g) / 255, blue: CGFloat(b) / 255, alpha: CGFloat(a) / 255)
    }
}

//Rahul 26july2018---->
extension HomeVC {
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation])
    {
        guard let locValue: CLLocationCoordinate2D = manager.location?.coordinate else { return }
        print("locations = \(locValue.latitude) \(locValue.longitude)")
        currentLocation = manager.location
        locationManager.stopUpdatingLocation()
        UserLatLongUpdate()
        DispatchQueue.main.async {
            self.markerDrawing()
            self.FindingDriver()
        }
    }
}
//---------<




//self.markerDrawing()
//self.FindingDriver()



// var locManager = CLLocationManager()

//        locManager.requestWhenInUseAuthorization()
//        if( CLLocationManager.authorizationStatus() == .authorizedWhenInUse ||
//            CLLocationManager.authorizationStatus() ==  .authorizedAlways){
//            currentLocation = locManager.location
//            UserLatLongUpdate()
//        }




//print("\(appDelegate.cordinates.latitude)")
//print("\(appDelegate.cordinates.longitude)")

//        DispatchQueue.main.async {
//            self.appDelegate.UserLatLongUpdate()
//            self.vwGoogleMap.clear()
//            self.markerDrawing()
//        }
//        markerDrawing()
//        self.FindingDriver()
//        vwGoogleMap.isMyLocationEnabled = true
//        vwGoogleMap.settings.myLocationButton = true


//        DispatchQueue.global(qos: .userInitiated).async { // 1
//            self.appDelegate.UserLatLongUpdate()
//            DispatchQueue.main.async { // 2
//                self.vwGoogleMap.clear() //3
//                self.markerDrawing()
//                print("\(self.appDelegate.cordinates.latitude)")
//                print("\(self.appDelegate.cordinates.longitude)")
//
//            }
//        }




//
//    func markerDrawing(){
//        print(appDelegate.cordinates)
//        vwGoogleMap.animate(toLocation: CLLocationCoordinate2D(latitude: appDelegate.cordinates.latitude, longitude: appDelegate.cordinates.longitude))
//        vwGoogleMap.setMinZoom(4.6, maxZoom: 20)
//        let camera = GMSCameraPosition.camera(withLatitude: appDelegate.cordinates.latitude, longitude: appDelegate.cordinates.longitude, zoom: 16.0)
//        let mapView = GMSMapView.map(withFrame: self.vwGoogleMap.bounds, camera: camera)
//        vwGoogleMap.animate(to: camera)
//        mapView.isMyLocationEnabled = true
//    }
//

