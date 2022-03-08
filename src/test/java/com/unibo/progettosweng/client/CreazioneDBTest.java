package com.unibo.progettosweng.client;

import com.unibo.progettosweng.CreazioneDB;

import com.unibo.progettosweng.model.Esame;
import com.unibo.progettosweng.model.Utente;
import com.unibo.progettosweng.server.SerializerUtente;
import org.junit.Test;
import org.mapdb.DB;


public class CreazioneDBTest {

    @Test
    public void test(){
        CreazioneDB<Utente> db = new CreazioneDB();
        DB result = db.getDb(CreazioneDB.DB_UTENTI);
        assert (result.isClosed() != true);

    }

    @Test
    public void inserimentoUtente(){

        Utente urs1 = new Utente("Gianluca", "Gueli","gianluca.gueli@studio.unibo.it","123","studente");
        boolean a = urs1.aggiuntiUtenteAlDB();
        assert (a == true);
    }


    @Test
    public void getNomiUtentiTest(){
        Utente urs1 = new Utente("Erika", "Colonna","erika.colonna@studio.unibo","123","studente");
        urs1.aggiuntiUtenteAlDB();
        String[] a = urs1.getNomiUtenti();
        for (String nome : a) {
            System.out.println("nome -> " + nome);
        }
    }

    @Test
    public void controlloUtenteDuplicato(){

        Utente urs1 = new Utente("Gianluca", "Gueli","gianluca.gueli@studio.unibo.it","123","studente");
        urs1.aggiuntiUtenteAlDB();
        //Non posso inserirlo due volte
        boolean a = urs1.aggiuntiUtenteAlDB();
        assert (a == false);
    }

    @Test
    public void inserimentoEsame(){
        Esame urs1 = new Esame("12-01-21", "12:00","media","M1","Algoritmi");
        boolean a = urs1.inserimentoEsame();
        assert (a == true);
    }





}
