package com.example.appchalet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    static boolean NotLoggedIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(NotLoggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            NotLoggedIn = false;
        }
        else
        {
            setContentView(R.layout.activity_main);
        }

    }

    public void TostTemps(View v)
    {
        Toast rotie = Toast.makeText(MainActivity.this,  // contexte
                " Toast TEMP PeanutButter", // message
                Toast.LENGTH_SHORT);          // durée
        rotie.show();
    }
}


