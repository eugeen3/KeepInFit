package eugeen3.keepinfit.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import eugeen3.keepinfit.dao.FoodItemDAO;
import eugeen3.keepinfit.databases.FoodItemDatabase;
import eugeen3.keepinfit.entities.FoodItem;

public class FoodItemRepository {
    private FoodItemDAO foodItemDAO;
    private LiveData<List<FoodItem>> allFoodItems;

    public FoodItemRepository(Application application) {
        FoodItemDatabase database = FoodItemDatabase.getInstance(application);
        foodItemDAO = database.foodItemDAO();
        allFoodItems = foodItemDAO.getAllFoodItems();
    }

    public void insert(FoodItem foodItem) {
        new InsertFoodItemAsyncTask(foodItemDAO).execute(foodItem);
    }


    public void update(FoodItem foodItem) {
        new UpdateFoodItemAsyncTask(foodItemDAO).execute(foodItem);
    }


    public void delete(FoodItem foodItem) {
        new DeleteFoodItemAsyncTask(foodItemDAO).execute(foodItem);
    }


    public void deleteAllFoodItems() {
        new DeleteAllFoodItemAsyncTask(foodItemDAO).execute();
    }


    public LiveData<List<FoodItem>> getAllFoodItems() {
        return allFoodItems;
    }

    private static class InsertFoodItemAsyncTask extends AsyncTask<FoodItem, Void, Void> {
        private FoodItemDAO foodItemDAO;

        private InsertFoodItemAsyncTask(FoodItemDAO foodItemDAO) {
            this.foodItemDAO = foodItemDAO;
        }

        @Override
        protected Void doInBackground(FoodItem... foodItems) {
            foodItemDAO.insert(foodItems[0]);
            return null;
        }
    }

    private static class UpdateFoodItemAsyncTask extends AsyncTask<FoodItem, Void, Void> {
        private FoodItemDAO foodItemDAO;

        private UpdateFoodItemAsyncTask(FoodItemDAO foodItemDAO) {
            this.foodItemDAO = foodItemDAO;
        }

        @Override
        protected Void doInBackground(FoodItem... foodItems) {
            foodItemDAO.update(foodItems[0]);
            return null;
        }
    }

    private static class DeleteFoodItemAsyncTask extends AsyncTask<FoodItem, Void, Void> {
        private FoodItemDAO foodItemDAO;

        private DeleteFoodItemAsyncTask(FoodItemDAO foodItemDAO) {
            this.foodItemDAO = foodItemDAO;
        }

        @Override
        protected Void doInBackground(FoodItem... foodItems) {
            foodItemDAO.delete(foodItems[0]);
            return null;
        }
    }

    private static class DeleteAllFoodItemAsyncTask extends AsyncTask<FoodItem, Void, Void> {
        private FoodItemDAO foodItemDAO;

        private DeleteAllFoodItemAsyncTask(FoodItemDAO foodItemDAO) {
            this.foodItemDAO = foodItemDAO;
        }

        @Override
        protected Void doInBackground(FoodItem... foodItems) {
            foodItemDAO.deleteAll();
            return null;
        }
    }
}
