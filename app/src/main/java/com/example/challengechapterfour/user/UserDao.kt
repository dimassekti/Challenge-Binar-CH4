package com.example.challengechapterfour.user

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User) : Long

    @Query("SELECT * FROM User")
    fun getAllUser() : List<User>

    @Query("SELECT * FROM User WHERE email = :email")
    fun findUser(email : String) : List<User>

    @Query("SELECT username FROM User " +
            "WHERE User.email = :email AND User.password = :password")
    fun checkLogin(email: String, password : String) : String

    @Delete
    fun deleteUser(user: User) : Int

    @Update
    fun updateUser(user: User) : Int


}