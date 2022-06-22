//
//  HomeVC.swift
//  Arrive5Driver
//
//  Created by Joy on 09/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces
import SVProgressHUD
import CoreLocation

class HomeVC: UIViewController,GMSMapViewDelegate,CLLocationManagerDelegate {
    
    @IBOutlet weak var vwNotificationVal: UIView!
    @IBOutlet weak var vwGoogleMap: GMSMapView!
    @IBOutlet weak var lblNotificationVal: UILabel!
    @IBOutlet weak var lblWaveZoneVal: UILabel!
    
    @IBOutlet var StartstopBtn: UIButton!
    @IBOutlet weak var btnToggleVal: UISwitch!
    @IBOutlet weak var btnToggle: UIButton!
    
    var statsuss: NSString?

    var timer: DispatchSourceTimer?
    var locationManager:CLLocationManager!
    var marker:GMSMarker = GMSMarker()
    override func viewDidLoad() {
        super.viewDidLoad()
        self.btnToggleVal.isHidden = true
        self.btnToggleVal.onTintColor = UIColor.green
        self.btnToggleVal.tintColor = UIColor.white
        self.btnToggleVal.frame.size.height = 22.0
        self.btnToggleVal.onImage = UIImage(named: "toggle_on")
        self.btnToggleVal.offImage = UIImage(named: "toggle_off")
       
        locationManager = CLLocationManager()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        let authorizationStatus = CLLocationManager.authorizationStatus()
        if (authorizationStatus == CLAuthorizationStatus.notDetermined) {
            locationManager.requestWhenInUseAuthorization()
        } else if (authorizationStatus == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
            self.placeMarkersInMap()
            self.startTimer()
        }
        
        vwGoogleMap.isMyLocationEnabled = false
        vwGoogleMap.settings.myLocationButton = false
        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        self.vwNotificationVal.layer.masksToBounds = true
        self.vwNotificationVal.layer.cornerRadius = self.vwNotificationVal.frame.height/2
        let is_Online = UserDefaults.standard.value(forKey: "is_online") as! String
        if is_Online == "0"{
            self.btnToggle.setImage(UIImage(named: "toggle_off"), for: UIControl.State.normal)
            StartstopBtn.setBackgroundImage(UIImage(named: "off"), for: UIControl.State.normal)

//             self.StartstopBtn.setImage(UIImage(named: "off"), for: .normal)
        }else{
            self.btnToggle.setImage(UIImage(named: "toggle_on"), for: UIControl.State.normal)
            
//             self.StartstopBtn.setImage(UIImage(named: "on"), for: .normal)
            
             StartstopBtn.setBackgroundImage(UIImage(named: "on"), for: UIControl.State.normal)
        }
        
        self.btnToggleVal.isHidden = true
        
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(true)
        stopTimer()
        SVProgressHUD.dismiss()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    
    func startTimer() {
        let queue = DispatchQueue(label: "DispatchQueue.main")  // you can also use `DispatchQueue.main`, if you want
        timer = DispatchSource.makeTimerSource(queue: queue)
        timer!.schedule(deadline: .now(), repeating: .seconds(20))
        timer!.setEventHandler { [weak self] in
            // do whatever you want here
            if (UserDefaults.standard.value(forKey: "user_id") != nil){
                self?.updateDriverPoint()
              
            }else{
            }
            
        }
        timer!.resume()
    }
    
    func stopTimer() {
        timer?.cancel()
        timer = nil
        //        timer.isValid = false
    }
    deinit {
        self.stopTimer()
    }
    
    func updateDriverPoint(){
        let usrId  = UserDefaults.standard.value(forKey: "user_id") as! String
        
        let aStrApi = "\(Constant.API.kUpdateDriverLat)"
        let aDictParam : [String:Any]!
        aDictParam = ["driver_id":usrId,
                      "lat":locationManager.location?.coordinate.latitude ?? 29.3344455,
                      "long":locationManager.location?.coordinate.longitude ?? 79.3344455]
        APIManager.requestPOSTURL(aStrApi, params: aDictParam as [String : AnyObject]?, headers: nil, success: {(usrDetail) in
            if usrDetail["status"].rawString() == "true"{
                
                self.btnToggleVal.isHidden = true
            }else{
                self.view.makeToast(usrDetail["message"].rawString())
            }
            print(usrDetail)
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error)
            
        })
    }
    
    func driverOnlineStatus(_ onlineStts : String){
        
        let usrId  = UserDefaults.standard.value(forKey: "user_id") as! String
        
        let aStrApi = "\(Constant.API.kUpdateOnlineStatus)"
        let aDictParam : [String:Any]!
        aDictParam = ["driver_id":usrId,
                      "is_online":onlineStts]
        APIManager.requestPOSTURL(aStrApi, params: aDictParam as [String : AnyObject]?, headers: nil, success: {(usrDetail) in
            if usrDetail["status"].rawString() == "true"{
                
                if onlineStts == "1"{
                    self.btnToggle.setImage(UIImage(named: "toggle_on"), for: UIControl.State.normal)
//                    self.StartstopBtn.setImage(UIImage(named: "on"), for: .normal)
                    self.StartstopBtn.setBackgroundImage(UIImage(named: "on"), for: UIControl.State.normal)
                    let userDefaultId = UserDefaults.standard
                    userDefaultId.set("1", forKey: "is_online")

                    
                    self.statsuss = "1"
                    
                }else{
                    self.btnToggle.setImage(UIImage(named: "toggle_off"), for: UIControl.State.normal)
//                     self.StartstopBtn.setImage(UIImage(named: "off"), for: .normal)
                    
                    self.StartstopBtn.setBackgroundImage(UIImage(named: "off"), for: UIControl.State.normal)
                    let userDefaultId = UserDefaults.standard
                    userDefaultId.set("0", forKey: "is_online")
                       self.statsuss = "0"
                    
                }
                self.view.makeToast(usrDetail["message"].rawString())
            }else{
                self.view.makeToast(usrDetail["message"].rawString())
            }
            print(usrDetail)
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error)
            
        })
        
    }
    
    func placeMarkersInMap(){
        
        
        let locValue: CLLocationCoordinate2D = locationManager.location!.coordinate
        vwGoogleMap.animate(toLocation: CLLocationCoordinate2D(latitude: locValue.latitude, longitude: locValue.longitude))
        vwGoogleMap.setMinZoom(4.6, maxZoom: 20)
        marker = GMSMarker(position: locValue)
        marker.appearAnimation = GMSMarkerAnimation.pop
        marker.icon = UIImage(named: "pin")
        marker.infoWindowAnchor = CGPoint(x: 0.44, y: 0.45)
        marker.map = vwGoogleMap
        moveCamera(marker: marker)
        
    }
    func moveCamera(marker:GMSMarker){
        vwGoogleMap.camera = GMSCameraPosition(target: marker.position, zoom: 12, bearing: 0, viewingAngle: 0)
    }
    
    func markerDrawing(){
        let locValue:CLLocationCoordinate2D = locationManager.location!.coordinate
        vwGoogleMap.animate(toLocation: CLLocationCoordinate2D(latitude: locValue.latitude, longitude: locValue.longitude))
        
        vwGoogleMap.setMinZoom(4.6, maxZoom: 20)
    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if (status == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
            
        }
    }
    
    @IBAction func btnNotificationAction(_ sender: UIButton) {
        
//        let aNotifVC = self.storyboard?.instantiateViewController(withIdentifier: "TimerVC") as! TimerVC
//
//        self.navigationController?.pushViewController(aNotifVC, animated: true)
        
        
    }
    
    @IBAction func StartSTopBtnActions(_ sender: UIButton)
    {
     
        let is_Online = UserDefaults.standard.value(forKey: "is_online") as! String
        if is_Online == "0"{
        

            self.driverOnlineStatus("1")
        }else{

            self.driverOnlineStatus("0")
        }
        
        
        
    }
    
    @IBAction func btnToggleAction(_ sender: UIButton) {
        
        if self.btnToggle.currentImage == UIImage(named: "toggle_off"){
            
            
            
            self.driverOnlineStatus("1")
        }else{
            
            self.driverOnlineStatus("0")
        }
        
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
    
    @IBAction func btnMoveToMarker(_ sender: UIButton) {
        
        vwGoogleMap.camera = GMSCameraPosition(target: marker.position, zoom: 12, bearing: 0, viewingAngle: 0)
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
