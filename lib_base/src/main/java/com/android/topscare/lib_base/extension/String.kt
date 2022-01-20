package com.android.topscare.lib_base.extension

import android.net.Uri
import android.text.Html
import android.widget.TextView
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.core.util.PatternsCompat
import androidx.databinding.BindingAdapter
import java.lang.Exception
import java.text.DecimalFormat
import java.util.regex.Pattern

fun String.isEmail(): Boolean = PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()
fun String.toUri(): Uri = Uri.parse(this)
fun String.toFormattedHtml(): String = this.replace("<blockquote>", "<p>").replace("</blockquote>", "</p>")
fun String.isPhoneNumber(): Boolean {
    if (startsWith("+")) {
        return Pattern.compile("^(\\+66|\\+65|\\+60|\\+86|\\+852|\\+44|\\+1)[0-9]{8,11}\$")
            .matcher(this).matches()
    } else if (startsWith("0")) {
        return Pattern.compile("[0-9]{10}\$").matcher(this).matches()
    }
    return false
}

@BindingAdapter("app:htmlText")
fun setHtmlText(view: TextView, htmlText: String?) {
    htmlText?.let {
        view.text = Html.fromHtml(htmlText, FROM_HTML_MODE_LEGACY)
    }
}

@BindingAdapter("app:firstCapText")
fun setFirstCharAsCapital(view: TextView, text: String?) {
    text?.let {
        view.text = text.capitalize()
    }
}

fun String.toCurrencyFormat() : String {
    var i : Double = 0.0
    try {
        i = this.toDouble()
    }catch (ex: Exception){
        ex.printStackTrace()
    }
    val formatter = DecimalFormat("###,###,##0.00")
    return formatter.format(i)
}

fun String.toCurrencyFormatWithoutDecimal() : String {
    var i : Double = 0.0
    try {
        i = this.toDouble()
    }catch (ex: Exception){
        ex.printStackTrace()
    }
    val formatter = DecimalFormat("###,###,##0")
    return formatter.format(i)
}