package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.unibo.progettosweng.client.model.Utente;

import java.util.ArrayList;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface UtenteServiceAsync {
    //tutti questi metodi devono essere void
    void remove(Utente ut, AsyncCallback<Void> callback) throws IllegalArgumentException;
    void add(String[] input, AsyncCallback<String> callback) throws IllegalArgumentException;
    void getUtenti(AsyncCallback<Utente[]> callback) throws Exception;
    void login(String username, String password, AsyncCallback<Utente> callback);
    void aggiorna(Utente utente, String usernameOriginale, AsyncCallback<Utente> callback) throws Exception;
    void getUtenteByUsername(String username, AsyncCallback<Utente> callback) throws Exception;
    void getCodocenti(String username, AsyncCallback<ArrayList<Utente>> callback) throws Exception;
    void getStudenti(AsyncCallback<ArrayList<Utente>> callback) throws Exception;
    void getDocenti(AsyncCallback<ArrayList<Utente>> callback) throws Exception;
    void getNumeroStudenti(AsyncCallback<Integer> callback) throws Exception;
    void getNumeroDocenti(AsyncCallback<Integer> callback) throws Exception;
}