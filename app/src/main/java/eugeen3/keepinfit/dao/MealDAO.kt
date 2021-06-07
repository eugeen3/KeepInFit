package eugeen3.keepinfit.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import eugeen3.keepinfit.entities.Meal

@Dao
interface MealDAO {
    @Insert
    fun insert(meal: Meal)

    @Update
    fun update(meal: Meal)

    @Delete
    fun delete(meal: Meal)

    @get:Query("SELECT * FROM foodItems_table ORDER BY name ASC")
    val allFoodItems: LiveData<List<Meal>>
}