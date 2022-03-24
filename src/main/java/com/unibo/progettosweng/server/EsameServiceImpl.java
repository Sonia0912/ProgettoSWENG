package com.unibo.progettosweng.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.unibo.progettosweng.client.EsameService;

import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Esame;
import com.unibo.progettosweng.client.model.Serializer.SerializerCorso;
import com.unibo.progettosweng.client.model.Serializer.SerializerEsame;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class EsameServiceImpl extends RemoteServiceServlet implements EsameService {

    DB db;
    HTreeMap<String, Esame> map;

    private void createOrOpenDB(){
        this.db = getDb("esami.db");
        this.map = this.db.hashMap("esamiMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerEsame()).createOrOpen();
    }

    private DB getDb(String nameDB) {
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB");
            if(db == null) {
                db = DBMaker.fileDB(nameDB).make();
                context.setAttribute("DB", db);
            }
            return db;
        }
    }

    @Override
    public String add(String[] input) throws IllegalArgumentException {
        createOrOpenDB();
        for(int i = 0; i < input.length; i++) {
            input[i] = escapeHtml(input[i]);
        }
        Esame esame = new Esame(input[0], input[1], input[2], input[3], input[4]);
        if(controlloEsamiDuplicati( map,esame)){
            return "Esame già inserito!";
        }else {
            map.put(String.valueOf(map.size() + 1), esame);
            db.commit();
            return "(size: " + map.size()+ ") L'esame di " + esame.getNomeCorso() + " con data: " + esame.getData() + " è stato creato e aggiunto al database.";
        }
    }

    private boolean controlloEsamiDuplicati(HTreeMap<String, Esame> map, Esame esame) {

        for ( String i: map.getKeys()) {
            if (map.get(i).getNomeCorso().equals(esame.getNomeCorso()) ){
                return true;
            }
        }
        return false;
    }

    @Override
    public String remove(String key) throws IllegalArgumentException {
        createOrOpenDB();
        for ( String i: map.getKeys()) {
            if(map.get(i).getNomeCorso().equals(key)){
                map.remove(i);
                db.commit();
                return "La taglia: " + map.size() + " L'esame di " + key + " è  stato rimosso.";
            }
        }
        return "Nessun corso Presente!";
    }

    @Override
    public Esame[] getEsami() throws Exception {
        createOrOpenDB();
        Esame[] esames = new Esame[map.size()];

        int k = 0;
        for ( String i: map.getKeys()) {
            esames[k] = map.get(i);
            k++;
        }
        return esames;
    }

    @Override
    public void aggiorna(Esame esame) throws Exception {
        createOrOpenDB();
        for ( String i: map.getKeys()) {
            if(map.get(i).getNomeCorso().equals(esame.getNomeCorso())){
                map.replace(i,esame);
            }
        }
    }
    // metodo per assicurarsi che non le stringhe vengano lette come tali e non come codice html
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }





}
