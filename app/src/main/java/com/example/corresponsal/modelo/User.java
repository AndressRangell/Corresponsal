package com.example.corresponsal.modelo;

public class User {

    String id;
    String email;
    String password;
    String name;
    double saldo;

    public User() {
    }

    public User(String id, String email, String password, String name, double saldo) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.saldo = saldo;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getSaldo() { return saldo; }

    public void setSaldo(double saldo) { this.saldo = saldo; }
}
