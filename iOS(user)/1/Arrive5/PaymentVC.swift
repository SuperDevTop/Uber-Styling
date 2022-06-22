//
//  PaymentVC.swift
//  Arrive5
//
//  Created by parangat2 on 6/27/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import WebKit
import SVProgressHUD

class PaymentVC: UIViewController, WKUIDelegate, WKNavigationDelegate {

    @IBOutlet weak var imgDriver: UIImageView!
    @IBOutlet weak var lblName: UILabel!
    
       var dissct : [String:Any] = [:]
    
    @IBOutlet var AmountLabel: UILabel!
    
    @IBOutlet var Webviewss: WKWebView!
    
    
    @IBOutlet var bidid: NSString!

    @IBOutlet var user_id: NSString!

    @IBOutlet var customerid: NSString!

    @IBOutlet var totalprice: NSString!

    
   
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        //UIImage
        
        
        Webviewss.isHidden = true;
        
         let  amount = dissct ["gcm.notification.amount"] as? String
        
        
        totalprice = dissct ["gcm.notification.amount"] as? String as NSString?

        bidid = dissct ["gcm.notification.bookingId"] as? String as NSString?
        
         customerid = dissct ["gcm.notification.driverId"] as? String as NSString?

        user_id = UserDefaults.standard.string(forKey: "user_id") as NSString?
        
        
                  AmountLabel.text = "$" + amount!
        
        imgDriver.layer.cornerRadius = imgDriver.frame.height/2
        imgDriver.clipsToBounds = true
        imgDriver.layer.borderWidth = 2
        imgDriver.layer.masksToBounds = false
        imgDriver.layer.borderColor = UIColor.white.cgColor
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnActionBack(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func btnActionPayment(_ sender: UIButton) {
        
        let rateTripVC = self.storyboard?.instantiateViewController(withIdentifier: "RateTripVC") as! RateTripVC
        self.navigationController?.pushViewController(rateTripVC, animated: true)
    }
    
    @IBAction func CardBtnACtions(_ sender: UIButton)
    {
//        appDelegate.tfPassword = self.txtPassword.text
        let signup4 = self.storyboard?.instantiateViewController(withIdentifier: "Signup4") as! Signup4
        signup4.statuss = "yes"
        signup4.dissctsss = dissct;
        self.navigationController?.pushViewController(signup4, animated: true)
    }
    @IBAction func PayPalBtnACtions(_ sender: UIButton)
    {
        
        
        
        
      //  https://maestrosinfotech.com/arrive/web/payment.php?booking_id=6A02VIUF&user_id=1&customer_id=6&amount=200
        
        
        let Combinedstring = "https://maestrosinfotech.com/arrive/web/payment.php?booking_id=\(bidid!)&user_id=\(user_id!)&customer_id=\(customerid!)&amount=\(totalprice!)"

        
//        let Combinedstring = "https://www.sandbox.paypal.com/webapps/hermes?token=40V64697MT177471K&useraction=commit&mfid=1611819244644_8edf6ee575a7f"
        
         print(Combinedstring)


         let url = URL(string: Combinedstring)

         self.Webviewss.uiDelegate = self
         Webviewss.navigationDelegate = self;
        // webviewoutlet.navigationDelegate = self;

             Webviewss.load(URLRequest(url: url!))
        
        Webviewss.isHidden = false
        
       // SVProgressHUD .show()
        
    }
    
    func webViewDidStartLoad(webView: WKWebView)
        {
           
          
    //        webviewoutlet.bringSubviewToFront:loaderimage
    //        loaderimage.addSubview:webviewoutlet
            
            
        }
        
        func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
           // let img = webView.screenCapture()
            
            
            let ghhhjh =  "https://www.sandbox.paypal.com/webapps/hermes?token=4L0635335X235770V&useraction=commit&mfid=1609399901611_91d0900e3c7ee"

            
            let ghgfhfg = "https://developerdeepika.com/taxi/PayPal/successful.php"
            
            NSLog("Webview url: %@",Webviewss.url!.absoluteString)
             let urlllllll = Webviewss.url!.absoluteString
           
            
            if (urlllllll == ghgfhfg)
            {
                Webviewss.isHidden = true
                
                self.view.makeToast("Tripe Succesfully Completed")
                               
                let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                self.navigationController?.pushViewController(homeVc, animated: true)
                
                
                 SVProgressHUD .dismiss()
            }
            
        
            
     
                
                
        else if  (urlllllll == ghhhjh)
            {
               SVProgressHUD .dismiss()
                
            }
            
            
            else
            {
                
                
            }

            let array = urlllllll.components(separatedBy: "https://maestrosinfotech.com/arrive/web/payment.php?")

            if array.count == 2
            {
                let name  = array[0]
                
                if name == "https://maestrosinfotech.com/arrive/web/payment.php?"
                {
                      SVProgressHUD .dismiss()
                }
            }
            
            
            
            let arrayss = urlllllll.components(separatedBy: ".")

            if arrayss.count == 3
            {
                let namess = arrayss[1]
                
                if namess == "com/arrive/web/successful"
                {
                    
                    Webviewss.isHidden = true
                                  
                                  self.view.makeToast("Tripe Succesfully Completed")
                                                 
                                  let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                                  self.navigationController?.pushViewController(homeVc, animated: true)
                                  
                                  
                                   SVProgressHUD .dismiss()
                      SVProgressHUD .dismiss()
                }
            }
            

            

            print(array)
            
        }
        
      
        
        
        func webViewDidFinishLoad(webView: WKWebView)
        {
            
              SVProgressHUD .dismiss()
            //AcceptBidRequestAPI()
        }
    
    
    @IBOutlet var btnOther: UIButton!
    @IBAction func otherBtnPressed(_ sender: Any) {
        btn1.isSelected = false
        btn2.isSelected = false
        btn5.isSelected = false
        btnOther.isSelected = true
        btnNotip.isSelected = false
    }
    
    @IBOutlet var btn5: UIButton!
    @IBAction func btn5Pressed(_ sender: Any) {
        btn1.isSelected = false
        btn2.isSelected = false
        btn5.isSelected = true
        btnOther.isSelected = false
        btnNotip.isSelected = false
    }
    
    @IBOutlet var btn2: UIButton!
    @IBAction func btn2Pressed(_ sender: Any) {
        btn1.isSelected = false
        btn2.isSelected = true
        btn5.isSelected = false
        btnOther.isSelected = false
        btnNotip.isSelected = false
    }
    
    @IBOutlet var btn1: UIButton!
    @IBAction func btn1Pressed(_ sender: Any) {
        btn1.isSelected = true
        btn2.isSelected = false
        btn5.isSelected = false
        btnOther.isSelected = false
        btnNotip.isSelected = false
    }

    
    @IBOutlet var btnNotip: UIButton!
    @IBAction func btnNotipPressed(_ sender: Any) {
        btn1.isSelected = false
        btn2.isSelected = false
        btn5.isSelected = false
        btnOther.isSelected = false
        btnNotip.isSelected = true
    }
    
    
    
    
}
