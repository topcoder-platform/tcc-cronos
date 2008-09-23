/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations;

import java.lang.reflect.Field;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test the exception rethrow of <code>ClientAssociationServiceStatelessSessionBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientAssociationServiceStatelessSessionBeanFailureTests extends TestCase {

    /**
     * <p>
     * Represents the <code>ClientAssociationServiceStatelessSessionBean</code> instance that related to the Stateless
     * Session Bean.
     * </p>
     */
    private ClientAssociationServiceStatelessSessionBean clientAssociationService;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ClientAssociationServiceStatelessSessionBeanFailureTests.class);
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
        clientAssociationService = new ClientAssociationServiceStatelessSessionBean();

        setFieldValue(clientAssociationService, "dao", new MockClientAssociationDAO());
    }

    /**
     * <p>
     * Sets the field value of an object.
     * </p>
     *
     * @param obj
     *            the object where to get the field value.
     * @param fieldName
     *            the name of the field.
     * @param value
     *            the value to set.
     * @return the field value
     * @throws Exception
     *             any exception occurs.
     */
    private static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#assignComponent(long, int)}</code>
     * method.
     * </p>
     * <p>
     * If the dao throw exception, the bean just rethrow it.
     * </p>
     */
    public void testAssignComponent_ClientAssociationServiceException() {
        try {
            clientAssociationService.assignComponent(1, 1);
            fail("Expected ClientAssociationServiceException");
        } catch (ClientAssociationServiceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#assignUser(long, int, boolean)}</code>
     * method.
     * </p>
     * <p>
     * If the dao throw exception, the bean just rethrow it.
     * </p>
     */
    public void testAssignUser_ClientAssociationServiceException() {
        try {
            clientAssociationService.assignUser(1, 1, true);
            fail("Expected ClientAssociationServiceException");
        } catch (ClientAssociationServiceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#removeComponent(long, int)}</code>
     * method.
     * </p>
     * <p>
     * If the dao throw exception, the bean just rethrow it.
     * </p>
     */
    public void testRemoveComponent_ClientAssociationServiceException() {
        try {
            clientAssociationService.removeComponent(1, 1);
            fail("Expected ClientAssociationServiceException");
        } catch (ClientAssociationServiceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#removeUser(long, int)}</code> method.
     * </p>
     * <p>
     * If the dao throw exception, the bean just rethrow it.
     * </p>
     */
    public void testRemoveUser_ClientAssociationServiceException() {
        try {
            clientAssociationService.removeUser(1, 1);
            fail("Expected ClientAssociationServiceException");
        } catch (ClientAssociationServiceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getComponentsByClient(int)}</code>
     * method.
     * </p>
     * <p>
     * If the dao throw exception, the bean just rethrow it.
     * </p>
     */
    public void testGetComponentsByClient_ClientAssociationServiceException() {
        try {
            clientAssociationService.getComponentsByClient(1);
            fail("Expected ClientAssociationServiceException");
        } catch (ClientAssociationServiceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getComponentsByUser(int)}</code>
     * method.
     * </p>
     * <p>
     * If the dao throw exception, the bean just rethrow it.
     * </p>
     */
    public void testGetComponentsByUser_ClientAssociationServiceException() {
        try {
            clientAssociationService.getComponentsByUser(1);
            fail("Expected ClientAssociationServiceException");
        } catch (ClientAssociationServiceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getUsers(int)}</code> method.
     * </p>
     * <p>
     * If the dao throw exception, the bean just rethrow it.
     * </p>
     */
    public void testGetUsers_ClientAssociationServiceException() {
        try {
            clientAssociationService.getUsers(1);
            fail("Expected ClientAssociationServiceException");
        } catch (ClientAssociationServiceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#isAdminUser(long, int)}</code> method.
     * </p>
     * <p>
     * If the dao throw exception, the bean just rethrow it.
     * </p>
     */
    public void testIsAdminUser_ClientAssociationServiceException() {
        try {
            clientAssociationService.isAdminUser(1, 1);
            fail("Expected ClientAssociationServiceException");
        } catch (ClientAssociationServiceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getClientsByComponent(long)}</code>
     * method.
     * </p>
     * <p>
     * If the dao throw exception, the bean just rethrow it.
     * </p>
     */
    public void testGetClientsByComponent_ClientAssociationServiceException() {
        try {
            clientAssociationService.getClientsByComponent(1);
            fail("Expected ClientAssociationServiceException");
        } catch (ClientAssociationServiceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ClientAssociationServiceStatelessSessionBean#getClientsByUser(long)}</code> method.
     * </p>
     * <p>
     * If the dao throw exception, the bean just rethrow it.
     * </p>
     */
    public void testGetClientsByUser_ClientAssociationServiceException() {
        try {
            clientAssociationService.getClientsByUser(1);
            fail("Expected ClientAssociationServiceException");
        } catch (ClientAssociationServiceException e) {
            // expected
        }
    }
}
