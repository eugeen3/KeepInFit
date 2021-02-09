package eugeen3.keepinfit.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import eugeen3.keepinfit.R
import eugeen3.keepinfit.adapters.FoodItemAdapter
import eugeen3.keepinfit.databinding.FragmentFoodItemsListBinding
import eugeen3.keepinfit.entities.FoodItem
import eugeen3.keepinfit.itemtouch.SimpleItemTouchHelperCallback
import eugeen3.keepinfit.viewmodels.SharedViewModel

class FoodItemsListFragment : BaseFragment(R.layout.fragment_food_items_list) {

    private var mBinding: FragmentFoodItemsListBinding? = null
    private lateinit var mSharedViewModel: SharedViewModel
    private lateinit var mAdapter: FoodItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFoodItemsListBinding.bind(view)
        mBinding = binding
    }

    override fun onDestroyView() {
        mBinding = null

        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
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
        mBinding?.btnAddFoodItem?.setOnClickListener {
            this.fragmentManager
                    ?.beginTransaction()
                    ?.addToBackStack(null)
                    ?.replace(R.id.vFragmentContainer, FoodItemFragment())
                    ?.commit()
        }
    }

    private fun createRecycler() {
        mAdapter = FoodItemAdapter()

        mBinding?.rvFoodItems?.layoutManager = LinearLayoutManager(activity)
        mBinding?.rvFoodItems?.setHasFixedSize(true)
        mBinding?.rvFoodItems?.adapter = mAdapter

        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(mAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(mBinding?.rvFoodItems)
    }

        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            activity?.menuInflater?.inflate(R.menu.main_menu, menu)
            val searchItem = menu.findItem(R.id.action_search)
            val searchView = searchItem.actionView as SearchView
            searchView.imeOptions = EditorInfo.IME_ACTION_DONE
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    mAdapter.filter.filter(newText)
                    return false
                }
            })
        }
}