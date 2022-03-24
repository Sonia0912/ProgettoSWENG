package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.unibo.progettosweng.client.model.Esame;

public interface EsameServiceAsync {
    //tutti questi metodi devono essere void
    void remove(String key, AsyncCallback<String> callback) throws IllegalArgumentException;
    void add(String[] input, AsyncCallback<String> callback) throws IllegalArgumentException;
    void getEsami(AsyncCallback<Esame[]> callback) throws Exception;
    void aggiorna(Esame esame, AsyncCallback<Esame> callback) throws Exception;
}