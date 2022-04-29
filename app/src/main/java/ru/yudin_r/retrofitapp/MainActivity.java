package ru.yudin_r.retrofitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String version = "2.5";
        String apiKey =  "46633b8bd46722664e218858b12874b8";

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        Call<Example> call = weatherApi.weatherList(version, "Moscow", apiKey);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Example example = response.body();
                Log.d("RETROFIT", example.getMain().getTemp().toString());
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
}