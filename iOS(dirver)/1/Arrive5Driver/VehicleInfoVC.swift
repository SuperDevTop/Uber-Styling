//
//  VehicleInfoVC.swift
//  Arrive5Driver
//
//  Created by Joy on 17/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import SVProgressHUD

class VehicleInfoVC: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource, UIImagePickerControllerDelegate, UINavigationControllerDelegate, UIPickerViewDelegate, UIPickerViewDataSource {
    
    @IBOutlet weak var imgFirstImageCross: UIImageView!
    @IBOutlet weak var imgFirstImage: UIImageView!
    @IBOutlet var imgOtherImagesFrst: UIImageView!
    @IBOutlet var imgOtherImagesSec: UIImageView!
    @IBOutlet var imgOtherImagesThrd: UIImageView!
    @IBOutlet var imgOtherImagesFrth: UIImageView!
    @IBOutlet var imgOtherImagesFfth: UIImageView!
    @IBOutlet var imgOtherImagesSixth: UIImageView!
    @IBOutlet weak var imgOtherCrossFrst: UIImageView!
    @IBOutlet weak var imgOtherCrossSec: UIImageView!
    @IBOutlet weak var imgOtherCrossThrd: UIImageView!
    @IBOutlet weak var imgOtherCrossFrth: UIImageView!
    @IBOutlet weak var imgOtherCrossFfth: UIImageView!
    @IBOutlet weak var imgOtherCrossSixth: UIImageView!
    @IBOutlet weak var clcVehicleImages: UICollectionView!
    @IBOutlet var vwPickerVal: UIView!
    @IBOutlet weak var pkPickerVal: UIPickerView!
    @IBOutlet weak var lblVehicleType: UILabel!
    @IBOutlet weak var lblyearType: UILabel!
    @IBOutlet weak var lblModeltype: UILabel!
    @IBOutlet weak var lblMakeType: UILabel!
    @IBOutlet weak var lblNoOfDoors: UILabel!
    @IBOutlet weak var lblNoOfSeatBelts: UILabel!
    @IBOutlet weak var lblVehicleClass: UILabel!
    @IBOutlet weak var lblColorVal: UILabel!
    @IBOutlet weak var btnBack: UIButton!
    
    var arrImages : [UIImage] = []
    var ArrData : [Any] = []
    var ArrVehicleType : [Any] = []
    var ArrVehicleColor : [Any] = []
    var ArrVehicleModel : [Any] = []
    var arrNoOfDoors : [Any] = []
    var arrSeats : [String] = ["1","2","3","4","5"]
    var arrDoors : [String] = ["2","3","4","5"]
    var arrYear : [String] = ["2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022"]
    var arrMake : [String] = ["2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018"]
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    let picker = UIImagePickerController()
    let ImageAlert = UIAlertController()
    var ImageFilePath : String!
    var ImageDataWork : Data!
    var typeval : String!
    var modalId : String = "1"
    var colorId : String = "1"
    var vehicleType : String = "1"
    var VehicleSubtype : String = "1"
    var driverId : String!
    var flagForVC: CShort = 0

    override func viewDidLoad() {
        super.viewDidLoad()
        
        if flagForVC == 1
        {
            btnBack.isHidden = false
        }
        else
        {
            btnBack.isHidden = true
        }
        
        self.gettingUserData()
        self.clcVehicleImages.delegate = self
        self.clcVehicleImages.dataSource = self
        picker.delegate = self
        picker.allowsEditing = true
        pkPickerVal.delegate = self
        pkPickerVal.dataSource = self
        self.ImagePopUp()
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getVehicleInfo()
    }
    
    func getVehicleInfo()  {
        
        if let vehicleClass: String = UserDefaults.standard.value(forKey: "VehicleInfo_VehicleClass") as? String
        {
            lblVehicleClass.text = vehicleClass
        }
        
        if let vehicleType: String = UserDefaults.standard.value(forKey: "VehicleInfo_VehicleType") as? String
        {
            lblVehicleType.text = vehicleType
        }

        if let vehicleYear: String = UserDefaults.standard.value(forKey: "VehicleInfo_VehicleYear") as? String
        {
            lblyearType.text = vehicleYear
        }
        
        if let vehicleModel: String = UserDefaults.standard.value(forKey: "VehicleInfo_VehicleModel") as? String
        {
            lblModeltype.text = vehicleModel
        }
        
        if let vehicleColour: String = UserDefaults.standard.value(forKey: "VehicleInfo_VehicleColour") as? String
        {
            lblColorVal.text = vehicleColour
        }
        
        if let vehicleManufactureYear: String = UserDefaults.standard.value(forKey: "VehicleInfo_VehicleManufactureYear") as? String
        {
            lblMakeType.text = vehicleManufactureYear
        }
        
        if let vehicleDoors: String = UserDefaults.standard.value(forKey: "VehicleInfo_VehicleDoors") as? String
        {
            lblNoOfDoors.text = vehicleDoors
        }
        
        if let vehicleSeatBelts: String = UserDefaults.standard.value(forKey: "VehicleInfo_VehicleSeatBelts") as? String
        {
            lblNoOfSeatBelts.text = vehicleSeatBelts
        }
        
        DispatchQueue.main.async {
            if  let json: Optional = self.getJSON("VehicleInfo_VehicleImages")
           {
            print(json?.arrayObject as? [String] as Any)
                
            if let imgArr: [String] = json?.arrayObject as? [String]
                {
                    self.arrImages.removeAll()
                    print(imgArr)
                    for dict in imgArr
                    {
                        let imageUrl = URL(string: dict)!
                        let imageData = try! Data(contentsOf: imageUrl)
                        let image = UIImage(data: imageData)
                        self.arrImages.append(image!)
                    }
                    // 3               arrImages = dict as! [UIImage]
                    self.clcVehicleImages.reloadData()
                }
            }
        }



//        if let imgArr: [UIImage] = UserDefaults.standard.value(forKey: "VehicleInfo_VehicleImages") as? [UIImage]
//        {
//            arrImages = imgArr
//            clcVehicleImages.reloadData()
//        }

    }
    
    
    

    func getJSON(_ key: String)->JSON {
        var p = ""
        if let buildNumber = UserDefaults.standard.value(forKey: key) as? String {
            p = buildNumber
        }else {
            p = ""
        }
        if  p != "" {
            if let json = p.data(using: String.Encoding.utf8, allowLossyConversion: false) {
                return try! JSON(data: json)
            } else {
                return JSON("nil")
            }
        } else {
            return JSON("nil")
        }
    }

    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func MainImgSelection(_ sender: UIButton) {
        
        self.present(ImageAlert, animated: true, completion: {
            self.ImageAlert.view.superview?.isUserInteractionEnabled = true
            self.ImageAlert.view.superview?.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.alertClose(gesture:))))
        })
    }
    
    @IBAction func firstImgSelection(_ sender: UIButton) {
    }
    
    @IBAction func secImgSelection(_ sender: UIButton) {
    }
    
    @IBAction func thrdImgSelection(_ sender: UIButton) {
    }
    
    @IBAction func fourthImgSelection(_ sender: UIButton) {
    }
    
    @IBAction func fifthImgSelection(_ sender: UIButton) {
    }
    
    @IBAction func sixthImgSelection(_ sender: UIButton) {
    }
    
    @IBAction func btnNextAction(_ sender: UIButton) {
        self.callingAPiData()
    }
    
    @IBAction func btnVehicleClassAction(_ sender: UIButton) {
        typeval = "VehicleClass"
        self.GetVehicletype()
    }
    
    @IBAction func btnVehicleTypeAction(_ sender: UIButton) {
        typeval = "VehicleType"
//        self.GetVehiclesubtype(vehicleType)
        
        self.GetVehiclesubtype()
    }
    
    @IBAction func btnYearAction(_ sender: UIButton) {
        typeval = "Year"
        self.addingView()
        self.pkPickerVal.reloadAllComponents()
    }
    
    @IBAction func btnModelAction(_ sender: UIButton) {
        typeval = "Model"
        self.GetModal()
    }
    
    @IBAction func btnColorAction(_ sender: UIButton) {
        typeval = "Color"
        self.GetColor()
    }
    
    @IBAction func btnMakeAction(_ sender: UIButton) {
        typeval = "Make"
        self.addingView()
        self.pkPickerVal.reloadAllComponents()
    }
    
    @IBAction func btnNumberOfDoorsAction(_ sender: UIButton) {
        typeval = "Doors"
        self.addingView()
        self.pkPickerVal.reloadAllComponents()
    }
    
    @IBAction func btnNumberOfSeatBeltsAction(_ sender: UIButton) {
        typeval = "Seats"
        self.addingView()
        self.pkPickerVal.reloadAllComponents()
    }
    
    @IBAction func btnCancelAction(_ sender: UIButton) {
        self.closingView()
    }
    
    
    //MARK: - Methods
    //MARK: -
    
    func GetColor(){
        let aStrApi = "\(Constant.API.kGetColor)"
        APIManager.requestGETURL(aStrApi, success: {(json) in
            
            if json["status"].rawString() == "true"{
                self.ArrVehicleColor = json["detail"].rawValue as! [Any]
                if self.ArrVehicleColor.count > 0
                {
                    self.addingView()
                    self.pkPickerVal.reloadAllComponents()
                }
            }else{
                
            }
            
        }, failure: {(error) in
            
        })
    }
    
    func gettingUserData(){
        let userDetail = appDelegate.userDetail
        self.driverId = UserDefaults.standard.value(forKey: "user_id") as? String
    }
    
    func GetModal(){
        
        let aStrApi = "\(Constant.API.kGetModal)"
        APIManager.requestGETURL(aStrApi, success: {(json) in
            
            if json["status"].rawString() == "true"{
                self.ArrVehicleModel = json["detail"].rawValue as! [Any]
                if self.ArrVehicleModel.count > 0
                {
                    self.addingView()
                    self.pkPickerVal.reloadAllComponents()
                }
            }else{
            }
        }, failure: {(error) in
        })
    }
    
    func GetVehicletype(){
        let aStrApi = "\(Constant.API.kGetVehicletype)"
        APIManager.requestGETURL(aStrApi, success: {(json) in
            
            if json["status"].rawString() == "true"{
                self.ArrData = json["detail"].rawValue as! [Any]
                if self.ArrData.count > 0
                {
                    self.addingView()
                    self.pkPickerVal.reloadAllComponents()
                }
            }else{
                
            }
            
        }, failure: {(error) in
            
        })
    }
    
//    func GetVehiclesubtype(_ typeid: String){
//        let aStrApi = "\(Constant.API.kGetVehiclesubtype)\(typeid)"
//
//        (print(aStrApi))
//
//
//
//        APIManager.requestGETURL(aStrApi, success: {(json) in
//
//            if json["status"].rawString() == "true"{
//                self.ArrVehicleType = json["detail"].rawValue as! [Any]
//                if self.ArrVehicleType.count > 0
//                {
//                    self.addingView()
//                    self.pkPickerVal.reloadAllComponents()
//                }
//            }else{
//
//            }
//
//        }, failure: {(error) in
//
//        })
//
//    }
    
    func GetVehiclesubtype(){
        
        let aStrApi = "\(Constant.API.kGetVehiclesubtype)"
        let dictData : [String : AnyObject]!
        dictData = ["typeid" : vehicleType] as [String : AnyObject]
        
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            
            
                if json["status"].rawString() == "true"
                {
              
                   self.ArrVehicleType = json["detail"].rawValue as! [Any]
                                    if self.ArrVehicleType.count > 0
                                    {
                                        self.addingView()
                                        self.pkPickerVal.reloadAllComponents()
                                    }
                else{
                    
                                }
                }
                else
                    
                {
                    SVProgressHUD.dismiss()
                
                }
                print("Hello Swift")
                
        
    
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
    
    
    func addingView(){
        view.addSubview(vwPickerVal)
        AlertViewData.animate(withDuration: 0.0, delay: 0.0, usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwPickerVal.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY+UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            AlertViewData.animate(withDuration: 0.5, delay: 0.4, usingSpringWithDamping: 1.0,
                                  initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwPickerVal.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY-UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)}, completion: { (finished: Bool) in
            })
        })
    }
    
    func closingView(){
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwPickerVal.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
    }
    
    func ImagePopUp(){
        ImageAlert.title = "Choose One From The Following"
        
        ImageAlert.addAction(UIAlertAction(title: "Photos", style: .default, handler: { action in
            self.picker.sourceType = .photoLibrary
            
            self.present(self.picker, animated: true, completion: nil)
            
            
        }))
        ImageAlert.addAction(UIAlertAction(title: "Camera", style: .destructive, handler: { action in
            self.picker.sourceType = .camera
            self.present(self.picker, animated: true, completion: nil)
            
        }))
        ImageAlert.dismiss(animated: true, completion: nil)
    }
    
    @objc func alertClose(gesture: UITapGestureRecognizer) {
        self.dismiss(animated: true, completion: nil)
    }
    
    func callingAPiData(){
//        ,,,,,,,,[]
        let aStrApi = "\(Constant.API.kUpdateDriverInfo)"
        let driverid = self.driverId
        let vechile_type = self.vehicleType
        let vechile_subtype = self.VehicleSubtype
        let modelid = self.modalId
        let colorid = self.colorId
        let noofdoor = self.lblNoOfDoors.text!
        let noofsbelt = self.lblNoOfSeatBelts.text!
        let parameters = [
            "driverid" : driverid!,
            "vechile_type" : vechile_type,
            "vechile_subtype" : vechile_subtype,
            "make":self.lblMakeType.text!,
            "year":self.lblyearType.text!,
            "modelid" : modelid,
            "colorid" : colorid,
            "noofdoor" : noofdoor,
            "noofsbelt":noofsbelt]
        
        Alamofire.upload(multipartFormData: { multipartFormData in
            for (key, value) in parameters {
                multipartFormData.append((value as AnyObject).data(using: String.Encoding.utf8.rawValue)!, withName: key)
                
                
            }
            if self.arrImages.count > 0{
                for (image) in self.arrImages {
                    
//                    if  let imageData = UIImageJPEGRepresentation(image,0.2 )
                       if  let imageData =   image.jpegData(compressionQuality: 0.2)
                    {
                        multipartFormData.append(imageData, withName: "image[]", fileName: "image.jpeg", mimeType: "image/jpeg")
                    }
                }
            }
            
            
        }, to: aStrApi,
           encodingCompletion: { (result) in
            
            switch result {
            case .success(let upload, _, _):
                print("the status code is :")
                
                upload.uploadProgress(closure: { (progress) in
                    
                    print(progress)
                    SVProgressHUD.showProgress(Float(progress.fractionCompleted))
                })
                
                upload.responseJSON { response in
                    SVProgressHUD.dismiss()
                    print("the response code is : \(String(describing: response.response?.statusCode))")
                    print(response)
                    if response.result.isSuccess {
                        
                        let resJson = JSON(response.result.value!)
                        if resJson["msg"].rawString() == "successfully saved"{
//                            self.view.makeToast(resJson["msg"].rawString())
                            
                            let result = resJson["result"].rawValue as! [String : Any]

//                            let result: [String : Any] = resJson["result"].rawString() as! [String : Any]
                            UserDefaults.standard.set(self.lblVehicleClass.text, forKey: "VehicleInfo_VehicleClass")
                            UserDefaults.standard.set(self.lblVehicleType.text, forKey: "VehicleInfo_VehicleType")
                            UserDefaults.standard.set(self.lblyearType.text, forKey: "VehicleInfo_VehicleYear")
                            UserDefaults.standard.set(self.lblModeltype.text, forKey: "VehicleInfo_VehicleModel")
                            UserDefaults.standard.set(self.lblColorVal.text, forKey: "VehicleInfo_VehicleColour")
                            UserDefaults.standard.set(self.lblMakeType.text, forKey: "VehicleInfo_VehicleManufactureYear")
                            UserDefaults.standard.set(self.lblNoOfDoors.text, forKey: "VehicleInfo_VehicleDoors")
                            UserDefaults.standard.set(self.lblNoOfSeatBelts.text, forKey: "VehicleInfo_VehicleSeatBelts")
                            if let imgArr : [String] = result["vehicle_img"] as? [String]
                            {
                                if imgArr.count > 0
                                {
//                                    UserDefaults.standard.set(imgArr, forKey: "VehicleInfo_VehicleImages")
                                    
                                    do {
                                        let jsonData = try JSONSerialization.data(withJSONObject: imgArr, options: .prettyPrinted)
                                        // here "jsonData" is the dictionary encoded in JSON data
                                        
                                        let json = NSString(data: jsonData as Data, encoding: String.Encoding.utf8.rawValue)
                                        if let json = json {
                                            print("JSON: \(json)")
                                            UserDefaults.standard.set(json, forKey: "VehicleInfo_VehicleImages")
                                            //                                self.saveJSON(json: json, key: "UpdateDriverInfo")
                                        }
                                        else {
                                        }
                                    } catch {
                                        print(error.localizedDescription)
                                    }
                                }
                            }

                            let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "DriverInfoVC") as! DriverInfoVC
                            self.navigationController?.pushViewController(homeVc, animated: true)
                        }else
                        {
                            self.view.makeToast(resJson["msg"].rawString())
                        }
                        
                        
                        SVProgressHUD.dismiss()
                    }
                    if response.result.isFailure {
                        let error : Error = response.result.error!
                        self.view.makeToast(error.localizedDescription)
                        SVProgressHUD.dismiss()
                    }
                    
                    
                }
                break
            case .failure(let encodingError):
                print("the error is  : \(encodingError.localizedDescription)")
                
                
                break
            }
        })
    }
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        
        return arrImages.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let clcVehicleImagesCell = collectionView.dequeueReusableCell(withReuseIdentifier: "clcVehicleImagesCell", for: indexPath) as! clcVehicleImagesCell
        
        
        clcVehicleImagesCell.ivOtherImages.image = self.arrImages[indexPath.item] 
        return clcVehicleImagesCell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        self.arrImages.remove(at: indexPath.row)
        self.clcVehicleImages.reloadData()
        print(indexPath.row)
    }
    
    // MARK: - Delegates
    // MARK: -
    
//    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
           func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
            
            if let pickedImage = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
            var paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)
            let documentsDirectory = paths[0] as NSString
            ImageFilePath = documentsDirectory.appendingPathExtension("jpg")
//            ImageDataWork = UIImageJPEGRepresentation(pickedImage, 0.2)
                ImageDataWork =  pickedImage.jpegData(compressionQuality: 0.2)

            let dataURL = URL(fileURLWithPath: ImageFilePath)
            
            do{
                try ImageDataWork.write(to: dataURL, options: [.atomic])
            }catch{
                //process errors
            }
            arrImages.append(pickedImage)
            self.clcVehicleImages.reloadData()
            
        }
        ImageAlert.dismiss(animated: true, completion: nil)
        dismiss(animated: true, completion: nil)
    }
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        dismiss(animated: true, completion: nil)
        ImageAlert.dismiss(animated: true, completion: nil)
    }
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if typeval == "VehicleClass"{
            return self.ArrData.count
        }else if typeval == "VehicleType"{
            return self.ArrVehicleType.count
        }else if typeval == "Year"{
            return self.arrYear.count
        }else if typeval == "Model"{
            return self.ArrVehicleModel.count
        }else if typeval == "Color"{
            return self.ArrVehicleColor.count
        }else if typeval == "Make"{
            return self.arrMake.count
        }else if typeval == "Doors"{
            return self.arrDoors.count
        }else{
            return self.arrSeats.count
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        if typeval == "VehicleClass"{
            let aDict = self.ArrData[row] as! [String:Any]
            let aClassString = aDict["vehicle_type"] as! String
            return aClassString
        }else if typeval == "VehicleType"{
            let aDict = self.ArrVehicleType[row] as! [String:Any]
            let aClassString = aDict["vehicle_model"] as! String
            return aClassString
            
        }else if typeval == "Year"{
            return self.arrYear[row] 
        }else if typeval == "Model"{
            let aDict = self.ArrVehicleModel[row] as! [String:Any]
            let aClassString = aDict["modelname"] as! String
            return aClassString
            
        }else if typeval == "Color"{
            let aDict = self.ArrVehicleColor[row] as! [String:Any]
            let aClassString = aDict["color_name"] as! String
            return aClassString
            
        }else if typeval == "Make"{
            return self.arrMake[row]
        }else if typeval == "Doors"{
            return self.arrDoors[row]
        }else{
            return self.arrSeats[row]
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if typeval == "VehicleClass"{
            let aDict = self.ArrData[row] as! [String:Any]
            let aClassString = aDict["vehicle_type"] as! String
            vehicleType = aDict["id"] as! String
            self.lblVehicleClass.text = aClassString
            print(aClassString)
        }else if typeval == "VehicleType"{
            let aDict = self.ArrVehicleType[row] as! [String:Any]
            let aClassString = aDict["vehicle_model"] as! String
            VehicleSubtype = aDict["id"] as! String
            self.lblVehicleType.text = aClassString
            print(aClassString)
        }else if typeval == "Year"{
            self.lblyearType.text = self.arrYear[row]
        }else if typeval == "Model"{
            let aDict = self.ArrVehicleModel[row] as! [String:Any]
            let aClassString = aDict["modelname"] as! String
            modalId = aDict["id"] as! String
            self.lblModeltype.text = aClassString
            print(aClassString)
            
        }else if typeval == "Color"{
            let aDict = self.ArrVehicleColor[row] as! [String:Any]
            let aClassString = aDict["color_name"] as! String
            colorId = aDict["id"] as! String
            self.lblColorVal.text = aClassString
            print(aClassString)
            
        }else if typeval == "Make"{
            self.lblMakeType.text = self.arrMake[row]
        }else if typeval == "Doors"{
            self.lblNoOfDoors.text = self.arrDoors[row]
        }else if typeval == "Seats"{
            self.lblNoOfSeatBelts.text = self.arrSeats[row]
            print(arrDoors[row])
        }
    }

}
