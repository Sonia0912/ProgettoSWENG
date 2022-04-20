/**
 *  Classe che rappresenta l'oggetto Registrazione.
 **/
package com.unibo.progettosweng.client.model;

import java.io.Serializable;

public class Registrazione implements Serializable {

    private String studente;
    private String corso;

    public Registrazione(String studente, String corso) {
        this.studente = studente;
        this.corso = corso;
    }

    public Registrazione() {

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
