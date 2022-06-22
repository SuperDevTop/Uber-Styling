//
//  ViewController.swift
//  Arrive5
//
//  Created by Maestros Infotech on 10/12/20.
//  Copyright © 2020 Apple Inc. All rights reserved.
//

import UIKit
import SVProgressHUD

class ShowReceiptViewController: UIViewController {

    @IBOutlet var DateLabel: UILabel!
    
    @IBOutlet var TripFareLabel: UILabel!
    
    @IBOutlet var TotalLabel: UILabel!
    
    @IBOutlet var SubTotal: UILabel!
    
    @IBOutlet var TollTExcharge: UILabel!
    
    @IBOutlet var GrandTotal: UILabel!
    
    
    @IBOutlet weak var dripidss : NSString!

    
    
    var arrayPastBooking : [Any] = []

    
    override func viewDidLoad() {
        super.viewDidLoad()

        
       bookingdetail()
        SVProgressHUD.show()
    }
    
    
    @IBAction func BackBtnaCtionss(_ sender: UIButton)
       {
           self.navigationController?.popViewController(animated: true)
       }

   func bookingdetail(){
        let aStrApi = "\(Constant.API.bookingdetail)"
      //  let user_id = UserDefaults.standard.string(forKey: "user_id")
        let type = dripidss
        let dictData : [String : AnyObject]!
        dictData = [
                    "id" : type] as [String : AnyObject]
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            self.arrayPastBooking = json["result"].rawValue as! [AnyObject]
            
            
            let dict: [String : Any] = self.arrayPastBooking[0] as! [String : Any]

            
            let time = dict["schedule_time"] as? String
            
             let datess = dict["schedule_date"] as? String
            
            
            let destination = "\(time!)\(" ")\("at")\(" ")\(datess!)"

            self.DateLabel.text = destination

            
            self.TotalLabel.text = dict["amount"] as? String
            
            self.TripFareLabel.text = "0"
            
               self.SubTotal.text = dict["amount"] as? String
            
            self.TollTExcharge.text = "0"
            self.GrandTotal.text = dict["amount"] as? String
            
            
             SVProgressHUD.dismiss()
           // self.tblView.reloadData()
            
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
             SVProgressHUD.dismiss()
            print(error.localizedDescription)
        })
    }


}
