//
//  FreeRideViewController.swift
//  Arrive5
//
//  Created by Parangat Air 1 on 5/30/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class FreeRideViewController: UIViewController {
    @IBOutlet weak var lblInvite : UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        lblInvite.text = UserDefaults.standard.string(forKey: "invite_code")

        // Do any additional setup after loading the view.
    }
    @IBAction func btnBackPressed (_sender : Any){
        self.navigationController? . popViewController(animated: true)
        
    }

    @IBAction func ShareBtn(_ sender: UIButton)
    {
        let text = lblInvite.text;
        
        // set up activity view controller
        let textToShare = [ text ]
        let activityViewController = UIActivityViewController(activityItems: textToShare, applicationActivities: nil)
        activityViewController.popoverPresentationController?.sourceView = self.view // so that iPads won't crash
        
        // exclude some activity types from the list (optional)
        activityViewController.excludedActivityTypes = [ UIActivity.ActivityType.airDrop, UIActivity.ActivityType.postToFacebook ]
        
        // present the view controller
        self.present(activityViewController, animated: true, completion: nil)
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}
