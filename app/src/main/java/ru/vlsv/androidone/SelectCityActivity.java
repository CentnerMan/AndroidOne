package ru.vlsv.androidone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SelectCityActivity extends AppCompatActivity {

    private TextView selectCityTextView;
    private CheckBox selectWindSpeed;
    private CheckBox selectPressure;
    private Button buttonOK;

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
                startActivity(intent);

            }
        });
    }
}