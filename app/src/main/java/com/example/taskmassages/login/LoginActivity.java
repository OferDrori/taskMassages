package com.example.taskmassages.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.taskmassages.R;

public class LoginActivity extends AppCompatActivity {

    private FrameLayout Main_FrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.Main_FrameLayout);

        if (fragment == null) {
            fragment = new FragmentStart();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.add(R.id.Main_FrameLayout, fragment, "0").commit();
        }
    }

    private void findView(){
        Main_FrameLayout = findViewById(R.id.Main_FrameLayout);
    }
}
