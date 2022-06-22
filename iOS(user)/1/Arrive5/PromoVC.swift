//
//  PromoVC.swift
//  Arrive5
//
//  Created by Rahul on 08/08/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import SwiftyJSON


protocol sendDataDelegate:class {
    func myVCDidFinish(text1: String, text: String)
  
}


class PromoVC: UIViewController {
   
    weak var delegate: sendDataDelegate?

    
    
    @IBOutlet var objTableView: UITableView!
    
    var appdelegate : AppDelegate!
    var array = [JSON]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.promoAPI()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    

    func promoAPI(){
        
        let url = "\(Constant.API.kPromoCode)"
        let userId = UserDefaults.standard.value(forKey: "user_id") as! String
        let dictData : [String : AnyObject]!
        dictData = ["user_id" : userId] as [String : AnyObject]
        
        APIManager.requestPOSTURL(url, params: dictData, headers: nil, success: {(json) in
            print(json)
            
            if json["status"].rawString() == "true"{
                //self.lblPrice.text = "$" + json["result"].stringValue
                // self.view.makeToast(json["message"].rawString())
                self.array = json["promoCode"].array!
                self.objTableView.reloadData()
            }else{
                self.view.makeToast(json["message"].rawString())
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }

    
    func checkPromoAPI(strPromoCode :String){
        
        let url = "\(Constant.API.kcheckPromoCode)"
        let userId = UserDefaults.standard.value(forKey: "user_id") as! String
        let dictData : [String : AnyObject]!
        //user_id:100

        dictData = ["user_id" : userId , "promo_code" : strPromoCode] as [String : AnyObject]
        
        APIManager.requestPOSTURL(url, params: dictData, headers: nil, success: {(json) in
            print(json)
            if json["success"].rawString() == "true"
            {
                let dict = json["result"].dictionary
                self.delegate?.myVCDidFinish(text1: (dict?["promoValue"]?.string)!, text: (dict?["promoCode"]?.string)!)
                self.navigationController?.popViewController(animated: true)
            }else{
                self.view.makeToast(json["message"].rawString())
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
    @IBAction func backBtn(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
}

extension PromoVC : UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return array.count;
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "PromoCell") as! PromoCell
        let dict = array[indexPath.row].dictionary
        
        let ptName = dict?["promo_type_name"]?.stringValue
        if ptName == "Percentage"{
            cell.lblThree.text = "Upto " + (dict?["discount"]?.stringValue)! + " %off"
        }else{
            cell.lblThree.text = "Flat " + (dict?["discount"]?.stringValue)! + " off"
        }
        cell.lblone.text = dict?["promo_code"]?.stringValue
        cell.lbltwo.text = dict?["valid_to"]?.stringValue
        return cell
        /*
         "promo_type_name" : "Percentage",
         "promo_code" : "ABCD",
         "valid_to" : "2018-08-25",
         "promo_value" : "123"
         },
         {
         "discount" : "4",
         "promo_type_name" : "Flat",
         */
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let dict = array[indexPath.row].dictionary
        let promo_code = dict?["promo_code"]?.stringValue
        self.checkPromoAPI(strPromoCode: promo_code!)
    }
    
}
