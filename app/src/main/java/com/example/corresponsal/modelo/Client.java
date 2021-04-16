package com.example.corresponsal.modelo;

public class Client {

    String id;
    String name;
    String cedula;
    String cuenta;
    String PIN;
    double saldo;
    String idBanco;

    public Client() {
    }

    public Client(String id, String name, String cedula, String cuenta, String PIN, double saldo,
                  String idBanco) {
        this.id = id;
        this.name = name;
        this.cedula = cedula;
        this.cuenta = cuenta;
        this.PIN = PIN;
        this.saldo = saldo;
        this.idBanco = idBanco;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCedula() { return cedula; }

    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getCuenta() { return cuenta; }

    public void setCuenta(String cuenta) { this.cuenta = cuenta; }

    public String getPIN() { return PIN; }

    public void setPIN(String PIN) { this.PIN = PIN; }

    public double getSaldo() { return saldo; }

    public void setSaldo(double saldo) { this.saldo = saldo; }

    public String getIdBanco() { return idBanco; }

    public void setIdBanco(String idBanco) { this.idBanco = idBanco; }
}
