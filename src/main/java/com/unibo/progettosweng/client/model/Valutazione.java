package com.unibo.progettosweng.client.model;

import java.io.Serializable;

public class Valutazione implements Serializable {

    private String nomeCorso;
    private String studente;
    private int voto;
    private int stato;

    public Valutazione(){

    }

    public Valutazione(String nomeCorso, String studente, int voto, int stato){
        this.nomeCorso = nomeCorso;
        this.studente = studente;
        this.voto = voto;
        this.stato = stato;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }

    public int getVoto() {
        return voto;
    }

    public int getStato() {
        return stato;
    }

    public String getStudente() {
        return studente;
    }

    public void setStudente(String studente) {
        this.studente = studente;
    }


    public void setStato(int stato) {
        this.stato = stato;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
    }

    @Override
    public String toString(){
        return this.nomeCorso+" "+this.studente+" "+this.voto+" "+this.stato;
    }

}
