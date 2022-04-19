package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.unibo.progettosweng.client.model.Corso;

import java.util.ArrayList;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("corsi")
public interface CorsoService extends RemoteService {
        String add(String[] input) throws IllegalArgumentException;
        String remove(String key) throws IllegalArgumentException;
        Corso[] getCorsi() throws Exception;
        void aggiorna(Corso corso) throws Exception;
        ArrayList<Corso> getCorsiDocente(String usernameDocente) throws Exception;
        ArrayList<Corso> getCorsiCoDocente(String usernameCoDocente) throws Exception;
        Corso getCorso(String nomeCorso) throws Exception;
        ArrayList<Corso> getListaCorsiIscrizioni(ArrayList<String> nomiCorsi) throws Exception;
        int getNumeroCorsi() throws Exception;
}
