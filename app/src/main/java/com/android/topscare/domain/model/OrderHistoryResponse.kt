package com.android.topscare.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class OrderHistoryResponse(
    @SerializedName("order_id") val id: String,
    @SerializedName("pro_id") val pid: String,
    @SerializedName("value_order") val amount: Int,
    @SerializedName("value_premium") val freeAmount: Int,
    @SerializedName("HH_name") val hhName: String,
    @SerializedName("product_bname") val name: String,
    @SerializedName("product_barcode") val barcode: String,
    @SerializedName("create_date") val date: String
): Parcelable {
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<OrderHistoryResponse> = object : DiffUtil.ItemCallback<OrderHistoryResponse>() {
            override fun areItemsTheSame(oldItem: OrderHistoryResponse, newItem: OrderHistoryResponse): Boolean =
                (oldItem.id == newItem.id)

            override fun areContentsTheSame(oldItem: OrderHistoryResponse, newItem: OrderHistoryResponse): Boolean =
                oldItem.id == newItem.id
        }
    }
}
