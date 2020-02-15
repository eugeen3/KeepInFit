package eugeen3.keepinfit.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity (tableName = "foodItems_table")
public class FoodItem {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private int mass;
    private float proteins;
    private float carbohydrates;
    private float fats;
    private int kcals;

    public FoodItem(String name, int mass, float prots, float fats, float carbs, int kcals) {
        this.name = name;
        this.mass = mass;
        this.proteins = prots;
        this.fats = fats;
        this.carbohydrates = carbs;
        this.kcals = kcals;
    }

    public static float portionNV(int mass, float num) {
        return new BigDecimal(mass * num / 100).
                setScale(2, RoundingMode.UP).floatValue();
    }

    public static int portionNV(int mass, int num) {
        return Math.round(mass * num / 100);
    }

    public String getName() {
        return name;
    }

    public int getMass() {
        return mass;
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

    @Override
    public String toString() {
        return name + " " + mass + " " + proteins +
                " " + fats + " " + carbohydrates + " " + kcals;
    }
}
