/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.InitialContext;

import org.hibernate.Session;

import com.topcoder.controlpanel.clientassociations.dao.hibernate.CompClient;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.CompClientPK;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.HibernateHelper;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.UserClient;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.UserClientPK;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientAssociationServiceInContainerUnitTests extends DBTestCase {

    /**
     * <p>
     * Represents the <code>ClientAssociationServiceRemote</code> instance that related to the Stateless
     * Session Bean.
     * </p>
     */
    private ClientAssociationServiceRemote clientAssociationService;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ClientAssociationServiceInContainerUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // create a new context
        InitialContext ctx = new InitialContext();
        // look up the session bean
        clientAssociationService = (ClientAssociationServiceRemote) ctx
                .lookup("ClientAssociationServiceStatelessSessionBean");
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        clientAssociationService = null;

        super.tearDown();
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#assignComponent(long, int)}</code>
     * method.
     * </p>
     * <p>
     * If the dependent client does exist, record should be inserted.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAssignComponent_accuracy() throws Exception {
        clientAssociationService.assignComponent(1, 1);

        Session session = HibernateHelper.getSessionFactory().openSession();
        CompClient comClient = (CompClient) session.load(CompClient.class, new CompClientPK(1l, 1));
        assertNotNull("The relation does not exist in database.", comClient);
        assertEquals("Incorrect client name.", "ivern", comClient.getClient().getName());
        session.close();
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#assignUser(long, int, boolean)}</code>
     * method.
     * </p>
     * <p>
     * The association should be created.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAssingUser_accuracy() throws Exception {
        clientAssociationService.assignUser(1, 1, true);

        // verification
        Session session = HibernateHelper.getSessionFactory().openSession();
        UserClient userClient = (UserClient) session.load(UserClient.class, new UserClientPK(1l, 1));
        assertNotNull("The relation does not exist in database.", userClient);
        assertEquals("Incorrect client name.", "ivern", userClient.getClient().getName());
        assertEquals("Incorrect admin Ind.", new Integer(1), userClient.getAdminInd());

        session.close();
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#removeComponent(long, int)}</code>
     * method.
     * </p>
     * <p>
     * The association should be removed.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveComponent_accuracy() throws Exception {
        // add association
        saveEntity(new CompClient(new CompClientPK(1l, 1)));

        // execute
        clientAssociationService.removeComponent(1l, 1);

        // verification
        Session session = HibernateHelper.getSessionFactory().openSession();
        CompClient comClient = (CompClient) session.get(CompClient.class, new CompClientPK(1l, 1));
        assertNull("The relation should not exist in database.", comClient);

        session.close();
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#removeUser(long, int)}</code> method.
     * </p>
     * <p>
     * The association should be removed.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveUser_accuracy() throws Exception {
        // add association
        saveEntity(new UserClient(new UserClientPK(1l, 1)));

        // execute
        clientAssociationService.removeUser(1l, 1);

        // verification
        Session session = HibernateHelper.getSessionFactory().openSession();
        UserClient userClient = (UserClient) session.get(UserClient.class, new UserClientPK(1l, 1));
        assertNull("The relation should not exist in database.", userClient);

        session.close();
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getComponentsByClient(int)}</code>
     * method.
     * </p>
     * <p>
     * Should return an empty list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetComponentsByClient_empty() throws Exception {
        List<Long> ids = clientAssociationService.getComponentsByClient(1);

        assertNotNull("The ids list should not null.", ids);
        assertTrue("The list should be empty.", ids.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getComponentsByClient(int)}</code>
     * method.
     * </p>
     * <p>
     * Should return an exact list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetComponentsByClient_accuracy() throws Exception {
        int count = 5;
        Set<Long> expectedIds = new HashSet<Long>();
        for (long i = 1; i <= count; i++) {
            expectedIds.add(i);
            saveEntity(new CompClient(new CompClientPK(i, 1)));
        }

        saveEntity(new CompClient(new CompClientPK(1l, 2)));

        List<Long> ids = clientAssociationService.getComponentsByClient(1);

        assertNotNull("The ids list should not null.", ids);
        assertEquals("The list should contains five elements.", count, ids.size());

        for (Iterator<Long> iter = ids.iterator(); iter.hasNext();) {
            assertTrue("The list does not contain valid ids.", expectedIds.remove(iter.next()));
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getComponentsByUser(long)}</code>
     * method.
     * </p>
     * <p>
     * Should return an empty list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetComponentsByUser_empty() throws Exception {
        List<Long> ids = clientAssociationService.getComponentsByUser(1);

        assertNotNull("The ids list should not null.", ids);
        assertTrue("The list should be empty.", ids.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getComponentsByUser(int)}</code>
     * method.
     * </p>
     * <p>
     * Should return an exact list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetComponentsByUser_accuracy() throws Exception {
        int count = 5;
        Set<Long> expectedIds = new HashSet<Long>();
        for (long i = 1; i <= count; i++) {
            expectedIds.add(i);
            saveEntity(new CompClient(new CompClientPK(i, 1)));
        }

        saveEntity(new CompClient(new CompClientPK(1l, 2)));

        saveEntity(new UserClient(new UserClientPK(1l, 1)));

        List<Long> ids = clientAssociationService.getComponentsByClient(1);

        assertNotNull("The ids list should not null.", ids);
        assertEquals("The list should contains five elements.", count, ids.size());

        for (Iterator<Long> iter = ids.iterator(); iter.hasNext();) {
            assertTrue("The list does not contain valid ids.", expectedIds.remove(iter.next()));
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getComponentsByUser(int)}</code>
     * method.
     * </p>
     * <p>
     * Should return an exact list that does not contains duplicated id..
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetComponentsByUser_noDuplicateId() throws Exception {
        int count = 5;
        Set<Long> expectedIds = new HashSet<Long>();
        for (long i = 1; i <= count; i++) {
            expectedIds.add(i);
            saveEntity(new CompClient(new CompClientPK(i, 1)));
        }

        saveEntity(new CompClient(new CompClientPK(1l, 2)));

        List<Long> ids = clientAssociationService.getComponentsByClient(1);

        assertNotNull("The ids list should not null.", ids);
        assertEquals("The list should contains five elements.", count, ids.size());

        for (Iterator<Long> iter = ids.iterator(); iter.hasNext();) {
            assertTrue("The list does not contain valid ids.", expectedIds.remove(iter.next()));
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getUsers(int)}</code> method.
     * </p>
     * <p>
     * Should return an empty list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUsers_empty() throws Exception {
        List<Long> ids = clientAssociationService.getUsers(1);

        assertNotNull("The ids list should not null.", ids);
        assertTrue("The list should be empty.", ids.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getUsers(int)}</code> method.
     * </p>
     * <p>
     * Should return an exact list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUsers_accuracy() throws Exception {
        int count = 5;
        Set<Long> expectedIds = new HashSet<Long>();
        for (long i = 1; i <= count; i++) {
            expectedIds.add(i);
            saveEntity(new UserClient(new UserClientPK(i, 1)));
        }

        saveEntity(new UserClient(new UserClientPK(6l, 2)));

        List<Long> ids = clientAssociationService.getUsers(1);

        assertNotNull("The ids list should not null.", ids);
        assertEquals("The list should contains five elements.", count, ids.size());

        for (Iterator<Long> iter = ids.iterator(); iter.hasNext();) {
            assertTrue("The list does not contain valid ids.", expectedIds.remove(iter.next()));
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#isAdminUser(long, int)}</code> method.
     * </p>
     * <p>
     * Should return true, as the user is an admin.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testIsAdminUser_accuracy() throws Exception {
        UserClient userClient = new UserClient(new UserClientPK(1l, 1));
        userClient.setAdminInd(1);
        saveEntity(userClient);

        assertTrue("Should return true, as the user is an admin.", clientAssociationService.isAdminUser(1l, 1));
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getClientsByComponent(long)}</code>
     * method.
     * </p>
     * <p>
     * If there are no component and client association, should return empty list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetClientsByComponent_empty() throws Exception {
        List<Integer> componentIds = clientAssociationService.getClientsByComponent(1);

        assertNotNull("The ids list should not null.", componentIds);
        assertTrue("The list should be empty.", componentIds.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getClientsByComponent(long)}</code>
     * method.
     * </p>
     * <p>
     * Should return the associated client with the specified component.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetClientsByComponent_accuracy() throws Exception {
        int count = 5;
        Set<Long> expectedIds = new HashSet<Long>();
        for (long i = 1; i <= count; i++) {
            expectedIds.add(i);
            saveEntity(new CompClient(new CompClientPK(i, 1)));
        }

        saveEntity(new CompClient(new CompClientPK(1l, 2)));

        List<Integer> ids = clientAssociationService.getClientsByComponent(1);

        assertNotNull("The ids list should not null.", ids);
        assertEquals("The list should contains two elements.", 2, ids.size());

        assertTrue("The client id - 1 does not exist.", ids.contains(new Integer(1)));
        assertTrue("The client id - 2 does not exist.", ids.contains(new Integer(2)));
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getClientsByUser(long)}</code> method.
     * </p>
     * <p>
     * If there are no user and client association, should return empty list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetClientsByUser_empty() throws Exception {
        List<Integer> clientIds = clientAssociationService.getClientsByUser(1);

        assertNotNull("The ids list should not null.", clientIds);
        assertTrue("The list should be empty.", clientIds.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getClientsByUser(long)}</code> method.
     * </p>
     * <p>
     * Should return the associated client ids with the specified component.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetClientsByUser_accuracy() throws Exception {
        int count = 5;
        for (long i = 1; i <= count; i++) {
            saveEntity(new UserClient(new UserClientPK(i, 1)));
        }

        saveEntity(new UserClient(new UserClientPK(1l, 2)));

        List<Integer> ids = clientAssociationService.getClientsByUser(1);

        assertNotNull("The ids list should not null.", ids);
        assertEquals("The list should contains two elements.", 2, ids.size());

        assertTrue("The client id - 1 does not exist.", ids.contains(new Integer(1)));
        assertTrue("The client id - 2 does not exist.", ids.contains(new Integer(2)));
    }
}
