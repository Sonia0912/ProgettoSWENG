package com.unibo.progettosweng.shared.model;

import com.google.gwt.view.client.ProvidesKey;
import com.unibo.progettosweng.shared.CreazioneDB;
import com.unibo.progettosweng.shared.SerializerCorso;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class Corso {

    private String nome;
    private String dataInizio;
    private String dataFine;
    private String descrizione;
    private String dipartimento;

    public Corso(String nome,String inizio, String fine, String descrizione, String dipartimento){

        this.nome = nome;
        this.dataInizio = inizio;
        this.dataFine = fine;
        this.descrizione = descrizione;
        this.dipartimento = dipartimento;
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

    public String getDipartimento() {
        return dipartimento;
    }

    public static final ProvidesKey<Corso> KEY_PROVIDER = new ProvidesKey<Corso>() {
        @Override
        public Object getKey(Corso item) {
            return item == null ? null : item.getNomeCorso();
        }
    };

/*    public void setDipartimento(String dipartimento){
        this.dipartimento = dipartimento;
        aggiorna();
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

    @Override
    public boolean add() {
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

    @Override
    public boolean remove() {
        CreazioneDB<Corso> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_CORSI);
        HTreeMap<String,Corso> map = data.getMap(db, CreazioneDB.CORSI_MAP, new SerializerCorso());

        for ( String i: map.getKeys()) {
            if (map.get(i).getNomeCorso().equals(this.nome) && map.get(i).getDataInizio().equals(this.dataInizio) ){
                map.remove(i);
                db.close();
                return true;
            }
        }
        return false;
    }

    private boolean controllaDuplicato(HTreeMap<String, Corso> map) {

        for ( String i: map.getKeys()) {
            if ((map.get(i).getNomeCorso().equals(this.nome)) & (map.get(i).getDataInizio().equals(this.dataInizio) )){
                return true;
            }
        }
        return false;
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


    public static Corso[] getCollections() {
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
    }*/


}
