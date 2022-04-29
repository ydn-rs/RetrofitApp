package ru.yudin_r.retrofitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.yudin_r.retrofitapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String version = "2.5";
    String apiKey =  "46633b8bd46722664e218858b12874b8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = binding.cityField.getText().toString();
                getWeatherData(city);
            }
        });
    }

    private void getWeatherData(String city) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        Call<Example> call = weatherApi.weatherList(version, city, apiKey);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.code()==404) {
                    showMsg("Ошибка 404, проверьте введенный город!");
                }
                Example example = response.body();
                Double tempDouble = example.getMain().getTemp() - 273;
                int tempInt = tempDouble.intValue();
                binding.tempTextView.setText(tempInt + " °");
                binding.cityTextView.setText(example.getName());
                binding.humidityTextView.setText("Влажность: " +
                        example.getMain().getHumidity().toString() + " %");
                binding.windTextView.setText("Ветер: " +
                        example.getWind().getSpeed().toString() + " М/с");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e("Retrofit Error", t.toString());
                showMsg(t.toString());
            }
        });
    }

    private void showMsg(String e) {
        Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), e,
                Snackbar.LENGTH_LONG);
        View snackBarView = snackBar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.error));
        snackBar.show();
    }
}