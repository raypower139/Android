package org.wit.hillfort.models


interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user: UserModel)
    fun updateUser(user: UserModel)
    fun delete(user: UserModel)
    fun login(email: String, password: String): Boolean
    fun findByEmail(name: String): UserModel?

    fun findAllHillfort(user: UserModel): List<HillfortModel>
    fun createHillfort(user: UserModel, hillfort: HillfortModel)
    fun updateHillfort(user: UserModel, hillfort: HillfortModel)
    fun deleteHillfort(user: UserModel, hillfort: HillfortModel)



}