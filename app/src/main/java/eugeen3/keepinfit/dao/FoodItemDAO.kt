package eugeen3.keepinfit.dao

import eugeen3.keepinfit.entities.FoodItem
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FoodItemDAO {
    @Insert
    fun insert(foodItem: FoodItem)

    @Update
    fun update(foodItem: FoodItem)

    @Delete
    fun delete(foodItem: FoodItem)

    @Query("DELETE FROM foodItems_table")
    fun deleteAll()

    @get:Query("SELECT * FROM foodItems_table ORDER BY name ASC")
    val allFoodItems: LiveData<List<FoodItem>>
}