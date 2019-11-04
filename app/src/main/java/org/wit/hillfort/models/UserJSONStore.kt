package org.wit.hillfort.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfort.helpers.exists
import org.wit.hillfort.helpers.read
import org.wit.hillfort.helpers.write
import java.util.*
import kotlin.collections.ArrayList

val USER_JSON_FILE = "users.json"
val user_gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val userlistType = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type

fun generateRandomUserId(): Long {
    return Random().nextLong()
}

fun generateRandomHillfortId(): Long {
    return Random().nextLong()
}

class UserJSONStore : UserStore, AnkoLogger {


    val context: Context
    var users = ArrayList<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, USER_JSON_FILE)) {
            deserialize()
        }
    }

    // USER CODE
    override fun findAll(): ArrayList<UserModel> {
        return users
    }

    override fun findByEmail(email: String): UserModel? {
        return users.find { user -> user.email == email }
    }

    override fun create(user: UserModel) {
        user.id = generateRandomUserId()
        users.add(user)
        serialize()
    }

    override fun updateUser(user: UserModel) {
        var foundUser: UserModel? = users.find { p -> p.id == user.id }
        if (foundUser != null) {
            foundUser.name = user.name
            foundUser.email = user.email
            foundUser.password = user.password
            serialize()
        }
    }

    override fun login(email: String, password: String): Boolean {
        val user = findByEmail(email)

        if (user != null){
            if (user.password == password){
                return true
            }
        }
        return false
    }

    private fun serialize() {
        val jsonString = user_gsonBuilder.toJson(users, userlistType)
        write(context, USER_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, USER_JSON_FILE)
        users = Gson().fromJson(jsonString, userlistType)
    }

    override fun delete(user: UserModel) {
        users.remove(user)
        serialize()
    }


    // HILLFORT CODE
    override fun findAllHillfort(user: UserModel): ArrayList<HillfortModel> {
        return user.hillforts
    }

    override fun createHillfort(user: UserModel, hillfort: HillfortModel) {
        hillfort.id = generateRandomHillfortId()
        user.hillforts.add(hillfort)
        serialize()
    }

    override fun updateHillfort(user: UserModel, hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = user.hillforts.find { p -> p.id == hillfort.id }
        if (foundHillfort != null) {
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.image = hillfort.image
            foundHillfort.visited = hillfort.visited
            foundHillfort.date = hillfort.date
            foundHillfort.notes = hillfort.notes
            foundHillfort.lat = hillfort.lat
            foundHillfort.lng = hillfort.lng
            foundHillfort.zoom = hillfort.zoom
            serialize()
        }
    }

    override fun deleteHillfort(user: UserModel, hillfort: HillfortModel) {
        user.hillforts.remove(hillfort)
        serialize()
    }


}
