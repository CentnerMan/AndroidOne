package ru.vlsv.androidone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import ru.vlsv.androidone.entities.City;

public class SelectCityActivity extends AppCompatActivity {

    private TextView selectCityTextView;
    private CheckBox selectWindSpeed;
    private CheckBox selectPressure;
    private Button buttonOK;
    final static String cityData = "cityData";
    private City city = new City();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        initViews();
        setOnButtonOKClickBehavior();
    }

    private void initViews() {
        selectCityTextView = findViewById(R.id.selectCity);
        selectWindSpeed = findViewById(R.id.selectWindSpeed);
        selectPressure = findViewById(R.id.selectPressure);
        buttonOK = findViewById(R.id.buttonOK);
    }

    private void setOnButtonOKClickBehavior() {
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectCityActivity.this, WeatherActivity.class);
                saveData();
                intent.putExtra(cityData, city);
                startActivity(intent);
            }
        });
    }

    private void saveData() {
        city.setCity(selectCityTextView.getEditableText().toString());
        city.setIsWindSpeed(selectWindSpeed.isChecked());
        city.setIsPressure(selectPressure.isChecked());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        saveData();
        savedInstanceState.putSerializable(cityData, city);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        City city = (City) savedInstanceState.getSerializable(cityData);
        if (city != null) {
            selectCityTextView.setText(city.getCity());
            selectWindSpeed.setChecked(city.getIsWindSpeed());
            selectPressure.setChecked(city.getIsPressure());
        }
    }

    @Override
    public void onBackPressed() {
        saveData();
        Intent intent = new Intent();
        intent.putExtra(cityData, city);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

}