package dealroutewd.kiosk.orders.ui.composable.screen.productdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dealroutewd.kiosk.orders.R
import dealroutewd.kiosk.orders.data.model.Product
import dealroutewd.kiosk.orders.ui.composable.shared.LJZTUContentWrapper
import dealroutewd.kiosk.orders.ui.composable.shared.LJZTUEmptyView
import dealroutewd.kiosk.orders.ui.state.DataUiState
import dealroutewd.kiosk.orders.ui.theme.Accent
import dealroutewd.kiosk.orders.ui.theme.BodyFamily
import dealroutewd.kiosk.orders.ui.theme.HeadingFamily
import dealroutewd.kiosk.orders.ui.theme.Muted
import dealroutewd.kiosk.orders.ui.theme.OnSurface
import dealroutewd.kiosk.orders.ui.theme.Primary
import dealroutewd.kiosk.orders.ui.viewmodel.ProductDetailsViewModel
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = koinViewModel(),
) {
    val productState by viewModel.productDetailsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeProductDetails(productId)
    }

    ProductDetailsScreenContent(
        productState = productState,
        modifier = modifier,
        onAddToCart = viewModel::addProductToCart
    )
}

@Composable
private fun ProductDetailsScreenContent(
    productState: DataUiState<Product>,
    modifier: Modifier = Modifier,
    onAddToCart: () -> Unit,
) {
    var cartAdded by remember { mutableStateOf(false) }

    LaunchedEffect(cartAdded) {
        if (cartAdded) {
            delay(2000)
            cartAdded = false
        }
    }

    Column(modifier = modifier.fillMaxSize().background(Color(0xFFF5F5F0))) {
        LJZTUContentWrapper(
            dataState = productState,
            dataPopulated = {
                val data = (productState as DataUiState.Populated).data

                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Hero image with price overlay
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                        ) {
                            AsyncImage(
                                model = data.imageUrl,
                                contentDescription = stringResource(R.string.ljztu_product_image_description),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                                    .align(Alignment.BottomCenter)
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color.Transparent, Accent)
                                        )
                                    )
                            )
                            Text(
                                text = "£%.2f".format(data.price),
                                fontFamily = HeadingFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                color = Primary,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(16.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                        ) {
                            Text(
                                text = data.title,
                                fontFamily = HeadingFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = OnSurface
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = data.description,
                                fontFamily = BodyFamily,
                                fontSize = 15.sp,
                                color = Muted,
                                lineHeight = 22.sp,
                                maxLines = 3
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(data.category.titleRes),
                                fontFamily = BodyFamily,
                                fontSize = 13.sp,
                                color = Primary,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // Sticky Add to Cart
                        Button(
                            onClick = {
                                onAddToCart()
                                cartAdded = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(horizontal = 20.dp, vertical = 4.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Accent),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.ljztu_button_add_to_cart_label),
                                fontFamily = HeadingFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Primary,
                                letterSpacing = 1.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Added to cart feedback banner
                    AnimatedVisibility(
                        visible = cartAdded,
                        enter = slideInVertically(animationSpec = spring()) { it },
                        exit = slideOutVertically(animationSpec = spring()) { it },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Accent)
                                .padding(vertical = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓ ADDED TO CART",
                                fontFamily = HeadingFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.White,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            },
            dataEmpty = {
                LJZTUEmptyView(
                    primaryText = stringResource(R.string.ljztu_product_details_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}
