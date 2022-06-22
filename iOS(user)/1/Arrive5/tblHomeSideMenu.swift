//
//  tblHomeSideMenu.swift
//  Arrive5
//
//  Created by Joy on 05/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class tblHomeSideMenu: UITableViewCell {
    
    @IBOutlet weak var ivSIdeMenu: UIImageView!
    @IBOutlet weak var lblSideMenu: UILabel!
    @IBOutlet weak var vwHighlighted: UIView!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
