package org.wit.hillfort.models


interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user: UserModel)
    fun updateUser(user: UserModel)
    fun delete(user: UserModel)
    

    fun findAllHillfort(List<HillfortModel>
    fun createHillfort(hillfort: HillfortModel)
    fun updateHillfort(hillfort: HillfortModel)
    fun deleteHillfort(hillfort: HillfortModel)



}