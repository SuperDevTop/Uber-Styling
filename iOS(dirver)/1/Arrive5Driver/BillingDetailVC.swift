//
//  BillingDetailVC.swift
//  Arrive5Driver
//
//  Created by Joy on 02/05/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class BillingDetailVC: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout,UITextViewDelegate{
    
    @IBOutlet weak var ivUserImage: UIImageView!
    @IBOutlet weak var clcRatingOption: UICollectionView!
    @IBOutlet weak var tvReviewVal: UITextView!
    @IBOutlet weak var lblPayableAmount: UILabel!
    @IBOutlet weak var vwRatingView: CosmosView!
    var formattedArray : String!
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    var arrBillingVal : [Any] = []
    var selectedId : [Int] = []
    var appdelegate = UIApplication.shared.delegate as! AppDelegate
    var arraySelectedItem : [Int] = []
    override func viewDidLoad() {
        super.viewDidLoad()
        self.callApi()
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        let aImgPath = appDelegate.userInfo["gcm.notification.userImg"] as? String
        self.ivUserImage.layer.cornerRadius = self.ivUserImage.frame.height/2
        self.ivUserImage.layer.masksToBounds = true
        self.ivUserImage.contentMode = .scaleAspectFill
        APIManager.requestImage(path: aImgPath!, completionHandler: {(usrImg) in
            self.ivUserImage.image = usrImg
        })
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func textViewDidBeginEditing(_ textView: UITextView) {
        if textView.textColor == UIColor.black{
            
        }else{
            textView.text = "";
        }
        textView.textColor=UIColor.black
    }
    func textViewDidEndEditing(_ textView: UITextView) {
        if tvReviewVal.text == ""{
            tvReviewVal.text = "Write a review here...."
            tvReviewVal.textColor=UIColor.lightGray
            
        }else{
            tvReviewVal.textColor=UIColor.black
            
        }
    }

    @IBAction func btnSubmitAction(_ sender: UIButton) {
        if tvReviewVal.text == "Write a review here...."{
            self.view .makeToast("Please a write a Review")
            
        }else{
            var array : [Any] = []
            for i in 0..<arraySelectedItem.count{
                let dict1 : [String:Any] = arrBillingVal[i] as! [String:Any]
                let  item_id = dict1 ["id"] as! String
                
                array.append(item_id)
                print(array)
                formattedArray = (array.map{String(describing: $0)}).joined(separator: ",")
                print(formattedArray)
                
            }
            //Driver rate
            let aStrApi = "\(Constant.API.kBillingDetail)"
            let driver_id = UserDefaults.standard.string(forKey: "user_id")
            let booking_id = appdelegate.booking_id
            print(booking_id!)
            let rate = vwRatingView.rating
            print(rate)
            let comment = tvReviewVal.text
            let review = formattedArray
            print(review!)
            
            let dictData : [String : AnyObject]!
            dictData = ["driver_id" : driver_id! as AnyObject,
                        "booking_id": booking_id! as AnyObject,
                        "rate":rate,
                        "comment":comment!,
                        "review" : review!] as [String : AnyObject]
            
            APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
                print(json)
                if json["status"].rawString() == "true"{
                    let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                    self.navigationController?.pushViewController(homeVc, animated: false)
                    
                }else{
                    self.view.makeToast(json["msg"].rawString())
                }
            }, failure: {(error) in
                self.view.makeToast(error.localizedDescription)
                print(error.localizedDescription)
            })
        }
    }
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    func callApi(){
        let aStrApi = "\(Constant.API.kgetRatingComment)"
        APIManager.requestGETURL(aStrApi, success: {(json) in
            if json["status"].rawString() == "true"{
                self.arrBillingVal = json["result"].rawValue as! [Any]
                self.clcRatingOption.reloadData()
            }else{
            }
        }, failure: {(error) in

        })
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        let aDict = arrBillingVal[indexPath.row] as! [String : Any]
        let aName = aDict["comment"] as! String
        let aWidth : CGFloat = aName.width(withConstraintedHeight: 30, font: UIFont(name: "Avenir-book", size: 14.0)!)
        
        return CGSize(width: aWidth + 40 , height: 30)
    }
    
    
    
    func systemLayoutSizeFitting(_ targetSize: CGSize, withHorizontalFittingPriority horizontalFittingPriority: UILayoutPriority, verticalFittingPriority: UILayoutPriority) -> CGSize {
        
        //        self.colViewObj.frame = CGRect(x: 0, y: 0,
        //                                       width: targetSize.width, height: 100)
        self.clcRatingOption.layoutIfNeeded()
        
        let aSize = self.clcRatingOption.collectionViewLayout.collectionViewContentSize
        
        return CGSize(width: aSize.width,
                      height: aSize.height + 40)
        
        
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.arrBillingVal.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let clcBillingDetailCell = collectionView.dequeueReusableCell(withReuseIdentifier: "clcBillingDetailCell", for: indexPath) as! clcBillingDetailCell
        let aDict = arrBillingVal[indexPath.row] as! [String : Any]
        let aName = aDict["comment"] as! String
        clcBillingDetailCell.lblSelectionVal.text = aName
        clcBillingDetailCell.lblSelectionVal.layer.cornerRadius = clcBillingDetailCell.lblSelectionVal.frame.height/2
        clcBillingDetailCell.lblSelectionVal.layer.masksToBounds = true
        
        
        clcBillingDetailCell.lblSelectionVal.layer.borderWidth = 1.0
        clcBillingDetailCell.lblSelectionVal.layer.borderColor = UIColor(hexString: "#2AA0E7").cgColor
        
        if arraySelectedItem.count>0{
            if arraySelectedItem.contains(indexPath.row) {
                clcBillingDetailCell.lblSelectionVal.layer.borderWidth = 1.0
                clcBillingDetailCell.lblSelectionVal.layer.borderColor = UIColor.white.cgColor
                clcBillingDetailCell.lblSelectionVal.textColor = UIColor.white
                clcBillingDetailCell.lblSelectionVal.backgroundColor = UIColor(hexString: "#2AA0E7")
                
            }else{
                clcBillingDetailCell.lblSelectionVal.layer.borderWidth = 1.0
                clcBillingDetailCell.lblSelectionVal.layer.borderColor = UIColor(hexString: "#2AA0E7").cgColor
                clcBillingDetailCell.lblSelectionVal.textColor = UIColor(hexString: "#2AA0E7")
                clcBillingDetailCell.lblSelectionVal.backgroundColor = UIColor.white
            }
        }
        
        
       
 
       
        
        

        return clcBillingDetailCell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        if arraySelectedItem.contains(indexPath.item) {
            arraySelectedItem.remove(at: arraySelectedItem.index(of: indexPath.item)!)
            
        }else{
            arraySelectedItem.append(indexPath.row)
        }
        print(arraySelectedItem)
        clcRatingOption.reloadData()

    }
    
    

}

extension String {
    func height(for width: CGFloat, font: UIFont) -> CGFloat {
        let maxSize = CGSize(width: width, height: CGFloat.greatestFiniteMagnitude)
        let actualSize = self.boundingRect(with: maxSize, options: [.usesLineFragmentOrigin], attributes: [NSAttributedString.Key.font: font], context: nil)
        return actualSize.height
    }
    
    func width(withConstraintedHeight height: CGFloat, font: UIFont) -> CGFloat {
        let constraintRect = CGSize(width: .greatestFiniteMagnitude, height: height)
        let boundingBox = self.boundingRect(with: constraintRect, options: .usesLineFragmentOrigin, attributes: [NSAttributedString.Key.font: font], context: nil)
        
        return ceil(boundingBox.width)
    }
}
