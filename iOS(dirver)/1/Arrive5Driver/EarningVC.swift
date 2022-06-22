//
//  EarningVC.swift
//  Arrive5Driver
//
//  Created by parangat2 on 6/19/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import SVProgressHUD
import SDWebImage

class EarningVC: UIViewController, UITableViewDelegate, UITableViewDataSource, UIPickerViewDelegate, UIPickerViewDataSource {

    @IBOutlet weak var lblEaringFilter: UILabel!
    @IBOutlet weak var lblTotalRides: UILabel!
    @IBOutlet weak var lblTotalHours: UILabel!
    @IBOutlet weak var lblRideCost: UILabel!
    @IBOutlet weak var tblViewEarnings: UITableView!
    @IBOutlet weak var heightTblViewEarnings: NSLayoutConstraint!
    @IBOutlet var driverEarningFilterAccessoryView: UIView!
    @IBOutlet weak var driverEarningFilterPickerView: UIPickerView!
    @IBOutlet weak var btnDriverEarningFilter: UIButton!
    
    var arrDriverEarningFilter: [[String : Any]] = [[:]]
    var driverEarningFilterID: Int = -1
    var arrBookingList: [[String : Any]] = [[:]]
    var flagForService: CShort = 0

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        tblViewEarnings.isHidden = true
        driverEarningFilterPickerView.delegate = self
        driverEarningFilterPickerView.dataSource = self
        btnDriverEarningFilter.isUserInteractionEnabled = false
        serviceDriverEarningFilter()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    override func viewDidAppear(_ animated: Bool)
    {
        //UITableView
        let height:CGFloat = tblViewEarnings.contentSize.height;
        heightTblViewEarnings.constant = height;
    }
    
    override func viewDidLayoutSubviews()
    {
        //UITableView
        let height:CGFloat = tblViewEarnings.contentSize.height;
        heightTblViewEarnings.constant = height;
    }
    
    override func updateViewConstraints() {
        super.updateViewConstraints()
        //UITableView
        let height:CGFloat = tblViewEarnings.contentSize.height;
        heightTblViewEarnings.constant = height;
    }

    
    //MARK:
    //MARK: UITableView Delegate & DataSource
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return arrBookingList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell") as! EarningTableViewCell
        
        let dict: [String : Any] = arrBookingList[indexPath.row]
        cell.lblDriverName.text = dict["name"] as? String
        cell.lblPickUpLocation.text = dict["start_point"] as? String
        cell.lblDropOffLocation.text = dict["end_point"] as? String
        if let amount: String = dict["amount"] as? String
        {
            cell.lblAmount.text = "$ " + amount
        }

        if let driver_img: String = dict["image"] as? String
        {
            cell.imgDriver.sd_setImage(with: URL(string: driver_img), placeholderImage: #imageLiteral(resourceName: "userProfile"))
        }
        else
        {
            cell.imgDriver.image = #imageLiteral(resourceName: "userProfile")
        }
        
        //UITableView
        let height:CGFloat = tblViewEarnings.contentSize.height;
        heightTblViewEarnings.constant = height;

        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    }
    
    // MARK: - Picker View Delegates
    // MARK: -
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return arrDriverEarningFilter.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        let dict: [String : Any] = arrDriverEarningFilter[row]
        return dict["type"] as? String
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        let dict: [String : Any] = arrDriverEarningFilter[row]
        lblEaringFilter.text = dict["type"] as? String
        driverEarningFilterID = (dict["id"] as? Int)!
    }

    //MARK:
    //MARK: UIButton Action

    @IBAction func btnActionDriverEarningFilter(_ sender: UIButton) {
        
        view.addSubview(driverEarningFilterAccessoryView)
        AlertViewData.animate(withDuration: 0.0, delay: 0.0, usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.driverEarningFilterAccessoryView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY+UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
            AlertViewData.animate(withDuration: 0.5, delay: 0.4, usingSpringWithDamping: 1.0,
                                  initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.driverEarningFilterAccessoryView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY-UIScreen.main.bounds.height/3, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)}, completion: { (finished: Bool) in
            })
        })
    }
    
    @IBAction func btnActionDoneGenderAccessoryView(_ sender: UIButton) {
        
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.driverEarningFilterAccessoryView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
        serviceDriverEarning()
    }
    
    @IBAction func btnActionCancelGenderAccessoryView(_ sender: UIButton) {
        
        AlertViewData.animate(withDuration: 0.5, delay: 0.4,usingSpringWithDamping: 1.0,
                              initialSpringVelocity: 1.0, options: UIView.AnimationOptions.curveEaseIn, animations:{ self.driverEarningFilterAccessoryView.frame = CGRect(x: 0, y: UIScreen.main.bounds.maxY, width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
        }, completion: { (finished: Bool) in
        })
    }

    @IBAction func btnActionBack(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }

    //MARK:
    //MARK: Web Service
    
    func serviceDriverEarningFilter(){
        
        let aStrApi = "\(Constant.API.KDriverEarningFilter)"
        SVProgressHUD.show(withStatus: "Please Wait")
        
        APIManager.requestGETURL(aStrApi, success: {(json) in
            if json["status"].rawString() == "true"{
                SVProgressHUD.dismiss()
                
                let ResultVal = json["result"].arrayObject as! [[String : Any]]
                self.arrDriverEarningFilter.removeAll()
                for dict in ResultVal
                {
                    self.arrDriverEarningFilter.append(dict)
                }
                if self.arrDriverEarningFilter.count > 0
                {
                    let dict : [String : Any] = self.arrDriverEarningFilter[0]
                    self.lblEaringFilter.text = dict["type"] as? String
                    self.driverEarningFilterID = (dict["id"] as? Int)!
                    self.btnDriverEarningFilter.isUserInteractionEnabled = true
                    if self.flagForService == 0
                    {
                        self.serviceDriverEarning()
                        self.flagForService = 1
                    }
                }
            }else{
                SVProgressHUD.dismiss()
            }
        }, failure: {(error) in
            SVProgressHUD.dismiss()
        })
    }

    func serviceDriverEarning(){
        
        let aStrApi = "\(Constant.API.KDriverEarning)"
        let driver_id  = UserDefaults.standard.value(forKey: "user_id") as! String
        
        SVProgressHUD.show(withStatus: "Please Wait")
        
        let dictData : [String : AnyObject]!
        dictData = ["driver_id" : driver_id,
                    "time_period": "\(driverEarningFilterID)"] as [String : AnyObject]
        
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            if json["status"].rawString() == "true"{
                
                SVProgressHUD.dismiss()
//                let ResultVal = json["result"].arrayObject as! [[String : Any]]
                
                let result = json["result"].rawValue as! [String : Any]

                self.arrBookingList.removeAll()
                self.arrBookingList = result["bookinglist"] as! [[String : Any]]
                if let totalEarnAmount : Int = result["totalearnamount"] as? Int
                {
                    self.lblRideCost.text = "$ " + "\(totalEarnAmount)"
                }
                if let totalRide : Int = result["totalride"] as? Int
                {
                    self.lblTotalRides.text = "\(totalRide)"
                }
                if let totalTime : String = result["totaltime"] as? String
                {
                    self.lblTotalHours.text = totalTime
                }

                self.tblViewEarnings.isHidden = false
                let height:CGFloat = self.tblViewEarnings.contentSize.height;
                self.heightTblViewEarnings.constant = height;
                self.tblViewEarnings.reloadData()
            }
            else{
                SVProgressHUD.dismiss()
                self.view.makeToast(json["msg"].rawString())
            }
        }, failure: {(error) in
            SVProgressHUD.dismiss()
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })
    }
}

class EarningTableViewCell: UITableViewCell {
    
    @IBOutlet weak var imgDriver: UIImageView!
    @IBOutlet weak var lblDriverName: UILabel!
    @IBOutlet weak var lblPickUpLocation: UILabel!
    @IBOutlet weak var lblDropOffLocation: UILabel!
    @IBOutlet weak var lblAmount: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        // UIImage
        imgDriver.layer.cornerRadius = imgDriver.frame.height/2
        imgDriver.clipsToBounds = true
        // UILabel
        lblAmount.layer.borderColor = UIColor.black.cgColor
        lblAmount.layer.borderWidth = 1
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
}
