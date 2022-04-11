
package com.unibo.progettosweng.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.unibo.progettosweng.client.model.Utente;
import org.checkerframework.checker.units.qual.A;

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

    //test inserimento con DB vuoto
    public void testAdd1() throws Exception {

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

    //test inserimento utente già esistente
    public void testAdd2(){
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

    //test inserimento con DB non vuoto
    public void testAdd3(){
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

    //test rimozione utente non esistente
    public void testRemove1() throws Exception {
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

    //test rimozione utente esistente
    public void testRemove2() throws Exception {
        createService();
        delayTestFinish(10000);

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

    //test getUtenti
    public void testGetUtenti() throws Exception {
        createService();
        delayTestFinish(10000);
        service.getUtenti(new AsyncCallback<Utente[]>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get utenti: " + caught.getMessage());
                finishTest();
            }

            @Override
            public void onSuccess(Utente[] utenti) {
                assertEquals("get utenti", 1, utenti.length);
                finishTest();
            }
        });
    }

    //test login successfull
    public void testLogin1(){
        createService();
        delayTestFinish(10000);

        String[] utente = utentiTest.get(1);
        service.login(utente[2], utente[3], new AsyncCallback<Utente>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure login: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Utente utente) {
                assertNotNull("Login corretto", utente);
                finishTest();
            }
        });
    }

    //test login username errato
    public void testLogin2(){
        createService();
        delayTestFinish(10000);

        String[] utente = utentiTest.get(1);
        service.login("usernameErrato", utente[3], new AsyncCallback<Utente>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure login: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Utente utente) {
                assertNull("Login username errato", utente);
                finishTest();
            }
        });
    }

    //test login password errata
    public void testLogin3(){
        createService();
        delayTestFinish(10000);

        String[] utente = utentiTest.get(1);
        service.login(utente[2], "0000", new AsyncCallback<Utente>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure login: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Utente utente) {
                assertNull("Login username errato", utente);
                finishTest();
            }
        });
    }

    //test modifica utente
    /*public void testAggiorna() throws Exception {
        //modifico nome e cognome
        utentiTest.get(1)[0] = "Mario";
        utentiTest.get(1)[1] = "Rossi";
        Utente utente = new Utente(utentiTest.get(1)[0], utentiTest.get(1)[1], utentiTest.get(1)[2], utentiTest.get(1)[3], utentiTest.get(1)[4]);
        service.aggiorna(utente, utente.getUsername(), new AsyncCallback<Utente>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure aggiornamento: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Utente result) {
                assertEquals(utentiTest.get(1)[0], result.getNome());
                assertEquals(utentiTest.get(1)[1], result.getCognome());
                assertEquals(utentiTest.get(1)[2], result.getUsername());
                assertEquals(utentiTest.get(1)[3], result.getPassword());
                assertEquals(utentiTest.get(1)[4], result.getTipo());
                finishTest();
            }
        });
    }*/

    public void testGetUtenteByUsername() throws Exception {
        createService();
        delayTestFinish(10000);

        String[] utente = utentiTest.get(1);
        service.getUtenteByUsername(utente[2], new AsyncCallback<Utente>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get utente by username: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Utente result) {
                assertEquals(utente[0], result.getNome());
                assertEquals(utente[1], result.getCognome());
                assertEquals(utente[2], result.getUsername());
                assertEquals(utente[3], result.getPassword());
                assertEquals(utente[4], result.getTipo());
                finishTest();
            }
        });
    }





    @Override
    public String getModuleName() {
        return "com.unibo.progettosweng.ProgettoSWENGJUnit";
    }
}

