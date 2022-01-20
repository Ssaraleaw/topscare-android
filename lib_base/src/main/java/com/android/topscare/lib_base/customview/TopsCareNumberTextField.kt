package com.android.topscare.lib_base.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.android.topscare.lib_base.R
import com.android.topscare.lib_base.databinding.LayoutTopsCareNumberTextFieldBinding
import com.android.topscare.lib_base.databinding.LayoutTopsCareTextFieldBinding
import com.android.topscare.lib_base.state.DataState

class TopsCareNumberTextField : LinearLayout {
    var binding: LayoutTopsCareNumberTextFieldBinding
    var onEndDrawableClicked: () -> Unit = {}

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_tops_care_number_text_field, this, true)
        bindActions(binding)
    }

    fun setText(text: String) {
        binding.text = text
    }

    fun setTitle(title: String) {
        binding.title = title
    }

    fun setHint(hint: String) {
        binding.hint = hint
    }

    fun setMaxLength(length: Int) {
        binding.maxLength = length
    }

    fun setEndTitleDrawable(drawable: Drawable) {
        binding.endTextDrawable = drawable
    }

    fun setTextWatcher(textWatcher: TextWatcher) {
        binding.editText.addTextChangedListener(textWatcher)
    }

    fun setUiState(state: DataState<Unit>) {
        binding.uiState = state
    }

    private fun bindActions(binding: LayoutTopsCareNumberTextFieldBinding) {

        binding.editText.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_UP -> //Do Something
                    if ((event.eventTime - event.downTime < 1000) && binding.editText.compoundDrawables[2] != null && event.rawX >= (binding.editText.right - binding.editText.compoundDrawables[2].bounds.width()) && (binding?.uiState?.isDisable() != true)) {
                        onEndDrawableClicked()
                    }
            }
            v?.onTouchEvent(event) ?: true
        }


        binding.editText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            binding.isTextFocused = hasFocus
        }
    }

    private fun hideSoftKeyboard(input: EditText) {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(
            input.windowToken,
            0
        )
    }
}

@BindingAdapter("app:textAttrChanged")
fun TopsCareNumberTextField.setTextAttrChanged(listener: InverseBindingListener) {
    if (listener == null)
        return

    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            listener.onChange()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
    binding.editText.addTextChangedListener(textWatcher)
}

@BindingAdapter("app:onClickEndDrawable")
fun TopsCareNumberTextField.setOnClickEndDrawable(callback: () -> Unit) {
    onEndDrawableClicked = callback
}

@BindingAdapter("app:text")
fun TopsCareNumberTextField.setText(value: String?) {
    if (value ?: "" != binding.editText.text.toString()) {
        binding.editText.setText(value)
    }
}

@BindingAdapter("app:maxLength")
fun TopsCareNumberTextField.setMaxLength(length: Int) {
    binding.maxLength = length
}

@InverseBindingAdapter(attribute = "app:text")
fun TopsCareNumberTextField.getText(): String {
    return binding.editText.text.toString()
}

@BindingAdapter("app:hint")
fun TopsCareNumberTextField.setHint(value: String?) {
    binding.hint = value ?: ""
}

@BindingAdapter("app:readOnly")
fun TopsCareNumberTextField.setReadOnly(value: Boolean) {
    binding.editText.isEnabled = !value
}

@BindingAdapter("app:uiState")
fun TopsCareNumberTextField.setUiState(state: DataState<Unit>?){
    state?.let {
        binding.uiState = it
    }
}