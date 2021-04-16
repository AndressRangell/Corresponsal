package com.example.corresponsal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.corresponsal.database.DataBase;
import com.example.corresponsal.fragments.BalanceFragment;
import com.example.corresponsal.fragments.ConsignFragment;
import com.example.corresponsal.fragments.DrawalFragment;
import com.example.corresponsal.fragments.MenuFragment;

public class BankActivity extends AppCompatActivity {

    Context context = BankActivity.this;
    String idBank, imageBank, idUser;
    ImageView imgBank;
    TextView quotaUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        imgBank = findViewById(R.id.imageBank);
        quotaUser = findViewById(R.id.quotaUser);

        SharedPreferences preferences = getSharedPreferences("bankSelected",
                Context.MODE_PRIVATE);
        idBank = preferences.getString("idBank", "");
        imageBank = preferences.getString("imgBank", "");

        preferences = getSharedPreferences("loginPreferences",
                Context.MODE_PRIVATE);
        idUser = preferences.getString("idUser", "");

        Glide.with(context).load(imageBank).error(R.drawable.ic_launcher_background).into(imgBank);

        getQuota();

    }

    public void getQuota(){
        DataBase db = new DataBase(this);
        String quota = db.getQuota(idUser);

        quotaUser.setText("Cupo: $ " + quota);
    }

    @Override
    public void onBackPressed() {

    }
}