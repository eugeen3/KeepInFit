package eugeen3.keepinfit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import eugeen3.keepinfit.R;
import eugeen3.keepinfit.entities.FoodItem;

import static java.lang.String.valueOf;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> {

    private List<FoodItem> foodItems;
    private final static String PORTION_WEIGHT = "в 100 гр:";

    @Override
    public FoodItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodItemAdapter.ViewHolder holder, int position) {
        FoodItem currentFoodItem = foodItems.get(position);
        holder.nameView.setText(currentFoodItem.getName());
        holder.massView.setText(PORTION_WEIGHT);
        holder.proteinsView.setText(valueOf(currentFoodItem.getProteins()));
        holder.carbohydratesView.setText(valueOf(currentFoodItem.getCarbohydrates()));
        holder.fatsView.setText(valueOf(currentFoodItem.getFats()));
        holder.kcalsView.setText(valueOf(currentFoodItem.getKcals()));
    }

    @Override
    public int getItemCount() {
        return foodItems == null ? 0 : foodItems.size();
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
        notifyDataSetChanged();
    }

    public FoodItem getFoodItemAt(int position) {
        return  foodItems.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView, massView, proteinsView, carbohydratesView, fatsView, kcalsView;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.foodName);
            massView = view.findViewById(R.id.foodMass);
            proteinsView = view.findViewById(R.id.foodProtValue);
            carbohydratesView = view.findViewById(R.id.foodCarbValue);
            fatsView = view.findViewById(R.id.foodFatValue);
            kcalsView = view.findViewById(R.id.foodKcalValue);
        }
    }
}