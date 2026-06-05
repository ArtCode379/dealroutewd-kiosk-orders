package dealroutewd.kiosk.orders.ui.composable.screen.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dealroutewd.kiosk.orders.R
import dealroutewd.kiosk.orders.ui.theme.Accent
import dealroutewd.kiosk.orders.ui.theme.BodyFamily
import dealroutewd.kiosk.orders.ui.theme.HeadingFamily
import dealroutewd.kiosk.orders.ui.theme.Primary
import dealroutewd.kiosk.orders.ui.viewmodel.LJZTUOnboardingVM
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

data class OnboardingContent(
    @field:StringRes val titleRes: Int,
    @field:StringRes val descriptionRes: Int,
    @field:DrawableRes val imageRes: Int
)

private val onboardingPagesContent = listOf(
    OnboardingContent(
        titleRes = R.string.ljztu_page_1_title,
        descriptionRes = R.string.ljztu_page_1_description,
        imageRes = R.drawable.onboarding_1,
    ),
    OnboardingContent(
        titleRes = R.string.ljztu_page_2_title,
        descriptionRes = R.string.ljztu_page_2_description,
        imageRes = R.drawable.onboarding_2,
    ),
    OnboardingContent(
        titleRes = R.string.ljztu_page_3_title,
        descriptionRes = R.string.ljztu_page_3_description,
        imageRes = R.drawable.onboarding_3,
    ),
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: LJZTUOnboardingVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
) {
    val onboardingSetState by viewModel.onboardingSetState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { onboardingPagesContent.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(onboardingSetState) {
        if (onboardingSetState) {
            onNavigateToHomeScreen()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            val content = onboardingPagesContent[page]
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(content.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Dark gradient overlay on bottom 40%
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(0.4f)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xE6000000))
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 80.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(content.titleRes),
                        fontFamily = HeadingFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Primary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(content.descriptionRes),
                        fontFamily = BodyFamily,
                        fontSize = 15.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Skip button top-right
        Text(
            text = stringResource(R.string.ljztu_skip_button_title),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .clickable { viewModel.setOnboardingCompleted() },
            fontFamily = BodyFamily,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )

        // Dot indicators + CTA
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(onboardingPagesContent.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == pagerState.currentPage) 12.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (index == pagerState.currentPage) Primary else Color.White.copy(alpha = 0.4f))
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (pagerState.currentPage == onboardingPagesContent.size - 1) {
                Button(
                    onClick = { viewModel.setOnboardingCompleted() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Get Started",
                        fontFamily = HeadingFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Accent,
                        letterSpacing = 1.sp
                    )
                }
            } else {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.ljztu_next_button_title),
                        fontFamily = HeadingFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Accent,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}
