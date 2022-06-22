//
//  ProfileVC.swift
//  Arrive5Driver
//
//  Created by Joy on 10/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class ProfileVC: UIViewController, UITableViewDelegate, UITableViewDataSource{
    
    // MARK: - IBOutlet
    // MARK: -
    
    @IBOutlet weak var tblProfileView: UITableView!
    @IBOutlet weak var ivUserImage: UIImageView!
    @IBOutlet weak var vwRatingView: CosmosView!
    @IBOutlet weak var lblRatingVal: UILabel!
    @IBOutlet var vwProfileHeader: UIView!
    
    
    var dictProfileInfo: [String : Any] = [:]

    // MARK: - Properties
    // MARK: -
    
    // MARK: - VCCycles
    // MARK: -
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        let aImgPath = UserDefaults.standard.value(forKey: "img_url") as? String
        self.ivUserImage.layer.masksToBounds = true
        self.ivUserImage.layer.cornerRadius = self.ivUserImage.frame.height/2
        
        APIManager.requestImage(path: aImgPath!, completionHandler: {(usrImage) in
            self.ivUserImage.image = usrImage
        })
        tblProfileView.reloadData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Button Actions
    // MARK: -
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }

    @IBAction func btnEditAction(_ sender: UIButton) {
        
        let updateProfileVC = self.storyboard?.instantiateViewController(withIdentifier: "UpdateProfileVC") as! UpdateProfileVC
        self.navigationController?.pushViewController(updateProfileVC, animated: true)
    }
    
    // MARK: - TableView Delegates
    // MARK: -
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 220
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        return self.vwProfileHeader
    }
    
    // MARK: - TableView DataSource
    // MARK: -
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 9
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let tblProfileCell = tableView.dequeueReusableCell(withIdentifier: "tblProfileCell", for: indexPath) as! tblProfileCell
        if indexPath.row == 0{
            //        userDefaultId.set("Female", forKey: "gender")
            //    }else{
            //    userDefaultId.set("Male", forKey: "gender")
            //    }
            //    self.getAddressFromLatLon(pdblLatitude: lat!, withLongitude: long!)
            //    userDefaultId.set(city, forKey: "cityVal")
            //    userDefaultId.set(userId, forKey: "user_id")
            //    userDefaultId.set(aImgUrl, forKey: "img_url")
            //    userDefaultId.set(aPhn, forKey: "mobile")
            //    userDefaultId.set(name, forKey: "name")
            tblProfileCell.lblProfileData.text = UserDefaults.standard.value(forKey: "first_name") as? String
            tblProfileCell.vwProfileGap.isHidden = false
        }
        else if indexPath.row == 1{
            tblProfileCell.vwProfileGap.isHidden = false
            tblProfileCell.lblProfileData.text = UserDefaults.standard.value(forKey: "middle_name") as? String
        }
        else if indexPath.row == 2{
            tblProfileCell.vwProfileGap.isHidden = false
            tblProfileCell.lblProfileData.text = UserDefaults.standard.value(forKey: "last_name") as? String
        }
        else if indexPath.row == 3{
            tblProfileCell.vwProfileGap.isHidden = false
            tblProfileCell.lblProfileData.text = UserDefaults.standard.value(forKey: "emailId") as? String
            
        }else if indexPath.row == 4{
            tblProfileCell.vwProfileGap.isHidden = false
            tblProfileCell.lblProfileData.text = UserDefaults.standard.value(forKey: "gender") as? String
            
        }else if indexPath.row == 5{
            tblProfileCell.vwProfileGap.isHidden = false
            tblProfileCell.lblProfileData.text = UserDefaults.standard.value(forKey: "mobile") as? String
        }else if indexPath.row == 6{
            tblProfileCell.vwProfileGap.isHidden = false
            tblProfileCell.lblProfileData.text = UserDefaults.standard.value(forKey: "AddressVal") as? String
            
        }else if indexPath.row == 7{
            tblProfileCell.vwProfileGap.isHidden = false
            tblProfileCell.lblProfileData.text = UserDefaults.standard.value(forKey: "cityVal") as? String
            
        }else if indexPath.row == 8{
            tblProfileCell.vwProfileGap.isHidden = false
            tblProfileCell.lblProfileData.text = UserDefaults.standard.value(forKey: "country") as? String
            tblProfileCell.vwProfileGap.isHidden = true
        }
        return tblProfileCell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print(indexPath.row)
    }

}
