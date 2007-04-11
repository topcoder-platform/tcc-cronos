package com.topcoder.timetracker.rates.accuracytests;

import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.persistence.InformixRatePersistence;

/**
 * Accuracy tests for the class: InformixRatePersistence.
 * 
 * @author kinfkong
 * @version 3.2
 */
public class InformixRatePersistenceAccuracyTest extends AccuracyBase {
    
    /**
     * Represents the instance of InformixRatePersistence for accuracy tests.
     */
    private InformixRatePersistence irp = null;
    
    /**
     * Sets up the test environment.
     * 
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        
        irp = new InformixRatePersistence("com.topcoder.timetracker.rates.persistence.InformixRatePersistence");
    }
    
    /**
     * Removes all the configuration environments.
     * 
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception {
        super.tearDown();
        
        
    }
    
    /**
     * Accuracy tests for the constructor: InformixRatePersistence.
     *
     * Checks if the instance can be constructed.
     */
    public void testInformixRatePersistence() {
        assertNotNull("The instance cannot be created.", irp);
    }
    
    /**
     * Accuracy tests for the method: addRates().
     *
     * @throws Exception to JUnit
     */
    public void testAddRates() throws Exception {
        // creates the rates
        Rate[] rates = new Rate[10];
        for (int i = 0; i < rates.length; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        
        irp.addRates(rates, false);
        
        // check if they are exists
        for (int i = 0; i < rates.length; i++) {
            assertTrue("The method: addRates does not work properly.", existsRate(rates[i]));
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
        
        irp.addRates(rates, false);
        
        for (int i = 0; i < 5; i++) {
            rates[i].setModificationUser("User who updated it");
        }
        
        irp.updateRates(rates, false);
        
        // check if they are exists
        for (int i = 0; i < rates.length; i++) {
            assertTrue("The method: updateRates does not work properly.", existsRate(rates[i]));
        }
    }

    /**
     * Accuracy tests for the method: deleteRates().
     * 
     * @throws Exception to JUnit
     *
     */
    public void testDeleteRates() throws Exception {
        // creates the rates
        Rate[] rates = new Rate[10];
        for (int i = 0; i < rates.length; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        
        irp.addRates(rates, false);
        
        Rate[] deletedRates = new Rate[5];
        
        for (int i = 0; i < 5; i++) {
            deletedRates[i] = rates[i];
        }
        
        irp.deleteRates(deletedRates, false);
        
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
        irp.addRates(rates, false);
        
        // retrieve one
        Rate rate = irp.retrieveRate(3, 3);
        
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
        irp.addRates(rates, false);
        
        // retrieve one
        Rate rate = irp.retrieveRate(30, 30);
        
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
        irp.addRates(rates, false);
        
        // retrieve one
        Rate rate = irp.retrieveRate("for accuracy tests of 3 3", 3);
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
        irp.addRates(rates, false);
        
        // retrieve one
        Rate rate = irp.retrieveRate("for accuracy tests of 3 3", 2);
      
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
        irp.addRates(rates, false);
        
        // retrieve one
        Rate[] retrievedRates = irp.retrieveRates(1);
        
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
        irp.addRates(rates, false);
        
        // retrieve one
        Rate[] retrievedRates = irp.retrieveRates(100);
        
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
        irp.addRates(rates, false);
        
        for (int i = 0; i < 10; i++) {
            rates[i] = createDefaultRate(101 + i, i + 1);
        }
        irp.addRates(rates, false);
        
        // retrieve one
        Rate[] retrievedRates = irp.retrieveRates(3);
        
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
    
 
    
}
