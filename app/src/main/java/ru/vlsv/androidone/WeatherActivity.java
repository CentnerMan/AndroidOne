package ru.vlsv.androidone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {

    private Button selectCityBtn;
    private TextView city;
    private TextView temperature;
    private TextView windSpeed;
    private TextView pressure;
    final static String cityData = "cityData";
    private final int requestCode = 6585;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initViews();
        City inputCity = (City) getIntent().getSerializableExtra(cityData);
        initFields(inputCity);
        selectCityBtnClickBehavior();
    }

    @SuppressLint("SetTextI18n")
    private void initFields(City inputCity) {
        windSpeed.setVisibility(View.INVISIBLE);
        pressure.setVisibility(View.INVISIBLE);
        temperature.setText(getString(R.string.temperature));
        if (inputCity != null) {
            if (!inputCity.getCity().equals("")) {
                city.setText(inputCity.getCity());
            } else {
                city.setText(getString(R.string.default_city));
            }
            temperature.setText(getString(R.string.temperature) + ": " + RandomInt(-40, 40)
                    + " " + getString(R.string.type_temp));
            if (inputCity.getIsWindSpeed()) {
                windSpeed.setText(getString(R.string.select_wind_speed) + ": " + RandomInt(0, 30)
                        + " " + getString(R.string.type_wind_speed));
                windSpeed.setVisibility(View.VISIBLE);
            }
            if (windSpeed.getVisibility() == View.VISIBLE) {
                if (inputCity.getIsPressure()) {
                    pressure.setText(getString(R.string.select_pressure) + ": " + RandomInt(750, 780)
                            + " " + getString(R.string.type_pressure));
                    pressure.setVisibility(View.VISIBLE);
                }
            } else {
                if (inputCity.getIsPressure()) {
                    windSpeed.setText(getString(R.string.select_pressure) + ": " + RandomInt(750, 780)
                            + " " + getString(R.string.type_pressure));
                    windSpeed.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private int RandomInt(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    private void initViews() {
        selectCityBtn = findViewById(R.id.selectCityBtn);
        city = findViewById(R.id.cityName);
        temperature = findViewById(R.id.temperatureData);
        windSpeed = findViewById(R.id.windSpeedData);
        pressure = findViewById(R.id.pressureData);
    }

    private void selectCityBtnClickBehavior() {
        selectCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeatherActivity.this, SelectCityActivity.class);
                startActivityForResult(intent, requestCode);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putString("CityField", city.getText().toString());
        savedInstanceState.putString("TemperatureField", temperature.getText().toString());
        savedInstanceState.putString("WindField", windSpeed.getText().toString());
        savedInstanceState.putString("PressureField", pressure.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        city.setText(savedInstanceState.getString("CityField"));
        temperature.setText(savedInstanceState.getString("TemperatureField"));
        windSpeed.setText(savedInstanceState.getString("WindField"));
        pressure.setText(savedInstanceState.getString("PressureField"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == this.requestCode && resultCode == RESULT_OK && data != null) {
            City resultCity = (City) data.getSerializableExtra(SelectCityActivity.cityData);
            initFields(resultCity);
        }
    }

}