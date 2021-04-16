package com.example.corresponsal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.corresponsal.R;

import static androidx.navigation.Navigation.findNavController;

public class MenuFragment extends Fragment {

    private Button btnBalance, btnConsignMoney, btnDrawalMoney;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        btnBalance = root.findViewById(R.id.btnBalance);
        btnConsignMoney = root.findViewById(R.id.btnConsignMoney);
        btnDrawalMoney = root.findViewById(R.id.btnDrawalMoney);

        btnBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.idBalance);
            }
        });

        btnConsignMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.idConsign);
            }
        });

        btnDrawalMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.idDrawal);
            }
        });

        return root;

    }

}