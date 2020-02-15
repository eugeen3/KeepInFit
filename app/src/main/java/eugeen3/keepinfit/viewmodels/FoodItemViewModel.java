package eugeen3.keepinfit.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import eugeen3.keepinfit.entities.FoodItem;
import eugeen3.keepinfit.repositories.FoodItemRepository;

public class FoodItemViewModel extends AndroidViewModel {
    private FoodItemRepository repository;
    private LiveData<List<FoodItem>> allFoodItems;

    public FoodItemViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodItemRepository(application);
        allFoodItems = repository.getAllFoodItems();
    }

    public void insert(FoodItem foodItem) {
        repository.insert(foodItem);
    }


    public void update(FoodItem foodItem) {
        repository.update(foodItem);
    }


    public void delete(FoodItem foodItem) {
        repository.delete(foodItem);
    }


    public void deleteAllFoodItems() {
        repository.deleteAllFoodItems();
    }

    public LiveData<List<FoodItem>> getAllFoodItems() {
        return allFoodItems;
    }
}
