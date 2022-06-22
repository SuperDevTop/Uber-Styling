//
//  SelectIssueViewController.swift
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
class SelectIssueViewController: UIViewController,GMSMapViewDelegate,CLLocationManagerDelegate,UITableViewDelegate,UITableViewDataSource {
    @IBOutlet var moneylabel: UILabel!
    
    @IBOutlet var GooglesmapViewss: GMSMapView!
    @IBOutlet var ShadowView: UIView!
    @IBOutlet var FromAddressLabel: UILabel!
    @IBOutlet var ToAddressLabel: UILabel!
    @IBOutlet var countLabel: UILabel!
    @IBOutlet var nameLabel: UILabel!
    @IBOutlet var DateLabel: UILabel!
    var arrayPastBooking : [Any] = []
    var submitreasonarray : [Any] = []
    
    @IBOutlet var tableviewss: UITableView!
    var timess : String!
    var datess : String!
    var moness : String!
    var contss : String!
     var space1 : String!
     var space2 : String!
     var bookinidss : String!
     var reasonsidd : String!
     var driveidsssss : String!
    var startlat : String!
    var integerss : Int!
    var startlong : String!
    var endlatitud : String!
    var endlongsss : String!
    var Driver: [[String:Any]] = [[:]]
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
        
        self.tableviewss.isHidden = true;
        getpastbooking()
        showreasons()
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
        
        GooglesmapViewss.isMyLocationEnabled = false
        GooglesmapViewss.settings.myLocationButton = false
    }
    
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(true)
        SVProgressHUD.dismiss()
    }
    
    
    func markerDrawing(){
        //vwGoogleMap.clear()
        GooglesmapViewss.animate(toLocation: CLLocationCoordinate2D(latitude: currentLocation.coordinate.latitude, longitude: currentLocation.coordinate.longitude))
        
        GooglesmapViewss.setMinZoom(4.6, maxZoom: 20)
        let camera = GMSCameraPosition.camera(withLatitude: currentLocation.coordinate.latitude, longitude: currentLocation.coordinate.longitude, zoom: 16.0)
        let mapView = GMSMapView.map(withFrame: self.GooglesmapViewss.bounds, camera: camera)
        GooglesmapViewss.animate(to: camera)
        mapView.isMyLocationEnabled = true
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
            self.countLabel.text = dict["carNo."] as? String
            self.moness = dict["amount"] as? String
            let appendString22 = "$"  + self.space2 + self.moness
            self.moneylabel.text = appendString22;
            self.timess = dict["schedule_time"] as? String
            self.datess = dict["schedule_date"] as? String
            self.nameLabel.text = dict["vehicle_sub_type_name"] as? String
            
            let appendString2 = self.datess  + self.space1 +  self.timess
            self.DateLabel.text = appendString2;
            self.startlat =  dict["start_point_lat"] as? String
            self.startlong =  dict["start_point_long"] as? String
            self.endlatitud =  dict["end_point_lat"] as? String
            self.endlongsss =  dict["end_point_long"] as? String
            self.bookinidss =  dict["booking_id"] as? String
            self.driveidsssss =  dict["driver_id"] as? String
            self.ToAddressLabel.text =  dict["start_point"] as? String
            self.FromAddressLabel.text =  dict["end_point"] as? String
            self.placeMarkersInMap()
            SVProgressHUD.dismiss()
            
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }

    
    func submitreasonapi(){
        let aStrApi = "\(Constant.API.submitreason)"
        let user_id = UserDefaults.standard.string(forKey: "user_id")
        let type = "user"
        let image = ""
        let comment = ""
        
        let dictData : [String : AnyObject]!
        dictData = ["user_id" : user_id,
                    "type" : type,"image" : image,"comment" : comment,"booking_id" : bookinidss, "driver_id" : driveidsssss, "reason_id" : reasonsidd ] as [String : AnyObject]
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
          
             if json["status"].rawString() == "true"
             {
                self.view.makeToast(json["message"].rawString())
                let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                self.navigationController?.pushViewController(homeVc, animated: true)
                 SVProgressHUD.dismiss()
            }
            else
             {
                self.view.makeToast(json["message"].rawString())
                  SVProgressHUD.dismiss()
            }
            
          
            
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
    
    func showreasons(){
        let aStrApi = "\(Constant.API.reportreasonlist)"
      
    let dictData : [String : AnyObject]!
        dictData = ["type" : "user" ] as [String : AnyObject]
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            
            if json["status"].rawString() == "true"
            {
                self.submitreasonarray = json["result"].rawValue as! [Any]
                if self.submitreasonarray.count == 0
                {
                    self.tableviewss.isHidden = true;
                    SVProgressHUD.dismiss()
                }
                else
                    
                {
                    self.tableviewss.isHidden = false;
                    self.tableviewss.reloadData()
                     SVProgressHUD.dismiss()
                }
                
            }
            
            else
            {
                self.view.makeToast(json["message"].rawString())
                SVProgressHUD.dismiss()
            }
            
    
            
            
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
    
    
    
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return submitreasonarray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell = tableView.dequeueReusableCell(withIdentifier: "SelectIssueTableViewCell") as! SelectIssueTableViewCell
        let dict: [String : Any] = submitreasonarray[indexPath.row] as! [String : Any]
        cell.issueLabel.text = dict["reason"] as? String
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if (integerss == indexPath.row)
        {
            cell.checkboximage.image = UIImage (imageLiteralResourceName: "radio_button")
        }
        else
        {
             cell.checkboximage.image = UIImage (imageLiteralResourceName: "radio_button1")
        }
       
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath)
    {
        integerss = indexPath.row
        let dict: [String : Any] = submitreasonarray[indexPath.row] as! [String : Any]
        reasonsidd = dict["id"] as? String
        self.tableviewss.reloadData()
        
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat
    {
        return 28
    }
    
    
    func placeMarkersInMap(){
        
        for index in 0...1
        {
            if (index == 0)
            {
               
                let endlat = Double(self.endlatitud)
                let endlong = Double(self.endlongsss)
                
                marker.position = CLLocationCoordinate2D(latitude: endlat!, longitude: endlong!)
                marker.icon = UIImage(named: "car")
                marker.map = self.GooglesmapViewss
            }
            else if (index == 1)
            {
              
                let startlat = Double(self.startlat)
                let startlong = Double(self.startlong)
                
                let locValue: CLLocationCoordinate2D = locationManager.location!.coordinate
                GooglesmapViewss.animate(toLocation: CLLocationCoordinate2D(latitude: startlat!, longitude: startlong!))
                GooglesmapViewss.setMinZoom(4.6, maxZoom: 20)
                marker = GMSMarker(position: locValue)
                marker.appearAnimation = GMSMarkerAnimation.pop
                marker.icon = UIImage(named: "pin")
                marker.infoWindowAnchor = CGPoint(x: 0.44, y: 0.45)
                marker.map = GooglesmapViewss
                moveCamera(marker: marker)
            }
        }
        
        
        
    }
    func moveCamera(marker:GMSMarker){
        GooglesmapViewss.camera = GMSCameraPosition(target: marker.position, zoom: 12, bearing: 0, viewingAngle: 0)
    }
    
  
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if (status == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
            
        }
    }
    
    
    @IBAction func btnMoveToMarker(_ sender: UIButton) {
        
        GooglesmapViewss.camera = GMSCameraPosition(target: marker.position, zoom: 12, bearing: 0, viewingAngle: 0)
    }
    
    
    
    
    @IBAction func BackBtn(_ sender: UIButton)
    {
         self.navigationController? . popViewController(animated: true)
    }
    
    @IBAction func SubmitBtnAction(_ sender: UIButton)
    {
        submitreasonapi()
        SVProgressHUD.show()
    }
   
}
