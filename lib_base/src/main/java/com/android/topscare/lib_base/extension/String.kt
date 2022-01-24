package com.android.topscare.lib_base.extension

import android.net.Uri
import android.os.Parcel
import android.text.Html
import android.util.Patterns
import android.widget.TextView
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.core.util.PatternsCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.datepicker.CalendarConstraints
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun String.isEmail(): Boolean = PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()
fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()
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
fun String.isDateFormat(dateFormat: String) : Boolean{
    return try {
        doFormatDate(this,dateFormat)
        true
    }catch (ex: ParseException){
        false
    }
}
fun String.toDateFormat() : String{
    var data = ""
    try {
        var formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = formatter.parse(this)
        val format = SimpleDateFormat("dd/MM/yyy")
        data = format.format(date)
    }catch (ex: Exception){
        ex.printStackTrace()
    }
    return data
}

@Throws(ParseException::class)
fun doFormatDate(dateStr: String, formatSrt : String) {
    var formatter = SimpleDateFormat(formatSrt, Locale.getDefault())
    val date = formatter.parse(dateStr)
    println(date)
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