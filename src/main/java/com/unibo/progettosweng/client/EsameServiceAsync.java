/**
 *  Interfaccia asincrona per il servizio da chiamare lato client.
 **/
package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Esame;

import java.util.ArrayList;

public interface EsameServiceAsync {
    void remove(String key, AsyncCallback<String> callback) throws IllegalArgumentException;
    void add(String[] input, AsyncCallback<String> callback) throws IllegalArgumentException;
    void getEsami(AsyncCallback<ArrayList<Esame>> callback) throws Exception;
    void aggiorna(Esame esame, AsyncCallback<Esame> callback) throws Exception;
    void getEsamiFromCorsi(ArrayList<Corso> corsi, AsyncCallback<ArrayList<Esame>> callback) throws Exception;
    void getEsamiFromNomeCorsi(ArrayList<String> corsi, AsyncCallback<ArrayList<Esame>> callback) throws Exception;
    void getSize(AsyncCallback<Integer> callback) throws Exception;
}