package eugeen3.keepinfit.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eugeen3.keepinfit.entities.FoodItem;

@Dao
public interface FoodItemDAO {

    @Insert
    void insert(FoodItem foodItem);

    @Update
    void update(FoodItem foodItem);

    @Delete
    void delete(FoodItem foodItem);

    @Query("DELETE FROM foodItems_table")
    void deleteAll();

    @Query("SELECT * FROM foodItems_table ORDER BY name ASC")
    LiveData<List<FoodItem>> getAllFoodItems();
}
