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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

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
    public Utente aggiorna(Utente ut, String usernameOriginale){
        createOrOpenDB();
        for (String i: map.getKeys()) {
            if(map.get(i).getUsername().equals(usernameOriginale)){
                map.replace(i,ut);
            }
        }
        return ut;
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

    @Override
    public ArrayList<Utente> getCodocenti(String username) throws Exception {
        createOrOpenDB();
        ArrayList<Utente> codocenti = new ArrayList<>();
        for (String i: map.getKeys()) {
            if(!map.get(i).getUsername().equals(username) && map.get(i).getTipo().equals("Docente")){
                codocenti.add(map.get(i));
            }
        }
        return codocenti;
    }

    @Override
    public ArrayList<Utente> getStudenti() throws Exception {
        createOrOpenDB();
        ArrayList<Utente> studenti = new ArrayList<>();
        for (String i: map.getKeys()) {
            if(map.get(i).getTipo().equals("Studente")){
                studenti.add(map.get(i));
            }
        }
        return studenti;
    }

    @Override
    public ArrayList<Utente> getDocenti() throws Exception {
        createOrOpenDB();
        ArrayList<Utente> docenti = new ArrayList<>();
        for (String i: map.getKeys()) {
            if(map.get(i).getTipo().equals("Docente")){
                docenti.add(map.get(i));
            }
        }
        return docenti;
    }

    public int getNumeroStudenti() {
        createOrOpenDB();
        int count = 0;
        for(int i = 0; i < map.size(); i++) {
            if(map.get(i).getTipo().equals("Studente")) {
                count++;
            }
        }
        return count;
    }

    public int getNumeroDocenti() {
        createOrOpenDB();
        int count = 0;
        for(int i = 0; i < map.size(); i++) {
            if(map.get(i).getTipo().equals("Docente")) {
                count++;
            }
        }
        return count;
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