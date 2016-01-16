package com.phila.samergigabyte.philabooksx;


import android.content.Context;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by SamerGigaByte on 15/01/2016.
 */
public class Net {
//    @POST("pdf/api/userlogin")
//    Call<User> login(@Field("email")String email, @Field("pass") String password);
//    @POST("pdf/api/saveuser")
//    Call<Saved> signUp(@Field("id")String id, @Field("name")String name, @Field("email")String email, @Field("pass") String password);

    private static ApiInterface mApiInterface;

    private static RestAdapter getRestAdapter(Context context) {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://79.134.150.46")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter;
    }


    public static ApiInterface apiClient(Context context) {
        if (mApiInterface == null) {
            mApiInterface = getRestAdapter(context).create(ApiInterface.class);

        }
        return mApiInterface;
    }


    public interface ApiInterface {

        @FormUrlEncoded
        @POST("/pdf/api/users/login")
        void login(@Field("email")String email, @Field("pass") String password, Callback<Object> callback);

        @FormUrlEncoded
        @POST("/pdf/api/users/saveuser")
        void signUp(@Field("id")String id, @Field("name")String name, @Field("email")String email, @Field("pass") String password, Callback<Object> callback);

        @Multipart
        @POST("/pdf/api/pdf/savePdf")
        void savePdf(@Field("UserId") String UserId, @Field("Name") String Name, @Part("upload_path")TypedFile upload_path, Callback<Object> callback);
    }
}
