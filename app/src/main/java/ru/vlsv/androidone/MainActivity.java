package ru.vlsv.androidone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button buttonOne;
    private TextView textViewOne;
    private EditText editTextOne;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setOnButtonClickBehavior();
    }

    private void initViews() {
        buttonOne = findViewById(R.id.buttonOne);
        textViewOne = findViewById(R.id.textViewOne);
        editTextOne = findViewById(R.id.editText);
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

}