//
//  YourRidesViewController.swift
//  Arrive5
//
//  Created by Fusion Techware on 25/05/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
class YourRidesViewController: UIViewController,CarbonTabSwipeNavigationDelegate{
    
    var items = NSArray()
    var carbonTabSwipeNavigation: CarbonTabSwipeNavigation = CarbonTabSwipeNavigation()
    
    @IBOutlet weak var toolBar : UIToolbar!
    @IBOutlet weak var viewTarget : UIView!

    override func viewDidLoad() {
        super.viewDidLoad()
        items = ["Past","Upcoming"]
        carbonTabSwipeNavigation = CarbonTabSwipeNavigation(items: items as [AnyObject], toolBar: toolBar, delegate: self)
        style()
        carbonTabSwipeNavigation.insert(intoRootViewController: self, andTargetView: viewTarget)
    }
    
    func style() {
        let color: UIColor = UIColor(red: 24.0 / 255, green: 75.0 / 255, blue: 152.0 / 255, alpha: 1)
        self.navigationController!.navigationBar.isTranslucent = false
        self.navigationController!.navigationBar.tintColor = UIColor.white
        self.navigationController!.navigationBar.barTintColor = color
        self.navigationController!.navigationBar.barStyle = .blackTranslucent
        carbonTabSwipeNavigation.toolbar.isTranslucent = false
        carbonTabSwipeNavigation.setIndicatorColor(color)
        carbonTabSwipeNavigation.setTabExtraWidth(0)
        carbonTabSwipeNavigation.carbonSegmentedControl?.setWidth(self.view.frame.size.width/2, forSegmentAt: 0)
        carbonTabSwipeNavigation.carbonSegmentedControl?.setWidth(self.view.frame.size.width/2, forSegmentAt: 1)
        carbonTabSwipeNavigation.setNormalColor(UIColor.black.withAlphaComponent(0.6))
        carbonTabSwipeNavigation.setSelectedColor(color, font: UIFont.boldSystemFont(ofSize: 14))
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @IBAction func btnBackPressed(_sender : Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
    
    func carbonTabSwipeNavigation(_ carbonTabSwipeNavigation: CarbonTabSwipeNavigation, viewControllerAt index: UInt) -> UIViewController {
        
        switch index {
        case 0:
            return self.storyboard!.instantiateViewController(withIdentifier: "PastRideViewController") as! PastRideViewController
        default:
            return self.storyboard!.instantiateViewController(withIdentifier: "UpcomingViewController") as! UpcomingViewController
        }
    }
    
    func carbonTabSwipeNavigation(_ carbonTabSwipeNavigation: CarbonTabSwipeNavigation, willMoveAt index: UInt) {
        print("Did move at index: %ld", index)
    }
}
