package com.android.topscare.lib_base.extension

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.android.topscare.lib_base.R


fun RecyclerView.addLineItemDecoration(@DrawableRes res: Int = R.drawable.veritical_item_divider_grey_100) {
    val itemDecorator =
        DividerItemDecoration(
            context,
            DividerItemDecoration.VERTICAL
        )
    itemDecorator.setDrawable(
        ContextCompat.getDrawable(
            context,
            res
        )!!
    )
    addItemDecoration(itemDecorator)
}

val RecyclerView.ViewHolder.context: Context
    get() = itemView.context

fun ConcatAdapter.safeAddAdapter(
    adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
    position: Int? = null
) {
    if (!adapters.contains(adapter)) {
        if (position != null) {
            addAdapter(position, adapter)
        } else {
            addAdapter(adapter)
        }
    }
}

fun ConcatAdapter.safeRemoveAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
    if (adapters.contains(adapter)) {
        removeAdapter(adapter)
    }
}

fun ConcatAdapter.getSectionAdapterPosition(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) =
    adapters.indexOf(adapter)
