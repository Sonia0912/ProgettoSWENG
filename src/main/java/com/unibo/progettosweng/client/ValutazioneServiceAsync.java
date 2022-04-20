/**
 *  Interfaccia asincrona per il servizio da chiamare lato client.
 **/
package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.unibo.progettosweng.client.model.Valutazione;

import java.util.ArrayList;

public interface ValutazioneServiceAsync {
    void add(String info[], AsyncCallback<String> callback)  throws IllegalArgumentException;
    void addMore(ArrayList<String[]> listaValutazione, AsyncCallback<String> callback) throws IllegalArgumentException;
    void remove(String nomeCorso,String usernameStudente, AsyncCallback<String> callback)  throws IllegalArgumentException;
    void getValutazioni(AsyncCallback<Valutazione[]> callback) throws Exception;
    void aggiorna(Valutazione val, AsyncCallback<Void> callback) throws Exception;
    void getValutazioniStudente(String studente, AsyncCallback<ArrayList<Valutazione>> callback) throws Exception;
    void getValutazioniFromEsame(String nomeCorsoEsame, AsyncCallback<ArrayList<Valutazione>> callback) throws IllegalArgumentException;
    void getValutazioniDaInserire(AsyncCallback<ArrayList<Valutazione>> callback) throws Exception;
    void getValutazioniDaPubblicare(AsyncCallback<ArrayList<Valutazione>> callback) throws Exception;
    void cambiaStatoValutazione(String username,String corso, int stato, AsyncCallback<Valutazione> callback) throws Exception;
    void getSize(AsyncCallback<Integer> callback) throws Exception;
}
