package com.android.modmenu.overlay

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Myviewmodel : ViewModel() {
    private val _gety = MutableStateFlow(0)
    val gety: StateFlow<Int> = _gety
    private val _getx = MutableStateFlow(100)
    val getx: StateFlow<Int> = _getx

    fun setOffsetX(offsetX: Int) {
        _getx.value = offsetX
    }

    fun setOffsetY(offsetY: Int) {
        _gety.value = offsetY
    }
}
