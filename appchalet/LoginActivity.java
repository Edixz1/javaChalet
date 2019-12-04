package com.example.appchalet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void Clause(View v)
    {

        Intent intent = new Intent(this, MainActivity.class);
        MainActivity.NotLoggedIn = false;
        startActivity(intent);
        finishActivity(0);

    }
}
