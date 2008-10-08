/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.webservices.usermapping.impl.JPAUserMappingRetriever;

import junit.framework.TestCase;


/**
 * This is a test case for <code>JPAUserMappingRetriever</code>.
 *
 * @author PE
 * @version 1.0
 */
public class JPAUserMappingRetrieverFailureTest extends TestCase {
    /** Represents an instance of JPAUserMappingRetriever. */
    private JPAUserMappingRetriever retriever;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        retriever = new JPAUserMappingRetriever(FailureTestHelper.getEntityManager());
    }

    /**
     * Test for <code>getValidUsersForClient</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testGetValidUsersForClient_InvalidArguments1()
        throws Throwable {
        try {
            retriever.getValidUsersForClient(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>getValidUsersForClient</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testGetValidUsersForClient_InvalidStates1()
        throws Throwable {
        retriever.setEntityManager(null);

        try {
            retriever.getValidUsersForClient(new Client());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>getValidUsersForProject</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testGetValidUsersForProject_InvalidArguments1()
        throws Throwable {
        try {
            retriever.getValidUsersForProject(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>getValidUsersForProject</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testGetValidUsersForProject_InvalidStates1()
        throws Throwable {
        retriever.setEntityManager(null);

        try {
            retriever.getValidUsersForProject(new Project());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>getClientsForUserId</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testGetClientsForUserId_InvalidArguments1()
        throws Throwable {
        try {
            retriever.getClientsForUserId(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>getClientsForUserId</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testGetClientsForUserId_InvalidStates1()
        throws Throwable {
        retriever.setEntityManager(null);

        try {
            retriever.getClientsForUserId(1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>getProjectsForUserId</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testGetProjectsForUserId_InvalidArguments1()
        throws Throwable {
        try {
            retriever.getProjectsForUserId(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>getProjectsForUserId</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testGetProjectsForUserId_InvalidStates1()
        throws Throwable {
        retriever.setEntityManager(null);

        try {
            retriever.getProjectsForUserId(1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }
}
