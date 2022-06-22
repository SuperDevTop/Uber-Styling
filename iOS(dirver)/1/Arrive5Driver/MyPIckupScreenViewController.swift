//
//  MyPIckupScreenViewController.swift
//  Arrive5Driver
//
//  Created by Test on 09/10/1941 Saka.
//  Copyright © 1941 Apple Inc. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import SVProgressHUD
import CoreLocation
import GoogleMaps
import GooglePlaces
class MyPIckupScreenViewController: UIViewController,UITableViewDelegate, UITableViewDataSource,GMSMapViewDelegate,CLLocationManagerDelegate {

    @IBOutlet var Tableviewss: UITableView!
    var ArrVehicleModel : [Any] = []
    var driverId : String!
    var lat : String!
    var addbookinidd : String!
    
    var longss : String!
    var lat2 : String!
    var longss2 : String!
    var locationManager:CLLocationManager!
    var marker:GMSMarker = GMSMarker()
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
            //            self.placeMarkersInMap()
            
        }
        
    }
    
    
     override func viewWillAppear(_ animated: Bool)
     {
        self.Tableviewss.isHidden = true;
        SVProgressHUD.show()
        gettingmypickup()
        self.driverId = UserDefaults.standard.value(forKey: "user_id") as? String
    }

    func gettingmypickup(){
        
        let aStrApi = "\(Constant.API.Availablepickup)"
        let dictData : [String : AnyObject]!
        dictData = ["driver_id" : driverId, "type_pick_up" : "2" ] as [String : AnyObject]
        
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            
            
            if json["status"].rawString() == "true"
            {
                self.ArrVehicleModel = json["scheduled_pickups"].rawValue as! [Any]
                if self.ArrVehicleModel.count == 0
                {
                    SVProgressHUD.dismiss()
                    self.view.makeToast(json["msg"].rawString())
                    self.Tableviewss.isHidden = true;
                }
                else
                    
                {
                    SVProgressHUD.dismiss()
                   
                    self.Tableviewss.isHidden = false;
                    self.Tableviewss.reloadData()
                }
                print("Hello Swift")
                
            }
            else
            {
                print("Hello objective")
                
                self.view.makeToast(json["msg"].rawString())
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
    
    
    func removemypic(){
        
        let aStrApi = "\(Constant.API.removepicku)"
        let dictData : [String : AnyObject]!
        dictData = ["driver_id" : driverId, "booking_id" : addbookinidd ] as [String : AnyObject]
        
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            
            
                if json["status"].rawString() == "true"
                {
              
                    SVProgressHUD.dismiss()
                   
                    let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                    self.navigationController?.pushViewController(homeVc, animated: true)
                }
                else
                    
                {
                    SVProgressHUD.dismiss()
                    
                    self.Tableviewss.isHidden = false;
                    self.Tableviewss.reloadData()
                }
                print("Hello Swift")
                
        
    
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
    
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return ArrVehicleModel.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell = Tableviewss.dequeueReusableCell(withIdentifier: "MyPIckup") as! ScheduledPickScrTableViewCell
        
        let dict: [String : Any] = ArrVehicleModel[indexPath.row] as! [String : Any]
        
        cell.TimeLabel.text =  dict["schedule_time"] as? String
        cell.ToLabel.text = dict["start_point"] as? String
        cell.FromLabel.text = dict["end_point"] as? String
        cell.FareLabel.text = dict["amount"] as? String
        
        self.lat = dict["end_point_lat"] as? String
        self.longss = dict["end_point_long"] as? String
        
        cell.RemoveBtnObj.layer.cornerRadius = 7
        
        cell.RemoveBtnObj.tag = indexPath.row
        cell.RemoveBtnObj.addTarget(self, action: #selector(buttonSelected), for: .touchUpInside)
        
        cell.GoogleMapss.isMyLocationEnabled = false
        cell.GoogleMapss.settings.myLocationButton = false
        let endlat = Double(self.lat)
        let endlong = Double(self.longss)
        
        
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        cell.GoogleMapss.setMinZoom(4.6, maxZoom: 20)
        let camera = GMSCameraPosition.camera(withLatitude: endlat!, longitude: endlong!, zoom: 16.0)
        let mapView = GMSMapView.map(withFrame: cell.GoogleMapss.bounds, camera: camera)
        cell.GoogleMapss.animate(to: camera)
        mapView.isMyLocationEnabled = true
        
        
        marker.position = CLLocationCoordinate2D(latitude: endlat!, longitude: endlong!)
        marker.icon = UIImage(named: "icons8-region-48")
        marker.map = cell.GoogleMapss
        
        
        let locValue: CLLocationCoordinate2D = locationManager.location!.coordinate
        cell.GoogleMapss.animate(toLocation: CLLocationCoordinate2D(latitude: endlat!, longitude: endlong!))
        cell.GoogleMapss.setMinZoom(4.6, maxZoom: 20)
        marker = GMSMarker(position: locValue)
        marker.appearAnimation = GMSMarkerAnimation.pop
        marker.icon = UIImage(named: "pin")
        marker.infoWindowAnchor = CGPoint(x: 0.44, y: 0.45)
        marker.map = cell.GoogleMapss

        
        return cell
    }
    
    
    @objc func buttonSelected(sender: UIButton){
        print(sender.tag)
        
        SVProgressHUD.show()
        let dict: [String : Any] = ArrVehicleModel[sender.tag] as! [String : Any]
        
        self.addbookinidd =  dict["id"] as? String
       
        removemypic()
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    }
    

   
}
