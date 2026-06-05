package dealroutewd.kiosk.orders.ui.composable.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dealroutewd.kiosk.orders.R
import dealroutewd.kiosk.orders.data.entity.OrderEntity
import dealroutewd.kiosk.orders.ui.composable.shared.LJZTUContentWrapper
import dealroutewd.kiosk.orders.ui.composable.shared.LJZTUEmptyView
import dealroutewd.kiosk.orders.ui.state.DataUiState
import dealroutewd.kiosk.orders.ui.theme.Background
import dealroutewd.kiosk.orders.ui.theme.BodyFamily
import dealroutewd.kiosk.orders.ui.theme.HeadingFamily
import dealroutewd.kiosk.orders.ui.theme.Muted
import dealroutewd.kiosk.orders.ui.theme.OnSurface
import dealroutewd.kiosk.orders.ui.theme.Primary
import dealroutewd.kiosk.orders.ui.theme.Success
import dealroutewd.kiosk.orders.ui.theme.Surface
import dealroutewd.kiosk.orders.ui.viewmodel.OrderViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = koinViewModel(),
) {
    val ordersState by viewModel.ordersState.collectAsState()

    OrdersContent(
        ordersState = ordersState,
        modifier = modifier,
    )
}

@Composable
private fun OrdersContent(
    ordersState: DataUiState<List<OrderEntity>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        LJZTUContentWrapper(
            dataState = ordersState,
            dataPopulated = {
                val data = (ordersState as DataUiState.Populated).data
                val sorted = data.sortedByDescending { it.timestamp }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(sorted) { order ->
                        OrderCard(order = order)
                    }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            },
            dataEmpty = {
                LJZTUEmptyView(
                    primaryText = stringResource(R.string.ljztu_orders_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun OrderCard(order: OrderEntity) {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.ljztu_order_number, order.orderNumber),
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = OnSurface
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Success.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Completed",
                        fontFamily = BodyFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Success
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = order.timestamp.format(formatter),
                fontFamily = BodyFamily,
                fontSize = 13.sp,
                color = Muted
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.ljztu_order_customer, order.customerFirstName, order.customerLastName),
                fontFamily = BodyFamily,
                fontSize = 14.sp,
                color = OnSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Total: £%.2f".format(order.price),
                fontFamily = HeadingFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Primary
            )
        }
    }
}
