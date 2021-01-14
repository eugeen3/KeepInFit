package eugeen3.keepinfit.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Meal {
    @PrimaryKey
    private val id: Long = 0
    private val name: String? = null
    private val foodItems: List<FoodItem>? = null
}