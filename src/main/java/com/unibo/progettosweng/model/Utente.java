package com.unibo.progettosweng.model;

public class Utente {

    private String nome;
    private String cognome;

    private String username;

    public Utente(String nome, String cognome, String username){
        super();
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
    }


    public String getNome(){
        return nome;
    }
    public String getCognome(){
        return cognome;
    }
    public String getUsername(){
        return username;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public void setUsername(String username){
        this.username = username;
    }

}
