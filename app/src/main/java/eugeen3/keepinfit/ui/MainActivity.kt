package eugeen3.keepinfit.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import eugeen3.keepinfit.R
import eugeen3.keepinfit.adapters.FoodItemAdapter
import eugeen3.keepinfit.databinding.ActivityMainBinding
import eugeen3.keepinfit.entities.FoodItem
import eugeen3.keepinfit.itemtouch.SimpleItemTouchHelperCallback
import eugeen3.keepinfit.viewmodels.FoodItemViewModel
import eugeen3.keepinfit.utils.funs

class MainActivity : AppCompatActivity(), MenuItem.OnActionExpandListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var foodItemViewModel: FoodItemViewModel
    private lateinit var adapter: FoodItemAdapter
    private lateinit var recyclerView: RecyclerView
    var container: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
//        val buttonAddFoodItem = findViewById<FloatingActionButton>(R.id.btnAddFoodItem)
//        buttonAddFoodItem.setOnClickListener { v: View? -> supportFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, FoodItemsFragment())
//                .commit()}
        recyclerView = findViewById(R.id.rvFoodItems)
        recyclerView?.setLayoutManager(LinearLayoutManager(this))
        recyclerView?.setHasFixedSize(true)
        adapter = FoodItemAdapter()
        recyclerView?.setAdapter(adapter)
        foodItemViewModel = ViewModelProviders.of(this).get(FoodItemViewModel::class.java)
        foodItemViewModel!!.allFoodItems.observe(this, { foodItems: List<FoodItem?>? ->
            adapter!!.setFoodItems(foodItems)
            adapter!!.setFoodItemsFull(foodItems)
            adapter!!.setFoodItemViewModel(foodItemViewModel)
        })
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter!!)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_FOOD_ITEM_REQUEST && resultCode == RESULT_OK) {
            val title = data!!.getStringExtra(AddFoodItemToDB.EXTRA_TITLE)
            val proteins = data.getFloatExtra(AddFoodItemToDB.EXTRA_PROTEINS, 0f)
            val fats = data.getFloatExtra(AddFoodItemToDB.EXTRA_FATS, 0f)
            val carbohydrates = data.getFloatExtra(AddFoodItemToDB.EXTRA_CARBOHYDRATES, 0f)
            val kilocalories = data.getIntExtra(AddFoodItemToDB.EXTRA_KCALS, 0)
            Toast.makeText(this, title + " " + proteins + " " +
                    fats + " " + carbohydrates + " " + kilocalories, Toast.LENGTH_SHORT).show()
            val foodItem = FoodItem(title!!, proteins, fats, carbohydrates, kilocalories)
            foodItemViewModel!!.insert(foodItem)
            Toast.makeText(this, "Продукт добавлен", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Продукт не добавлен", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter!!.filter.filter(newText)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_food_items -> {
                foodItemViewModel!!.deleteAllFoodItems()
                Toast.makeText(this, "Все продукты удалены", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

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