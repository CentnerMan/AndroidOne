package ru.vlsv.androidone.fragments;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import ru.vlsv.androidone.R;
import ru.vlsv.androidone.entities.CityContainer;

public class WeatherFragment extends Fragment {

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

    String getCityName() {
        CityContainer cityContainer = (CityContainer) (Objects.requireNonNull(getArguments())
                .getSerializable("index"));

        try {
            return cityContainer.getCity().getCity();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    @SuppressLint("Recycle")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView cityNameTextView = new TextView(getContext());
        String cityName = getCityName();
        cityNameTextView.setText(cityName);

        // Определить какой герб надо показать, и показать его
        ImageView coatOfArms = new ImageView(getActivity());

        // Получить из ресурсов массив указателей на изображения гербов
        TypedArray images = getResources().obtainTypedArray(R.array.weather);
        // Выбрать по индексу подходящий
        coatOfArms.setImageResource(images.getResourceId(getIndex(), -1));

        layout.addView(cityNameTextView);
        layout.addView(coatOfArms);

        return layout;
    }

}
