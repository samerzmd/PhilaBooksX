package com.phila.samergigabyte.philabooksx;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SamerGigaByte on 15/01/2016.
 */
public class User {
    @SerializedName("Users")
    MineUser Users;
    public class MineUser {
        @SerializedName("id")
        String id;
        @SerializedName("username")
        String username;
        @SerializedName("password")
        String password;
        @SerializedName("email")
        String email;
    }
}
