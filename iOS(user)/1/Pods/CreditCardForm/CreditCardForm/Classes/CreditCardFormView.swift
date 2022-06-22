//
//  CreditCardForumView.swift
//  CreditCardForm
//
//  Created by Atakishiyev Orazdurdy on 11/28/16.
//  Copyright © 2016 Veriloft. All rights reserved.
//

import UIKit

public enum Brands : String {
    case NONE, Visa, UnionPay, MasterCard, Amex, JCB, DEFAULT, Discover
}

@IBDesignable
public class CreditCardFormView : UIView {
    
    fileprivate var cardView: UIView    = UIView(frame: .zero)
    fileprivate var backView: UIView    = UIView(frame: .zero)
    fileprivate var frontView: UIView   = UIView(frame: CGRect(x: 0, y: 0, width: 300, height: 200))
    fileprivate var gradientLayer       = CAGradientLayer()
    fileprivate var showingBack:Bool    = false
    
    fileprivate var backImage: UIImageView   = UIImageView(frame: .zero)
    fileprivate var brandImageView           = UIImageView(frame: .zero)
    fileprivate var cardNumber:AKMaskField   = AKMaskField(frame: .zero)
    fileprivate var cardHolderText:UILabel   = UILabel(frame: .zero)
    fileprivate var cardHolder:UILabel       = UILabel(frame: .zero)
    fileprivate var expireDate: AKMaskField  = AKMaskField(frame: .zero)
    fileprivate var expireDateText: UILabel  = UILabel(frame: .zero)
    fileprivate var backLine: UIView         = UIView(frame: .zero)
    fileprivate var cvc: AKMaskField         = AKMaskField(frame: .zero)
    fileprivate var chipImg: UIImageView     = UIImageView(frame: .zero)
    fileprivate var cvcAmexImageView: UIImageView     = UIImageView(frame: .zero)
    fileprivate var amexCVC: AKMaskField     = AKMaskField(frame: .zero)
    fileprivate var colors = [Brands.DEFAULT.rawValue: [
        UIColor.hexStr(hexStr: "363434", alpha: 1),
        UIColor.hexStr(hexStr: "363434", alpha: 1)]
    ]
    
    fileprivate var cardNumberCenterXConstraint = NSLayoutConstraint()
    fileprivate var amex = false {
        didSet {
            self.cardNumberCenterXConstraint.constant = (self.amex) ? -20 : 0.0
            self.cvcAmexImageView.isHidden = !self.amex
            self.amexCVC.isHidden = !self.amex
            UIView.animate(withDuration: 0.5) {
                self.cardView.layoutIfNeeded()
            }
        }
    }
    
    public var cardGradientColors = [String : [UIColor]]()
    
    @IBInspectable
    public var defaultCardColor: UIColor = UIColor.hexStr(hexStr: "363434", alpha: 1) {
        didSet {
            gradientLayer.colors = [defaultCardColor.cgColor, defaultCardColor.cgColor]
            backView.backgroundColor = defaultCardColor
        }
    }
    
    @IBInspectable
    public var cardHolderExpireDateTextColor: UIColor = UIColor.hexStr(hexStr: "#bdc3c7", alpha: 1) {
        didSet {
            cardHolderText.textColor = cardHolderExpireDateTextColor
            expireDateText.textColor = cardHolderExpireDateTextColor
            amexCVC.textColor = cardHolderExpireDateColor
        }
    }
    
    @IBInspectable
    public var cardHolderExpireDateColor: UIColor = .white {
        didSet {
            cardHolder.textColor = cardHolderExpireDateColor
            expireDate.textColor = cardHolderExpireDateColor
            cardNumber.textColor = cardHolderExpireDateColor
        }
    }
    
    @IBInspectable
    public var backLineColor: UIColor = .black {
        didSet {
            backLine.backgroundColor = backLineColor
        }
    }
    
    @IBInspectable
    public var chipImage = UIImage(named: "chip", in: Bundle.currentBundle(), compatibleWith: nil) {
        didSet {
            chipImg.image = chipImage
        }
    }
    
    @IBInspectable
    public var cvcAmexImageName = UIImage(named: "amexCvc", in: Bundle.currentBundle(), compatibleWith: nil) {
        didSet {
            cvcAmexImageView.image = cvcAmexImageName
        }
    }
    
    @IBInspectable
    public var cardHolderString = "----" {
        didSet {
            cardHolder.text = cardHolderString
        }
    }
    
    @IBInspectable
    public var cardHolderPlaceholderString = "CARD HOLDER" {
        didSet {
            cardHolderText.text = cardHolderPlaceholderString
        }
    }
    
    @IBInspectable
    public var expireDatePlaceholderText = "EXPIRY" {
        didSet {
            expireDateText.text = expireDatePlaceholderText
        }
    }
    
    @IBInspectable
    public var cardNumberMaskExpression = "{....} {....} {....} {....}" {
        didSet {
            cardNumber.maskExpression = cardNumberMaskExpression
        }
    }
    
    @IBInspectable
    public var cardNumberMaskTemplate = "**** **** **** ****" {
        didSet {
            cardNumber.maskTemplate = cardNumberMaskTemplate
        }
    }
    
    public var cardNumberFont: UIFont = UIFont(name: "HelveticaNeue", size: 20)!
    public var cardPlaceholdersFont: UIFont = UIFont(name: "HelveticaNeue", size: 10)!
    public var cardTextFont: UIFont = UIFont(name: "HelveticaNeue", size: 12)!
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        createViews()
    }
    
    required public init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    public override func layoutSubviews() {
        super.layoutSubviews()
        createViews()
    }
    
    private func createViews() {
        frontView.isHidden = false
        backView.isHidden = true
        cardView.clipsToBounds = true
        
        if colors.count < 7 {
            setBrandColors()
        }
        
        createCardView()
        createFrontView()
        createbackImage()
        createBrandImageView()
        createCardNumber()
        createCardHolder()
        createCardHolderText()
        createExpireDate()
        createExpireDateText()
        createChipImage()
        createAmexCVC()
        createBackView()
        createBackLine()
        createCVC()
    }
    
    private func setGradientBackground(v: UIView, top: CGColor, bottom: CGColor) {
        let colorTop =  top
        let colorBottom = bottom
        gradientLayer.colors = [ colorTop, colorBottom]
        gradientLayer.locations = [ 0.0, 1.0]
        gradientLayer.frame = v.bounds
        backView.backgroundColor = defaultCardColor
        v.layer.addSublayer(gradientLayer)
    }
    
    private func createCardView() {
        cardView.translatesAutoresizingMaskIntoConstraints = false
        cardView.layer.cornerRadius = 6
        cardView.backgroundColor = .clear
        self.addSubview(cardView)
        //CardView
        self.addConstraint(NSLayoutConstraint(item: cardView, attribute: NSLayoutConstraint.Attribute.top, relatedBy: NSLayoutConstraint.Relation.equal, toItem: self, attribute: NSLayoutConstraint.Attribute.top, multiplier: 1.0, constant: 0.0));
        
        self.addConstraint(NSLayoutConstraint(item: cardView, attribute: NSLayoutConstraint.Attribute.centerX, relatedBy: NSLayoutConstraint.Relation.equal, toItem: self, attribute: NSLayoutConstraint.Attribute.centerX, multiplier: 1.0, constant: 0.0));
        
        self.addConstraint(NSLayoutConstraint(item: cardView, attribute: NSLayoutConstraint.Attribute.width, relatedBy: NSLayoutConstraint.Relation.equal, toItem: self, attribute: NSLayoutConstraint.Attribute.width, multiplier: 1.0, constant: 0.0));
        
        self.addConstraint(NSLayoutConstraint(item: cardView, attribute: NSLayoutConstraint.Attribute.height, relatedBy: NSLayoutConstraint.Relation.equal, toItem: self, attribute: NSLayoutConstraint.Attribute.height, multiplier: 1.0, constant: 0.0));
    }
    
    private func createBackView() {
        backView.translatesAutoresizingMaskIntoConstraints = false
        backView.layer.cornerRadius = 6
        backView.center = CGPoint(x: self.bounds.midX, y: self.bounds.midY)
        backView.autoresizingMask = [UIView.AutoresizingMask.flexibleLeftMargin, UIView.AutoresizingMask.flexibleRightMargin, UIView.AutoresizingMask.flexibleTopMargin, UIView.AutoresizingMask.flexibleBottomMargin]
        cardView.addSubview(backView)
        
        self.addConstraint(NSLayoutConstraint(item: backView, attribute: NSLayoutConstraint.Attribute.top, relatedBy: NSLayoutConstraint.Relation.equal, toItem: self, attribute: NSLayoutConstraint.Attribute.top, multiplier: 1.0, constant: 0.0));
        
        self.addConstraint(NSLayoutConstraint(item: backView, attribute: NSLayoutConstraint.Attribute.centerX, relatedBy: NSLayoutConstraint.Relation.equal, toItem: self, attribute: NSLayoutConstraint.Attribute.centerX, multiplier: 1.0, constant: 0.0));
        
        self.addConstraint(NSLayoutConstraint(item: backView, attribute: NSLayoutConstraint.Attribute.width, relatedBy: NSLayoutConstraint.Relation.equal, toItem: self, attribute: NSLayoutConstraint.Attribute.width, multiplier: 1.0, constant: 0.0));
        
        self.addConstraint(NSLayoutConstraint(item: backView, attribute: NSLayoutConstraint.Attribute.height, relatedBy: NSLayoutConstraint.Relation.equal, toItem: self, attribute: NSLayoutConstraint.Attribute.height, multiplier: 1.0, constant: 0.0));
    }
    
    private func createFrontView() {
        frontView.translatesAutoresizingMaskIntoConstraints = false
        frontView.layer.cornerRadius = 6
        frontView.center = CGPoint(x: self.bounds.midX, y: self.bounds.midY)
        frontView.autoresizingMask = [UIView.AutoresizingMask.flexibleLeftMargin, UIView.AutoresizingMask.flexibleRightMargin, UIView.AutoresizingMask.flexibleTopMargin, UIView.AutoresizingMask.flexibleBottomMargin]
        cardView.addSubview(frontView)
        setGradientBackground(v: frontView, top: defaultCardColor.cgColor, bottom: defaultCardColor.cgColor)
        
        self.addConstraint(NSLayoutConstraint(item: frontView, attribute: NSLayoutConstraint.Attribute.top, relatedBy: NSLayoutConstraint.Relation.equal, toItem: self, attribute: NSLayoutConstraint.Attribute.top, multiplier: 1.0, constant: 0.0));
        
        self.addConstraint(NSLayoutConstraint(item: frontView, attribute: NSLayoutConstraint.Attribute.centerX, relatedBy: NSLayoutConstraint.Relation.equal, toItem: self, attribute: NSLayoutConstraint.Attribute.centerX, multiplier: 1.0, constant: 0.0));
        
        self.addConstraint(NSLayoutConstraint(item: frontView, attribute: NSLayoutConstraint.Attribute.width, relatedBy: NSLayoutConstraint.Relation.equal, toItem: self, attribute: NSLayoutConstraint.Attribute.width, multiplier: 1.0, constant: 0.0));
        
        self.addConstraint(NSLayoutConstraint(item: frontView, attribute: NSLayoutConstraint.Attribute.height, relatedBy: NSLayoutConstraint.Relation.equal, toItem: self, attribute: NSLayoutConstraint.Attribute.height, multiplier: 1.0, constant: 0.0));
    }
    
    private func createbackImage() {
        backImage.translatesAutoresizingMaskIntoConstraints = false
        backImage.image = UIImage(named: "back.jpg")
        backImage.contentMode = UIView.ContentMode.scaleAspectFill
        frontView.addSubview(backImage)
        
        self.addConstraint(NSLayoutConstraint(item: backImage, attribute: NSLayoutConstraint.Attribute.top, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.top, multiplier: 1.0, constant: 0.0));
        
        self.addConstraint(NSLayoutConstraint(item: backImage, attribute: NSLayoutConstraint.Attribute.leading, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.leading, multiplier: 1.0, constant: 0.0));
        
        self.addConstraint(NSLayoutConstraint(item: backImage, attribute: NSLayoutConstraint.Attribute.trailing, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.trailing, multiplier: 1.0, constant: 0.0));
        
        self.addConstraint(NSLayoutConstraint(item: backImage, attribute: NSLayoutConstraint.Attribute.bottom, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.bottom, multiplier: 1.0, constant: 0.0));
    }
    
    private func createBrandImageView() {
        //Card brand image
        brandImageView.translatesAutoresizingMaskIntoConstraints = false
        brandImageView.contentMode = UIView.ContentMode.scaleAspectFit
        frontView.addSubview(brandImageView)
        
        self.addConstraint(NSLayoutConstraint(item: brandImageView, attribute: NSLayoutConstraint.Attribute.top, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.top, multiplier: 1.0, constant: 10));
        
        self.addConstraint(NSLayoutConstraint(item: brandImageView, attribute: NSLayoutConstraint.Attribute.trailing, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.trailing, multiplier: 1.0, constant: -10));
        
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[view(==60)]", options: NSLayoutConstraint.FormatOptions(rawValue: 0), metrics: nil, views: ["view": brandImageView]));
        
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[view(==40)]", options: NSLayoutConstraint.FormatOptions(rawValue: 0), metrics: nil, views: ["view": brandImageView]));
    }
    
    private func createCardNumber() {
        //Credit card number
        cardNumber.translatesAutoresizingMaskIntoConstraints = false
        cardNumber.maskExpression = cardNumberMaskExpression
        cardNumber.maskTemplate = cardNumberMaskTemplate
        cardNumber.textColor = cardHolderExpireDateColor
        cardNumber.isUserInteractionEnabled = false
        cardNumber.textAlignment = NSTextAlignment.center
        cardNumber.font = cardNumberFont
        frontView.addSubview(cardNumber)
        
        cardNumberCenterXConstraint = NSLayoutConstraint(item: cardNumber, attribute: NSLayoutConstraint.Attribute.centerX, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.centerX, multiplier: 1.0, constant: 0)
        self.addConstraint(cardNumberCenterXConstraint);
        
        self.addConstraint(NSLayoutConstraint(item: cardNumber, attribute: NSLayoutConstraint.Attribute.centerY, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.centerY, multiplier: 1.0, constant: 0.0));
        
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[view(==200)]", options: NSLayoutConstraint.FormatOptions(rawValue: 0), metrics: nil, views: ["view": cardNumber]));
        
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[view(==30)]", options: NSLayoutConstraint.FormatOptions(rawValue: 0), metrics: nil, views: ["view": cardNumber]));
    }
    
    private func createCardHolder() {
        //Name
        cardHolder.translatesAutoresizingMaskIntoConstraints = false
        cardHolder.font = cardTextFont
        cardHolder.textColor = cardHolderExpireDateColor
        cardHolder.text = cardHolderString
        frontView.addSubview(cardHolder)
        
        self.addConstraint(NSLayoutConstraint(item: cardHolder, attribute: NSLayoutConstraint.Attribute.bottom, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.bottom, multiplier: 1.0, constant: -20));
        
        self.addConstraint(NSLayoutConstraint(item: cardHolder, attribute: NSLayoutConstraint.Attribute.leading, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.leading, multiplier: 1.0, constant: 15));
        
        self.addConstraint(NSLayoutConstraint(item: cardHolder, attribute: NSLayoutConstraint.Attribute.height, relatedBy: NSLayoutConstraint.Relation.equal, toItem: nil, attribute: NSLayoutConstraint.Attribute.height, multiplier: 1.0, constant: 20));
    }
    
    private func createCardHolderText() {
        //Card holder uilabel
        cardHolderText.translatesAutoresizingMaskIntoConstraints = false
        cardHolderText.font = cardPlaceholdersFont
        cardHolderText.text = cardHolderPlaceholderString
        cardHolderText.textColor = cardHolderExpireDateTextColor
        frontView.addSubview(cardHolderText)
        
        self.addConstraint(NSLayoutConstraint(item: cardHolderText, attribute: NSLayoutConstraint.Attribute.bottom, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardHolder, attribute: NSLayoutConstraint.Attribute.top, multiplier: 1.0, constant: -3));
        
        self.addConstraint(NSLayoutConstraint(item: cardHolderText, attribute: NSLayoutConstraint.Attribute.leading, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.leading, multiplier: 1.0, constant: 15));
    }
    
    private func createExpireDate() {
        //Expire Date
        expireDate = AKMaskField()
        expireDate.translatesAutoresizingMaskIntoConstraints = false
        expireDate.font = cardTextFont
        expireDate.maskExpression = "{..}/{..}"
        expireDate.text = "MM/YY"
        expireDate.textColor = cardHolderExpireDateColor
        frontView.addSubview(expireDate)
        
        self.addConstraint(NSLayoutConstraint(item: expireDate, attribute: NSLayoutConstraint.Attribute.bottom, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.bottom, multiplier: 1.0, constant: -20));
        
        self.addConstraint(NSLayoutConstraint(item: expireDate, attribute: NSLayoutConstraint.Attribute.trailing, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.trailing, multiplier: 1.0, constant: -55));
    }
    
    private func createExpireDateText() {
        //Expire Date Text
        expireDateText.translatesAutoresizingMaskIntoConstraints = false
        expireDateText.font = cardPlaceholdersFont
        expireDateText.text = expireDatePlaceholderText
        expireDateText.textColor = cardHolderExpireDateTextColor
        frontView.addSubview(expireDateText)
        
        self.addConstraint(NSLayoutConstraint(item: expireDateText, attribute: NSLayoutConstraint.Attribute.bottom, relatedBy: NSLayoutConstraint.Relation.equal, toItem: expireDate, attribute: NSLayoutConstraint.Attribute.top, multiplier: 1.0, constant: -3));
        
        self.addConstraint(NSLayoutConstraint(item: expireDateText, attribute: NSLayoutConstraint.Attribute.trailing, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.trailing, multiplier: 1.0, constant: -58));
    }
    
    private func createAmexCVC() {
        
        //Create Amex card cvc
        amexCVC = AKMaskField()
        amexCVC.translatesAutoresizingMaskIntoConstraints = false
        amexCVC.font = cardTextFont
        amexCVC.text = expireDatePlaceholderText
        amexCVC.maskExpression = "{....}"
        amexCVC.text = "***"
        amexCVC.isHidden = true
        frontView.addSubview(amexCVC)
        
        self.addConstraint(NSLayoutConstraint(item: amexCVC, attribute: NSLayoutConstraint.Attribute.trailing, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.trailing, multiplier: 1.0, constant: -23));
        
        self.addConstraint(NSLayoutConstraint(item: amexCVC, attribute: NSLayoutConstraint.Attribute.centerY, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardNumber, attribute: NSLayoutConstraint.Attribute.centerY, multiplier: 1.0, constant: 15));
        
        self.addConstraint(NSLayoutConstraint(item: amexCVC, attribute: NSLayoutConstraint.Attribute.width, relatedBy: NSLayoutConstraint.Relation.equal, toItem: nil, attribute: NSLayoutConstraint.Attribute.width, multiplier: 1.0, constant: 27));
        
        // Create amex cvc image
        cvcAmexImageView.translatesAutoresizingMaskIntoConstraints = false
        cvcAmexImageView.image = cvcAmexImageName
        cvcAmexImageView.contentMode = ContentMode.scaleAspectFit
        cvcAmexImageView.isHidden = true
        frontView.addSubview(cvcAmexImageView)
        
        self.addConstraint(NSLayoutConstraint(item: cvcAmexImageView, attribute: NSLayoutConstraint.Attribute.trailing, relatedBy: NSLayoutConstraint.Relation.equal, toItem: amexCVC, attribute: NSLayoutConstraint.Attribute.leading, multiplier: 1.0, constant: -5));
        
        self.addConstraint(NSLayoutConstraint(item: cvcAmexImageView, attribute: NSLayoutConstraint.Attribute.centerY, relatedBy: NSLayoutConstraint.Relation.equal, toItem: amexCVC, attribute: NSLayoutConstraint.Attribute.centerY, multiplier: 1.0, constant: 0));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[view(==15)]", options: NSLayoutConstraint.FormatOptions(rawValue: 0), metrics: nil, views: ["view": cvcAmexImageView]));

        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[view(==15)]", options: NSLayoutConstraint.FormatOptions(rawValue: 0), metrics: nil, views: ["view": cvcAmexImageView]));
    
    }
    
    private func createChipImage() {
        //Chip image
        chipImg.translatesAutoresizingMaskIntoConstraints = false
        chipImg.alpha = 0.5
        chipImg.image = chipImage
        frontView.addSubview(chipImg)
        
        self.addConstraint(NSLayoutConstraint(item: chipImg, attribute: NSLayoutConstraint.Attribute.top, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.top, multiplier: 1.0, constant: 15));
        
        self.addConstraint(NSLayoutConstraint(item: chipImg, attribute: NSLayoutConstraint.Attribute.leading, relatedBy: NSLayoutConstraint.Relation.equal, toItem: cardView, attribute: NSLayoutConstraint.Attribute.leading, multiplier: 1.0, constant: 15));
        
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[view(==45)]", options: NSLayoutConstraint.FormatOptions(rawValue: 0), metrics: nil, views: ["view": chipImg]));
        
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[view(==30)]", options: NSLayoutConstraint.FormatOptions(rawValue: 0), metrics: nil, views: ["view": chipImg]));
    }
    
    private func createBackLine() {
        //BackLine
        backLine.translatesAutoresizingMaskIntoConstraints = false
        backLine.backgroundColor = backLineColor
        backView.addSubview(backLine)
        
        self.addConstraint(NSLayoutConstraint(item: backLine, attribute: NSLayoutConstraint.Attribute.top, relatedBy: NSLayoutConstraint.Relation.equal, toItem: backView, attribute: NSLayoutConstraint.Attribute.top, multiplier: 1.0, constant: 20));
        
        self.addConstraint(NSLayoutConstraint(item: backLine, attribute: NSLayoutConstraint.Attribute.centerX, relatedBy: NSLayoutConstraint.Relation.equal, toItem: backView, attribute: NSLayoutConstraint.Attribute.centerX, multiplier: 1.0, constant: 0));
        
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[view(==300)]", options: NSLayoutConstraint.FormatOptions(rawValue: 0), metrics: nil, views: ["view": backLine]));
        
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[view(==50)]", options: NSLayoutConstraint.FormatOptions(rawValue: 0), metrics: nil, views: ["view": backLine]));
    }
    
    private func createCVC() {
        //CVC textfield
        cvc.translatesAutoresizingMaskIntoConstraints = false
        cvc.maskExpression = "..."
        cvc.text = "CVC"
        cvc.backgroundColor = .white
        cvc.textAlignment = NSTextAlignment.center
        cvc.isUserInteractionEnabled = false
        if #available(iOS 13.0, *) {
            cvc.backgroundColor = UIColor.systemGray3
        }
        backView.addSubview(cvc)
        
        self.addConstraint(NSLayoutConstraint(item: cvc, attribute: NSLayoutConstraint.Attribute.top, relatedBy: NSLayoutConstraint.Relation.equal, toItem: backLine, attribute: NSLayoutConstraint.Attribute.bottom, multiplier: 1.0, constant: 10));
        
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[view(==50)]", options: NSLayoutConstraint.FormatOptions(rawValue: 0), metrics: nil, views: ["view": cvc]));
        
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[view(==25)]", options: NSLayoutConstraint.FormatOptions(rawValue: 0), metrics: nil, views: ["view": cvc]));
        
        self.addConstraint(NSLayoutConstraint(item: cvc, attribute: NSLayoutConstraint.Attribute.trailing, relatedBy: NSLayoutConstraint.Relation.equal, toItem: backView, attribute: NSLayoutConstraint.Attribute.trailing, multiplier: 1.0, constant: -10));
    }
    
    private func setType(colors: [UIColor], alpha: CGFloat, back: UIColor) {
        UIView.animate(withDuration: 2, animations: { () -> Void in
            self.gradientLayer.colors = [colors[0].cgColor, colors[1].cgColor]
        })
        self.backView.backgroundColor = back
        self.chipImg.alpha = alpha
    }
    
    private func flip() {
        var showingSide = frontView
        var hiddenSide = backView
        if showingBack {
            (showingSide, hiddenSide) = (backView, frontView)
        }
        
        UIView.transition(from:showingSide,
                          to: hiddenSide,
                          duration: 0.7,
                          options: [UIView.AnimationOptions.transitionFlipFromRight, UIView.AnimationOptions.showHideTransitionViews],
                          completion: nil)
    }
    
    public func isAmex() -> Bool {
        return self.amex
    }
    
    public func paymentCardTextFieldDidChange(cardNumber: String? = "", expirationYear: UInt?, expirationMonth: UInt?, cvc: String? = "") {
        self.cardNumber.text = cardNumber
        
        if let expireMonth = expirationMonth, let expireYear = expirationYear {
            self.expireDate.text = NSString(format: "%02ld", expireMonth) as String + "/" + (NSString(format: "%02ld", expireYear) as String)
        }
        
        if expirationMonth == 0 {
            expireDate.text = "MM/YY"
        }
        
        let v = CreditCardValidator()
        self.cvc.text = cvc
        self.amexCVC.text = cvc
        
        guard let cardN = cardNumber else {
            return
        }
        
        if (cardN.count == 0)
        {
            self.cardNumber.maskExpression = "{....} {....} {....} {....}"
        }
        if (cardN.count >= 7 || cardN.count < 4) {
            amex = false
            guard let type = v.type(from: "\(cardN as String?)") else {
                self.brandImageView.image = nil
                if let name = colors["NONE"] {
                    setType(colors: [name[0], name[1]], alpha: 0.5, back: name[0])
                }
                return
            }
            
            // Visa, Mastercard, Amex etc.
            if let name = colors[type.name] {
                if(type.name.lowercased() == "amex".lowercased()){
                    if !amex {
                        self.cardNumber.maskExpression = "{....} {....} {....} {...}"
                        self.cardNumber.text = cardNumber
                        amex = true
                    }
                }
                self.brandImageView.image = UIImage(named: type.name, in: Bundle.currentBundle(), compatibleWith: nil)
                setType(colors: [name[0], name[1]], alpha: 1, back: name[0])
            } else {
                setType(colors: [self.colors["DEFAULT"]![0], self.colors["DEFAULT"]![0]], alpha: 1, back: self.colors["DEFAULT"]![0])
            }
        }
    }
    
    public func paymentCardTextFieldDidEndEditingExpiration(expirationYear: UInt) {
        if "\(expirationYear)".count <= 1 {
            expireDate.text = "MM/YY"
        }
    }
    
    public func paymentCardTextFieldDidBeginEditingCVC() {
        if !showingBack {
            if !amex {
                flip()
                showingBack = true
            }
        }
    }
    
    public func paymentCardTextFieldDidEndEditingCVC() {
        if showingBack {
            flip()
            showingBack = false
        }
    }
    
}

//: CardColors
extension CreditCardFormView {
    
    fileprivate func setBrandColors() {
        colors[Brands.NONE.rawValue] = [defaultCardColor, defaultCardColor]
        colors[Brands.Visa.rawValue] = [UIColor.hexStr(hexStr: "#5D8BF2", alpha: 1), UIColor.hexStr(hexStr: "#3545AE", alpha: 1)]
        colors[Brands.MasterCard.rawValue] = [UIColor.hexStr(hexStr: "#ED495A", alpha: 1), UIColor.hexStr(hexStr: "#8B1A2B", alpha: 1)]
        colors[Brands.UnionPay.rawValue] = [UIColor.hexStr(hexStr: "#987c00", alpha: 1), UIColor.hexStr(hexStr: "#826a01", alpha: 1)]
        colors[Brands.Amex.rawValue] = [UIColor.hexStr(hexStr: "#005B9D", alpha: 1), UIColor.hexStr(hexStr: "#132972", alpha: 1)]
        colors[Brands.JCB.rawValue] = [UIColor.hexStr(hexStr: "#265797", alpha: 1), UIColor.hexStr(hexStr: "#3d6eaa", alpha: 1)]
        colors["Diners Club"] = [UIColor.hexStr(hexStr: "#5b99d8", alpha: 1), UIColor.hexStr(hexStr: "#4186CD", alpha: 1)]
        colors[Brands.Discover.rawValue] = [UIColor.hexStr(hexStr: "#e8a258", alpha: 1), UIColor.hexStr(hexStr: "#D97B16", alpha: 1)]
        colors[Brands.DEFAULT.rawValue] = [UIColor.hexStr(hexStr: "#5D8BF2", alpha: 1), UIColor.hexStr(hexStr: "#3545AE", alpha: 1)]
        
        if cardGradientColors.count > 0 {
            for (_, value) in cardGradientColors.enumerated() {
                colors[value.key] = value.value
            }
        }
    }
}


