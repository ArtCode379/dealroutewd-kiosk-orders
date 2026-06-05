package dealroutewd.kiosk.orders.ui.composable.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dealroutewd.kiosk.orders.R
import dealroutewd.kiosk.orders.ui.theme.Accent
import dealroutewd.kiosk.orders.ui.theme.HeadingFamily
import dealroutewd.kiosk.orders.ui.theme.Primary
import dealroutewd.kiosk.orders.ui.viewmodel.LJZTUSplashVM
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: LJZTUSplashVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
) {
    val onboardedState by viewModel.onboardedState.collectAsStateWithLifecycle()

    val pulseScale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        repeat(2) {
            pulseScale.animateTo(1.4f, animationSpec = tween(600))
            pulseScale.animateTo(1f, animationSpec = tween(400))
        }
    }

    LaunchedEffect(onboardedState) {
        delay(1500)
        if (onboardedState) {
            onNavigateToHomeScreen()
        } else {
            onNavigateToOnboarding()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Accent),
        contentAlignment = Alignment.Center
    ) {
        // Pulse ring
        Box(
            modifier = Modifier
                .size(160.dp)
                .scale(pulseScale.value)
                .background(
                    color = Primary.copy(alpha = 0.25f),
                    shape = CircleShape
                )
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = "Deal Route logo",
                modifier = Modifier.size(96.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "DEAL ROUTE",
                fontFamily = HeadingFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                letterSpacing = 4.sp,
                color = Primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Street Retail Redefined",
                fontFamily = HeadingFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = 2.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}
