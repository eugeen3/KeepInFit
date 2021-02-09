package eugeen3.keepinfit.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import eugeen3.keepinfit.R
import eugeen3.keepinfit.adapters.FoodItemAdapter
import eugeen3.keepinfit.databinding.FragmentMealBinding
import eugeen3.keepinfit.entities.FoodItem
import eugeen3.keepinfit.viewmodels.SharedViewModel

class MealFragment : BaseFragment(R.layout.fragment_meal) {

    private lateinit var mSharedViewModel: SharedViewModel
    private lateinit var mAdapter: FoodItemAdapter
    private var mBinding: FragmentMealBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMealBinding.bind(view)
        mBinding = binding
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        setupViewModel()
        initInterface()
    }

    private fun setupViewModel() {
        mSharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        mSharedViewModel.allFoodItems.observe(this, { foodItems: List<FoodItem?>? ->
            mAdapter.setFoodItems(foodItems)
            mAdapter.setFoodItemsFull(foodItems)
            mAdapter.setSharedViewModel(mSharedViewModel)
        })
    }

    private fun initInterface() {
        createRecycler()
        createButton()
    }

    private fun createButton() {
        mBinding?.rvEating?.setOnClickListener {
            this.fragmentManager
                    ?.beginTransaction()
                    ?.addToBackStack(null)
                    ?.replace(R.id.vFragmentContainer, FoodItemsListFragment())
                    ?.commit()
        }
    }

    private fun createRecycler() {
        mAdapter = FoodItemAdapter()

        mBinding?.rvEating?.layoutManager = LinearLayoutManager(activity)
        mBinding?.rvEating?.setHasFixedSize(true)
        mBinding?.rvEating?.adapter = mAdapter

//        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(mAdapter)
//        val touchHelper = ItemTouchHelper(callback)
//        touchHelper.attachToRecyclerView(mBinding?.rvEating)
    }
}