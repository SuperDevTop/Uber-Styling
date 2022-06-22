//
//  ApiManager.swift
//  Arrive5Driver
//
//  Created by Joy on 09/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import Foundation
import UIKit
import Alamofire
import SwiftyJSON
import SVProgressHUD

class APIManager: NSObject {
    class func requestGETURL(_ strURL: String, success:@escaping (JSON) -> Void, failure:@escaping (Error) -> Void) {
        SVProgressHUD.show(withStatus: "Please Wait")
        Alamofire.request(strURL).responseJSON { (responseObject) -> Void in
            print(responseObject)
            if responseObject.result.isSuccess {
                let resJson = JSON(responseObject.result.value!)
                success(resJson)
                SVProgressHUD.dismiss()
            }
            if responseObject.result.isFailure {
                let error : Error = responseObject.result.error!
                SVProgressHUD.dismiss()
                failure(error)
            }
        }
    }
    
    class func requestPOSTURL(_ strURL : String, params : [String : AnyObject]?, headers : [String : String]?, success:@escaping (JSON) -> Void, failure:@escaping (Error) -> Void){
        SVProgressHUD.show(withStatus: "Please Wait")
        Alamofire.request(strURL, method: .post, parameters: params, encoding: URLEncoding.httpBody, headers: headers).responseJSON { (responseObject) -> Void in
            
            print(responseObject)
            
            if responseObject.result.isSuccess {
                let resJson = JSON(responseObject.result.value!)
                success(resJson)
                SVProgressHUD.dismiss()
            }
            if responseObject.result.isFailure {
                let error : Error = responseObject.result.error!
                
                failure(error)
                SVProgressHUD.dismiss()
            }
        }
    }
    
    
    //    class func requestDeleteURL
    
    class func requestDeleteURL(_ strURL : String, params : [String : AnyObject]?, headers : [String : String]?, success:@escaping (JSON) -> Void, failure:@escaping (Error) -> Void){
        SVProgressHUD.show(withStatus: "Please Wait")
        Alamofire.request(strURL, method: .delete, parameters: params, encoding: JSONEncoding.default, headers: headers).responseJSON { (responseObject) -> Void in
            
            print(responseObject)
            
            if responseObject.result.isSuccess {
                let resJson = JSON(responseObject.result.value!)
                success(resJson)
                SVProgressHUD.dismiss()
            }
            if responseObject.result.isFailure {
                let error : Error = responseObject.result.error!
                failure(error)
                SVProgressHUD.dismiss()
            }
        }
    }
    
    class func requestImage(path: String, completionHandler: @escaping (_ img : UIImage? ) -> Void){
        SVProgressHUD.show(withStatus: "Please Wait")
        Alamofire.request(path).responseData { (response) in
            if response.error == nil {
                
                print(response.result)
                SVProgressHUD.dismiss()
                if let data = response.data {
                    if UIImage(data: data) == nil{
                    }else{
                        
                        let aImg = UIImage(data: data)!
                        completionHandler(aImg)
                    }
                }else{
                    completionHandler(nil)
                    SVProgressHUD.dismiss()
                }
                
            }
        }
    }
}
