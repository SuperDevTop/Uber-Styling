//
//  NotificationViewController.swift
//  Arrive5
//
//  Created by Fusion Techware on 25/05/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
class NotificationCell : UITableViewCell{
    @IBOutlet weak var lbldesc : UILabel!
    @IBOutlet weak var lblDate : UILabel!
    @IBOutlet weak var lblTime : UILabel!
    @IBOutlet weak var viewData : UIView!
}

class NotificationViewController: UIViewController,UITableViewDataSource,UITableViewDelegate {
    
    @IBOutlet weak var tblView : UITableView!
    var arrayNotification : [Any] = []

    override func viewDidLoad() {
        super.viewDidLoad()
        getNotificationData()
    }
    
    @IBAction func btnBackPressed (_sender : Any){
        self.navigationController? . popViewController(animated: true)
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return arrayNotification.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell = tableView.dequeueReusableCell(withIdentifier: "NotificationCell") as! NotificationCell
        let dict : [String:Any] = arrayNotification[indexPath.item] as! [String:Any]
        cell.lblDate.text = dict ["notification_date"] as? String
        cell.lblTime.text = dict ["notification_time"] as? String
        cell.lbldesc.text = dict ["notification_msg"] as? String
        cell.viewData.layer.cornerRadius = 10
        cell.viewData.clipsToBounds = true

        return cell
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
       
    }
    func getNotificationData (){
        let aStrApi = "\(Constant.API.kNotification)"
        let user_id = UserDefaults.standard.string(forKey: "user_id")
        let dictData : [String : AnyObject]!
        dictData = ["user_id" : user_id] as [String : AnyObject]
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            self.arrayNotification = json["result"].rawValue as! [AnyObject]
            self.tblView.reloadData()
            
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
}
