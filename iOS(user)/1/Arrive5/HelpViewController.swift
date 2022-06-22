//
//  HelpViewController.swift
//  Arrive5
//
//  Created by Test on 05/10/1941 Saka.
//  Copyright © 1941 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces
import SVProgressHUD
import CoreLocation

class HelpViewController: UIViewController,GMSMapViewDelegate,CLLocationManagerDelegate  {

    @IBOutlet var Googlemapview: GMSMapView!
    
    @IBOutlet var DateLabel: UILabel!
    
    @IBOutlet var MoneyLabel: UILabel!
    @IBOutlet var CountLabel: UILabel!
    @IBOutlet var NameLabel: UILabel!
    var Driver: [[String:Any]] = [[:]]
     var arrayPastBooking : [Any] = []
    var timess : String!
    var datess : String!
     var moness : String!
     var contss : String!
    var space1 : String!
    var space2 : String!
    var startlat : String!
    var startlong : String!
    var endpointlat : String!
    var endpointlong : String!
   
    var timer: DispatchSourceTimer?
    var appDelegate  = UIApplication.shared.delegate as! AppDelegate
    var locationManager:CLLocationManager!
    var marker:GMSMarker = GMSMarker()
    //Rahul 26july2018 ---->
    var currentLocation: CLLocation!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        space1 = " "
        
         space2 = " "
          getpastbooking()
         SVProgressHUD.show()
        locationManager = CLLocationManager()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        let authorizationStatus = CLLocationManager.authorizationStatus()
        if (authorizationStatus == CLAuthorizationStatus.notDetermined) {
            locationManager.requestWhenInUseAuthorization()
        } else if (authorizationStatus == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
//            self.placeMarkersInMap()
            
        }
        
        Googlemapview.isMyLocationEnabled = false
        Googlemapview.settings.myLocationButton = false
        

    }
    
    
    
   
    
    func getpastbooking(){
        let aStrApi = "\(Constant.API.lasttripe)"
        let user_id = UserDefaults.standard.string(forKey: "user_id")
        let type = "past"
        let dictData : [String : AnyObject]!
        dictData = ["user_id" : user_id,
                    "type" : type] as [String : AnyObject]
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            self.arrayPastBooking = json["result"].rawValue as! [AnyObject]
           
            let dict: [String : Any] = self.arrayPastBooking[self.arrayPastBooking.count-1] as! [String : Any]
            self.CountLabel.text = dict["carNo."] as? String
             self.moness = dict["amount"] as? String
             let appendString22 = "$"  + self.space2 + self.moness
            self.MoneyLabel.text = appendString22;
            self.timess = dict["schedule_time"] as? String
            self.datess = dict["schedule_date"] as? String
            self.NameLabel.text = dict["vehicle_sub_type_name"] as? String
          
            let appendString2 = self.datess + self.space1 + self.timess
            self.DateLabel.text = appendString2
            self.startlat =  dict["start_point_lat"] as? String
             self.startlong =  dict["start_point_long"] as? String
            self.endpointlat =  dict["end_point_lat"] as? String
            self.endpointlong =  dict["end_point_long"] as? String
            
            self.placeMarkersInMap()
             SVProgressHUD.dismiss()
            
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }

    
    
    func markerDrawing(){
        //vwGoogleMap.clear()
        Googlemapview.animate(toLocation: CLLocationCoordinate2D(latitude: currentLocation.coordinate.latitude, longitude: currentLocation.coordinate.longitude))
        
        Googlemapview.setMinZoom(4.6, maxZoom: 20)
        let camera = GMSCameraPosition.camera(withLatitude: currentLocation.coordinate.latitude, longitude: currentLocation.coordinate.longitude, zoom: 16.0)
        let mapView = GMSMapView.map(withFrame: self.Googlemapview.bounds, camera: camera)
        Googlemapview.animate(to: camera)
        mapView.isMyLocationEnabled = true
    }
    
    
    func placeMarkersInMap(){
        
        for index in 0...1
        {
            if (index == 0)
            {
                let endlat = Double(self.endpointlat)
                let endlong = Double(self.endpointlong)
                
                marker.position = CLLocationCoordinate2D(latitude: endlat!, longitude: endlong!)
                marker.icon = UIImage(named: "car")
                marker.map = self.Googlemapview
            }
            else if (index == 1)
            {

                let startlat = Double(self.startlat)
                let startlong = Double(self.startlong)

        
                
                let locValue: CLLocationCoordinate2D = locationManager.location!.coordinate
                Googlemapview.animate(toLocation: CLLocationCoordinate2D(latitude: startlat!, longitude: startlong!))
                Googlemapview.setMinZoom(4.6, maxZoom: 20)
                marker = GMSMarker(position: locValue)
                marker.appearAnimation = GMSMarkerAnimation.pop
                marker.icon = UIImage(named: "pin")
                marker.infoWindowAnchor = CGPoint(x: 0.44, y: 0.45)
                marker.map = Googlemapview
                moveCamera(marker: marker)
            }
        }
        
       
        
    }
    func moveCamera(marker:GMSMarker){
        Googlemapview.camera = GMSCameraPosition(target: marker.position, zoom: 12, bearing: 0, viewingAngle: 0)
    }
    
//    func markerDrawing(){
//        let locValue:CLLocationCoordinate2D = locationManager.location!.coordinate
//        Googlemapview.animate(toLocation: CLLocationCoordinate2D(latitude: locValue.latitude, longitude: locValue.longitude))
//
//        Googlemapview.setMinZoom(4.6, maxZoom: 20)
//    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if (status == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
            
        }
    }
    
    
@IBAction func btnMoveToMarker(_ sender: UIButton) {
    
    Googlemapview.camera = GMSCameraPosition(target: marker.position, zoom: 12, bearing: 0, viewingAngle: 0)
}

    @IBAction func BackBtn(_ sender: UIButton)
    {
         self.navigationController? . popViewController(animated: true)
    }
    
    
    @IBAction func ReportissueBtn(_ sender: UIButton)
    {
        let ratingReviewVC = self.storyboard?.instantiateViewController(withIdentifier: "SelectIssueViewController") as! SelectIssueViewController
        self.navigationController?.pushViewController(ratingReviewVC, animated: true)
    }
    
    
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
