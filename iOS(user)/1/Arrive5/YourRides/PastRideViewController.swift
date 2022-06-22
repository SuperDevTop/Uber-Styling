//
//  PastRideViewController.swift
//  Arrive5
//
//  Created by Fusion Techware on 25/05/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
class PastRideCell: UITableViewCell {
    @IBOutlet weak var lbldate : UILabel!
    @IBOutlet weak var lblDriverName : UILabel!
    @IBOutlet weak var lblStartPoint : UILabel!
    @IBOutlet weak var lblEndPoint : UILabel!
    @IBOutlet weak var lblVeiclModal : UILabel!
    @IBOutlet weak var lblCarNo : UILabel!
    @IBOutlet weak var lblTip : UILabel!
    @IBOutlet weak var lblAmount : UILabel!
    @IBOutlet weak var lblStatus : UILabel!
    @IBOutlet weak var imgDriver : UIImageView!
    @IBOutlet weak var viewData : UIView!
    
}
class PastRideViewController: UIViewController,UITableViewDelegate,UITableViewDataSource {
    @IBOutlet weak var tblView : UITableView!
    var arrayPastBooking : [Any] = []

    override func viewDidLoad() {
        super.viewDidLoad()
        getpastbooking()

    }
    func getpastbooking(){
        let aStrApi = "\(Constant.API.kPastRide)"
        let user_id = UserDefaults.standard.string(forKey: "user_id")
        let type = "past"
        let dictData : [String : AnyObject]!
        dictData = ["user_id" : user_id,
                    "type" : type] as [String : AnyObject]
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            self.arrayPastBooking = json["result"].rawValue as! [AnyObject]
            self.tblView.reloadData()
            
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return arrayPastBooking.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell = tableView.dequeueReusableCell(withIdentifier: "PastRideCell") as! PastRideCell
        let dict : [String:Any] = arrayPastBooking[indexPath.item] as! [String:Any]
        
        let schedule_date = dict["schedule_date"] as! String
        let schedule_time = dict["schedule_time"] as! String
        let combine = "\(schedule_date) at \(schedule_time)"
        print(combine)
        cell.lbldate.text = combine
        let aImgUrl = dict ["userImg"] as! String
        APIManager.requestImage(path: aImgUrl, completionHandler: {(image) in
            cell.imgDriver.image = image
        })
        cell.imgDriver.layer.masksToBounds = true
        cell.imgDriver.layer.cornerRadius = cell.imgDriver.frame.height/2
        cell.lblDriverName.text = dict ["driverName"] as? String
        cell.lblStartPoint.text = dict ["start_point"] as? String
        cell.lblEndPoint.text = dict ["end_point"] as? String
        cell.lblCarNo.text = dict ["carNo"] as? String
        cell.lblVeiclModal.text = dict ["vehicle_model"] as? String
        let amount = dict["amount"] as? String
        
        cell.lblAmount.text = amount
        cell.lblTip.text  = dict["tip"] as? String
        cell.viewData.layer.cornerRadius = 10
        cell.viewData.clipsToBounds = true
        
        let status = dict["status"] as? String
        if status == "1"{
            cell.lblStatus.text = "Completed"
            cell.lblStatus.textColor = UIColor(red: 68.0/255.0, green: 183.0/255.0, blue: 91.0/255.0, alpha: 1)
        }else{
            cell.lblStatus.text = "Cancelled"
            cell.lblStatus.textColor = UIColor(red: 234.0/255.0, green: 55.0/255.0, blue: 58.0/255.0, alpha: 1)
        }
        return cell
    }

    
     func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath)

    {
         let dict : [String:Any] = arrayPastBooking[indexPath.item] as! [String:Any]
        
        let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "TripDetailViewController") as! TripDetailViewController
        homeVc.alldata = dict
        self.navigationController?.pushViewController(homeVc, animated: true)
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
