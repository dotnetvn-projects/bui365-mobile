package bui365.mobile.main.util

import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created by ASUS on 3/11/2018.
 */

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}

fun View.showSnackBarAction(message: String, duration: Int, action: Snackbar.() -> Unit) {
    Snackbar.make(this, message, duration).apply { action() }.show()
}
