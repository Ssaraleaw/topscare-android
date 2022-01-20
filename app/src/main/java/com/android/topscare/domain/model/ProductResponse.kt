package com.android.topscare.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
{
"product_id":"00001",
"product_bname":"\u0e41\u0e2d\u0e25\u0e01\u0e2d\u0e2e\u0e2d\u0e25\u0e4c\u0e2a\u0e2b\u0e01\u0e32\u0e23 450 \u0e21\u0e25.",
"product_barcode":"8853428000143",
"product_commonname":"",
"product_sale_price":55,
"product_stay":""
}
*/
@Keep
@Parcelize
data class ProductResponse(
    @SerializedName("product_id") val id: String,
    @SerializedName("product_bname") val name: String?,
    @SerializedName("product_barcode") val barcode: String?,
    @SerializedName("product_commonname") val commonName: String?,
    @SerializedName("product_sale_price") val price: Double,
    @SerializedName("product_stay") val stay: String?,
) : Parcelable{

}
