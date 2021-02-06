package eugeen3.keepinfit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eugeen3.keepinfit.R
import eugeen3.keepinfit.entities.FoodItem
import eugeen3.keepinfit.itemtouch.ItemTouchHelperAdapter
import eugeen3.keepinfit.viewmodels.SharedViewModel
import java.util.*

class FoodItemAdapter : RecyclerView.Adapter<FoodItemAdapter.ViewHolder>(), Filterable, ItemTouchHelperAdapter {

    private var foodItems: MutableList<FoodItem>? = null
    private var foodItemsFull: MutableList<FoodItem>? = null
    private var sharedViewModel: SharedViewModel? = null

    fun setSharedViewModel(sharedViewModel: SharedViewModel?) {
        this.sharedViewModel = sharedViewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.base_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFoodItem = foodItems!![position]
        holder.nameView.text = currentFoodItem.name
        holder.massView.text = PORTION_WEIGHT
        holder.proteinsView.text = currentFoodItem.proteins.toString()
        holder.carbohydratesView.text = currentFoodItem.carbohydrates.toString()
        holder.fatsView.text = currentFoodItem.fats.toString()
        holder.kcalsView.text = currentFoodItem.kilocalories.toString()
    }

    override fun getItemCount(): Int {
        return if (foodItems == null) 0 else foodItems!!.size
    }

    fun setFoodItems(foodItems: List<FoodItem?>?) {
        this.foodItems = ArrayList(foodItems)
        notifyDataSetChanged()
    }

    fun setFoodItemsFull(foodItems: List<FoodItem?>?) {
        foodItemsFull = ArrayList(foodItems)
        notifyDataSetChanged()
    }

    fun getFoodItemAt(position: Int): FoodItem? {
        return foodItems!![position]
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView
        val massView: TextView
        val proteinsView: TextView
        val carbohydratesView: TextView
        val fatsView: TextView
        val kcalsView: TextView

        init {
            nameView = view.findViewById(R.id.tvCardTitle)
            massView = view.findViewById(R.id.tvCardSubtitle)
            proteinsView = view.findViewById(R.id.tvProteinsValue)
            carbohydratesView = view.findViewById(R.id.tvCarbohydratesValue)
            fatsView = view.findViewById(R.id.tvFatsValue)
            kcalsView = view.findViewById(R.id.tvKilocaloriesValue)
        }
    }

    override fun getFilter(): Filter {
        return filter
    }

    private val filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
//            val filteredList: MutableList<FoodItem> = ArrayList()
//            if (constraint.isEmpty()) {
//                filteredList.addAll(foodItemsFull as Array<FoodItem>)
//            } else {
//                val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
//                for (foodItem in foodItemsFull!!) {
//                    if (foodItem.name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
//                        filteredList.add(foodItem)
//                    }
//                }
//            }
//            val results = FilterResults()
//            results.values = filteredList
//            return results
            val charSearch = constraint.toString()
            if (charSearch.isEmpty()) {
                foodItems = foodItemsFull
            } else {
                val resultList = ArrayList<FoodItem>()
                for (FoodItem in foodItemsFull!!) {
                    if (FoodItem.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                        resultList.add(FoodItem)
                    }
                }
                foodItems = resultList
            }
            val filterResults = FilterResults()
            filterResults.values = foodItems
            return filterResults
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            foodItems?.clear()
            foodItems?.addAll(results?.values as Collection<FoodItem>)
            notifyDataSetChanged()
        }
    }

    override fun onItemDismiss(position: Int) {
        sharedViewModel?.delete(foodItems?.get(position))
        foodItems?.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(foodItems, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(foodItems, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    companion object {
        private const val PORTION_WEIGHT = "в 100 гр:"
    }
}