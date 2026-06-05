package dealroutewd.kiosk.orders.di

import dealroutewd.kiosk.orders.data.repository.CartRepository
import dealroutewd.kiosk.orders.data.repository.LJZTUOnboardingRepo
import dealroutewd.kiosk.orders.data.repository.OrderRepository
import dealroutewd.kiosk.orders.data.repository.ProductRepository

import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        LJZTUOnboardingRepo(
            ljztuOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ProductRepository() }

    single {
        CartRepository(
            cartItemDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single {
        OrderRepository(
            orderDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}