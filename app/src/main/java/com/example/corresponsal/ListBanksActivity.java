package com.example.corresponsal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.corresponsal.database.DataBase;
import com.example.corresponsal.modelo.Bank;

import java.util.ArrayList;

public class ListBanksActivity extends AppCompatActivity {

    ArrayList<Bank> listBanks;
    RecyclerView recyclerViewBanks;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_banks);

        db = new DataBase(getApplicationContext());
        listBanks = new ArrayList<>();
        recyclerViewBanks = findViewById(R.id.recyclerViewBanks);
        recyclerViewBanks.setLayoutManager(new LinearLayoutManager(this));
        listBanks = db.getListBanks();
        MyAdapterBanks myAdapter = new MyAdapterBanks( this, listBanks);

        myAdapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getSharedPreferences("bankSelected",
                        Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("idBank", listBanks.get(recyclerViewBanks.getChildAdapterPosition(view)).getId());
                editor.putString("imgBank", listBanks.get(recyclerViewBanks.getChildAdapterPosition(view)).getImgLogo());
                editor.commit();

                Intent intent = new Intent(ListBanksActivity.this, BankActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewBanks.setAdapter(myAdapter);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}