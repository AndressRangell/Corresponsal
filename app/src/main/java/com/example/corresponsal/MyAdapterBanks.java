package com.example.corresponsal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.corresponsal.modelo.Bank;

import java.util.ArrayList;

public class MyAdapterBanks extends RecyclerView.Adapter<MyAdapterBanks.MyViewHolder> implements View.OnClickListener{

    Context context;
    ArrayList<Bank> listBanks;
    private View.OnClickListener listener;

    public MyAdapterBanks(Context context, ArrayList<Bank> listBanks) {
        this.context = context;
        this.listBanks = listBanks;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public void setListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyAdapterBanks.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_bank, null, false);

        view.setOnClickListener(this);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterBanks.MyViewHolder holder, int position) {

        Glide.with(context).load(listBanks.get(position).getImgLogo()).error(R.drawable.ic_launcher_background).into(holder.imgBank);

    }

    @Override
    public int getItemCount() {
        return listBanks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgBank;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBank = itemView.findViewById(R.id.imgBank);

        }
    }
}
