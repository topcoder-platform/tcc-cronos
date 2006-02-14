/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.failuretests;

import com.topcoder.timetracker.entry.time.DAOFactory;
import com.topcoder.timetracker.entry.time.DAOFactoryException;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.TimeStatusDAO;

/**
 * Failure tests for <c>DAOFactory</c> class.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class DAOFactoryFailureTests extends FailureTestBase {

    /** Conafiguration namespace. */
    private static final String NAMESPACE = DAOFactoryFailureTests.class.getName();
    
    /** File path with test configuration. */
    private static final String CONF_FILE = "failuretests/dao_factory_conf.xml";
    
    /**
     * Sets up test environment.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        config.add(CONF_FILE);
    }

    /**
     * Tests <c>getDAO</c> method for invalid arguments.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetDAONullClass() throws Exception {
        try {
            DAOFactory.getDAO(null, NAMESPACE);
            fail("Null object, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>getDAO</c> method for invalid arguments.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetDAONullNamespace() throws Exception {
        try {
            DAOFactory.getDAO(TimeStatusDAO.class, null);
            fail("Null object, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>getDAO</c> method for invalid arguments.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetDAOEmptyNamespace() throws Exception {
        try {
            DAOFactory.getDAO(TimeStatusDAO.class, " ");
            fail("Empty namespace, IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>getDAO</c> method for invalid arguments.
     * 
     */
    public void testGetDAOClassNotDAO() {
        try {
            DAOFactory.getDAO(String.class, NAMESPACE);
            fail("DAOFactoryException expected.");
        } catch (DAOFactoryException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>getDAO</c> method for invalid arguments.
     * 
     */
    public void testGetDAOInvalidNamespace() {
        try {
            DAOFactory.getDAO(TimeStatus.class, "x");
            fail("DAOFactoryException expected.");
        } catch (DAOFactoryException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>getDAO</c> method for invalid configuration options.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetDAOConfigFailure1() throws Exception {
        try {
            DAOFactory.getDAO(TimeStatus.class, NAMESPACE + ".1");
            fail("DAOFactoryException expected.");
        } catch (DAOFactoryException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>getDAO</c> method for invalid configuration options.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetDAOConfigFailure2()throws Exception  {
        try {
            DAOFactory.getDAO(TimeStatus.class, NAMESPACE + ".2");
            fail("DAOFactoryException expected.");
        } catch (DAOFactoryException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>getDAO</c> method for invalid configuration options.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetDAOConfigFailure3() throws Exception {
        try {
            DAOFactory.getDAO(TimeStatus.class, NAMESPACE + ".3");
            fail("DAOFactoryException expected.");
        } catch (DAOFactoryException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>getDAO</c> method for invalid configuration options.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetDAOConfigFailure4() throws Exception {
        try {
            DAOFactory.getDAO(TimeStatus.class, NAMESPACE + ".4");
            fail("DAOFactoryException expected.");
        } catch (DAOFactoryException ex) {
            // ok
        }
    }
    
    
    /**
     * Tests <c>getDAO</c> method for invalid configuration options.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetDAOConfigFailure5() throws Exception {
        try {
            DAOFactory.getDAO(TimeStatus.class, NAMESPACE + ".5");
            fail("DAOFactoryException expected.");
        } catch (DAOFactoryException ex) {
            // ok
        }
    }

}
