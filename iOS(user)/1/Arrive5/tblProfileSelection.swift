//
//  tblProfileSelection.swift
//  Arrive5
//
//  Created by Joy on 17/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class tblProfileSelection: UITableViewCell {

    @IBOutlet weak var btnSelectedVal: UIButton!
    @IBOutlet weak var lblProfileSubtype: UILabel!
    @IBOutlet weak var lblProfileHeader: UILabel!
    @IBOutlet weak var ivProfileVIew: UIImageView!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
