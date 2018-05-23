package com.teaching.android.miprimeraapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    List<User> getAll();

    @Query("SELECT * FROM User WHERE username IS :username")
    User findByUsername(String username);

    /*@Query("SELECT * FROM User WHERE password IS :password")
    User checkPassword(String password);*/

    @Insert
    void insert(User user);

    @Insert
    void delete(User user);
}
