package com.unibo.progettosweng;

import com.unibo.progettosweng.client.EsamiServiceImplTest;
import com.unibo.progettosweng.client.RegistrazioniServiceImplTest;
import com.unibo.progettosweng.client.UtentiServiceImplTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class ProgettoSWENGSuite extends GWTTestSuite {
  public static Test suite() {
    TestSuite suite = new TestSuite("Tests for ProgettoSWENG");
    suite.addTestSuite(UtentiServiceImplTest.class);
    suite.addTestSuite(EsamiServiceImplTest.class);
    suite.addTestSuite(RegistrazioniServiceImplTest.class);
    return suite;
  }
}
