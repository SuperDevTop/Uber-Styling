//
//  TermsandicondictionViewController.swift
//  Arrive5
//
//  Created by Maestros Infotech on 09/07/20.
//  Copyright © 2020 Apple Inc. All rights reserved.
//

import UIKit
import SVProgressHUD
import SwiftyJSON
import Alamofire

class TermsandicondictionViewController: UIViewController {

    @IBOutlet weak var Titlesl: UILabel!
    @IBOutlet weak var Textviewss: UITextView!
    
    @IBOutlet weak var radioimage: UIImageView!
    @IBOutlet weak var statusss: NSString!

    var iselected : Bool!
    override func viewDidLoad() {
        super.viewDidLoad()

        iselected = true
    
        
        if statusss .isEqual(to: "terms")
        {
             SVProgressHUD.show()
            Titlesl.text = "Terms & Conditions"
         
             self.Textviewss.isHidden = true;
            
             APIManager.requestPOSTURL("https://maestrosinfotech.com/arrive/webservice/user/getTerms", params: nil, headers: nil, success: {(json) in
                          print(json)
                          if json["status"].rawString() == "true"{
                           
                           
                           let userDetail = json["get_terms"].rawValue as! [String:Any]
                                         
                                          self.Textviewss.text = userDetail["description"] as! String
                           
                           
                             SVProgressHUD.dismiss()
                           self.Textviewss.isHidden = false;

                          }else{
                              self.view.makeToast(json["msg"].rawString())
                           SVProgressHUD.dismiss()
                           self.Textviewss.isHidden = true;


                          }
                       
                      },
                                               failure: {(error) in
                                                   SVProgressHUD.dismiss()


                      })
                   
            
        }
        else
        {

            
            Titlesl.text = "Privacy Policy"
                       self.Textviewss.isHidden = true;
            APIManager.requestPOSTURL("https://maestrosinfotech.com/arrive/webservice/user/getPrivacyPolicy", params: nil, headers: nil, success: {(json) in
                   print(json)
                   if json["status"].rawString() == "true"{
                    
                    
                    let userDetail = json["get_data"].rawValue as! [String:Any]
                                  
                                   self.Textviewss.text = userDetail["description"] as! String
                    
                    
                      SVProgressHUD.dismiss()
                    self.Textviewss.isHidden = false;

                   }else{
                       self.view.makeToast(json["msg"].rawString())
                    SVProgressHUD.dismiss()
                    self.Textviewss.isHidden = true;


                   }
                
               },
                                        failure: {(error) in
                                            SVProgressHUD.dismiss()


               })
            
           

        }
        
      
    }
    
    
    
   
   
    
    
    
    
    @IBAction func BackBtnActions(_ sender: UIButton)
    {
         self.navigationController?.popViewController(animated: true)
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    
    
    
    @IBAction func AcceptTermsAndconditons(_ sender: UIButton)
    {
        if iselected == true
        {
            radioimage.image = UIImage (imageLiteralResourceName: "radioSelect")
            iselected = false
        }
        else
        {
            radioimage.image = UIImage (imageLiteralResourceName: "RadioCircle")
            iselected = true
        }
    }
    

}
