package eugeen3.keepinfit.ui

import android.app.ActionBar
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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

    //TODO close button and title

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFoodItemBinding.bind(view)
        mBinding = binding
        setHasOptionsMenu(true)
        val actionBar: ActionBar? = activity?.actionBar
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
                val newFoodItem: FoodItem? = createFoodItem()
                if (newFoodItem != null) {
                    sharedViewModel.insert(newFoodItem)
                    fragmentManager
                            ?.beginTransaction()
                            ?.replace(R.id.vFragmentContainer, FoodItemsListFragment())
                            ?.commit()
                }
            }
        }
        return true
    }

    private fun createFoodItem(): FoodItem? {
        return if (validateAll())
            FoodItem(
                    mBinding?.etFoodItemTitle?.text.toString(),
                    parseFloat(mBinding?.etFoodItemProteins?.text.toString()),
                    parseFloat(mBinding?.etFoodItemFats?.text.toString()),
                    parseFloat(mBinding?.etFoodItemCarbohydrates?.text.toString()),
                    parseInt(mBinding?.etFoodItemKilocalories?.text.toString())
            )
        else {
            null
        }
    }

    private fun validateAll(): Boolean {
        return if (validateTitle() &&
                validateProteins() &&
                validateFats() &&
                validateCarbohydrates() &&
                validateKilocalories()) {
            if (parseFloat(mBinding?.etFoodItemProteins?.text.toString()) +
                    parseFloat(mBinding?.etFoodItemFats?.text.toString()) +
                    parseFloat(mBinding?.etFoodItemCarbohydrates?.text.toString()) > 100) {
                Toast.makeText(activity,
                        getString(R.string.toastCheckIfDataCorrect), Toast.LENGTH_SHORT).show()
                false
            } else true
        } else {
            false
        }
    }

    private fun validateTitle(): Boolean {
        return when {
            mBinding?.etFoodItemTitle?.text?.isEmpty() == true -> {
                mBinding?.etFoodItemTitle?.error =
                        getString(R.string.etEmptyTitleError)
                false
            }
            else -> true
        }
    }

    private fun validateProteins(): Boolean {
        return when {
            mBinding?.etFoodItemProteins?.text?.isEmpty() == true -> {
                mBinding?.etFoodItemProteins?.error =
                        getString(R.string.etEmptyNumberError)
                false
            }
            parseFloat(mBinding?.etFoodItemProteins?.text.toString()) > 100 -> {
                mBinding?.etFoodItemProteins?.error =
                        getString(R.string.etInputNumberWrongValueError)
                false
            }
            else -> {
                mBinding?.etFoodItemProteins?.error = null
                true
            }
        }
    }

    private fun validateFats(): Boolean {
        return when {
            mBinding?.etFoodItemFats?.text?.isEmpty() == true -> {
                mBinding?.etFoodItemFats?.error =
                        getString(R.string.etEmptyNumberError)
                false
            }
            parseFloat(mBinding?.etFoodItemFats?.text.toString()) > 100 -> {
                mBinding?.etFoodItemFats?.error =
                        getString(R.string.etInputNumberWrongValueError)
                false
            }
            else -> {
                mBinding?.etFoodItemFats?.error = null
                true
            }
        }
    }

    private fun validateCarbohydrates(): Boolean {
        return when {
            mBinding?.etFoodItemCarbohydrates?.text?.isEmpty() == true -> {
                mBinding?.etFoodItemCarbohydrates?.error =
                        getString(R.string.etEmptyNumberError)
                false
            }
            parseFloat(mBinding?.etFoodItemCarbohydrates?.text.toString()) > 100 -> {
                mBinding?.etFoodItemCarbohydrates?.error =
                        getString(R.string.etInputNumberWrongValueError)
                false
            }
            else -> {
                mBinding?.etFoodItemCarbohydrates?.error = null
                true
            }
        }
    }

    private fun validateKilocalories(): Boolean {
        return when {
            mBinding?.etFoodItemKilocalories?.text?.isEmpty() == true -> {
                mBinding?.etFoodItemKilocalories?.error =
                        getString(R.string.etEmptyNumberError)
                false
            }
            parseInt(mBinding?.etFoodItemKilocalories?.text.toString()) >= 1000 -> {
                mBinding?.etFoodItemKilocalories?.error =
                        getString(R.string.etInputNumberWrongValueKilocaloriesError)
                false
            }
            else -> {
                mBinding?.etFoodItemKilocalories?.error = null
                true
            }
        }
    }
}