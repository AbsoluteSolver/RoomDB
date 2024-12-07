package com.example.dbconnecting;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "users")
public class User {

    @ColumnInfo(name = "u_id")
    @PrimaryKey
    @NonNull
    String id;

    @ColumnInfo(name = "u_name")
    String name;

    @ColumnInfo(name = "u_age")
    String age;

    @Ignore
    public User(){}

    public User(String id, String name, String age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
