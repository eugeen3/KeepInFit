package eugeen3.keepinfit.ui

import android.app.ActionBar
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import eugeen3.keepinfit.R
import eugeen3.keepinfit.databinding.FragmentFoodItemBinding
import eugeen3.keepinfit.entities.FoodItem
import eugeen3.keepinfit.utils.replaceFragment
import eugeen3.keepinfit.utils.showToast
import eugeen3.keepinfit.viewmodels.SharedViewModel
import java.lang.Float.parseFloat
import java.lang.Integer.parseInt

class FoodItemFragment(
        private val isOpenedToEdit: Boolean = false
) : Fragment(R.layout.fragment_food_item) {
    private var mBinding: FragmentFoodItemBinding? = null
    private val binding get() = mBinding!!
    private lateinit var oldFoodItem: FoodItem
    private lateinit var sharedViewModel: SharedViewModel

    //TODO close button and title

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFoodItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.foodItemPosition?.let { position ->
            sharedViewModel.allFoodItems.value?.get(position)?.let { foodItem ->
                oldFoodItem = foodItem
                setFoodItemValues(foodItem)
            }
        }

        val actionBar: ActionBar? = activity?.actionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        actionBar?.title = getString(R.string.FoodItemFragmentTitle)
    }

    private fun setFoodItemValues(foodItem: FoodItem) {
        binding.apply {
            etFoodItemTitle.setText(foodItem.name)
            etFoodItemProteins.setText(foodItem.proteins.toString())
            etFoodItemFats.setText(foodItem.fats.toString())
            etFoodItemCarbohydrates.setText(foodItem.carbohydrates.toString())
            etFoodItemKilocalories.setText(foodItem.kilocalories.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_add_food_item_to_db, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_food_item_in_db -> {
                val newFoodItem: FoodItem? = createFoodItem()
                if (newFoodItem != null) {
                    if (isOpenedToEdit) {
                        sharedViewModel.delete(oldFoodItem)
                        sharedViewModel.insert(newFoodItem)
                    } else {
                        sharedViewModel.insert(newFoodItem)
                    }
                    showToast(getString(R.string.toast_data_updated))
                    replaceFragment(FoodItemsListFragment())
                }
            }
        }
        return true
    }

    private fun createFoodItem(): FoodItem? {
        return if (validateAll())
            FoodItem(
                    binding.etFoodItemTitle.text.toString(),
                    parseFloat(binding.etFoodItemProteins.text.toString()),
                    parseFloat(binding.etFoodItemFats.text.toString()),
                    parseFloat(binding.etFoodItemCarbohydrates.text.toString()),
                    parseInt(binding.etFoodItemKilocalories.text.toString())
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
            if (parseFloat(binding.etFoodItemProteins.text.toString()) +
                    parseFloat(binding.etFoodItemFats.text.toString()) +
                    parseFloat(binding.etFoodItemCarbohydrates.text.toString()) > 100) {
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
            binding.etFoodItemTitle.text?.isBlank() == true -> {
                binding.etFoodItemTitle.error =
                        getString(R.string.etEmptyTitleError)
                false
            }
            else -> true
        }
    }

    private fun validateProteins(): Boolean {
        return when {
            binding.etFoodItemProteins.text?.isEmpty() == true -> {
                binding.etFoodItemProteins.error =
                        getString(R.string.etEmptyNumberError)
                false
            }
            parseFloat(binding.etFoodItemProteins.text.toString()) > 100 -> {
                binding.etFoodItemProteins.error =
                        getString(R.string.etInputNumberWrongValueError)
                false
            }
            else -> {
                binding.etFoodItemProteins.error = null
                true
            }
        }
    }

    private fun validateFats(): Boolean {
        return when {
            binding.etFoodItemFats.text?.isEmpty() == true -> {
                binding.etFoodItemFats.error =
                        getString(R.string.etEmptyNumberError)
                false
            }
            parseFloat(binding.etFoodItemFats.text.toString()) > 100 -> {
                binding.etFoodItemFats.error =
                        getString(R.string.etInputNumberWrongValueError)
                false
            }
            else -> {
                binding.etFoodItemFats.error = null
                true
            }
        }
    }

    private fun validateCarbohydrates(): Boolean {
        return when {
            binding.etFoodItemCarbohydrates.text?.isEmpty() == true -> {
                binding.etFoodItemCarbohydrates.error =
                        getString(R.string.etEmptyNumberError)
                false
            }
            parseFloat(binding.etFoodItemCarbohydrates.text.toString()) > 100 -> {
                binding.etFoodItemCarbohydrates.error =
                        getString(R.string.etInputNumberWrongValueError)
                false
            }
            else -> {
                binding.etFoodItemCarbohydrates.error = null
                true
            }
        }
    }

    private fun validateKilocalories(): Boolean {
        return when {
            binding.etFoodItemKilocalories.text?.isEmpty() == true -> {
                binding.etFoodItemKilocalories.error =
                        getString(R.string.etEmptyNumberError)
                false
            }
            parseInt(binding.etFoodItemKilocalories.text.toString()) >= 1000 -> {
                binding.etFoodItemKilocalories.error =
                        getString(R.string.etInputNumberWrongValueKilocaloriesError)
                false
            }
            else -> {
                binding.etFoodItemKilocalories.error = null
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}