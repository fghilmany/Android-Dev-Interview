package com.fghilmany.qikiinterview.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("availabilityStatus")
    val availabilityStatus: String? = null,
    @SerialName("brand")
    val brand: String? = null,
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("dimensions")
    val dimensions: Dimensions,
    @SerialName("discountPercentage")
    val discountPercentage: Double? = null,
    @SerialName("id")
    val id: Int,
    @SerialName("images")
    val images: List<String?>,
    @SerialName("meta")
    val meta: Meta,
    @SerialName("minimumOrderQuantity")
    val minimumOrderQuantity: Int? = null,
    @SerialName("price")
    val price: Double,
    @SerialName("rating")
    val rating: Double? = null,
    @SerialName("returnPolicy")
    val returnPolicy: String? = null,
    @SerialName("reviews")
    val reviews: List<Review>,
    @SerialName("shippingInformation")
    val shippingInformation: String? = null,
    @SerialName("sku")
    val sku: String? = null,
    @SerialName("stock")
    val stock: Int? = null,
    @SerialName("tags")
    val tags: List<String?>,
    @SerialName("thumbnail")
    val thumbnail: String? = null,
    @SerialName("title")
    val title: String,
    @SerialName("warrantyInformation")
    val warrantyInformation: String? = null,
    @SerialName("weight")
    val weight: Int
)