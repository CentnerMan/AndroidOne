package ru.vlsv.androidone.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.vlsv.androidone.R;
import ru.vlsv.androidone.entities.City;
import ru.vlsv.androidone.entities.CityContainer;
import ru.vlsv.androidone.entities.HistoryData;
import ru.vlsv.androidone.model.WeatherRequest;

import static ru.vlsv.androidone.tools.Tools.getLines;
import static ru.vlsv.androidone.tools.Tools.historyList;

public class WeatherFragment extends Fragment {

    private TextView cityView;
    private TextView temperature;
    private TextView humidity;
    private TextView windSpeed;
    private TextView pressure;

    private String[] cities;
    private String[] citiesEng;

    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL_PREFFIX = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String WEATHER_URL_SUFFIX = "&units=metric&appid=";

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
        showWeather(getCity());
    }

    @SuppressLint("SetTextI18n")
    private void initFields(City inputCity) {
        if (inputCity != null) {
            cityView.setText(inputCity.getCity());
            temperature.setText(getString(R.string.temperature));
            humidity.setText(getString(R.string.humidity));
            windSpeed.setText(getString(R.string.select_wind_speed));
            pressure.setText(getString(R.string.select_pressure));
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
        humidity = view.findViewById(R.id.humidityData);
        windSpeed = view.findViewById(R.id.windSpeedData);
        pressure = view.findViewById(R.id.pressureData);
    }

    private void initArrays() {
        cities = getResources().getStringArray(R.array.cities);
        citiesEng = getResources().getStringArray(R.array.cities_eng);
    }

    public void showWeather(City inputCity) {
        try {
            int currentPosition = getIndex();
            String WEATHER_URL = WEATHER_URL_PREFFIX + citiesEng[currentPosition] + WEATHER_URL_SUFFIX;
            final URL uri = new URL(WEATHER_URL + "88a393e90d1a828aeabbca6a84a02cae");
            final Handler handler = new Handler(); // Запоминаем основной поток
            new Thread(new Runnable() {
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
                        urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
                        String result = getLines(in);
                        // преобразование данных запроса в модель
                        Gson gson = new Gson();
                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                        // Возвращаемся к основному потоку
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayWeather(weatherRequest);
                            }
                        });
                    } catch (Exception e) {
                        initFields(getCity()); //Если не сработает интернет
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void displayWeather(WeatherRequest weatherRequest) {
        int currentPosition = getIndex();
        cityView.setText(cities[currentPosition]);

        String temperatureValue = String.format(Locale.getDefault(), "%.1f", weatherRequest.getMain().getTemp());
        temperature.setText(getString(R.string.temperature) + ": " + temperatureValue + " " + getString(R.string.type_temp));

        String humidityStr = String.format(Locale.getDefault(), "%d", weatherRequest.getMain().getHumidity());
        humidity.setText(getString(R.string.humidity) + ": " + humidityStr + " %");

        double convertPressure = weatherRequest.getMain().getPressure() * 0.7501; // Конвертируем в мм.рт.ст
        String pressureText = String.format(Locale.getDefault(), "%.0f", convertPressure);
        pressure.setText(getString(R.string.select_pressure) + ": " + pressureText + " " + getString(R.string.type_pressure));

        String windSpeedStr = String.format(Locale.getDefault(), "%.0f", weatherRequest.getWind().getSpeed());
        windSpeed.setText(getString(R.string.select_wind_speed) + ": " + windSpeedStr + " " + getString(R.string.type_wind_speed));

        historyList.add(new HistoryData(cities[currentPosition], temperatureValue, humidityStr, pressureText, windSpeedStr));
    }
}
