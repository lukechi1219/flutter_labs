import UIKit
import Flutter

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {

    var launchWindow: UIWindow? //启动页
    
    override func application(_ application: UIApplication,
      didFinishLaunchingWithOptions launchOptions:
        [UIApplication.LaunchOptionsKey: Any]? ) -> Bool {

        launchWindow = UIWindow.init(frame: UIScreen.main.bounds)
        launchWindow?.rootViewController = UIStoryboard.init(name: "LaunchScreen", bundle: nil).instantiateInitialViewController()

        launchWindow?.windowLevel = .normal + 1
        launchWindow?.isHidden = false

        UIView.animate(withDuration: 3,
                       delay: 0,
                       animations: {

                        (self.launchWindow?.viewWithTag(1) as? UIImageView)?
                            .transform =
                            CGAffineTransform(rotationAngle: .pi)

                        (self.launchWindow?.viewWithTag(1) as? UIImageView)?
                            .transform = .identity
                        //        self.launchWindow?.alpha = 0.0
        }) { (finish) in
            if finish {
                self.launchWindow?.isHidden = true
                self.launchWindow?.windowLevel = .normal - 1
                self.launchWindow?.alpha = 1.0
            }
        }

        window = UIWindow.init()
        window?.frame = UIScreen.main.bounds
        window?.rootViewController = UIStoryboard.init(name: "Main", bundle: nil).instantiateInitialViewController()

        GeneratedPluginRegistrant.register(with: self)

        return super.application(application, didFinishLaunchingWithOptions: launchOptions)
    }
}

