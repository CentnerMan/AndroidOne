package ru.vlsv.androidone.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import ru.vlsv.androidone.R;
import ru.vlsv.androidone.WeatherFragmentActivity;
import ru.vlsv.androidone.entities.City;
import ru.vlsv.androidone.entities.CityContainer;

public class CitiesFragment extends Fragment {

    private ListView listView;
    private TextView emptyTextView;

    private boolean isExistWeather;  // Можно ли расположить рядом фрагмент с погодой
    private int currentPosition = 0;    // Текущая позиция (выбранный город)

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cities_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initList();
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
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showCoatOfArms();
        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("CurrentCity", currentPosition);
        super.onSaveInstanceState(outState);
    }

    private void initViews(View view) {
        listView = view.findViewById(R.id.cities_list_view);
        emptyTextView = view.findViewById(R.id.cities_list_empty_view);
    }

    private void initList() {
        // Для того, чтобы показать список, надо задействовать адаптер.
        // Такая конструкция работает для списков, например ListActivity.
        // Здесь создаем из ресурсов список городов (из массива)
        ArrayAdapter adapter =
                ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.cities,
                        android.R.layout.simple_list_item_activated_1);
        listView.setAdapter(adapter);

        listView.setEmptyView(emptyTextView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                showCoatOfArms();
            }
        });
    }

    // Показать погоду. Ecли возможно, то показать рядом со списком,
    // если нет, то открыть вторую activity
    private void showCoatOfArms() {
        if (isExistWeather) {
            // Выделим текущий элемент списка
            listView.setItemChecked(currentPosition, true);

            // Проверим, что фрагмент с погодой существует в activity
            WeatherFragment detail = (WeatherFragment)
                    Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.weather);

            // Если есть необходимость, то выведем погоду
            if (detail == null || detail.getIndex() != currentPosition) {
                // Создаем новый фрагмент с текущей позицией для вывода герба

                detail = WeatherFragment.create(getCityContainer());

                // Выполняем транзакцию по замене фрагмента
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.weather, detail);  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                //ft.addToBackStack(null);
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
