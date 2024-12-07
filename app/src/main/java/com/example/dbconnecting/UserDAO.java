package com.example.dbconnecting;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO
{
    @Insert
    public void addUser(User user);

//    @Update
//    public void updateUser(User user);

//    @Query("delete from users where u_id ==:user_id")
//    public void deleteUser(String user_id);

    @Delete
    public void deleteUser(User user);

    @Query("select * from users")
    public List<User> getAllUsers();

//    @Query("select * from users where u_id==:user_id")
//    public List<User> getUser(String user_id);


}
