package com.unibo.progettosweng.model;

import com.unibo.progettosweng.CreazioneDB;
import com.unibo.progettosweng.server.SerializerCorso;
import com.unibo.progettosweng.server.SerializerInsegnamento;
import com.unibo.progettosweng.server.SerializerUtente;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class Insegnamento implements OperazioniDB<Insegnamento>{

    private String nomeDocente;
    private String nomeCorso;
    private String tipologia;

    public Insegnamento(String nomeDocente, String nomeCorso, String tipologia){
        this.nomeDocente = nomeDocente;
        this.nomeCorso = nomeCorso;
        this.tipologia = tipologia;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }
    public String getNomeDocente(){
        return nomeDocente;
    }
    public String getTipologia(){
        return tipologia;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
        aggiorna();
    }

    public void setNomeDocente(String nomeDocente) {
        this.nomeDocente = nomeDocente;
        aggiorna();
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
        aggiorna();
    }

    @Override
    public boolean add() {
        //creo un riferimento alla classe CreazioneBD e creo la struttura data
        // HTreeeMap per salvare gli utenti
        CreazioneDB<Insegnamento> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.INSEGNAMETO_MAP);
        HTreeMap<String,Insegnamento> map = data.getMap(db, CreazioneDB.INSEGNAMETO_MAP, new SerializerInsegnamento());

        if(controllaDuplicato(map) ){
            System.out.println("Insegnamento già inserito!");
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
        CreazioneDB<Insegnamento> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_INSEGNAMENTO);
        HTreeMap<String,Insegnamento> map = data.getMap(db, CreazioneDB.INSEGNAMETO_MAP, new SerializerInsegnamento());

        for ( String i: map.getKeys()) {
            if ((map.get(i).getNomeDocente().equals(this.nomeDocente)) & (map.get(i).getNomeCorso().equals(this.nomeCorso)) ){
                map.remove(i);
                db.close();
                return true;
            }
        }
        return false;
    }

    private boolean controllaDuplicato(HTreeMap<String, Insegnamento> map) {
        for ( String i: map.getKeys()) {
            if ((map.get(i).getNomeCorso().equals(this.nomeCorso)) && (map.get(i).getNomeDocente().equals(this.nomeDocente)) ){
                return true;
            }
        }
        return false;
    }

    private void aggiorna(){
        CreazioneDB<Insegnamento> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_INSEGNAMENTO);
        HTreeMap<String, Insegnamento> map = db.hashMap(CreazioneDB.INSEGNAMETO_MAP).counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerInsegnamento()).createOrOpen();

        for ( String i: map.getKeys()) {
            if((map.get(i).getNomeCorso().equals(this.nomeCorso)) && (map.get(i).getNomeDocente().equals(this.nomeDocente)) ){
                map.replace(i,this);
            }
        }
        db.close();
    }

    public static Insegnamento[] getCollections() {
        CreazioneDB<Insegnamento> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_INSEGNAMENTO);
        HTreeMap<String,Insegnamento> map = data.getMap(db, CreazioneDB.INSEGNAMETO_MAP, new SerializerInsegnamento());
        Insegnamento[] insegnamenti = new Insegnamento[map.size()];

        int k = 0;
        for ( String i: map.getKeys()) {
            insegnamenti[k] = map.get(i);
            k++;
        }
        db.close();
        return insegnamenti;
    }
}
