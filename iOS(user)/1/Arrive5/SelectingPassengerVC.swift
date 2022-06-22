//
//  SelectingPassengerVC.swift
//  Arrive5
//
//  Created by Joy on 17/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps

protocol selectingPassengerDelegate {
    func selectingPassengerSelected(_ selectedId : String)
}

class SelectingPassengerVC: UIViewController ,CLLocationManagerDelegate{

    @IBOutlet weak var lblPassengerThird: UILabel!
    @IBOutlet weak var lblPassenger: UILabel!
    
    var locationManager:CLLocationManager!
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    var delegate :selectingPassengerDelegate?
    
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
        }
        
        if appDelegate.PassengerNumber == "1"{
            self.lblPassenger.textColor = UIColor(hexString: "#2abbe7")
            self.lblPassengerThird.textColor = UIColor.black
        }
        else{
            self.lblPassenger.textColor = UIColor.black
            self.lblPassengerThird.textColor = UIColor(hexString: "#2abbe7")
        }
//        vwGoogleMapView.isMyLocationEnabled = true
//        vwGoogleMapView.settings.myLocationButton = true

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        UIView.animate(withDuration: 0.3, animations: {
            self.view.frame = CGRect(x: 0,
                                     y: self.view.frame.size.height,
                                     width: self.view.frame.size.width,
                                     height: self.view.frame.size.height)
        }) { (bool) in
            self.willMove(toParent: nil)
            
            self.view.reloadInputViews()
            self.view.removeFromSuperview()
            self.removeFromParent()
        }
    }
    
    @IBAction func btnSinglePassengerSelection(_ sender: UIButton) {
        delegate?.selectingPassengerSelected("1")
        appDelegate.PassengerNumber = "1"
        lblPassenger.textColor = UIColor.red
        UIView.animate(withDuration: 0.3, animations: {
            self.view.frame = CGRect(x: 0,
                                     y: self.view.frame.size.height,
                                     width: self.view.frame.size.width,
                                     height: self.view.frame.size.height)
        }) { (bool) in
            self.willMove(toParent: nil)
            
            self.view.reloadInputViews()
            self.view.removeFromSuperview()
            self.removeFromParent()
        }
    }

    @IBAction func btnMultiPassengerSelection(_ sender: UIButton) {
        delegate?.selectingPassengerSelected("1-3")
        appDelegate.PassengerNumber = "1-3"
        lblPassengerThird.textColor = UIColor.red
        UIView.animate(withDuration: 0.3, animations: {
            self.view.frame = CGRect(x: 0,
                                     y: self.view.frame.size.height,
                                     width: self.view.frame.size.width,
                                     height: self.view.frame.size.height)
        }) { (bool) in
            self.willMove(toParent: nil)
            
            self.view.reloadInputViews()
            self.view.removeFromSuperview()
            self.removeFromParent()
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if (status == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
        }
    }
}
