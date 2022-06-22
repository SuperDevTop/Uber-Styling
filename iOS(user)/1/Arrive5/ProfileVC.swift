//
//  ProfileVC.swift
//  Arrive5
//
//  Created by Joy on 04/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class ProfileVC: UIViewController,UITableViewDelegate,UITableViewDataSource {

    @IBOutlet weak var ivUserImage: UIImageView!
    @IBOutlet weak var lblUserName: UILabel!
    @IBOutlet weak var vwRatingVal: CosmosView!
    @IBOutlet weak var lblDateOfJoining: UILabel!
    @IBOutlet weak var lblHomeCity: UILabel!
    @IBOutlet weak var lblFavSinger: UILabel!
    @IBOutlet weak var lblUserDescription: UILabel!
    @IBOutlet weak var tblUserPoints: UITableView!
    @IBOutlet weak var widthLblDescription: NSLayoutConstraint!
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        tblUserPoints.reloadData()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        self.ivUserImage.layer.masksToBounds = true
        self.ivUserImage.layer.cornerRadius = self.ivUserImage.frame.height/2
        
        var firstName: String = ""
        var lastName: String = ""

        if let first_name: String = UserDefaults.standard.value(forKey: "first_name") as? String
        {
            firstName = first_name
        }
        if let last_name: String = UserDefaults.standard.value(forKey: "last_name") as? String
        {
            lastName = last_name
        }
        lblUserName.text = firstName + lastName
        
        if let city: String = UserDefaults.standard.value(forKey: "city") as? String
        {
            lblHomeCity.text = city
        }
        
        if let fav_music: String = UserDefaults.standard.value(forKey: "fav_music") as? String
        {
            lblFavSinger.text = fav_music
        }

        if let about_me: String = UserDefaults.standard.value(forKey: "about_me") as? String
        {
            lblUserDescription.text = about_me
            let rect: CGFloat =  lblUserDescription.intrinsicContentSize.width
            print(rect)
            if rect > self.view.frame.width - 60
            {
                widthLblDescription.constant = self.view.frame.width - 60
            }
            else
            {
                widthLblDescription.constant = rect
            }

        }

        if let join_date: String = UserDefaults.standard.value(forKey: "join_date") as? String
        {
            lblDateOfJoining.text = join_date
        }
        
        let aImgUrl = UserDefaults.standard.string(forKey: "img_url")!
        APIManager.requestImage(path: aImgUrl, completionHandler: {(image) in
            self.ivUserImage.image = image
        })
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnSideMenu(_ sender: UIButton) {
        
        let HomeSideMenuVC = self.storyboard?.instantiateViewController(withIdentifier: "HomeSideMenuVC") as! HomeSideMenuVC
        HomeSideMenuVC.PathDirection = "ProfileVC"
        addChild(HomeSideMenuVC)
        
        HomeSideMenuVC.didMove(toParent: self)
        
        UIView.animate(withDuration: 0.3, delay: 0.2, usingSpringWithDamping: 0.2, initialSpringVelocity: 0.2, options: .curveEaseIn, animations: {
            HomeSideMenuVC.view.frame = CGRect(x: self.view.frame.minX,
                                               y: 0,
                                               width: self.view.frame.size.width ,
                                               height: self.view.frame.size.height)
        }, completion: {(bool) in
            self.view.addSubview(HomeSideMenuVC.view)
            HomeSideMenuVC.view.frame = self.view.bounds
        })
    }
    
    @IBAction func btnEditProfile(_ sender: UIButton) {
        let updateProfileVC = self.storyboard?.instantiateViewController(withIdentifier: "UpdateProfileVC") as! UpdateProfileVC
        self.navigationController?.pushViewController(updateProfileVC, animated: true)
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 4
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.row == 0{
            let tblTotalCell = tableView.dequeueReusableCell(withIdentifier: "tblTotalPts", for: indexPath) as! tblTotalPts
            if let total_points: String = UserDefaults.standard.value(forKey: "total_points") as? String
            {
                tblTotalCell.lblTtlPointsVal.text = total_points
            }
            return tblTotalCell
        }else if indexPath.row == 1{
            let tblUsedPts = tableView.dequeueReusableCell(withIdentifier: "tblUsedPts", for: indexPath) as! tblUsedPts
            if let used_point: String = UserDefaults.standard.value(forKey: "used_point") as? String
            {
                tblUsedPts.lblUsedPts.text = used_point
            }
            return tblUsedPts
        }else if indexPath.row == 2{
            let tblCancelledPts = tableView.dequeueReusableCell(withIdentifier: "tblCancelledPts", for: indexPath) as! tblCancelledPts
            if let cancelled_point: String = UserDefaults.standard.value(forKey: "cancelled_point") as? String
            {
                tblCancelledPts.lblCancelledPts.text = cancelled_point
            }
            return tblCancelledPts
        }else{
            let tblPointsAvailable = tableView.dequeueReusableCell(withIdentifier: "tblPointsAvailable", for: indexPath) as! tblPointsAvailable
            if let points_available: String = UserDefaults.standard.value(forKey: "points_available") as? String
            {
                tblPointsAvailable.lblPointsAvailable.text = points_available
            }
            return tblPointsAvailable
        }
        
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print(indexPath.row)
    }
}
