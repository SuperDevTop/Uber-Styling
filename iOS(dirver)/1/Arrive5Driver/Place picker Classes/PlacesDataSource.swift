//
//  PlacesDataSource.swift
//  PlacePicker-iOS
//
//  Created by Piotr Bernad on 05/07/2019.
//  Copyright © 2019 Piotr Bernad. All rights reserved.
//

import Foundation
import CoreLocation
import GooglePlaces
import GoogleMaps

protocol PlacesDataSourceDelegate: class {
    func placePickerDidSelectPlace(place: GMSPlace)
    func autoCompleteControllerDidProvide(place: GMSPlace)
}

class PlacesDataSource: NSObject {
    weak var tableView: UITableView?
    weak var delegate: PlacesDataSourceDelegate?
    
    private let geocoder = GMSGeocoder()
    private let ggeocoder = Geocoder()
    private let renderer: PlacesListRenderer
    private var state = ListState.nothingSelected {
        didSet {
            self.tableView?.reloadData()
        }
    }
    
    private var sessionToken: GMSAutocompleteSessionToken!
    
    init(renderer: PlacesListRenderer) {
        self.renderer = renderer
        self.sessionToken = GMSAutocompleteSessionToken()
        super.init()
    }
    
    func fetchPlacesFor(coordinate: CLLocationCoordinate2D, bounds: GMSCoordinateBounds?) {
        self.state = .loading
        reverseGecodeLocation(coordinate: coordinate)
    }
    
    func fetchPlaceDetails(placeId: String) {
        self.state = .loading
        fetchDetailsFromGooglePlaces(placeId: placeId)
    }
    
    func didSelectListItemAt(index: Int) {
        fetchSublistOrSelectPlace(index: index)
    }
    
    private func fetchDetailsFromGooglePlaces(placeId: String) {
        GMSPlacesClient.shared().lookUpPlaceID(placeId) { [weak self] (place, error) in
            if let error = error {
                self?.state = .error(error: error)
                self?.tableView?.reloadData()
                return
            }
            
            if let place = place {
                self?.state = .singlePlace(place: place)
                self?.tableView?.reloadData()
            }
            
        }
    }
    
    private func reverseGecodeLocation(coordinate: CLLocationCoordinate2D) {
        
        ggeocoder.reverseGeocode(coordinate: coordinate) { [weak self] (response, error) in
            if let error = error {
                self?.state = .error(error: error)
                return
            }
            
            self?.state = ListState.adresses(objects: response?.results ?? [])
        }
    }
    
    private func fetchSublistOrSelectPlace(index: Int) {
        switch state {
        case .adresses(let objects):
            fetchDetailsFor(address: objects[index])
        case .singlePlace(let place):
            delegate?.placePickerDidSelectPlace(place: place)
        default:
            return
        }
    }
    
    private func fetchDetailsFor(address: ReverseGeocodeResult) {
        fetchPlaceDetails(placeId: address.placeId)
    }
 
    private func fetchDetailsFor(prediction: GMSAutocompletePrediction) {
        fetchDetailsFromGooglePlaces(placeId: prediction.placeID)
    }
}

extension PlacesDataSource: UITableViewDataSource {
    
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        switch state {
        case .adresses(let objects):
            return objects.count
        case .singlePlace, .loading, .error, .nothingSelected:
            return 1
        }
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        
        switch state {
        case .adresses(let objects):
            let address = objects[indexPath.row]
            return renderer.cellForRowAt(indexPath: indexPath, tableView: tableView, object: .address(address: address))
        case .error(let error):
            return renderer.cellForRowAt(indexPath: indexPath, tableView: tableView, object: .error(error: error))
        case .nothingSelected:
            return renderer.cellForRowAt(indexPath: indexPath, tableView: tableView, object: .nothingSelected)
        case .loading:
            return renderer.cellForRowAt(indexPath: indexPath, tableView: tableView, object: .loading)
        case .singlePlace(let place):
            return renderer.cellForRowAt(indexPath: indexPath, tableView: tableView, object: .place(place: place))
        }
    }
}

extension PlacesDataSource: GMSAutocompleteViewControllerDelegate {
    public func viewController(_ viewController: GMSAutocompleteViewController, didAutocompleteWith place: GMSPlace) {
        self.state = .singlePlace(place: place)
        
        viewController.dismiss(animated: true, completion: { [weak self] in
            self?.delegate?.autoCompleteControllerDidProvide(place: place)
        })
    }
    
    public func viewController(_ viewController: GMSAutocompleteViewController, didFailAutocompleteWithError error: Error) {
        viewController.dismiss(animated: true, completion: nil)
    }
    
    public func wasCancelled(_ viewController: GMSAutocompleteViewController) {
        viewController.dismiss(animated: true, completion: nil)
    }
}
