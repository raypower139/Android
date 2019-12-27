package org.wit.hillfort.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



@Parcelize
data class HillfortModel(var id: Long = 0,
                         var title: String = "",
                         var description: String = "",
                         var image: String = "",
                         var visited: Boolean = false,
                         var date: String = "",
                         var notes: String = "",
                         var rating: Float = 0f,
                         var lat : Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f
                          ) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable