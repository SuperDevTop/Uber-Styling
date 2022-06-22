//
//  MaterialDesign.swift
//  Arrive5Driver
//
//  Created by Joy on 09/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

private var materialKey = false

extension UIView {
    
    @IBInspectable var maerialDesign: Bool{
        
        get{
            return materialKey
        }
        set{
            materialKey = newValue
            if materialKey{
                self.layer.masksToBounds = false
                self.layer.cornerRadius = 3.0
                self.layer.shadowOpacity = 0.4
                self.layer.shadowRadius = 1.0
                self.layer.shadowOffset = CGSize(width: 0.0, height: 1.0)
                self.layer.shadowColor = UIColor(red: 157/255, green: 157/255, blue: 157/255, alpha: 1.0).cgColor
                
            }else {
                self.layer.cornerRadius = 0.0
                self.layer.shadowOpacity = 0.0
                self.layer.shadowRadius = 0.0
                self.layer.shadowColor = nil
            }
        }
    }
}
