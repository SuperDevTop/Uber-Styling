//
//  Signup1.swift
//  Arrive5
//
//  Created by Rahul on 28/09/18.
//  Copyright © 2018 Apple Inc. All rights reserved.
//

import UIKit

class Signup1: UIViewController ,UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    @IBOutlet var userImage: UIImageView!
    @IBOutlet var txtFirstName: UITextField!
    @IBOutlet var txtLastName: UITextField!
    
    
    // MARK: - Properties
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    let picker = UIImagePickerController()
    let ImageAlert = UIAlertController()
    var ImageFilePath : String!
    var ImageDataWork : Data!
    
    // MARK: - VCCycles

    override func viewDidLoad() {
        super.viewDidLoad()
        
        ImageAlert.title = "Choose one of the following:"
        ImageAlert.message = ""
        picker.delegate = self
        picker.allowsEditing = true
        self.ImagePopUp()

    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        self.userImage.layer.masksToBounds = true
        self.userImage.layer.cornerRadius = self.userImage.frame.height/2
        
    }
    
    
    
    
    
    @IBAction func btnBackAction(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func gallaryBtn(_ sender: Any) {
        self.present(ImageAlert, animated: true, completion: {
            self.ImageAlert.view.superview?.isUserInteractionEnabled = true
            self.ImageAlert.view.superview?.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.alertClose(gesture:))))
        })
        
        
    }
    
    
    @IBAction func nextPress(_ sender: Any) {
        if self.txtFirstName.text == ""{
            self.view.makeToast("Enter user first name")
        }else if self.txtLastName.text == ""{
            self.view.makeToast("Enter user last name")
        }else{
            appDelegate.tfFirstName = self.txtFirstName.text
            appDelegate.tfLastName = self.txtLastName.text
            let signup2 = self.storyboard?.instantiateViewController(withIdentifier: "Signup2") as! Signup2
            self.navigationController?.pushViewController(signup2, animated: true)
        }
    }
    
    
    func ImagePopUp(){
        
        
        ImageAlert.addAction(UIAlertAction(title: "Photos", style: .default, handler: { action in
            self.picker.sourceType = .photoLibrary
            self.present(self.picker, animated: true, completion: nil)
        }))
        ImageAlert.addAction(UIAlertAction(title: "Camera", style: .destructive, handler: { action in
            self.picker.sourceType = .camera
            self.present(self.picker, animated: true, completion: nil)
            
        }))
        ImageAlert.dismiss(animated: true, completion: nil)
    }
    
  @objc func alertClose(gesture: UITapGestureRecognizer) {
        self.dismiss(animated: true, completion: nil)
    }
    
    
    
    // MARK: - Delegates
    // MARK: -
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {

        
        if let pickedImage = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
            var paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)
            let documentsDirectory = paths[0] as NSString
            ImageFilePath = documentsDirectory.appendingPathExtension("jpg")
//            ImageDataWork = UIImageJPEGRepresentation(pickedImage, 0.2)
            ImageDataWork =  pickedImage.jpegData(compressionQuality: 0.2)

            appDelegate.dataImageDataWork = pickedImage.jpegData(compressionQuality: 0.2)
            let dataURL = URL(fileURLWithPath: ImageFilePath)
            
            do{
                try ImageDataWork.write(to: dataURL, options: [.atomic])
            }catch{
                //process errors
            }
            userImage.contentMode = .scaleToFill
            userImage.image = pickedImage
            
        }
        ImageAlert.dismiss(animated: true, completion: nil)
        dismiss(animated: true, completion: nil)
    }
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        dismiss(animated: true, completion: nil)
        ImageAlert.dismiss(animated: true, completion: nil)
    }
    
    
    
    
    

}
