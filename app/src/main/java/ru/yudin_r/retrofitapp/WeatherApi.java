package ru.yudin_r.retrofitapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("{version}/weather")
    Call<Example> weatherList(@Path("version") String version, @Query("q") String qCity,
                              @Query("appid") String apiKey);
}
