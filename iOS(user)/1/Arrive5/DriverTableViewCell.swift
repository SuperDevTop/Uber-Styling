//
//  DriverTableViewCell.swift
//  Arrive5
//
//  Created by parangat2 on 6/12/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class DriverTableViewCell: UITableViewCell {

    @IBOutlet weak var imgDriver: UIImageView!
    @IBOutlet weak var btnSend: UIButton!
    @IBOutlet weak var lblDriverName: UILabel!
    @IBOutlet weak var lblCarCode: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        // UIImage
        imgDriver.layer.cornerRadius = imgDriver.frame.height/2
        imgDriver.clipsToBounds = true
        
        //UIButton
        btnSend.layer.cornerRadius = btnSend.frame.height / 2
        btnSend.clipsToBounds = true
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
