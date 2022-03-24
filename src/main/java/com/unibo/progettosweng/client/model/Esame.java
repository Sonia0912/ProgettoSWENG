package com.unibo.progettosweng.client.model;

import java.io.Serializable;

public class Esame implements Serializable {

   // private static final long serialVersionUID = 6920443223422767550L;
    private String data;
    private String ora;
    private String difficolta;
    private String aula;
    private String nomeCorso;

    public Esame(String data, String ora, String difficolta, String aula, String nomeCorso) {
        super();
        this.data = data;
        this.ora = ora;
        this.difficolta = difficolta;
        this.aula = aula;
        this.nomeCorso = nomeCorso;
    }

    public Esame(){

    }

    public String getData() {
        return data;
    }

    public String getAula() {
        return aula;
    }

    public String getDifficolta() {
        return difficolta;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }

    public String getOra() {
        return ora;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDifficolta(String difficolta) {
        this.difficolta = difficolta;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }
}
