package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Esame;

import java.util.ArrayList;


@RemoteServiceRelativePath("esami")
public interface EsameService extends RemoteService {
    String add(String[] input) throws IllegalArgumentException;
    String remove(String key) throws IllegalArgumentException;
    Esame[] getEsami() throws Exception;
    void aggiorna(Esame esame) throws Exception;
    ArrayList<Esame> getEsamiFromCorsi(ArrayList<Corso> corsi) throws Exception;
}
