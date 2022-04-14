
package com.unibo.progettosweng.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Esame;
import com.unibo.progettosweng.client.model.Utente;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;

public class EsamiServiceImplTest extends GWTTestCase {

    //prima di far partire il test eliminare il db utenti
    private static ArrayList<String[]> esamiTest = new ArrayList<>(Arrays.asList(
            new String[]{"08/06/2022", "09:00", "media", "Tonelli", "Tecnologie Web"},
            new String[]{"05/07/2022", "10:30", "difficile", "Cremona", "Sistemi Operativi"},
            new String[]{"03/06/2022", "09:30", "media", "Ercolani 3", "Basi di Dati"}
    ));

    ArrayList<Corso> corsiTest = new ArrayList<>(Arrays.asList(
            new Corso("Tecnologie Web", "23/02/2022", "11/05/2022", "Corso sullo sviluppo web", "Informatica", "docente", "codocente", true),
            new Corso("Sistemi Operativi", "23/02/2022", "11/05/2022", "Corso sull'informatica'", "Informatica", "docente", "codocente", true),
            new Corso("Basi di Dati", "23/02/2022", "11/05/2022", "Corso sullo sviluppo di basi di dati", "Informatica", "docente", "codocente", false)
    ));



    EsameServiceAsync service;

    public void gwtSetUp(){
        // Create the service that we will test.
        service = GWT.create(EsameService.class);
        ServiceDefTarget target = (ServiceDefTarget) service;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "progettosweng/esami");
    }

    public synchronized void testAdd() throws Exception {
        delayTestFinish(10000);

        //inserimento primo esame
        service.add(esamiTest.get(0), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure add esame: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });
        //inserimento secondo esame
        service.add(esamiTest.get(1), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure add esame: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        //inserimento esame già inserito
        service.add(esamiTest.get(1), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure add esame: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        service.getSize(new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get size: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Integer size) {
                assertEquals("Inserimento esame", new Integer(3), size);
                finishTest();
            }
        });



    }

    public synchronized void testRemove() throws Exception {
        delayTestFinish(10000);

        //remove esame non esistente
        service.remove(esamiTest.get(2)[4], new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure remove esame: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        //remove esame esistente
        service.remove(esamiTest.get(1)[4], new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure remove esame: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        service.getSize(new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get size: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Integer size) {
                assertEquals("Rimozione esame", new Integer(1), size);
                finishTest();
            }
        });
    }

    public synchronized void testGetEsami() throws Exception {
        delayTestFinish(10000);

        service.getEsami(new AsyncCallback<ArrayList<Esame>>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get esami: " + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Esame> esami) {
                try {
                    service.getSize(new AsyncCallback<Integer>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            fail("Failure get size: " + caught.getMessage());
                        }

                        @Override
                        public void onSuccess(Integer size) {
                            assertEquals("get esami", size, new Integer(esami.size()));
                            finishTest();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public synchronized void testAggiorna() throws Exception {
        //modifico ora e aula
        esamiTest.get(0)[1] = "14:00";
        esamiTest.get(0)[3] = "Ercolani 2";
        Esame esame = new Esame(esamiTest.get(0)[0], esamiTest.get(0)[1], esamiTest.get(0)[2], esamiTest.get(0)[3], esamiTest.get(0)[4]);

        service.aggiorna(esame, new AsyncCallback<Esame>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure aggiorna esame: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Esame result) {
                assertNotNull(result);
                assertEquals(esamiTest.get(1)[0], result.getData());
                assertEquals(esamiTest.get(1)[1], result.getOra());
                assertEquals(esamiTest.get(1)[2], result.getDifficolta());
                assertEquals(esamiTest.get(1)[3], result.getAula());
                assertEquals(esamiTest.get(1)[4], result.getNomeCorso());
                finishTest();
            }
        });
    }

    public synchronized void testGetEsamiFromCorsi() throws Exception {
        delayTestFinish(10000);

        service.add(esamiTest.get(2), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure add esame: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        service.getEsamiFromCorsi(corsiTest, new AsyncCallback<ArrayList<Esame>>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get esami from lista corsi: " + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Esame> result) {
                assertEquals("get esami from lista corsi", 1, result.size());
                finishTest();
            }
        });
    }

    public synchronized void testGetEsamiFromNomeCorsi() throws Exception {
        ArrayList<String> nomiCorsiTest = new ArrayList<>(Arrays.asList(
                corsiTest.get(0).getNomeCorso(),
                corsiTest.get(1).getNomeCorso(),
                corsiTest.get(2).getNomeCorso()
        ));

        service.getEsamiFromNomeCorsi(nomiCorsiTest, new AsyncCallback<ArrayList<Esame>>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get esami from lista nome corsi: " + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Esame> result) {
                assertEquals("get esami from lista nome corsi", 1, result.size());
                finishTest();
            }
        });
    }

    @Override
    public String getModuleName() {
        return "com.unibo.progettosweng.ProgettoSWENGJUnit";
    }
}

