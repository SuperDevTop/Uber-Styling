//
//  PromoCell.swift
//  Arrive5
//
//  Created by Rahul on 08/08/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class PromoCell: UITableViewCell {
    @IBOutlet var lblone: UILabel!
    
    @IBOutlet var lbltwo: UILabel!
    
    @IBOutlet var lblThree: UILabel!
    
    @IBOutlet var subView: UIView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        // Initialization code
        //subView.layer.shadowOpacity = 0.2
        //subView.layer.shadowRadius = 2
        //subView.layer.shadowColor = UIColor.black.cgColor
        subView.layer.borderWidth = 1
        subView.layer.borderColor = UIColor.blue.cgColor
        subView.layer.cornerRadius = 5
        subView.layer.masksToBounds = false
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
