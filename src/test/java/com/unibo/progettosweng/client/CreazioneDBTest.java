package com.unibo.progettosweng.client;

import com.unibo.progettosweng.CreazioneDB;

import org.junit.Test;


public class CreazioneDBTest {
    @Test
    public void test(){
        CreazioneDB db = new CreazioneDB();
        int result = db.createMap(CreazioneDB.DB_UTENTI, CreazioneDB.UTENTI_MAP);

        assert (result == 1);

    }

    @Test
    public void inserimentoUtente(){

        CreazioneDB db = new CreazioneDB();
        db.createMap(CreazioneDB.DB_UTENTI, CreazioneDB.UTENTI_MAP);
        String [] utente = {"Gianluca", "Gueli","gianluca.gueli@studio.unibo.it"};
        int result = db.insert(CreazioneDB.DB_UTENTI,CreazioneDB.UTENTI_MAP,utente);

        assert (result == 1);
    }

   @Test
   public void getNameUtente(){

       CreazioneDB db = new CreazioneDB();

       String [] utente = {"Gianluca", "Gueli","gianluca.gueli@studio.unibo.it"};
       db.insert(CreazioneDB.DB_UTENTI,CreazioneDB.UTENTI_MAP,utente);

       String  nome = "Gianluca";

        String username = db.getUsernameUtente(CreazioneDB.DB_UTENTI,CreazioneDB.UTENTI_MAP,nome);

       assert (username.equals(nome));

   }


}
