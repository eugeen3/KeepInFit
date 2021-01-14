package eugeen3.keepinfit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import eugeen3.keepinfit.repositories.FoodItemRepository
import androidx.lifecycle.LiveData
import eugeen3.keepinfit.entities.FoodItem

class FoodItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FoodItemRepository
    val allFoodItems: LiveData<List<FoodItem>>
    fun insert(foodItem: FoodItem?) {
        repository.insert(foodItem)
    }

    fun update(foodItem: FoodItem?) {
        repository.update(foodItem)
    }

    fun delete(foodItem: FoodItem?) {
        repository.delete(foodItem)
    }

    fun deleteAllFoodItems() {
        repository.deleteAllFoodItems()
    }

    init {
        repository = FoodItemRepository(application)
        allFoodItems = repository.allFoodItems
    }
}