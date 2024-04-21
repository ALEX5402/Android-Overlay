package com.android.modmenu.overlay

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.android.modmenu.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking

class OverlayService : Service() {

    private val windowManager get() = getSystemService(WINDOW_SERVICE) as WindowManager

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_Androidmodmenu)
        showOverlay()
    }

    // https://developer.android.com/reference/android/view/WindowManager.LayoutParams
    // alt: WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
    // WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    // WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE

    @SuppressLint("ClickableViewAccessibility", "StateFlowValueCalledInComposition")
    private fun showOverlay() {
        val _gety = MutableStateFlow(0)
        val gety: StateFlow<Int> = _gety
        val _getx = MutableStateFlow(100)
        val getx: StateFlow<Int> = _getx


        var initialX: Int
        var initialY: Int
        var initialTouchX: Float
        var initialTouchY: Float
        val layoutFlag: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        val LAYOUT_FLAG2 = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            LAYOUT_FLAG2,
            PixelFormat.TRANSLUCENT
        )

        params.x = getx.value
        params.y = gety.value

        val composeView = ComposeView(this)


        composeView.setContent {
            var offset by remember { mutableStateOf(Offset.Zero) }


            Box (modifier = Modifier
                .pointerInput(Unit) {
                    // Handle touch events
                    detectTransformGestures { centroid, panDelta, zoom, rotation ->
                        offset += panDelta

                    }
                }
                .offset(offset.x.dp, offset.y.dp)
                .height(40.dp)
                .width((40.dp))
                .shadow(
                    elevation = 20.dp,
                    shape = CircleShape
                )
                .background(Color.Red, shape = RoundedCornerShape(100))
                .clickable(
                    enabled = true,
                    onClick = {

                        Toast
                            .makeText(this, "Oh You just touch the balls haha ", Toast.LENGTH_LONG)
                            .show()
                    }
                )
            ){

            }
            _getx.value = dpToPx(offset.x.dp) // may be this is useless
            _gety.value = dpToPx(offset.y.dp)

        }

        val viewModelStoreOwner = ViewmodelOwber

        val lifecycleOwner = lifesycleowner()
        lifecycleOwner.performRestore(null)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        composeView.setViewTreeLifecycleOwner(lifecycleOwner)
        composeView.setViewTreeViewModelStoreOwner(viewModelStoreOwner)
        composeView.setViewTreeSavedStateRegistryOwner(lifecycleOwner)

        // This is required or otherwise the UI will not recompose
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        windowManager.addView(composeView, params)

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    fun dpToPx(dp: Dp): Int {
        return dp.value.toInt()
    }

}