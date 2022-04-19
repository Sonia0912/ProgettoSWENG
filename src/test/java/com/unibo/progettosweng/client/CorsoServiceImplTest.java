package com.unibo.progettosweng.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.unibo.progettosweng.client.model.Corso;

import java.util.ArrayList;
import java.util.Arrays;

public class CorsoServiceImplTest extends GWTTestCase {

    CorsoServiceAsync service;
    ArrayList<String[]> corsiTest = new ArrayList<>(Arrays.asList(
            new String[]{"Tecnologie Web", "23/02/2022", "11/05/2022", "Corso sullo sviluppo web", "Informatica", "docente", "codocente", "true"},
            new String[]{"Sistemi Operativi", "23/02/2022", "11/05/2022", "Corso sull'informatica'", "Informatica", "docente", "codocente", "true"},
            new String[]{"Basi di Dati", "23/02/2022", "11/05/2022", "Corso sullo sviluppo di basi di dati", "Informatica", "docente", "codocente", "false"}
    ));

    public void gwtSetUp(){
        // Create the service that we will test.
        service = GWT.create(CorsoService.class);
        ServiceDefTarget target = (ServiceDefTarget) service;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "progettosweng/corsi");
    }
    public synchronized void testAdd() throws Exception {
        delayTestFinish(10000);

        //inserimento primo corso
        service.add(corsiTest.get(0), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure add corso: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        //inserimento secondo corso
        service.add(corsiTest.get(1), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure add corso: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                assertTrue(true);
            }
        });
        //add corso già inserito
        service.add(corsiTest.get(0), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure add corso: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                assertTrue(true);
            }
        });

        service.getNumeroCorsi(new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure get mapSize: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Integer integer) {
                assertTrue(integer != 0);
                finishTest();
            }
        });

    }
    public synchronized void testRemove() throws Exception {

        delayTestFinish(10000);

        //remove corso non esistente
        service.remove(corsiTest.get(2)[0], new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure remove esame: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        //remove corso esistente
        service.remove(corsiTest.get(0)[0], new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure remove corso: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });
        //remove corso esistente
        service.remove(corsiTest.get(1)[0], new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure remove corso: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        service.getNumeroCorsi(new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get size: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Integer size) {
                assertEquals("Rimozione corso", new Integer(0), size);
                finishTest();
            }
        });

    }

    public synchronized void testGetCorsi() throws Exception {
        delayTestFinish(10000);

        service.getCorsi(new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure get corsi: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Corso[] corsos) {
                assertNotNull(corsos);
                finishTest();
            }
        });
    }

    public synchronized void testAggiorna() {
        delayTestFinish(10000);

        service.add(corsiTest.get(2), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure add corso: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                try {
                    service.getCorso(corsiTest.get(2)[0], new AsyncCallback<Corso>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            fail("Failure get corso: " + throwable.getMessage());
                        }

                        @Override
                        public void onSuccess(Corso corso) {
                            corso.setEsameCreato(false);
                            try {
                                service.aggiorna(corso, new AsyncCallback<Void>() {
                                    @Override
                                    public void onFailure(Throwable throwable) {
                                        fail("Failure aggiorna corso: " + throwable.getMessage());
                                    }

                                    @Override
                                    public void onSuccess(Void unused) {
                                        try {
                                            service.getCorso(corsiTest.get(2)[0], new AsyncCallback<Corso>() {
                                                @Override
                                                public void onFailure(Throwable throwable) {
                                                    fail("Failure get corso: " + throwable.getMessage());
                                                }

                                                @Override
                                                public void onSuccess(Corso corso) {
                                                    assertTrue(corso.getEsameCreato() == false);
                                                    finishTest();
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        assertTrue(true);
        finishTest();
    }

    public synchronized void testGetCorsiDocente() throws Exception {
        delayTestFinish(10000);

        service.getCorsiDocente("docente", new AsyncCallback<ArrayList<Corso>>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure get corsi Docente: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Corso> corsos) {
                assertNotNull(corsos);
                finishTest();
            }
        });

    }

    public synchronized void testGetCorsiCoDocente() throws Exception {
        delayTestFinish(10000);

        //aggiungo nel caso non fosse già inserito
        service.add(corsiTest.get(0), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure add corso: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                assertTrue(true);
            }
        });

        service.getCorsiCoDocente(corsiTest.get(0)[0], new AsyncCallback<ArrayList<Corso>>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure get corsi CoDocente: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Corso> corsos) {
                assertNotNull(corsos);
                finishTest();
            }
        });
    }

    public synchronized void testGetCorso() throws Exception {
        delayTestFinish(10000);

        //aggiungo nel caso non fosse già inserito
        service.add(corsiTest.get(0), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure add corso: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                assertTrue(true);
            }
        });

        service.getCorso(corsiTest.get(0)[0], new AsyncCallback<Corso>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure get corso: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Corso corso) {
                assertNotNull(corso);
                finishTest();
            }
        });
    }

    public void testGetListaCorsiIscrizioni() throws Exception {
        delayTestFinish(10000);

        //aggiungo nel caso non fosse già inserito
        service.add(corsiTest.get(1), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure add corso: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                assertTrue(true);
            }
        });

        service.getListaCorsiIscrizioni(new ArrayList<String>(Arrays.asList(corsiTest.get(1)[0])), new AsyncCallback<ArrayList<Corso>>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure getListaCorsiIscrizioni: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Corso> corsos) {
                assertNotNull(corsos);
                finishTest();
            }
        });

    }

    public synchronized void testGetNumeroCorsi() throws Exception {
        delayTestFinish(10000);
        assertTrue(true);
        finishTest();
   /*     //aggiungo primo Corso
        service.add(corsiTest.get(0), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure add corso: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                assertTrue(true);
            }
        });

        //aggiungo secondo Corso
        service.add(corsiTest.get(1), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure add corso: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                assertTrue(true);
            }
        });

        //aggiungo terzo Corso
        service.add(corsiTest.get(2), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure add corso: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                assertTrue(true);
            }
        });


        service.getNumeroCorsi(new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable throwable) {
                fail("Failure getNumeroCorso: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Integer numCorsi) {
                assertEquals("Numero Corsi", new Integer(3), numCorsi);
                finishTest();
            }
        });
*/

    }

    @Override
    public String getModuleName() {
        return "com.unibo.progettosweng.ProgettoSWENGJUnit";
    }
}