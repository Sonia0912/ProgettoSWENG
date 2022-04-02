package com.unibo.progettosweng.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.unibo.progettosweng.client.model.Registrazione;

import java.util.ArrayList;

@RemoteServiceRelativePath("registrazioni")
public interface RegistrazioneService extends RemoteService {
    String add(String studente, String corso) throws IllegalArgumentException;
    String aggiorna(String vecchioCorso, String nuovoCorso) throws IllegalArgumentException;
    ArrayList<Registrazione> getRegistrazioniStudente(String studente) throws IllegalArgumentException;
    ArrayList<Registrazione> getRegistrazioniFromEsame(String nomeCorso) throws IllegalArgumentException;
}
