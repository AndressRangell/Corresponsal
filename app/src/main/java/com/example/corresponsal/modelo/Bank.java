package com.example.corresponsal.modelo;

import java.util.ArrayList;

public class Bank {

    String id;
    String name;
    String imgLogo;
    ArrayList<Client> usuariosBanco;

    public Bank() {
    }

    public Bank(String id, String name, String imgLogo, ArrayList<Client> usuariosBanco) {
        this.id = id;
        this.name = name;
        this.imgLogo = imgLogo;
        this.usuariosBanco = usuariosBanco;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getImgLogo() { return imgLogo; }

    public void setImgLogo(String imgLogo) { this.imgLogo = imgLogo; }

    public ArrayList<Client> getUsuariosBanco() {
        return usuariosBanco;
    }

    public void setUsuariosBanco(ArrayList<Client> usuariosBanco) {
        this.usuariosBanco = usuariosBanco;
    }
}
