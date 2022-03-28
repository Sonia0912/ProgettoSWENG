package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.unibo.progettosweng.client.model.Iscrizione;

import java.util.ArrayList;

public interface IscrizioneServiceAsync {
    void add(String studente, String corso, AsyncCallback<String> callback) throws IllegalArgumentException;
    void aggiorna(String vecchioCorso, String nuovoCorso, AsyncCallback<String> callback) throws IllegalArgumentException;
    void getIscrizioniStudente(String studente, AsyncCallback<ArrayList<Iscrizione>> callback) throws IllegalArgumentException;
}
