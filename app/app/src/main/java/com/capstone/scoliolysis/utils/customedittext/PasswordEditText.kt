package com.capstone.scoliolysis.utils.customedittext

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.capstone.scoliolysis.R


class PasswordEditText : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    error = context.getString(R.string.passwordWarning)
                    requestFocus()
                }
                else if (s.toString().length < 6) {
                    error = context.getString(R.string.shortPasswordWarning)
                    requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Enter your password"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
}