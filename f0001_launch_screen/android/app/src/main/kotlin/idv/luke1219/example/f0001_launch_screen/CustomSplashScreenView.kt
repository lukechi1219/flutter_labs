package idv.luke1219.example.f0001_launch_screen

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewPropertyAnimator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlin.math.roundToLong

class CustomSplashScreenView(context: Context) : FrameLayout(context) {

    private var flutterLogo: ImageView? = null
    private var rotateAnimator: ViewPropertyAnimator? = null

    //rotate animation variables
    private val ANIMATION_TIME_IN_MILLIS = 600L
    private var rotateAngle = -360f

    //fade animation variables
    private val TRANSITION_TIME_IN_MILLIS = 500
    private var transitionPercentWhenAnimationStarted = 0.0f
    private var totalTransitionPercent = 0.0f
    private lateinit var onTransitionComplete: Runnable
    private lateinit var fadeAnimator: ViewPropertyAnimator

    // Listener for rotateAnimator for event onAnimationEnd and onAnimationCancel
    private val rotateAnimatorListener: AnimatorListener = object : AnimatorListenerAdapter() {

        override fun onAnimationEnd(animation: Animator) {
            animation.removeAllListeners()
            rotateAngle -= 360
            animateFlutterLogo()
        }

        override fun onAnimationCancel(animation: Animator) {
            animation.removeAllListeners()
        }
    }

    //update Listener for transittion animator
    private val transitionAnimatorUpdateListener = AnimatorUpdateListener { animation ->
        totalTransitionPercent = transitionPercentWhenAnimationStarted +
                animation.animatedFraction * (1.0f - transitionPercentWhenAnimationStarted)
    }
    private val transitionAnimatorListener: AnimatorListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            animation.removeAllListeners()
            onTransitionComplete.run()
        }
    }

    /**
     * primary constructor
     */
    init {
        setBackgroundColor(Color.parseColor("#FFFFFF"))
        flutterLogo = ImageView(getContext())
        flutterLogo!!.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.launch_background))
        //
        addView(flutterLogo, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER))
        //
        animateFlutterLogo()
    }

    private fun animateFlutterLogo() {
        rotateAnimator = flutterLogo!!
                .animate().rotation(rotateAngle).setDuration(ANIMATION_TIME_IN_MILLIS)
                .setInterpolator(LinearInterpolator())
                .setListener(rotateAnimatorListener)
        //
        rotateAnimator!!.start()
    }

    //transition animation
    fun animateAway(onTransitionComplete: Runnable) {
        this.onTransitionComplete = onTransitionComplete

        fadeAnimator = animate()
                .alpha(0.0f)
                .setDuration((TRANSITION_TIME_IN_MILLIS * (1.0 - totalTransitionPercent)).roundToLong())
                .setUpdateListener(transitionAnimatorUpdateListener)
                .setListener(transitionAnimatorListener)

        fadeAnimator.start()
    }

    /**
     * on leave splash screen
     */
    override fun onDetachedFromWindow() {
        rotateAnimator?.cancel()
        super.onDetachedFromWindow()
    }

    //state saving
    fun saveSplashState(): Bundle? {
        val state = Bundle()
        state.putFloat("totalTransitionPercent", totalTransitionPercent)
        return state
    }

    fun restoreSplashState(bundle: Bundle?) {
        if (bundle != null) {
            transitionPercentWhenAnimationStarted = bundle.getFloat("totalTransitionPercent")
            alpha = 1.0f - transitionPercentWhenAnimationStarted
        }
    }
}
