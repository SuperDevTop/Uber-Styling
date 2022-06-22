//
//  Signup5.swift
//  Arrive5
//
//  Created by Rahul on 28/09/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import SVProgressHUD
class Signup5: UIViewController {

    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    var checkTerms : Bool!
    
    @IBOutlet var btnCheckBox: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
       
        
        checkTerms = false
    }
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }

    @IBAction func Check(_ sender: Any) {
        
        if let button = sender as? UIButton {
            if button.isSelected {
                // set deselected
                button.isSelected = false
                checkTerms = false

            } else {
                // set selected
                button.isSelected = true
                checkTerms = true
            }
        }
        
    }
    
    @IBAction func nextPress(_ sender: Any) {
        if checkTerms == true {
            self.ProfileUpdationData()
        }else{
             self.view.makeToast("Accept Terms & Condition")
        }
    }
    
    
    
    func ProfileUpdationData(){
        
        let aStrApi = "\(Constant.API.kAddProfile)"

        let parameters = [
            "firstname" : appDelegate.tfFirstName!,
            "lastname" : appDelegate.tfLastName!,
            "emailid" : appDelegate.tfEmail!,
            "mobileno" : "\(appDelegate.lblPhoneCode ?? "" )\(appDelegate.tfPhoneNo ?? "")",
            "password":appDelegate.tfPassword!,
            "credit_card_no":appDelegate.tfCode ?? "" ,
            "card_valid_year":appDelegate.tfyear ?? "" ,
            "card_valid_month":appDelegate.tfmonth ?? "" ,
            "cvv_no":appDelegate.tfcvv ?? "" ,
             "paypal_email":appDelegate.tfpaypalemail ?? "" ,
             "paypal_password":appDelegate.tfpaypalpasword ?? "" ,
             "code":"" ,
            
            "token" : appDelegate.deviceTokenString ?? "0000",
            "appplatform":"ios"
        ] as [String : Any]
        
        SVProgressHUD.show(withStatus: "Please Wait")
        
        Alamofire.upload(multipartFormData: { (multipartFormData) in
            
            for (key, value) in parameters {
                multipartFormData.append((value as AnyObject).data(using: String.Encoding.utf8.rawValue)!, withName: key)
                if  self.appDelegate.dataImageDataWork != nil{
                    multipartFormData.append( self.appDelegate.dataImageDataWork, withName: "image", fileName: "image", mimeType: "File/png")
                    
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
//                                let userId = userDetail["id"] as! String
//                                let name = userDetail["first_name"] as? String
//                                let aImgUrl = userDetail["image"] as? String
//                                let aPhn = userDetail["mobile"] as? String
//
//                                let email = userDetail["mobile"] as? String
//                                let invite_code = userDetail["invite_code"] as? String
//
//                                let userDefaultId = UserDefaults.standard
//                                userDefaultId.set(invite_code, forKey: "invite_code")
//                                userDefaultId.set(email, forKey: "email")
//                                userDefaultId.set(userId, forKey: "user_id")
//                                userDefaultId.set(aImgUrl, forKey: "img_url")
//                                userDefaultId.set(aPhn, forKey: "mobile")
//                                userDefaultId.set(name, forKey: "name")
                
                                self.appDelegate.userDetail = userDetail
                                let userId = userDetail["id"] as! String
                                let name = userDetail["first_name"] as? String
                                
                                let aImgUrl = userDetail["image"] as? String
                                let aPhn = userDetail["mobile"] as? String
                                let email = userDetail["email"] as? String
                                let invite_code = userDetail["invite_code"] as? String
                                
                                let userDefaultId = UserDefaults.standard
                                userDefaultId.set(invite_code, forKey: "invite_code")
                                userDefaultId.set(email, forKey: "email")
                                userDefaultId.set(userId, forKey: "user_id")
                                userDefaultId.set(aImgUrl, forKey: "img_url")
                                userDefaultId.set(aPhn, forKey: "mobile")
                                userDefaultId.set(userDetail["first_name"] as! String, forKey: "first_name")
                                userDefaultId.set(userDetail["last_name"] as! String, forKey: "last_name")
                                userDefaultId.set(userDetail["fav_music"] as! String, forKey: "fav_music")
                                userDefaultId.set(userDetail["about_me"] as! String, forKey: "about_me")
                                userDefaultId.set(userDetail["city"], forKey: "city")
                                userDefaultId.set(userDetail["join_date"], forKey: "join_date")
                                userDefaultId.set(userDetail["total_points"], forKey: "total_points")
                                userDefaultId.set(userDetail["used_point"], forKey: "used_point")
                                userDefaultId.set(userDetail["cancelled_point"], forKey: "cancelled_point")
                                userDefaultId.set(userDetail["points_available"], forKey: "points_available")
                                
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
    
    
}
