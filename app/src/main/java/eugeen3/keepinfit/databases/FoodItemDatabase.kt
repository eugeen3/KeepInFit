package eugeen3.keepinfit.databases

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import eugeen3.keepinfit.dao.FoodItemDAO
import eugeen3.keepinfit.entities.FoodItem

@Database(entities = [FoodItem::class], version = 3, exportSchema = false)
abstract class FoodItemDatabase : RoomDatabase() {

    abstract fun foodItemDAO(): FoodItemDAO

    companion object {

        private var instance: FoodItemDatabase? = null

        @Synchronized
        internal fun getInstance(context: Context): FoodItemDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        FoodItemDatabase::class.java,
                        "foodItem_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
            }
            return instance
        }

        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDBAsyncTask(instance).execute()
            }
        }
    }

    private class PopulateDBAsyncTask constructor(database: FoodItemDatabase?) : AsyncTask<Void?, Void?, Void?>() {
        private val foodItemDAO: FoodItemDAO = database!!.foodItemDAO()
        override fun doInBackground(vararg voids: Void?): Void? {
            foodItemDAO.insert(FoodItem("Яйцо", 12.7f, 0.7f, 11.5f, 157))
            foodItemDAO.insert(FoodItem("Банан", 1.5f, 21f, 0.5f, 96))
            foodItemDAO.insert(FoodItem("Яблоко", 0.4f, 9.8f, 0.4f, 47))
            foodItemDAO.insert(FoodItem("Говядина", 22.9f, 32.7f, 0f, 393))
            return null
        }

    }
}