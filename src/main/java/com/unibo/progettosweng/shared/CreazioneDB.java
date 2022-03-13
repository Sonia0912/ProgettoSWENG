package com.unibo.progettosweng.shared;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class CreazioneDB<T> {
    public static final String DB_UTENTI = "db/utenti.db";
    public static final String UTENTI_MAP = "utentiMap";

    public static final String DB_ESAMI = "db/esami.db";
    public static final String ESAMI_MAP = "esamiMap";

    public static final String DB_CORSI = "db/corsi.db";
    public static final String CORSI_MAP = "corsiMap";

    public static final String DB_VOTI = "db/voti.db";
    public static final String VOTI_MAP = "votiMap";

    public static final String DB_INSEGNAMENTO = "db/insegnamento.db";
    public static final String INSEGNAMETO_MAP = "titolareMap";


    public DB getDb(String nameDB){
        return DBMaker.fileDB(nameDB).make();
    }

   public HTreeMap<String, T > getMap(DB db, String nameMap, Serializer obj){
       HTreeMap<String,T> map = null;
        if(obj != null){
           if(db.isClosed()){
              System.out.println("BD chiuso!");
           }else {
               map = db.hashMap(nameMap).counterEnable().keySerializer(Serializer.STRING)
                       .valueSerializer(obj).createOrOpen();
           }


        }
        return map;
    }



}
