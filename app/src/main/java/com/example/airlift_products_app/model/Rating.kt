package com.example.airlift_products_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating (
    var rate: Double = 0.0,
    var count: Int = 0): Parcelable