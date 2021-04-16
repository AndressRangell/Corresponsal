package com.example.corresponsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.corresponsal.modelo.User;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameUser, emailUser;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameUser = findViewById(R.id.nameUser);
        emailUser = findViewById(R.id.emailUser);

        SharedPreferences preferences = getSharedPreferences("loginPreferences",
                Context.MODE_PRIVATE);
        nameUser.setText(preferences.getString("nameUser", ""));
        emailUser.setText(preferences.getString("emailUser", ""));

    }

    public void cambiarContraseña(View view){

    }

}