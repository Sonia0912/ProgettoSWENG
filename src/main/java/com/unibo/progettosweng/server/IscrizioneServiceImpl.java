package com.unibo.progettosweng.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.unibo.progettosweng.client.IscrizioneService;
import com.unibo.progettosweng.client.model.Iscrizione;
import com.unibo.progettosweng.client.model.Serializer.SerializerIscrizione;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import javax.servlet.ServletContext;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class IscrizioneServiceImpl extends RemoteServiceServlet implements IscrizioneService {

    DB db;
    HTreeMap<String, Iscrizione> map;

    private void createOrOpenDB(){
        this.db = getDb("iscrizioni.db");
        this.map = this.db.hashMap("iscrizioniMap").counterEnable().keySerializer(Serializer.STRING)
                .valueSerializer(new SerializerIscrizione()).createOrOpen();
    }

    @Override
    public String add(String studente, String corso) throws IllegalArgumentException {
        createOrOpenDB();
        studente = escapeHtml(studente);
        corso = escapeHtml(corso);
        Iscrizione iscr = new Iscrizione(studente, corso);
        map.put(String.valueOf(map.size() + 1), iscr);
        db.commit();
        return "successo";
    }

    // quando viene modificato un corso, se si modifica il nome devono essere modificate le relative iscrizioni
    @Override
    public String aggiorna(String vecchioCorso, String nuovoCorso) throws IllegalArgumentException {
        createOrOpenDB();
        vecchioCorso = escapeHtml(vecchioCorso);
        nuovoCorso = escapeHtml(nuovoCorso);
        for (String i: map.getKeys()) {
            if(map.get(i).getCorso().equals(vecchioCorso)){
                Iscrizione nuovaIscr = new Iscrizione(map.get(i).getStudente(), nuovoCorso);
                map.replace(i, nuovaIscr);
            }
        }
        return "successo";
    }

    public ArrayList<Iscrizione> getIscrizioniStudente(String studente) {
        createOrOpenDB();
        ArrayList<Iscrizione> iscrizioni = new ArrayList<>();
        for (String i: map.getKeys()) {
            if(map.get(i).getStudente().equals(studente)) {
                iscrizioni.add(map.get(i));
            }
        }
        return iscrizioni;
    }

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
            DB db = (DB)context.getAttribute("DB_Iscrizione");
            if(db == null) {
                db = DBMaker.fileDB(nameDB).make();
                context.setAttribute("DB_Iscrizione", db);
            }
            return db;
        }
    }

}
