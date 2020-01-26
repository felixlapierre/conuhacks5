package com.example.tunein;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TuneInApi {
    @GET("search")
    Call<List<Song>> getSongs(@Query("query") String title);

    @GET("play")
    Call<List<String>> getPLayURL(@Query("id") long id);

    @GET("nearby")
    Call<List<Song>> getNearby();
}
