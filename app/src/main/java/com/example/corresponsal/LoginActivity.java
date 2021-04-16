package com.example.corresponsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.corresponsal.database.DataBase;
import com.example.corresponsal.modelo.JsonCorresponsal;
import com.example.corresponsal.modelo.User;
import com.example.corresponsal.volley.VolleyApplication;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    Context context = LoginActivity.this;
    TextInputEditText txtEmail, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        SharedPreferences preferences = getSharedPreferences("loginPreferences",
                Context.MODE_PRIVATE);
        preferences.edit().clear().commit();

        sincronizar();

    }

    public void login(View view) {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        DataBase db = new DataBase(this);
        User user = db.validateLogin(email, password);
        if (user != null) {
            SharedPreferences preferences = getSharedPreferences("loginPreferences",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("idUser", user.getId());
            editor.putString("nameUser", user.getName());
            editor.putString("emailUser", user.getEmail());
            editor.commit();
            Toast.makeText(this, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error en inicio de sesion", Toast.LENGTH_SHORT).show();
        }
    }

    public void sincronizar() {
        String url = "https://pruebaswposs.000webhostapp.com/BancosEjercicio.json";

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null, response ->  {
            //Exito y obtuvimos el json
            Log.d("JSON", response.toString());
            Gson gson = new Gson();
            JsonCorresponsal jsonCorresponsal = gson.fromJson(response.toString(), JsonCorresponsal.class);
            // Guardar esto en nuestra base de datos
            DataBase database = new DataBase(LoginActivity.this);
            database.addUsers(jsonCorresponsal.getUsers());
            database.addBanks(jsonCorresponsal.getBanks());
            Toast.makeText(this, "Datos sincronizados con exito", Toast.LENGTH_SHORT).show();
        }, error -> {
            //Se presento un error
            Log.d("JSON", "Fail");
            Toast.makeText(this, "Falló la sincronización", Toast.LENGTH_SHORT).show();
        }
        );
        VolleyApplication.getInstance(LoginActivity.this).addToRequestQueue(jsonObject);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

}