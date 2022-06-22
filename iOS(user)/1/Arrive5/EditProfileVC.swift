//
//  EditProfileVC.swift
//  Arrive5
//
//  Created by Joy on 06/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import SVProgressHUD

class EditProfileVC: UIViewController,UITextFieldDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    // MARK: - IBOutlets
    // MARK: -
    
    @IBOutlet weak var tfUserFirstName: UITextField!
    @IBOutlet weak var tfUserLastName: UITextField!
    @IBOutlet weak var tfUserEmailId: UITextField!
    @IBOutlet weak var tfUserPhone: UITextField!
    @IBOutlet weak var tfUserPassword: UITextField!
    @IBOutlet weak var tfUserCode: UITextField!
    @IBOutlet weak var btnClearText: UIButton!
    @IBOutlet weak var btnCheckBox: UIButton!
    @IBOutlet weak var btnSignUpAction: UIButton!
    @IBOutlet weak var ivUserImageChange: UIImageView!
    @IBOutlet weak var ivCountryFlag: UIImageView!
    @IBOutlet weak var lblPhoneCode: UILabel!
    
    
    // MARK: - Properties
    // MARK: -
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    let picker = UIImagePickerController()
    let ImageAlert = UIAlertController()
    var ImageFilePath : String!
    var ImageDataWork : Data!
    
    // MARK: - VCCycles
    // MARK: -
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.btnCheckBox.backgroundColor = UIColor.white
        ImageAlert.title = "Choose one of the following:"
        ImageAlert.message = ""
        picker.delegate = self
        picker.allowsEditing = true
        self.btnClearText.isHidden = true
        self.tfUserPassword.delegate = self
        
        self.tfUserPhone.text = appDelegate.tfPhoneNo
        self.lblPhoneCode.text = appDelegate.lblPhoneCode
        self.ivCountryFlag.image = appDelegate.ivFlagValue
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
        if self.tfUserFirstName.text == ""{
            self.view.makeToast("Enter user first name")
        }else if self.tfUserLastName.text == ""{
            self.view.makeToast("Enter user last name")
        }else if self.tfUserPhone.text == ""{
            self.view.makeToast("Enter user Phone Number")
        }else if self.tfUserEmailId.text == ""{
            self.view.makeToast("Enter user email id")
        }else if self.tfUserPassword.text == ""{
            self.view.makeToast("Enter user password")
        }else{
            self.ProfileUpdationData()
        }
    }
    
    @IBAction func btnCheckBoxAction(_ sender: UIButton) {
        if btnCheckBox.backgroundColor == UIColor.white{
            btnCheckBox.backgroundColor = UIColor.clear
            btnCheckBox.setImage(UIImage(named:"Group 1"), for: .normal)
        }else{
            btnCheckBox.backgroundColor = UIColor.white
            btnCheckBox.setImage(UIImage(named:"Joy"), for: .normal)
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
    
    func ProfileUpdationData(){
        var code :  String!
        
        let aStrApi = "\(Constant.API.kAddProfile)"
         code = self.tfUserCode.text!
        if code == nil{
           code = ""
        }
        
        
        let parameters = [
            "firstname" : self.tfUserFirstName.text!,
            "lastname" : self.tfUserLastName.text!,
            "emailid" : self.tfUserEmailId.text!,
            "mobileno" : "\(self.lblPhoneCode.text!)\(self.tfUserPhone.text!)",
            "password":self.tfUserPassword.text!,
            "code":code,
            "token" : appDelegate.deviceTokenString,
            "appplatform":"ios"
        ]
        
            SVProgressHUD.show(withStatus: "Please Wait")
        
        Alamofire.upload(multipartFormData: { (multipartFormData) in
            
            for (key, value) in parameters {
                multipartFormData.append((value as AnyObject).data(using: String.Encoding.utf8.rawValue)!, withName: key)
                if self.ImageDataWork != nil{
                    multipartFormData.append(self.ImageDataWork, withName: "image", fileName: "image", mimeType: "File/png")
                    
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
                                
                                let email = userDetail["mobile"] as? String
                                let invite_code = userDetail["invite_code"] as? String
   
                                let userDefaultId = UserDefaults.standard
                                userDefaultId.set(invite_code, forKey: "invite_code")
                                userDefaultId.set(email, forKey: "email")
                                userDefaultId.set(userId, forKey: "user_id")
                                userDefaultId.set(aImgUrl, forKey: "img_url")
                                userDefaultId.set(aPhn, forKey: "mobile")
                                userDefaultId.set(name, forKey: "name")
                                userDefaultId.synchronize()
                                let homeVC = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                                self.navigationController?.pushViewController(homeVC, animated: true)
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
    
    // MARK: - Delegates
    // MARK: -
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {

        
        if let pickedImage = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
            var paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)
            let documentsDirectory = paths[0] as NSString
            ImageFilePath = documentsDirectory.appendingPathExtension("jpg")
//            ImageDataWork = UIImageJPEGRepresentation(pickedImage, 0.2)
            
              ImageDataWork =  pickedImage.jpegData(compressionQuality: 0.75)
            
//             if  let imageData = self.changedImage.jpegData(compressionQuality: 0.75)
            
            let dataURL = URL(fileURLWithPath: ImageFilePath)
            
            do{
                try ImageDataWork.write(to: dataURL, options: [.atomic])
            }catch{
                //process errors
            }
            ivUserImageChange.contentMode = .scaleToFill
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
