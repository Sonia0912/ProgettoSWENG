package com.unibo.progettosweng.server;

import com.unibo.progettosweng.client.CorsoService;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Serializer.SerializerCorso;
import com.unibo.progettosweng.client.model.Utente;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CorsoServiceImpl extends RemoteServiceServlet implements CorsoService {

    DB db;
    HTreeMap<String,Corso> map;

    private void createOrOpenDB(){
        this.db = getDb("corsi.db");
        this.map = this.db.hashMap("corsiMap").counterEnable().keySerializer(Serializer.STRING)
                .valueSerializer(new SerializerCorso()).createOrOpen();
    }

    @Override
    public String add(String[] input) throws IllegalArgumentException {
        createOrOpenDB();
        for(int i = 0; i < input.length; i++) {
            input[i] = escapeHtml(input[i]);
        }
        Corso corso = new Corso(input[0], input[1], input[2], input[3], input[4]);
        if(controlloUtenteDuplicato( map,corso)){
            return "Utente già inserito!";
        }else {
            map.put(String.valueOf(map.size() + 1), corso);
            db.commit();
            return "(size: " + map.size()+ ") L'utente " + corso.getNomeCorso() + ", inzio: " + corso.getDataInizio() + " è stato creato e aggiunto al database.";
        }
    }

    @Override
    public String remove(String nomeCorso) throws IllegalArgumentException {
        createOrOpenDB();
        for ( String i: map.getKeys()) {
            if(map.get(i).getNomeCorso().equals(nomeCorso)){
                map.remove(i);
                db.commit();
                return "La taglia: " + map.size() + " Il corso " + nomeCorso + " è  stato rimosso.";
            }
        }
        return "Nessun corso Presente!";
    }

    @Override
    public Corso[] getCorsi() throws Exception{
        createOrOpenDB();
        Corso[] corsi = new Corso[map.size()];

        int k = 0;
        for ( String i: map.getKeys()) {
            corsi[k] = map.get(i);
            k++;
        }
        return corsi;
    }


    //metodo che viene invocato quando vengono modificato le informazioni degli utenti
    @Override
    public void aggiorna(Corso corso){
        createOrOpenDB();
        for ( String i: map.getKeys()) {
            if(map.get(i).getNomeCorso().equals(corso.getNomeCorso())){
                map.replace(i,corso);
            }
        }

    }


    private boolean controlloUtenteDuplicato(HTreeMap<String, Corso> map, Corso utente){
        for ( String i: map.getKeys()) {
            if (map.get(i).getNomeCorso().equals(utente.getNomeCorso()) ){
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


    private DB getDb(String nameDB){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB");
            if(db == null) {
                db = DBMaker.fileDB(nameDB).make();
                context.setAttribute("DB", db);
            }
            return db;
        }
    }

}