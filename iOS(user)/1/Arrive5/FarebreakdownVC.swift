//
//  FarebreakdownVC.swift
//  Arrive5
//
//  Created by Joy on 17/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class FarebreakdownVC: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnCloseFarebreakdown(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
}
