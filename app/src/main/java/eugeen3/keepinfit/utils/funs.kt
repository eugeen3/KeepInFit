package eugeen3.keepinfit.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import eugeen3.keepinfit.R

class funs {
    fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true) {
        if (addStack) {
            supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragmentContainer, fragment)
                    .commit()
        } else {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit()
        }
    }
}