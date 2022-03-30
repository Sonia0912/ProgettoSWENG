package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.unibo.progettosweng.client.model.Registrazione;

import java.util.ArrayList;

public interface RegistrazioneServiceAsync {
    void add(String studente, String corso, AsyncCallback<String> callback) throws IllegalArgumentException;
    void aggiorna(String vecchioCorso, String nuovoCorso, AsyncCallback<String> callback) throws IllegalArgumentException;
    void getRegistrazioniStudente(String studente, AsyncCallback<ArrayList<Registrazione>> callback) throws IllegalArgumentException;
}
