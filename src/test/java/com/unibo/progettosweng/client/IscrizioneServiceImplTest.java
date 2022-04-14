package com.unibo.progettosweng.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.unibo.progettosweng.client.IscrizioneService;
import com.unibo.progettosweng.client.IscrizioneServiceAsync;
import com.unibo.progettosweng.client.model.Iscrizione;
import junit.framework.TestCase;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class IscrizioneServiceImplTest extends GWTTestCase {

    IscrizioneServiceAsync service;

    private void config(){
        service = GWT.create(IscrizioneService.class);
        ServiceDefTarget target = (ServiceDefTarget) service;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "progettosweng/iscrizioni");
        delayTestFinish(10000);
    }

    /*
        Test per l'aggiunta di un'iscrizione al database.
        Il test consiste nell'aggiungere un'iscrizione per uno studente e controllare in seguito
        che il numero di iscrizioni risulti essere 1.
    */
    public synchronized void testAdd() {
        config();
        service.add("studenteTest", "corsoTest", new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure add: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(String s) {
                service.getIscrizioniStudente("studenteTest", new AsyncCallback<ArrayList<Iscrizione>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        fail("Failure getIscrizioniStudente: " + throwable.getMessage());
                    }
                    @Override
                    public void onSuccess(ArrayList<Iscrizione> iscrizioni) {
                        assertEquals(iscrizioni.size(), 1);
                    }
                });
            }
        });
        finishTest();
    }

    public synchronized void testAggiorna() {
        config();
        assertTrue(true);
        finishTest();
    }

    /*
        Test per prendere i corsi a cui e' iscritto uno studente.
        Il test consiste nell'aggiungere due iscrizioni per lo stesso utente
        e controllare che siano effettivamente nel database.
    */
    public synchronized void testGetIscrizioniStudente() {
        config();
        service.add("studente1Test", "corso1Test", new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure add (1): " + throwable.getMessage());
            }
            @Override
            public void onSuccess(String s) {
                service.add("studente1Test", "corso2Test", new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        fail("Failure add (2): " + throwable.getMessage());
                    }
                    @Override
                    public void onSuccess(String s) {
                        service.getIscrizioniStudente("studente1Test", new AsyncCallback<ArrayList<Iscrizione>>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                fail("Failure getIscrizioniStudente: " + throwable.getMessage());
                            }
                            @Override
                            public void onSuccess(ArrayList<Iscrizione> iscrizioni) {
                                assertEquals("corso1Test", iscrizioni.get(0).getCorso());
                                assertEquals("corso2Test", iscrizioni.get(1).getCorso());
                            }
                        });
                    }
                });
            }
        });
        finishTest();
    }

    @Override
    public String getModuleName() {
        return "com.unibo.progettosweng.ProgettoSWENGJUnit";
    }
}