package ru.vlsv.androidone.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.vlsv.androidone.R;
import ru.vlsv.androidone.entities.City;
import ru.vlsv.androidone.entities.CityContainer;

import static ru.vlsv.androidone.tools.Tools.randomInt;

public class WeatherFragment extends Fragment {

    private TextView cityView;
    private TextView temperature;
    private TextView windSpeed;
    private TextView pressure;

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
        initFields(getCity());
    }

    @SuppressLint("SetTextI18n")
    private void initFields(City inputCity) {
        if (inputCity != null) {
            cityView.setText(inputCity.getCity());
            temperature.setText(getString(R.string.temperature) + ": " + randomInt(-40, 40)
                    + " " + getString(R.string.type_temp));
            windSpeed.setText(getString(R.string.select_wind_speed) + ": " + randomInt(0, 30)
                    + " " + getString(R.string.type_wind_speed));
            pressure.setText(getString(R.string.select_pressure) + ": " + randomInt(750, 780)
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
        windSpeed = view.findViewById(R.id.windSpeedData);
        pressure = view.findViewById(R.id.pressureData);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putString("CityField", cityView.getText().toString());
        savedInstanceState.putString("TemperatureField", temperature.getText().toString());
        savedInstanceState.putString("WindField", windSpeed.getText().toString());
        savedInstanceState.putString("PressureField", pressure.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        cityView.setText(savedInstanceState.getString("CityField"));
        temperature.setText(savedInstanceState.getString("TemperatureField"));
        windSpeed.setText(savedInstanceState.getString("WindField"));
        pressure.setText(savedInstanceState.getString("PressureField"));

    }

}
