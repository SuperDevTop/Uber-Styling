//
//  ScheduledPickupViewController.swift
//  Arrive5Driver
//
//  Created by Test on 15/09/1941 Saka.
//  Copyright © 1941 Apple Inc. All rights reserved.
//

import UIKit


class ScheduledPickupViewController : UIViewController,CarbonTabSwipeNavigationDelegate{
   
    
  
    var items = NSArray()
    var carbonTabSwipeNavigation: CarbonTabSwipeNavigation = CarbonTabSwipeNavigation()

    @IBOutlet var MyPickuLabel: UILabel!
    
    @IBOutlet var AvailablePickup: UILabel!
    
    @IBOutlet weak var toolBar : UIToolbar!
    @IBOutlet weak var viewTarget : UIView!
    
    
   
    
    override func viewDidLoad() {
        super.viewDidLoad()

        
      
        items = ["Available Pickups","My Pickups"]
        carbonTabSwipeNavigation = CarbonTabSwipeNavigation(items: items as [AnyObject], toolBar: toolBar, delegate: self)
        style()
        carbonTabSwipeNavigation.insert(intoRootViewController: self, andTargetView: viewTarget)
    
        MyPickuLabel.isHidden = false;
        AvailablePickup.isHidden = true;
        
    }
    
    func style() {
//        let color: UIColor = UIColor(red: 24.0 / 255, green: 75.0 / 255, blue: 152.0 / 255, alpha: 1)
        
         let color: UIColor = UIColor.orange
         let color2: UIColor = UIColor.white
//        let color: UIColor = UIColor.orange
        self.navigationController!.navigationBar.isTranslucent = false
        self.navigationController!.navigationBar.tintColor = UIColor.white
        self.navigationController!.navigationBar.barTintColor = UIColor.white
        self.navigationController!.navigationBar.barStyle = .blackTranslucent
        carbonTabSwipeNavigation.toolbar.isTranslucent = false
        carbonTabSwipeNavigation.setIndicatorColor(color)
        carbonTabSwipeNavigation.setTabExtraWidth(0)
        carbonTabSwipeNavigation.carbonSegmentedControl?.setWidth(self.view.frame.size.width/2, forSegmentAt: 0)
        carbonTabSwipeNavigation.carbonSegmentedControl?.setWidth(self.view.frame.size.width/2, forSegmentAt: 1)
        carbonTabSwipeNavigation.setNormalColor(UIColor.white.withAlphaComponent(0.6))
        carbonTabSwipeNavigation.setSelectedColor(color2, font: UIFont.boldSystemFont(ofSize: 14))
    }
    


func carbonTabSwipeNavigation(_ carbonTabSwipeNavigation: CarbonTabSwipeNavigation, viewControllerAt index: UInt) -> UIViewController {
    
    switch index {
    case 0:
        return self.storyboard!.instantiateViewController(withIdentifier: "AvailaBlePIcupScViewController") as! AvailaBlePIcupScViewController
    default:
        return self.storyboard!.instantiateViewController(withIdentifier: "MyPIckupScreenViewController") as! MyPIckupScreenViewController
    }
}

func carbonTabSwipeNavigation(_ carbonTabSwipeNavigation: CarbonTabSwipeNavigation, willMoveAt index: UInt) {
    print("Did move at index: %ld", index)
    }


    @IBAction func SidemenuBtn(_ sender: UIButton)
    {
        let HomeSideMenuVC = self.storyboard?.instantiateViewController(withIdentifier: "HomeSideMenuVC") as! HomeSideMenuVC
        HomeSideMenuVC.PathDirection = "ScheduledPickupViewController"
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
    
    
    @IBAction func AvailablePIckBtn(_ sender: UIButton)
    {
        MyPickuLabel.isHidden = false;
        AvailablePickup.isHidden = true;
    }
    
    @IBAction func MypickupBtns(_ sender: UIButton)
    {
        MyPickuLabel.isHidden = true;
        AvailablePickup.isHidden = false;
    }
    
    

}
