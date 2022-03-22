package com.unibo.progettosweng.client.model;

import java.io.Serializable;

public class Insegnamento implements Serializable {

    private String nomeDocente;
    private String nomeCorso;
    private String tipologia;

    public Insegnamento(String nomeDocente, String nomeCorso, String tipologia){
        this.nomeDocente = nomeDocente;
        this.nomeCorso = nomeCorso;
        this.tipologia = tipologia;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }
    public String getNomeDocente(){
        return nomeDocente;
    }
    public String getTipologia(){
        return tipologia;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;

    }

    public void setNomeDocente(String nomeDocente) {
        this.nomeDocente = nomeDocente;

    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

}
