//
//  SelectIssueTableViewCell.swift
//  Arrive5
//
//  Created by Test on 06/10/1941 Saka.
//  Copyright © 1941 Apple Inc. All rights reserved.
//

import UIKit

class SelectIssueTableViewCell: UITableViewCell {

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    @IBOutlet var checkboximage: UIImageView!
    
    @IBOutlet var issueLabel: UILabel!
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
