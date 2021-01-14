package eugeen3.keepinfit.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Meal {
    @PrimaryKey
    private long id;

    private String name;
    private List<FoodItem> foodItems;

}
