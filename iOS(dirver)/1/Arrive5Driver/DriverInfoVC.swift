//
//  DriverInfoVC.swift
//  Arrive5Driver
//
//  Created by Joy on 17/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import MRCountryPicker


class DriverInfoVC: UIViewController, MRCountryPickerDelegate {
    
    // MARK: - Outlet
    // MARK: -
    
    @IBOutlet weak var tfFirstName: UITextField!
    @IBOutlet weak var tfMiddleName: UITextField!
    @IBOutlet weak var tfLastName: UITextField!
    @IBOutlet weak var tfSelectLocation: UITextField!
    @IBOutlet weak var tfSocialSecurity: UITextField!
    @IBOutlet weak var tfDateOfBirth: UITextField!
    @IBOutlet weak var tfLicenceCardNumber: UITextField!
    @IBOutlet weak var tfExpirationDate: UITextField!
    @IBOutlet weak var tfFirstAddress: UITextField!
    @IBOutlet weak var tfSecondAddress: UITextField!
    @IBOutlet weak var tfCityVal: UITextField!
    @IBOutlet weak var tfStateVal: UITextField!
    @IBOutlet weak var tfZipcodeVal: UITextField!
    @IBOutlet weak var btnFirstNameEdit: UIButton!
    @IBOutlet weak var btnMiddleNameEdit: UIButton!
    @IBOutlet weak var btnLastNameEdit: UIButton!
    @IBOutlet weak var btnSecurityNumberEdit: UIButton!
    @IBOutlet weak var btnLicenceNumberEdit: UIButton!
    @IBOutlet weak var btnFirstAddressEdit: UIButton!
    @IBOutlet weak var btnSecondAddressEdit: UIButton!
    @IBOutlet weak var btnCityEdit: UIButton!
    @IBOutlet weak var btnStateEdit: UIButton!
    @IBOutlet weak var btnZipcodeEdit: UIButton!
    @IBOutlet var vwBirthdate: UIView!
    @IBOutlet var vwExpireDate: UIView!
    @IBOutlet weak var dpBirthPicker: UIDatePicker!
    @IBOutlet weak var dpExpirePicker: UIDatePicker!
    @IBOutlet weak var btnCheckBox: UIButton!
    @IBOutlet var accessoryViewCountry: UIView!
    @IBOutlet weak var pickerViewCountry: MRCountryPicker!
    
    @IBOutlet var Dateofbirthbtn: UIButton!
    
    
    
    // MARK: - Properties
    // MARK: -
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    var driverId : String!
    // MARK: - VCCycles
    // MARK: -

    override func viewDidLoad() {
        super.viewDidLoad()
        self.gettingUserData()
        // Do any additional setup after loading the view.
        tfMiddleName.text = "";
        pickerViewCountry.countryPickerDelegate = self
        pickerViewCountry.showPhoneNumbers = true
        pickerViewCountry.setCountry("SI")
        pickerViewCountry.setCountryByName("India")
        
        
//        tfFirstName.layer.borderWidth = 1;
//        tfFirstName.layer.borderColor = UIColor.red.cgColor;
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getDriverInfo()
    }
    
    func getDriverInfo()  {
        
        if let driverFirstName: String = UserDefaults.standard.value(forKey: "name") as? String
        {
            tfFirstName.text = driverFirstName
        }
        
        if let middleName: String = UserDefaults.standard.value(forKey: "DriverInfo_MiddleName") as? String
        {
            tfMiddleName.text = middleName
        }
        
        if let lastName: String = UserDefaults.standard.value(forKey: "last_name") as? String
        {
            tfLastName.text = lastName
        }
        
        if let selectLocation: String = UserDefaults.standard.value(forKey: "country") as? String
        {
            tfSelectLocation.text = selectLocation
        }
        
//        UserDefaults.standard.set(self.tfSelectLocation.text, forKey: "DriverInfo_SelectLocation")

        if let socialSecurityNumber: String = UserDefaults.standard.value(forKey: "DriverInfo_SocialSecurityNumber") as? String
        {
            tfSocialSecurity.text = socialSecurityNumber
        }
        
        if let DOB: String = UserDefaults.standard.value(forKey: "DriverInfo_DOB") as? String
        {
            tfDateOfBirth.text = DOB
        }
        
        if let licenceCardNumber: String = UserDefaults.standard.value(forKey: "VehicleInfo_LicenceCardNumber") as? String
        {
            tfLicenceCardNumber.text = licenceCardNumber
        }
        
        if let expirationDate: String = UserDefaults.standard.value(forKey: "DriverInfo_ExpirationDate") as? String
        {
            tfExpirationDate.text = expirationDate
        }
        
        if let buttonCheckBoxStatus: String = UserDefaults.standard.value(forKey: "DriverInfo_ButtonCheckBoxStatus") as? String
        {
            if buttonCheckBoxStatus == "Selected"
            {
                btnCheckBox.isSelected = true
                btnCheckBox.setImage(UIImage(named: "checkedbox"), for: UIControl.State.normal)
            }
            else
            {
                btnCheckBox.isSelected = false
                btnCheckBox.setImage(UIImage(named: "checkbox"), for: UIControl.State.normal)
            }
        }

        if let addressOne: String = UserDefaults.standard.value(forKey: "DriverInfo_AddressOne") as? String
        {
            tfFirstAddress.text = addressOne
        }
        
        if let addressTwo: String = UserDefaults.standard.value(forKey: "DriverInfo_AddressTwo") as? String
        {
            tfSecondAddress.text = addressTwo
        }

        if let driverCity: String = UserDefaults.standard.value(forKey: "cityVal") as? String
        {
            tfCityVal.text = driverCity
        }
        if let driverState: String = UserDefaults.standard.value(forKey: "DriverInfo_State") as? String
        {
            tfStateVal.text = driverState
        }
        if let zipcode: String = UserDefaults.standard.value(forKey: "DriverInfo_ZipcodeVal") as? String
        {
            tfZipcodeVal.text = zipcode
        }

    }
    
    
    // MARK: - CountryPicker
    // MARK: -
    
    func countryPhoneCodePicker(_ picker: MRCountryPicker, didSelectCountryWithName name: String, countryCode: String, phoneCode: String, flag: UIImage) {
        tfSelectLocation.text = name
    }

    // MARK: - Button Actions
    // MARK: -
    
    @IBAction func btnDateOfBirth(_ sender: UIButton) {
        
        view.addSubview(vwBirthdate)
        self.vwBirthdate.isHidden = false
        self.vwExpireDate.isHidden = true
        AlertViewData.animate(withDuration: 0.0, delay: 0.0, usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwBirthdate.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY+UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            AlertViewData.animate(withDuration: 0.5, delay: 0.4, usingSpringWithDamping: 1.0,
                                  initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwBirthdate.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY-UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)}, completion: { (finished: Bool) in
            })
        })
    }
    
    @IBAction func expirationAction(_ sender: UIButton) {
        
        view.addSubview(vwExpireDate)
        self.vwBirthdate.isHidden = true
        self.vwExpireDate.isHidden = false
        AlertViewData.animate(withDuration: 0.0, delay: 0.0, usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwExpireDate.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY+UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            AlertViewData.animate(withDuration: 0.5, delay: 0.4, usingSpringWithDamping: 1.0,
                                  initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwExpireDate.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY-UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)}, completion: { (finished: Bool) in
            })
        })
    }
    
    @IBAction func btnDoneBirthAction(_ sender: UIButton) {
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwBirthdate.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            self.vwBirthdate.isHidden = true
            self.vwExpireDate.isHidden = true
        })
        
        dpBirthPicker.datePickerMode = UIDatePicker.Mode.date
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/yyyy"
        let selectedDate = dateFormatter.string(from: dpBirthPicker.date)
        self.tfDateOfBirth.text = selectedDate
        print("selectedDate",selectedDate)
    }
    
    @IBAction func btnCheckBoxSelected(_ sender: UIButton) {
        if sender.currentImage == UIImage(named: "checkbox"){
            sender.setImage(UIImage(named: "checkedbox"), for: UIControl.State.normal)
            //            self.view.makeToast("Driver Online")
        }else{
            sender.setImage(UIImage(named: "checkbox"), for: UIControl.State.normal)
            //            self.view.makeToast("Driver Online")
        }
    }
    
    
    @IBAction func btnDoneExpireAction(_ sender: UIButton) {
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.vwExpireDate.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            self.vwBirthdate.isHidden = true
            self.vwExpireDate.isHidden = true
        })
        
        dpExpirePicker.datePickerMode = UIDatePicker.Mode.date
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/yyyy"
        let selectedDate = dateFormatter.string(from: dpExpirePicker.date)
        print("selectedDate",selectedDate)
        self.tfExpirationDate.text = selectedDate
    }
    
    
    @IBAction func btnSaveAction(_ sender: UIButton) {
        if tfFirstName.text == ""{
        }else{
            if tfMiddleName.text == ""{
            }else{
                if tfLastName.text == ""{
                }else{
                    if tfSelectLocation.text == ""{
                    }else{
                        if tfSocialSecurity.text == ""{
                        }else{
                            if tfDateOfBirth.text == ""{
                            }else{
                                if tfLicenceCardNumber.text == ""{
                                }else{
                                    if tfExpirationDate.text == ""{
                                    }else{
                                        if tfFirstAddress.text == ""{
                                        }else{
                                            if tfSecondAddress.text == ""{
                                            }else{
                                                if tfCityVal.text == ""{
                                                }else{
                                                    if tfStateVal.text == ""{
                                                    }else{
                                                        if tfZipcodeVal.text == ""{
                                                        }else{
                                                            self.givingApiData(self.driverId)
                                                        }
                                                        
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func btnCheckBoxAction(_ sender: UIButton) {
        if sender.currentImage == UIImage(named: "checkbox-2")
        {
            sender.setImage(UIImage(named: "icons8-checked-checkbox-24"),for: UIControl.State.normal)
            //            self.view.makeToast("Driver Online")
        }
        else
        {
            sender.setImage(UIImage(named: "checkbox-2"), for: UIControl.State.normal)
            //            self.view.makeToast("Driver Online")
        }
    }
    
    @IBAction func btnNextAction(_ sender: UIButton) {
        
        if tfFirstName.text == ""{
            self.view.makeToast("Please Enter User First Name")
        }
//        else{
//            if tfMiddleName.text == ""{
//                self.view.makeToast("Please Enter User Middle Name")
//            }
            else{
                if tfLastName.text == ""{
                    self.view.makeToast("Please Enter User Last Name")
                }else{
                    if tfSelectLocation.text == ""{
                        self.view.makeToast("Please Select Location")
                    }else{
                        if tfSocialSecurity.text == ""{
                            self.view.makeToast("Please Enter Social Security Number")
                        }else{
                            if tfDateOfBirth.text == ""{
                                self.view.makeToast("Please Enter Date Of Birth")
                            }else{
                                if tfLicenceCardNumber.text == ""{
                                    self.view.makeToast("Please Enter Licence Card Number")
                                }else{
                                    if tfExpirationDate.text == ""{
                                        self.view.makeToast("Please Enter Expiration Date")
                                    }else{
                                        if tfFirstAddress.text == ""{
                                            self.view.makeToast("Please Enter First Address")
                                        }
//                                        else{
//                                            if tfSecondAddress.text == ""{
//                                                self.view.makeToast("Please Enter Second Address")
//                                            }
                                            else{
                                                if tfCityVal.text == ""{
                                                    self.view.makeToast("Please Enter City")
                                                }else{
                                                    if tfStateVal.text == ""{
                                                        self.view.makeToast("Please Enter State")
                                                    }else{
                                                        if tfZipcodeVal.text == ""{
                                                            self.view.makeToast("Please Enter Zipcode")
                                                        }else{
                                                            self.givingApiData(self.driverId)
                                                        }
                                                        
                                                    }
                                                }
                                            }
//                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
//        }
    }
    
    @IBAction func btnFirstNameEdit(_ sender: UIButton) {
    }
    @IBAction func btnMiddleNameEdit(_ sender: UIButton) {
    }
    @IBAction func btnLastNameEdit(_ sender: UIButton) {
    }
    @IBAction func btnSecurityNumberEdit(_ sender: UIButton) {
    }
    @IBAction func btnLicenceNumberEdit(_ sender: UIButton) {
    }
    @IBAction func btnFirstAddressEdit(_ sender: UIButton) {
    }
    @IBAction func btnSecondAddressEdit(_ sender: UIButton) {
    }
    @IBAction func btnCityEdit(_ sender: UIButton)
    {
    }
    @IBAction func btnStateEdit(_ sender: UIButton)
    {
    }
    @IBAction func btnZipcodeEdit(_ sender: UIButton)
    {
    }
    
    
    @IBAction func ResidentialBtnnActionn(_ sender: UIButton)
    {
        let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "SelectAddressViewController") as! SelectAddressViewController
        self.navigationController?.pushViewController(homeVc, animated: true)
    }
    
    
    @IBAction func btnSelectLocation(_ sender: UIButton) {
        
        view.addSubview(accessoryViewCountry)
        AlertViewData.animate(withDuration: 0.0, delay: 0.0, usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.accessoryViewCountry.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY+UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            AlertViewData.animate(withDuration: 0.5, delay: 0.4, usingSpringWithDamping: 1.0,
                                  initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.accessoryViewCountry.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY-UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)}, completion: { (finished: Bool) in
            })
        })
    }
    
    @IBAction func btnDoneAction(_ sender: UIButton) {
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.accessoryViewCountry.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
    }
    
    @IBAction func btnCancelAction(_ sender: UIButton) {
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.accessoryViewCountry.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
    }

    @IBAction func btnSelectDOB(_ sender: UIButton) {
    }
    @IBAction func btnSelectExpirationDate(_ sender: UIButton) {
    }
    
    
    func givingApiData(_ driverId : String){
        
        let aStrApi = Constant.API.kProfileUpdate
        let dictData : [String : AnyObject]!
//        ,,,,,,,
        dictData = ["driverid" : driverId,
                    "driving_licence":self.tfLicenceCardNumber.text!,
                    "address1":self.tfFirstAddress.text!,
                    "address2":self.tfSecondAddress.text!,
                    "zipcode":self.tfZipcodeVal.text!,
                    "dob":self.tfDateOfBirth.text!,
                    "social_secrityno":self.tfSocialSecurity.text!,
                    "middle_name":self.tfMiddleName.text!,
                    "state":self.tfStateVal.text!] as [String : AnyObject]
        APIManager.requestPOSTURL(aStrApi, params: dictData!, headers: nil, success: {(json) in
            if json["status"].rawString() == "true"{
                self.view.makeToast(json["msg"].rawString())
                
//                UserDefaults.standard.set(self.tfFirstName.text, forKey: "DriverInfo_FirstName")
                UserDefaults.standard.set(self.tfMiddleName.text, forKey: "DriverInfo_MiddleName")
//                UserDefaults.standard.set(self.tfLastName.text, forKey: "DriverInfo_LastName")
                UserDefaults.standard.set(self.tfSelectLocation.text, forKey: "DriverInfo_SelectLocation")
                UserDefaults.standard.set(self.tfSelectLocation.text, forKey: "country")
                UserDefaults.standard.set(self.tfSocialSecurity.text, forKey: "DriverInfo_SocialSecurityNumber")
                UserDefaults.standard.set(self.tfDateOfBirth.text, forKey: "DriverInfo_DOB")
                UserDefaults.standard.set(self.tfLicenceCardNumber.text, forKey: "VehicleInfo_LicenceCardNumber")
                UserDefaults.standard.set(self.tfExpirationDate.text, forKey: "DriverInfo_ExpirationDate")
                
                if self.btnCheckBox.isSelected == true
                {
                    UserDefaults.standard.set("Selected", forKey: "DriverInfo_ButtonCheckBoxStatus")
                }
                else
                {
                    UserDefaults.standard.set("Unselected", forKey: "DriverInfo_ButtonCheckBoxStatus")
                }
                
                UserDefaults.standard.set(self.tfFirstAddress.text, forKey: "DriverInfo_AddressOne")
                UserDefaults.standard.set(self.tfSecondAddress.text, forKey: "DriverInfo_AddressTwo")
                UserDefaults.standard.set(self.tfCityVal.text, forKey: "cityVal")
                UserDefaults.standard.set(self.tfStateVal.text, forKey: "DriverInfo_State")
                UserDefaults.standard.set(self.tfZipcodeVal.text, forKey: "DriverInfo_ZipcodeVal")

                let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "DriverRequirementsVC") as! DriverRequirementsVC
                self.navigationController?.pushViewController(homeVc, animated: true)
            }else{
                self.view.makeToast(json["msg"].rawString())
            }
        }, failure: {(error) in
            
        })
        
    }
    
    func gettingUserData(){
        let userDetail = appDelegate.userDetail
        self.tfFirstName.isUserInteractionEnabled = false
        self.tfLastName.isUserInteractionEnabled = false
        self.tfSelectLocation.isUserInteractionEnabled = false
        self.tfCityVal.isUserInteractionEnabled = false
        self.tfFirstName.text = UserDefaults.standard.value(forKey: "name") as? String
        self.tfLastName.text = UserDefaults.standard.value(forKey: "last_name") as? String
//        userDefaultId.set(city, forKey: "cityVal")
//        userDefaultId.set(email, forKey: "emailId")
//        userDefaultId.set(userId, forKey: "user_id")
//        userDefaultId.set(aImgUrl, forKey: "img_url")
//        userDefaultId.set(aPhn, forKey: "mobile")
//        userDefaultId.set(country, forKey: "country")
        self.tfSelectLocation.text = UserDefaults.standard.value(forKey: "country") as? String
        self.driverId = UserDefaults.standard.value(forKey: "user_id") as? String
        self.tfCityVal.text = UserDefaults.standard.value(forKey: "cityVal") as? String
    }

}
