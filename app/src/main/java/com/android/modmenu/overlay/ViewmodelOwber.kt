package com.android.modmenu.overlay

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

object ViewmodelOwber : ViewModelStoreOwner {
    override val viewModelStore: ViewModelStore
        get() = ViewModelStore()

}