package com.unibo.progettosweng.model;

import com.unibo.progettosweng.CreazioneDB;
import com.unibo.progettosweng.server.SerializerEsame;
import com.unibo.progettosweng.server.SerializerUtente;
import org.mapdb.DB;
import org.mapdb.HTreeMap;

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

    public void setAula(String aula) {
        this.aula = aula;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDifficolta(String difficolta) {
        this.difficolta = difficolta;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public boolean inserimentoEsame(){

        //creo un riferimento alla classe CreazioneBD e creo la struttura data
        // HTreeeMap per salvare gli gli esami
        CreazioneDB<Esame> data = new CreazioneDB();
        DB db = data.getDb(CreazioneDB.DB_ESAMI);
        HTreeMap<String,Esame> map = data.getMap(db, CreazioneDB.ESAMI_MAP, new SerializerEsame() );

        map.put(String.valueOf(map.size() +1 ),this);
        db.close();
        return true;
        }
}


