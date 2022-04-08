package com.example.challengechapterfour.user

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun inserUser(user: User) : Long

    @Query("SELECT * FROM User")
    fun getAllUser() : List<User>

    @Query("SELECT * FROM User WHERE email = :email")
    fun findUserByUsername(email : String) : List<User>

    @Delete
    fun deleteUser(user: User) : Int

    @Update
    fun updateUser(user: User) : Int

}