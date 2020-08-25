package idv.luke1219.example.f0001_launch_screen

import android.content.Context
import android.os.Bundle
import android.view.View
import io.flutter.embedding.android.SplashScreen


class SplashScreenWithTransition : SplashScreen {
    private var splashView: CustomSplashScreenView? = null

    override fun createSplashView(
            context: Context,
            savedInstanceState: Bundle?
    ): View? {
        // A reference to the MySplashView is retained so that it can be told
        // to transition away at the appropriate time.
        if (splashView == null) {
            splashView = CustomSplashScreenView(context)
            splashView!!.restoreSplashState(savedInstanceState)
        }
        return splashView
    }

    override fun transitionToFlutter(onTransitionComplete: Runnable) {
        // Instruct MySplashView to animate away in whatever manner it wants.
        // The onTransitionComplete Runnable is passed to the MySplashView
        // to be invoked when the transition animation is complete.
        splashView?.animateAway(onTransitionComplete)
    }

    //state saving
    override fun doesSplashViewRememberItsTransition(): Boolean {
        return true
    }

    override fun saveSplashScreenState(): Bundle? {
        return splashView?.saveSplashState()
    }
}
