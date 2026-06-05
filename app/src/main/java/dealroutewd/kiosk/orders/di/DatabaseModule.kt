package dealroutewd.kiosk.orders.di

import androidx.room.Room
import dealroutewd.kiosk.orders.data.database.LJZTUDatabase
import org.koin.dsl.module

private const val DB_NAME = "ljztu_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = LJZTUDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<LJZTUDatabase>().cartItemDao() }

    single { get<LJZTUDatabase>().orderDao() }
}