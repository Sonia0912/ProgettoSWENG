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
import java.util.ArrayList;

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
            DB db = (DB)context.getAttribute("DB_Esami");
            if(db == null) {
                db = DBMaker.fileDB(nameDB).make();
                context.setAttribute("DB_Esami", db);
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
    public ArrayList<Esame> getEsami() throws Exception {
        createOrOpenDB();
        ArrayList<Esame> esames = new ArrayList<>();

        int k = 0;
        for ( String i: map.getKeys()) {
            esames.add(map.get(i));
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

    @Override
    public ArrayList<Esame> getEsamiFromCorsi(ArrayList<Corso> corsi) throws Exception {
        createOrOpenDB();
        ArrayList<Esame> esami = new ArrayList<>();
        for (int c = 0; c < corsi.size(); c++){
            for (String i: map.getKeys()){
                if(map.get(i).getNomeCorso().equals(corsi.get(c).getNomeCorso())){
                    esami.add(map.get(i));
                }
            }
        }
        return esami;
    }

    @Override
    public ArrayList<Esame> getEsamiFromNomeCorsi(ArrayList<String> corsi) throws Exception {
        createOrOpenDB();
        ArrayList<Esame> esami = new ArrayList<>();
        for (int c = 0; c < corsi.size(); c++){
            for (String i: map.getKeys()){
                if(map.get(i).getNomeCorso().equals(corsi.get(c))){
                    esami.add(map.get(i));
                }
            }
        }
        return esami;
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
