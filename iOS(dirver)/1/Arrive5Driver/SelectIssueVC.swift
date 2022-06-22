//
//  SelectIssueVC.swift
//  Arrive5Driver
//
//  Created by parangat2 on 6/15/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import SVProgressHUD
import GoogleMaps
import GooglePlaces
import Alamofire
import SwiftyJSON

class SelectIssueVC: UIViewController, UITableViewDelegate, UITableViewDataSource, GMSMapViewDelegate, UITextViewDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    @IBOutlet weak var googleMapMyTrip: GMSMapView!
    @IBOutlet weak var tblViewReportReasons: UITableView!
    @IBOutlet weak var lblScheduleDateTime: UILabel!
    @IBOutlet weak var lblSource: UILabel!
    @IBOutlet weak var lblDestination: UILabel!
    @IBOutlet weak var heightTblViewReportReasons: NSLayoutConstraint!
    @IBOutlet weak var txtViewRemark: UITextView!
    @IBOutlet weak var btnSubmit: UIButton!
    @IBOutlet weak var imgItem: UIImageView!
    
    var reportDetailsDict : [String : Any] = [:]
    var arrReportReasonList: [[String : Any]] = [[:]]
    var arrSelectedUnselectedReasons : [String] = []
    var sourceLat: Double = 0.0
    var sourceLong: Double = 0.0
    var DestinationLat: Double = 0.0
    var DestinationLong: Double = 0.0
    var startLOC = CLLocation()
    var endLOC = CLLocation()
    var placeholderText: String = "Write a remark here..."
    let picker = UIImagePickerController()
    let imageAlert = UIAlertController()
    var imageFilePath : String!
    var imageDataWork : Data!
    var reasonId : String = ""
    var changedImage = UIImage()



    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        tblViewReportReasons.isHidden = true
        arrReportReasonList.removeAll()
        tblViewReportReasons.estimatedRowHeight = 40.0
        tblViewReportReasons.rowHeight = UITableView.automaticDimension
        let scheduleDate: String = reportDetailsDict["schedule_date"] as! String
        let scheduleTime: String = reportDetailsDict["schedule_time"] as! String
        lblScheduleDateTime.text = scheduleDate + " at " + scheduleTime
        lblSource.text = reportDetailsDict["start_point"] as? String
        lblDestination.text = reportDetailsDict["end_point"] as? String
        txtViewRemark.delegate = self
        txtViewRemark.text = placeholderText
        txtViewRemark.textColor = UIColor.darkGray
        
//        let notificationCenter = NotificationCenter.default
//        notificationCenter.addObserver(self, selector: #selector(keyBoardWillHide), name: Notification.Name.UIResponder.keyboardWillHideNotification, object: nil)
//
//        notificationCenter.addObserver(self, selector: #selector(keyBoardWillShow), name: Notification.Name.UIResponder.keyboardWillChangeFrameNotification, object: nil)

        NotificationCenter.default.addObserver(self, selector: #selector(keyBoardWillShow), name: UIResponder.keyboardWillShowNotification, object: nil)

        
          NotificationCenter.default.addObserver(self, selector: #selector(keyBoardWillHide), name: UIResponder.keyboardWillShowNotification, object: nil)
        
        // UIButton
        btnSubmit.layer.cornerRadius = btnSubmit.frame.height / 2
        btnSubmit.clipsToBounds = true
        btnSubmit.layer.borderColor = UIColor.black.cgColor
        btnSubmit.layer.borderWidth = 1
        // UITextView
        txtViewRemark.layer.borderColor = UIColor.black.cgColor
        txtViewRemark.layer.borderWidth = 1
        drawPolyline()
        addImagePopUp()
        serviceReportReasonList()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    //MARK:- Bump_KeyBoard
    @objc func keyBoardWillShow(sender:NSNotification) -> Void {
        self.view.frame = CGRect(x: 0, y: 20, width: self.view.frame.size.width, height: self.view.frame.size.height)
    }
    
    @objc func keyBoardWillHide(sender:NSNotification) -> Void {
        self.view.frame = CGRect(x: 0, y: 0, width: self.view.frame.size.width, height: self.view.frame.size.height)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }

    // TODO: TextView Delegate
    func textViewDidBeginEditing(_ textView: UITextView) {
        if textView.textColor == UIColor.darkGray {
            textView.text = nil
            textView.textColor = UIColor.black
        }
    }
    
    func textViewDidEndEditing(_ textView: UITextView) {
        if textView.text.isEmpty {
            textView.text = placeholderText
            textView.textColor = UIColor.darkGray
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
            textView.textColor = UIColor.darkGray
            
            textView.selectedTextRange = textView.textRange(from: textView.beginningOfDocument, to: textView.beginningOfDocument)
            
            return false
        }
            
            // Else if the text view's placeholder is showing and the
            // length of the replacement string is greater than 0, clear
            // the text view and set its color to black to prepare for
            // the user's entry
        else if textView.textColor == UIColor.darkGray && !text.isEmpty {
            textView.text = nil
            textView.textColor = UIColor.black
        }
        return true
    }
    
    func textViewShouldBeginEditing(_ textView: UITextView) -> Bool {
        
        return true
    }
    
    func addImagePopUp(){
        
        imageAlert.title = "Choose One From The Following"

        imageAlert.addAction(UIAlertAction(title: "Photos", style: .default, handler: { action in
            
            
//            self.picker.sourceType = .photoLibrary
//
//            self.present(self.picker, animated: true, completion: nil)

            if UIImagePickerController.isSourceTypeAvailable(.photoLibrary) {
                let imagePicker = UIImagePickerController()
                imagePicker.delegate = self
                imagePicker.sourceType = .photoLibrary;
                imagePicker.allowsEditing = true
                self.present(imagePicker, animated: true, completion: nil)
            }
        }))
        imageAlert.addAction(UIAlertAction(title: "Camera", style: .destructive, handler: { action in
//            self.picker.sourceType = .camera
//            self.present(self.picker, animated: true, completion: nil)
            if UIImagePickerController.isSourceTypeAvailable(.camera) {
                let imagePicker = UIImagePickerController()
                imagePicker.delegate = self
                imagePicker.sourceType = .camera;
                imagePicker.allowsEditing = false
                self.present(imagePicker, animated: true, completion: nil)
            }

        }))
        imageAlert.dismiss(animated: true, completion: nil)
    }
    
    // MARK: - Delegates
    // MARK: -
    
    func fbImagePicker(imageSelected image: UIImage?) {
        print("Image selected")
    }
    
  func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        
    if let pickedImage = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
            var paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)
            let documentsDirectory = paths[0] as NSString
            
        changedImage = info[UIImagePickerController.InfoKey.originalImage] as! UIImage
            imgItem.image = changedImage
        }
        imageAlert.dismiss(animated: true, completion: nil)
        dismiss(animated: true, completion: nil)
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        dismiss(animated: true, completion: nil)
        imageAlert.dismiss(animated: true, completion: nil)
    }

    @objc func alertClose(gesture: UITapGestureRecognizer) {
        self.dismiss(animated: true, completion: nil)
    }


    override func viewDidAppear(_ animated: Bool)
    {
        //UITableView
        let height:CGFloat = tblViewReportReasons.contentSize.height;
        heightTblViewReportReasons.constant = height;
    }
    
    override func viewDidLayoutSubviews()
    {
        //UITableView
        let height:CGFloat = tblViewReportReasons.contentSize.height;
        heightTblViewReportReasons.constant = height;
    }

    func drawPolyline()  {
        
        sourceLat = Double(reportDetailsDict["start_point_lat"] as! String)!
        sourceLong = Double(reportDetailsDict["start_point_long"] as! String)!
        DestinationLat = Double(reportDetailsDict["end_point_lat"] as! String)!
        DestinationLong = Double(reportDetailsDict["end_point_long"] as! String)!
        
        // Route Source & Destination
        self.startLOC = CLLocation(latitude: sourceLat, longitude: sourceLong)
        self.endLOC = CLLocation(latitude: DestinationLat, longitude: DestinationLong)
        
        drawPath(startLocation: startLOC, endLocation: endLOC)
        
        let marker = GMSMarker()
        marker.position = CLLocationCoordinate2D(latitude: sourceLat, longitude: sourceLong)
        // marker.icon = userImage.af_imageScaled(to: CGSize(width: 50, height: 50)).af_imageRoundedIntoCircle()
        marker.title = "Source"
        marker.map = googleMapMyTrip
        
        
        let markerr = GMSMarker()
        markerr.position = CLLocationCoordinate2D(latitude: DestinationLat, longitude: DestinationLong)
        // markerr.icon =  washerImage.af_imageScaled(to: CGSize(width: 50, height: 50)).af_imageRoundedIntoCircle()
        markerr.title = "Desintation"
        markerr.map = googleMapMyTrip
        
        let camera = GMSCameraPosition.camera(withLatitude: sourceLat, longitude: sourceLong, zoom: 10.0)
        self.googleMapMyTrip.camera = camera
        self.googleMapMyTrip.animate(to: camera)
    }
    
    func drawPath(startLocation: CLLocation, endLocation: CLLocation)
    {
        
        let origin = "\(startLocation.coordinate.latitude),\(startLocation.coordinate.longitude)"
        let destination = "\(endLocation.coordinate.latitude),\(endLocation.coordinate.longitude)"
        let url = "https://maps.googleapis.com/maps/api/directions/json?origin=\(origin)&destination=\(destination)&mode=driving"
        
        Alamofire.request(url).responseJSON { response in
            //print(response.request as Any)  // original URL request
            //print(response.response as Any) // HTTP URL response
            //print(response.data as Any)     // server data
            //print(response.result as Any)   // result of response serialization
            
//            let json = JSON(data: response.data!)
            let json = JSON(response.result.value!)

            let routes = json["routes"].arrayValue
            print(json)
            // print route using Polyline
            
            DispatchQueue.global(qos: .default).async(execute: {() -> Void in
                // Do something...
                DispatchQueue.main.async(execute: {() -> Void in
                    // self.hideHUD()
                })
            })
            for route in routes
            {
                let routeOverviewPolyline = route["overview_polyline"].dictionary
                let points = routeOverviewPolyline?["points"]?.stringValue
                let path = GMSPath.init(fromEncodedPath: points!)
                let polyline = GMSPolyline.init(path: path)
                polyline.strokeWidth = 4
                polyline.strokeColor =  UIColor.black
                polyline.map = self.googleMapMyTrip
            }
        }
    }
    
    
    //MARK:
    //MARK: UITableView Delegate & DataSource
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return arrReportReasonList.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell") as! SelectIssueTableViewCell
        
        let dict: [String : Any] = arrReportReasonList[indexPath.row]
        cell.btnSelectUnselectReason.tag = indexPath.row
        cell.lblReason.text = dict["reason"] as? String
        if arrSelectedUnselectedReasons[indexPath.row] == "1"
        {
            cell.btnSelectUnselectReason.isSelected = true
        }
        else
        {
            cell.btnSelectUnselectReason.isSelected = false
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        arrSelectedUnselectedReasons.removeAll()
        var i: Int = 0
        while i < arrReportReasonList.count
        {
            if i == indexPath.row
            {
                arrSelectedUnselectedReasons.append("1")
            }
            else
            {
                arrSelectedUnselectedReasons.append("0")
            }
            i += 1
        }
        let dict: [String : Any] = arrReportReasonList[indexPath.row]
        reasonId = dict["id"] as! String
        tblViewReportReasons.reloadData()
    }
    
    //MARK:
    //MARK: UIButton Action
    
    @IBAction func btnActionBack(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func btnActionSelectUnselectReportReason(_ sender: UIButton) {
    }
    
    @IBAction func btnActionAddImage(_ sender: UIButton) {
        
        self.present(imageAlert, animated: true, completion: {
            self.imageAlert.view.superview?.isUserInteractionEnabled = true
            self.imageAlert.view.superview?.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.alertClose(gesture:))))
        })
    }
    
    @IBAction func btnActionSubmit(_ sender: UIButton) {
        serviceSubmitReason()
    }
    
    //MARK:
    //MARK: Web Service
    
    func serviceReportReasonList(){
        let aStrApi = "\(Constant.API.kReportReasonList)"
        SVProgressHUD.show(withStatus: "Please Wait")

        APIManager.requestGETURL(aStrApi, success: {(json) in
            if json["status"].rawString() == "true"{
                SVProgressHUD.dismiss()

                let ResultVal = json["result"].arrayObject as! [[String : Any]]
                self.arrReportReasonList.removeAll()
                self.arrSelectedUnselectedReasons.removeAll()
                for dict in ResultVal
                {
                    self.arrReportReasonList.append(dict)
                }
                for _ in self.arrReportReasonList
                {
                    self.arrSelectedUnselectedReasons.append("0")
                }
                self.tblViewReportReasons.isHidden = false
                self.tblViewReportReasons.reloadData()
//                self.clcRatingOption.reloadData()
            }else{
                SVProgressHUD.dismiss()
            }
        }, failure: {(error) in
            SVProgressHUD.dismiss()
        })
    }

    
    func serviceSubmitReason(){
        
        SVProgressHUD.show(withStatus: "Please Wait")
        
        let aStrApi = "\(Constant.API.kSubmitReason)"
        let driver_id  = UserDefaults.standard.value(forKey: "user_id") as! String
        var comment: String = ""
        
        if txtViewRemark.text == placeholderText
        {
            comment = ""
        }
        else
        {
            comment = txtViewRemark.text
        }
        let parameters = [
            "driver_id" : driver_id,
            "user_id" : reportDetailsDict["user_id"] as? String,
            "booking_id" : reportDetailsDict["booking_id"] as? String,
            "reason_id" : reasonId,
            "comment":comment]
        
        Alamofire.upload(multipartFormData: { multipartFormData in
            for (key, value) in parameters {
                multipartFormData.append((value as AnyObject).data(using: String.Encoding.utf8.rawValue)!, withName: key)
                
            }
            if  let imageData = self.changedImage.jpegData(compressionQuality: 0.2)
            
            {
                multipartFormData.append(imageData, withName: "image", fileName: "image.jpg", mimeType: "jpg/jpeg")
            }
            
        }, to: aStrApi,
           encodingCompletion: { encodingResult in
            switch encodingResult {
            case .success(let upload, _, _):
                upload.responseJSON { response in
                    SVProgressHUD.dismiss()
                    print("the response is : \(response)")
                    let data = response.data
                    do{
                        let responseJSON = try JSON(data: data!)
                        let msgStts = responseJSON["status"].rawString()
                        if msgStts == "true"{
                            
                            let allViewController: [UIViewController] = self.navigationController!.viewControllers as [UIViewController];
                            for aViewController : UIViewController in allViewController
                            {
                                print(aViewController)
                                if aViewController.isKind(of: HomeVC.self)
                                {
                                    self.navigationController?.popToViewController(aViewController, animated: true)
                                }
                            }
                        }
                        else{
                            self.view.makeToast(responseJSON["message"].rawString())
                        }
                    }catch{
                    }
                }
                
            case .failure(let error):
                print(error)
                SVProgressHUD.dismiss()
                
            }
            //        }
        })
    }
}
