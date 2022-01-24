package com.android.topscare.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class CountResponse(
    @SerializedName("checkstock_id") val id: String,
    @SerializedName("pro_id") val pid: String,
    @SerializedName("value_check") val amount: Int,
    @SerializedName("HH_name") val hhName: String,
    @SerializedName("product_bname") val name: String,
    @SerializedName("product_barcode") val barcode: String,
    @SerializedName("create_date") val date: String
) : Parcelable {
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<CountResponse> = object : DiffUtil.ItemCallback<CountResponse>() {
            override fun areItemsTheSame(oldItem: CountResponse, newItem: CountResponse): Boolean =
                (oldItem.id == newItem.id)

            override fun areContentsTheSame(oldItem: CountResponse, newItem: CountResponse): Boolean =
                oldItem.id == newItem.id
        }
    }
}