package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.unibo.progettosweng.client.model.Valutazione;

import java.util.ArrayList;


public interface ValutazioneServiceAsync {
    void add(String info[], AsyncCallback<String> callback)  throws IllegalArgumentException;
    void remove(String nomeCorso,String usernameStudente, AsyncCallback<String> callback)  throws IllegalArgumentException;
    void getValutazioni(AsyncCallback<Valutazione[]> callback) throws Exception;
    void aggiorna(Valutazione val,String nomeCorso, String studente, AsyncCallback<Valutazione> callback) throws Exception;
    void getValutazioniStudente(String studente, AsyncCallback<ArrayList<Valutazione>> callback) throws Exception;
}
