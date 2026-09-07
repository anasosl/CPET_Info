package lima.sofia.ana.estoque

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "EstoqueBelezaApp",
    ) {
        App()
    }
}