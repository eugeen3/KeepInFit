package eugeen3.keepinfit.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import eugeen3.keepinfit.repositories.FoodItemRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import eugeen3.keepinfit.entities.FoodItem
import eugeen3.keepinfit.entities.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FoodItemRepository = FoodItemRepository(application)
    val allFoodItems: LiveData<List<FoodItem>> = repository.allFoodItems!!

    //val mealList: LiveData<List<Meal>>
    var foodItemPosition: Int? = null


    fun insert(foodItem: FoodItem?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(foodItem)
        }
    }

    fun update(foodItem: FoodItem?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(foodItem)
        }
    }

    fun delete(foodItem: FoodItem?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(foodItem)
        }
    }

    fun deleteAllFoodItems() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFoodItems()
        }
    }

}