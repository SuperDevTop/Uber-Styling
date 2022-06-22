//
//  UpcomingViewController.swift
//  Arrive5
//
//  Created by Fusion Techware on 25/05/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
class UpcomingCell: UITableViewCell {
    @IBOutlet weak var lbldate : UILabel!
    @IBOutlet weak var lblAmount : UILabel!
    @IBOutlet weak var lblStartPoint : UILabel!
    @IBOutlet weak var lblEndPoint : UILabel!
    @IBOutlet weak var lblVeiclModal : UILabel!
    
    @IBOutlet weak var viewData : UIView!
    
}

class UpcomingViewController: UIViewController,UITableViewDataSource,UITableViewDelegate {
    var arrayUpComingBooking : [Any] = []
    @IBOutlet weak var tblView : UITableView!

    override func viewDidLoad() {
        super.viewDidLoad()
        getUpcominBooking()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func getUpcominBooking(){
        let aStrApi = "\(Constant.API.kPastRide)"
        let user_id = UserDefaults.standard.string(forKey: "user_id")
        let type = "upcoming"
        let dictData : [String : AnyObject]!
        dictData = ["user_id" : user_id,
                    "type" : type] as [String : AnyObject]
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            self.arrayUpComingBooking = json["result"].rawValue as! [AnyObject]
            self.tblView.reloadData()
            
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
    
   
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return arrayUpComingBooking.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell = tableView.dequeueReusableCell(withIdentifier: "UpcomingCell") as! UpcomingCell
        let dict : [String:Any] = arrayUpComingBooking[indexPath.item] as! [String:Any]
        
        let schedule_date = dict["schedule_date"] as! String
        let schedule_time = dict["schedule_time"] as! String
        let combine = "\(schedule_date) at \(schedule_time)"
        print(combine)
        cell.lbldate.text = combine
        
        cell.lblStartPoint.text = dict ["start_point"] as? String
        cell.lblEndPoint.text = dict ["end_point"] as? String
        
        cell.lblVeiclModal.text = dict ["vehicle_model"] as? String
        cell.viewData.layer.cornerRadius = 10
        cell.viewData.clipsToBounds = true
        let amount = dict ["amount"] as? String
        cell.lblAmount.text = amount
         return cell
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
