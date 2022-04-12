
package com.unibo.progettosweng.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.unibo.progettosweng.client.model.Utente;
import org.checkerframework.checker.units.qual.A;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Arrays;

public class UtentiServiceImplTest extends GWTTestCase {

    //prima di far partire il test eliminare il db utentiTest
    private static ArrayList<String[]> utentiTest = new ArrayList<>(Arrays.asList(
            new String[]{"studente", "studente", "studente", "123", "Studente"},
            new String[]{"docente", "docente", "docente", "123", "Docente"},
            new String[]{"admin", "admin", "admin", "123", "Admin"},
            new String[]{"codocente", "codocente", "codocente", "123", "Docente"},
            new String[]{"segreteria", "segreteria", "segreteria", "123", "Segreteria"}
    ));

    UtenteServiceAsync service;

    public void gwtSetUp(){
        // Create the service that we will test.
        service = GWT.create(UtenteService.class);
        ServiceDefTarget target = (ServiceDefTarget) service;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "progettosweng/utenti");
    }

    //test inserimento
    public synchronized void testAdd() throws Exception {

        // Since RPC calls are asynchronous, we will need to wait for a response
        // after this test method returns. This line tells the test runner to wait
        // up to 10 seconds before timing out.
        delayTestFinish(10000);

        //inserimento primo utente
        service.add(utentiTest.get(1), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure add utente: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        //inserimento secondo utente
        service.add(utentiTest.get(2), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get size: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        //inserimento utente già esistente
        service.add(utentiTest.get(2), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get size: " + caught.getMessage());
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
                assertEquals("Inserimento utente", new Integer(2), size);
                finishTest();
            }
        });
    }

    //test getUtenti
    public synchronized void testGetUtenti() throws Exception {
        delayTestFinish(10000);

        service.getUtenti(new AsyncCallback<Utente[]>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get utenti: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Utente[] utenti) {
                try {
                    service.getSize(new AsyncCallback<Integer>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            fail("Failure get size: " + caught.getMessage());
                        }

                        @Override
                        public void onSuccess(Integer result) {
                            assertEquals("get utenti", result, new Integer(utenti.length));
                            finishTest();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //test login
    public synchronized void testLogin(){
        delayTestFinish(10000);

        //test successfull
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

        //username errato
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

        //password errata
        service.login(utente[2], "0000", new AsyncCallback<Utente>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure login: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Utente utente) {
                assertNull("Login password errata", utente);
                finishTest();
            }
        });

    }

    //test modifica utente
    public synchronized void testAggiorna() throws Exception {
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
    }

    public synchronized void testGetUtenteByUsername() throws Exception {
        delayTestFinish(10000);

        //utente esistente
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

        //utente non esistente
        service.getUtenteByUsername(utentiTest.get(4)[2], new AsyncCallback<Utente>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get utente by username: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Utente result) {
                assertNotNull(result);
                finishTest();
            }
        });


    }

    public synchronized void testGetCodocenti() throws Exception {
        delayTestFinish(10000);

        //assenza di codocenti
        service.getCodocenti(utentiTest.get(1)[2], new AsyncCallback<ArrayList<Utente>>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get codocenti: " + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Utente> result) {
                assertEquals(0, result.size());
                finishTest();
            }
        });

        service.add(utentiTest.get(3), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                System.out.println("Failure add utente:" + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                try {
                    service.getCodocenti(utentiTest.get(1)[2], new AsyncCallback<ArrayList<Utente>>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            fail("Failure get codocenti: " + caught.getMessage());
                        }

                        @Override
                        public void onSuccess(ArrayList<Utente> result) {
                            assertEquals(1, result.size());
                            finishTest();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public synchronized void testGetStudenti() throws Exception {
        delayTestFinish(10000);
        //assenza di studenti
        service.getStudenti(new AsyncCallback<ArrayList<Utente>>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get studenti: " + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Utente> result) {
                assertEquals(0, result.size());
                finishTest();
            }
        });

        //in presenza di almeno uno studente
        service.add(utentiTest.get(0), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure add studente: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                try {
                    service.getStudenti(new AsyncCallback<ArrayList<Utente>>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            fail("Failure get studenti: " + caught.getMessage());
                        }

                        @Override
                        public void onSuccess(ArrayList<Utente> students) {
                            assertEquals(1, students.size());
                            finishTest();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public synchronized void testGetDocenti() throws Exception {
        delayTestFinish(10000);

        service.getDocenti(new AsyncCallback<ArrayList<Utente>>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get docenti: " + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Utente> docenti) {
                try {
                    service.getNumeroDocenti(new AsyncCallback<Integer>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            fail("Failure get num docenti: " + caught.getMessage());
                        }

                        @Override
                        public void onSuccess(Integer result) {
                            assertEquals("Docenti", result, new Integer(docenti.size()));
                            finishTest();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public synchronized void testRemove() throws Exception {
        delayTestFinish(10000);
        //remove utente inesistente
        String[] utente = utentiTest.get(4); //segreteria
        service.remove(new Utente(utente[0], utente[1], utente[2], utente[3], utente[4]), new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure remove: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Void result) {
                assertTrue("Remove utente non esistente", true);
            }
        });

        //remove utente esistente
        utente = utentiTest.get(2); //admin
        service.remove(new Utente(utente[0], utente[1], utente[2], utente[3], utente[4]), new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure remove: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Void result) {
                assertTrue("Remove utente esistente", true);
            }
        });

        service.getSize(new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get size: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Integer size) {
                assertEquals("Rimozione utente", new Integer(3), size);
                finishTest();
            }
        });

    }

    @Override
    public String getModuleName() {
        return "com.unibo.progettosweng.ProgettoSWENGJUnit";
    }
}

