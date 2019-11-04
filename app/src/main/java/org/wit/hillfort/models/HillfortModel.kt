package org.wit.hillfort.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList


@Parcelize
data class HillfortModel(var id: Long = 0,
                         var title: String = "",
                         var description: String = "",
                         var image: ArrayList<String> = ArrayList(),
                         var visited: Boolean = false,
                         var date: String = "",
                         var notes: ArrayList<String> = ArrayList(),
                         var lat : Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f
                          ) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable