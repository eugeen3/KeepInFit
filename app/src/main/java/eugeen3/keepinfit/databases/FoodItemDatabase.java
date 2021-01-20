package eugeen3.keepinfit.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import eugeen3.keepinfit.dao.FoodItemDAO;
import eugeen3.keepinfit.entities.FoodItem;

@Database(entities = FoodItem.class, version = 2, exportSchema = false)
public abstract class FoodItemDatabase extends RoomDatabase {

    private static FoodItemDatabase instance;

    public abstract FoodItemDAO foodItemDAO();

    public static synchronized FoodItemDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FoodItemDatabase.class,
                    "foodItem_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static FoodItemDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private FoodItemDAO foodItemDAO;

        private PopulateDBAsyncTask(FoodItemDatabase database) {
            foodItemDAO = database.foodItemDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            foodItemDAO.insert(new FoodItem("Яйцо", 12.7f, 0.7f, 11.5f, 157));
            foodItemDAO.insert(new FoodItem("Банан", 1.5f, 21f, 0.5f, 96));
            foodItemDAO.insert(new FoodItem("Яблоко", 0.4f, 9.8f, 0.4f, 47));
            foodItemDAO.insert(new FoodItem("Говядина", 22.9f, 32.7f, 0f, 393));
            return null;
        }
    }
}
