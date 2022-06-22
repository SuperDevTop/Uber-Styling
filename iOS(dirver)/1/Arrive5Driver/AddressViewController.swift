//
//  AddressViewController.swift
//  Arrive5Driver
//
//  Created by Admin on 07/08/21.
//  Copyright © 2021 Apple Inc. All rights reserved.
//

import UIKit
import CoreLocation

class AddressViewController: UIViewController,CLLocationManager,CLLocationManagerDelegate {

    
    var geoCoder :CLGeocoder!
    var locationManager = CLLocationManager()
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        locationManager.delegate = self
               locationManager.requestWhenInUseAuthorization()
               locationManager.desiredAccuracy = kCLLocationAccuracyBest
               locationManager.startUpdatingLocation()
        
     
        
   // pinImage.image (UIImage (imageLiteralResourceName: T##String))
          geoCoder = CLGeocoder()
        
        
//           PlacePicker.configure(googleMapsAPIKey: "AIzaSyBbaouxPAhiTnGUNce9wO0yP6OKYt7_dyk", placesAPIKey: "AIzaSyBbaouxPAhiTnGUNce9wO0yP6OKYt7_dyk")
        
        // Do any additional setup after loading the view.
    }
    
    func getLocationFromPostalCode(postalCode : String){
            let geocoder = CLGeocoder()
            
            geocoder.geocodeAddressString(postalCode) {
                (placemarks, error) -> Void in
                // Placemarks is an optional array of type CLPlacemarks, first item in array is best guess of Address
                
                if let placemark = placemarks?[0] {
                    
                    if placemark.postalCode == postalCode{
                     // you can get all the details of place here
                        print("\(String(describing: placemark.locality))")
                        print("\(String(describing: placemark.country))")
                    }
                    else{
                       print("Please enter valid zipcode")
                    }
                }
            }
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
