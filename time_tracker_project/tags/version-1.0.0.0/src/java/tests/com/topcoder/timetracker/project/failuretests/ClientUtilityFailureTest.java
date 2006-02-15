/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.project.ClientUtility;
import com.topcoder.timetracker.project.InsufficientDataException;
import com.topcoder.timetracker.project.ProjectPersistenceManager;

/**
 * Failure tests for ClientUtility implementation.
 * 
 * <p>
 * Here assump that all failure tests about PersistenceException will be taken
 * in the failure test of persistence class.
 * </p>
 * 
 * @author dmks
 * @version 1.0
 */
public class ClientUtilityFailureTest extends TestCase {

    /**
     * The ClientUtility instance to test against.
     */
    private ClientUtility utility = null;

    /**
     * Prepares a ClientUtility for testing. Also prepares the method arguments,
     * the persistence manager and the persistence.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    protected void setUp() throws Exception {
        FailureTestHelper.loadConfig();
        ProjectPersistenceManager manager = new ProjectPersistenceManager(FailureTestHelper.NAMESPACE);
        utility = new ClientUtility(manager);
    }

    /**
     * Clears all the namespaces.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test constructor will null, expects NullPointerException.
     *  
     */
    public void testConstructor() {
        try {
            new ClientUtility(null);
            fail("should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Test of addClient method with null client. Expects NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddClient_NullClient() throws Exception {
        try {
            utility.addClient(null);
            fail("Adds null client");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addClient method with illegal client. Expects
     * IllegalArgumentException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddClient_IllegalClient() throws Exception {
        try {
            utility.addClient(FailureTestHelper.createIllegalClient());
            fail("Adds illegal client");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addClient method with client with insufficient data. Expects
     * InsufficientDataException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddClient_InsufficientClient() throws Exception {
        try {
            utility.addClient(FailureTestHelper.createInsufficientClient());
            fail("Adds client with insufficient data");
        } catch (InsufficientDataException e) {
        }
    }

    /**
     * Test of updateClient method with null client. Expects
     * NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateClient_NullClient() throws Exception {
        try {
            utility.updateClient(null);
            fail("Updates null client");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of updateClient method with client with insufficient data. Expects
     * InsufficientDataException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateClient_InsufficientClient() throws Exception {
        try {
            utility.updateClient(FailureTestHelper.createInsufficientClient());
            fail("Updates client with insufficient data");
        } catch (InsufficientDataException e) {
        }
    }

    /**
     * Test of addProjectToClient method with null project. Expects
     * NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProjectToClient_NullProject() throws Exception {
        try {
            utility.addProjectToClient(1, null, "user");
            fail("Adds null project to client");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addProjectToClient method with null user. Expects
     * NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProjectToClient_NullUser() throws Exception {
        try {
            utility.addProjectToClient(1, FailureTestHelper.createProject(-1), null);
            fail("Adds FailureTestHelper.createProject() to client with null user");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addProjectToClient method with empty user. Expects
     * IllegalArgumentException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProjectToClient_EmptyUser() throws Exception {
        try {
            utility.addProjectToClient(1, FailureTestHelper.createProject(-1), "");
            fail("Adds FailureTestHelper.createProject() to client with empty user");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addProjectToClient method with illegal
     * FailureTestHelper.createProject(). Expects IllegalArgumentException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProjectToClient_IllegalProject() throws Exception {
        try {
            utility.addProjectToClient(1, FailureTestHelper.createIllegalProject(), "user");
            fail("Adds illegal FailureTestHelper.createProject() to client");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addProjectToClient method with FailureTestHelper.createProject()
     * with insufficient data. Expects InsufficientDataException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProjectToClient_InsufficientProject() throws Exception {
        try {
            utility.addProjectToClient(1, FailureTestHelper.createInsufficientProject(), "user");
            fail("Adds FailureTestHelper.createProject() with insufficient data to client");
        } catch (InsufficientDataException e) {
        }
    }

}
