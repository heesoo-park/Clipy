package com.example.clipy.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.clipy.R

class ClearEditText(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
) : AppCompatEditText(context, attrs, defStyleAttr), TextWatcher, View.OnTouchListener, View.OnFocusChangeListener {

    private var clearDrawable: Drawable? = null
    private var customOnFocusChangeListener: OnFocusChangeListener? = null
    private var customOnTouchListener: OnTouchListener? = null

    init {
        init()
    }

    private fun init() {
        val tempDrawable = ContextCompat.getDrawable(context, R.drawable.ic_example)
        clearDrawable = DrawableCompat.wrap(tempDrawable!!)
        DrawableCompat.setTintList(clearDrawable!!, hintTextColors)
        clearDrawable!!.setBounds(0, 0, clearDrawable!!.intrinsicWidth, clearDrawable!!.intrinsicHeight)
        setClearIconVisible(false)
        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        addTextChangedListener(this)
    }

    constructor(context: Context) : this(context, null, android.R.attr.editTextStyle)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, android.R.attr.editTextStyle)

    override fun setOnFocusChangeListener(onFocusChangeListener: OnFocusChangeListener) {
        this.customOnFocusChangeListener = onFocusChangeListener
    }

    override fun setOnTouchListener(onTouchListener: OnTouchListener) {
        this.customOnTouchListener = onTouchListener
    }

    private fun setClearIconVisible(visible: Boolean) {
        clearDrawable?.setVisible(visible, false)
        setCompoundDrawables(null, null, if (visible) clearDrawable else null, null)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (isFocused) {
            setClearIconVisible(s.isNotEmpty())
        }
    }

    override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean {
        val x = motionEvent.x.toInt()
        if (clearDrawable!!.isVisible && x > width - paddingRight - clearDrawable!!.intrinsicWidth) {
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                error = null
                text = null
            }
            return true
        }
        return customOnTouchListener?.onTouch(view, motionEvent) ?: false
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (hasFocus) {
            setClearIconVisible(text?.isNotEmpty() == true)
        } else {
            setClearIconVisible(false)
        }
        customOnFocusChangeListener?.onFocusChange(view, hasFocus)
    }
}