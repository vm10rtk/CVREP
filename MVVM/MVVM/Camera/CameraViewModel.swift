//
//  CameraViewModel.swift
//  MVVM
//
//  Created by V M on 6.03.24.
//
import Foundation
import UIKit
import AVFoundation

class CameraViewModel {
    let userDefaults = UserDefaults.standard
    let dbManager  = DBService()
}
//MARK: - CameraViewModelProtocol
extension CameraViewModel: CameraViewModelProtocol {
    var isFlashEnabled: Bool {
        get {
            userDefaults.bool(forKey: "IsFlashEnabled")
        }
        set {
            userDefaults.setValue(newValue, forKey: "IsFlashEnabled")
        }
    }
    
    var isLivePhotoEnabled: Bool {
        get {
            userDefaults.bool(forKey: "IsLivePhotoEnabled")
        }
        set {
            userDefaults.setValue(newValue, forKey: "IsLivePhotoEnabled")
        }
    }
    
    var isWideSreenAvailable: Bool {
        if let _ = AVCaptureDevice.default(.builtInWideAngleCamera, for: .video,position: .back){
            return true
        }
        else {
            return false
        }
    }
    
    var isWideSreenEnabled: Bool {
        get {
            userDefaults.bool(forKey: "IsWideSreenEnabled")
        }
        set {
            userDefaults.setValue(newValue, forKey: "IsWideSreenEnabled")
        }
    }
    
    var isCameraFlipAvailable: Bool {
        if #available(iOS 3.0, *){
            return true
        }
        else {
            return false
        }
    }
    
    func GetAvailableCameraModels() -> [CameraMode] {
        var modes = [CameraMode]()
        modes.append(.photo)
        modes.append(.video)
        if let _ = AVCaptureDevice.default(.builtInDualWideCamera, for: .video, position: .back){
            modes.append(.portrait)
        }
        if #available(iOS 11.0, *){
            modes.append(.SlowMotion)
            
        }
        modes.append(.panorama)
        return modes
    }
    
    func GetRecentImage() -> UIImage? {
        dbManager.getRecentImage()
    }
    
    
}
