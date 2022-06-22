//
//  ScheduledPickScrTableViewCell.swift
//  Arrive5Driver
//
//  Created by Test on 09/10/1941 Saka.
//  Copyright © 1941 Apple Inc. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces
import SVProgressHUD
import CoreLocation
import CoreLocation
import GoogleMaps
import GooglePlaces

class ScheduledPickScrTableViewCell: UITableViewCell,CLLocationManagerDelegate {

    @IBOutlet var TimeLabel: UILabel!
    
    @IBOutlet var ToLabel: UILabel!
    
    @IBOutlet var FromLabel: UILabel!
    
    @IBOutlet var FareLabel: UILabel!
    
    @IBOutlet var GoogleMapss: GMSMapView!
    @IBOutlet var RemoveBtnObj: UIButton!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

    
    func moveCamera(marker:GMSMarker){
        GoogleMapss.camera = GMSCameraPosition(target: marker.position, zoom: 12, bearing: 0, viewingAngle: 0)
    }
    
    
    
//    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
//        if (status == CLAuthorizationStatus.authorizedWhenInUse) {
//            locationManager.startUpdatingLocation()
//            
//        }
//    }
//    
}
