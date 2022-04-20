/**
 *  Interfaccia asincrona per il servizio da chiamare lato client.
 **/
package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.unibo.progettosweng.client.model.Corso;
import java.util.ArrayList;

public interface CorsoServiceAsync {
    void add(String[] input, AsyncCallback<String> callback) throws IllegalArgumentException;
    void remove(String key, AsyncCallback<String> callback) throws IllegalArgumentException;
    void getCorsi(AsyncCallback<Corso[]> callback) throws Exception;
    void aggiorna(Corso corso, AsyncCallback<Void> callback) throws Exception;
    void getCorsiDocente(String usernameDocente, AsyncCallback<ArrayList<Corso>> callback) throws Exception;
    void getCorsiCoDocente(String usernameCoDocente, AsyncCallback<ArrayList<Corso>> callback) throws Exception;
    void getCorso(String nomeCorso, AsyncCallback<Corso> callback) throws Exception;
    void getListaCorsiIscrizioni(ArrayList<String> nomiCorsi, AsyncCallback<ArrayList<Corso>> callback) throws Exception;
    void getNumeroCorsi(AsyncCallback<Integer> callback) throws Exception;
}