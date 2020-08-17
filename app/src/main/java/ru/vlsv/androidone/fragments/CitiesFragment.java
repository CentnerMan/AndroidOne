package ru.vlsv.androidone.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import ru.vlsv.androidone.IRVOnItemClick;
import ru.vlsv.androidone.R;
import ru.vlsv.androidone.RecyclerDataAdapter;
import ru.vlsv.androidone.WeatherFragmentActivity;
import ru.vlsv.androidone.entities.City;
import ru.vlsv.androidone.entities.CityContainer;

public class CitiesFragment extends Fragment implements IRVOnItemClick {

    private RecyclerView recyclerView;
    private boolean isExistWeather;  // Можно ли расположить рядом фрагмент с погодой
    private int currentPosition = 0;    // Текущая позиция (выбранный город)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cities_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupRecyclerView();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.cities)));
        RecyclerDataAdapter adapter = new RecyclerDataAdapter(stringArrayList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(View view, int position) {
        currentPosition = position;
        showWeather();
    }

    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Определение, можно ли будет расположить рядом погоду в другом фрагменте
        isExistWeather = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentPosition = savedInstanceState.getInt("CurrentCity", 0);
        }

        // Если можно нарисовать рядом погоду, то сделаем это
        if (isExistWeather) {
            showWeather();
        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("CurrentCity", currentPosition);
        super.onSaveInstanceState(outState);
    }

    // Показать погоду. Если возможно, то показать рядом со списком,
    // если нет, то открыть вторую activity
    private void showWeather() {
        if (isExistWeather) {
            // Выделим текущий элемент списка
//            recyclerView.setItemChecked(currentPosition, true);

            // Проверим, что фрагмент с погодой существует в activity
            WeatherFragment detail = (WeatherFragment)
                    Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.weather);

            // Если есть необходимость, то выведем погоду
            if (detail == null || detail.getIndex() != currentPosition) {
                // Создаем новый фрагмент с текущей позицией для вывода погоды

                detail = WeatherFragment.create(getCityContainer());

                // Выполняем транзакцию по замене фрагмента
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.weather, detail);  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack("Some_Key");
                ft.commit();
            }
        } else {
            // Если нельзя вывести погоду рядом, откроем вторую activity
            Intent intent = new Intent();
            intent.setClass(Objects.requireNonNull(getActivity()), WeatherFragmentActivity.class);
            // и передадим туда параметры
            intent.putExtra("index", getCityContainer());
            startActivity(intent);
        }
    }

    private CityContainer getCityContainer() {
        String[] cities = getResources().getStringArray(R.array.cities);
        return new CityContainer(currentPosition, new City(cities[currentPosition]));
    }
}
