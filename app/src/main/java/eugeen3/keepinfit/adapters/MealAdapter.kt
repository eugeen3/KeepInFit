package eugeen3.keepinfit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eugeen3.keepinfit.R
import eugeen3.keepinfit.databinding.BaseItemBinding
import eugeen3.keepinfit.entities.FoodItem
import eugeen3.keepinfit.entities.Meal

class MealAdapter : RecyclerView.Adapter<MealAdapter.ViewHolder>() {

    private var mMealsList: MutableList<Meal>? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = BaseItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.base_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvCardTitle.text = mMealsList!![position].name
            tvCardSubtitle.text = mMealsList!![position].foodItems?.size.toString()
            tvProteinsValue.text = calculateSum(mMealsList!![position].foodItems, PROTEINS)
            tvFatsValue.text = calculateSum(mMealsList!![position].foodItems, FATS)
            tvCarbohydratesValue.text = calculateSum(mMealsList!![position].foodItems, CARBOHYDRATES)
            tvKilocaloriesValue.text = calculateSum(mMealsList!![position].foodItems, KILOCALORIES)
//            ibEdit.setOnClickListener {
//                sharedViewModel?.setFoodItem(mMealsList!![position])
//                TODO("open fooditemfragment")
//            }
        }
    }

    override fun getItemCount(): Int {
        return mMealsList?.size ?: 0
    }

    private fun calculateSum(list: List<FoodItem>?, param: String): String? {
        when (param) {
            PROTEINS -> {
                var sum = 0F
                if (list != null) {
                    for (i in list) {
                        sum += i.proteins
                    }
                }
                return sum.toString()
            }
            FATS -> {
                var sum = 0F
                if (list != null) {
                    for (i in list) {
                        sum += i.fats
                    }
                }
                return sum.toString()
            }
            CARBOHYDRATES -> {
                var sum = 0F
                if (list != null) {
                    for (i in list) {
                        sum += i.carbohydrates
                    }
                }
                return sum.toString()
            }
            KILOCALORIES -> {
                var sum = 0
                if (list != null) {
                    for (i in list) {
                        sum += i.kilocalories
                    }
                }
                return sum.toString()
            }
            else -> return null
        }
    }

    companion object {
        private const val PROTEINS = "PROTEINS"
        private const val FATS = "FATS"
        private const val CARBOHYDRATES = "CARBOHYDRATES"
        private const val KILOCALORIES = "KILOCALORIES"
    }
}