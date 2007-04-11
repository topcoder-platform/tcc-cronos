/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.accuracytests;

import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.RateManager;

/**
 * Accuracy tests for the method: RateManager.
 * 
 * @author kinfkong
 * @version 3.2
 *
 */
public class RateManagerAccuracyTest extends AccuracyBase {

    /**
     * Represents the instance of RateManager for accuracy tests.
     */
    private RateManager manager = null;
    
    /**
     * Sets up the test environment.
     * 
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        initializeJNDIEnvironment();
        manager = new RateManager();
    }
    
    /**
     * Clears the test environment.
     */
    public void tearDown() throws Exception {
        clearJNDIEnvironment();
        super.tearDown();
    }
    
    /**
     * Accuracy tests for the constructor: RateManager().
     *
     */
    public void testRateManager() {
        assertNotNull("The instance cannot be created.", manager);
    }

    /**
     * Accuracy tests for the constructor: RateManager(String).
     *
     * @throws Exception to JUnit
     */
    public void testRateManagerString() throws Exception {
        assertNotNull("The instance cannot be created.", new RateManager(
            "com.topcoder.timetracker.rates"));
    }

    /**
     * Accuracy tests for the method: addRate.
     * 
     * @throws Exception to JUnit
     *
     */
    public void testAddRate() throws Exception {
        Rate rate = createDefaultRate(3, 3);
        manager.addRate(rate, false);
        assertTrue("The method does not work properly.", existsRate(rate));
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
        
        manager.addRates(rates, false);
        
        // check if they are exists
        for (int i = 0; i < rates.length; i++) {
            assertTrue("The method: addRates does not work properly.", existsRate(rates[i]));
        }
    }

    /**
     * Accuracy tests the method: deleteRate.
     * 
     * @throws Exception to JUnit
     */
    public void testDeleteRate() throws Exception {
        // creates the rates
        Rate[] rates = new Rate[10];
        for (int i = 0; i < rates.length; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        
        manager.addRates(rates, false);
        
        Rate deletedRate = createDefaultRate(3, 3);

        manager.deleteRate(deletedRate, false);
        
        // check if they exist
        for (int i = 0; i < 10; i++) {
            if (i == 2) {
                assertFalse("The method: deleted rates does not work properly.", existsRate(rates[i]));
             
            } else {
                assertTrue("The method: deleted rates does not work properly.", existsRate(rates[i]));
            }
        }
        
    }

    /**
     * Accuracy tests for the method: deleteRates.
     * 
     * @throws Exception to JUnit
     */
    public void testDeleteRates() throws Exception {
        // creates the rates
        Rate[] rates = new Rate[10];
        for (int i = 0; i < rates.length; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        
        manager.addRates(rates, false);
        
        Rate[] deletedRates = new Rate[5];
        
        for (int i = 0; i < 5; i++) {
            deletedRates[i] = rates[i];
        }
        
        manager.deleteRates(deletedRates, false);
        
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
        manager.addRates(rates, false);
        
        // retrieve one
        Rate rate = manager.retrieveRate(3, 3);
        
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
        manager.addRates(rates, false);
        
        // retrieve one
        Rate rate = manager.retrieveRate(30, 30);
        
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
        manager.addRates(rates, false);
        
        // retrieve one
        Rate rate = manager.retrieveRate("for accuracy tests of 3 3", 3);
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
        manager.addRates(rates, false);
        
        // retrieve one
        Rate rate = manager.retrieveRate("for accuracy tests of 3 3", 2);
      
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
        manager.addRates(rates, false);
        
        // retrieve one
        Rate[] retrievedRates = manager.retrieveRates(1);
        
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
        manager.addRates(rates, false);
        
        // retrieve one
        Rate[] retrievedRates = manager.retrieveRates(100);
        
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
        manager.addRates(rates, false);
        
        for (int i = 0; i < 10; i++) {
            rates[i] = createDefaultRate(101 + i, i + 1);
        }
        manager.addRates(rates, false);
        
        // retrieve one
        Rate[] retrievedRates = manager.retrieveRates(3);
        
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
     * Accuracy tests for the method: updateRate.
     * 
     * @throws Exception to JUnit
     */
    public void testUpdateRate() throws Exception {
        // creates the rates
        Rate[] rates = new Rate[10];
        for (int i = 0; i < rates.length; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        
        manager.addRates(rates, false);
        
        rates[3].setModificationUser("kinfkong updated");
        
        manager.updateRate(rates[3], false);
        
        // check if they are exists
        for (int i = 0; i < rates.length; i++) {
            assertTrue("The method: updateRates does not work properly.", existsRate(rates[i]));
        }
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
        
        manager.addRates(rates, false);
        
        for (int i = 0; i < 5; i++) {
            rates[i].setModificationUser("User who updated it");
        }
        
        manager.updateRates(rates, false);
        
        // check if they are exists
        for (int i = 0; i < rates.length; i++) {
            assertTrue("The method: updateRates does not work properly.", existsRate(rates[i]));
        }
    }
}
