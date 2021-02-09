package eugeen3.keepinfit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import eugeen3.keepinfit.repositories.FoodItemRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import eugeen3.keepinfit.entities.FoodItem
import eugeen3.keepinfit.entities.Meal

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    val repository: FoodItemRepository = FoodItemRepository(application)
    val allFoodItems: LiveData<List<FoodItem>>
    //val mealList: LiveData<List<Meal>>
    var foodItem: MutableLiveData<FoodItem>? = null

    fun setFoodItem(foodItem: FoodItem?) {
        this.foodItem?.value = foodItem
    }

    fun getFoodItem() : FoodItem? {
        return foodItem?.value
    }

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
        allFoodItems = repository.allFoodItems!!
    }
}