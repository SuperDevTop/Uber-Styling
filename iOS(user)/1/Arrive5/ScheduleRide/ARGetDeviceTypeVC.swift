//
//  ARGetDeviceTypeVC.swift
//  Arrive5
//
//  Created by parangat2 on 6/12/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import SwiftyJSON
import Alamofire
import CoreLocation


protocol changeVehicleTypeDelegate {
    func changeVehicleTypeSelected(_ selectedId : String, selectedVehicleName : String)
//    func changeVehicleTypeSelectedName(_ selectedVehicleName : String)
}

class ARGetDeviceTypeVC: UIViewController , UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout,clcVehicleTypeCellDelegate{
    
    // MARK: - IBOutlets
    // MARK: -
    @IBOutlet weak var clcVehicleType: UICollectionView!
    
    // MARK: - Properties
    // MARK: -
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    var delegate :changeVehicleTypeDelegate?
    
    var pickLocationCordinate = CLLocationCoordinate2D()
    var dropLocationCordinate = CLLocationCoordinate2D()

    var PickUpAddress : String!
    var DropUpAddress : String!
    var arrDeviceType : [Any]=[]
    
    var vehicle_type_id : String = "1"
    var vehicle_sub_type_id : String = "1"
    var vehicle_sub_type_modelName : String = ""

    // MARK: - VCCycles
    // MARK: -
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        self.callApiData()

    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(true)
    }
    
    // MARK: - Methods
    // MARK: -
    
    
    func callApiData(){
//        let url = "\(Constant.API.kVehicleType)"
        let url  = "\(Constant.API.kVehicleDetails)"
        let dictData : [String : AnyObject]!
        dictData = ["start_point_lat" : pickLocationCordinate.latitude,
                    "start_point_long":pickLocationCordinate.longitude,
                    "end_point_lat":dropLocationCordinate.latitude,
                    "end_point_long":dropLocationCordinate.longitude] as [String : AnyObject]
        
        print(dictData)
        APIManager.requestPOSTURL(url, params: dictData, headers: nil, success: {(json) in
            print(json)
            
            if json["status"].rawString() == "true"{
                self.arrDeviceType = json["result"].rawValue as! [Any]
                
                let aDict:[String:Any] = self.arrDeviceType[0] as! [String : Any]
                
                if let arrVehicleType : [[String:Any]] = aDict["vehicleType"] as? [[String:Any]]
                {
                    let dict:[String:Any] = arrVehicleType[0]
                    
                    self.vehicle_sub_type_id = dict["id"] as! String
                    self.vehicle_sub_type_modelName = dict["vehicle_model"] as! String
                }
                self.clcVehicleType.reloadData()
            }else{
            }        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
    
    
    
    
    
//    func callApiData(){
//        let aStrApi = "\(Constant.API.kVehicleType)"
//        APIManager.requestGETURL(aStrApi, success: {(json) in
//            if json["status"].rawString() == "true"{
//                self.arrDeviceType = json["details"].rawValue as! [Any]
//                
//                let aDict:[String:Any] = self.arrDeviceType[0] as! [String : Any]
//                
//                if let arrVehicleType : [[String:Any]] = aDict["vehicleType"] as? [[String:Any]]
//                {
//                    let dict:[String:Any] = arrVehicleType[0]
//                    
//                    self.vehicle_sub_type_id = dict["id"] as! String
//                    self.vehicle_sub_type_modelName = dict["vehicle_model"] as! String
//                }
//                self.clcVehicleType.reloadData()
//            }else{
//            }
//        }, failure: {(error) in
//        })
//    }
    
    func clcVehicleTypeSelected(_ selectedId: String, selectedModelName: String) {
        vehicle_sub_type_id = selectedId
        vehicle_sub_type_modelName = selectedModelName
    }
    
    // MARK: - ButtonActions
    // MARK: -
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        
        delegate?.changeVehicleTypeSelected(vehicle_sub_type_id, selectedVehicleName: vehicle_sub_type_modelName)

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
    
    // MARK: - Collection View Delegate and DataSource
    // MARK: -
    
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        var visibleRect = CGRect()
        
        visibleRect.origin = clcVehicleType.contentOffset
        visibleRect.size = clcVehicleType.bounds.size
        
        let visiblePoint = CGPoint(x: visibleRect.midX, y: visibleRect.midY)
        
        guard let indexPath = clcVehicleType.indexPathForItem(at: visiblePoint) else { return }
        
        print(indexPath)
        
        let aDict:[String:Any] = self.arrDeviceType[indexPath.item] as! [String : Any]
        
        if let arrVehicleType : [[String:Any]] = aDict["vehicleType"] as? [[String:Any]]
        {
            let dict:[String:Any] = arrVehicleType[0]
            vehicle_sub_type_id = dict["id"] as! String
            vehicle_sub_type_modelName = dict["vehicle_model"] as! String
        }
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: UIScreen.main.bounds.width, height: 159.0)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsets(top: 0.0, left: 0.0, bottom: 0.0, right: 0.0)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return arrDeviceType.count
    }
    
    func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        let aDict:[String:Any] = self.arrDeviceType[indexPath.item] as! [String : Any]
        vehicle_type_id = (aDict["vehicleTypeId"] as? String)!
        print(indexPath.item)
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let clcVehicleTypeCell = collectionView.dequeueReusableCell(withReuseIdentifier: "clcVehicleTypeCell", for: indexPath) as! clcVehicleTypeCell
        let aDict:[String:Any] = self.arrDeviceType[indexPath.item] as! [String : Any]
        clcVehicleTypeCell.lblVehicleTypeName.text = aDict["vehicleTypeName"] as? String
        clcVehicleTypeCell.delegate = self
        clcVehicleTypeCell.arrDeviceType = aDict["vehicleType"] as! [Any]
        if clcVehicleTypeCell.arrSelection.count < 1{
            clcVehicleTypeCell.arrSelection.append("1")
            clcVehicleTypeCell.arrSelection.append("4")
            clcVehicleTypeCell.arrSelection.append("6")
        }
        clcVehicleTypeCell.clcVehicleInfo.reloadData()
        return clcVehicleTypeCell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print(indexPath.row)
    }
}
