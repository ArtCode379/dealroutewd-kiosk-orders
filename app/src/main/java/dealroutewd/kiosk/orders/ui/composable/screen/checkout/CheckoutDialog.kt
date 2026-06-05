package dealroutewd.kiosk.orders.ui.composable.screen.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dealroutewd.kiosk.orders.R
import dealroutewd.kiosk.orders.data.entity.OrderEntity
import dealroutewd.kiosk.orders.ui.theme.Accent
import dealroutewd.kiosk.orders.ui.theme.BodyFamily
import dealroutewd.kiosk.orders.ui.theme.HeadingFamily
import dealroutewd.kiosk.orders.ui.theme.Muted
import dealroutewd.kiosk.orders.ui.theme.OnSurface
import dealroutewd.kiosk.orders.ui.theme.Primary

@Composable
fun CheckoutDialog(
    order: OrderEntity,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onConfirm() },
        title = {
            Text(
                text = stringResource(R.string.ljztu_checkout_dialog_title),
                fontFamily = HeadingFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = OnSurface
            )
        },
        text = {
            Column {
                Text(
                    text = stringResource(R.string.ljztu_checkout_dialog_order_number, order.orderNumber),
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Customer: ${order.customerFirstName} ${order.customerLastName}",
                    fontFamily = BodyFamily,
                    fontSize = 14.sp,
                    color = OnSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Total: £%.2f".format(order.price),
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Accent
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.ljztu_checkout_dialog_processing_message),
                    fontFamily = BodyFamily,
                    fontSize = 13.sp,
                    color = Muted,
                    lineHeight = 19.sp
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Accent),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "View Orders",
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Primary
                )
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}
