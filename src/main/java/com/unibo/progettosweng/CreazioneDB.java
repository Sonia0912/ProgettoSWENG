package com.unibo.progettosweng;

import com.unibo.progettosweng.model.Utente;
import com.unibo.progettosweng.server.SerializerUtente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class CreazioneDB {
    public static final String DB_UTENTI = "db/studenti.db";
    public static final String UTENTI_MAP = "studentiMap";

    public static final String DB_CORSI = "db/corsi.db";
    public static final String CORSI_MAP = "corsiMap";

    public static final String DB_VOTI = "db/voti.db";
    public static final String VOTI_MAP = "votiMap";

    public static final String DB_ESAMI = "db/esami.db";
    public static final String ESAMI_MAP = "esamiMap";

    private  DB db;

    public int createMap(String dataBase, String nameMap) {
        // creo un riferimento al db (nota è lo stesso comando per creare il db)
        this.db = DBMaker.fileDB(dataBase).make();
        // creao un riferimento alla mappa nel db(nota è lo stesso comando per creare la
        // mappa)
        // il tipo HTreeMap è thread safe
        HTreeMap<String, Utente> map = db.hashMap(nameMap).counterEnable().keySerializer(Serializer.STRING)
                .valueSerializer(new SerializerUtente()).createOrOpen();

        // chiudo la connessione al db
        db.close();
        return 1;
    }

    public DB getDb(String nameDB){
        return DBMaker.fileDB(nameDB).make();
    }

    public int insert(String db, String mapName, String [] args){

        if(db.equals(DB_UTENTI)){
            if (args.length == 3){
                DB data = DBMaker.fileDB(DB_UTENTI).make();
                HTreeMap<String, Utente> map = data.hashMap(mapName).counterEnable().keySerializer(Serializer.STRING)
                        .valueSerializer(new SerializerUtente()).createOrOpen();
                map.put(String.valueOf(map.size() +1 ),new Utente(args[0],args[1],args[2]));
                data.close();
                return 1;
            }else {return -1;}
        }
            return -1;
    }


    public String getUsernameUtente(String db, String utentiMap, String nome) {

        if(db.equals(DB_UTENTI)){
            DB data = DBMaker.fileDB(DB_UTENTI).make();
            HTreeMap<String, Utente> map = data.hashMap(utentiMap).counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerUtente()).createOrOpen();

            for ( String i: map.getKeys()) {

                if (map.get(i).getNome().equals(nome) ){
                    data.close();
                    return map.get(i).getUsername();
                }
            }
            return "";
        }
        return "";
    }
}

