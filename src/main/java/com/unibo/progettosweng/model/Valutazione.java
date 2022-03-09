package com.unibo.progettosweng.model;

import com.unibo.progettosweng.CreazioneDB;
import com.unibo.progettosweng.server.SerializerUtente;
import com.unibo.progettosweng.server.SerializerValutazione;
import org.mapdb.DB;
import org.mapdb.HTreeMap;

public class Valutazione {

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
    }

    public boolean aggiungiValutazione(){

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
            map.put(String.valueOf(map.size() +1 ),this);
            db.close();
            return true;
        }
    }

    private boolean controlloDuplicato(HTreeMap<String, Valutazione> map) {

        for ( String i: map.getKeys()) {
            if (map.get(i).getNomeCorso().equals(this.nomeCorso) && map.get(i).getUsernameStudente().equals(this.usernameStudente) ){
                return true;
            }
        }
        return false;
    }


}
