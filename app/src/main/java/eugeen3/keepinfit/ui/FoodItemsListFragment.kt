package eugeen3.keepinfit.ui

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import eugeen3.keepinfit.R
import eugeen3.keepinfit.adapters.FoodItemAdapter
import eugeen3.keepinfit.databinding.FragmentFoodItemBinding
import eugeen3.keepinfit.databinding.FragmentFoodItemsListBinding
import eugeen3.keepinfit.entities.FoodItem
import eugeen3.keepinfit.itemtouch.SimpleItemTouchHelperCallback
import eugeen3.keepinfit.utils.replaceFragment
import eugeen3.keepinfit.viewmodels.SharedViewModel

class FoodItemsListFragment : BaseFragment(R.layout.fragment_food_items_list) {

    private var mBinding: FragmentFoodItemsListBinding? = null
    private val binding get() = mBinding!!
    private lateinit var mSharedViewModel: SharedViewModel
    private lateinit var mAdapter: FoodItemAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFoodItemsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
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
        mBinding?.btnAddFoodItem?.setOnClickListener {
            replaceFragment(FoodItemFragment())
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

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

}