package com.example.schoolbustracking.activities.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    private Context context;
    private SharedPreferences sharedPreferences;
    private String name;

    public void remove() {
        sharedPreferences.edit().clear().commit();
    }


    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("userdata", name).apply();
    }

    public String getName() {
        name = sharedPreferences.getString("userdata", "");
        return name;
    }

    public User(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
    }

}
