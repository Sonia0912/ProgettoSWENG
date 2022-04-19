
package com.unibo.progettosweng.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.unibo.progettosweng.client.model.Utente;
import com.unibo.progettosweng.client.model.Valutazione;

import java.util.ArrayList;
import java.util.Arrays;

public class ValutazioniServiceImplTest extends GWTTestCase {

    private static ArrayList<String[]> valutazioniTest = new ArrayList<>(Arrays.asList(
            new String[]{"Sistemi Operativi", "studente", "28", "0"},
            new String[]{"Algebra Lineare", "studente", "25", "1"},
            new String[]{"Metodi Numerici", "studente", "23", "2"},
            new String[]{"Tecnologie Web", "studente", "30", "0"},
            new String[]{"Basi di Dati", "studente", "29", "1"}
    ));

    ValutazioneServiceAsync service;

    public void gwtSetUp(){
        // Create the service that we will test.
        service = GWT.create(ValutazioneService.class);
        ServiceDefTarget target = (ServiceDefTarget) service;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "progettosweng/valutazioni");
    }

    //test inserimento
    public synchronized void testAddMore() throws Exception {
        delayTestFinish(10000);

        service.addMore(valutazioniTest, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure add more valutazioni: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        //inserimento valutazione già esistente
        service.add(valutazioniTest.get(0), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure add valutazione esistente: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        //prendo la size del db e la confronto con quella attesa
        service.getSize(new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get size: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Integer size) {
                assertEquals("Inserimento valutazione", new Integer(5), size);
                finishTest();
            }
        });
    }

    public synchronized void testRemove() throws Exception {
        delayTestFinish(10000);
        //valutazione esistente
        service.remove("Sistemi Operativi", "studente", new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure remove valutazione: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        //valutazione non esistente
        service.remove("Lettere", "studente", new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure remove valutazione non esistente: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        //prendo la size del db e la confronto con quella attesa
        service.getSize(new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get size: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Integer size) {
                assertEquals("Inserimento valutazione", new Integer(4), size);
                finishTest();
            }
        });
    }

    //test getValutazioni
    public synchronized void testGetValutazioni() throws Exception {
        delayTestFinish(10000);

        service.getValutazioni(new AsyncCallback<Valutazione[]>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get valutazioni: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Valutazione[] valutazioni) {
                try {
                    service.getSize(new AsyncCallback<Integer>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            fail("Failure get size: " + caught.getMessage());
                        }

                        @Override
                        public void onSuccess(Integer result) {
                            assertEquals("get utenti", result, new Integer(valutazioni.length));
                            finishTest();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //stato: 0
    public synchronized void testGetValutazioniDaInserire() throws Exception {
        delayTestFinish(10000);
        service.getValutazioniDaInserire(new AsyncCallback<ArrayList<Valutazione>>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get valutazioni: " + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Valutazione> valutazioni) {
                assertEquals("get valutazioni da inserire", 2, valutazioni.size());
                finishTest();
            }
        });
    }

    //stato: 1
    public synchronized void testGetValutazioniDaPubblicare() throws Exception {
        delayTestFinish(10000);
        service.getValutazioniDaPubblicare(new AsyncCallback<ArrayList<Valutazione>>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get valutazioni: " + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Valutazione> valutazioni) {
                assertEquals("get valutazioni da pubblicare", 2, valutazioni.size());
                finishTest();
            }
        });
    }

    public synchronized void testCambiaStatoValutazione() throws Exception {
        delayTestFinish(10000);
        int index = 2;
        int stato = Integer.parseInt(valutazioniTest.get(index)[3]);
        valutazioniTest.get(index)[3] = String.valueOf(stato++);
        service.cambiaStatoValutazione(valutazioniTest.get(index)[1], valutazioniTest.get(index)[0], Integer.parseInt(valutazioniTest.get(index)[3]), new AsyncCallback<Valutazione>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure cambio stato: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Valutazione result) {
                assertEquals("Cambio stato", Integer.parseInt(valutazioniTest.get(index)[3]), result.getStato());
                finishTest();
            }
        });
    }

    @Override
    public String getModuleName() {
        return "com.unibo.progettosweng.ProgettoSWENGJUnit";
    }
}


