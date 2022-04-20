/**
 *  Interfaccia client-side per l'RCP service.
 **/
package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.unibo.progettosweng.client.model.Valutazione;

import java.util.ArrayList;

@RemoteServiceRelativePath("valutazioni")
public interface ValutazioneService extends RemoteService {
    String add(String[] input) throws IllegalArgumentException;
    String addMore(ArrayList<String[]> listaValutazioni) throws IllegalArgumentException;
    String remove(String nomeCorso, String usernameStudente) throws IllegalArgumentException;
    Valutazione[] getValutazioni() throws Exception;
    void aggiorna(Valutazione val) throws Exception;
    ArrayList<Valutazione> getValutazioniStudente(String studente) throws Exception;
    ArrayList<Valutazione> getValutazioniFromEsame(String nomeCorsoEsame) throws IllegalArgumentException;
    ArrayList<Valutazione> getValutazioniDaInserire() throws Exception;
    ArrayList<Valutazione> getValutazioniDaPubblicare() throws Exception;
    Valutazione cambiaStatoValutazione(String username,String corso, int stato) throws Exception;
    Integer getSize() throws Exception;
}
