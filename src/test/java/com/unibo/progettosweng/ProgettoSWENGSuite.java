package com.unibo.progettosweng;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.unibo.progettosweng.client.IscrizioneServiceImplTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class ProgettoSWENGSuite extends GWTTestSuite {
  public static Test suite() {
    TestSuite suite = new TestSuite("Tests for ProgettoSWENG");
    suite.addTestSuite(IscrizioneServiceImplTest.class);
    return suite;
  }
}
