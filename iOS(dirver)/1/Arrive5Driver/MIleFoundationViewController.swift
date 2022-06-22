//
//  MIleFoundationViewController.swift
//  Arrive5Driver
//
//  Created by Test on 12/09/1941 Saka.
//  Copyright © 1941 Apple Inc. All rights reserved.
//

import UIKit

class MIleFoundationViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    @IBAction func BackBtn(_ sender: UIButton)
    {
        self.navigationController?.popViewController(animated: true)
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
