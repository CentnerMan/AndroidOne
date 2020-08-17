package ru.vlsv.androidone.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.vlsv.androidone.R;
import ru.vlsv.androidone.entities.City;
import ru.vlsv.androidone.entities.CityContainer;

public class WeatherFragment extends Fragment {

    private TextView cityView;
    private TextView temperature;
    private TextView temperatureDays;
    private TextView windSpeed;
    private TextView pressure;
    private MaterialButton button;

    private String[] cities;
    private int[] temperatureArr;
    private int[] temperatureOneArr;
    private int[] temperatureTwoArr;
    private int[] temperatureThreeArr;
    private int[] windSpeedArr;
    private int[] pressureArr;

    static WeatherFragment create(CityContainer container) {
        WeatherFragment fragment = new WeatherFragment();    // создание
        // Передача параметра
        Bundle args = new Bundle();
        args.putSerializable("index", container);
        fragment.setArguments(args);
        return fragment;
    }

    // Получить индекс из списка (фактически из параметра)
    int getIndex() {
        CityContainer cityContainer = (CityContainer) (Objects.requireNonNull(getArguments())
                .getSerializable("index"));
        try {
            return cityContainer.getPosition();
        } catch (Exception e) {
            return 0;
        }
    }

    City getCity() {
        CityContainer cityContainer = (CityContainer) (Objects.requireNonNull(getArguments())
                .getSerializable("index"));
        try {
            return cityContainer.getCity();
        } catch (Exception e) {
            return new City();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initArrays();
        initFields(getCity());
        setOnBtnClickListener();
    }

    @SuppressLint("ResourceAsColor")
    private void setOnBtnClickListener() {
        button.setOnClickListener(view -> {
            button.setPressed(true);
            button.setText(R.string.touch_btn_press);
            button.setBackgroundColor(R.color.colorAccent);
            cityView.setText(R.string.default_city);
        });
    }

    @SuppressLint("SetTextI18n")
    private void initFields(City inputCity) {
        if (inputCity != null) {
            int currentPosition = getIndex();
            cityView.setText(inputCity.getCity());
            temperature.setText(getString(R.string.temperature) + ": " + temperatureArr[currentPosition]
                    + " " + getString(R.string.type_temp));
            temperatureDays.setText(temperatureOneArr[currentPosition] + " " + getString(R.string.type_temp)
                    + " | " + temperatureTwoArr[currentPosition] + " " + getString(R.string.type_temp)
                    + " | " + temperatureThreeArr[currentPosition] + " " + getString(R.string.type_temp));
            windSpeed.setText(getString(R.string.select_wind_speed) + ": " + windSpeedArr[currentPosition]
                    + " " + getString(R.string.type_wind_speed));
            pressure.setText(getString(R.string.select_pressure) + ": " + pressureArr[currentPosition]
                    + " " + getString(R.string.type_pressure));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_fragment, container, false);
    }

    private void initViews(View view) {
        cityView = view.findViewById(R.id.cityName);
        temperature = view.findViewById(R.id.temperatureData);
        temperatureDays = view.findViewById(R.id.temperatureDays);
        windSpeed = view.findViewById(R.id.windSpeedData);
        pressure = view.findViewById(R.id.pressureData);
        button = view.findViewById(R.id.touch_btn);
    }

    private void initArrays() {
        cities = getResources().getStringArray(R.array.cities);
        temperatureArr = getResources().getIntArray(R.array.weather_today);
        temperatureOneArr = getResources().getIntArray(R.array.weather_tomorrow);
        temperatureTwoArr = getResources().getIntArray(R.array.weather_two);
        temperatureThreeArr = getResources().getIntArray(R.array.weather_three);
        windSpeedArr = getResources().getIntArray(R.array.wind_speed);
        pressureArr = getResources().getIntArray(R.array.pressure);
    }

}
