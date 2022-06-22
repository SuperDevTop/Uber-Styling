//
//  ARRatingReviewVC.swift
//  Arrive5
//
//  Created by parangat2 on 6/13/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import SVProgressHUD
import SDWebImage

class ARRatingReviewVC: UIViewController, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var tblViewRatingInfo: UITableView!
    
    var arrRatingInfo : [[String:Any]] = [[:]]

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        tblViewRatingInfo.estimatedRowHeight = 120.0
        tblViewRatingInfo.rowHeight = UITableView.automaticDimension
//        serviceGetReviewList()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    //MARK:
    //MARK: UITableView Delegate & DataSource
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return arrRatingInfo.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell") as! RatingTableViewCell
        
        let dict: [String : Any] = arrRatingInfo[indexPath.row]
        cell.lblDriverName.text = dict["dname"] as? String
        
        if let rating: String = dict["rating"] as? String
        {
            cell.lblRating.text = rating
            cell.ratingView.rating = Double(rating)!
        }
        else
        {
            cell.lblRating.text = "0.0"
            cell.ratingView.rating = 0.0
        }

        if let driver_img: String = dict["user_img"] as? String
        {
            cell.imgDriver.sd_setImage(with: URL(string: driver_img), placeholderImage: nil)
        }
        else
        {
            //            cell.imgDriver.image =
        }

        for subview in cell.parentView.subviews {
            if (subview.tag == 1) {
                subview.removeFromSuperview()
            }
        }

        if let arr: [String] = dict["comment_list"] as? [String]
        {
            cell.commentListArr = arr
            
            var xOrigin: CGFloat = cell.imgDriver.frame.origin.x
            var yOrigin: CGFloat = cell.imgDriver.frame.origin.y + cell.imgDriver.frame.size.height + 10
//            var yOrigin: CGFloat = cell.ratingView.frame.origin.y + cell.ratingView.frame.size.height + 30

            var newYOrigin : CGFloat = 0.0
            for attribute in arr
            {

                let label = UILabel()
                label.text = attribute
                var rect: CGFloat =  label.intrinsicContentSize.width
                print(rect)

                label.tag = 1
                label.textColor = UIColor.white
                label.backgroundColor = UIColor(red: 56/255.0, green: 118/255.0, blue: 29/255.0, alpha: 1.0)
                label.layer.borderColor = UIColor.black.cgColor
                label.layer.borderWidth = 2
                label.numberOfLines = 0
                rect += 10
                label.textAlignment = .center
                label.font = UIFont.boldSystemFont(ofSize: 15.0)

                label.sizeToFit()
                let newXOrigin = xOrigin + rect + 10
                if newXOrigin > self.view.frame.size.width - 32
                {
                    xOrigin = cell.imgDriver.frame.origin.x
                    yOrigin = newYOrigin + 10
                }

                label.frame = CGRect(x: xOrigin,
                                     y: yOrigin,
                                     width: rect + 10,
                                     height: 30)

                cell.parentView.addSubview(label)
                xOrigin = xOrigin + label.frame.size.width + 10
                newYOrigin = label.frame.origin.y + label.frame.size.height
            }
            cell.bottomTocontentView.constant = yOrigin
            
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    }
    
    //MARK:
    //MARK: UIButton Action
    
    @IBAction func btnActionBack(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    //MARK:
    //MARK: Web Service

    func serviceGetReviewList(){
//        let aStrApi = "\(Constant.API.KReviewList)"
//        let user_id = UserDefaults.standard.string(forKey: "user_id")
//
//        SVProgressHUD.show(withStatus: "Please Wait")
//
//        let dictData : [String : AnyObject]!
//        dictData = ["user_id" : user_id] as [String : AnyObject]
//
//        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
//            print(json)
//            if json["status"].rawString() == "true"{
//
//                SVProgressHUD.dismiss()
//                let ResultVal = json["message"].arrayObject as! [[String : Any]]
//
//                self.arrRatingInfo.removeAll()
//                self.arrRatingInfo = ResultVal
//                self.tblViewRatingInfo.reloadData()
//            }
//            else{
//                SVProgressHUD.dismiss()
//                self.view.makeToast(json["msg"].rawString())
//            }
//        }, failure: {(error) in
//            SVProgressHUD.dismiss()
//            self.view.makeToast(error.localizedDescription)
//            print(error.localizedDescription)
//        })
    }

}
