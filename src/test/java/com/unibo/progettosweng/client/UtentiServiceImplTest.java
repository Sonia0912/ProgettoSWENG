
package com.unibo.progettosweng.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.unibo.progettosweng.client.model.Utente;

import java.util.ArrayList;
import java.util.Arrays;


public class UtentiServiceImplTest extends GWTTestCase {

    //prima di far partire il test eliminare il db utentiTest
    private static ArrayList<String[]> utentiTest = new ArrayList<>(Arrays.asList(
            new String[]{"studente", "studente", "studente", "123", "studente"},
            new String[]{"docente", "docente", "docente", "123", "docente"},
            new String[]{"admin", "admin", "admin", "123", "admin"},
            new String[]{"segreteria", "segreteria", "segreteria", "123", "segreteria"}
    ));

    UtenteServiceAsync service;

    private void createService(){
        // Create the service that we will test.
        service = GWT.create(UtenteService.class);
        ServiceDefTarget target = (ServiceDefTarget) service;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "progettosweng/utenti");
    }

    public void testAddConDBVuoto() throws Exception {

        createService();

        // Since RPC calls are asynchronous, we will need to wait for a response
        // after this test method returns. This line tells the test runner to wait
        // up to 10 seconds before timing out.
        delayTestFinish(10000);

        //inserimento con db vuoto
        service.add(utentiTest.get(0), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure add utente: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                try {
                    service.getSize(new AsyncCallback<Integer>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            fail("Failure get new size: " + caught.getMessage());
                        }

                        @Override
                        public void onSuccess(Integer size) {
                            assertEquals("Inserimento utente con db vuoto", new Integer(1), size);
                            finishTest();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void testAddEsistente(){
        createService();
        delayTestFinish(10000);

        //controllo l'inserimento con utente già inserito
        service.add(utentiTest.get(0), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                try {
                    service.getSize(new AsyncCallback<Integer>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            fail("Failure get new size: " + caught.getMessage());
                        }

                        @Override
                        public void onSuccess(Integer size) {
                            assertEquals("Inserimento utente già inserito", new Integer(1), size);
                            finishTest();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void testAddConDBNonVuoto(){
        createService();
        delayTestFinish(10000);

        //controllo l'inserimento con db non vuoto
        service.add(utentiTest.get(1), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                try {
                    service.getSize(new AsyncCallback<Integer>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            fail("Failure get new size: " + caught.getMessage());
                        }

                        @Override
                        public void onSuccess(Integer size) {
                            assertEquals("Inserimento con db non vuoto", new Integer(2), size);
                            finishTest();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void testRemoveNonEsistente() throws Exception {
        createService();
        delayTestFinish(10000);

        //rimozione utente non esistente
        service.getSize(new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Integer oldSize) {
                String[] utente = utentiTest.get(2);
                service.remove(new Utente(utente[0], utente[1], utente[2], utente[3], utente[4]), new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        fail("Failure: " + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(Void result) {
                        try {
                            service.getSize(new AsyncCallback<Integer>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    fail("Failure: " + caught.getMessage());
                                }

                                @Override
                                public void onSuccess(Integer size) {
                                    assertEquals("Rimozione utente non esistente", oldSize, size);
                                    finishTest();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void testRemoveEsistente() throws Exception {
        createService();
        delayTestFinish(10000);

        //rimozione utente esistente
        service.getSize(new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Integer oldSize) {
                String[] utente = utentiTest.get(0);
                service.remove(new Utente(utente[0], utente[1], utente[2], utente[3], utente[4]), new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        fail("Failure: " + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(Void result) {
                        try {
                            service.getSize(new AsyncCallback<Integer>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    fail("Failure: " + caught.getMessage());
                                }

                                @Override
                                public void onSuccess(Integer size) {
                                    assertEquals("Rimozione utente esistente", new Integer(oldSize - 1), size);
                                    finishTest();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }




    @Override
    public String getModuleName() {
        return "com.unibo.progettosweng.ProgettoSWENGJUnit";
    }
}

