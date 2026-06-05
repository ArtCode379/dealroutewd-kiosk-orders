package dealroutewd.kiosk.orders.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dealroutewd.kiosk.orders.data.dao.CartItemDao
import dealroutewd.kiosk.orders.data.dao.OrderDao
import dealroutewd.kiosk.orders.data.database.converter.Converters
import dealroutewd.kiosk.orders.data.entity.CartItemEntity
import dealroutewd.kiosk.orders.data.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LJZTUDatabase : RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderDao(): OrderDao
}