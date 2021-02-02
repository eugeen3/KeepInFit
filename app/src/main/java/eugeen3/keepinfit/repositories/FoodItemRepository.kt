package eugeen3.keepinfit.repositories

import android.app.Application
import android.os.AsyncTask
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
        InsertFoodItemAsyncTask(foodItemDAO).execute(foodItem)
    }

    fun update(foodItem: FoodItem?) {
        UpdateFoodItemAsyncTask(foodItemDAO).execute(foodItem)
    }

    fun delete(foodItem: FoodItem?) {
        DeleteFoodItemAsyncTask(foodItemDAO).execute(foodItem)
    }

    fun deleteAllFoodItems() {
        DeleteAllFoodItemAsyncTask(foodItemDAO).execute()
    }

    private class InsertFoodItemAsyncTask constructor (private val foodItemDAO: FoodItemDAO?) :
            AsyncTask<FoodItem, Void, Void>() {
        override fun doInBackground(vararg foodItems: FoodItem): Void? {
            foodItemDAO?.insert(foodItems[0])
            return null
        }
    }

    private class UpdateFoodItemAsyncTask constructor(private val foodItemDAO: FoodItemDAO?) :
            AsyncTask<FoodItem, Void, Void>() {
        override fun doInBackground(vararg foodItems: FoodItem): Void? {
            foodItemDAO?.update(foodItems[0])
            return null
        }
    }

    private class DeleteFoodItemAsyncTask constructor(private val foodItemDAO: FoodItemDAO?) :
            AsyncTask<FoodItem, Void, Void>() {
        override fun doInBackground(vararg foodItems: FoodItem): Void? {
            foodItemDAO?.delete(foodItems[0])
            return null
        }
    }

    private class DeleteAllFoodItemAsyncTask constructor(private val foodItemDAO: FoodItemDAO?) :
            AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void): Void? {
            foodItemDAO?.deleteAll()
            return null
        }
    }
}