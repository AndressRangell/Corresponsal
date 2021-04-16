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

import com.example.corresponsal.R;
import com.example.corresponsal.database.DataBase;

import static androidx.navigation.Navigation.findNavController;

public class DrawalFragment extends Fragment {

    private Button btnDrawal;
    private EditText txtCountDrawal, txtCardDrawal, txtPinDrawal, txtAmountDrawal;
    DataBase db;
    Bundle bundle;
    SharedPreferences preferences;

    public DrawalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_drawal, container, false);

        btnDrawal = root.findViewById(R.id.btnDrawal);

        txtCountDrawal = root.findViewById(R.id.txtCountDrawal);
        txtCardDrawal = root.findViewById(R.id.txtCardDrawal);
        txtPinDrawal = root.findViewById(R.id.txtPinDrawal);
        txtAmountDrawal = root.findViewById(R.id.txtAmountDrawal);

        preferences = this.getActivity().getSharedPreferences("bankSelected", Context.MODE_PRIVATE);
        int idBank = Integer.parseInt(preferences.getString("idBank", ""));

        preferences = this.getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        int idUser = Integer.parseInt(preferences.getString("idUser", ""));

        btnDrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberCount = txtCountDrawal.getText().toString();
                String numberCard = txtCardDrawal.getText().toString();
                String pin = txtPinDrawal.getText().toString();
                String amount = txtAmountDrawal.getText().toString();

                db = new DataBase(getContext());
                boolean drawal = db.validateDrawal(numberCount, numberCard, pin, amount, idBank, idUser);

                findNavController(view).navigate(R.id.idAnswer);

                if(drawal){

                    String answer = "Retiro Exitoso!\n\nNúmero de cuenta:\n" + numberCount + "\nCédula de quien retira:\n" + numberCard +
                            "\nMonto Retirado:\n" + amount;

                    bundle = new Bundle();
                    bundle.putString("answer", answer);

                    getParentFragmentManager().setFragmentResult("key", bundle);
                }else{
                    bundle = new Bundle();
                    bundle.putString("answer", "Retiro incorrecto\nPor favor valide los datos");
                    getParentFragmentManager().setFragmentResult("key", bundle);
                }
            }
        });

        return root;

    }


}