package dealroutewd.kiosk.orders.di

import dealroutewd.kiosk.orders.data.datastore.LJZTUOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { LJZTUOnboardingPrefs(androidContext()) }
}