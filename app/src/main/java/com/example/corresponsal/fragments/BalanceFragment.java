package com.example.corresponsal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.corresponsal.BankActivity;
import com.example.corresponsal.R;
import com.example.corresponsal.database.DataBase;
import com.example.corresponsal.modelo.Bank;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

public class BalanceFragment extends Fragment {

    private Button btnConsult;
    private EditText txtCountBalance, txtCardBalance, txtPinBalance;
    DataBase db;
    Bundle bundle;

    public BalanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_balance, container, false);

        btnConsult = root.findViewById(R.id.btnConsult);

        txtCountBalance = root.findViewById(R.id.txtCountBalance);
        txtCardBalance = root.findViewById(R.id.txtCardBalance);
        txtPinBalance = root.findViewById(R.id.txtPinBalance);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("bankSelected", Context.MODE_PRIVATE);
        int idBank = Integer.parseInt(preferences.getString("idBank", ""));

        btnConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberCount = txtCountBalance.getText().toString();
                String numberCard = txtCardBalance.getText().toString();
                String pin = txtPinBalance.getText().toString();

                db = new DataBase(getContext());
                ArrayList<String> consult = db.validateConsult(numberCount, numberCard, pin, idBank);

                findNavController(view).navigate(R.id.idAnswer);

                if(consult != null){

                    String answer = "Consulta Exitosa!\n\nNúmero de cuenta:\n" + numberCount + "\nNumero de cedula:\n" + numberCard +
                            "\nNombre:\n" + consult.get(0) + "\nSaldo:\n" + consult.get(1);

                    bundle = new Bundle();
                    bundle.putString("answer", answer);

                    getParentFragmentManager().setFragmentResult("key", bundle);
                }else{
                    bundle = new Bundle();
                    bundle.putString("answer", "Consulta de saldo incorrecta\nPor favor valide los datos");
                    getParentFragmentManager().setFragmentResult("key", bundle);
                }
            }
        });

        return root;

    }
}