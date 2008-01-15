/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.failuretests;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.transaction.UserTransaction;

import com.topcoder.controlpanel.clientassociations.dao.ClientAssociationDAOException;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.ClientAssociationHibernateDAO;

import junit.framework.TestCase;

/**
 * This is a failure test case for <code>ClientAssociationHibernateDAO</code> class.
 * @author moonli
 * @version 1.0
 */
public class ClientAssociationHibernateDAOTest extends TestCase {

    /**
     * Represents an instance of <code>ClientAssociationHibernateDAO</code> class.
     */
    private ClientAssociationHibernateDAO dao;

    /**
     * Represents an instance of UserTransaction.
     */
    private UserTransaction transaction;

    /**
     * Sets up the test environment.
     * @throws Exception
     *         to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        dao = new ClientAssociationHibernateDAO();

        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.NamingContextFactory");
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
        Context ctx = new InitialContext(props);
        Object ref = ctx.lookup("UserTransaction");

        // UserTransaction transaction = (UserTransaction) ref;
        transaction = (UserTransaction) PortableRemoteObject.narrow(ref, UserTransaction.class);
    }

    /**
     * Tears down the test environment.
     * @throws Exception
     *         to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test for <code>assignComponent</code> method.
     * <p>
     * Tests it against component id and client id already assigned, expects
     * ClientAssociationDAOException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testAssignComponentWithDuplicateIds() throws Exception {
        transaction.begin();
        boolean error = false;

        try {
            dao.assignComponent(1999, 10000);
            fail("ClientAssociationDAOException should be thrown.");
        } catch (ClientAssociationDAOException ex) {
            // success
            error = true;
        } finally {
            if (error) {
                transaction.rollback();
            } else {
                transaction.commit();
            }
        }
    }

    /**
     * Test for <code>assignUser</code> method.
     * <p>
     * Tests it against duplicate ids, expects ClientAssociationDAOException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testAssignUserWithDuplicateIds() throws Exception {
        transaction.begin();
        boolean error = false;

        try {
            dao.assignUser(2000, 10000, true);
            fail("ClientAssociationDAOException should be thrown.");
        } catch (ClientAssociationDAOException ex) {
            // success
            error = true;
        } finally {
            if (error) {
                transaction.rollback();
            } else {
                transaction.commit();
            }
        }
    }

    /**
     * Test for <code>assignUser</code> method.
     * <p>
     * Tests it against unkown client id, expects ClientAssociationDAOException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testAssignUserWithUnknownClientId() throws Exception {
        transaction.begin();
        boolean error = false;

        try {
            dao.assignUser(2000, 10001, true);
            fail("ClientAssociationDAOException should be thrown.");
        } catch (ClientAssociationDAOException ex) {
            // success
            error = true;
        } finally {
            if (error) {
                transaction.rollback();
            } else {
                transaction.commit();
            }
        }
    }

    /**
     * Test for <code>getComponentsByClient</code> method.
     * @throws Exception
     *         to JUnit
     */
    public void testGetComponentsByClient1() throws Exception {
        transaction.begin();

        List<Long> comps = dao.getComponentsByClient(10000);

        assertEquals("There should be one component.", 1, comps.size());
        assertEquals("Component id mismatched.", new Long(1999), comps.get(0));

        comps = dao.getComponentsByClient(10001);
        assertTrue("There should be no component.", comps.isEmpty());

        transaction.commit();
    }

    /**
     * Test for <code>getComponentsByUser</code> method.
     * @throws Exception
     *         to JUnit
     */
    public void testGetComponentsByUser() throws Exception {
        transaction.begin();

        List<Long> comps = dao.getComponentsByUser(2000);
        assertEquals("There should be one component.", 1, comps.size());
        assertEquals("Component id mismatched.", new Long(1999), comps.get(0));

        comps = dao.getComponentsByUser(1999);
        assertTrue("There should be no component.", comps.isEmpty());

        transaction.commit();
    }

    /**
     * Test for <code>getUsers</code> method.
     * @throws Exception
     *         to JUnit
     */
    public void testGetUsers() throws Exception {
        transaction.begin();

        List<Long> users = dao.getUsers(10000);
        assertEquals("There should be one component.", 1, users.size());
        assertEquals("Component id mismatched.", new Long(2000), users.get(0));

        users = dao.getComponentsByUser(10001);
        assertTrue("There should be no component.", users.isEmpty());

        transaction.commit();
    }

    /**
     * Test for <code>isAdminUser</code> method.
     * <p>
     * Tests it against unknown ids, expects ClientAssociationDAOException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testIsAdminUser() throws Exception {
        transaction.begin();
        boolean error = false;

        try {
            dao.isAdminUser(2000, 10001);
            fail("ClientAssociationDAOException should be thrown.");
        } catch (ClientAssociationDAOException ex) {
            // success
            error = true;
        } finally {
            if (error) {
                transaction.rollback();
            } else {
                transaction.commit();
            }
        }
    }

    /**
     * Test for <code>getClientsByComponent</code> method.
     * @throws Exception
     *         to JUnit
     */
    public void testGetClientsByComponent() throws Exception {
        transaction.begin();

        List<Integer> clients = dao.getClientsByComponent(1999);
        assertEquals("There should be one component.", 1, clients.size());
        assertEquals("Component id mismatched.", 10000, clients.get(0).intValue());

        clients = dao.getClientsByComponent(19991);
        assertTrue("There should be no component.", clients.isEmpty());

        transaction.commit();
    }

    /**
     * Test for <code>getClientsByUser</code> method.
     * @throws Exception
     *         to JUnit
     */
    public void testGetClientsByUser() throws Exception {
        transaction.begin();

        List<Integer> clients = dao.getClientsByUser(2000);
        assertEquals("There should be one component.", 1, clients.size());
        assertEquals("Component id mismatched.", 10000, clients.get(0).intValue());

        clients = dao.getClientsByUser(1000000);
        assertTrue("There should be no component.", clients.isEmpty());

        transaction.commit();
    }
}
