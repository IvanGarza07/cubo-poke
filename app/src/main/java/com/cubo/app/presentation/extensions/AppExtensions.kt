package com.cubo.app.presentation.extensions

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.cubo.app.BuildConfig
import com.cubo.app.R
import com.cubo.app.presentation.data.enums.EventEnum
import com.cubo.app.presentation.data.models.AppEvent
import com.cubo.app.presentation.utils.EventUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

fun initLogger() {
    val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
        .showThreadInfo(true) // (Optional) Whether to show thread info or not. Default true
        .methodCount(1) // (Optional) How many method line to show. Default 2
        .methodOffset(5) // Set methodOffset to 5 in order to hide internal method calls
        .tag("") // To replace the default PRETTY_LOGGER tag with a dash (-).
        .build()

    Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    Timber.plant(object : Timber.DebugTree() {
        override fun log(
            priority: Int, tag: String?, message: String, t: Throwable?
        ) {
            Logger.log(priority, "-$tag", message, t)
        }
    })
}

fun Activity.showSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // show app content in fullscreen, i. e. behind the bars when they are shown (alternative to
        // deprecated View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.setDecorFitsSystemWindows(false)
        // finally, show the system bars
        window.insetsController?.show(WindowInsets.Type.systemBars())
    } else {
        // Shows the system bars by removing all the flags
        // except for the ones that make the content appear under the system bars.
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}

fun Activity.hideSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.let {
            // Default behavior is that if navigation bar is hidden, the system will "steal" touches
            // and show it again upon user's touch. We just want the user to be able to show the
            // navigation bar by swipe, touches are handled by custom code -> change system bar behavior.
            // Alternative to deprecated SYSTEM_UI_FLAG_IMMERSIVE.
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // make navigation bar translucent (alternative to deprecated
            // WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            // - do this already in hideSystemUI() so that the bar
            // is translucent if user swipes it up
            //window.navigationBarColor = getColor(R.color.internal_black_semitransparent_light)
            // Finally, hide the system bars, alternative to View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            // and SYSTEM_UI_FLAG_FULLSCREEN.
            it.hide(WindowInsets.Type.systemBars())
        }
    } else {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
                // Do not let system steal touches for showing the navigation bar
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        // Keep the app content behind the bars even if user swipes them up
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        // make navbar translucent - do this already in hideSystemUI() so that the bar
        // is translucent if user swipes it up
        @Suppress("DEPRECATION")
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }
}

fun Activity.updateStatusBarColor(color: Int) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        @Suppress("DEPRECATION")
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
    window.statusBarColor = ContextCompat.getColor(this, color)
}

fun Activity.updateNavBarColor(color: Int) {
    window.navigationBarColor = ContextCompat.getColor(this, color)
}

inline fun <T> Fragment.liveDataObserver(liveData: LiveData<T>?, crossinline func: (T) -> (Unit)) {
    liveData?.observe(viewLifecycleOwner) { t -> func(t) }
}

inline fun <T> AppCompatActivity.liveDataObserver(
    liveData: LiveData<T>?,
    crossinline func: (T) -> (Unit)
) {
    liveData?.observe(this) { t -> func(t) }
}

fun String.logV(tag: String = "hugoTAG") {
    if (BuildConfig.DEBUG)
        Timber.tag(tag).v(this)
}

fun String.logE(tag: String = "hugoTAG") {
    if (BuildConfig.DEBUG)
        Timber.tag(tag).e(this)
}

inline fun <reified T> getKoinInstance(): T {
    return object : KoinComponent {
        val value: T by inject()
    }.value
}

fun FragmentActivity.initBackPressedToFinishHost(viewLifecycleOwner: LifecycleOwner) {
    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }
    this.onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        onBackPressedCallback
    )
}

fun Throwable.chooseViewModelError() {
    val error = when (this) {
        is IOException -> R.string.io_exception_error
        is HttpException -> this.message()
        else -> R.string.general_exception_error
    }
    EventUtils.publish(AppEvent(EventEnum.VIEW_MODEL_ERROR, error))
}