package dealroutewd.kiosk.orders.di

import dealroutewd.kiosk.orders.ui.viewmodel.AppViewModel
import dealroutewd.kiosk.orders.ui.viewmodel.CartViewModel
import dealroutewd.kiosk.orders.ui.viewmodel.CheckoutViewModel
import dealroutewd.kiosk.orders.ui.viewmodel.LJZTUOnboardingVM
import dealroutewd.kiosk.orders.ui.viewmodel.OrderViewModel
import dealroutewd.kiosk.orders.ui.viewmodel.ProductDetailsViewModel
import dealroutewd.kiosk.orders.ui.viewmodel.ProductViewModel
import dealroutewd.kiosk.orders.ui.viewmodel.LJZTUSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        AppViewModel(
            cartRepository = get()
        )
    }

    viewModel {
        LJZTUSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        LJZTUOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ProductViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        ProductDetailsViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            cartRepository = get(),
            productRepository = get(),
            orderRepository = get(),
        )
    }

    viewModel {
        CartViewModel(
            cartRepository = get(),
            productRepository = get(),
        )
    }

    viewModel {
        OrderViewModel(
            orderRepository = get(),
        )
    }
}