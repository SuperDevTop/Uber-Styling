//
//  RatingTableViewCell.swift
//  Arrive5
//
//  Created by parangat2 on 6/13/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class RatingTableViewCell: UITableViewCell {

    @IBOutlet weak var imgDriver: UIImageView!
    @IBOutlet weak var lblDriverName: UILabel!
    @IBOutlet weak var lblRating: UILabel!
    @IBOutlet weak var ratingView: CosmosView!
    @IBOutlet weak var parentView: UIView!
    @IBOutlet weak var bottomTocontentView: NSLayoutConstraint!
    var commentListArr: [String] = []
    var arrCount : Int = 0
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        // UIImage
        imgDriver.layer.cornerRadius = imgDriver.frame.height/2
        imgDriver.clipsToBounds = true
        
        //CosmosView
//        ratingView.settings.fillMode = hal

    }


    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
}
