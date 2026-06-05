package dealroutewd.kiosk.orders.ui.composable.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import dealroutewd.kiosk.orders.data.model.ProductCategory
import dealroutewd.kiosk.orders.ui.composable.shared.LJZTUContentWrapper
import dealroutewd.kiosk.orders.ui.composable.shared.LJZTUEmptyView
import dealroutewd.kiosk.orders.ui.state.DataUiState
import dealroutewd.kiosk.orders.ui.theme.Accent
import dealroutewd.kiosk.orders.ui.theme.Background
import dealroutewd.kiosk.orders.ui.theme.BodyFamily
import dealroutewd.kiosk.orders.ui.theme.Border
import dealroutewd.kiosk.orders.ui.theme.ChipBackground
import dealroutewd.kiosk.orders.ui.theme.ChipContent
import dealroutewd.kiosk.orders.ui.theme.GradientEnd
import dealroutewd.kiosk.orders.ui.theme.GradientStart
import dealroutewd.kiosk.orders.ui.theme.HeadingFamily
import dealroutewd.kiosk.orders.ui.theme.Muted
import dealroutewd.kiosk.orders.ui.theme.OnSurface
import dealroutewd.kiosk.orders.ui.theme.Primary
import dealroutewd.kiosk.orders.ui.theme.Surface
import dealroutewd.kiosk.orders.ui.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onNavigateToProductDetails: (productId: Int) -> Unit,
) {
    val productsState by viewModel.productsState.collectAsState()

    HomeContent(
        productsState = productsState,
        modifier = modifier,
        onNavigateToProductDetails = onNavigateToProductDetails,
        onAddProductToCart = viewModel::addToCart,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    productsState: DataUiState<List<Product>>,
    modifier: Modifier = Modifier,
    onNavigateToProductDetails: (productId: Int) -> Unit,
    onAddProductToCart: (productId: Int) -> Unit,
) {
    var selectedCategory by remember { mutableStateOf<ProductCategory?>(null) }

    Column(modifier = modifier.fillMaxSize().background(Background)) {
        // Gradient TopAppBar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(GradientStart, GradientEnd)))
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "DEAL ROUTE",
                        fontFamily = HeadingFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        letterSpacing = 2.sp,
                        color = Accent
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }

        LJZTUContentWrapper(
            dataState = productsState,
            dataPopulated = {
                val allProducts = (productsState as DataUiState.Populated).data
                val featured = allProducts.firstOrNull()
                val filteredProducts = if (selectedCategory == null) allProducts
                else allProducts.filter { it.category == selectedCategory }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    // Featured banner
                    if (featured != null && selectedCategory == null) {
                        item {
                            BannerCard(
                                product = featured,
                                onClick = { onNavigateToProductDetails(featured.id) }
                            )
                        }
                    }

                    // Category chips
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = selectedCategory == null,
                                onClick = { selectedCategory = null },
                                label = {
                                    Text(
                                        text = "All",
                                        fontFamily = BodyFamily,
                                        fontSize = 13.sp
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Primary,
                                    selectedLabelColor = Accent,
                                    containerColor = ChipBackground,
                                    labelColor = ChipContent
                                )
                            )
                            ProductCategory.entries.forEach { cat ->
                                FilterChip(
                                    selected = selectedCategory == cat,
                                    onClick = { selectedCategory = if (selectedCategory == cat) null else cat },
                                    label = {
                                        Text(
                                            text = stringResource(cat.titleRes),
                                            fontFamily = BodyFamily,
                                            fontSize = 13.sp
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Primary,
                                        selectedLabelColor = Accent,
                                        containerColor = ChipBackground,
                                        labelColor = ChipContent
                                    )
                                )
                            }
                        }
                    }

                    items(filteredProducts) { product ->
                        ProductRowCard(
                            product = product,
                            onClick = { onNavigateToProductDetails(product.id) }
                        )
                    }

                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            },
            dataEmpty = {
                LJZTUEmptyView(
                    primaryText = stringResource(R.string.ljztu_products_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun BannerCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xCC000000))
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.title,
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Primary)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "£%.2f".format(product.price),
                        fontFamily = HeadingFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Accent
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductRowCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Left accent bar
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(80.dp)
                    .background(Primary)
            )
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 8.dp, bottomEnd = 8.dp))
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.title,
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = OnSurface,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.description,
                    fontFamily = BodyFamily,
                    fontSize = 12.sp,
                    color = Muted,
                    maxLines = 2,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = stringResource(product.category.titleRes),
                    fontFamily = BodyFamily,
                    fontSize = 11.sp,
                    color = ChipContent
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Primary)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "£%.2f".format(product.price),
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Accent
                )
            }
        }
    }
}
