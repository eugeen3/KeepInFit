package eugeen3.keepinfit.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foodItems_table")
data class FoodItem(
        val name: String,
        val proteins: Float,
        val fats: Float,
        val carbohydrates: Float,
        val kilocalories: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return name + " " + proteins + " " + fats +
                " " + carbohydrates + " " + kilocalories
    }
}