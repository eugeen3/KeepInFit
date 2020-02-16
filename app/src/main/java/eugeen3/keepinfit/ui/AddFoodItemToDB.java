package eugeen3.keepinfit.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import eugeen3.keepinfit.R;

public class AddFoodItemToDB extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextProteins;
    private EditText editTextFats;
    private EditText editTextCarbohydrates;
    private EditText editTextKcals;

    private final static String TITLE = "Добавьте новый продукт";

    public static final String EXTRA_TITLE = "eugeen3.keepinfit.ui.EXTRA_TITLE";
    public static final String EXTRA_PROTEINS = "eugeen3.keepinfit.ui.EXTRA_PROTEINS";
    public static final String EXTRA_FATS = "eugeen3.keepinfit.ui.EXTRA_FATS";
    public static final String EXTRA_CARBOHYDRATES = "eugeen3.keepinfit.ui.EXTRA_CARBOHYDRATES";
    public static final String EXTRA_KCALS = "eugeen3.keepinfit.ui.EXTRA_KCALS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item_to_db);

        editTextTitle = findViewById(R.id.edit_text_food_item_title);
        editTextProteins = findViewById(R.id.edit_text_food_item_proteins);
        editTextFats = findViewById(R.id.edit_text_food_item_fats);
        editTextCarbohydrates = findViewById(R.id.edit_text_food_item_carbohydrates);
        editTextKcals = findViewById(R.id.edit_text_food_item_kcals);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle(TITLE);
    }

    private void saveFoodItem() {
        String title = editTextTitle.getText().toString();
        float proteins = 0f, fats = 0f, carbohydrates = 0f;
        int kcals = 0;

        proteins = Float.parseFloat(editTextProteins.getText().toString());
        fats = Float.parseFloat(editTextFats.getText().toString());
        carbohydrates = Float.parseFloat(editTextCarbohydrates.getText().toString());
        kcals = Integer.parseInt(editTextKcals.getText().toString());

        if (title.isEmpty()) {
            Toast.makeText(this, "Введите название", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_PROTEINS, proteins);
        data.putExtra(EXTRA_FATS, fats);
        data.putExtra(EXTRA_CARBOHYDRATES, carbohydrates);
        data.putExtra(EXTRA_KCALS, kcals);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_food_item_to_db, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_food_item_in_db:
                saveFoodItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
