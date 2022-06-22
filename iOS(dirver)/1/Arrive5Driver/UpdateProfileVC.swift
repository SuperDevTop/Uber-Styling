//
//  UpdateProfileVC.swift
//  Arrive5Driver
//
//  Created by parangat2 on 6/18/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import SVProgressHUD
import Alamofire
import SwiftyJSON

class UpdateProfileVC: UIViewController, UITextFieldDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate, UIPickerViewDelegate, UIPickerViewDataSource {

    // MARK: - IBOutlet
    // MARK: -

    @IBOutlet weak var txtFldFirstName: UITextField!
    @IBOutlet weak var txtFldMiddleName: UITextField!
    @IBOutlet weak var txtFldLastName: UITextField!
    @IBOutlet weak var txtFldEmail: UITextField!
    @IBOutlet weak var txtFldGender: UITextField!
    @IBOutlet weak var txtFldMobileNumber: UITextField!
    @IBOutlet weak var txtFldCity: UITextField!
    @IBOutlet weak var txtFldState: UITextField!
    @IBOutlet weak var txtFldCountry: UITextField!
    @IBOutlet weak var imgDriver: UIImageView!
    @IBOutlet weak var lblRating: UILabel!
    @IBOutlet weak var ratingView: CosmosView!
    @IBOutlet var chooseGenderAccessoryView: UIView!
    @IBOutlet weak var pickerViewGender: UIPickerView!
    
    var dictProfileInfo: [String : Any] = [:]
    let picker = UIImagePickerController()
    let imageAlert = UIAlertController()
    var imageFilePath : String!
    var imageDataWork : Data!
    var reasonId : String = ""
    var changedImage = UIImage()
    var GenderType : Int!
    var arrGender : [String] = ["Female", "Male"]

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        updateInfo()
        addImagePopUp()
        pickerViewGender.delegate = self
        pickerViewGender.dataSource = self
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    func updateInfo()  {
        
        txtFldFirstName.text = UserDefaults.standard.value(forKey: "first_name") as? String
        txtFldMiddleName.text = UserDefaults.standard.value(forKey: "middle_name") as? String
        txtFldLastName.text = UserDefaults.standard.value(forKey: "last_name") as? String
        txtFldEmail.text = UserDefaults.standard.value(forKey: "emailId") as? String
        if let gender: String = UserDefaults.standard.value(forKey: "gender") as? String
        {
            txtFldGender.text = gender
            if gender == ""
            {
                GenderType = 0
            }
            else{
                GenderType = 1
            }
        }
        txtFldMobileNumber.text = UserDefaults.standard.value(forKey: "mobile") as? String
        txtFldCity.text = UserDefaults.standard.value(forKey: "AddressVal") as? String
        txtFldState.text = UserDefaults.standard.value(forKey: "cityVal") as? String
        txtFldCountry.text = UserDefaults.standard.value(forKey: "country") as? String

        // UIImage
        let aImgPath = UserDefaults.standard.value(forKey: "img_url") as? String
        imgDriver.layer.masksToBounds = true
        imgDriver.layer.cornerRadius = imgDriver.frame.height/2
        
        APIManager.requestImage(path: aImgPath!, completionHandler: {(usrImage) in
            self.imgDriver.image = usrImage
        })
    }
    
    func addImagePopUp(){
        
        imageAlert.title = "Choose One From The Following"
        
        imageAlert.addAction(UIAlertAction(title: "Photos", style: .default, handler: { action in
            
            
            //            self.picker.sourceType = .photoLibrary
            //
            //            self.present(self.picker, animated: true, completion: nil)
            
            if UIImagePickerController.isSourceTypeAvailable(.photoLibrary) {
                let imagePicker = UIImagePickerController()
                imagePicker.delegate = self
                imagePicker.sourceType = .photoLibrary;
                imagePicker.allowsEditing = true
                self.present(imagePicker, animated: true, completion: nil)
            }
        }))
        imageAlert.addAction(UIAlertAction(title: "Camera", style: .destructive, handler: { action in
            //            self.picker.sourceType = .camera
            //            self.present(self.picker, animated: true, completion: nil)
            if UIImagePickerController.isSourceTypeAvailable(.camera) {
                let imagePicker = UIImagePickerController()
                imagePicker.delegate = self
                imagePicker.sourceType = .camera;
                imagePicker.allowsEditing = false
                self.present(imagePicker, animated: true, completion: nil)
            }
            
        }))
        imageAlert.dismiss(animated: true, completion: nil)
    }
    
    // MARK: - Delegates
    // MARK: -
    
    func fbImagePicker(imageSelected image: UIImage?) {
        print("Image selected")
    }
    
//    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
//
     func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
    
        if let pickedImage = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
            var paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)
            let documentsDirectory = paths[0] as NSString
            
            changedImage = info[UIImagePickerController.InfoKey.originalImage] as! UIImage
            imgDriver.image = changedImage
        }
        imageAlert.dismiss(animated: true, completion: nil)
        dismiss(animated: true, completion: nil)
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        dismiss(animated: true, completion: nil)
        imageAlert.dismiss(animated: true, completion: nil)
    }
    
    @objc func alertClose(gesture: UITapGestureRecognizer) {
        self.dismiss(animated: true, completion: nil)
    }
    
    // MARK: - Picker View Delegates
    // MARK: -
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return arrGender.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
//        return arrGender[row] as? String
        return arrGender[row]
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        txtFldGender.text = self.arrGender[row]
        GenderType = row
        txtFldGender.resignFirstResponder()
    }

    //MARK:
    //MARK: UIButton Action
    
    @IBAction func btnActionBack(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }

    @IBAction func btnActionUpdateImage(_ sender: UIButton) {
        
        self.present(imageAlert, animated: true, completion: {
            self.imageAlert.view.superview?.isUserInteractionEnabled = true
            self.imageAlert.view.superview?.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.alertClose(gesture:))))
        })
    }
    
    @IBAction func btnActionUpdateProfile(_ sender: UIButton) {
        serviceEditDriverPofile()
    }
    
    @IBAction func btnActionChooseGender(_ sender: UIButton) {
        
        txtFldGender.resignFirstResponder()
        view.addSubview(chooseGenderAccessoryView)
        AlertViewData.animate(withDuration: 0.0, delay: 0.0, usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.chooseGenderAccessoryView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY+UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            AlertViewData.animate(withDuration: 0.5, delay: 0.4, usingSpringWithDamping: 1.0,
                                  initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.chooseGenderAccessoryView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY-UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)}, completion: { (finished: Bool) in
            })
        })
    }
    
    
    @IBAction func btnActionDoneGenderAccessoryView(_ sender: UIButton) {
        
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.chooseGenderAccessoryView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
    }
    
    @IBAction func btnActionCancelGenderAccessoryView(_ sender: UIButton) {
        
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.chooseGenderAccessoryView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
    }
    
    //MARK:
    //MARK: Web Service

    func serviceEditDriverPofile(){
        
        SVProgressHUD.show(withStatus: "Please Wait")
        
        let aStrApi = "\(Constant.API.KEditDriverPofile)"
        let driver_id  = UserDefaults.standard.value(forKey: "user_id") as! String

        let parameters = [
            "driver_id" : driver_id,
            "first_name" : txtFldFirstName.text!,
            "last_name" : txtFldLastName.text!,
            "middle_name" : txtFldMiddleName.text!,
            "email" : txtFldEmail.text!,
            "gender" : "\(GenderType)",
            "address1" : txtFldCity.text!,
            "city": txtFldState.text!,
            "country" : txtFldCountry.text!] as [String : Any]
        
        Alamofire.upload(multipartFormData: { multipartFormData in
            for (key, value) in parameters {
                multipartFormData.append((value as AnyObject).data(using: String.Encoding.utf8.rawValue)!, withName: key)
                
            }
//            if  let imageData = UIImageJPEGRepresentation(self.changedImage,0.2 )
            if  let  imageData =  self.changedImage.jpegData(compressionQuality: 0.2)
                
            {
                multipartFormData.append(imageData, withName: "image", fileName: "image.jpg", mimeType: "jpg/jpeg")
            }
            
        }, to: aStrApi,
           encodingCompletion: { encodingResult in
            switch encodingResult {
            case .success(let upload, _, _):
                upload.responseJSON { response in
                    SVProgressHUD.dismiss()
                    print("the response is : \(response)")
                    let data = response.data
                    do{
                        let responseJSON = try JSON(data: data!)
                        let msgStts = responseJSON["status"].rawString()
                        if msgStts == "true"{
                            
                            let result = responseJSON["result"].rawValue as! [String : Any]

                            print(result)
                            UserDefaults.standard.set(result["image"] as! String, forKey: "img_url")
                            UserDefaults.standard.set(result["first_name"] as! String, forKey: "first_name")
                            UserDefaults.standard.set(result["middle_name"] as! String, forKey: "middle_name")
                            UserDefaults.standard.set(result["last_name"] as! String, forKey: "last_name")
                            UserDefaults.standard.set(result["email"] as! String, forKey: "emailId")
                            UserDefaults.standard.set(result["gender"] as! String, forKey: "gender")
                            UserDefaults.standard.set(result["mobile"] as! String, forKey: "mobile")
                            UserDefaults.standard.set(result["address"] as! String, forKey: "AddressVal")
                            UserDefaults.standard.set(result["city"] as! String, forKey: "cityVal")
                            UserDefaults.standard.set(result["country"] as! String, forKey: "country")
                            
                            self.navigationController?.popViewController(animated: true)
                        }
                        else{
                            self.view.makeToast(responseJSON["message"].rawString())
                        }
                    }catch{
                    }
                }
                
            case .failure(let error):
                print(error)
                SVProgressHUD.dismiss()
                
            }
            //        }
        })
    }
}
