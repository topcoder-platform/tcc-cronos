/*
 * ClientUtilityTest.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit tests for ClientUtility implementation.
 * 
 * <p>
 * Here we assume that the methods return the result of the call to the underlying persistence, and that the
 * PersistenceException is just propagated as usual. For simplicity, tests on method returns and PersistenceException
 * are skipped here.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientUtilityTest extends TestCase {
    /**
     * The ClientUtility instance to test against.
     */
    private ClientUtility utility = null;

    /**
     * The ProjectPersistenceManager used by the ClientUtility.
     */
    private ProjectPersistenceManager manager = null;

    /**
     * The underlying persistence used by the ProjectPersistenceManager.
     */
    private SimplePersistence persistence = null;

    /**
     * The valid client argument supplied to the methods.
     */
    private Client client = null;

    /**
     * The valid project argument supplied to the methods.
     */
    private Project project = null;

    /**
     * The client id argument supplied to the methods.
     */
    private int clientId = -1;

    /**
     * The project id argument supplied to the methods.
     */
    private int projectId = -1;

    /**
     * The valid user argument supplied to the methods.
     */
    private String user = null;

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ClientUtilityTest.class);

        return suite;
    }

    /**
     * Prepares a ClientUtility for testing. Also prepares the method arguments, the persistence manager and the
     * persistence.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfig();
        manager = new SimplePersistenceManager(TestHelper.NAMESPACE);
        persistence = (SimplePersistence) manager.getPersistence();

        client = TestHelper.createClient();
        project = TestHelper.createProject();
        clientId = 100;
        projectId = 200;
        user = "user";

        utility = new ClientUtility(manager);
    }

    /**
     * Clears all the namespaces.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void tearDown() throws Exception {
        TestHelper.unloadConfig();
    }

    /**
     * Test of constructor with null manager. Expects NullPointerException.
     */
    public void testConstructor_NullManager() {
        try {
            new ClientUtility(null);
            fail("Creates ClientUtility with null manager");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of addClient method with null client. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddClient_NullClient() throws Exception {
        try {
            utility.addClient(null);
            fail("Adds null client");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of addClient method with illegal client. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddClient_IllegalClient() throws Exception {
        try {
            utility.addClient(TestHelper.createIllegalClient());
            fail("Adds illegal client");
        } catch (IllegalArgumentException e) {}
    }

    /**
     * Test of addClient method with client with insufficient data. Expects InsufficientDataException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddClient_InsufficientClient() throws Exception {
        try {
            utility.addClient(TestHelper.createInsufficientClient());
            fail("Adds client with insufficient data");
        } catch (InsufficientDataException e) {}
    }

    /**
     * Test of addClient method with valid client. Verifies if the persistence method was called appropriately by
     * checking the last method being called and the arguments passed.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddClient_ValidClient() throws Exception {
        utility.addClient(client);

        assertEquals("Fails to call method", "addClient", persistence.getLastMethod());
        assertEquals("Fails to pass arguments", client, persistence.getLastClient());
    }

    /**
     * Test of removeClient method. Verifies if the persistence method was called appropriately by checking the last
     * method being called and the arguments passed.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveClient() throws Exception {
        utility.removeClient(clientId);

        assertEquals("Fails to call method", "removeClient", persistence.getLastMethod());
        assertEquals("Fails to pass arguments", clientId, persistence.getLastClientId());
    }

    /**
     * Test of removeAllClients method. Verifies if the persistence method was called appropriately by checking the
     * last method being called and the arguments passed.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveAllClients() throws Exception {
        utility.removeAllClients();

        assertEquals("Fails to call method", "removeAllClients", persistence.getLastMethod());
    }

    /**
     * Test of updateClient method with null client. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testUpdateClient_NullClient() throws Exception {
        try {
            utility.updateClient(null);
            fail("Updates null client");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of updateClient method with client with insufficient data. Expects InsufficientDataException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testUpdateClient_InsufficientClient() throws Exception {
        try {
            utility.updateClient(TestHelper.createInsufficientClient());
            fail("Updates client with insufficient data");
        } catch (InsufficientDataException e) {}
    }

    /**
     * Test of updateClient method with valid client. Verifies if the persistence method was called appropriately by
     * checking the last method being called and the arguments passed.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testUpdateClient_ValidClient() throws Exception {
        utility.updateClient(client);

        assertEquals("Fails to call method", "updateClient", persistence.getLastMethod());
        assertEquals("Fails to pass arguments", client, persistence.getLastClient());
    }

    /**
     * Test of addProjectToClient method with null project. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_NullProject() throws Exception {
        try {
            utility.addProjectToClient(clientId, null, user);
            fail("Adds null project to client");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of addProjectToClient method with null user. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_NullUser() throws Exception {
        try {
            utility.addProjectToClient(clientId, project, null);
            fail("Adds project to client with null user");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of addProjectToClient method with empty user. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_EmptyUser() throws Exception {
        try {
            utility.addProjectToClient(clientId, project, "");
            fail("Adds project to client with empty user");
        } catch (IllegalArgumentException e) {}
    }

    /**
     * Test of addProjectToClient method with illegal project. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_IllegalProject()
        throws Exception
    {
        try {
            utility.addProjectToClient(clientId, TestHelper.createIllegalProject(), user);
            fail("Adds illegal project to client");
        } catch (IllegalArgumentException e) {}
    }

    /**
     * Test of addProjectToClient method with project with insufficient data. Expects InsufficientDataException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_InsufficientProject()
        throws Exception
    {
        try {
            utility.addProjectToClient(clientId, TestHelper.createInsufficientProject(), user);
            fail("Adds project with insufficient data to client");
        } catch (InsufficientDataException e) {}
    }

    /**
     * Test of addProjectToClient method with valid project and user. Verifies if the persistence method was called
     * appropriately by checking the last method being called and the arguments passed.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_Valid() throws Exception {
        utility.addProjectToClient(clientId, project, user);

        assertEquals("Fails to call method", "addProjectToClient", persistence.getLastMethod());
        assertEquals("Fails to pass arguments", project, persistence.getLastProject());
        assertEquals("Fails to pass arguments", user, persistence.getLastUser());
    }

    /**
     * Test of removeProjectFromClient method. Verifies if the persistence method was called appropriately by checking
     * the last method being called and the arguments passed.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveProjectFromClient() throws Exception {
        utility.removeProjectFromClient(clientId, projectId);

        assertEquals("Fails to call method", "removeProjectFromClient", persistence.getLastMethod());
        assertEquals("Fails to pass arguments", clientId, persistence.getLastClientId());
        assertEquals("Fails to pass arguments", projectId, persistence.getLastProjectId());
    }

    /**
     * Test of getClientProject method. Verifies if the persistence method was called appropriately by checking the
     * last method being called and the arguments passed.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetClientProject() throws Exception {
        utility.getClientProject(clientId, projectId);

        assertEquals("Fails to call method", "getClientProject", persistence.getLastMethod());
        assertEquals("Fails to pass arguments", clientId, persistence.getLastClientId());
        assertEquals("Fails to pass arguments", projectId, persistence.getLastProjectId());
    }

    /**
     * Test of getAllClientProjects method. Verifies if the persistence method was called appropriately by checking the
     * last method being called and the arguments passed.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetAllClientProjects() throws Exception {
        utility.getAllClientProjects(clientId);

        assertEquals("Fails to call method", "getAllClientProjects", persistence.getLastMethod());
        assertEquals("Fails to pass arguments", clientId, persistence.getLastClientId());
    }

    /**
     * Test of getClient method. Verifies if the persistence method was called appropriately by checking the last
     * method being called and the arguments passed.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetClient() throws Exception {
        utility.getClient(clientId);

        assertEquals("Fails to call method", "getClient", persistence.getLastMethod());
        assertEquals("Fails to pass arguments", clientId, persistence.getLastClientId());
    }

    /**
     * Test of getAllClients method. Verifies if the persistence method was called appropriately by checking the last
     * method being called and the arguments passed.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetAllClients() throws Exception {
        utility.getAllClients();

        assertEquals("Fails to call method", "getAllClients", persistence.getLastMethod());
    }

    /**
     * Test of getPersistenceManager method. Verifies if it returns the persistence manager set in the constructor.
     */
    public void testGetPersistenceManager() {
        assertEquals("Returns an incorrect persistence manager", manager, utility.getPersistenceManager());
    }
}
