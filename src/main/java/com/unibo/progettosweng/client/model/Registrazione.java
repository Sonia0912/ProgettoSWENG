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

/*    @Override
    public String toString() {
        return this.studente.getNome() + "|" + this.studente.getCognome() + "|" + this.studente.getUsername() +
                "|" + this.studente.getPassword() + "|" + this.studente.getTipo() +
                "|" + this.corso.getNomeCorso() + "|" + this.corso.getDataInizio() +
                "|" + this.corso.getDataFine() + "|" + this.corso.getDescrizione() + "|" + this.corso.getDipartimento() +
                "|" + this.corso.getDocente() + "|" + this.corso.getCodocente() + "|" + this.corso.getEsameCreato();
    }

    public static Utente getUtenteDB(String dbData) {
        String[] info = dbData.split("|");
        Utente ut = new Utente(info[0], info[1], info[2], info[3], info[4]);
        return ut;
    }

    public static Corso getCorsoDB(String dbData) {
        String[] info = dbData.split("|");
        Corso c = new Corso(info[5], info[6], info[7], info[8], info[9], info[10], info[11], Boolean.valueOf(info[12]));
        return c;
    }*/

}
