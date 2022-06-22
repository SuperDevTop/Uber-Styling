//
//  MyTripsTableViewCell.swift
//  Arrive5Driver
//
//  Created by parangat2 on 6/14/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class MyTripsTableViewCell: UITableViewCell {

    @IBOutlet weak var imgCustomer: UIImageView!
    @IBOutlet weak var lblCustomerName: UILabel!
    @IBOutlet weak var lblAddressFrom: UILabel!
    @IBOutlet weak var lblAddressTo: UILabel!
    @IBOutlet weak var btnOngoing: UIButton!
    @IBOutlet weak var btnRejected: UIButton!
    @IBOutlet weak var btnCompleted: UIButton!
    @IBOutlet weak var btnReportTrip: UIButton!
    @IBOutlet weak var heightStackView: NSLayoutConstraint!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        // UIImage
        imgCustomer.layer.cornerRadius = imgCustomer.frame.height/2
        imgCustomer.clipsToBounds = true
        
        //UIButton
        btnOngoing.layer.cornerRadius = btnOngoing.frame.height / 2
        btnOngoing.clipsToBounds = true
        btnRejected.layer.cornerRadius = btnRejected.frame.height / 2
        btnRejected.clipsToBounds = true
        btnCompleted.layer.cornerRadius = btnCompleted.frame.height / 2
        btnCompleted.clipsToBounds = true
        btnReportTrip.layer.cornerRadius = btnReportTrip.frame.height / 2
        btnReportTrip.clipsToBounds = true
        btnReportTrip.layer.borderColor = UIColor(red: 240/255.0, green: 172/255.0, blue: 102/255.0, alpha: 1.0).cgColor
        btnReportTrip.layer.borderWidth = 1
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }

}
