package com.unibo.progettosweng.server;

import com.unibo.progettosweng.client.CorsoService;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Serializer.SerializerCorso;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CorsoServiceImpl extends RemoteServiceServlet implements CorsoService {

    DB db;
    HTreeMap<String,Corso> map;

    private void createOrOpenDB(){
        this.db = getDb("corsi.db");
        this.map = this.db.hashMap("corsiMap").counterEnable().keySerializer(Serializer.STRING)
                .valueSerializer(new SerializerCorso()).createOrOpen();
    }

    @Override
    public String add(String[] input) throws IllegalArgumentException {
        createOrOpenDB();
        for(int i = 0; i < input.length; i++) {
            input[i] = escapeHtml(input[i]);
        }

        //if(input.length == 6){
            Corso corso = new Corso(input[0], input[1], input[2], input[3], input[4], input[5], input[6],Boolean.valueOf(input[7]));
            if(controlloCorsoDuplicato( map,corso)){
                return "Corso già inserito!";
            }else {
                map.put(String.valueOf(map.size() + 1), corso);
                db.commit();
                return "(size: " + map.size()+ ") Il corso " + corso.getNomeCorso() + ", inzio: " + corso.getDataInizio() + " è stato creato e aggiunto al database.";
            }
//        }else {
//            return "Errore lunghezza input array";
//        }

    }

    @Override
    public String remove(String nomeCorso) throws IllegalArgumentException {
        createOrOpenDB();
        for ( String i: map.getKeys()) {
            if(map.get(i).getNomeCorso().equals(nomeCorso)){
                map.remove(i);
                db.commit();
                return "La taglia: " + map.size() + " Il corso " + nomeCorso + " è  stato rimosso.";
            }
        }
        return "Nessun corso Presente!";
    }

    @Override
    public Corso[] getCorsi() throws Exception {
        createOrOpenDB();
        Corso[] corsi = new Corso[map.size()];
        int k = 0;
        for (String i: map.getKeys()) {
            corsi[k] = map.get(i);
            k++;
        }
        return corsi;
    }


    //metodo che viene invocato quando vengono modificato le informazioni degli utenti
    @Override
    public void aggiorna(Corso corso){
        createOrOpenDB();
        for ( String i: map.getKeys()) {
            if(map.get(i).getNomeCorso().equals(corso.getNomeCorso())){
                map.replace(i,corso);
            }
        }

    }

    @Override
    public ArrayList<Corso> getCorsiDocente(String usernameDocente) throws Exception {
      createOrOpenDB();
      ArrayList<Corso> corsiDocente = new ArrayList<>();
      for (String i: map.getKeys()){
          if(map.get(i).getDocente().equals(usernameDocente)){
              corsiDocente.add(map.get(i));
          }
      }
      return corsiDocente;
    }

    @Override
    public ArrayList<Corso> getCorsiCoDocente(String usernameCoDocente) throws Exception {
        createOrOpenDB();
        ArrayList<Corso> corsiCoDocente = new ArrayList<>();
        for (String i: map.getKeys()){
            if(map.get(i).getCodocente().equals(usernameCoDocente)){
                corsiCoDocente.add(map.get(i));
            }
        }
        return corsiCoDocente;
    }


    private boolean controlloCorsoDuplicato(HTreeMap<String, Corso> map, Corso corso){
        for ( String i: map.getKeys()) {
            if (map.get(i).getNomeCorso().equals(corso.getNomeCorso()) ){
                return true;
            }
        }
        return false;
    }


    // metodo per assicurarsi che non le stringhe vengano lette come tali e non come codice html
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }


    private DB getDb(String nameDB){
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB_Corsi");
            if(db == null) {
                db = DBMaker.fileDB(nameDB).make();
                context.setAttribute("DB_Corsi", db);
            }
            return db;
        }
    }

}