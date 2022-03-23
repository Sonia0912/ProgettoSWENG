package com.unibo.progettosweng.server;

import com.unibo.progettosweng.client.UtenteService;
import com.unibo.progettosweng.client.model.Serializer.SerializerUtente;
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
public class UtenteServiceImpl extends RemoteServiceServlet implements UtenteService {

    DB db;
    HTreeMap<String,Utente> map;

    private void createOrOpenDB(){
        this.db = getDb("utenti.db");
        this.map = this.db.hashMap("utentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerUtente()).createOrOpen();
    }

    @Override
    public String add(String[] input) throws IllegalArgumentException {
        createOrOpenDB();
        for(int i = 0; i < input.length; i++) {
            input[i] = escapeHtml(input[i]);
        }
        Utente ut = new Utente(input[0], input[1], input[2], input[3], input[4]);
        if(controlloUtenteDuplicato( map,ut)){
            return "Utente già inserito!";
        }else {
            map.put(String.valueOf(map.size() + 1), ut);
            db.commit();
            return "(size: " + map.size()+ ") L'utente " + ut.getNome() + " " + ut.getCognome() + " è stato creato e aggiunto al database.";
        }
    }

    @Override
    public String remove(String username) throws IllegalArgumentException {
        createOrOpenDB();
        for ( String i: map.getKeys()) {
            if(map.get(i).getUsername().equals(username)){
                map.remove(i);
                db.commit();
                return "La taglia: " + map.size() + " L'utente " + username + " è  stato rimosso.";
            }
        }
        return "Nessun utente Presente!";
    }

    @Override
    public Utente[] getUtenti() throws Exception{
        createOrOpenDB();
        Utente[] utenti = new Utente[map.size()];

        int k = 0;
        for ( String i: map.getKeys()) {
            utenti[k] = map.get(i);
            k++;
        }
        return utenti;
    }

    @Override
    public Utente login(String username, String password) {
        createOrOpenDB();
        for (String i : map.getKeys()) {
            if(map.get(i).getUsername().equals(username) & map.get(i).getPassword().equals(password)){
                return map.get(i);
            }
        }
        return null;
    }




    //metodo che viene invocato quando vengono modificato le informazioni degli utenti
    @Override
    public void aggiorna(Utente ut){
        createOrOpenDB();
        for ( String i: map.getKeys()) {
            if(map.get(i).getUsername().equals(ut.getUsername())){
                map.replace(i,ut);
            }
        }

    }

    @Override
    public Utente getUtenteByUsername(String username) throws Exception {
        createOrOpenDB();
        for (String i : map.getKeys()) {
            if(map.get(i).getUsername().equals(username)){
                return map.get(i);
            }
        }
        return null;
    }


    private boolean controlloUtenteDuplicato(HTreeMap<String,Utente> map, Utente utente){
        for ( String i: map.getKeys()) {
            if (map.get(i).getUsername().equals(utente.getUsername()) ){
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