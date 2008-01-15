/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.failuretests;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.transaction.UserTransaction;

import com.topcoder.controlpanel.clientassociations.ClientAssociationServiceException;
import com.topcoder.controlpanel.clientassociations.ClientAssociationServiceStatelessSessionBean;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.ClientAssociationHibernateDAO;

import junit.framework.TestCase;

/**
 * This is a test case for <code>ClientAssociationServiceStatelessSessionBean</code> class.
 * @author moonli
 * @version 1.0
 */
public class ClientAssociationServiceStatelessSessionBeanTest extends TestCase {

    /**
     * Represents an instance of ClientAssociationServiceStatelessSessionBean.
     */
    private ClientAssociationServiceStatelessSessionBean bean;

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

        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.NamingContextFactory");
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
        Context ctx = new InitialContext(props);
        Object ref = ctx.lookup("UserTransaction");

        // UserTransaction transaction = (UserTransaction) ref;
        transaction = (UserTransaction) PortableRemoteObject.narrow(ref, UserTransaction.class);

        bean = new ClientAssociationServiceStatelessSessionBean();

        Field field = ClientAssociationServiceStatelessSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);
        field.set(bean, new ClientAssociationHibernateDAO());
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
     * ClientAssociationServiceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testAssignComponentWithDuplicateIds() throws Exception {
        transaction.begin();
        boolean error = false;

        try {
            bean.assignComponent(1999, 10000);
            fail("ClientAssociationServiceException should be thrown.");
        } catch (ClientAssociationServiceException ex) {
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
     * Tests it against duplicate ids, expects ClientAssociationServiceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testAssignUserWithDuplicateIds() throws Exception {
        transaction.begin();
        boolean error = false;

        try {
            bean.assignUser(2000, 10000, true);
            fail("ClientAssociationServiceException should be thrown.");
        } catch (ClientAssociationServiceException ex) {
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
     * Tests it against unkown client id, expects ClientAssociationServiceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testAssignUserWithUnknownClientId() throws Exception {
        transaction.begin();
        boolean error = false;

        try {
            bean.assignUser(2000, 10001, true);
            fail("ClientAssociationServiceException should be thrown.");
        } catch (ClientAssociationServiceException ex) {
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

        List<Long> comps = bean.getComponentsByClient(10000);

        assertEquals("There should be one component.", 1, comps.size());
        assertEquals("Component id mismatched.", new Long(1999), comps.get(0));

        comps = bean.getComponentsByClient(10001);
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

        List<Long> comps = bean.getComponentsByUser(2000);
        assertEquals("There should be one component.", 1, comps.size());
        assertEquals("Component id mismatched.", new Long(1999), comps.get(0));

        comps = bean.getComponentsByUser(1999);
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

        List<Long> users = bean.getUsers(10000);
        assertEquals("There should be one component.", 1, users.size());
        assertEquals("Component id mismatched.", new Long(2000), users.get(0));

        users = bean.getComponentsByUser(10001);
        assertTrue("There should be no component.", users.isEmpty());

        transaction.commit();
    }

    /**
     * Test for <code>isAdminUser</code> method.
     * <p>
     * Tests it against unknown ids, expects ClientAssociationServiceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testIsAdminUser() throws Exception {
        transaction.begin();
        boolean error = false;

        try {
            bean.isAdminUser(2000, 10001);
            fail("ClientAssociationServiceException should be thrown.");
        } catch (ClientAssociationServiceException ex) {
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

        List<Integer> clients = bean.getClientsByComponent(1999);
        assertEquals("There should be one component.", 1, clients.size());
        assertEquals("Component id mismatched.", 10000, clients.get(0).intValue());

        clients = bean.getClientsByComponent(19991);
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

        List<Integer> clients = bean.getClientsByUser(2000);
        assertEquals("There should be one component.", 1, clients.size());
        assertEquals("Component id mismatched.", 10000, clients.get(0).intValue());

        clients = bean.getClientsByUser(1000000);
        assertTrue("There should be no component.", clients.isEmpty());

        transaction.commit();
    }

}
