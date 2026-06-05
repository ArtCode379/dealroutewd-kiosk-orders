package dealroutewd.kiosk.orders.data.repository

import dealroutewd.kiosk.orders.data.datastore.LJZTUOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LJZTUOnboardingRepo(
    private val ljztuOnboardingStoreManager: LJZTUOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return ljztuOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            ljztuOnboardingStoreManager.setOnboardedState(state)
        }
    }
}