package dealroutewd.kiosk.orders

import android.app.Application
import dealroutewd.kiosk.orders.di.dataModule
import dealroutewd.kiosk.orders.di.dispatcherModule
import dealroutewd.kiosk.orders.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class LJZTUApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModules = dataModule + viewModule + dispatcherModule

        startKoin {
            androidLogger()
            androidContext(this@LJZTUApplication)
            modules(appModules)
        }
    }
}