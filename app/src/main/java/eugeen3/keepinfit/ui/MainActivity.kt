package eugeen3.keepinfit.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import eugeen3.keepinfit.R
import eugeen3.keepinfit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MenuItem.OnActionExpandListener {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportFragmentManager.beginTransaction()
                .add(R.id.vFragmentContainer, FoodItemsListFragment())
                .commit()

    }

    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        return true
    }
}