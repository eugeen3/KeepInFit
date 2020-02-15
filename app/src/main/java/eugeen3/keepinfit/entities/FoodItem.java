package eugeen3.keepinfit.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "foodItems_table")
public class FoodItem {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private float proteins;
    private float carbohydrates;
    private float fats;
    private int kcals;

    public FoodItem(String name, float proteins, float carbohydrates, float fats, int kcals) {
        this.name = name;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.kcals = kcals;
    }

    public String getName() {
        return name;
    }

    public float getProteins() {
        return proteins;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public float getFats() {
        return fats;
    }

    public int getKcals() {
        return kcals;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + " " + proteins + " " + fats +
                " " + carbohydrates + " " + kcals;
    }
}
