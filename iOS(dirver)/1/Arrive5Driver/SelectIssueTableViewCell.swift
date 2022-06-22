//
//  SelectIssueTableViewCell.swift
//  Arrive5Driver
//
//  Created by parangat2 on 6/15/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class SelectIssueTableViewCell: UITableViewCell {

    @IBOutlet weak var lblReason: UILabel!
    @IBOutlet weak var btnSelectUnselectReason: UIButton!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        btnSelectUnselectReason.layer.cornerRadius = btnSelectUnselectReason.frame.height / 2
        btnSelectUnselectReason.clipsToBounds = true
        btnSelectUnselectReason.layer.borderColor = UIColor.darkGray.cgColor
        btnSelectUnselectReason.layer.borderWidth = 1
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
