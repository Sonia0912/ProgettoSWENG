package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.unibo.progettosweng.client.model.Utente;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface UtenteServiceAsync {
    //tutti questi metodi devono essere void
    void remove(String key, AsyncCallback<String> callback) throws IllegalArgumentException;
    void add(String[] input, AsyncCallback<String> callback) throws IllegalArgumentException;
    void getUtenti(AsyncCallback<Utente[]> callback) throws Exception;
    void login(String username, String password, AsyncCallback<Boolean> callback) throws Exception;
    void aggiorna(Utente utente, AsyncCallback<Utente> callback) throws Exception;
}