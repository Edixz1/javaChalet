package com.example.appchalet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

    }

    public void TostTemps(View v)
    {
        Toast rotie = Toast.makeText(MainActivity.this,  // contexte
                "Tester Toast", // message
                Toast.LENGTH_LONG);          // durée
        rotie.show();
    }
}
