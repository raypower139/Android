package org.wit.hillfort.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlin.collections.ArrayList


@Parcelize
data class UserModel(var id: Long = 0,
                          var name: String = "",
                          var password: String = "")
                           : Parcelable
