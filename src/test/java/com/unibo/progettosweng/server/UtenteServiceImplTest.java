package com.unibo.progettosweng.server;

import com.unibo.progettosweng.client.model.Serializer.SerializerUtente;
import com.unibo.progettosweng.client.model.Utente;
import junit.framework.TestCase;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.util.ArrayList;
import java.util.Arrays;

public class UtenteServiceImplTest extends TestCase {

    private static ArrayList<String[]> utentiTest = new ArrayList<>(Arrays.asList(
            new String[]{"studente", "studente", "studente", "123", "studente"},
            new String[]{"docente", "docente", "docente", "123", "docente"},
            new String[]{"admin", "admin", "admin", "123", "admin"},
            new String[]{"segreteria", "segreteria", "segreteria", "123", "segreteria"}
    ));

    public void testAdd() {
        //prima di far partire il test eliminare il db utentiTest
        UtenteServiceImpl ut = new UtenteServiceImpl();
        DB db = DBMaker.fileDB("utentiTest.db").checksumHeaderBypass().make();
        HTreeMap<String, Utente> map = db.hashMap("utentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerUtente()).createOrOpen();
        ut.createOrOpenDB(map, db);

        //controllo che il test venga superato sia con utente inserito (db vuoto) che con utente già inserito
        for (int i = 0; i < 2; i++){
            int oldSize = map.size();
            String result = ut.add(utentiTest.get(0));
            if (result.startsWith("(")){
                assertEquals(oldSize + 1, map.size()); //utente inserito
            } else if (result.startsWith("Utente")){
                assertEquals(oldSize, map.size()); //utente già inserito -> non avviene alcun inserimento
            }
        }

        //controllo che l'inserimento avvenga anche quando il db non è vuoto
        int oldSize = map.size();
        String result = ut.add(utentiTest.get(1));
        if (result.startsWith("(")){
            assertEquals(oldSize + 1, map.size()); //utente inserito
        } else if (result.startsWith("Utente")){
            assertEquals(oldSize, map.size()); //utente già inserito -> non avviene alcun inserimento
        }
        db.close();
    }

    public void testRemove() {
        UtenteServiceImpl ut = new UtenteServiceImpl();
        DB db = DBMaker.fileDB("utentiTest.db").checksumHeaderBypass().make();
        HTreeMap<String, Utente> map = db.hashMap("utentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerUtente()).createOrOpen();
        ut.createOrOpenDB(map, db);

        int oldSize = map.size();
        String[] utente = utentiTest.get(0); //elimino un utente esistente
        ut.remove(new Utente(utente[0], utente[1], utente[2], utente[3], utente[4]));
        //controllo sia il caso in cui il db sia vuoto, sia che non lo sia
        assertEquals(oldSize == 0 ? 0 : oldSize - 1, map.size());

        String[] utenteNonEsistente = new String[]{"utente", "utente","utente","123","studente"}; //utente non esistente
        oldSize = map.size();
        ut.remove(new Utente(utenteNonEsistente[0], utenteNonEsistente[1], utenteNonEsistente[2], utenteNonEsistente[3], utenteNonEsistente[4]));
        //controllo nel caso l'utente non esista
        assertEquals(oldSize, map.size()); //non rimuove niente


    }

    public void testGetUtenti() throws Exception {
        UtenteServiceImpl ut = new UtenteServiceImpl();
        DB db = DBMaker.fileDB("utentiTest.db").checksumHeaderBypass().make();
        HTreeMap<String, Utente> map = db.hashMap("utentiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerUtente()).createOrOpen();
        ut.createOrOpenDB(map, db);

        int mapSize = map.size();
        Utente[] utenti = ut.getUtenti();
        assertEquals(mapSize, utenti.length);

        db.close();
    }

    public void testLogin() {

    }

    public void testAggiorna() {
    }

    public void testGetUtenteByUsername() {
    }

    public void testGetCodocenti() {
    }

    public void testGetStudenti() {
    }

    public void testGetDocenti() {
    }

    public void testGetNumeroStudenti() {
    }

    public void testGetNumeroDocenti() {
    }
}