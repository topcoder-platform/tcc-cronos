/*
 * Copyright (c) 2005, 2006 TopCoder, Inc., All Rights Reserved
 */
package com.cronos.timetracker.project.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.project.Client;
import com.cronos.timetracker.project.ClientUtility;
import com.cronos.timetracker.project.InsufficientDataException;
import com.cronos.timetracker.project.ProjectPersistenceManager;
import com.cronos.timetracker.project.persistence.BatchOperationException;
import com.cronos.timetracker.project.searchfilters.Filter;

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
 * @author kr00tki
 * @version 1.1
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

    // since 1.1

    /**
     * Tests the {@link ClientUtility#searchClients(Filter)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testSearchClients_NullFilter() throws Exception {
        try {
            utility.searchClients(null);
            fail("Null filter, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#searchClients(Filter)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testSearchClients_IllegalFilter() throws Exception {
        try {
            utility.searchClients(new Filter() {
            });
            fail("Illegal filter, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#addClients(Client[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddClients_NullArray() throws Exception {
        try {
            utility.addClients(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#addClients(Client[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddClients_EmptyArray() throws Exception {
        try {
            utility.addClients(new Client[] {}, true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#addClients(Client[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddClients_ArrayWithNull() throws Exception {
        try {
            utility.addClients(new Client[] { null }, true);
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#addClients(Client[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddClients_InsufficientData() throws Exception {
        try {
            utility.addClients(new Client[] { FailureTestHelper.createInsufficientClient() }, true);
            fail("Insufficient data, IAE expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#addClients(Client[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddClients_IllegalData() throws Exception {
        try {
            utility.addClients(new Client[] { FailureTestHelper.createIllegalClient() }, true);
            fail("Illegal data, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#removeClients(int[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveClients_NullArray() throws Exception {
        try {
            utility.removeClients(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#removeClients(int[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveClients_EmptyArray() throws Exception {
        try {
            utility.removeClients(new int[] {}, true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#removeClients(int[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveClients_NoClient() throws Exception {
        try {
            utility.removeClients(new int[] { 1 }, true);
            fail("No such client, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#updateClients(Client[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateClients_NullArray() throws Exception {
        try {
            utility.updateClients(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#updateClients(Client[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateClients_EmptyArray() throws Exception {
        try {
            utility.updateClients(new Client[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#updateClients(Client[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateClients_ArrayWithNull() throws Exception {
        try {
            utility.updateClients(new Client[] { null }, true);
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#updateClients(Client[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateClients_InsufficientData() throws Exception {
        try {
            utility.updateClients(new Client[] { FailureTestHelper.createInsufficientClient() }, true);
            fail("Insufficient data, IAE expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#getClients(int[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetClients_NullArray() throws Exception {
        try {
            utility.getClients(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#getClients(int[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetClients_EmptyArray() throws Exception {
        try {
            utility.getClients(new int[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ClientUtility#getClients(int[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetClients_NoClient() throws Exception {
        try {
            utility.getClients(new int[] { 1 }, true);
            fail("No such client, IAE expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }
}
