package eugeen3.keepinfit.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import eugeen3.keepinfit.R

fun replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    if (addStack) {
        ACTIVITY.supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.vFragmentContainer, fragment)
                .commit()
    } else {
        ACTIVITY.supportFragmentManager.beginTransaction()
                .replace(R.id.vFragmentContainer, fragment)
                .commit()
    }
}

fun showToast(message: String) {
    Toast.makeText(ACTIVITY, message, Toast.LENGTH_SHORT).show()
}
