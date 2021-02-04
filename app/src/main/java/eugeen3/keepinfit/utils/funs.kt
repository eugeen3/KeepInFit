package eugeen3.keepinfit.utils

import android.app.Application
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import eugeen3.keepinfit.R

class funs {
    fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true) {
        if (addStack) {
            supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.vFragmentContainer, fragment)
                    .commit()
        } else {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.vFragmentContainer, fragment)
                    .commit()
        }
    }

    fun Application.showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
    }
}