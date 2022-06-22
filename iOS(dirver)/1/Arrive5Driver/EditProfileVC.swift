//
//  EditProfileVC.swift
//  Arrive5Driver
//
//  Created by Joy on 12/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import SVProgressHUD
import CoreLocation
import MRCountryPicker

class EditProfileVC: UIViewController,UITextFieldDelegate,MRCountryPickerDelegate,UIImagePickerControllerDelegate, UINavigationControllerDelegate, UIPickerViewDelegate, UIPickerViewDataSource,CLLocationManagerDelegate {
    
    // MARK: - IBOutlets
    // MARK: -
    
    @IBOutlet weak var tfUserFirstName: UITextField!
    @IBOutlet weak var tfUserLastName: UITextField!
    @IBOutlet weak var tfUserEmailId: UITextField!
    @IBOutlet weak var tfUserPhone: UITextField!
    @IBOutlet weak var tfUserPassword: UITextField!
    @IBOutlet weak var tfUserCode: UITextField!
    @IBOutlet weak var tfGenderField: UITextField!
    @IBOutlet weak var tfCityToDrive: UITextField!
    @IBOutlet weak var btnClearText: UIButton!
    @IBOutlet weak var btnCheckBox: UIButton!
    @IBOutlet weak var btnSignUpAction: UIButton!
    @IBOutlet weak var ivUserImageChange: UIImageView!
    @IBOutlet weak var ivCountryFlag: UIImageView!
    @IBOutlet weak var lblPhoneCode: UILabel!
    @IBOutlet weak var pkGenderPicker: UIPickerView!
    @IBOutlet var vwGenderView: UIView!
    @IBOutlet var tfConfirmpassword: UITextField!
    @IBOutlet var vwCountryView: UIView!
    @IBOutlet weak var pkCountryPicker: MRCountryPicker!
    @IBOutlet weak var tfCountrySelect: UITextField!
    
    // MARK: - Properties
    // MARK: -
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    let picker = UIImagePickerController()
    let ImageAlert = UIAlertController()
    var ImageFilePath : String!
    var ImageDataWork : Data!
    var locationManager:CLLocationManager!
    var GenderType : Int!
    var arrGender : [Any] = ["Female","Male"]
    
    // MARK: - VCCycles
    // MARK: -
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tfCountrySelect.isUserInteractionEnabled = false
        self.tfGenderField.isUserInteractionEnabled = false
        self.btnCheckBox.backgroundColor = UIColor.white
        ImageAlert.title = "Choose one of the following:"
        ImageAlert.message = ""
        self.btnClearText.isHidden = true
        self.tfUserPassword.delegate = self
        picker.delegate = self
        picker.allowsEditing = true
        self.pkGenderPicker.delegate = self
        self.pkGenderPicker.dataSource = self
        self.tfUserPhone.text = appDelegate.tfPhoneNo
        self.lblPhoneCode.text = appDelegate.lblPhoneCode
        self.ivCountryFlag.image = appDelegate.ivFlagValue
        pkCountryPicker.countryPickerDelegate = self
        pkCountryPicker.showPhoneNumbers = true
        pkCountryPicker.setCountry("SI")
        pkCountryPicker.setCountryByName("India")
        locationManager = CLLocationManager()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        let authorizationStatus = CLLocationManager.authorizationStatus()
        if (authorizationStatus == CLAuthorizationStatus.notDetermined) {
            locationManager.requestWhenInUseAuthorization()
        } else if (authorizationStatus == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
            //            markerDrawing()
        }
        self.ImagePopUp()
        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        self.ivUserImageChange.layer.masksToBounds = true
        self.ivUserImageChange.layer.cornerRadius = self.ivUserImageChange.frame.height/2
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Button Action
    // MARK: -
    
    @IBAction func btnDoneCountry(_ sender: UIButton) {
        
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwCountryView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
    }
    
    @IBAction func btnCancelCountry(_ sender: UIButton) {
        
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwCountryView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
    }
    
    @IBAction func btnDoneGender(_ sender: UIButton) {
        
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwGenderView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
    }
    
    @IBAction func btnCancelGender(_ sender: UIButton) {
        
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwGenderView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
    }
    
    @IBAction func btnSelectGender(_ sender: UIButton) {
        
        self.tfUserPassword.resignFirstResponder()
        view.addSubview(vwGenderView)
        AlertViewData.animate(withDuration: 0.0, delay: 0.0, usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwGenderView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY+UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            AlertViewData.animate(withDuration: 0.5, delay: 0.4, usingSpringWithDamping: 1.0,
                                  initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwGenderView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY-UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)}, completion: { (finished: Bool) in
            })
        })
    }
    
    @IBAction func btnSelectCountry(_ sender: UIButton) {
        
        view.addSubview(vwCountryView)
        AlertViewData.animate(withDuration: 0.0, delay: 0.0, usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwCountryView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY+UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            AlertViewData.animate(withDuration: 0.5, delay: 0.4, usingSpringWithDamping: 1.0,
                                  initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwCountryView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY-UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)}, completion: { (finished: Bool) in
            })
        })
    }
    
    @IBAction func btnCameraSelection(_ sender: UIButton) {
        
        self.present(ImageAlert, animated: true, completion: {
            self.ImageAlert.view.superview?.isUserInteractionEnabled = true
            self.ImageAlert.view.superview?.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.alertClose(gesture:))))
        })
    }
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func btnSignUp(_ sender: UIButton) {
        
        if locationManager.location?.coordinate.latitude == nil{
            self.view.makeToast("Please allow the app to get your location")
        }else{
            if tfUserFirstName.text == ""
            {
                    self.view.makeToast("Enter User First Name")
            }else
            {
                if tfUserLastName.text == ""
                {
                    self.view.makeToast("Enter User Last Name")
                }else
                {
                    if tfUserEmailId.text == ""
                    {
                        self.view.makeToast("Enter User Email Id")
                    }else
                    {
                        if tfUserPhone.text == ""{
                            self.view.makeToast("Enter User Phone Number")
                        }else
                        {
                            if tfUserPassword.text == ""
                            {
                                self.view.makeToast("Enter User Password")
                            }else
                            {
                                if tfConfirmpassword.text == ""
                                {
                                    self.view.makeToast("Enter Confirm Password")
                                }
                                else
                                {
                                    if tfUserPassword.text != tfConfirmpassword.text
                                    {
                                        self.view.makeToast("Mismatch your Password")
                                    }
//                                    else
//                                    {
//                                    if tfUserCode.text == ""
//                                {
//                                    self.view.makeToast("Enter User Code")
//                                }
                                    else
                                {
                                    if tfGenderField.text == ""
                                    {
                                        self.view.makeToast("Enter User Gender")
                                    }else
                                    {
                                        if tfCityToDrive.text == ""
                                        {
                                            self.view.makeToast("Enter User City To Drive")
                                        }else
                                        {
                                                ProfileUpdationData("\(locationManager.location?.coordinate.latitude ?? 29.771111)", "\(locationManager.location?.coordinate.longitude ?? 79.8222)", "\(GenderType)")
                                                }
                                            }
                                        }
                                  //  }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    @IBAction func btnCheckBoxAction(_ sender: UIButton) {
        
//        if btnCheckBox.backgroundColor == UIColor.white{
//            btnCheckBox.backgroundColor = UIColor.clear
//            btnCheckBox.setImage(UIImage(named:"Group 1"), for: .normal)
//        }
//        else{
//            btnCheckBox.backgroundColor = UIColor.white
//            btnCheckBox.setImage(UIImage(named:"Joy"), for: .normal)
//        }
        
        
        if btnCheckBox.currentImage == UIImage(named: "checkbox-2")
        {
            btnCheckBox.setImage(UIImage(named: "icons8-checked-checkbox-32"),for: UIControl.State.normal)
            //            self.view.makeToast("Driver Online")
        }
        else
        {
            btnCheckBox.setImage(UIImage(named: "checkbox-2"), for: UIControl.State.normal)
            //            self.view.makeToast("Driver Online")
        }
        
        
    }
    
    // MARK: - Text Field Delegates
    // MARK: -
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if textField == tfUserPassword{
            self.btnClearText.isHidden = false
        }else{
            self.btnClearText.isHidden = true
        }
    }
    func textFieldDidEndEditing(_ textField: UITextField) {
        self.btnClearText.isHidden = true
    }
    
    //MARK: - LocationManager Delegate
    //MARK: -
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if (status == CLAuthorizationStatus.authorizedWhenInUse) {
            locationManager.startUpdatingLocation()
            
        }
    }
    
    //MARK: - Methods
    //MARK: -
    
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
    
    func ProfileUpdationData(_ latitudeVal : String, _ longitudeVal: String,_ GenderVal : String){
        
        let aStrApi = "\(Constant.API.kAddProfile)"
        let parameters = [
            "firstname" : self.tfUserFirstName.text!,
            "lastname" : self.tfUserLastName.text!,
            "emailid" : self.tfUserEmailId.text!,
            "mobileno" : "\(self.lblPhoneCode.text!)\(self.tfUserPhone.text!)",
            "password":self.tfUserPassword.text!,
            
            //            "profile_picture" : imageData,
            "token" : appDelegate.deviceTokenString ?? "93283232932098231902130843980jndsjn0208",
            "appplatform":"ios",
            "gender":GenderVal,
            "lat":latitudeVal,
            "lng":longitudeVal,
            "country":self.tfCountrySelect.text!,
            "city":self.tfCityToDrive.text!,
            "refcode":self.tfUserCode.text!
            
            
//            $refcode = $this->input->post('refcode');
//            $firstname = $this->input->post('firstname');
//            $lastname = $this->input->post('lastname');
//            $emailid = $this->input->post('emailid');
//            $mobile_no = $this->input->post('mobileno');
//            $password = $this->input->post('password');
//            $gender = $this->input->post('gender');
//            $city = $this->input->post('city');
//            $country = $this->input->post('country');
//            $lat = $this->input->post('lat');
//            $lng = $this->input->post('lng');
//            $token = $this->input->post('token');
//            $appPlatform = $this->input->post('appplatform');
            
            
        ]
        
        SVProgressHUD.show(withStatus: "Please Wait")
        
        Alamofire.upload(multipartFormData: { (multipartFormData) in
            
            for (key, value) in parameters {
                multipartFormData.append((value as AnyObject).data(using: String.Encoding.utf8.rawValue)!, withName: key)
                if self.ImageDataWork != nil{
                    
                    
                    multipartFormData.append(self.ImageDataWork, withName: "image", fileName: "image.jpg", mimeType: "jpg")
                    
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
                                let userDetail = resJson["details"].rawValue as! [String:Any]
                                self.appDelegate.userDetail = userDetail
                                let userId = userDetail["id"] as! String
                                let name = userDetail["first_name"] as? String
                                let aImgUrl = userDetail["image"] as? String
                                let aPhn = userDetail["mobile"] as? String
                                let aGender = userDetail["gender"] as? String
                                let lat = userDetail["latitude"] as? String
                                let long = userDetail["longitude"] as? String
                                let city = userDetail["city"] as? String
                                let country = userDetail["country"] as? String
                                let email = userDetail["email"] as? String
                                let last_name = userDetail["last_name"] as? String
//                                let isOnline = userDetail["is_online"] as? String
                                
                                let userDefaultId = UserDefaults.standard
                                if aGender == "0"{
                                    userDefaultId.set("Female", forKey: "gender")
                                }else{
                                    userDefaultId.set("Male", forKey: "gender")
                                }
                                self.getAddressFromLatLon(pdblLatitude: lat!, withLongitude: long!)
                                userDefaultId.set(last_name, forKey: "last_name")
                                userDefaultId.set(city, forKey: "cityVal")
                                userDefaultId.set(email, forKey: "emailId")
                                userDefaultId.set(userId, forKey: "user_id")
                                userDefaultId.set(aImgUrl, forKey: "img_url")
                                userDefaultId.set(aPhn, forKey: "mobile")
                                userDefaultId.set(country, forKey: "country")
                                userDefaultId.set(name, forKey: "name")
                                userDefaultId.set("0", forKey: "is_online")
                                userDefaultId.synchronize()
                                let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "VehicleInfoVC") as! VehicleInfoVC
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
    
    func getAddressFromLatLon(pdblLatitude: String, withLongitude pdblLongitude: String) {
        var center : CLLocationCoordinate2D = CLLocationCoordinate2D()
        let lat: Double = Double("\(pdblLatitude)")!
        //21.228124
        let lon: Double = Double("\(pdblLongitude)")!
        //72.833770
        let ceo: CLGeocoder = CLGeocoder()
        center.latitude = lat
        center.longitude = lon
        
        let loc: CLLocation = CLLocation(latitude:center.latitude, longitude: center.longitude)
        
        
        ceo.reverseGeocodeLocation(loc, completionHandler:
            {(placemarks, error) in
                if (error != nil)
                {
                    print("reverse geodcode fail: \(error!.localizedDescription)")
                }
                let pm = placemarks! as [CLPlacemark]
                
                if pm.count > 0 {
                    let pm = placemarks![0]
                    var addressString : String = ""
                    if pm.subLocality != nil {
                        addressString = addressString + pm.subLocality! + ", "
                    }
                    if pm.thoroughfare != nil {
                        addressString = addressString + pm.thoroughfare! + ", "
                    }
                    if pm.locality != nil {
                        addressString = addressString + pm.locality! + " "
                    }
//                    if pm.country != nil {
//                        addressString = addressString + pm.country! + ", "
//                    }
//                    if pm.postalCode != nil {
//                        addressString = addressString + pm.postalCode! + " "
//                    }
//                    
                    let userDefaultId = UserDefaults.standard
                    userDefaultId.set(addressString, forKey: "AddressVal")
                    userDefaultId.synchronize()
                    print(addressString)
                }
        })
        
    }
    
    // MARK: - CountryPicker
    // MARK: -
    
    func countryPhoneCodePicker(_ picker: MRCountryPicker, didSelectCountryWithName name: String, countryCode: String, phoneCode: String, flag: UIImage) {
        self.tfCountrySelect.text = name
    }
    
    // MARK: - Picker View Delegates
    // MARK: -
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return self.arrGender.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return self.arrGender[row] as? String
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        self.tfGenderField.text = self.arrGender[row] as? String
        self.GenderType = row
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
            ivUserImageChange.contentMode = .scaleAspectFill
            ivUserImageChange.image = pickedImage
            
        }
        ImageAlert.dismiss(animated: true, completion: nil)
        dismiss(animated: true, completion: nil)
    }
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        dismiss(animated: true, completion: nil)
        ImageAlert.dismiss(animated: true, completion: nil)
    }
    
}
