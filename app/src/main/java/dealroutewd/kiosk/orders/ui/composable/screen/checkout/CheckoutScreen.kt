package dealroutewd.kiosk.orders.ui.composable.screen.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dealroutewd.kiosk.orders.R
import dealroutewd.kiosk.orders.data.entity.OrderEntity
import dealroutewd.kiosk.orders.ui.state.DataUiState
import dealroutewd.kiosk.orders.ui.theme.Accent
import dealroutewd.kiosk.orders.ui.theme.Background
import dealroutewd.kiosk.orders.ui.theme.BodyFamily
import dealroutewd.kiosk.orders.ui.theme.HeadingFamily
import dealroutewd.kiosk.orders.ui.theme.Muted
import dealroutewd.kiosk.orders.ui.theme.OnSurface
import dealroutewd.kiosk.orders.ui.theme.Primary
import dealroutewd.kiosk.orders.ui.viewmodel.CheckoutViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToOrdersScreen: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val orderState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalidState by viewModel.emailInvalidState.collectAsStateWithLifecycle()

    val isButtonEnabled by remember {
        derivedStateOf {
            viewModel.customerFirstName.isNotEmpty() &&
                    viewModel.customerLastName.isNotEmpty() &&
                    viewModel.customerEmail.isNotEmpty()
        }
    }

    if (orderState is DataUiState.Populated) {
        CheckoutDialog(
            order = (orderState as DataUiState.Populated<OrderEntity>).data,
            onConfirm = onNavigateToOrdersScreen
        )
    }

    CheckoutContent(
        customerFirstName = viewModel.customerFirstName,
        customerLastName = viewModel.customerLastName,
        customerEmail = viewModel.customerEmail,
        isEmailInvalid = emailInvalidState,
        modifier = modifier,
        focusManager = focusManager,
        isButtonEnabled = isButtonEnabled,
        onFirstNameChanged = viewModel::updateCustomerFirstName,
        onLastNameChanged = viewModel::updateCustomerLastName,
        onEmailChanged = viewModel::updateCustomerEmail,
        onPlaceOrderButtonClick = viewModel::placeOrder
    )
}

@Composable
private fun CheckoutContent(
    customerFirstName: String,
    customerLastName: String,
    customerEmail: String,
    isEmailInvalid: Boolean,
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    isButtonEnabled: Boolean,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPlaceOrderButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(20.dp)
    ) {
        Text(
            text = "Checkout",
            fontFamily = HeadingFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = OnSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Enter your details to complete your order",
            fontFamily = BodyFamily,
            fontSize = 14.sp,
            color = Muted
        )
        Spacer(modifier = Modifier.height(24.dp))

        CheckoutTextField(
            input = customerFirstName,
            onInputChange = onFirstNameChanged,
            labelText = stringResource(R.string.ljztu_checkout_text_field_first_name),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(16.dp))

        CheckoutTextField(
            input = customerLastName,
            onInputChange = onLastNameChanged,
            labelText = stringResource(R.string.ljztu_checkout_text_field_last_name),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(16.dp))

        CheckoutTextField(
            input = customerEmail,
            onInputChange = onEmailChanged,
            labelText = stringResource(R.string.ljztu_checkout_text_field_email),
            modifier = Modifier.fillMaxWidth(),
            isError = isEmailInvalid,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )
        if (isEmailInvalid) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Please enter a valid email address",
                fontFamily = BodyFamily,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onPlaceOrderButtonClick,
            enabled = isButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Accent,
                disabledContainerColor = Muted
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = stringResource(R.string.ljztu_button_confirm_order_label),
                fontFamily = HeadingFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Primary,
                letterSpacing = 0.5.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CheckoutTextField(
    input: String,
    onInputChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        modifier = modifier,
        enabled = enabled,
        label = {
            Text(
                text = labelText,
                style = MaterialTheme.typography.titleSmall,
            )
        },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
    )
}
