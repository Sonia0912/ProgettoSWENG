package com.unibo.progettosweng.client.model;

import com.google.gwt.view.client.ProvidesKey;

import java.io.Serializable;

public class Corso implements Serializable {

    private String nome;
    private String dataInizio;
    private String dataFine;
    private String descrizione;
    private String docente;
    private String codocente;
    private boolean esameCreato;
    private String dipartimento;


    public Corso(String nome, String inizio, String fine, String descrizione, String dipartimento, String docente,  String codocente, Boolean esameCreato) {
        this.nome = nome;
        this.dataInizio = inizio ;
        this.dataFine =  fine;
        this.descrizione = descrizione;
        this.docente =  docente;
        this.codocente = codocente;
        this.dipartimento = dipartimento;
        this.esameCreato = esameCreato;
    }

    public Corso() {

    }

    public String getDipartimento(){
        return dipartimento;
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

    public String getDocente(){return docente;}

    public String getCodocente() {
        return codocente;
    }

    public boolean getEsameCreato() {
        return esameCreato;
    }


    public static final ProvidesKey<Corso> KEY_PROVIDER = new ProvidesKey<Corso>() {
        @Override
        public Object getKey(Corso item) {
            return item == null ? null : item.getNomeCorso();
        }
    };

    public void setCodocente(String codocente) {
        this.codocente = codocente;

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

    public void setEsameCreato(Boolean value){
        this.esameCreato = value;
    }
}