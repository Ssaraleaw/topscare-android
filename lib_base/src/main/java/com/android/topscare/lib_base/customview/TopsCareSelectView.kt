package com.android.topscare.lib_base.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.android.topscare.lib_base.R
import com.android.topscare.lib_base.databinding.LayoutTopsCareSelectViewBinding
import com.android.topscare.lib_base.state.DataState

class TopsCareSelectView : LinearLayout {
    var binding: LayoutTopsCareSelectViewBinding
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
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_tops_care_select_view, this, true)
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

    private fun bindActions(binding: LayoutTopsCareSelectViewBinding) {
        binding.editText.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_UP -> //Do Something
                    if ((event.eventTime - event.downTime < 1000) && binding.editText.compoundDrawables[2] != null && binding.uiState?.isDisable() !=true) {
                        onEndDrawableClicked()
                    }
            }
            v?.onTouchEvent(event) ?: true
        }
    }
}

@BindingAdapter("app:textAttrChanged")
fun TopsCareSelectView.setTextAttrChanged(listener: InverseBindingListener) {
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
fun TopsCareSelectView.setOnClickEndDrawable(callback: () -> Unit) {
    onEndDrawableClicked = callback
}

@BindingAdapter("app:text")
fun TopsCareSelectView.setText(value: String?) {
    if (value ?: "" != binding.editText.text.toString()) {
        binding.editText.setText(value)
    }
}

@BindingAdapter("app:maxLength")
fun TopsCareSelectView.setMaxLength(length: Int) {
    binding.maxLength = length
}

@InverseBindingAdapter(attribute = "app:text")
fun TopsCareSelectView.getText(): String {
    return binding.editText.text.toString()
}

@BindingAdapter("app:hint")
fun TopsCareSelectView.setHint(value: String?) {
    binding.hint = value ?: ""
}

@BindingAdapter("app:uiState")
fun TopsCareSelectView.setUiState(state: DataState<Unit>?){
    state?.let {
        binding.uiState = it
    }
}
