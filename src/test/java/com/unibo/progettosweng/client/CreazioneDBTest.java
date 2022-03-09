package com.unibo.progettosweng.client;

import com.unibo.progettosweng.CreazioneDB;

import com.unibo.progettosweng.model.Corso;
import com.unibo.progettosweng.model.Esame;
import com.unibo.progettosweng.model.Utente;
import com.unibo.progettosweng.model.Valutazione;
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
        boolean a = urs1.aggiuntiUtente();
        assert (a == true);
    }


    @Test
    public void getNomiUtentiTest(){
        Utente urs1 = new Utente("Erika", "Colonna","erika.colonna@studio.unibo","123","studente");
        urs1.aggiuntiUtente();
        Utente[] a =  Utente.getUtenti();
        for (Utente utente : a) {
            System.out.println("nome -> " + utente.getNome());
        }
    }

    @Test
    public void controlloUtenteDuplicato(){

        Utente urs1 = new Utente("Gianluca", "Gueli","gianluca.gueli@studio.unibo.it","123","studente");
        urs1.aggiuntiUtente();
        //Non posso inserirlo due volte
        boolean a = urs1.aggiuntiUtente();
        assert (a == false);
    }

    @Test
    public void rimuoviUtente(){

        Utente urs1 = new Utente("Sonia", "Nicoletti","sonia.nicoletti@studio.unibo.it","123","studente");
        urs1.aggiuntiUtente();
        System.out.println("Prima della rimozione: ");
        Utente[] lista =  Utente.getUtenti();
        for (Utente nome : lista) {
            System.out.println("nome -> " + nome.getNome());
        }

        boolean b = urs1.rimuoviUtente();

        System.out.println("Dopo la rimozione: ");
        Utente[]  lista1 =  Utente.getUtenti();
        for (Utente nome : lista1) {
            System.out.println("nome -> " + nome.getNome());
        }

        assert (b == true);
    }
    @Test
    public void aggiornoValore(){

        Utente urs1 = new Utente("Luca", "Gueli","gianluca.gueli@studio.unibo.it","123","studente");
        boolean a = urs1.aggiuntiUtente();

        urs1.setNome("Gian");
        System.out.println("Dopo la modifica: ");
        Utente[]  lista1 =  Utente.getUtenti();
        for (Utente nome : lista1) {
            System.out.println("nome -> " + nome.getNome());
        }

    }

    @Test
    public void inserimentoEsame(){
        Esame urs1 = new Esame("12-01-21", "12:00","media","M1","Algoritmi");
        boolean a = urs1.inserimentoEsame();
        assert (a == true);
    }

    @Test
    public void inserimentoCorso(){
        Corso corso = new Corso("ING-SOFT","12-12-22","01-02-22","Corso di ingneria del software");
        boolean a = corso.aggiungiCorso();
        assert (a== true);
    }

    @Test
    public void inserimentoValutazione(){

        Valutazione valutazione = new Valutazione("ING-SOFT","sonia.nicoletti@studio.unibo.it",22);
        boolean a = valutazione.aggiungiValutazione();
        assert (a== true);
    }







}
