//
//  ViewController.swift
//  MVVM
//
//  Created by V M on 6.03.24.
//

import UIKit

class ViewController: UIViewController {
    @IBOutlet private var flashButton: UIButton!
    @IBOutlet private var  livePgotoButton: UIButton!
    
    var viewModel: CameraViewModelProtocol = CameraViewModel()
    override func viewDidLoad() {
        super.viewDidLoad()
       configureFlashButtton()
        configureLivePhotoBUtton()
    }
    @IBAction func didTapFlashButton(){
        viewModel.isFlashEnabled = !viewModel.isFlashEnabled
        configureFlashButtton()
    }
    @IBAction func didTapLivePhotoButton(){
        viewModel.isLivePhotoEnabled = !viewModel.isLivePhotoEnabled
        configureLivePhotoBUtton()
    }
    private func configureFlashButtton(){
        let systemName = viewModel.isFlashEnabled ? "lightbulb" : "lightbulb.slash"
        flashButton.setImage(UIImage(systemName: systemName), for: .normal)
    }
    private func configureLivePhotoBUtton(){
        let systemName = viewModel.isFlashEnabled ? "livepgoto" : "livephoto.slash"
        livePgotoButton.setImage(UIImage(systemName: systemName), for: .normal)
    }
}

