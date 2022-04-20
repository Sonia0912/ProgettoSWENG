/**
 *  Classe che rappresenta l'oggetto Iscrizione.
 **/
package com.unibo.progettosweng.client.model;

import java.io.Serializable;

public class Iscrizione implements Serializable {

    private String studente;
    private String corso;

    public Iscrizione(String studente, String corso) {
        this.studente = studente;
        this.corso = corso;
    }

    public Iscrizione() {

    }

    public String getStudente() { return studente; }

    public String getCorso() { return corso; }

    public void setStudente(String studenteInput) {
        this.studente = studenteInput;
    }

    public void setCorso(String corsoInput) {
        this.corso = corsoInput;
    }

}
