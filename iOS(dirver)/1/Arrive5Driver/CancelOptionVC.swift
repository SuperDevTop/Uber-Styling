//
//  CancelOptionVC.swift
//  Arrive5Driver
//
//  Created by Joy on 02/05/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class CancelOptionVC: UIViewController, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var tblCancelOptions: UITableView!
    
    var arrCancelOption : [Any] = []
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    //var bookingId : Int = 0
    var bookingId : String = ""
    var arrCancel : [Int] = [0]
    var cancelReason : String = "I am far away"
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        self.callApiData()
        
   
                self.bookingId = appDelegate.userInfo["gcm.notification.booking_rand"] as! String
               
         // asdfasdfdsf
            
            
        }
        


    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnCloseAction(_ sender: UIButton) {
        self.ClosingAction()
    }

    @IBAction func btnBackAction(_ sender: UIButton) {
        self.ClosingAction()
    }
    @IBAction func btnSubmitAction(_ sender: UIButton) {
        
        self.cancelSubmit()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.arrCancelOption.count
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let tblCancelOptionCell = tableView.dequeueReusableCell(withIdentifier: "tblCancelOptionCell", for: indexPath) as! tblCancelOptionCell
        let aDict = self.arrCancelOption[indexPath.row] as! [String:Any]
        tblCancelOptionCell.lblCancelOptionVal.text = aDict["cancel_reason"] as? String
        if self.arrCancel.contains(indexPath.row){
            tblCancelOptionCell.ivCancelSelected.image = UIImage(named: "radio_button")
            self.arrCancel.removeAll()
        }else{
            tblCancelOptionCell.ivCancelSelected.image = UIImage(named: "radio_button1")
        }
        return tblCancelOptionCell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print(indexPath.row)
        let aDict = self.arrCancelOption[indexPath.row] as! [String:Any]
        cancelReason = aDict["cancel_reason"] as! String
        self.arrCancel.append(indexPath.row)
        self.tblCancelOptions.reloadData()
        
    }
    
    func ClosingAction(){
        UIView.animate(withDuration: 0.3, animations: {
            self.view.frame = CGRect(x: 0,
                                     y: self.view.frame.size.height,
                                     width: self.view.frame.size.width,
                                     height: self.view.frame.size.height)
        }) { (bool) in
            self.willMove(toParent: nil)
            self.view.reloadInputViews()
            self.view.removeFromSuperview()
            self.removeFromParent()
        }
    }
    
    func callApiData(){
        let aStrApi = "\(Constant.API.kCancelReason)"
        
        let aDictParam : [String:Any]!
        aDictParam = ["type":"driver"]
        APIManager.requestPOSTURL(aStrApi, params: aDictParam! as [String : AnyObject], headers: nil, success: {(json) in
            if json["status"].rawString() == "true"{
                self.arrCancelOption = json["result"].rawValue as! [Any]
                self.tblCancelOptions.reloadData()
            }else
            {
                
            }
        }, failure: {(error) in
            
             self.view.makeToast(error.localizedDescription)
            
        })
        
    }
    
    func cancelSubmit(){
        let aStrApi = "\(Constant.API.kCancelBooking)"
//        , , type(user/driver)
        let aDictParam : [String:Any]!
        aDictParam = ["type":"driver",
                      "booking_id":self.bookingId,
                      "cancel_reason":self.cancelReason]
        APIManager.requestPOSTURL(aStrApi, params: aDictParam! as [String : AnyObject], headers: nil, success: {(json) in
            if json["status"].rawString() == "true"{
                self.navigationController?.popToRootViewController(animated: false)
            }else{
            }
        }, failure: {(error) in
            
        })
        
    }
    
    
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
