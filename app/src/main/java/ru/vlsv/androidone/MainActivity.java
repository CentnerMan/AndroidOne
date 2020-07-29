package ru.vlsv.androidone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
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
        buttonSelectCity = findViewById(R.id.buttonSitySelect);
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
    }

    private void setOtButtonCitySelectClickBehavior(){
        buttonSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectCityActivity.class);
                startActivity(intent);
            }
        });
    }
}
