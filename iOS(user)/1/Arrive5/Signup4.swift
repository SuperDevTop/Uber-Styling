//
//  Signup4.swift
//  Arrive5
//
//  Created by Rahul on 28/09/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import Stripe
import CreditCardForm
//import ObjectMapper
import SwiftyJSON
import SVProgressHUD

class Signup4: UIViewController,STPPaymentCardTextFieldDelegate {

    @IBOutlet var txtCode: UITextField!
    @IBOutlet var MontText: UITextField!
    @IBOutlet var YearTExt: UITextField!
    @IBOutlet var Cvvtext: UITextField!
    var firstname : String!
    var lastname : String!
      var combine : String!
      var statuss : NSString!
    @IBOutlet var paypalpasword: UITextField!
    @IBOutlet var paypaltext: UITextField!
   
    @IBOutlet var PayPalEmailLabel: UILabel!
    
    @IBOutlet var PaypalPasswordLabel: UILabel!
    @IBOutlet var creditCardView: CreditCardFormView!
     let paymentTextField = STPPaymentCardTextField()
    var cardField = STPPaymentCardTextField()

     var dissctsss : [String:Any] = [:]
    
      var tokeasdfn : String!
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate

    @IBOutlet var Scrollviewss: UIScrollView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        if statuss .isEqual(to: "yes")
        {
            paypalpasword.isHidden = true
            paypaltext.isHidden = true
            PayPalEmailLabel.isHidden = true
            PaypalPasswordLabel.isHidden = true
            
            firstname = appDelegate.tfFirstName
            lastname = appDelegate.tfLastName
            
           
          
            
                        let firstname = UserDefaults.standard.string(forKey: "first_name")
            
               let lastname = UserDefaults.standard.string(forKey: "last_name")
             
            combine = firstname! + lastname!
        }
        else
        {
            paypalpasword.isHidden = false
                       paypaltext.isHidden = false
            
            PayPalEmailLabel.isHidden = false
                       PaypalPasswordLabel.layer.isHidden = false
            
            
            firstname = appDelegate.tfFirstName
                  lastname = appDelegate.tfLastName
                   
                   combine = firstname + lastname
        }
        
      
        
        creditCardView.cardHolderString = combine
        createTextField()

    }
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }

    
    
    func createTextField() {
        paymentTextField.frame = CGRect(x: 15, y: 199, width: self.view.frame.size.width - 30, height: 44)
        paymentTextField.delegate = self
        paymentTextField.translatesAutoresizingMaskIntoConstraints = false
        paymentTextField.borderWidth = 0
        
        let border = CALayer()
        let width = CGFloat(1.0)
        border.borderColor = UIColor.darkGray.cgColor
        border.frame = CGRect(x: 0, y: paymentTextField.frame.size.height - width, width:  paymentTextField.frame.size.width, height: paymentTextField.frame.size.height)
        border.borderWidth = width
        paymentTextField.layer.addSublayer(border)
        paymentTextField.layer.masksToBounds = true
        
        Scrollviewss.addSubview(paymentTextField)
        
        NSLayoutConstraint.activate([
            paymentTextField.topAnchor.constraint(equalTo: creditCardView.bottomAnchor, constant: 20),
            paymentTextField.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            paymentTextField.widthAnchor.constraint(equalToConstant: self.view.frame.size.width-20),
            paymentTextField.heightAnchor.constraint(equalToConstant: 44)
            ])
    }
    
    func paymentCardTextFieldDidChange(_ textField: STPPaymentCardTextField) {
        creditCardView.paymentCardTextFieldDidChange(cardNumber: textField.cardNumber, expirationYear: textField.expirationYear, expirationMonth: textField.expirationMonth, cvc: textField.cvc)
        
        print(textField.expirationYear)
        print(textField.expirationMonth)
        print(textField.cardNumber)
    }
    
    func paymentCardTextFieldDidEndEditingExpiration(_ textField: STPPaymentCardTextField) {
        creditCardView.paymentCardTextFieldDidEndEditingExpiration(expirationYear: textField.expirationYear)
        
        print(textField)
    }
    
    func paymentCardTextFieldDidBeginEditingCVC(_ textField: STPPaymentCardTextField) {
        creditCardView.paymentCardTextFieldDidBeginEditingCVC()
        print(textField)
    }
    
    func paymentCardTextFieldDidEndEditingCVC(_ textField: STPPaymentCardTextField) {
        creditCardView.paymentCardTextFieldDidEndEditingCVC()
        
        print(textField)
    }
    
    @IBAction func nextPress(_ sender: Any) {
        
        if statuss .isEqual(to: "yes")
             {
                
                txtCode.text = paymentTextField.cardNumber
                    
                    MontText.text = paymentTextField.formattedExpirationMonth
                    
                    YearTExt.text = paymentTextField.formattedExpirationYear
                    
                    Cvvtext.text = paymentTextField.cvc
                    
                    if self.txtCode.text == ""
                    {
                        self.view.makeToast("Enter valid  Card Number")
                    }
                    else if self.MontText.text == ""
                    {
                        
                            self.view.makeToast("Enter Valid Card Month")
                    }
                        
                    else if self.YearTExt.text == ""
                    {
                        
                        self.view.makeToast("Enter Valid Card Year")
                    }
                    else if self.Cvvtext.text == ""
                    {
                   
                        self.view.makeToast("Enter Valid Card Cvv")
                    }
                else
                {
                    print("btnPayNowClicked")
                    
                   // cardField.resignFirstResponder()
                    
                    print("Get token using card details")
                    
               
                    let card: STPCardParams = STPCardParams()
                    card.number = paymentTextField.cardNumber
                    card.expMonth = paymentTextField.expirationMonth
                    card.expYear =  paymentTextField.expirationYear
                    
                    
                
                    STPAPIClient.shared().createToken(withCard: card) { (token: STPToken?, error: Error?) in
                        guard let token = token, error == nil else {
                            // Present error to user...
                            return
                        }
                      
                        self.tokeasdfn = token.tokenId
                     
                    

                        
                        print(self.tokeasdfn)
                        
                        self.userpayment()

                    }
                    
                    
 
                }
                
        }
        
        else
        
        {
            txtCode.text = paymentTextField.cardNumber
                   
                   MontText.text = paymentTextField.formattedExpirationMonth
                   
                   YearTExt.text = paymentTextField.formattedExpirationYear
                   
                   Cvvtext.text = paymentTextField.cvc
                   
                   if self.txtCode.text == ""
                   {
                       self.view.makeToast("Enter valid  Card Number")
                   }
                   else if self.MontText.text == ""
                   {
                       
                           self.view.makeToast("Enter Valid Card Month")
                   }
                       
                   else if self.YearTExt.text == ""
                   {
                       
                       self.view.makeToast("Enter Valid Card Year")
                   }
                   else if self.Cvvtext.text == ""
                   {
                  
                       self.view.makeToast("Enter Valid Card Cvv")
                   }
               else
               {
                   
                   appDelegate.tfCode = self.txtCode.text
                   appDelegate.tfmonth = self.MontText.text
                   appDelegate.tfcvv = self.Cvvtext.text
                   appDelegate.tfyear = self.YearTExt.text
                   appDelegate.tfpaypalemail = self.paypaltext.text
                    appDelegate.tfpaypalemail = self.paypalpasword.text
                   let signup5 = self.storyboard?.instantiateViewController(withIdentifier: "Signup5") as! Signup5
                   self.navigationController?.pushViewController(signup5, animated: true)
               }
        }
       

}
    
    
    
    func userpayment(){
        let userId = UserDefaults.standard.string(forKey: "user_id")
        //let aStrApi = "http://arrive5.pcthepro.com/api/locationUpdate"
        let aStrApi = "https://maestrosinfotech.com/arrive/webservice/stripe/withoutCardSavePayment"

    
        
         let  amount = dissctsss ["gcm.notification.amount"] as? String
        
          let  asdfasdfadsf = dissctsss ["gcm.notification.bookingId"] as? String
        
       let emailt = UserDefaults.standard.string(forKey:"email")
             
                          let firstname = UserDefaults.standard.string(forKey: "first_name")
              
                 let lastname = UserDefaults.standard.string(forKey: "last_name")
               
              let name  = firstname! + lastname!
        
        print(dissctsss)
        
       let cardnumber = paymentTextField.cardNumber
                         
                       let cardmonth = paymentTextField.formattedExpirationMonth
                         
                        let cardyear = paymentTextField.formattedExpirationYear
                         
                         let cardcvv = paymentTextField.cvc
        
        
        let dictData : [String : AnyObject]!
        dictData = ["name" : name,
                    "email": emailt,
                    "card_num":cardnumber,
                    "cvc" : cardcvv,
                    "exp_month":cardmonth,
                    "exp_year":cardyear,
                    "amount":amount,
                    "booking_id":asdfasdfadsf,
                    "user_id":userId! ] as [String : AnyObject]
        print(dictData)
        
        
        APIManager.requestPOSTURL(aStrApi, params: dictData, headers: nil, success: {(json) in
            print(json)
            if json["result"].rawString() == "true"{
                self.view.makeToast(json["msg"].rawString())
                
               // self.view.makeToast(json["Payment made successfully."].rawString())
                
                 self.view.makeToast(json["msg"].rawString())
                
                let homeVc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                self.navigationController?.pushViewController(homeVc, animated: true)
                
//                self.finalpayment()
            }else{
                self.view.makeToast(json["msg"].rawString())
            }
        }, failure: {(error) in

        })
    }
    
    
   
}
