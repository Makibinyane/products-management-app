package com.example.airlift_products_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductResponse(
    var id: String = "",
    var title: String = "",
    var price: Double = 0.0,
    var description: String = "",
    var category: String = "",
    var image: String = "",
    var rating: Rating = Rating()): Parcelable