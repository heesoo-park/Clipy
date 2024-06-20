package com.example.clipy.utils

import android.view.View

class OnSingleClickListener(
    private val onSingleCLick: (View) -> Unit
): View.OnClickListener {

    companion object {
        const val CLICK_INTERVAL = 500
    }

    private var lastClickedTime = 0L

    private fun isSafe(): Boolean {
        return System.currentTimeMillis() - lastClickedTime > CLICK_INTERVAL
    }

    override fun onClick(view: View?) {
        if (isSafe() && view != null) {
            onSingleCLick(view)
        }

        lastClickedTime = System.currentTimeMillis()
    }
}