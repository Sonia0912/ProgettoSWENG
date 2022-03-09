package com.unibo.progettosweng.model;

import com.unibo.progettosweng.CreazioneDB;
import com.unibo.progettosweng.server.SerializerUtente;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class Utente {

    private String nome;
    private String cognome;
    private String username;
    private String password;
    private String tipo;

    public Utente(String nome, String cognome, String username, String password, String tipo ){
        super();
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.tipo = tipo;
    }


    public String getNome(){
        return nome;
    }
    public String getCognome(){
        return cognome;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){return password;}
    public String getTipo(){return tipo;}

    public void setNome(String nome){
        this.nome = nome;
        aggiorna();
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
        aggiorna();
    }

    public void setPassword(String password) {
        this.password = password;
        aggiorna();
    }

    public boolean aggiuntiUtente(){

        //creo un riferimento alla classe CreazioneBD e creo la struttura data
        // HTreeeMap per salvare gli utenti
        CreazioneDB<Utente> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_UTENTI);
        HTreeMap<String,Utente> map = data.getMap(db, CreazioneDB.UTENTI_MAP, new SerializerUtente());

        if(controlloDuplicato(map) ){
            System.out.println("Utente già inserito!");
            db.close();
            return false;
        }else {
            map.put(String.valueOf(map.size() +1 ),this);
            db.close();
            return true;
        }
    }
    private boolean controlloDuplicato(HTreeMap<String,Utente> map){

        for ( String i: map.getKeys()) {
            if (map.get(i).getUsername().equals(this.username) ){
                return true;
            }
        }
        return false;

    }

    private void aggiorna(){
        CreazioneDB<Utente> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_UTENTI);
        HTreeMap<String, Utente> map = db.hashMap(CreazioneDB.UTENTI_MAP).counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerUtente()).createOrOpen();

        for ( String i: map.getKeys()) {
            if(map.get(i).getUsername().equals(this.username)){
                map.replace(i,this);
            }
        }
        db.close();
    }

    public static Utente[] getUtenti(){

        CreazioneDB<Utente> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_UTENTI);
        HTreeMap<String, Utente> map = db.hashMap(CreazioneDB.UTENTI_MAP).counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerUtente()).createOrOpen();
        Utente[] utenti = new Utente[map.size()];

        int k = 0;
        for ( String i: map.getKeys()) {
            utenti[k] = map.get(i);
            k++;
        }
        db.close();
        return utenti;

    }

    public boolean rimuoviUtente(){

        CreazioneDB<Utente> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_UTENTI);
        HTreeMap<String,Utente> map = data.getMap(db, CreazioneDB.UTENTI_MAP, new SerializerUtente());

        for ( String i: map.getKeys()) {
            if (map.get(i).getUsername().equals(this.username) ){
                map.remove(i);
                db.close();
                return true;
            }
        }
        return false;


    }
}
