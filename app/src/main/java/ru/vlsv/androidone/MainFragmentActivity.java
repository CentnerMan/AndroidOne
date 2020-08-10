package ru.vlsv.androidone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int countOfFragmentInManager = getSupportFragmentManager().getBackStackEntryCount();
        if(countOfFragmentInManager > 0) {
            getSupportFragmentManager().popBackStack();
            /*getSupportFragmentManager().popBackStack("Some_Key",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
        }
    }
}