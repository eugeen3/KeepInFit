package eugeen3.keepinfit.ui

import android.app.ActionBar
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import eugeen3.keepinfit.R
import eugeen3.keepinfit.databinding.FragmentFoodItemBinding
import eugeen3.keepinfit.entities.FoodItem
import eugeen3.keepinfit.viewmodels.SharedViewModel
import java.lang.Float.parseFloat
import java.lang.Integer.parseInt

class FoodItemFragment : BaseFragment(R.layout.fragment_food_item) {
    private var mBinding: FragmentFoodItemBinding? = null
    private lateinit var sharedViewModel: SharedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFoodItemBinding.bind(view)
        mBinding = binding
        setHasOptionsMenu(true)
        val actionBar : ActionBar? = activity?.actionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        actionBar?.title = getString(R.string.FoodItemFragmentTitle)
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_add_food_item_to_db, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_food_item_in_db -> {
                sharedViewModel.insert(createFoodItem())
                fragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.vFragmentContainer,FoodItemsListFragment())
                        ?.commit()
            }
        }
        return true
    }

    fun createFoodItem() : FoodItem{
        return FoodItem(
                mBinding?.etFoodItemTitle?.text.toString(),
                parseFloat(mBinding?.etFoodItemProteins?.text.toString()),
                parseFloat(mBinding?.etFoodItemFats?.text.toString()),
                parseFloat(mBinding?.etFoodItemCarbohydrates?.text.toString()),
                parseInt(mBinding?.etFoodItemKilocalories?.text.toString())
        )
    }
}