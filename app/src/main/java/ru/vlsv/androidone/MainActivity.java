package ru.vlsv.androidone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ru.vlsv.androidone.entities.City;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private Button buttonOne;
    private TextView textViewOne;
    private EditText editTextOne;
    private ImageView imageViewTwo;
    private Switch simpleSwitch;
    private Button buttonSelectCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        String instanceState;
        if (savedInstanceState == null) {
            instanceState = "Первый запуск!";
        } else {
            instanceState = "Повторный запуск!";
        }
        City city = (City) getIntent().getSerializableExtra("cityData");
        if (city != null) {
            editTextOne.setText(city.getCity());
            String options = "";
            if (city.getIsWindSpeed()) options += getString(R.string.is_wind_speed) + ", ";
            if (city.getIsPressure()) options += getString(R.string.is_pressure);
            textViewOne.setText(options);
        }
        Toast.makeText(getApplicationContext(), instanceState + " - onCreate()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", instanceState + " - onCreate()");
        setOnButtonClickBehavior();
        setOnImageClickBehavior();
        setOtButtonCitySelectClickBehavior();
    }

    private void initViews() {
        buttonOne = findViewById(R.id.buttonOne);
        textViewOne = findViewById(R.id.textViewOne);
        editTextOne = findViewById(R.id.editText);
        imageViewTwo = findViewById(R.id.imageViewOne);
        simpleSwitch = findViewById(R.id.switchOne);
        if (simpleSwitch != null) {
            simpleSwitch.setOnCheckedChangeListener(this);
        }
        buttonSelectCity = findViewById(R.id.buttonCitySelect);
    }

    private void setOnButtonClickBehavior() {
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);
                textViewOne.setText(R.string.hello_user);
                textViewOne.setTextColor(color);
                editTextOne.setText(R.string.hello_android);
                editTextOne.setTextColor(color);
            }
        });
    }

    private void setOnImageClickBehavior() {
        imageViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewOne.setText(R.string.image_click);
                Toast.makeText(getApplicationContext(), getString(R.string.image_click),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, getString(R.string.check_switch) + (isChecked ? " on" : " off"),
                Toast.LENGTH_SHORT).show();
        if (isChecked) editTextOne.setText(R.string.is_on);
        if (!isChecked) editTextOne.setText(R.string.is_off);
    }

    private void setOtButtonCitySelectClickBehavior() {
        buttonSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectCityActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onPause()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        Toast.makeText(getApplicationContext(), "onsavedInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onsavedInstanceState()");
        saveFields(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void saveFields(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putString("EditText", editTextOne.getText().toString());
        savedInstanceState.putInt("EditTextColor", editTextOne.getCurrentTextColor());
        savedInstanceState.putString("TextView", textViewOne.getText().toString());
        savedInstanceState.putInt("TextViewColor", textViewOne.getCurrentTextColor());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(getApplicationContext(),
                "Повторный запуск!! - onRestoreInstanceState()",
                Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "Повторный запуск!! - onRestoreInstanceState()");
        restoreFields(savedInstanceState);
    }

    private void restoreFields(@NonNull Bundle savedInstanceState) {
        editTextOne.setText(savedInstanceState.getString("EditText"));
        editTextOne.setTextColor(savedInstanceState.getInt("EditTextColor"));
        textViewOne.setText(savedInstanceState.getString("TextView"));
        textViewOne.setTextColor(savedInstanceState.getInt("TextViewColor"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onDestroy()");
    }

}
