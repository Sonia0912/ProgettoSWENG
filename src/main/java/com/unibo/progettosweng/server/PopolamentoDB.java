package com.unibo.progettosweng.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.unibo.progettosweng.model.Utente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.io.File;

public class PopolamentoDB  {
    public static final String DB_FILE = "file1.db";
    public static final String UTENTE_MAP = "utenteMap";

    public static void main(String[] args) {
        createMap();
        getUtenti();
    }

    private static void createMap() {
        // creo un riferimento al db (nota è lo stesso comando per creare il db)
        DB db = DBMaker.fileDB(DB_FILE).make();

        // creao un riferimento alla mappa nel db(nota è lo stesso comando per creare la
        // mappa)
        // il tipo HTreeMap è thread safe
        HTreeMap<String, Utente> map = db.hashMap(UTENTE_MAP).keySerializer(Serializer.STRING)
                .valueSerializer(new SerializerUtente()).createOrOpen();

        // aggiungo delle persone alla mappa
        map.put("0", new Utente("Gianluca", "Gueli","gianluca.gueli@studio.unibo.it"));
        map.put("1", new Utente("Sonia", "Nicoleetti","sonia.nicoletti@studio.unibo.it"));
        map.put("2", new Utente("Erika", "Colonna","erika.colonna@studio.unibo.it"));

        // chiudo la connessione al db
        db.close();
    }

    private static void readMap() {
        // creo un riferimento al db (nota è lo stesso comando per creare il db)
        DB db = DBMaker.fileDB(DB_FILE).make();

        // creao un riferimento alla mappa nel db(nota è lo stesso comando per creare la
        // mappa)
        // il tipo HTreeMap è thread safe
        HTreeMap<String, Utente> map = db.hashMap(UTENTE_MAP).keySerializer(Serializer.STRING)
                .valueSerializer(new SerializerUtente()).createOrOpen();

        // recupero delle persone dalla mappa
        System.out.println(map.get("0").getNome()+" "+map.get("0").getUsername());

        // chiudo la connessione al db
        db.close();
    }
    private static String[] getUtenti(){

        // creo un riferimento al db (nota è lo stesso comando per creare il db)
        DB db = DBMaker.fileDB(DB_FILE).make();


        // creao un riferimento alla mappa nel db(nota è lo stesso comando per creare la
        // mappa)
        // il tipo HTreeMap è thread safe
        HTreeMap<String, Utente> map = db.hashMap(UTENTE_MAP).keySerializer(Serializer.STRING)
                .valueSerializer(new SerializerUtente()).createOrOpen();

        String[] utenti = new String[map.getSize()];

        for ( String i: map.getKeys()
             ) {
            utenti[Integer.parseInt(i)] = map.get(i).getNome()+" "+map.get(i).getUsername();
            System.out.println(map.get(i).getNome()+" "+map.get(i).getUsername() + "\n");


        }

        // chiudo la connessione al db
        db.close();
        return utenti;

    }


}



