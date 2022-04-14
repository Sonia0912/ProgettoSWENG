
package com.unibo.progettosweng.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.unibo.progettosweng.client.model.Corso;
import com.unibo.progettosweng.client.model.Esame;
import com.unibo.progettosweng.client.model.Registrazione;

import java.util.ArrayList;
import java.util.Arrays;

public class RegistrazioniServiceImplTest extends GWTTestCase {

    RegistrazioneServiceAsync service;

    public void gwtSetUp(){
        // Create the service that we will test.
        service = GWT.create(RegistrazioneService.class);
        ServiceDefTarget target = (ServiceDefTarget) service;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "progettosweng/registrazioni");
    }

    public synchronized void testAdd() throws Exception {
        delayTestFinish(10000);

        //inserimento prima registrazione
        service.add("studente", "Basi di Dati", new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure add registrazione: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                assertTrue(true);
            }
        });

        //inserimento seconda registrazione
        service.add("mario", "Basi di Dati", new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure add registrazione: " + caught.getMessage());
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
                assertEquals("Inserimento registrazione", new Integer(2), size);
                finishTest();
            }
        });
    }

    public synchronized void testGetRegistrazioniStudente(){
        delayTestFinish(10000);

        service.getRegistrazioniStudente("studente", new AsyncCallback<ArrayList<Registrazione>>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get registrazioni studente: " + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Registrazione> registrazioni) {
                assertEquals("get registrazioni studente", 1, registrazioni.size());
                finishTest();
            }
        });
    }

    public synchronized void testGetRegistrazioniFromEsame(){
        delayTestFinish(10000);

        service.getRegistrazioniFromEsame("Basi di Dati", new AsyncCallback<ArrayList<Registrazione>>() {
            @Override
            public void onFailure(Throwable caught) {
                fail("Failure get registrazioni from esame: " + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<Registrazione> registrazioni) {
                assertEquals("get registrazioni from esame", 2, registrazioni.size());
                finishTest();
            }
        });
    }

    @Override
    public String getModuleName() {
        return "com.unibo.progettosweng.ProgettoSWENGJUnit";
    }
}

