/**
 *  Classe che estende RemoteServiceServlet e implementa RegistrazioneService.
 *  E' l'implementazione del servizio RCP lato server.
 **/
package com.unibo.progettosweng.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.unibo.progettosweng.client.RegistrazioneService;
import com.unibo.progettosweng.client.model.Iscrizione;
import com.unibo.progettosweng.client.model.Registrazione;
import com.unibo.progettosweng.client.model.Serializer.SerializerRegistrazione;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class RegistrazioneServiceImpl extends RemoteServiceServlet implements RegistrazioneService {

    DB db;
    HTreeMap<String, Registrazione> map;

    @Override
    public String add(String studente, String corso) throws IllegalArgumentException {
        createOrOpenDB();
        studente = escapeHtml(studente);
        corso = escapeHtml(corso);
        Registrazione registr = new Registrazione(studente, corso);
        map.put(String.valueOf(map.size() + 1), registr);
        db.commit();
        return "successo";
    }

    @Override
    public ArrayList<Registrazione> getRegistrazioniStudente(String studente) throws IllegalArgumentException {
        createOrOpenDB();
        ArrayList<Registrazione> registrazioni = new ArrayList<>();
        for (String i: map.getKeys()) {
            if(map.get(i).getStudente().equals(studente)) {
                registrazioni.add(map.get(i));
            }
        }
        return registrazioni;
    }

    @Override
    public ArrayList<Registrazione> getRegistrazioniFromEsame(String nomeCorso) throws IllegalArgumentException {
        createOrOpenDB();
        ArrayList<Registrazione> registrazioni = new ArrayList<>();
        for (String i: map.getKeys()){
            if(map.get(i).getCorso().equals(nomeCorso)){
                registrazioni.add(map.get(i));
            }
        }
        return registrazioni;
    }

    public Integer getSize(){
        return map.getSize();
    }

    // metodo per assicurarsi che non le stringhe vengano lette come tali e non come codice html
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }

    // Apre il db registrazioni.db e se non esiste lo crea
    private void createOrOpenDB(){
        this.db = getDb("registrazioni.db");
        this.map = this.db.hashMap("registrazioniMap").counterEnable().keySerializer(Serializer.STRING)
                .valueSerializer(new SerializerRegistrazione()).createOrOpen();
    }

    private DB getDb(String nameDB){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB_Registrazione");
            if(db == null) {
                db = DBMaker.fileDB(nameDB).make();
                context.setAttribute("DB_Registrazione", db);
            }
            return db;
        }
    }

}
