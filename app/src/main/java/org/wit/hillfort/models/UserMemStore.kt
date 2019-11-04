package org.wit.hillfort.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.models.*

class UserMemStore : UserStore, AnkoLogger {

    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        users.add(user)
        logAll();
    }

    override fun delete(user: UserModel) {
        users.remove(user)
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
    override fun findByEmail(email: String): UserModel? {
        return users.find { user -> user.email == email }
    }

    fun logAll() {
        users.forEach{ info("${it}") }
    }

    override fun updateUser(user: UserModel) {
        var foundUser: UserModel? = users.find { p -> p.id == user.id }
        if (foundUser != null) {
            foundUser.name = user.name
            foundUser.email = user.email
            foundUser.password = user.password


            logAll()
        }
    }
}