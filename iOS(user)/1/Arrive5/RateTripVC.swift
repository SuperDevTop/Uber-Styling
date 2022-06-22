//
//  RateTripVC.swift
//  Arrive5
//
//  Created by parangat2 on 6/28/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import SwiftyJSON
class RateTripVC: UIViewController, UITextViewDelegate {
    
    @IBOutlet weak var ratingView: CosmosView!
    
    var mainRatingDict = [String: JSON]()
    
    @IBOutlet weak var imgDriver: UIImageView!
    @IBOutlet weak var subViewInfo: UIView!
    @IBOutlet weak var verticalSpacingTextView: NSLayoutConstraint!
    @IBOutlet weak var txtViewReview: UITextView!
    @IBOutlet weak var scrllView: UIScrollView!
    @IBOutlet weak var bottomToscrollView: NSLayoutConstraint!
    
    
    var reviewStrArr = [String]()
    var mRatingList : [JSON]!

    //var mRatingList = [[String:Any]]()
    //var commentListArr: [String] = ["On time", "Reliable"]
    var commentListArr = [String?]()

    var placeholderText: String = "Write a review here..."


    
   
    override func viewDidLoad() {
        super.viewDidLoad()
        ratingView.rating = 1
        commentListArr.append("a")
        
        ratingView.didFinishTouchingCosmos = { rating in
            print(rating)
            let onekey = self.mainRatingDict["1"]?.array
            let twokey = self.mainRatingDict["2"]?.array
            let threekey = self.mainRatingDict["3"]?.array
            let fourkey = self.mainRatingDict["4"]?.array
            let fivekey = self.mainRatingDict["5"]?.array
            
            if self.ratingView.rating == 1{
                self.commentListArr.removeAll()
                let subArray = onekey?.first?.array
                for jsonObject in subArray!{
                    let strComment = jsonObject["comment"].stringValue
                    print(strComment)
                    self.commentListArr.append(strComment)
                }
                self.createLblCommentList()
                
            }else if self.ratingView.rating == 2{
                self.commentListArr.removeAll()
                let subArray = twokey?.first?.array
                for jsonObject in subArray!{
                    let strComment = jsonObject["comment"].stringValue
                    print(strComment)
                    self.commentListArr.append(strComment)
                }
                self.createLblCommentList()
                
            }else if self.ratingView.rating == 3{
                self.commentListArr.removeAll()
                let subArray = threekey?.first?.array
                for jsonObject in subArray!{
                    let strComment = jsonObject["comment"].stringValue
                    print(strComment)
                    self.commentListArr.append(strComment)
                }
                self.createLblCommentList()
                
            }else if self.ratingView.rating == 4{
                self.commentListArr.removeAll()
                let subArray = fourkey?.first?.array
                for jsonObject in subArray!{
                    let strComment = jsonObject["comment"].stringValue
                    print(strComment)
                    self.commentListArr.append(strComment)
                }
                self.createLblCommentList()
                
            }else if self.ratingView.rating == 5{
                self.commentListArr.removeAll()
                let subArray = fivekey?.first?.array
                for jsonObject in subArray!{
                    let strComment = jsonObject["comment"].stringValue
                    print(strComment)
                    self.commentListArr.append(strComment)
                }
                self.createLblCommentList()
            }
            
        }
        
        ratingView.didTouchCosmos = { rating in
            print(rating)
        }
       
        
        RatingListAPI()
        
        

        // Do any additional setup after loading the view.
        //createLblCommentList()
        
        
        
        // UIImage
        imgDriver.layer.cornerRadius = imgDriver.frame.height/2
        imgDriver.clipsToBounds = true
        imgDriver.layer.borderWidth = 2
        imgDriver.layer.masksToBounds = false
        imgDriver.layer.borderColor = UIColor.white.cgColor
        // UITextView
        txtViewReview.layer.cornerRadius = 10
        txtViewReview.clipsToBounds = true
        txtViewReview.layer.masksToBounds = true
        txtViewReview.layer.borderColor = UIColor.lightGray.cgColor
        txtViewReview.layer.borderWidth = 2
        txtViewReview.delegate = self
        txtViewReview.text = placeholderText
        txtViewReview.textColor = UIColor.lightGray
        
    }

    
    
    //rahul
    func RatingListAPI(){
        let URLString = "http://arrive5.pcthepro.com/webservice/Booking/get_rating_comment_user"
        APIManager.requestGETURL(URLString, success: {(json) in
            if json["status"].rawString() == "true"{
                let mainDict = json["result"].dictionary
                self.mainRatingDict = mainDict!
                
            }
            else{
                self.view.makeToast(json["msg"].rawString())
            }
            
        }, failure: {(error) in
                
                self.view.makeToast(error.localizedDescription)
                print(error.localizedDescription)
            })
    }
    
   
    
    
    
    
    @objc func buttonPressed(sender: AnyObject){
        let pressedButton = sender as! UIButton
        if pressedButton.tag == 1{
            pressedButton.layer.backgroundColor = UIColor(red: 42/255.0, green: 160/255.0, blue: 231/255.0, alpha: 1.0).cgColor
            pressedButton.setTitleColor(UIColor.white, for: UIControl.State.normal)
            pressedButton.tag = 0
            reviewStrArr.append((pressedButton.titleLabel?.text)!)
        }
        else{
            pressedButton.layer.backgroundColor = UIColor.white.cgColor
            pressedButton.layer.borderColor = UIColor(red: 42/255.0, green: 160/255.0, blue: 231/255.0, alpha: 1.0).cgColor
            pressedButton.setTitleColor(UIColor.gray, for: UIControl.State.normal)
            pressedButton.tag = 1
            reviewStrArr = reviewStrArr.filter{$0 != (pressedButton.titleLabel?.text)!}
        }
    }
    
    
    func createLblCommentList()  {

//        cell.commentListArr = arr
        
//        var xOrigin: CGFloat = ratingView.frame.origin.x
        var xOrigin: CGFloat = 20
//        var yOrigin: CGFloat = subViewInfo.frame.origin.y + subViewInfo.frame.size.height + 74
        var yOrigin: CGFloat = ratingView.frame.origin.y + 60
        print(subViewInfo.frame.origin.y + subViewInfo.frame.size.height + 74)
        print(subViewInfo.frame.origin.y)
        print(subViewInfo.frame.size.height)
        var verticalSpace: CGFloat = 0.0
        //            var yOrigin: CGFloat = cell.ratingView.frame.origin.y + cell.ratingView.frame.size.height + 30
        
        var newYOrigin : CGFloat = 0.0
        for attribute in commentListArr
        {
            
            let button = UIButton()
            button.setTitle(attribute, for:UIControl.State.normal)
            
            var rect: CGFloat =  button.intrinsicContentSize.width
            print(rect)
            verticalSpace = verticalSpace + button.intrinsicContentSize.height
            
            button.tag = 1
            button.setTitleColor(UIColor.gray, for: UIControl.State.normal)
            //button.textColor = UIColor.lightGray
            button.addTarget(self, action: #selector(self.buttonPressed), for: .touchUpInside)
            
            button.layer.borderColor = UIColor(red: 42/255.0, green: 160/255.0, blue: 231/255.0, alpha: 1.0).cgColor
            button.layer.borderWidth = 2
            //button.numberOfLines = 0
            rect += 10
            
        
            //button.font = UIFont.boldSystemFont(ofSize: 15.0)
            
            button.sizeToFit()
            let newXOrigin = xOrigin + rect + 10
            if newXOrigin > self.view.frame.size.width - 34
            {
//                xOrigin = ratingView.frame.origin.x
                xOrigin = 20
                yOrigin = newYOrigin + 10
            }
            
            button.frame = CGRect(x: xOrigin,
                                 y: yOrigin,
                                 width: rect + 10,
                                 height: 30)
            button.layer.cornerRadius = button.frame.height / 2
            button.clipsToBounds = true
            self.subViewInfo.addSubview(button)
            xOrigin = xOrigin + button.frame.size.width + 10
//            verticalSpace = verticalSpace + label.frame.size.height
            newYOrigin = button.frame.origin.y + button.frame.size.height
        }
        print(verticalSpace)
//        let vSpace = NSLayoutConstraint(item: subViewInfo, attribute: .top, relatedBy: .equal, toItem: txtViewReview, attribute: .bottom, multiplier: 1, constant: verticalSpace)
//        NSLayoutConstraint.activate([vSpace])

//        bottomToscrollView.constant = verticalSpace + 175
        
//        //txtViewReview.frame = CGRect(x: txtViewReview.frame.origin.x,
//                             y: yOrigin,
//                             width: txtViewReview.frame.size.width,
//                             height: txtViewReview.frame.size.height)
    }
    
    // TODO: TextView Delegate
    func textViewDidBeginEditing(_ textView: UITextView) {
        if textView.textColor == UIColor.lightGray {
            textView.text = nil
            textView.textColor = UIColor.darkGray
        }
    }
    
    func textViewDidEndEditing(_ textView: UITextView) {
        if textView.text.isEmpty {
            textView.text = placeholderText
            textView.textColor = UIColor.lightGray
        }
    }
    
    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
        
        // Combine the textView text and the replacement text to
        // create the updated text string
        let currentText:String = textView.text
        let updatedText = (currentText as NSString).replacingCharacters(in: range, with: text)
        
        // If updated text view will be empty, add the placeholder
        // and set the cursor to the beginning of the text view
        if updatedText.isEmpty {
            
            textView.text = placeholderText
            textView.textColor = UIColor.lightGray
            
            textView.selectedTextRange = textView.textRange(from: textView.beginningOfDocument, to: textView.beginningOfDocument)
            
            return false
        }
            
            // Else if the text view's placeholder is showing and the
            // length of the replacement string is greater than 0, clear
            // the text view and set its color to black to prepare for
            // the user's entry
        else if textView.textColor == UIColor.lightGray && !text.isEmpty {
            textView.text = nil
            textView.textColor = UIColor.darkGray
        }
        return true
    }
    
    func textViewShouldBeginEditing(_ textView: UITextView) -> Bool {
        return true
    }

    @IBAction func btnActionBack(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    
    //gautam
    func SubmitAPI(){
        
        let URLString = "http://arrive5.pcthepro.com/webservice/Booking/rate_user"
        
        let user_id = UserDefaults.standard.string(forKey: "user_id") as! String
        let booking_id = UserDefaults.standard.string(forKey: "bookingId") as! String

     //   var reviewArrayStringCode = String()
//        for selectedItem in reviewStrArr{
//        for item in mRatingList{
//            if item["comment"]as! String == selectedItem{
//                reviewArrayStringCode = reviewArrayStringCode + ( item["id"]as! String + ",")
//            }
//           }
//          }
        
        
        //just to remove last comma(,) character from the string.
        //reviewArrayStringCode.removeLast()
        
       
        let dictData : [String : AnyObject]!
        dictData = ["user_id" : user_id,
                    "booking_id":booking_id,
                    "rate":String(ratingView.rating),
                    "comment":txtViewReview.text,
                    "review":"1"] as [String : AnyObject]
//        dictData = ["user_id" : user_id,
//                    "booking_id":booking_id,
//                    "rate":String(ratingView.rating),
//                    "comment":txtViewReview.text,
//                    "review":reviewArrayStringCode] as [String : AnyObject]
        
        print(dictData)
        APIManager.requestPOSTURL(URLString, params: dictData, headers: nil, success: {(json) in
            print(json)
            
            if json["status"].rawString() == "true"{

            self.view.makeToast(json["message"].rawString())


            }else{
                self.view.makeToast(json["message"].rawString())
            }
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error.localizedDescription)
        })

    }
    
    
    
    
    @IBAction func btnActionSubmit(_ sender: UIButton) {
        print(reviewStrArr)
        SubmitAPI()
        
    }
  
}
