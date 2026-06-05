package dealroutewd.kiosk.orders.data.model

import androidx.annotation.StringRes
import dealroutewd.kiosk.orders.R

enum class ProductCategory(@field:StringRes val titleRes: Int) {
    SNACKS(R.string.ljztu_category_snacks),
    BEVERAGES(R.string.ljztu_category_beverages),
    DAILY_ESSENTIALS(R.string.ljztu_category_daily_essentials),
    SOUVENIRS(R.string.ljztu_category_souvenirs),
}
