package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.unibo.progettosweng.client.model.Corso;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("corsi")
public interface CorsoService extends RemoteService {
        String add(String[] input) throws IllegalArgumentException;
        String remove(String key) throws IllegalArgumentException;
        Corso[] getCorsi() throws Exception;
        void aggiorna(Corso corso) throws Exception;
}
