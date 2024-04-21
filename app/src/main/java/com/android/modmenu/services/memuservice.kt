package com.android.modmenu.services

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


class memuservice : Service() {

    override fun onCreate() {
        super.onCreate()
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        val rootView = FrameLayout(this)
        val composeView = ComposeView(this)
        rootView.addView(composeView)

        val uiHandler = Handler(Looper.getMainLooper())

        // Add a lifecycle observer to handle setup when the service is created
        memuservice.addObserver(object : LifecycleObserver {
            @Composable
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate() {
                uiHandler.post {
                    val rootComposable = @androidx.compose.runtime.Composable {
                        Icon(Icons.Default.AccountCircle, contentDescription = null)
                    }

                    composeView.setContent {
                        rootComposable()
                    }
                }
            }
        })
        windowManager.addView(rootView, layoutParams)
    }

    override fun onBind(intent: Intent?): IBinder? = null
    companion object {
        fun addObserver(lifecycleObserver: LifecycleObserver) {

        }
    }

}