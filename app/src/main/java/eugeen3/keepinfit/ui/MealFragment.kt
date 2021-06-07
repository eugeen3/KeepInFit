package eugeen3.keepinfit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import eugeen3.keepinfit.R
import eugeen3.keepinfit.adapters.FoodItemAdapter
import eugeen3.keepinfit.databinding.FragmentFoodItemsListBinding
import eugeen3.keepinfit.databinding.FragmentMealBinding
import eugeen3.keepinfit.entities.FoodItem
import eugeen3.keepinfit.utils.replaceFragment
import eugeen3.keepinfit.viewmodels.SharedViewModel

class MealFragment : BaseFragment(R.layout.fragment_meal) {
    private lateinit var mSharedViewModel: SharedViewModel
    private lateinit var mAdapter: FoodItemAdapter
    private var mBinding: FragmentMealBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupViewModel()
        initInterface()
    }

    private fun setupViewModel() {
        mSharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
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
            replaceFragment(FoodItemsListFragment())
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

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}