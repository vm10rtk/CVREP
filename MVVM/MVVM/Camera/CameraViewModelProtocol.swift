//
//  CameraViewModelProtocol.swift
//  MVVM
//
//  Created by V M on 6.03.24.
//

import UIKit

protocol CameraViewModelProtocol {
    var isFlashEnabled: Bool {get set}
    var isLivePhotoEnabled: Bool{get set}
    var isWideSreenAvailable:Bool{get}
    var isWideSreenEnabled: Bool{get set}
    var isCameraFlipAvailable: Bool{get}
    func GetAvailableCameraModels()->[CameraMode]
    func GetRecentImage()->UIImage?
    
}
