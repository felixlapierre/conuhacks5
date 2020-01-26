package com.example.tunein;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TuneInApi {
    @GET("search")
    Call<List<Song>> getSongs();
}
