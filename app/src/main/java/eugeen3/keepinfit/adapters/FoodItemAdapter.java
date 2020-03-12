package eugeen3.keepinfit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eugeen3.keepinfit.R;
import eugeen3.keepinfit.entities.FoodItem;
import eugeen3.keepinfit.itemtouch.ItemTouchHelperAdapter;

import static java.lang.String.valueOf;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> implements Filterable, ItemTouchHelperAdapter {
    private List<FoodItem> foodItems;
    private List<FoodItem> foodItemsFull;
    private final static String PORTION_WEIGHT = "в 100 гр:";

    @Override
    @NonNull
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

    public void setFoodItemsFull(List<FoodItem> foodItems) {
        this.foodItemsFull = new ArrayList<>(foodItems);
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List <FoodItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(foodItemsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (FoodItem foodItem: foodItemsFull) {
                    if (foodItem.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(foodItem);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            foodItems.clear();
            foodItems.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public void onItemDismiss(int position) {
        foodItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(foodItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(foodItems, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }
}