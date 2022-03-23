package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.unibo.progettosweng.client.model.Utente;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("utenti")
public interface UtenteService extends RemoteService {
    String add(String[] input) throws IllegalArgumentException;
    String remove(String key) throws IllegalArgumentException;
    Utente[] getUtenti() throws Exception;
    Utente login(String username, String password) ;
    void aggiorna(Utente utente) throws Exception;
    Utente getUtenteByUsername(String username) throws Exception;
}