package com.unibo.progettosweng.client.model;

import java.io.Serializable;

public class Corso implements Serializable {

    private String nome;
    private String dataInizio;
    private String dataFine;
    private String descrizione;
    private String dipartimento;

    public Corso(String nome,String inizio, String fine, String descrizione, String dipartimento){

        this.nome = nome;
        this.dataInizio = inizio;
        this.dataFine = fine;
        this.descrizione = descrizione;
        this.dipartimento = dipartimento;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getNomeCorso() {
        return nome;
    }

    public String getDipartimento() {
        return dipartimento;
    }

    public void setDipartimento(String dipartimento){
        this.dipartimento = dipartimento;
    }
    public void setDataFine(String dataFine) {
        this.dataFine = dataFine;
    }

    public void setDataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}