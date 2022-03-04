package com.unibo.progettosweng;

import com.unibo.progettosweng.client.ProgettoSWENGTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class ProgettoSWENGSuite extends GWTTestSuite {
  public static Test suite() {
    TestSuite suite = new TestSuite("Tests for ProgettoSWENG");
    suite.addTestSuite(ProgettoSWENGTest.class);
    return suite;
  }
}
