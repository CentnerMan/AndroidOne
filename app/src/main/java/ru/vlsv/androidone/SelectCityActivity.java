package ru.vlsv.androidone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.time.Instant;

public class SelectCityActivity extends AppCompatActivity {

    private TextView selectCityTextView;
    private CheckBox selectWindSpeed;
    private CheckBox selectPressure;
    private Button buttonOK;
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
                Intent intent = new Intent(SelectCityActivity.this, MainActivity.class);
                city.setCity(selectCityTextView.getEditableText().toString());
                city.setWindSpeed(selectWindSpeed.isChecked());
                city.setPressure(selectPressure.isChecked());
                intent.putExtra("cityData", city);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        city.setCity(selectCityTextView.getEditableText().toString());
        city.setWindSpeed(selectWindSpeed.isChecked());
        city.setPressure(selectPressure.isChecked());
        savedInstanceState.putSerializable("dataCity", city);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        City city = (City) savedInstanceState.getSerializable("dataCity");
        if (city != null) {
            selectCityTextView.setText(city.getCity());
            selectWindSpeed.setChecked(city.getWindSpeed());
            selectPressure.setChecked(city.getPressure());
        }
    }
}