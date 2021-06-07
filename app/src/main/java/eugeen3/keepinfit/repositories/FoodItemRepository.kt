package eugeen3.keepinfit.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import eugeen3.keepinfit.dao.FoodItemDAO
import eugeen3.keepinfit.databases.FoodItemDatabase
import eugeen3.keepinfit.entities.FoodItem

class FoodItemRepository(application: Application) {
    private var foodItemDAO: FoodItemDAO?
    val allFoodItems: LiveData<List<FoodItem>>?

    init {
        val database : FoodItemDatabase? = FoodItemDatabase.getInstance(application)
        foodItemDAO = database?.foodItemDAO()
        allFoodItems = foodItemDAO?.allFoodItems
    }

    fun insert(foodItem: FoodItem?) {
        if (foodItem != null) {
            foodItemDAO?.insert(foodItem)
        }
    }

    fun update(foodItem: FoodItem?) {
        if (foodItem != null) {
            foodItemDAO?.update(foodItem)
        }
    }

    fun delete(foodItem: FoodItem?) {
        if (foodItem != null) {
            foodItemDAO?.delete(foodItem)
        }
    }

    fun deleteAllFoodItems() {
        foodItemDAO?.deleteAll()
    }
}