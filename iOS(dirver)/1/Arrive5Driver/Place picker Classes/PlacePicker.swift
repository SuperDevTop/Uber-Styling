//
//  PlacePicker.swift
//  PlacePicker-iOS
//
//  Created by Piotr Bernad on 04/07/2019.
//  Copyright © 2019 Piotr Bernad. All rights reserved.
//

import Foundation
import GoogleMaps
import GooglePlaces

public class PlacePicker {
    static var googleMapsKey: String?
    
    public static func configure(googleMapsAPIKey: String, placesAPIKey: String) {
        GMSServices.provideAPIKey("AIzaSyBbaouxPAhiTnGUNce9wO0yP6OKYt7_dyk")
        GMSPlacesClient.provideAPIKey("AIzaSyBbaouxPAhiTnGUNce9wO0yP6OKYt7_dyk")
        googleMapsKey = googleMapsAPIKey
    }
    
    public static func placePickerController(config: PlacePickerConfig = PlacePickerConfig.default) -> PlacePickerController {
        return PlacePickerController.controler(config: config)
    }
}
