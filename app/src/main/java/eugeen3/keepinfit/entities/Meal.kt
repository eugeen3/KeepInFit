package eugeen3.keepinfit.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meal(val name: String, val foodItems: List<FoodItem>? = null) {
    @PrimaryKey(autoGenerate = true)
    private val id: Long = 0
}