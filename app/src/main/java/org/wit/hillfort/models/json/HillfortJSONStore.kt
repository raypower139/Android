package org.wit.hillfort.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfort.helpers.exists
import org.wit.hillfort.helpers.read
import org.wit.hillfort.helpers.write
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.HillfortStore
import java.util.*

val JSON_FILE = "hillfort.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<HillfortModel>>() {}.type
lateinit var app: MainApp


fun generateRandomUserId(): Long {
    return Random().nextLong()
}

fun generateRandomId(): Long {
    return Random().nextLong()
}

class HillfortJSONStore : HillfortStore, AnkoLogger {


    val context: Context
    var hillforts = mutableListOf<HillfortModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    // USER CODE
    override fun findAll(): MutableList<HillfortModel> {
        return hillforts
    }


    override fun create(hillfort: HillfortModel) {
        hillfort.id = generateRandomId()
        hillforts.add(hillfort)
        serialize()
    }

    override fun delete(hillfort: HillfortModel) {
        hillforts.remove(hillfort)
        serialize()
    }

    override fun findById(id:Long) : HillfortModel? {
        val foundHillfort: HillfortModel? = hillforts.find { it.id == id }
        return foundHillfort
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(hillforts,
            listType
        )
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        hillforts = Gson().fromJson(jsonString,
            listType
        )
    }

    override fun update(hillfort: HillfortModel) {
        val hillfortsList = findAll() as java.util.ArrayList<HillfortModel>
        var foundHillfort: HillfortModel? = hillfortsList.find { p -> p.id == hillfort.id }
        if (foundHillfort != null) {
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.image = hillfort.image
            foundHillfort.visited = hillfort.visited
            foundHillfort.date = hillfort.date
            foundHillfort.notes = hillfort.notes
            foundHillfort.rating = hillfort.rating
            foundHillfort.favourite = hillfort.favourite
            foundHillfort.location = hillfort.location
        }
        serialize()
    }




}
