package eugeen3.keepinfit.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import eugeen3.keepinfit.R;
import eugeen3.keepinfit.entities.FoodItem;
import eugeen3.keepinfit.viewmodels.FoodItemViewModel;

public class MainActivity extends AppCompatActivity {
    private FoodItemViewModel foodItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodItemViewModel = ViewModelProviders.of(this).get(FoodItemViewModel.class);
        foodItemViewModel.getAllFoodItems().observe(this, new Observer<List<FoodItem>>() {
            @Override
            public void onChanged(List<FoodItem> foodItems) {

            }
        });
    }
}
