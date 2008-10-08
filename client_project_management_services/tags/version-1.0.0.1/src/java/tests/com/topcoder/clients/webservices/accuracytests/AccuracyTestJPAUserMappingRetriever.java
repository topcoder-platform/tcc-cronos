/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.accuracytests;

import java.util.List;

import javax.persistence.EntityManager;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.webservices.usermapping.impl.JPAUserMappingRetriever;

/**
 * This class contains unit tests for <code>JPAUserMappingRetriever</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestJPAUserMappingRetriever extends AccuracyBaseTest {
    /**
     * <p>
     * Represents the instance of JPAUserMappingRetriever used for test.
     * </p>
     */
    private JPAUserMappingRetriever retriever = null;

    /**
     * <p>
     * Represents the instance of EntityManager used for test.
     * </p>
     */
    private EntityManager mgr = null;

    /**
     * Set Up the test environment before testing.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        mgr = getEntityManager();
        retriever = new JPAUserMappingRetriever(mgr);
    }

    /**
     * Clean up the test environment after testing.
     * 
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        retriever = null;
    }

    /**
     * Function test : Tests <code>JPAUserMappingRetriever()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testJPAUserMappingRetrieverAccuracy1() throws Exception {
        retriever = new JPAUserMappingRetriever();
        assertNotNull("Should not be null.", retriever);
    }

    /**
     * Function test : Tests <code>JPAUserMappingRetriever(EntityManager entityManager)</code>
     * method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testJPAUserMappingRetrieverAccuracy2() throws Exception {
        assertNotNull("Should not be null.", retriever);
    }

    /**
     * Function test : Tests <code>setEntityManager(EntityManager entityManager)</code> method for
     * accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testSetEntityManagerAccuracy() throws Exception {
        DummyJPAUserMappingRetriever r = new DummyJPAUserMappingRetriever();
        assertNull("Should be null.", r.getEntityManager());
        r.setEntityManager(mgr);
        assertEquals("Should not be mgr.", mgr, r.getEntityManager());
    }

    /**
     * Function test : Tests <code>getValidUsersForClient(Client client)</code> method for
     * accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetValidUsersForClientAccuracy() throws Exception {
        Client client = new Client();
        client.setId(1);
        List<Long> ids = retriever.getValidUsersForClient(client);
        assertEquals("Should be 3.", 3, ids.size());
        assertTrue("getValidUsersForClient is incorrect.", ids.contains(130L));
        assertTrue("getValidUsersForClient is incorrect.", ids.contains(131L));
        assertTrue("getValidUsersForClient is incorrect.", ids.contains(132L));
    }

    /**
     * Function test : Tests <code>getValidUsersForProject(Project project)</code> method for
     * accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetValidUsersForProjectAccuracy() throws Exception {
        Project project = new Project();
        project.setId(1);
        List<Long> ids = retriever.getValidUsersForProject(project);

        assertEquals("Should be 3.", 3, ids.size());
        assertTrue("getValidUsersForProject is incorrect.", ids.contains(230L));
        assertTrue("getValidUsersForProject is incorrect.", ids.contains(231L));
        assertTrue("getValidUsersForProject is incorrect.", ids.contains(232L));
    }

    /**
     * Function test : Tests <code>getClientsForUserId(long userId)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetClientsForUserIdAccuracy() throws Exception {
        List<Client> clients = retriever.getClientsForUserId(333L);
        assertEquals("Should be 4.", 4, clients.size());
        checkIds(clients, 2L, 3L, 4L, 5L);
    }

    /**
     * Function test : Tests <code>getProjectsForUserId(long userId)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetProjectsForUserIdAccuracy() throws Exception {
        long userId = 444L;
        List<Project> projects = retriever.getProjectsForUserId(userId);
        assertEquals("Should be 3.", 3, projects.size());
        checkIds(projects, 22L, 33L, 44L);
    }

    /**
     * Dummy class extends from JPAUserMappingRetriever.
     */
    final class DummyJPAUserMappingRetriever extends JPAUserMappingRetriever {
        /**
         * <p>
         * Getter for 'entityManager' property. Protected because it is used by subclasses only.
         * </p>
         * 
         * @return the value of the 'entityManager' property. It can be any value.
         */
        public EntityManager getEntityManager() {
            return super.getEntityManager();
        }
    }
}