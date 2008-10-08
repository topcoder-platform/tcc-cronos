/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;

import junit.framework.TestCase;

import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.webservices.ClientStatusServiceException;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.security.facade.BaseUserProfile;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit test for ClientStatusServiceBean class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientStatusServiceBeanTest extends TestCase {
    /**
     * <p>
     * Bean instance used in tests.
     * </p>
     */
    private ClientStatusServiceBean bean;

    /**
     * <p>
     * Sets up the environment for the TestCase.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    protected void setUp() throws Exception {
        bean = new ClientStatusServiceBean();
        bean.setVerboseLogging(true);

        // Initialize required resources
        this.setField("clientManagerFile", "config.properties");
        this.setField("clientManagerNamespace", "ClientStatusServiceBean");
        Method method = ClientStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        // Initialize SessionContext
        SessionContextMock context = new SessionContextMock();
        Principal principal = new UserProfilePrincipal(new BaseUserProfile(), 1, "testUser");
        SessionContextMock.setPrincipal(principal);
        this.setField("sessionContext", context);

        // Set default manager behavior
        ClientManagerMock.setFail(false);
    }

    /**
     * <p>
     * Clears the environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    protected void tearDown() throws Exception {
        bean = null;
    }

    /**
     * <p>
     * Accuracy test for <code>ClientStatusServiceBean()</code> constructor.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testClientStatusServiceBean_Accuracy() throws Exception {
        assertNotNull("ClientStatusServiceBean should be created.", new ClientStatusServiceBean());
    }

    /**
     * <p>
     * Accuracy test for <code>initialize()</code> method with valid manager configuration and absent log name
     * resource.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Accuracy1() throws Exception {
        this.setField("clientManagerFile", "config.properties");
        this.setField("clientManagerNamespace", "ClientStatusServiceBean");
        Method method = ClientStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        assertNotNull("The ClientManager should be created.", this.getField("clientManager"));
        assertNotNull("The Log should be created.", this.getField("log"));
    }

    /**
     * <p>
     * Accuracy test for <code>initialize()</code> method with valid manager configuration and log name
     * resource.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Accuracy2() throws Exception {
        this.setField("clientManagerFile", "config.properties");
        this.setField("clientManagerNamespace", "ClientStatusServiceBean");
        this.setField("logName", "ClientStatusServiceBean");
        Method method = ClientStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        assertNotNull("The ClientManager should be created.", this.getField("clientManager"));
        assertNotNull("The Log should be created.", this.getField("log"));
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code> method.
     * </p>
     * <p>
     * Expect: ClientStatusServiceBeanConfigurationException
     * </p>
     * <p>
     * Because of: if the required resources are missing.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Failure1() throws Exception {
        try {
            // Set empty resource
            this.setField("clientManagerFile", "");
            Method method = ClientStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
            method.setAccessible(true);
            method.invoke(bean, new Object[0]);
            fail("Expected ClientStatusServiceBeanConfigurationException if the required resources are missing.");
        } catch (InvocationTargetException e) {
            // expect
            assertTrue("ClientStatusServiceBeanConfigurationException should be thrown", e
                    .getTargetException() instanceof ClientStatusServiceBeanConfigurationException);
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code> method.
     * </p>
     * <p>
     * Expect: ClientStatusServiceBeanConfigurationException
     * </p>
     * <p>
     * Because of: if configuration namespace could not be found.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Failure2() throws Exception {
        try {
            this.setField("clientManagerFile", "broken.properties");
            this.setField("clientManagerNamespace", "ClientStatusServiceBean");
            Method method = ClientStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
            method.setAccessible(true);
            method.invoke(bean, new Object[0]);
            fail("Expected ClientStatusServiceBeanConfigurationException if configuration namespace"
                    + " could not be found.");
        } catch (InvocationTargetException e) {
            // expect
            assertTrue("ClientStatusServiceBeanConfigurationException should be thrown", e
                    .getTargetException() instanceof ClientStatusServiceBeanConfigurationException);
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code> method.
     * </p>
     * <p>
     * Expect: ClientStatusServiceBeanConfigurationException
     * </p>
     * <p>
     * Because of: if configuration file could not be found.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Failure3() throws Exception {
        try {
            this.setField("clientManagerFile", "absent_file");
            this.setField("clientManagerNamespace", "ClientStatusServiceBean");
            Method method = ClientStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
            method.setAccessible(true);
            method.invoke(bean, new Object[0]);
            fail("Expected ClientStatusServiceBeanConfigurationException if configuration file could not be found.");
        } catch (InvocationTargetException e) {
            // expect
            assertTrue("ClientStatusServiceBeanConfigurationException should be thrown", e
                    .getTargetException() instanceof ClientStatusServiceBeanConfigurationException);
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code> method.
     * </p>
     * <p>
     * Expect: ClientStatusServiceBeanConfigurationException
     * </p>
     * <p>
     * Because of: if manager couldn't be instantiated.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Failure4() throws Exception {
        try {
            this.setField("clientManagerFile", "config.properties");
            this.setField("clientManagerNamespace", "BrokenClientStatusServiceBean");
            Method method = ClientStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
            method.setAccessible(true);
            method.invoke(bean, new Object[0]);
            fail("Expected ClientStatusServiceBeanConfigurationException if manager couldn't be instantiated.");
        } catch (InvocationTargetException e) {
            // expect
            assertTrue("ClientStatusServiceBeanConfigurationException should be thrown", e
                    .getTargetException() instanceof ClientStatusServiceBeanConfigurationException);
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createClientStatus(ClientStatus status)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCreateClientStatus_Accuracy() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setDescription("Test entity");
        ClientStatus result = bean.createClientStatus(status);

        assertNotNull("ClientStatus object should be returned.", result);
        assertEquals("Status description should be " + status.getDescription(), status.getDescription(),
                result.getDescription());
    }

    /**
     * <p>
     * Failure test for <code>createClientStatus(ClientStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the client status is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCreateClientStatus_Failure1() throws Exception {
        try {
            bean.createClientStatus(null);
            fail("Expected IllegalArgumentException if the client status is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>createClientStatus(ClientStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the clientManager is not set (or is set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCreateClientStatus_Failure2() throws Exception {
        try {
            setField("clientManager", null);
            bean.createClientStatus(new ClientStatus());
            fail("Expected IllegalStateException if the clientManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>createClientStatus(ClientStatus status)</code> method.
     * </p>
     * <p>
     * Expect: ClientStatusServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCreateClientStatus_Failure3() throws Exception {
        try {
            ClientManagerMock.setFail(true);
            bean.createClientStatus(new ClientStatus());
            fail("Expected ClientStatusServiceException if error occurs during creating.");
        } catch (ClientStatusServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>updateClientStatus(ClientStatus status)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testUpdateClientStatus_Accuracy() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setDescription("Updated description");
        ClientStatus result = bean.updateClientStatus(status);

        assertNotNull("ClientStatus object should be returned.", result);
        assertEquals("Status description should be " + status.getDescription(), status.getDescription(),
                result.getDescription());
    }

    /**
     * <p>
     * Failure test for <code>updateClientStatus(ClientStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the client status is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testUpdateClientStatus_Failure1() throws Exception {
        try {
            bean.updateClientStatus(null);
            fail("Expected IllegalArgumentException if the client status is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>updateClientStatus(ClientStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the clientManager is not set (or is set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testUpdateClientStatus_Failure2() throws Exception {
        try {
            setField("clientManager", null);
            bean.updateClientStatus(new ClientStatus());
            fail("Expected IllegalStateException if the clientManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>updateClientStatus(ClientStatus status)</code> method.
     * </p>
     * <p>
     * Expect: ClientStatusServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testUpdateClientStatus_Failure3() throws Exception {
        try {
            ClientManagerMock.setFail(true);
            bean.updateClientStatus(new ClientStatus());
            fail("Expected ClientStatusServiceException if error occurs during updating.");
        } catch (ClientStatusServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>deleteClientStatus(ClientStatus status)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testDeleteClientStatus_Accuracy() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setDescription("To delete");
        ClientStatus result = bean.deleteClientStatus(status);

        assertNotNull("ClientStatus object should be returned.", result);
        assertEquals("Status description should be " + status.getDescription(), status.getDescription(),
                result.getDescription());
    }

    /**
     * <p>
     * Failure test for <code>deleteClientStatus(ClientStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the client status is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testDeleteClientStatus_Failure1() throws Exception {
        try {
            bean.deleteClientStatus(null);
            fail("Expected IllegalArgumentException if the client status is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>deleteClientStatus(ClientStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the clientManager is not set (or is set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testDeleteClientStatus_Failure2() throws Exception {
        try {
            setField("clientManager", null);
            bean.deleteClientStatus(new ClientStatus());
            fail("Expected IllegalStateException if the clientManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>deleteClientStatus(ClientStatus status)</code> method.
     * </p>
     * <p>
     * Expect: ClientStatusServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testDeleteClientStatus_Failure3() throws Exception {
        try {
            ClientManagerMock.setFail(true);
            bean.deleteClientStatus(new ClientStatus());
            fail("Expected ClientStatusServiceException if error occurs during deleting.");
        } catch (ClientStatusServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>isVerboseLogging()</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testIsVerboseLogging_Accuracy() throws Exception {
        assertTrue("verboseLogging should be true", bean.isVerboseLogging());
    }

    /**
     * <p>
     * Accuracy test for <code>setVerboseLogging(boolean verboseLogging)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSetVerboseLogging_Accuracy() throws Exception {
        bean.setVerboseLogging(false);
        assertFalse("verboseLogging should be false", bean.isVerboseLogging());
    }

    /**
     * <p>
     * Accuracy test for <code>getLog()</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetLog_Accuracy() throws Exception {
        assertNotNull("Logger field shouldn't be null.", bean.getLog());
    }

    /**
     * <p>
     * Accuracy test for <code>setLog(Log log)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSetLog_Accuracy() throws Exception {
        Log log = LogManager.getLog("TestLogger");
        bean.setLog(log);
        assertEquals("setLog returns invalid logger.", log, bean.getLog());
    }

    /**
     * <p>
     * Set the value into private field.
     * </p>
     *
     * @param fieldName
     *            The name of field.
     * @param fieldValue
     *            The field value to be set.
     * @throws Exception
     *             to JUnit
     */
    private void setField(String fieldName, Object fieldValue) throws Exception {
        Field field = ClientStatusServiceBean.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this.bean, fieldValue);
    }

    /**
     * <p>
     * Get the value from private field.
     * </p>
     *
     * @param fieldName
     *            The name of field.
     * @return the value of the field.
     * @throws Exception
     *             to JUnit
     */
    private Object getField(String fieldName) throws Exception {
        Field field = ClientStatusServiceBean.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(this.bean);
    }

}
