package com.unibo.progettosweng.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.unibo.progettosweng.client.ValutazioneService;
import com.unibo.progettosweng.client.model.Serializer.SerializerValutazione;
import com.unibo.progettosweng.client.model.Valutazione;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;

public class ValutazioneServiceImpl extends RemoteServiceServlet implements ValutazioneService {

    DB db;
    HTreeMap<String,Valutazione> map;

    private void createOrOpenDB(){
        this.db = getDb("valutazioni.db");
        this.map = this.db.hashMap("valutazioniMap").counterEnable().keySerializer(Serializer.STRING).valueSerializer(new SerializerValutazione()).createOrOpen();
    }

    private DB getDb(String nameDB) {
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB_Valutazioni");
            if(db == null) {
                db = DBMaker.fileDB(nameDB).make();
                context.setAttribute("DB_Valutazioni", db);
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
        Valutazione valutazione = new Valutazione(input[0], input[1], Integer.parseInt(input[2]), Boolean.parseBoolean(input[3]));
        if(controlloValutazioneDuplicato( map,valutazione)){
            return "Valutazione già inserito!";
        }else {
            map.put(String.valueOf(map.size() + 1), valutazione);
            db.commit();
            return "(size: " + map.size()+ ") La valutazione di " + valutazione.getStudente()+ " del corso " + valutazione.getNomeCorso()+ " è stata creata e aggiunta al database.";
        }
    }

    @Override
    public String addMore(ArrayList<String[]> listaValutazioni) throws IllegalArgumentException {
        createOrOpenDB();
        for (String[] val: listaValutazioni) {
            Valutazione valutazione = new Valutazione(val[0],val[1],Integer.parseInt(val[2]),Boolean.parseBoolean(val[3]));
            if(controlloValutazioneDuplicato( map,valutazione)){
                return "Valutazione già inserito!";
            }else {
                map.put(String.valueOf(map.size() + 1), valutazione);
                db.commit();
                 }
        }
        return "(size: " + map.size()+ ") La valutazione  sono stata creata e aggiunta al database.";

    };

    private boolean controlloValutazioneDuplicato(HTreeMap<String, Valutazione> map, Valutazione val) {

        for (String i : map.getKeys()) {
            if(map.get(i).getNomeCorso().equals(val.getNomeCorso()) && map.get(i).getStudente().equals(val.getStudente())){
                return true;
            }
        }
        return false;

    }

    @Override
    public String remove(String nomeCorso, String studente) throws IllegalArgumentException {
        createOrOpenDB();
        for ( String i: map.getKeys()) {
            if(map.get(i).getNomeCorso().equals(nomeCorso) && map.get(i).getStudente().equals(studente)){
                map.remove(i);
                db.commit();
                return "La taglia: " + map.size() + " La vautazione di " + studente + " del corso di" + nomeCorso + " è  stato rimosso.";
            }
        }
        return "Nessun voto presente";
    }

    @Override
    public Valutazione[] getValutazioni() throws Exception {
        createOrOpenDB();
        Valutazione[] valutazioni = new Valutazione[map.size()];
        int j = 0;
        for (String i: map.getKeys()) {
            valutazioni[j] = map.get(i);
            j++;
        }
        return valutazioni;
    }

    @Override
    public Valutazione aggiorna(Valutazione val, String nomeCorso, String studente) throws Exception {
        createOrOpenDB();
        for (String i: map.getKeys()) {
            if(map.get(i).getStudente().equals(studente) && map.get(i).getNomeCorso().equals(nomeCorso) ){
                map.replace(i,val);
            }
        }
        return  val;
    }

    public ArrayList<Valutazione> getValutazioniStudente(String studente) {
        createOrOpenDB();
        ArrayList<Valutazione> val = new ArrayList<>();
        for (String i: map.getKeys()) {
            if(map.get(i).getStudente().equals(studente) && map.get(i).getPubblicato()) {
                val.add(map.get(i));
            }
        }
        return val;
    }


    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }

}
