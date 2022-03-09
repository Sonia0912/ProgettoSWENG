package com.unibo.progettosweng.model;

import com.unibo.progettosweng.CreazioneDB;
import com.unibo.progettosweng.server.SerializerCorso;
import com.unibo.progettosweng.server.SerializerUtente;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class Corso {

    private String nome;
    private String dataInizio;
    private String dataFine;
    private String descrizione;

    public Corso(String nome,String inizio, String fine, String descrizione){

        this.nome = nome;
        this.dataInizio = inizio;
        this.dataFine = fine;
        this.descrizione = descrizione;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getNomeCorso() {
        return nome;
    }

    public void setDataFine(String dataFine) {
        this.dataFine = dataFine;
        aggiorna();
    }

    public void setDataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
        aggiorna();
    }

    public void setDescrizione(String descrizione) {
        descrizione = descrizione;
        aggiorna();
    }

    public void setNome(String nome) {
        this.nome = nome;
        // aggiorna();
    }

    public boolean aggiungiCorso(){
       //creo un riferimento alla classe CreazioneBD e creo la struttura data
        // HTreeeMap per salvare gli utenti
        CreazioneDB<Corso> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_CORSI);
        HTreeMap<String,Corso> map = data.getMap(db, CreazioneDB.CORSI_MAP, new SerializerCorso());

        if(controllaDuplicato(map) ){
            System.out.println("Corso già inserito!");
            db.close();
            return false;
        }else {
            map.put(String.valueOf(map.size() +1 ),this);
            db.close();
            return true;
        }

    }

    private void aggiorna(){
        CreazioneDB<Corso> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_CORSI);
        HTreeMap<String, Corso> map = db.hashMap(CreazioneDB.DB_CORSI).counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerCorso()).createOrOpen();

        for ( String i: map.getKeys()) {
            if(map.get(i).getNomeCorso().equals(this.nome)){
                map.replace(i,this);
            }
        }
        db.close();
    }

    private boolean controllaDuplicato(HTreeMap<String, Corso> map) {

        for ( String i: map.getKeys()) {
            if (map.get(i).getNomeCorso().equals(this.nome) && map.get(i).getNomeCorso().equals(this.dataInizio) ){
                return true;
            }
        }
        return false;
    }

    public static Corso[] getCorsi(){

        CreazioneDB<Corso> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_CORSI);
        HTreeMap<String, Corso> map = db.hashMap(CreazioneDB.CORSI_MAP).counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerCorso()).createOrOpen();
        Corso[] corsi = new Corso[map.size()];

        int k = 0;
        for ( String i: map.getKeys()) {
            corsi[k] = map.get(i);
            k++;
        }
        db.close();
        return corsi;
    }


}
