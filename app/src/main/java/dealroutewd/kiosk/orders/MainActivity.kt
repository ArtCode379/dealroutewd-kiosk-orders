package dealroutewd.kiosk.orders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dealroutewd.kiosk.orders.ui.composable.approot.AppRoot
import dealroutewd.kiosk.orders.ui.theme.ProductAppLJZTUTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductAppLJZTUTheme {
                AppRoot()
            }
        }
    }
}