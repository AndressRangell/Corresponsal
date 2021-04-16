package com.example.corresponsal.modelo;

import java.util.ArrayList;

public class JsonCorresponsal {

    ArrayList<User> usuariosCorresponsal;
    ArrayList<Bank> bancos;

    public JsonCorresponsal() {
    }

    public JsonCorresponsal(ArrayList<User> usuariosCorresponsal, ArrayList<Bank> bancos) {
        this.usuariosCorresponsal = usuariosCorresponsal;
        this.bancos = bancos;
    }

    public ArrayList<User> getUsers() { return usuariosCorresponsal; }

    public void setUsers(ArrayList<User> users) { this.usuariosCorresponsal = users; }

    public ArrayList<Bank> getBanks() { return bancos; }

    public void setBanks(ArrayList<Bank> banks) { this.bancos = banks; }
}
