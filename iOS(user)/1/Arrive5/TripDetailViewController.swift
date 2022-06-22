//
//  TripDetailViewController.swift
//  Arrive5
//
//  Created by Maestros Infotech on 10/12/20.
//  Copyright © 2020 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps

class TripDetailViewController: UIViewController, CLLocationManagerDelegate {

   var destinationMarker : GMSMarker!
   var locationManager:CLLocationManager!
   var DropDownLocation : CLLocationCoordinate2D!
   var PickUpLocation : CLLocationCoordinate2D!
    @IBOutlet var DriverNameLabel: UILabel!
    
    @IBOutlet var VehicleNumber: UILabel!
    
    @IBOutlet var DateLabess: UILabel!
    
    @IBOutlet var ModalNamess: UILabel!
    
    @IBOutlet var TwoLabel: UILabel!
    
    @IBOutlet var Fromlabel: UILabel!
    
    @IBOutlet var ReceiptBtn: UIButton!
    
    @IBOutlet weak var PickUplat : NSString!
    
    @IBOutlet weak var Picklong : NSString!

    @IBOutlet weak var droplat : NSString!

    @IBOutlet weak var droplongs : NSString!

     @IBOutlet weak var tipidss : NSString!
    var alldata: [String:Any] = [:]
    
    @IBOutlet var GoogleMapss: GMSMapView!
    override func viewDidLoad() {
           super.viewDidLoad()

        print(alldata)
        
        
        tipidss = alldata ["booking_id"] as? String as NSString?
        
        
        PickUplat =  alldata ["start_point_lat"] as? String as NSString?
        
          Picklong =  alldata ["start_point_long"] as? String as NSString?
        
         droplat =  alldata ["end_point_lat"] as? String as NSString?
        
         droplongs =  alldata ["end_point_long"] as? String as NSString?
        
        
        DriverNameLabel.text = alldata ["driverName"] as? String
        
        DateLabess.text = alldata ["schedule_date"] as? String
        
         ModalNamess.text = alldata ["vehicle_model"] as? String
        
       TwoLabel.text = alldata ["start_point"] as? String
              Fromlabel.text = alldata ["end_point"] as? String
        VehicleNumber.text =   alldata ["carNo"] as? String
        ReceiptBtn.layer.cornerRadius = 21
        
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
            
            
            let origin = "\(self.PickUplat.doubleValue),\(self.Picklong.doubleValue)"
            let destination = "\(self.droplat.doubleValue),\(self.droplongs.doubleValue)"
            
            
//            let origin = "\("23.2313"),\("77.4326")"
//            let destination = "\("23.2283"),\("77.4365")"
            
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
                        polyline.map = self.GoogleMapss
                    }else{
                        let polyline = GMSPolyline(path: path)
                        let red = UIColor(red: 48.0/255, green: 177.0/255, blue: 236.0/255, alpha: 1.0)
                        //                        let darkGrey = UIColor(hexString: "#f7acba")
                        polyline.strokeColor = red
                        polyline.strokeWidth = 5.0
                        polyline.map = self.GoogleMapss
                    }
                    
                    
                }
            }, failure: {(error) in
                
            })
        }
        
        func markerDrawing(){
            
            
            let origin = "\(self.PickUplat.doubleValue),\(self.Picklong.doubleValue)"
            let destination = "\(self.droplat.doubleValue),\(self.droplongs.doubleValue)"
            
            let locValue:CLLocationCoordinate2D = locationManager.location!.coordinate
            GoogleMapss.animate(toLocation: CLLocationCoordinate2D(latitude: (self.PickUplat.doubleValue) , longitude: (self.Picklong.doubleValue)))
            
            
            
            
            GoogleMapss.setMinZoom(4.6, maxZoom: 20)
            let camera = GMSCameraPosition.camera(withLatitude: (self.PickUplat.doubleValue), longitude: (self.Picklong.doubleValue), zoom: 16.0)
            let mapView = GMSMapView.map(withFrame: self.GoogleMapss.bounds, camera: camera)
            GoogleMapss.animate(to: camera)
            mapView.isMyLocationEnabled = true
            let Current = GMSMarker()
            Current.position = CLLocationCoordinate2D(latitude: (self.PickUplat.doubleValue) , longitude: (self.Picklong.doubleValue))
            Current.icon = UIImage(named: "pin")
            Current.map = GoogleMapss
            
            let DropDown = GMSMarker()
            DropDown.position = CLLocationCoordinate2D(latitude: (droplat.doubleValue) , longitude: (droplongs.doubleValue) )
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
            
            DropDown.map = GoogleMapss
        }
        
        func updateLocationoordinates(coordinates:CLLocationCoordinate2D) {
            
            if destinationMarker == nil
            {
                destinationMarker = GMSMarker()
                destinationMarker.position = CLLocationCoordinate2D(latitude: (23.2313) , longitude: (77.4326))
                let image = UIImage(named:"pin")
                destinationMarker.icon = image
                destinationMarker.map = GoogleMapss
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
    
    @IBAction func ReceiptBtnActionss(_ sender: UIButton)
    {
        let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "ShowReceiptViewController") as! ShowReceiptViewController
        homeVc.dripidss = tipidss
               self.navigationController?.pushViewController(homeVc, animated: true)
    }
    
    @IBAction func BackBtnaCtionss(_ sender: UIButton)
    {
        self.navigationController?.popViewController(animated: true)
    }
}
