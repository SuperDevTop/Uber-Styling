//
//  NotificationTableViewCell.swift
//  Arrive5Driver
//
//  Created by Test on 12/09/1941 Saka.
//  Copyright © 1941 Apple Inc. All rights reserved.
//

import UIKit

class NotificationTableViewCell: UITableViewCell {

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    @IBOutlet var NotificationDate: UILabel!
    @IBOutlet var NotificationLabel: UILabel!
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
