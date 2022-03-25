package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.unibo.progettosweng.client.model.Corso;

public interface CorsoServiceAsync {
    //tutti questi metodi devono essere void
    void remove(String key, AsyncCallback<String> callback) throws IllegalArgumentException;
    void add(String[] input, AsyncCallback<String> callback) throws IllegalArgumentException;
    void getCorso(AsyncCallback<Corso[]> callback) throws Exception;
    void getCorsi(AsyncCallback<Corso[]> callback) throws Exception;
    void aggiorna(Corso corso, AsyncCallback<Corso> callback) throws Exception;
}