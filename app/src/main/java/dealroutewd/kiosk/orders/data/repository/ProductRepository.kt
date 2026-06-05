package dealroutewd.kiosk.orders.data.repository

import dealroutewd.kiosk.orders.data.model.Product
import dealroutewd.kiosk.orders.data.model.ProductCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductRepository {
    private val products: List<Product> = listOf(
        Product(
            id = 1,
            title = "Classic Potato Chips",
            description = "Crispy golden potato chips lightly salted and perfectly crunchy. A kiosk favorite snack for on-the-go munching.",
            category = ProductCategory.SNACKS,
            price = 1.49,
            imageUrl = "https://images.unsplash.com/photo-1566478989037-eec170784d0b?w=600"
        ),
        Product(
            id = 2,
            title = "Sparkling Water 500ml",
            description = "Refreshing carbonated mineral water. Cold and crisp — the perfect hydration for any urban adventure.",
            category = ProductCategory.BEVERAGES,
            price = 1.29,
            imageUrl = "https://images.unsplash.com/photo-1523362628745-0c100150b504?w=600"
        ),
        Product(
            id = 3,
            title = "City Skyline Keychain",
            description = "Miniature metal keychain featuring a detailed city skyline silhouette. A perfect keepsake or gift.",
            category = ProductCategory.SOUVENIRS,
            price = 4.99,
            imageUrl = "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=600"
        ),
        Product(
            id = 4,
            title = "Energy Drink 250ml",
            description = "Boost your day with our high-energy drink packed with B-vitamins and natural caffeine.",
            category = ProductCategory.BEVERAGES,
            price = 2.49,
            imageUrl = "https://images.unsplash.com/photo-1570831739435-6601aa3fa4fb?w=600"
        ),
        Product(
            id = 5,
            title = "Wet Wipes Pack",
            description = "Pack of 10 antibacterial wet wipes. Essential daily carry for clean hands anytime, anywhere.",
            category = ProductCategory.DAILY_ESSENTIALS,
            price = 0.99,
            imageUrl = "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=600"
        ),
        Product(
            id = 6,
            title = "Chocolate Bar 50g",
            description = "Rich creamy milk chocolate bar. A sweet treat from our kiosk that satisfies every craving.",
            category = ProductCategory.SNACKS,
            price = 1.79,
            imageUrl = "https://images.unsplash.com/photo-1481391319762-47dff72954d9?w=600"
        ),
        Product(
            id = 7,
            title = "Travel Umbrella",
            description = "Compact folding umbrella with wind-resistant frame. Never get caught in the rain unprepared.",
            category = ProductCategory.DAILY_ESSENTIALS,
            price = 8.99,
            imageUrl = "https://images.unsplash.com/photo-1558449028-b53a39d100fc?w=600"
        ),
        Product(
            id = 8,
            title = "Street Map Postcard",
            description = "Illustrated postcard featuring a hand-drawn street map of the city. Great for travelers and collectors.",
            category = ProductCategory.SOUVENIRS,
            price = 2.99,
            imageUrl = "https://images.unsplash.com/photo-1586348943529-beaae6c28db9?w=600"
        ),
        Product(
            id = 9,
            title = "Freshly Brewed Coffee",
            description = "Hot arabica coffee brewed fresh to order. Fuel your day with the bold flavor of our signature blend.",
            category = ProductCategory.BEVERAGES,
            price = 2.99,
            imageUrl = "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=600"
        ),
        Product(
            id = 10,
            title = "Hand Sanitizer 50ml",
            description = "Pocket-sized 70% alcohol hand sanitizer gel. Stay safe and clean wherever the street takes you.",
            category = ProductCategory.DAILY_ESSENTIALS,
            price = 1.99,
            imageUrl = "https://images.unsplash.com/photo-1584483766114-2cea6facdf57?w=600"
        ),
    )

    fun observeById(id: Int): Flow<Product?> {
        val item = products.find { it.id == id }
        return flowOf(item)
    }

    fun getById(id: Int): Product? {
        return products.find { it.id == id }
    }

    fun observeAll(): Flow<List<Product>> {
        return flowOf(products)
    }

    suspend fun getAll(): List<Product> {
        return products
    }
}
