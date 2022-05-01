package com.example.airlift_products_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var id: String = "",
    var title: String = "",
    var price: Double = 0.0,
    var description: String = "",
    var category: String = "",
    var image: String = ""): Parcelable
