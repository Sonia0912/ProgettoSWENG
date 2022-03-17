package com.unibo.progettosweng.shared.model;

import com.unibo.progettosweng.shared.CreazioneDB;
import com.unibo.progettosweng.shared.SerializerValutazione;
import org.mapdb.DB;
import org.mapdb.HTreeMap;

public class Valutazione implements OperazioniDB<Valutazione> {

    private String nomeCorso;
    private String usernameStudente;
    private int voto;

    public Valutazione(String nomeCorso, String usernameStudente, int voto ){
        this.nomeCorso = nomeCorso;
        this.usernameStudente = usernameStudente;
        this.voto = voto;
    }


    public String getNomeCorso() {
        return nomeCorso;
    }

    public String getUsernameStudente() {
        return usernameStudente;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
        aggiorna();
    }



    @Override
    public boolean add() {
        //creo un riferimento alla classe CreazioneBD e creo la struttura data
        // HTreeeMap per salvare gli utenti
        CreazioneDB<Valutazione> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_VOTI);
        HTreeMap<String,Valutazione> map = data.getMap(db, CreazioneDB.VOTI_MAP, new SerializerValutazione());

        if(controlloDuplicato(map) ){
            System.out.println("Valutazione già inserito!");
            db.close();
            return false;
        }else {
            map.put(String.valueOf(map.size() + 1 ),this);
            db.close();
            return true;
        }
    }

    @Override
    public boolean remove() {
            CreazioneDB<Valutazione> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_VOTI);
        HTreeMap<String,Valutazione> map = data.getMap(db, CreazioneDB.CORSI_MAP, new SerializerValutazione());

        for ( String i: map.getKeys()) {
            if (map.get(i).getNomeCorso().equals(this.nomeCorso) && map.get(i).getUsernameStudente().equals(this.usernameStudente) ){
                map.remove(i);
                db.close();
                return true;
            }
        }
        return false;
    }

    private boolean controlloDuplicato(HTreeMap<String, Valutazione> map) {

        for ( String i: map.getKeys()) {
            if ((map.get(i).getNomeCorso().equals(this.nomeCorso)) && (map.get(i).getUsernameStudente().equals(this.usernameStudente) )){
                return true;
            }
        }
        return false;
    }

    private void aggiorna(){
        CreazioneDB<Valutazione> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_VOTI);
        HTreeMap<String,Valutazione> map = data.getMap(db, CreazioneDB.CORSI_MAP, new SerializerValutazione());

        for ( String i: map.getKeys()) {
            if((map.get(i).getNomeCorso().equals(this.nomeCorso)) && (map.get(i).getUsernameStudente().equals(this.usernameStudente) )){
                map.replace(i,this);
            }
        }
        db.close();
    }

    public static Valutazione[] getCollections() {
        CreazioneDB<Valutazione> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_VOTI);
        HTreeMap<String,Valutazione> map = data.getMap(db, CreazioneDB.CORSI_MAP, new SerializerValutazione());
        Valutazione[] valutazioni = new Valutazione[map.size()];

        int k = 0;
        for ( String i: map.getKeys()) {
            valutazioni[k] = map.get(i);
            k++;
        }
        db.close();
        return valutazioni;
    }

}
