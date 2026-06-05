package dealroutewd.kiosk.orders.ui.composable.screen.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dealroutewd.kiosk.orders.R
import dealroutewd.kiosk.orders.ui.theme.Accent
import dealroutewd.kiosk.orders.ui.theme.Background
import dealroutewd.kiosk.orders.ui.theme.BodyFamily
import dealroutewd.kiosk.orders.ui.theme.Border
import dealroutewd.kiosk.orders.ui.theme.HeadingFamily
import dealroutewd.kiosk.orders.ui.theme.Muted
import dealroutewd.kiosk.orders.ui.theme.OnSurface
import dealroutewd.kiosk.orders.ui.theme.Primary
import dealroutewd.kiosk.orders.ui.theme.Surface

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            fontFamily = HeadingFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = OnSurface
        )
        Spacer(modifier = Modifier.height(20.dp))

        // About section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "About",
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(12.dp))
                SettingsRow(
                    label = stringResource(R.string.ljztu_settings_screen_company_label),
                    value = stringResource(R.string.ljztu_company_name)
                )
                Divider(color = Border, modifier = Modifier.padding(vertical = 10.dp))
                SettingsRow(
                    label = stringResource(R.string.ljztu_settings_screen_version_label),
                    value = stringResource(R.string.ljztu_app_version)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Support section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Support",
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.ljztu_customer_support_link)))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Accent),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.ljztu_settings_screen_customer_support_label),
                        fontFamily = HeadingFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Primary
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontFamily = BodyFamily,
            fontSize = 14.sp,
            color = Muted,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontFamily = BodyFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = OnSurface
        )
    }
}
