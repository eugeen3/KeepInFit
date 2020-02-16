package eugeen3.keepinfit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import eugeen3.keepinfit.R;
import eugeen3.keepinfit.adapters.FoodItemAdapter;
import eugeen3.keepinfit.entities.FoodItem;
import eugeen3.keepinfit.viewmodels.FoodItemViewModel;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_FOOD_ITEM_REQUEST = 1;

    private FoodItemViewModel foodItemViewModel;
    FoodItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddFoodItem = findViewById(R.id.btnAddFoodItem);
        buttonAddFoodItem.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, AddFoodItemToDB.class);
            startActivityForResult(intent, ADD_FOOD_ITEM_REQUEST);
        });

        RecyclerView recyclerView = findViewById(R.id.rv_food_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new FoodItemAdapter();
        recyclerView.setAdapter(adapter);

        foodItemViewModel = ViewModelProviders.of(this).get(FoodItemViewModel.class);
        foodItemViewModel.getAllFoodItems().observe(this, (List<FoodItem> foodItems) -> {
            adapter.setFoodItems(foodItems);
            adapter.setFoodItemsFull(foodItems);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                foodItemViewModel.delete(adapter.getFoodItemAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Продукт удалён", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_FOOD_ITEM_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddFoodItemToDB.EXTRA_TITLE);
            float proteins = data.getFloatExtra(AddFoodItemToDB.EXTRA_PROTEINS, 0f);
            float fats = data.getFloatExtra(AddFoodItemToDB.EXTRA_FATS, 0f);
            float carbohydrates = data.getFloatExtra(AddFoodItemToDB.EXTRA_CARBOHYDRATES, 0f);
            int kcals = data.getIntExtra(AddFoodItemToDB.EXTRA_KCALS, 0);

            Toast.makeText(this, title + " " + proteins + " " +
                    fats + " " + carbohydrates + " " + kcals, Toast.LENGTH_SHORT).show();

            FoodItem foodItem = new FoodItem(title, proteins, fats, carbohydrates, kcals);
            foodItemViewModel.insert(foodItem);

            Toast.makeText(this, "Продукт добавлен", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Продукт не добавлен", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_food_items:
                foodItemViewModel.deleteAllFoodItems();
                Toast.makeText(this, "Все продукты удалены", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
