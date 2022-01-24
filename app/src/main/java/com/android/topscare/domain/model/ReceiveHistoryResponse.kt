package com.android.topscare.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class ReceiveHistoryResponse(
    @SerializedName("receive_id") val id: String,
    @SerializedName("pro_id") val pid: String,
    @SerializedName("value_receive") val amount: Int,
    @SerializedName("lot_no") val lotNo: String?,
    @SerializedName("exp_date") val expDate: String,
    @SerializedName("HH_name") val hhName: String,
    @SerializedName("product_bname") val name: String,
    @SerializedName("product_barcode") val barcode: String,
    @SerializedName("create_date") val date: String
): Parcelable {
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ReceiveHistoryResponse> = object : DiffUtil.ItemCallback<ReceiveHistoryResponse>() {
            override fun areItemsTheSame(oldItem: ReceiveHistoryResponse, newItem: ReceiveHistoryResponse): Boolean =
                (oldItem.id == newItem.id)

            override fun areContentsTheSame(oldItem: ReceiveHistoryResponse, newItem: ReceiveHistoryResponse): Boolean =
                oldItem.id == newItem.id
        }
    }
}
