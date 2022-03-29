package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RegistrazioneServiceAsync {
    void add(String studente, String corso, AsyncCallback<String> callback) throws IllegalArgumentException;
    void aggiorna(String vecchioCorso, String nuovoCorso, AsyncCallback<String> callback) throws IllegalArgumentException;
}
