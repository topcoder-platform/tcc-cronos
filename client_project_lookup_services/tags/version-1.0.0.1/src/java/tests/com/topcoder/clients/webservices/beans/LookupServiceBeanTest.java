/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.LookupServiceException;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.security.facade.BaseUserProfile;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit test for LookupServiceBean class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LookupServiceBeanTest extends TestCase {
    /**
     * <p>
     * Bean instance used in tests.
     * </p>
     */
    private LookupServiceBean bean;

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    protected void setUp() throws Exception {
        bean = new LookupServiceBean();
        bean.setVerboseLogging(true);

        // Initialize required resources
        this.setField("clientManagerFile", "config.properties");
        this.setField("clientManagerNamespace", "LookupServiceBean");
        this.setField("companyManagerFile", "config.properties");
        this.setField("companyManagerNamespace", "LookupServiceBean");
        this.setField("projectManagerFile", "config.properties");
        this.setField("projectManagerNamespace", "LookupServiceBean");
        this.setField("userMappingRetrieverFile", "config.properties");
        this.setField("userMappingRetrieverNamespace", "LookupServiceBean");
        this.setField("clientAndProjectUserRole", Roles.USER);
        Method method = LookupServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        // Initialize SessionContext
        SessionContextMock context = new SessionContextMock();
        Principal principal = new UserProfilePrincipal(new BaseUserProfile(), 1, "testUser");
        SessionContextMock.setPrincipal(principal);
        SessionContextMock.setRoles(new String[]{Roles.USER });
        this.setField("sessionContext", context);

        // Set default managers behavior
        ClientManagerMock.setFail(false);
        CompanyManagerMock.setFail(false);
        ProjectManagerMock.setFail(false);
    }

    /**
     * Clears the environment.
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    protected void tearDown() throws Exception {
        bean = null;
    }

    /**
     * <p>
     * Accuracy test for <code>LookupServiceBean()</code> constructor.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testLookupServiceBean_Accuracy() throws Exception {
        assertNotNull("LookupServiceBean should be created.", new LookupServiceBean());
    }

    /**
     * <p>
     * Accuracy test for <code>initialize()</code> method with valid configuration and absent log name
     * resource.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Accuracy1() throws Exception {
        // Initialize required resources
        this.setField("clientManagerFile", "config.properties");
        this.setField("clientManagerNamespace", "LookupServiceBean");
        this.setField("companyManagerFile", "config.properties");
        this.setField("companyManagerNamespace", "LookupServiceBean");
        this.setField("projectManagerFile", "config.properties");
        this.setField("projectManagerNamespace", "LookupServiceBean");
        this.setField("userMappingRetrieverFile", "config.properties");
        this.setField("userMappingRetrieverNamespace", "LookupServiceBean");
        Method method = LookupServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
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
        // Initialize required resources
        this.setField("clientManagerFile", "config.properties");
        this.setField("clientManagerNamespace", "LookupServiceBean");
        this.setField("companyManagerFile", "config.properties");
        this.setField("companyManagerNamespace", "LookupServiceBean");
        this.setField("projectManagerFile", "config.properties");
        this.setField("projectManagerNamespace", "LookupServiceBean");
        this.setField("userMappingRetrieverFile", "config.properties");
        this.setField("userMappingRetrieverNamespace", "LookupServiceBean");
        this.setField("logName", "LookupServiceBean");
        Method method = LookupServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
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
     * Expect: LookupServiceBeanConfigurationException
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
            Method method = LookupServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
            method.setAccessible(true);
            method.invoke(bean, new Object[0]);
            fail("Expected LookupServiceBeanConfigurationException if the required resources are missing.");
        } catch (InvocationTargetException e) {
            // expect
            assertTrue("LookupServiceBeanConfigurationException should be thrown",
                    e.getTargetException() instanceof LookupServiceBeanConfigurationException);
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceBeanConfigurationException
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
            this.setField("clientManagerNamespace", "LookupServiceBean");
            Method method = LookupServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
            method.setAccessible(true);
            method.invoke(bean, new Object[0]);
            fail("Expected LookupServiceBeanConfigurationException if configuration namespace"
                    + " could not be found.");
        } catch (InvocationTargetException e) {
            // expect
            assertTrue("LookupServiceBeanConfigurationException should be thrown",
                    e.getTargetException() instanceof LookupServiceBeanConfigurationException);
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceBeanConfigurationException
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
            this.setField("clientManagerNamespace", "LookupServiceBean");
            Method method = LookupServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
            method.setAccessible(true);
            method.invoke(bean, new Object[0]);
            fail("Expected LookupServiceBeanConfigurationException if configuration file could not be found.");
        } catch (InvocationTargetException e) {
            // expect
            assertTrue("LookupServiceBeanConfigurationException should be thrown",
                    e.getTargetException() instanceof LookupServiceBeanConfigurationException);
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceBeanConfigurationException
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
            this.setField("clientManagerNamespace", "BrokenLookupServiceBean");
            Method method = LookupServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
            method.setAccessible(true);
            method.invoke(bean, new Object[0]);
            fail("Expected LookupServiceBeanConfigurationException if manager couldn't be instantiated.");
        } catch (InvocationTargetException e) {
            // expect
            assertTrue("LookupServiceBeanConfigurationException should be thrown",
                    e.getTargetException() instanceof LookupServiceBeanConfigurationException);
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceBeanConfigurationException
     * </p>
     * <p>
     * Because of: if any of {administratorRole, clientAndProjectUserRole} resources are present.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Failure6() throws Exception {
        try {
            // Set roles to nulls
            this.setField("administratorRole", null);
            this.setField("clientAndProjectUserRole", null);
            this.setField("clientManagerFile", "config.properties");
            this.setField("clientManagerNamespace", "LookupServiceBean");
            Method method = LookupServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
            method.setAccessible(true);
            method.invoke(bean, new Object[0]);
            fail("Expected LookupServiceBeanConfigurationException if any of {administratorRole, "
                    + "clientAndProjectUserRole} resources are present.");
        } catch (InvocationTargetException e) {
            // expect
            assertTrue("LookupServiceBeanConfigurationException should be thrown",
                    e.getTargetException() instanceof LookupServiceBeanConfigurationException);
        }
    }

    /**
     * <p>
     * Accuracy test for <code>retrieveClient(long id)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveClient_Accuracy1() throws Exception {
        long id = 42;
        Client result = bean.retrieveClient(id);

        assertNotNull("Client object should be returned.", result);
        assertEquals("Client id should be " + id, id, result.getId());
    }

    /**
     * <p>
     * Accuracy test for <code>retrieveClient(long id)</code> method when caller is an administrator.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveClient_Accuracy2() throws Exception {

        // Return client invisible for users
        ClientManagerMock.setReturnRestricted(true);
        // Make caller administrator
        SessionContextMock.setRoles(new String[]{Roles.ADMIN });
        setField("administratorRole", Roles.ADMIN);

        long id = 42;
        Client result = bean.retrieveClient(id);

        assertNotNull("Client object should be returned.", result);
        assertEquals("Client id should be " + id, id, result.getId());
    }

    /**
     * <p>
     * Failure test for <code>retrieveClient(long id)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the id <= 0.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveClient_Failure1() throws Exception {
        try {
            bean.retrieveClient(-1);
            fail("Expected IllegalArgumentException if the id <= 0.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveClient(long id)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the clientManager is not set.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveClient_Failure2() throws Exception {
        try {
            setField("clientManager", null);
            bean.retrieveClient(17);
            fail("Expected IllegalStateException if the clientManager = null.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveClient(long id)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveClient_Failure3() throws Exception {
        try {
            ClientManagerMock.setReturnRestricted(true);
            bean.retrieveClient(17);
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        } finally {
            ClientManagerMock.setReturnRestricted(false);
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveClient(long id)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveClient_Failure4() throws Exception {
        try {
            ClientManagerMock.setFail(true);
            bean.retrieveClient(5);
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>retrieveAllClients()</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveAllClients_Accuracy() throws Exception {

        // Prepare clients which will be returned
        Client c1 = new Client();
        Client c2 = new Client();
        c1.setId(5);
        c2.setId(7);
        List < Client > clients = new ArrayList < Client >();
        clients.add(c1);
        clients.add(c2);
        UserMappingRetrieverMock.setClients(clients);

        List < Client > userResult = bean.retrieveAllClients();
        // Make caller administrator
        SessionContextMock.setRoles(new String[]{Roles.ADMIN });
        setField("administratorRole", Roles.ADMIN);
        List < Client > adminResult = bean.retrieveAllClients();

        assertTrue("Entities returned to user is invalid.", Arrays.equals(clients.toArray(), userResult
                .toArray()));
        assertEquals("Admin should see one more entity.", userResult.size() + 1, adminResult.size());
        assertTrue("Entities returned to admin is invalid.", Arrays.equals(clients.toArray(), adminResult
                .subList(0, clients.size()).toArray()));
    }

    /**
     * <p>
     * Failure test for <code>retrieveAllClients()</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the userMappingRetriever is not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveAllClients_Failure1() throws Exception {
        try {
            setField("userMappingRetriever", null);
            bean.retrieveAllClients();
            fail("Expected IllegalStateException if the userMappingRetriever are not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveAllClients()</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveAllClients_Failure2() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.retrieveAllClients();
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveAllClients()</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveAllClients_Failure3() throws Exception {
        try {
            ClientManagerMock.setFail(true);
            bean.retrieveAllClients();
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>searchClientsByName(String name)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchClientsByName_Accuracy() throws Exception {
        // Prepare clients which will be returned
        Client c1 = new Client();
        Client c2 = new Client();
        c1.setId(5);
        c1.setName("Jack");
        c2.setId(7);
        c2.setName("Jack");
        List < Client > clients = new ArrayList < Client >();
        clients.add(c1);
        clients.add(c2);
        UserMappingRetrieverMock.setClients(clients);

        List < Client > userResult = bean.searchClientsByName("Jack");
        // Make caller administrator
        SessionContextMock.setRoles(new String[]{Roles.ADMIN });
        setField("administratorRole", Roles.ADMIN);
        List < Client > adminResult = bean.retrieveAllClients();

        assertTrue("Entities returned to user is invalid.", Arrays.equals(clients.toArray(), userResult
                .toArray()));
        assertEquals("Admin should see one more entity.", userResult.size() + 1, adminResult.size());
        assertTrue("Entities returned to admin is invalid.", Arrays.equals(clients.toArray(), adminResult
                .subList(0, clients.size()).toArray()));
    }

    /**
     * <p>
     * Failure test for <code>searchClientsByName(String name)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the name is empty string.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchClientsByName_Failure1() throws Exception {
        try {
            bean.searchClientsByName("   ");
            fail("Expected IllegalArgumentException if the name is empty string.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchClientsByName(String name)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the clientManager is not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchClientsByName_Failure2() throws Exception {
        try {
            setField("clientManager", null);
            bean.searchClientsByName("Jack");
            fail("Expected IllegalStateException if the clientManager = null.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchClientsByName(String name)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchClientsByName_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.searchClientsByName("Jack");
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchClientsByName(String name)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchClientsByName_Failure4() throws Exception {
        try {
            ClientManagerMock.setFail(true);
            bean.searchClientsByName("Jack");
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>searchClients(Filter filter)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchClients_Accuracy() throws Exception {
        // Prepare clients which will be returned
        Client c1 = new Client();
        Client c2 = new Client();
        c1.setId(5);
        c1.setName("Jack");
        c2.setId(7);
        c2.setName("Jack");
        List < Client > clients = new ArrayList < Client >();
        clients.add(c1);
        clients.add(c2);
        UserMappingRetrieverMock.setClients(clients);

        List < Client > userResult = bean.searchClients(new NullFilter("test"));
        // Make caller administrator
        SessionContextMock.setRoles(new String[]{Roles.ADMIN });
        setField("administratorRole", Roles.ADMIN);
        List < Client > adminResult = bean.searchClients(new NullFilter("test"));

        assertTrue("Entities returned to user is invalid.", Arrays.equals(clients.toArray(), userResult
                .toArray()));
        assertEquals("Admin should see one more entity.", userResult.size() + 1, adminResult.size());
        assertTrue("Entities returned to admin is invalid.", Arrays.equals(clients.toArray(), adminResult
                .subList(0, clients.size()).toArray()));
    }

    /**
     * <p>
     * Failure test for <code>searchClients(Filter filter)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the filter is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchClients_Failure1() throws Exception {
        try {
            bean.searchClients(null);
            fail("Expected IllegalArgumentException if the filter is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchClients(Filter filter)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the clientManager is not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchClients_Failure2() throws Exception {
        try {
            setField("clientManager", null);
            bean.searchClients(new NullFilter("test"));
            fail("Expected IllegalStateException if the clientManager = null.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchClients(Filter filter)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchClients_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.searchClients(new NullFilter("test"));
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchClients(Filter filter)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchClients_Failure4() throws Exception {
        try {
            ClientManagerMock.setFail(true);
            bean.searchClients(new NullFilter("test"));
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getClientStatus(long id)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientStatus_Accuracy() throws Exception {
        long id = 42;
        ClientStatus result = bean.getClientStatus(id);

        assertNotNull("Client object should be returned.", result);
        assertEquals("Client id should be " + id, id, result.getId());
    }

    /**
     * <p>
     * Failure test for <code>getClientStatus(long id)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the id <= 0.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientStatus_Failure1() throws Exception {
        try {
            bean.getClientStatus(-1);
            fail("Expected IllegalArgumentException if the id <= 0.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientStatus(long id)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the clientManager is not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientStatus_Failure2() throws Exception {
        try {
            setField("clientManager", null);
            bean.getClientStatus(5);
            fail("Expected IllegalStateException if the clientManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientStatus(long id)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientStatus_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.getClientStatus(5);
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientStatus(long id)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientStatus_Failure4() throws Exception {
        try {
            ClientManagerMock.setFail(true);
            bean.getClientStatus(5);
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getAllClientStatuses()</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetAllClientStatuses_Accuracy() throws Exception {
        List < ClientStatus > statuses = bean.getAllClientStatuses();

        assertNotNull("Returnet list shouldn't be null.", statuses);
        assertTrue("Returnet list shouldn't be empty.", statuses.size() > 0);
    }

    /**
     * <p>
     * Failure test for <code>getAllClientStatuses()</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the clientManager is not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetAllClientStatuses_Failure1() throws Exception {
        try {
            setField("clientManager", null);
            bean.getAllClientStatuses();
            fail("Expected IllegalStateException if the clientManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getAllClientStatuses()</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetAllClientStatuses_Failure2() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.getAllClientStatuses();
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getAllClientStatuses()</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetAllClientStatuses_Failure3() throws Exception {
        try {
            ClientManagerMock.setFail(true);
            bean.getAllClientStatuses();
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getClientsForStatus(ClientStatus status)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientsForStatus_Accuracy() throws Exception {
        // Prepare clients which will be returned
        Client c1 = new Client();
        Client c2 = new Client();
        c1.setId(5);
        c1.setName("Jack");
        c2.setId(7);
        c2.setName("Nick");
        List < Client > clients = new ArrayList < Client >();
        clients.add(c1);
        clients.add(c2);
        UserMappingRetrieverMock.setClients(clients);

        List < Client > userResult = bean.getClientsForStatus(new ClientStatus());
        // Make caller administrator
        SessionContextMock.setRoles(new String[]{Roles.ADMIN });
        setField("administratorRole", Roles.ADMIN);
        List < Client > adminResult = bean.getClientsForStatus(new ClientStatus());

        assertTrue("Entities returned to user is invalid.", Arrays.equals(clients.toArray(), userResult
                .toArray()));
        assertEquals("Admin should see one more entity.", userResult.size() + 1, adminResult.size());
        assertTrue("Entities returned to admin is invalid.", Arrays.equals(clients.toArray(), adminResult
                .subList(0, clients.size()).toArray()));
    }

    /**
     * <p>
     * Failure test for <code>getClientsForStatus(ClientStatus status)</code> method.
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
    public void testGetClientsForStatus_Failure1() throws Exception {
        try {
            bean.getClientsForStatus(null);
            fail("Expected IllegalArgumentException if the client status is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientsForStatus(ClientStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the userMappingRetriever is not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientsForStatus_Failure2() throws Exception {
        try {
            setField("userMappingRetriever", null);
            bean.getClientsForStatus(new ClientStatus());
            fail("Expected IllegalStateException if the userMappingRetriever is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientsForStatus(ClientStatus status)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientsForStatus_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.getClientsForStatus(new ClientStatus());
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientsForStatus(ClientStatus status)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientsForStatus_Failure4() throws Exception {
        try {
            ClientManagerMock.setFail(true);
            bean.getClientsForStatus(new ClientStatus());
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>retrieveProject(long id)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveProject_Accuracy1() throws Exception {
        long id = 42;
        Project result = bean.retrieveProject(id);

        assertNotNull("Project object should be returned.", result);
        assertEquals("Project id should be " + id, id, result.getId());
    }

    /**
     * <p>
     * Accuracy test for <code>retrieveProject(long id)</code> method when caller is an administrator.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveProject_Accuracy2() throws Exception {

        // Return project invisible for users
        ProjectManagerMock.setReturnRestricted(true);
        // Make caller administrator
        SessionContextMock.setRoles(new String[]{Roles.ADMIN });
        setField("administratorRole", Roles.ADMIN);

        long id = 42;
        Project result = bean.retrieveProject(id);

        assertNotNull("Project object should be returned.", result);
        assertEquals("Project id should be " + id, id, result.getId());
    }

    /**
     * <p>
     * Failure test for <code>retrieveProject(long id)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the id <= 0.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveProject_Failure1() throws Exception {
        try {
            bean.retrieveProject(-5);
            fail("Expected IllegalArgumentException if the id <= 0.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveProject(long id)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the projectManager is not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveProject_Failure2() throws Exception {
        try {
            setField("projectManager", null);
            bean.retrieveProject(5);
            fail("Expected IllegalStateException if the projectManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveProject(long id)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveProject_Failure3() throws Exception {
        try {
            ProjectManagerMock.setReturnRestricted(true);
            bean.retrieveProject(5);
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        } finally {
            ProjectManagerMock.setReturnRestricted(false);
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveProject(long id)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveProject_Failure4() throws Exception {
        try {
            ProjectManagerMock.setFail(true);
            bean.retrieveProject(5);
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>retrieveProjectsForClient(Client client)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveProjectsForClient_Accuracy() throws Exception {

        // Prepare projects which will be returned
        Project p1 = new Project();
        Project p2 = new Project();
        p1.setId(5);
        p2.setId(7);
        List < Project > projects = new ArrayList < Project >();
        projects.add(p1);
        projects.add(p2);
        UserMappingRetrieverMock.setProjects(projects);

        List < Project > userResult = bean.retrieveProjectsForClient(new Client());
        // Make caller administrator
        SessionContextMock.setRoles(new String[]{Roles.ADMIN });
        setField("administratorRole", Roles.ADMIN);
        List < Project > adminResult = bean.retrieveProjectsForClient(new Client());

        assertTrue("Entities returned to user is invalid.", Arrays.equals(projects.toArray(), userResult
                .toArray()));
        assertEquals("Admin should see one more entity.", userResult.size() + 1, adminResult.size());
        assertTrue("Entities returned to admin is invalid.", Arrays.equals(projects.toArray(), adminResult
                .subList(0, projects.size()).toArray()));
    }

    /**
     * <p>
     * Failure test for <code>retrieveProjectsForClient(Client client)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the client is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveProjectsForClient_Failure1() throws Exception {
        try {
            bean.retrieveProjectsForClient(null);
            fail("Expected IllegalArgumentException if the client is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveProjectsForClient(Client client)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveProjectsForClient_Failure2() throws Exception {
        try {
            setField("projectManager", null);
            bean.retrieveProjectsForClient(new Client());
            fail("Expected IllegalStateException if the projectManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveProjectsForClient(Client client)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveProjectsForClient_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.retrieveProjectsForClient(new Client());
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveProjectsForClient(Client client)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveProjectsForClient_Failure4() throws Exception {
        try {
            ProjectManagerMock.setFail(true);
            bean.retrieveProjectsForClient(new Client());
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>retrieveAllProjects()</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveAllProjects_Accuracy() throws Exception {

        // Prepare projects which will be returned
        Project p1 = new Project();
        Project p2 = new Project();
        p1.setId(5);
        p2.setId(7);
        List < Project > projects = new ArrayList < Project >();
        projects.add(p1);
        projects.add(p2);
        UserMappingRetrieverMock.setProjects(projects);

        List < Project > userResult = bean.retrieveAllProjects();
        // Make caller administrator
        SessionContextMock.setRoles(new String[]{Roles.ADMIN });
        setField("administratorRole", Roles.ADMIN);
        List < Project > adminResult = bean.retrieveAllProjects();

        assertTrue("Entities returned to user is invalid.", Arrays.equals(projects.toArray(), userResult
                .toArray()));
        assertEquals("Admin should see one more entity.", userResult.size() + 1, adminResult.size());
        assertTrue("Entities returned to admin is invalid.", Arrays.equals(projects.toArray(), adminResult
                .subList(0, projects.size()).toArray()));
    }

    /**
     * <p>
     * Failure test for <code>retrieveAllProjects()</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveAllProjects_Failure1() throws Exception {
        try {
            setField("projectManager", null);
            bean.retrieveAllProjects();
            fail("Expected IllegalStateException if the projectManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveAllProjects()</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveAllProjects_Failure2() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.retrieveAllProjects();
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveAllProjects()</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveAllProjects_Failure3() throws Exception {
        try {
            ProjectManagerMock.setFail(true);
            bean.retrieveAllProjects();
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>searchProjectsByName(String name)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchProjectsByName_Accuracy() throws Exception {

        // Prepare projects which will be returned
        Project p1 = new Project();
        Project p2 = new Project();
        p1.setId(5);
        p2.setId(7);
        List < Project > projects = new ArrayList < Project >();
        projects.add(p1);
        projects.add(p2);
        UserMappingRetrieverMock.setProjects(projects);

        List < Project > userResult = bean.searchProjectsByName("test");
        // Make caller administrator
        SessionContextMock.setRoles(new String[]{Roles.ADMIN });
        setField("administratorRole", Roles.ADMIN);
        List < Project > adminResult = bean.searchProjectsByName("test");

        assertTrue("Entities returned to user is invalid.", Arrays.equals(projects.toArray(), userResult
                .toArray()));
        assertEquals("Admin should see one more entity.", userResult.size() + 1, adminResult.size());
        assertTrue("Entities returned to admin is invalid.", Arrays.equals(projects.toArray(), adminResult
                .subList(0, projects.size()).toArray()));
    }

    /**
     * <p>
     * Failure test for <code>searchProjectsByName(String name)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the name is empty string.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchProjectsByName_Failure1() throws Exception {
        try {
            bean.searchProjectsByName("");
            fail("Expected IllegalArgumentException if the name is empty string.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchProjectsByName(String name)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchProjectsByName_Failure2() throws Exception {
        try {
            setField("projectManager", null);
            bean.searchProjectsByName("test");
            fail("Expected IllegalStateException if the projectManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchProjectsByName(String name)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchProjectsByName_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.searchProjectsByName("test");
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchProjectsByName(String name)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchProjectsByName_Failure4() throws Exception {
        try {
            ProjectManagerMock.setFail(true);
            bean.searchProjectsByName("test");
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>searchProjects(Filter filter)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchProjects_Accuracy() throws Exception {

        // Prepare projects which will be returned
        Project p1 = new Project();
        Project p2 = new Project();
        p1.setId(5);
        p2.setId(7);
        List < Project > projects = new ArrayList < Project >();
        projects.add(p1);
        projects.add(p2);
        UserMappingRetrieverMock.setProjects(projects);

        List < Project > userResult = bean.searchProjects(new NullFilter("test"));
        // Make caller administrator
        SessionContextMock.setRoles(new String[]{Roles.ADMIN });
        setField("administratorRole", Roles.ADMIN);
        List < Project > adminResult = bean.searchProjects(new NullFilter("test"));

        assertTrue("Entities returned to user is invalid.", Arrays.equals(projects.toArray(), userResult
                .toArray()));
        assertEquals("Admin should see one more entity.", userResult.size() + 1, adminResult.size());
        assertTrue("Entities returned to admin is invalid.", Arrays.equals(projects.toArray(), adminResult
                .subList(0, projects.size()).toArray()));
    }

    /**
     * <p>
     * Failure test for <code>searchProjects(Filter filter)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the filter is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchProjects_Failure1() throws Exception {
        try {
            bean.searchProjects(null);
            fail("Expected IllegalArgumentException if the filter is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchProjects(Filter filter)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchProjects_Failure2() throws Exception {
        try {
            setField("projectManager", null);
            bean.searchProjects(new NullFilter("test"));
            fail("Expected IllegalStateException if the projectManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchProjects(Filter filter)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchProjects_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.searchProjects(new NullFilter("test"));
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchProjects(Filter filter)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchProjects_Failure4() throws Exception {
        try {
            ProjectManagerMock.setFail(true);
            bean.searchProjects(new NullFilter("test"));
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getProjectStatus(long id)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectStatus_Accuracy() throws Exception {
        long id = 42;
        ProjectStatus result = bean.getProjectStatus(id);

        assertNotNull("Client object should be returned.", result);
        assertEquals("Client id should be " + id, id, result.getId());
    }

    /**
     * <p>
     * Failure test for <code>getProjectStatus(long id)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the id <= 0.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectStatus_Failure1() throws Exception {
        try {
            bean.getProjectStatus(-7);
            fail("Expected IllegalArgumentException if the id <= 0.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getProjectStatus(long id)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectStatus_Failure2() throws Exception {
        try {
            setField("projectManager", null);
            bean.getProjectStatus(1);
            fail("Expected IllegalStateException if the projectManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getProjectStatus(long id)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectStatus_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.getProjectStatus(1);
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getProjectStatus(long id)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectStatus_Failure4() throws Exception {
        try {
            ProjectManagerMock.setFail(true);
            bean.getProjectStatus(1);
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getAllProjectStatuses()</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetAllProjectStatuses_Accuracy() throws Exception {
        List < ProjectStatus > statuses = bean.getAllProjectStatuses();

        assertNotNull("Returnet list shouldn't be null.", statuses);
        assertTrue("Returnet list shouldn't be empty.", statuses.size() > 0);
    }

    /**
     * <p>
     * Failure test for <code>getAllProjectStatuses()</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetAllProjectStatuses_Failure1() throws Exception {
        try {
            setField("projectManager", null);
            bean.getAllProjectStatuses();
            fail("Expected IllegalStateException if the projectManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getAllProjectStatuses()</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetAllProjectStatuses_Failure2() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.getAllProjectStatuses();
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getAllProjectStatuses()</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetAllProjectStatuses_Failure3() throws Exception {
        try {
            ProjectManagerMock.setFail(true);
            bean.getAllProjectStatuses();
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getProjectsForStatus(ProjectStatus status)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectsForStatus_Accuracy() throws Exception {
        // Prepare projects which will be returned
        Project p1 = new Project();
        Project p2 = new Project();
        p1.setId(5);
        p2.setId(7);
        List < Project > projects = new ArrayList < Project >();
        projects.add(p1);
        projects.add(p2);
        UserMappingRetrieverMock.setProjects(projects);

        List < Project > userResult = bean.getProjectsForStatus(new ProjectStatus());
        // Make caller administrator
        SessionContextMock.setRoles(new String[]{Roles.ADMIN });
        setField("administratorRole", Roles.ADMIN);
        List < Project > adminResult = bean.getProjectsForStatus(new ProjectStatus());

        assertTrue("Entities returned to user is invalid.", Arrays.equals(projects.toArray(), userResult
                .toArray()));
        assertEquals("Admin should see one more entity.", userResult.size() + 1, adminResult.size());
        assertTrue("Entities returned to admin is invalid.", Arrays.equals(projects.toArray(), adminResult
                .subList(0, projects.size()).toArray()));
    }

    /**
     * <p>
     * Failure test for <code>getProjectsForStatus(ProjectStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the project status is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectsForStatus_Failure1() throws Exception {
        try {
            bean.getProjectsForStatus(null);
            fail("Expected IllegalArgumentException if the project status is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getProjectsForStatus(ProjectStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectsForStatus_Failure2() throws Exception {
        try {
            setField("projectManager", null);
            bean.getProjectsForStatus(new ProjectStatus());
            fail("Expected IllegalStateException if the projectManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getProjectsForStatus(ProjectStatus status)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectsForStatus_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.getProjectsForStatus(new ProjectStatus());
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getProjectsForStatus(ProjectStatus status)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectsForStatus_Failure4() throws Exception {
        try {
            ProjectManagerMock.setFail(true);
            bean.getProjectsForStatus(new ProjectStatus());
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>retrieveCompany(long id)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveCompany_Accuracy() throws Exception {
        long id = 42;
        Company result = bean.retrieveCompany(id);

        assertNotNull("Company object should be returned.", result);
        assertEquals("Company id should be " + id, id, result.getId());
    }

    /**
     * <p>
     * Failure test for <code>retrieveCompany(long id)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the id <= 0.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveCompany_Failure1() throws Exception {
        try {
            bean.retrieveCompany(-1);
            fail("Expected IllegalArgumentException if the id <= 0.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveCompany(long id)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the companyManager or userMappingRetriever are not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveCompany_Failure2() throws Exception {
        try {
            setField("companyManager", null);
            bean.retrieveCompany(5);
            fail("Expected IllegalStateException if the companyManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveCompany(long id)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveCompany_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.retrieveCompany(5);
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveCompany(long id)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveCompany_Failure4() throws Exception {
        try {
            CompanyManagerMock.setFail(true);
            bean.retrieveCompany(5);
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>retrieveAllCompanies()</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveAllCompanies_Accuracy() throws Exception {
        List < Company > companies = bean.retrieveAllCompanies();

        assertNotNull("Returnet list shouldn't be null.", companies);
        assertTrue("Returnet list shouldn't be empty.", companies.size() > 0);
    }

    /**
     * <p>
     * Failure test for <code>retrieveAllCompanies()</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the companyManager or userMappingRetriever are not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveAllCompanies_Failure1() throws Exception {
        try {
            setField("companyManager", null);
            bean.retrieveAllCompanies();
            fail("Expected IllegalStateException if the companyManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveAllCompanies()</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveAllCompanies_Failure2() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.retrieveAllCompanies();
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>retrieveAllCompanies()</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testRetrieveAllCompanies_Failure3() throws Exception {
        try {
            CompanyManagerMock.setFail(true);
            bean.retrieveAllCompanies();
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>searchCompaniesByName(String name)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchCompaniesByName_Accuracy() throws Exception {
        List < Company > companies = bean.searchCompaniesByName("test");

        assertNotNull("Returnet list shouldn't be null.", companies);
        assertTrue("Returnet list shouldn't be empty.", companies.size() > 0);
    }

    /**
     * <p>
     * Failure test for <code>searchCompaniesByName(String name)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the name is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchCompaniesByName_Failure1() throws Exception {
        try {
            bean.searchCompaniesByName(null);
            fail("Expected IllegalArgumentException if the name is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchCompaniesByName(String name)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the companyManager or userMappingRetriever are not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchCompaniesByName_Failure2() throws Exception {
        try {
            setField("companyManager", null);
            bean.searchCompaniesByName("test");
            fail("Expected IllegalStateException if the companyManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchCompaniesByName(String name)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchCompaniesByName_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.searchCompaniesByName("test");
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchCompaniesByName(String name)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchCompaniesByName_Failure4() throws Exception {
        try {
            CompanyManagerMock.setFail(true);
            bean.searchCompaniesByName("test");
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>searchCompanies(Filter filter)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchCompanies_Accuracy() throws Exception {
        List < Company > companies = bean.searchCompanies(new NullFilter("test"));

        assertNotNull("Returnet list shouldn't be null.", companies);
        assertTrue("Returnet list shouldn't be empty.", companies.size() > 0);
    }

    /**
     * <p>
     * Failure test for <code>searchCompanies(Filter filter)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the filter is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchCompanies_Failure1() throws Exception {
        try {
            bean.searchCompanies(null);
            fail("Expected IllegalArgumentException if the filter is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchCompanies(Filter filter)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the companyManager or userMappingRetriever are not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchCompanies_Failure2() throws Exception {
        try {
            setField("companyManager", null);
            bean.searchCompanies(new NullFilter("test"));
            fail("Expected IllegalStateException if the companyManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchCompanies(Filter filter)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchCompanies_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.searchCompanies(new NullFilter("test"));
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>searchCompanies(Filter filter)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSearchCompanies_Failure4() throws Exception {
        try {
            CompanyManagerMock.setFail(true);
            bean.searchCompanies(new NullFilter("test"));
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getClientsForCompany(Company company)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientsForCompany_Accuracy() throws Exception {
        // Prepare clients which will be returned
        Client c1 = new Client();
        Client c2 = new Client();
        c1.setId(5);
        c1.setName("Jack");
        c2.setId(7);
        c2.setName("Jack");
        List < Client > clients = new ArrayList < Client >();
        clients.add(c1);
        clients.add(c2);
        UserMappingRetrieverMock.setClients(clients);

        List < Client > userResult = bean.getClientsForCompany(new Company());
        // Make caller administrator
        SessionContextMock.setRoles(new String[]{Roles.ADMIN });
        setField("administratorRole", Roles.ADMIN);
        List < Client > adminResult = bean.getClientsForCompany(new Company());

        assertTrue("Entities returned to user is invalid.", Arrays.equals(clients.toArray(), userResult
                .toArray()));
        assertEquals("Admin should see one more entity.", userResult.size() + 1, adminResult.size());
        assertTrue("Entities returned to admin is invalid.", Arrays.equals(clients.toArray(), adminResult
                .subList(0, clients.size()).toArray()));
    }

    /**
     * <p>
     * Failure test for <code>getClientsForCompany(Company company)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the company is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientsForCompany_Failure1() throws Exception {
        try {
            bean.getClientsForCompany(null);
            fail("Expected IllegalArgumentException if the company is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientsForCompany(Company company)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the companyManager or userMappingRetriever are not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientsForCompany_Failure2() throws Exception {
        try {
            setField("companyManager", null);
            bean.getClientsForCompany(new Company());
            fail("Expected IllegalStateException if the companyManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientsForCompany(Company company)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientsForCompany_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.getClientsForCompany(new Company());
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientsForCompany(Company company)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetClientsForCompany_Failure4() throws Exception {
        try {
            CompanyManagerMock.setFail(true);
            bean.getClientsForCompany(new Company());
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getProjectsForCompany(Company company)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectsForCompany_Accuracy() throws Exception {

        // Prepare projects which will be returned
        Project p1 = new Project();
        Project p2 = new Project();
        p1.setId(5);
        p2.setId(7);
        List < Project > projects = new ArrayList < Project >();
        projects.add(p1);
        projects.add(p2);
        UserMappingRetrieverMock.setProjects(projects);

        List < Project > userResult = bean.getProjectsForCompany(new Company());
        // Make caller administrator
        SessionContextMock.setRoles(new String[]{Roles.ADMIN });
        setField("administratorRole", Roles.ADMIN);
        List < Project > adminResult = bean.getProjectsForCompany(new Company());

        assertTrue("Entities returned to user is invalid.", Arrays.equals(projects.toArray(), userResult
                .toArray()));
        assertEquals("Admin should see one more entity.", userResult.size() + 1, adminResult.size());
        assertTrue("Entities returned to admin is invalid.", Arrays.equals(projects.toArray(), adminResult
                .subList(0, projects.size()).toArray()));
    }

    /**
     * <p>
     * Failure test for <code>getProjectsForCompany(Company company)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the company is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectsForCompany_Failure1() throws Exception {
        try {
            bean.getProjectsForCompany(null);
            fail("Expected IllegalArgumentException if the company is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getProjectsForCompany(Company company)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the companyManager or userMappingRetriever are not set (or are set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectsForCompany_Failure2() throws Exception {
        try {
            setField("companyManager", null);
            bean.getProjectsForCompany(new Company());
            fail("Expected IllegalStateException if the companyManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getProjectsForCompany(Company company)</code> method.
     * </p>
     * <p>
     * Expect: AuthorizationFailedException
     * </p>
     * <p>
     * Because of: if the user is not authorized to perform the operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectsForCompany_Failure3() throws Exception {
        try {
            SessionContextMock.setRoles(new String[]{});
            bean.getProjectsForCompany(new Company());
            fail("Expected AuthorizationFailedException if the user is not authorized.");
        } catch (AuthorizationFailedException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>getProjectsForCompany(Company company)</code> method.
     * </p>
     * <p>
     * Expect: LookupServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetProjectsForCompany_Failure4() throws Exception {
        try {
            CompanyManagerMock.setFail(true);
            bean.getProjectsForCompany(new Company());
            fail("Expected LookupServiceException if error occurs in the manager.");
        } catch (LookupServiceException e) {
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
        Field field = LookupServiceBean.class.getDeclaredField(fieldName);
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
        Field field = LookupServiceBean.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(this.bean);
    }

}
