package com.phila.samergigabyte.philabooksx;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by SDawalib on 1/16/2016.
 */
public class MyAppClass extends Application {
    User mCurrentUser;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("theone",MODE_PRIVATE);


        String userString = sharedPref.getString("user",null);
        if(userString!=null) {
            Gson gson = new Gson();
            User user = gson.fromJson(userString, User.class);
            mCurrentUser=user;
        }
    }
}
