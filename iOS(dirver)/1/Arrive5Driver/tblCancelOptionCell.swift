//
//  tblCancelOptionCell.swift
//  Arrive5Driver
//
//  Created by Joy on 02/05/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class tblCancelOptionCell: UITableViewCell {

    @IBOutlet weak var lblCancelOptionVal: UILabel!
    @IBOutlet weak var ivCancelSelected: UIImageView!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
