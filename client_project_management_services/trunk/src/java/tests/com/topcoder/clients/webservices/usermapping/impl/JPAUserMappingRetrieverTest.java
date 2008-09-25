/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.usermapping.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.webservices.mock.TestBase;
import com.topcoder.clients.webservices.usermapping.UserMappingRetrievalException;

/**
 * Unit test {@link JPAUserMappingRetriever}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class JPAUserMappingRetrieverTest extends TestBase {

    /**
     * The UMRE fail message.
     */
    private static final String UMRE = "UserMappingRetrievalException is expected to be thrown.";

    /**
     * The IAE fail message.
     */
    private static final String T_IAE = "IllegalArgumentException is expected to be thrown.";

    /**
     * The ISE fail message.
     */
    private static final String T_ISE = "IllegalStateException is expected to be thrown.";

    /**
     * Represents JPA mapping instance.
     * Null entityManager;
     */
    private JPAUserMappingRetriever jpa1;

    /**
     * Represents JPA mapping instance.
     * Not null entity manager.
     */
    private JPAUserMappingRetriever jpa2;

    /**
     * Setup environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        jpa1 = new JPAUserMappingRetriever();
        jpa2 = new JPAUserMappingRetriever(getEntityManager());
    }

    /**
     * Teardown environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void tearDown() throws Exception {
        jpa1 = null;
        jpa2 = null;

        super.tearDown();
    }

    /**
     * Test for {@link JPAUserMappingRetriever#JPAUserMappingRetriever()}.
     */
    public void testJPAUserMappingRetriever() {
        assertNotNull("Fail create new instance.", jpa1);
        assertNull("Fail create new insance", jpa1.getEntityManager());
    }

    /**
     * Test for {@link JPAUserMappingRetriever#JPAUserMappingRetriever(jEntityManager)}.
     */
    public void testJPAUserMappingRetrieverEntityManager() {
        EntityManager manager = getEntityManager();
        jpa2 = new JPAUserMappingRetriever(manager);

        assertEquals("Fail setup property", manager, jpa2.getEntityManager());
        assertNotNull("Fail setup property.", jpa2.getEntityManager());

        manager = null;
        jpa2 = new JPAUserMappingRetriever(manager);
        assertNull("Fail setup property.", jpa2.getEntityManager());
    }

    /**
     * Test for {@link JPAUserMappingRetriever#setEntityManager(EntityManager)}.
     */
    public void testSetEntityManager() {
        EntityManager manager = getEntityManager();

        assertNull("Fail setup property.", jpa1.getEntityManager());

        jpa1.setEntityManager(manager);

        assertNotNull("Fail setup property.", jpa1.getEntityManager());
        assertEquals("Fail setup property.", manager, jpa1.getEntityManager());
    }

    /**
     * Test for {@link JPAUserMappingRetriever#getValidUsersForClient(Client)}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetValidUsersForClient() throws Exception {
        Client client = createClient();
        List<Long> ret = jpa2.getValidUsersForClient(client);
        assertTrue("Fail perform operation.", ret.isEmpty());

        final long userId = 12;
        createUserClientMapping(client.getId(), userId);
        ret = jpa2.getValidUsersForClient(client);

        assertEquals("Fail perform operation.", 1, ret.size());
        assertEquals("Fail perform operation.", userId, ret.get(0).longValue());
    }

    /**
     * Test for {@link JPAUserMappingRetriever#getValidUsersForClient(Client)}.
     *
     * Caused by: Null Client.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetValidUsersForClient_WithNullClient() throws Exception {
        try {
            jpa2.getValidUsersForClient(null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link JPAUserMappingRetriever#getValidUsersForClient(Client)}.
     *
     * Caused by: Null EntityManager.
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetValidUsersForClient_WithNullEM() throws Exception {
        try {
            jpa1.getValidUsersForClient(new Client());
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link JPAUserMappingRetriever#getValidUsersForClient(Client)}.
     *
     * Caused by: Closed EntityManager.
     * Expected : {@link UserMappingRetrievalException}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetValidUsersForClient_WithClosedEM() throws Exception {
        try {
            getEntityManager().close();
            jpa2.getValidUsersForClient(new Client());
            fail(UMRE);
        } catch (UserMappingRetrievalException e) {
            // OK.
        }
    }

    /**
     * Test fpr {@link JPAUserMappingRetriever#getValidUsersForProject(Project)}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetValidUsersForProject() throws Exception {
        Project project = createProject();
        List<Long> ret = jpa2.getValidUsersForProject(project);
        assertTrue("Fail perform operation.", ret.isEmpty());

        long[] userIds = {1, 2};
        createUserProjectMapping(project.getId(), userIds[0]);
        createUserProjectMapping(project.getId(), userIds[1]);
        ret = jpa2.getValidUsersForProject(project);

        assertEquals("Fail perform operation.", userIds.length, ret.size());
        assertEquals("Fail perform operation.", userIds[0], ret.get(1).longValue());
        assertEquals("Fail perform operation.", userIds[1], ret.get(0).longValue());
    }

    /**
     * Test fpr {@link JPAUserMappingRetriever#getValidUsersForProject(Project)}.
     *
     * Caused by: Null Project.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetValidUsersForProject_WithNullProject() throws Exception {
        try {
            jpa2.getValidUsersForProject(null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test fpr {@link JPAUserMappingRetriever#getValidUsersForProject(Project)}.
     *
     * Caused by: Null EntityManager.
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetValidUsersForProject_WithNullEM() throws Exception {
        try {
            jpa1.getValidUsersForProject(new Project());
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test fpr {@link JPAUserMappingRetriever#getValidUsersForProject(Project)}.
     *
     * Caused by: Null EntityManager.
     * Expected : {@link UserMappingRetrievalException}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetValidUsersForProject_WithClosedEM() throws Exception {
        try {
            getEntityManager().close();
            jpa2.getValidUsersForProject(new Project());
            fail(UMRE);
        } catch (UserMappingRetrievalException e) {
            // OK.
        }
    }

    /**
     * Test for {@link JPAUserMappingRetriever#getClientsForUserId(long)}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetClientsForUserId() throws Exception {
        long userId = 1;
        Client client  = createClient();

        List<Client> ret = jpa2.getClientsForUserId(userId);
        assertTrue("Fail perform operation.", ret.isEmpty());

        createUserClientMapping(client.getId(), userId);
        createUserClientMapping(client.getId(), userId);

        ret = jpa2.getClientsForUserId(userId);
        assertEquals("Fail perform operation.", 1, ret.size());
    }

    /**
     * Test for {@link JPAUserMappingRetriever#getClientsForUserId(long)}.
     *
     * Caused by: UserId is 0.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetClientsForUserId_WithNolUserId() throws Exception {
        try {
            jpa2.getClientsForUserId(0);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link JPAUserMappingRetriever#getClientsForUserId(long)}.
     *
     * Caused by: Null EntityManager.
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetClientsForUserId_WithNullEM() throws Exception {
        try {
            jpa1.getClientsForUserId(1);
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link JPAUserMappingRetriever#getClientsForUserId(long)}.
     *
     * Caused by: Closed EntityManager.
     * Expected : {@link UserMappingRetrievalException}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetClientsForUserId_WithClosedEM() throws Exception {
        try {
            getEntityManager().close();
            jpa2.getClientsForUserId(1);
            fail(UMRE);
        } catch (UserMappingRetrievalException e) {
            // OK.
        }
    }

    /**
     * Test for {@link JPAUserMappingRetriever#getProjectsForUserId(long)}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetProjectsForUserId() throws Exception {
        long userId = 1;
        Project project = createProject();
        Project project2 = createProject();

        List<Project> ret = jpa2.getProjectsForUserId(userId);
        assertTrue("Fail perform operation.", ret.isEmpty());

        createUserProjectMapping(project.getId(), userId);
        createUserProjectMapping(project2.getId(), userId);

        ret = jpa2.getProjectsForUserId(userId);

        final int expected = 2;
        assertEquals("Fail perform operation.", expected, ret.size());
        assertTrue("Fail perform operation.", ret.contains(project));
        assertTrue("Fail perform operation.", ret.contains(project2));
    }

    /**
     * Test for {@link JPAUserMappingRetriever#getProjectsForUserId(long)}.
     *
     * Caused by: UserId less than 0.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetProjectsForUserId_WithNegativeUserId() throws Exception {
        try {
            jpa2.getProjectsForUserId(-1);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link JPAUserMappingRetriever#getProjectsForUserId(long)}.
     *
     * Caused by: Null EntityManager.
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetProjectsForUserId_WithNullEM() throws Exception {
        try {
            jpa1.getProjectsForUserId(1);
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link JPAUserMappingRetriever#getProjectsForUserId(long)}.
     *
     * Caused by: Closed EntityManager.
     * Expected : {@link UserMappingRetrievalException}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetProjectsForUserId_WithClosedEM() throws Exception {
        try {
            getEntityManager().close();
            jpa2.getProjectsForUserId(1);
            fail(UMRE);
        } catch (UserMappingRetrievalException e) {
            // OK.
        }
    }

    /**
     * Test for {@link JPAUserMappingRetriever#getProjectsForUserId(long)}.
     *
     * Caused by: Fail in main operation.
     * Expected : {@link UserMappingRetrievalException}.
     *
     * @throws Exception into JUnit.
     */
    public void testGetProjectsForUserId_WithFailInOperation() throws Exception {
        try {
            getEntityManager().close();
            jpa2.getProjectsForUserId(1);
        } catch (UserMappingRetrievalException e) {
            // OK.
        }
    }
}
