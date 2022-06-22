//
//  clcVehicleTypeCell.swift
//  Arrive5
//
//  Created by Joy on 17/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

protocol clcVehicleTypeCellDelegate {
    func clcVehicleTypeSelected(_ selectedId : String, selectedModelName: String)
}

class clcVehicleTypeCell: UICollectionViewCell, UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout{
    
    @IBOutlet weak var lblVehicleTypeName: UILabel!
    
    @IBOutlet weak var clcVehicleInfo: UICollectionView!
    var arrDeviceType : [Any] = []
    var arrSelection : [String] = []
    var delegate : clcVehicleTypeCellDelegate?
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsets(top: 0.0, left: 20.0, bottom: 0.0, right: 0.0)
    }
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: 100.0, height: 114.0)
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return arrDeviceType.count
    }
//
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let clcVehicleSubtype = collectionView.dequeueReusableCell(withReuseIdentifier: "clcVehicleSubtype", for: indexPath) as! clcVehicleSubtype
        let aDict:[String:Any] = self.arrDeviceType[indexPath.item] as! [String:Any]
        print(aDict)
        
        
        clcVehicleSubtype.lblVehicleName.text = aDict["vehicle_model"] as? String
        clcVehicleSubtype.lblVehicleCharge.text = "$" + "\(aDict["fare_amt"] as! Int)"
        clcVehicleSubtype.lblVehicleTime.text = aDict["drivertaketimetoreach"] as? String
        
        
        let normalImgPath = aDict["nonselectimg"] as? String
        let selectedImgPath = aDict["selectimg"] as? String
        print(indexPath.row)
//        if self.arrSelection.count < 1{
//            self.arrSelection.append("1")
//        }else{
//        }
        let selectedId = aDict["id"] as! String
        if self.arrSelection.contains(selectedId){
            APIManager.requestImage(path: selectedImgPath!, completionHandler: { (typeImage) in
                clcVehicleSubtype.ivVehicleImage.image = typeImage
            })
        }else{
            APIManager.requestImage(path: normalImgPath!, completionHandler: { (typeImage) in
                clcVehicleSubtype.ivVehicleImage.image = typeImage
            })
//            self.arrSelection.removeAll()
        }
        print(aDict)
        return clcVehicleSubtype
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let aDict:[String:Any] = self.arrDeviceType[indexPath.item] as! [String:Any]
        let selectedId = aDict["id"] as! String
        let selectedModelName = aDict["vehicle_model"] as! String

        if self.arrSelection.contains(selectedId){
            self.arrSelection.removeAll()
        }
        else{
            self.arrSelection.removeAll()
            self.arrSelection.append(selectedId)
        }
        
        delegate?.clcVehicleTypeSelected(selectedId, selectedModelName: selectedModelName)
        self.clcVehicleInfo.reloadData()
    }
}
