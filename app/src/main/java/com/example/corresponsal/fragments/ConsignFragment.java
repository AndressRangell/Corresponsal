package com.example.corresponsal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

public class ConsignFragment extends Fragment {

    private Button btnConsign;
    private EditText txtCountConsign, txtCardConsign, txtAmountConsign;
    DataBase db;
    Bundle bundle;
    SharedPreferences preferences;

    public ConsignFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_consign, container, false);

        btnConsign = root.findViewById(R.id.btnConsign);

        txtCountConsign = root.findViewById(R.id.txtCountConsign);
        txtCardConsign = root.findViewById(R.id.txtCardConsign);
        txtAmountConsign = root.findViewById(R.id.txtAmountConsign);

        preferences = this.getActivity().getSharedPreferences("bankSelected", Context.MODE_PRIVATE);
        int idBank = Integer.parseInt(preferences.getString("idBank", ""));

        preferences = this.getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        int idUser = Integer.parseInt(preferences.getString("idUser", ""));

        btnConsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberCount = txtCountConsign.getText().toString();
                String numberCard = txtCardConsign.getText().toString();
                String amount = txtAmountConsign.getText().toString();

                db = new DataBase(getContext());
                boolean consign = db.validateConsign(numberCount, numberCard, amount, idBank, idUser);

                findNavController(view).navigate(R.id.idAnswer);

                if(consign){

                    String answer = "Consignacion Exitosa!\n\nNúmero de cuenta:\n" + numberCount + "\nCédula de quien consigna:\n" + numberCard +
                            "\nMonto consignado:\n" + amount;

                    bundle = new Bundle();
                    bundle.putString("answer", answer);

                    getParentFragmentManager().setFragmentResult("key", bundle);
                }else{
                    bundle = new Bundle();
                    bundle.putString("answer", "Consignación incorrecta\nPor favor valide los datos");
                    getParentFragmentManager().setFragmentResult("key", bundle);
                }
            }
        });

        return root;

    }



}