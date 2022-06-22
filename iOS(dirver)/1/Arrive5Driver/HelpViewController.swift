//
//  HelpViewController.swift
//  Arrive5Driver
//
//  Created by Test on 11/09/1941 Saka.
//  Copyright © 1941 Apple Inc. All rights reserved.
//

import UIKit

class HelpViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    @IBAction func ReportBtn(_ sender: UIButton)
    {
        let myTripVC = self.storyboard?.instantiateViewController(withIdentifier: "MyTripVC") as! MyTripVC
        self.navigationController?.pushViewController(myTripVC, animated: true)
    }
    @IBAction func btnActionBack(_ sender: UIButton)
    {
        let HomeSideMenuVC = self.storyboard?.instantiateViewController(withIdentifier: "HomeSideMenuVC") as! HomeSideMenuVC
        HomeSideMenuVC.PathDirection = "HelpViewController"
        addChild(HomeSideMenuVC)
        
        HomeSideMenuVC.didMove(toParent: self)
        
        UIView.animate(withDuration: 0.5, delay: 1.0, usingSpringWithDamping: 0.1, initialSpringVelocity: 0.0, options: .transitionFlipFromLeft, animations: {
            HomeSideMenuVC.view.frame = CGRect(x: self.view.frame.minX,
                                               y: 0,
                                               width: self.view.frame.size.width ,
                                               height: self.view.frame.size.height)
        }, completion: {(bool) in
            self.view.addSubview(HomeSideMenuVC.view)
            HomeSideMenuVC.view.frame = self.view.bounds
        })
}
}




/*
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

 }*/
