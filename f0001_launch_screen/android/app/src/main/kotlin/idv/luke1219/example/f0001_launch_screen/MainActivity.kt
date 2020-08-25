package idv.luke1219.example.f0001_launch_screen

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.SplashScreen

class MainActivity : FlutterActivity() {
    override fun provideSplashScreen(): SplashScreen? {
        return SplashScreenWithTransition()
    }
}
