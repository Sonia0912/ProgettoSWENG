package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.unibo.progettosweng.client.model.Iscrizione;

import java.util.ArrayList;

@RemoteServiceRelativePath("iscrizioni")
public interface IscrizioneService extends RemoteService {
    String add(String studente, String corso) throws IllegalArgumentException;
    ArrayList<Iscrizione> getIscrizioniStudente(String studente) throws IllegalArgumentException;
}
