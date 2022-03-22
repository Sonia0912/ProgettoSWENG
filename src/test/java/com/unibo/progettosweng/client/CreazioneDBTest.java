package com.unibo.progettosweng.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import org.junit.Test;

public class CreazioneDBTest extends GWTTestCase {


    /**
     * Must refer to a valid module that sources this class.
     */
    public String getModuleName() {
        return "com.unibo.progettosweng.ProgettoSWENGJUnit";
    }


    public void testUtentiService() {
        // Create the service that we will test.
        UtenteServiceAsync utentiServices = GWT.create(UtenteService.class);
        ServiceDefTarget target = (ServiceDefTarget) utentiServices;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "progettosweng/utenti");

        // Since RPC calls are asynchronous, we will need to wait for a response
        // after this test method returns. This line tells the test runner to wait
        // up to 10 seconds before timing out.
        delayTestFinish(10000);

        // Send a request to the server.
        String [] info = {"gian", "luca", "g", "123", "studente"};
        utentiServices.add( info, new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                // The request resulted in an unexpected error.
                fail("Request failure: " + caught.getMessage());
            }

            @Override
            public void onSuccess(String info) {

                    System.out.println(info);
                    assertTrue(info.startsWith("Succ"));

                    // Now that we have received a response, we need to tell the test runner
                    // that the test is complete. You must call finishTest() after an
                    // asynchronous test finishes successfully, or the test will time out.
                    finishTest();
            }

        });
    }


}
