package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.unibo.progettosweng.client.model.Valutazione;

import java.util.ArrayList;

@RemoteServiceRelativePath("valutazioni")
public interface ValutazioneService extends RemoteService {
    String add(String[] input) throws IllegalArgumentException;
    String remove(String nomeCorso, String usernameStudente) throws IllegalArgumentException;
    Valutazione[] getValutazioni() throws Exception;
    Valutazione aggiorna(Valutazione val, String nomeCorso, String studente) throws Exception;
    ArrayList<Valutazione> getValutazioniStudente(String studente) throws Exception;
    ArrayList<Valutazione> getValutazioniFromEsame(String nomeCorsoEsame) throws IllegalArgumentException;
}
