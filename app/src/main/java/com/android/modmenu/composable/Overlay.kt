package com.android.modmenu.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.modmenu.ui.theme.AndroidmodmenuTheme

@Composable
fun Overlay(onClick: () -> Unit) {
    AndroidmodmenuTheme(isOverlay = true) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize()
            ) {
                var showHello by remember { mutableStateOf(true) }
                if (showHello) {
                    Text(text = "Hello from Compose")
                }
                Button(onClick = {
                    onClick()
                    showHello = false
                }) {
                    Text("Tap me for a little surprise!")
                }
            }
        }
    }
}

@Preview
@Composable
private fun OverlayPreview() {
    Overlay(onClick = {})
}