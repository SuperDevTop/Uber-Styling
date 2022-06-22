//
//  DriverRequirementsVC.swift
//  Arrive5Driver
//
//  Created by Joy on 17/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import Alamofire
import SVProgressHUD
import SwiftyJSON


class DriverRequirementsVC: UIViewController,UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    // MARK: - Outlets
    // MARK: -
    @IBOutlet weak var lblInsuranceName: UILabel!
    
    @IBOutlet weak var lblLicenceName: UILabel!
    @IBOutlet weak var lblAdhaarName: UILabel!
    @IBOutlet weak var lblVehicleName: UILabel!
    @IBOutlet weak var btnInsurance: UIButton!
    @IBOutlet weak var btnLicense: UIButton!
    @IBOutlet weak var btnAdhaar: UIButton!
    @IBOutlet weak var btnVehicle: UIButton!
    @IBOutlet weak var tfRegistrationPlate: UITextField!
    @IBOutlet weak var tfLicencePlate: UITextField!
    // MARK: - Properties
    // MARK: -
    
    let picker = UIImagePickerController()
    let ImageAlert = UIAlertController()
    var ImageFilePath : String!
    var ImageDataWork : Data!
    var ImageStringInsurance : String!
    var ImageDataInsurance : Data!
    var ImageDataLicence : Data!
    var ImageStringLicence : String!
    var ImageDataAdhar : Data!
    var ImageStringAdhar : String!
    var typeVal : String!
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    var driverId : String!
    
    // MARK: - VCCycles
    // MARK: -
    
    override func viewDidLoad() {
        super.viewDidLoad()
        picker.delegate = self
        picker.allowsEditing = true
        self.ImagePopUp()
        self.gettingUserData()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        getDriverInfo()
    }
    
    func getDriverInfo()  {

        if let licencePlate: String = UserDefaults.standard.value(forKey: "licence_plate") as? String
        {
            tfLicencePlate.text = licencePlate
        }
        
        if let registrationPlate: String = UserDefaults.standard.value(forKey: "vechile_reg") as? String
        {
            tfRegistrationPlate.text = registrationPlate
        }

        if let adharImg: String = UserDefaults.standard.value(forKey: "adhar_img") as? String
        {
            if adharImg == ""
            {
//                lblAdhaarName.text = ""
//                btnAdhaar.isHidden = true
            }
            else
            {
                lblAdhaarName.text = adharImg
                btnAdhaar.isHidden = false
            }
        }
        else
        {
//            lblAdhaarName.text = ""
//            btnAdhaar.isHidden = true
        }

        if let licenceImg: String = UserDefaults.standard.value(forKey: "licence_img") as? String
        {
            if licenceImg == ""
            {
//                lblLicenceName.text = ""
//                btnLicense.isHidden = true
            }
            else
            {
                lblLicenceName.text = licenceImg
                btnLicense.isHidden = false
            }
        }
        else
        {
//            lblLicenceName.text = ""
//            btnLicense.isHidden = true
        }

        if let insuaranceImg: String = UserDefaults.standard.value(forKey: "insuarance_img") as? String
        {
            if insuaranceImg == ""
            {
//                lblInsuranceName.text = ""
//                btnInsurance.isHidden = true
            }
            else
            {
                lblInsuranceName.text = insuaranceImg
                btnInsurance.isHidden = false
            }
        }
        else
        {
//            lblInsuranceName.text = ""
//            btnInsurance.isHidden = true
        }
        
        if let vechileImg: String = UserDefaults.standard.value(forKey: "vechile_img") as? String
        {
            if vechileImg == ""
            {
//                lblVehicleName.text = ""
//                btnVehicle.isHidden = true
            }
            else
            {
                lblVehicleName.text = vechileImg
                btnVehicle.isHidden = false
            }
        }
        else
        {
//            lblVehicleName.text = ""
//            btnVehicle.isHidden = true
        }
    }
    
    // MARK: - ButtonActions
    // MARK: -
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func btnSubmitAction(_ sender: UIButton) {
        
        self.ProfileUpdationData(self.driverId)
        
//        let UserRegistered  = UserDefaults.standard
//        UserRegistered.set("true", forKey: "UserRegistered")
//        UserRegistered.synchronize()
        
        
    }
    
    @IBAction func btnVehicleEdit(_ sender: UIButton) {
    }
    
    @IBAction func btnRegistrationNumberEdit(_ sender: UIButton) {
    }
    
    @IBAction func btnInsurancePicker(_ sender: UIButton) {
        typeVal = "Insurance"
        self.present(ImageAlert, animated: true, completion: {
            self.ImageAlert.view.superview?.isUserInteractionEnabled = true
            self.ImageAlert.view.superview?.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.alertClose(gesture:))))
        })
    }
    
    @IBAction func btnLicencePicker(_ sender: UIButton) {
        typeVal = "Licence"
        self.present(ImageAlert, animated: true, completion: {
            self.ImageAlert.view.superview?.isUserInteractionEnabled = true
            self.ImageAlert.view.superview?.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.alertClose(gesture:))))
        })
    }
    
    @IBAction func btnAdharPicker(_ sender: UIButton) {
        typeVal = "Adhar"
        self.present(ImageAlert, animated: true, completion: {
            self.ImageAlert.view.superview?.isUserInteractionEnabled = true
            self.ImageAlert.view.superview?.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.alertClose(gesture:))))
        })
    }
    
    @IBAction func btnVehiclePicker(_ sender: UIButton) {
        typeVal = "Vehicle"
        self.present(ImageAlert, animated: true, completion: {
            self.ImageAlert.view.superview?.isUserInteractionEnabled = true
            self.ImageAlert.view.superview?.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.alertClose(gesture:))))
        })
    }
    
    func ProfileUpdationData(_ driverId : String){
        
        let aStrApi = "\(Constant.API.kProfileUpdate)"
        //        let UserId : String = ""
        //firstname,lastname,emailid,mobileno,password,token,appplatform,gender(0,1),lat,lng,country,city,refcode,image
        let parameters = [
            "driverid" : driverId,
            "licence_plate":self.tfLicencePlate.text!,
            "vechile_reg":self.tfRegistrationPlate.text!
        ]
        
        SVProgressHUD.show(withStatus: "Please Wait")
        
        Alamofire.upload(multipartFormData: { (multipartFormData) in
            
            for (key, value) in parameters {
                multipartFormData.append((value as AnyObject).data(using: String.Encoding.utf8.rawValue)!, withName: key)
                if self.ImageDataWork != nil{
                    if self.ImageDataLicence != nil{
                        if self.ImageDataInsurance != nil{
                            if self.ImageDataAdhar != nil{
                                multipartFormData.append(self.ImageDataWork, withName: "vechile_img", fileName: "vechile_img.jpg", mimeType: "jpg")
                                multipartFormData.append(self.ImageDataLicence, withName: "licence_img", fileName: "licence_img.jpg", mimeType: "jpg")
                                multipartFormData.append(self.ImageDataInsurance, withName: "insuarance_img", fileName: "insuarance_img.jpg", mimeType: "jpg")
                                multipartFormData.append(self.ImageDataAdhar, withName: "adhar_img", fileName: "adhar_img.jpg", mimeType: "jpg")
                            }
                        }
                    }
                }
            }
            
        }, usingThreshold:UInt64.init(),
           to: aStrApi, //URL Here
            method: .post,
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
                            if resJson["status"].rawString() == "true"{
                                let userReg = UserDefaults.standard
                                userReg.set("UserRegistered", forKey: "UserRegistered")
                                userReg.synchronize()
                                
                                if let result = resJson["result"].rawValue as? [String : Any]
                                {
                                    UserDefaults.standard.set(result["adhar_img"] as! String, forKey: "adhar_img")
                                    UserDefaults.standard.set(result["licence_img"] as! String, forKey: "licence_img")
                                    UserDefaults.standard.set(result["insuarance_img"] as! String, forKey: "insuarance_img")
                                    UserDefaults.standard.set(result["vechile_img"] as! String, forKey: "vechile_img")
                                }

                                UserDefaults.standard.set(self.tfLicencePlate.text, forKey: "licence_plate")
                                UserDefaults.standard.set(self.tfRegistrationPlate.text, forKey: "vechile_reg")

                                UserDefaults.standard.synchronize()


                                
//                                UserDefaults.standard.set(self.tfDateOfBirth.text, forKey: "DriverInfo_DOB")
//                                UserDefaults.standard.set(self.tfLicenceCardNumber.text, forKey: "VehicleInfo_LicenceCardNumber")
//                                UserDefaults.standard.set(self.tfExpirationDate.text, forKey: "DriverInfo_ExpirationDate")

                                let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                                self.navigationController?.pushViewController(homeVc, animated: true)
                                
                            }else{
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
    
    func ImagePopUp(){
        
        
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
    
    func gettingUserData(){
        let userDetail = appDelegate.userDetail
        self.driverId = UserDefaults.standard.value(forKey: "user_id") as? String
    }
    
    // MARK: - Delegates
    // MARK: -
    
//    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
    
     func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
    
        if typeVal == "Licence"{
            if let pickedImage = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
                var paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)
                let documentsDirectory = paths[0] as NSString
                ImageStringLicence = documentsDirectory.appendingPathExtension("jpg")
               // ImageDataLicence = UIImageJPEGRepresentation(pickedImage, 0.2)
                ImageDataLicence =  pickedImage.jpegData(compressionQuality: 0.2)

                let dataURL = URL(fileURLWithPath: ImageStringLicence)
                self.lblLicenceName.text = documentsDirectory as String
                self.btnLicense.isHidden = false
                do{
                    try ImageDataLicence.write(to: dataURL, options: [.atomic])
                }catch{
                    
                    //process errors
                }
                
            }
        }else if typeVal == "Insurance"{
            if let pickedImage = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
                var paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)
                let documentsDirectory = paths[0] as NSString
                ImageStringInsurance = documentsDirectory.appendingPathExtension("jpg")
//                ImageDataInsurance = UIImageJPEGRepresentation(pickedImage, 0.2)
                ImageDataInsurance =  pickedImage.jpegData(compressionQuality: 0.2)

                let dataURL = URL(fileURLWithPath: ImageStringInsurance)
                self.lblInsuranceName.text = documentsDirectory as String
                self.btnInsurance.isHidden = false
                do{
                    try ImageDataInsurance.write(to: dataURL, options: [.atomic])
                }catch{
                    //process errors
                }
                
            }
        }else if typeVal == "Adhar"{

            
            if let pickedImage = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
                var paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)
                let documentsDirectory = paths[0] as NSString
                ImageStringAdhar = documentsDirectory.appendingPathExtension("jpg")
//                ImageDataAdhar = UIImageJPEGRepresentation(pickedImage, 0.2)
                ImageDataAdhar =  pickedImage.jpegData(compressionQuality: 0.2)

                let dataURL = URL(fileURLWithPath: ImageStringAdhar)
                self.lblAdhaarName.text = documentsDirectory as String
                self.btnAdhaar.isHidden = false
                do{
                    try ImageDataAdhar.write(to: dataURL, options: [.atomic])
                }catch{
                    //process errors
                }
                
            }
        }else if typeVal == "Vehicle"{
            if let pickedImage = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
                var paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)
                let documentsDirectory = paths[0] as NSString
                ImageFilePath = documentsDirectory.appendingPathExtension("jpg")
//                ImageDataWork = UIImageJPEGRepresentation(pickedImage, 0.2)
                  ImageDataWork =  pickedImage.jpegData(compressionQuality: 0.2)
                let dataURL = URL(fileURLWithPath: ImageFilePath)
                self.lblVehicleName.text = documentsDirectory as String
                self.btnVehicle.isHidden = false
                do{
                    try ImageDataWork.write(to: dataURL, options: [.atomic])
                }catch{
                    //process errors
                }
                
            }
        }
        
        ImageAlert.dismiss(animated: true, completion: nil)
        dismiss(animated: true, completion: nil)
    }
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        dismiss(animated: true, completion: nil)
        ImageAlert.dismiss(animated: true, completion: nil)
    }

}
