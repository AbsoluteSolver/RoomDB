package com.example.dbconnecting;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDB extends RoomDatabase{

    public abstract UserDAO getUserDAO();
}
