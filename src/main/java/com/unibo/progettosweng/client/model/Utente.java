package com.unibo.progettosweng.client.model;


import java.io.Serializable;


public class Utente implements Serializable {


    private static final long serialVersionUID = 6920472853042767550L;
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private String tipo;

    public Utente(){

    }

    public Utente(String nome, String cognome, String username, String password, String tipo){
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.tipo = tipo;
    }

    public String getNome(){
        return this.nome;
    }
    public String getCognome(){
        return this.cognome;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){return password;}
    public String getTipo(){return tipo;}

    public void setNome(String nome){
        this.nome = nome;

    }

    public void setCognome(String cognome) {
        this.cognome = cognome;

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username){
        this.username = username;
    }
    @Override
    public String toString(){
        return this.nome+" "+this.cognome+" "+this.username+" "+this.tipo;
    }


}
