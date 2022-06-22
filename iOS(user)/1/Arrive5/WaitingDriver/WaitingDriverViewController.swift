//
//  WaitingDriverViewController.swift
//  Arrive5
//
//  Created by Parangat Air 1 on 5/29/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces
import SDWebImage
import UIKit
import ImageIO


class WaitingDriverViewController: UIViewController,GMSMapViewDelegate {
    
    @IBOutlet weak var vwGoogleMap: GMSMapView!
    var appDelegate  = UIApplication.shared.delegate as! AppDelegate
    @IBOutlet weak var viewWaiting : UIView!
    @IBOutlet weak var viewAccept : UIView!
    var isFromNotification : Bool!
    
    @IBOutlet var gifImagess: UIImageView!
    @IBOutlet weak var lblDriverName : UILabel!
    @IBOutlet weak var imgDriver : UIImageView!
    @IBOutlet weak var lblRating : UILabel!
    @IBOutlet weak var lblCarName : UILabel!
    @IBOutlet weak var lblCarNumber : UILabel!
    @IBOutlet weak var lblOTP : UILabel!
    var cordinates = CLLocationCoordinate2D()
    var dictNotificationData : [String:Any] = [:]
    
    
    @IBOutlet var AstimateTimeLabel: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        let urlpath:String = Bundle.main.path(forResource: "car321", ofType: "gif")!
        let url   = URL(fileURLWithPath: urlpath)
        let data = try? Data(contentsOf: url)
        let image:UIImage = UIImage.sd_animatedGIF(with: data)
        gifImagess.image = image
        
        
        vwGoogleMap.isMyLocationEnabled = true
        vwGoogleMap.settings.myLocationButton = true
        markerDrawing()
        viewWaiting.layer.borderWidth = 1.0
        viewWaiting.layer.borderColor = UIColor.white.cgColor
        viewWaiting.layer.shadowColor = UIColor.lightGray.cgColor
        viewWaiting.layer.shadowRadius = 2.0
        viewWaiting.layer.shadowOpacity = 1.0
        viewWaiting.layer.shadowOffset = CGSize(width:0, height: 2)
        viewWaiting.layer.shadowPath = UIBezierPath(rect: viewWaiting.bounds).cgPath
        
        viewAccept.layer.borderWidth = 1.0
        viewAccept.layer.borderColor = UIColor.white.cgColor
        viewAccept.layer.shadowColor = UIColor.lightGray.cgColor
        viewAccept.layer.shadowRadius = 2.0
        viewAccept.layer.shadowOpacity = 1.0
        viewAccept.layer.shadowOffset = CGSize(width:0, height: 2)
        viewAccept.layer.shadowPath = UIBezierPath(rect: viewWaiting.bounds).cgPath
        
        imgDriver.layer.masksToBounds = true
        imgDriver.layer.cornerRadius = imgDriver.frame.height/2
        
        viewWaiting.isHidden = true
        viewAccept.isHidden = true
        
        if isFromNotification == true{
            isFromNotification = false
            viewAccept.isHidden = false
            viewWaiting.isHidden = true
       
        }else{
            viewAccept.isHidden = true
            viewWaiting.isHidden = false
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        appDelegate.WaitingDriver = true
        NotificationCenter.default.addObserver(self, selector: #selector(self.RefreshPage(notification:)), name: Notification.Name("RefreshPage"), object: nil)
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        appDelegate.WaitingDriver = false
    }
    
    @IBAction func btnBackPressed (_sender:Any){
        self.navigationController?.popViewController(animated: true)
    }

    @objc func RefreshPage(notification: Notification){
        dictNotificationData  = notification.object as! [String : Any]
        print("bbg1 \(dictNotificationData)")
        viewAccept.isHidden = false
        viewWaiting.isHidden = true
        
        let aImgUrl = dictNotificationData ["gcm.notification.driverImg"] as! String
        APIManager.requestImage(path: aImgUrl, completionHandler: {(image) in
            self.imgDriver.image = image
        })
        
        lblDriverName.text = dictNotificationData ["gcm.notification.driverName"] as? String
        lblCarName.text = dictNotificationData ["gcm.notification.carType"] as? String
        lblCarNumber.text = dictNotificationData ["gcm.notification.carNo"] as? String
        lblRating.text = dictNotificationData ["gcm.notification.rating"] as? String
        lblOTP.text = dictNotificationData ["gcm.notification.otp"] as? String
        
        let lat = Double(dictNotificationData["gcm.notification.driverLat"] as! String)
        let lon = Double(dictNotificationData["gcm.notification.driverLong"] as! String)
        
        let times =  dictNotificationData ["gcm.notification.duration"] as? String
        
        AstimateTimeLabel.text = "\("your Estimated time") \(" ") \(times!)"
        
        cordinates = CLLocationCoordinate2D(latitude: lat! , longitude: lon!)
        
        let marker = GMSMarker()
        marker.position = CLLocationCoordinate2D(latitude: lat!, longitude: lon!)
        marker.icon = UIImage(named: "car")
        marker.map = self.vwGoogleMap
        markerDrawing()
        self.PolylineDraw()
    }
    
    func PolylineDraw(){
        let origin = "\(appDelegate.cordinates.latitude),\(appDelegate.cordinates.longitude)"
        let destination = "\(cordinates.latitude),\(cordinates.longitude)"
        
           let url = "https://maps.googleapis.com/maps/api/directions/json?origin=\(origin)&destination=\(destination)&mode=driving&key=AIzaSyBNM-OE-KJPz_KEkY5uLjMbwMhLZ0uoBiM"
        
//        let url = "https://maps.googleapis.com/maps/api/directions/json?origin=\(origin)&destination=\(destination)&mode=driving&key=AIzaSyAc3ku-xOE3bfjaQtcdNBn4Sqqjfu5LsHg"
        
        
        //        https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=AIzaSyA_ErWhZguYEK3cQA_lb0pb8rA_dfJ0ZRM
        APIManager.requestGETURL(url, success: {(json) in
            print("bbg 2\(json)")
            
            let routes = json["routes"].arrayValue
            for route in routes
            {
                let routeOverviewPolyline = route["overview_polyline"].dictionary
                let points = routeOverviewPolyline?["points"]?.stringValue
                let path = GMSPath.init(fromEncodedPath: points!)
                
                    let polyline = GMSPolyline(path: path)
                    let red = UIColor.red
                    //let darkGrey = UIColor(hexString: "#f7acba")
                    polyline.strokeColor = red
                    polyline.strokeWidth = 2.0
                    polyline.map = self.vwGoogleMap
            }
        }, failure: {(error) in
            
        })
    }
    
    
    func markerDrawing(){
        
        vwGoogleMap.animate(toLocation: CLLocationCoordinate2D(latitude: (appDelegate.cordinates.latitude) , longitude: (appDelegate.cordinates.longitude)))
        
        vwGoogleMap.setMinZoom(4.6, maxZoom: 20)
        
        let camera = GMSCameraPosition.camera(withLatitude: (appDelegate.cordinates.latitude), longitude: (appDelegate.cordinates.longitude), zoom: 16.0)
        
        let mapView = GMSMapView.map(withFrame: self.vwGoogleMap.bounds, camera: camera)
        vwGoogleMap.animate(to: camera)
        mapView.isMyLocationEnabled = true
        
        let Current = GMSMarker()
        Current.position = CLLocationCoordinate2D(latitude: (appDelegate.cordinates.latitude) , longitude: (appDelegate.cordinates.longitude))
        Current.icon = UIImage(named: "pin")
        Current.map = vwGoogleMap
        
        let DropDown = GMSMarker()
        DropDown.position = CLLocationCoordinate2D(latitude: (cordinates.latitude) , longitude: (cordinates.longitude) )
        DropDown.icon = UIImage(named: "car")
        DropDown.map = vwGoogleMap
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @IBAction func btnPressed (_sender:Any){
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let vc = storyboard .instantiateViewController(withIdentifier: "EnRouteViewController") as! EnRouteViewController
        vc.dict = dictNotificationData
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
   
//    func markerDrawing(){
//        print(appDelegate.cordinates)
//        vwGoogleMap.animate(toLocation: CLLocationCoordinate2D(latitude: appDelegate.cordinates.latitude, longitude: appDelegate.cordinates.longitude))
//        vwGoogleMap.setMinZoom(4.6, maxZoom: 20)
//        let camera = GMSCameraPosition.camera(withLatitude: appDelegate.cordinates.latitude, longitude: appDelegate.cordinates.longitude, zoom: 16.0)
//        let mapView = GMSMapView.map(withFrame: self.vwGoogleMap.bounds, camera: camera)
//        vwGoogleMap.animate(to: camera)
//        mapView.isMyLocationEnabled = true
//    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
