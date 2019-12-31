package org.wit.hillfort.models

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize
import androidx.room.Entity
import androidx.room.PrimaryKey


@Parcelize
@Entity
data class HillfortModel(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                         var title: String = "",
                         var description: String = "",
                         var image: String = "",
                         var visited: Boolean = false,
                         var date: String = "",
                         var notes: String = "",
                         var rating: Float = 0f,
                         var favourite: Boolean = false,
                         @Embedded var location : Location = Location()): Parcelable


@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

