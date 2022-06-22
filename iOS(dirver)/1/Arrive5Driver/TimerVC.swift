//
//  TimerVC.swift
//  Arrive5Driver
//
//  Created by Joy on 25/04/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit
import AVFoundation


class TimerVC: UIViewController {

    @IBOutlet weak var lblDriverRating: UILabel!
    @IBOutlet weak var lblVehicleType: UILabel!
    @IBOutlet weak var lblDistToReach: UILabel!
    @IBOutlet weak var lblDistanceTime: UILabel!
    @IBOutlet weak var lblCountVal: CountdownLabel!
    @IBOutlet weak var vwMainView: UIView!
    @IBOutlet weak var btnAccept: UIButton!
    @IBOutlet weak var btnCancel: UIButton!
    
    var timer: DispatchSourceTimer?
//    var bookingId : Int = 0
    var bookingId : String = ""
    var end_point : String = ""
    var user_rating : String = ""
    var duration : String = ""
    var vehicleSubTypeName : String = ""
    let systemSoundID: SystemSoundID = 1016
    private var audioPlayer: AVAudioPlayer?
    var shifting : String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
        self.btnCancel.layer.borderColor = UIColor.darkGray.cgColor
        self.btnCancel.layer.borderWidth = 1.0
        self.btnCancel.layer.cornerRadius = 5.0
        self.btnAccept.layer.cornerRadius = 5.0
        self.vwMainView.layer.cornerRadius = self.vwMainView.frame.height/2
        self.vwMainView.layer.masksToBounds = true
        
        self.lblDriverRating.text = self.user_rating
        self.lblVehicleType.text = self.vehicleSubTypeName
        self.lblDistToReach.text = self.end_point
        self.lblDistanceTime.text = self.duration
        lblCountVal.countdownDelegate = self
        lblCountVal.setCountDownTime(minutes: 20)
        lblCountVal.timeFormat = "ss"
        lblCountVal.animationType = .Scale
        lblCountVal.start()
        self.startTimer()        
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(true)
        self.stopTimer()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func startTimer() {
        let queue = DispatchQueue(label: "DispatchQueue.main")  // you can also use `DispatchQueue.main`, if you want
        timer = DispatchSource.makeTimerSource(queue: queue)
        timer!.scheduleRepeating(deadline: .now(), interval: .seconds(1))
        timer!.setEventHandler { [weak self] in
            // do whatever you want here
            let alertSound = URL(fileURLWithPath: Bundle.main.path(forResource: "beep", ofType: "wav")!)
            print(alertSound)
            
            try! AVAudioSession.sharedInstance().setCategory(AVAudioSession.Category.playback)
            try! AVAudioSession.sharedInstance().setActive(true)
            
            try! self?.audioPlayer = AVAudioPlayer(contentsOf: alertSound)
            self?.audioPlayer!.prepareToPlay()
            self?.audioPlayer!.play()
            
        }
        timer!.resume()
    }
    
    func stopTimer() {
        timer?.cancel()
        self.audioPlayer?.stop()
        timer = nil
        //        timer.isValid = false
    }
    deinit {
        self.stopTimer()
    }
    
    @IBAction func btnCancelAction(_ sender: UIButton) {
        self.callAccept_RejectApi("reject")
    }
    
    @IBAction func btnAcceptAction(_ sender: UIButton) {
        self.callAccept_RejectApi("accept")
    }
    
    func callAccept_RejectApi(_ mode:String){
        
        let usrId  = UserDefaults.standard.value(forKey: "user_id") as! String
        let aStrApi = "\(Constant.API.kAcceptReject)"
        let aDictParam : [String:Any]!
        aDictParam = ["driver_id":usrId,
                      "booking_id":bookingId,
                      "mode":mode]
        
        APIManager.requestPOSTURL(aStrApi, params: aDictParam as [String : AnyObject]?, headers: nil, success: {(usrDetail) in
            if usrDetail["status"].rawString() == "true"{
                if usrDetail["message"].rawString() == "Driver reject booking request"{
                    self.navigationController?.popToRootViewController(animated: false)
                }else{
                    if mode == "accept"{
                        self.lblCountVal.pause()
                        self.stopTimer()
                        let ArriveClientVC = self.storyboard?.instantiateViewController(withIdentifier: "ArriveClientVC") as! ArriveClientVC
                        self.shifting = "true"
                         UserDefaults.standard.set("2", forKey: "check")
                        self.navigationController?.pushViewController(ArriveClientVC, animated: true)
                    }else{
                        //
                        
                    }
                }
                
                
            }else{
                self.view.makeToast(usrDetail["message"].rawString())
                self.navigationController?.popToRootViewController(animated: false)
            }
            print(usrDetail)
        }, failure: {(error) in
            self.view.makeToast(error.localizedDescription)
            print(error)
            
        })
    }

}

extension TimerVC: CountdownLabelDelegate {
    func countdownFinished() {
        debugPrint("countdownFinished at delegate.")
        if self.shifting == "true"{
        }else{
            self.stopTimer()
            
            self.callAccept_RejectApi("reject")
        }
    }
}

