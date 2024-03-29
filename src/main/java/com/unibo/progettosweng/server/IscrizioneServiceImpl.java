/**
 *  Classe che estende RemoteServiceServlet e implementa IscrizioneService.
 *  E' l'implementazione del servizio RCP lato server.
 **/
package com.unibo.progettosweng.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.unibo.progettosweng.client.IscrizioneService;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Iscrizione;
import com.unibo.progettosweng.client.model.Serializer.SerializerIscrizione;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import javax.servlet.ServletContext;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class IscrizioneServiceImpl extends RemoteServiceServlet implements IscrizioneService {

    DB db;
    HTreeMap<String, Iscrizione> map;

    @Override
    public String add(String studente, String corso) throws IllegalArgumentException {
        createOrOpenDB();
        studente = escapeHtml(studente);
        corso = escapeHtml(corso);
        Iscrizione iscr = new Iscrizione(studente, corso);
        if(controlloIscrizioneDuplicata(map, iscr)) {
            return "Ti sei già iscritto a questo corso";
        } else {
            map.put(String.valueOf(map.size() + 1), iscr);
            db.commit();
            return "Ti sei iscritto con successo al corso di " + corso;
        }
    }

    public ArrayList<Iscrizione> getIscrizioniStudente(String studente) {
        createOrOpenDB();
        ArrayList<Iscrizione> iscrizioni = new ArrayList<>();
        for (String i: map.getKeys()) {
            if(map.get(i).getStudente().equals(studente)) {
                iscrizioni.add(map.get(i));
            }
        }
        return iscrizioni;
    }

    private boolean controlloIscrizioneDuplicata(HTreeMap<String, Iscrizione> map, Iscrizione iscr){
        for (String i: map.getKeys()) {
            if (map.get(i).getCorso().equals(iscr.getCorso()) && map.get(i).getStudente().equals(iscr.getStudente())){
                return true;
            }
        }
        return false;
    }

    // metodo per assicurarsi che non le stringhe vengano lette come tali e non come codice html
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }

    // Apre il db iscrizioni.db e se non esiste lo crea
    private void createOrOpenDB(){
        this.db = getDb("iscrizioni.db");
        this.map = this.db.hashMap("iscrizioniMap").counterEnable().keySerializer(Serializer.STRING)
                .valueSerializer(new SerializerIscrizione()).createOrOpen();
    }

    private DB getDb(String nameDB){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB_Iscrizione");
            if(db == null) {
                db = DBMaker.fileDB(nameDB).make();
                context.setAttribute("DB_Iscrizione", db);
            }
            return db;
        }
    }

}
