package eugeen3.keepinfit.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import eugeen3.keepinfit.R
import eugeen3.keepinfit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MenuItem.OnActionExpandListener {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportFragmentManager.beginTransaction()
                .add(R.id.vFragmentContainer, FoodItemsFragment())
                .commit()

    }



//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == ADD_FOOD_ITEM_REQUEST && resultCode == RESULT_OK) {
//            val title = data!!.getStringExtra(AddFoodItemToDB.EXTRA_TITLE)
//            val proteins = data.getFloatExtra(AddFoodItemToDB.EXTRA_PROTEINS, 0f)
//            val fats = data.getFloatExtra(AddFoodItemToDB.EXTRA_FATS, 0f)
//            val carbohydrates = data.getFloatExtra(AddFoodItemToDB.EXTRA_CARBOHYDRATES, 0f)
//            val kilocalories = data.getIntExtra(AddFoodItemToDB.EXTRA_KCALS, 0)
//            Toast.makeText(this, title + " " + proteins + " " +
//                    fats + " " + carbohydrates + " " + kilocalories, Toast.LENGTH_SHORT).show()
//            val foodItem = FoodItem(title!!, proteins, fats, carbohydrates, kilocalories)
//            //mFoodItemViewModel!!.insert(foodItem)
//            Toast.makeText(this, getString(R.string.toast_product_added), Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, getString(R.string.toast_product_not_added), Toast.LENGTH_SHORT).show()
//        }
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.delete_all_food_items -> {
//                //mFoodItemViewModel!!.deleteAllFoodItems()
//                Toast.makeText(this, getString(R.string.toast_all_product_deleted),
//                        Toast.LENGTH_SHORT).show()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        return true
    }

    companion object {
        const val ADD_FOOD_ITEM_REQUEST = 1
    }
}