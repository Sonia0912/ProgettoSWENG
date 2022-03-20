package com.unibo.progettosweng.shared.model;

import com.unibo.progettosweng.shared.CreazioneDB;
import com.unibo.progettosweng.shared.SerializerEsame;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class Esame {

    private String data;
    private String ora;
    private String difficolta;
    private String aula;
    private String nomeCorso;

    public Esame(String data, String ora, String difficolta, String aula, String nomeCorso ){
        super();
        this.data = data;
        this.ora = ora;
        this.difficolta = difficolta;
        this.aula = aula;
        this.nomeCorso = nomeCorso;
    }

    public String getData() {
        return data;
    }

    public String getAula() {
        return aula;
    }

    public String getDifficolta() {
        return difficolta;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }

    public String getOra() {
        return ora;
    }

/*    public void setAula(String aula) {
        this.aula = aula;
        aggiorna();
    }

    public void setData(String data) {
        this.data = data;
        aggiorna();
    }

    public void setDifficolta(String difficolta) {
        this.difficolta = difficolta;
        aggiorna();
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
        aggiorna();
    }

    public void setOra(String ora) {
        this.ora = ora;
        aggiorna();
    }


    @Override
    public boolean add() {
        //creo un riferimento alla classe CreazioneBD e creo la struttura data
        // HTreeeMap per salvare gli gli esami
        CreazioneDB<Esame> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_ESAMI);
        HTreeMap<String,Esame> map = data.getMap(db, CreazioneDB.ESAMI_MAP, new SerializerEsame() );

        if (controlloDuplicato(map)){
            System.out.println("Esame già inserito!");
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
        CreazioneDB<Esame> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_ESAMI);
        HTreeMap<String,Esame> map = data.getMap(db, CreazioneDB.ESAMI_MAP, new SerializerEsame());

        for ( String i: map.getKeys()) {
            if (map.get(i).getData().equals(this.data) && map.get(i).getNomeCorso().equals(this.nomeCorso) ){
                map.remove(i);
                db.close();
                return true;
            }
        }
        return false;
    }

    private boolean controlloDuplicato(HTreeMap<String,Esame> map){

        for ( String i: map.getKeys()) {
            if (map.get(i).getNomeCorso().equals(this.nomeCorso) & map.get(i).getData().equals(this.data) & map.get(i).getOra().equals(this.data) ){
                return true;
            }
        }
        return false;
    }

    private void aggiorna(){
        CreazioneDB<Esame> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_ESAMI);
        HTreeMap<String, Esame> map = db.hashMap(CreazioneDB.ESAMI_MAP).counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerEsame()).createOrOpen();

        for ( String i: map.getKeys()) {
            if(map.get(i).getNomeCorso().equals(this.nomeCorso) & map.get(i).getData().equals(this.data) & map.get(i).getOra().equals(this.data) ){
                map.replace(i,this);
            }
        }
        db.close();
    }

    public static Esame[] getCollections() {
        CreazioneDB<Esame> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_ESAMI);
        HTreeMap<String, Esame> map = db.hashMap(CreazioneDB.ESAMI_MAP).counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerEsame()).createOrOpen();
        Esame[] esami = new Esame[map.size()];

        int k = 0;
        for ( String i: map.getKeys()) {
            esami[k] = map.get(i);
            k++;
        }
        db.close();
        return esami;
    }*/
}


