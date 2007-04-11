/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.accuracytests;

import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.ejb.RateEjb;

/**
 * Accuracy tests for the class: RateEjb.
 * 
 * @author kinfkong
 * @version 3.2
 */
public class RateEjbAccuracyTest extends AccuracyBase {

    /**
     * Represents the RateEjb instance for accuracy tests.
     */
    private RateEjb ejb = null;
    
    /**
     * Sets up the test environment.
     * 
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        initializeJNDIEnvironment();
        ejb = new RateEjb();
        ejb.setSessionContext(new DummySessionContext());
        ejb.ejbCreate();
    }
    
    /**
     * Clears the test environment.
     */
    public void tearDown() throws Exception {
        clearJNDIEnvironment();
        super.tearDown();
        
    }
    
    /**
     * Accuracy tests for the constructor.
     *
     */
    public void testRateEjb() {
        assertNotNull("The instance of RateEjb cannot be created.", ejb);
    }

    /**
     * Accuracy tests for the method: addRates.
     * 
     * @throws Exception to JUnit
     */
    public void testAddRates() throws Exception {
        // creates the rates
        Rate[] rates = new Rate[10];
        for (int i = 0; i < rates.length; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        
        ejb.addRates(rates, false);
        
        // check if they are exists
        for (int i = 0; i < rates.length; i++) {
            assertTrue("The method: addRates does not work properly.", existsRate(rates[i]));
        }
    }

    /**
     * Accuracy tests for the method: deleteRates.
     * 
     * @throws Exceptiont to JUnit
     */
    public void testDeleteRates() throws Exception {
        // creates the rates
        Rate[] rates = new Rate[10];
        for (int i = 0; i < rates.length; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        
        ejb.addRates(rates, false);
        
        Rate[] deletedRates = new Rate[5];
        
        for (int i = 0; i < 5; i++) {
            deletedRates[i] = rates[i];
        }
        
        ejb.deleteRates(deletedRates, false);
        
        // check if they exist
        for (int i = 0; i < 10; i++) {
            if (i < 5) {
                assertFalse("The method: deleted rates does not work properly.", existsRate(rates[i]));
             
            } else {
                assertTrue("The method: deleted rates does not work properly.", existsRate(rates[i]));
            }
        }
    }

    /**
     * Accuracy tests for the method: ejbActivate.
     *
     */
    public void testEjbActivate() {
        // no exception is thrown
        ejb.ejbActivate();
    }

    /**
     * Accuracy tests for the method: ejbCreate().
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate() throws Exception {
        // no exception is thrown
        ejb.ejbCreate();
    }

    /**
     * Accuracy tests for the method: ejbPassivate.
     * 
     * @throws Exception to JUnit
     *
     */
    public void testEjbPassivate() throws Exception {
        // no exception is thrown
        ejb.ejbPassivate();
    }

    /**
     * Accuracy tests for the method: ejbRemove.
     *
     */
    public void testEjbRemove() {
        // no exception is thrown
        ejb.ejbRemove();
    }

    /**
     * Accuracy tests for the method: retrieveRate(long, long).
     * 
     * @throws Exception to JUnit
     *
     */
    public void testRetrieveRateLongLong() throws Exception {
        addToRate();
        
        Rate[] rates = new Rate[10];
        
        for (int i = 0; i < 10; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        ejb.addRates(rates, false);
        
        // retrieve one
        Rate rate = ejb.retrieveRate(3, 3);
        
        // check accuracy
        assertEquals("The method; retrieveRate does not work properly.", 
            "for accuracy tests of 3 3", rate.getDescription());
    }

    /**
     * Accuracy tests for the method: retrieveRate(long, long).
     * 
     * @throws Exception to JUnit
     *
     */
    public void testRetrieveRateLongLong_NotFound() throws Exception {
        addToRate();
        
        Rate[] rates = new Rate[10];
        
        for (int i = 0; i < 10; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        ejb.addRates(rates, false);
        
        // retrieve one
        Rate rate = ejb.retrieveRate(30, 30);
        
        assertNull("The method: retrieveRate does not work properly.", rate);
    }
    
    /**
     * Accuracy tests for the method: retrieveRate(String, Long).
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateStringLong() throws Exception {
        addToRate();
        
        Rate[] rates = new Rate[10];
        
        for (int i = 0; i < 10; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        ejb.addRates(rates, false);
        
        // retrieve one
        Rate rate = ejb.retrieveRate("for accuracy tests of 3 3", 3);
        // check accuracy
        assertEquals("The method; retrieveRate does not work properly.", 
            "for accuracy tests of 3 3", rate.getDescription());
    }

    /**
     * Accuracy tests for the method: retrieveRate(String, Long).
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateStringLong_NotFound() throws Exception {
        addToRate();
        
        Rate[] rates = new Rate[10];
        
        for (int i = 0; i < 10; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        ejb.addRates(rates, false);
        
        // retrieve one
        Rate rate = ejb.retrieveRate("for accuracy tests of 3 3", 2);
      
        assertNull("The method: retrieveRate(String, long) does not work properly.", rate);
    }

    
    /**
     * Accuracy tests for the method: retrieveRates.
     * 
     * @throws Exception to JUnit
     */
    public void testRetrieveRates_One() throws Exception {
       addToRate();
        
        Rate[] rates = new Rate[10];
        
        for (int i = 0; i < 10; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        ejb.addRates(rates, false);
        
        // retrieve one
        Rate[] retrievedRates = ejb.retrieveRates(1);
        
        assertEquals("The method: retrieveRates does not work properly.", 1, retrievedRates.length);
        
        assertEquals("The method: retrieveRates does not work properly.", 
            "for accuracy tests of 1 1", retrievedRates[0].getDescription());
    }
    
    /**
     * Accuracy tests for the method: retrieveRates.
     * 
     * @throws Exception to JUnit
     */
    public void testRetrieveRates_NotFound() throws Exception {
       addToRate();
        
        Rate[] rates = new Rate[10];
        
        for (int i = 0; i < 10; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        ejb.addRates(rates, false);
        
        // retrieve one
        Rate[] retrievedRates = ejb.retrieveRates(100);
        
        assertEquals("The method: retrieveRates does not work properly.", 0, retrievedRates.length);
        
        
    }

    /**
     * Accuracy tests for the method: retrieveRates.
     * 
     * @throws Exception to JUnit
     */
    public void testRetrieveRates_MoreThanOne() throws Exception {
        addToRate();
        addToRate(101);
        
        Rate[] rates = new Rate[10];
        
        for (int i = 0; i < 10; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        ejb.addRates(rates, false);
        
        for (int i = 0; i < 10; i++) {
            rates[i] = createDefaultRate(101 + i, i + 1);
        }
        ejb.addRates(rates, false);
        
        // retrieve one
        Rate[] retrievedRates = ejb.retrieveRates(3);
        
        assertEquals("The method: retrieveRates does not work properly.", 2, retrievedRates.length);
        
        int c = 0;
        for (int i = 0; i < retrievedRates.length; i++) {
            if (retrievedRates[i].getDescription().equals("for accuracy tests of 3 3")) {
                c++;
            } else if(retrievedRates[i].getDescription().equals("for accuracy tests of 103 3")) {
                c++;
            }
            
        }
        
        assertEquals("The method: retrieveRates does not work properly.", 2, c);
        
    }
    
    /**
     * Accuracy tests for the method: setSessionContext.
     */
    public void testSetSessionContext() {
        // no exception is thrown
        ejb.setSessionContext(new DummySessionContext());
    }

    /**
     * Accuracy tests for the method: updateRates.
     * 
     * @throws Exception to JUnit
     *
     */
    public void testUpdateRates() throws Exception {
        // creates the rates
        Rate[] rates = new Rate[10];
        for (int i = 0; i < rates.length; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        
        ejb.addRates(rates, false);
        
        for (int i = 0; i < 5; i++) {
            rates[i].setModificationUser("User who updated it");
        }
        
        ejb.updateRates(rates, false);
        
        // check if they are exists
        for (int i = 0; i < rates.length; i++) {
            assertTrue("The method: updateRates does not work properly.", existsRate(rates[i]));
        }
    }

}
