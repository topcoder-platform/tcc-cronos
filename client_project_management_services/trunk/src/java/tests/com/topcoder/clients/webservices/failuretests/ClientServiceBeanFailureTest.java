/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.webservices.ClientServiceException;
import com.topcoder.clients.webservices.beans.AuthorizationFailedException;
import com.topcoder.clients.webservices.beans.ClientServiceBean;
import com.topcoder.clients.webservices.beans.ClientServiceBeanConfigurationException;
import com.topcoder.clients.webservices.beans.Roles;
import com.topcoder.clients.webservices.failuretests.mock.MockClientManager;
import com.topcoder.clients.webservices.failuretests.mock.MockUserMappingRetriever;

import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.security.facade.BaseUserProfile;

import junit.framework.TestCase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.security.Principal;


/**
 * This is a test case for <code>ClientServiceBean</code>.
 *
 * @author PE
 * @version 1.0
 */
public class ClientServiceBeanFailureTest extends TestCase {
    /** Represents an instance of ClientServiceBean. */
    private ClientServiceBean bean;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        bean = new ClientServiceBean();

        FailureTestHelper.setField(bean, "clientManagerFile", "failure/config.properties");
        FailureTestHelper.setField(bean, "clientManagerNamespace", "ClientServiceBean");
        FailureTestHelper.setField(bean, "userMappingRetrieverFile", "failure/config.properties");
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", "UserMappingRetriever");
        FailureTestHelper.setField(bean, "administratorRole", Roles.ADMIN);
        FailureTestHelper.setField(bean, "clientAndProjectUserRole", Roles.USER);

        initialize();

        // Initialize SessionContext
        SessionContextMock context = new SessionContextMock();
        Principal principal = new UserProfilePrincipal(new BaseUserProfile(), 1, "testUser");
        SessionContextMock.setPrincipal(principal);
        SessionContextMock.setRoles(new String[] { Roles.ADMIN, Roles.USER });
        FailureTestHelper.setField(bean, "sessionContext", context);

        // Set default managers behavior
        MockUserMappingRetriever.setFail(false);
        MockClientManager.throwCME(false);
        MockClientManager.throwIAE(false);
    }

    /**
     * <p>
     * initialize.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void initialize() throws Exception {
        Method method = ClientServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);
    }

    /**
     * <p>
     * Test for <code>initialize</code> method.
     * </p>
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig1() throws Throwable {
        FailureTestHelper.setField(bean, "clientManagerFile", null);

        checkClientServiceBeanConfigurationException();
    }

    /**
     * <p>
     * Checks the ClientServiceBeanConfigurationException for invalid configuration.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    private void checkClientServiceBeanConfigurationException()
        throws Throwable {
        try {
            try {
                initialize();
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }

            fail("ClientServiceBeanConfigurationException should be thrown.");
        } catch (ClientServiceBeanConfigurationException ex) {
            // success
        }
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig2() throws Throwable {
        FailureTestHelper.setField(bean, "clientManagerFile", " ");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig3() throws Throwable {
        FailureTestHelper.setField(bean, "clientManagerFile", "invalid");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig4() throws Throwable {
        FailureTestHelper.setField(bean, "clientManagerNamespace", null);

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig5() throws Throwable {
        FailureTestHelper.setField(bean, "clientManagerNamespace", " ");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig6() throws Throwable {
        FailureTestHelper.setField(bean, "clientManagerNamespace", "invalid");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig7() throws Throwable {
        FailureTestHelper.setField(bean, "clientManagerNamespace", "InvalidClientServiceBean1");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig8() throws Throwable {
        FailureTestHelper.setField(bean, "clientManagerNamespace", "InvalidClientServiceBean2");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig9() throws Throwable {
        FailureTestHelper.setField(bean, "clientManagerNamespace", "InvalidClientServiceBean3");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig10() throws Throwable {
        FailureTestHelper.setField(bean, "clientManagerNamespace", "InvalidClientServiceBean4");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * <p>
     * Test for <code>initialize</code> method.
     * </p>
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig11() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverFile", null);

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig12() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverFile", " ");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig13() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverFile", "invalid");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig14() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", null);

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig15() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", " ");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig16() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", "invalid");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig17() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", "InvalidUserMappingRetriever1");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig18() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", "InvalidUserMappingRetriever2");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig19() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", "InvalidUserMappingRetriever3");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ClientServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig20() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", "InvalidUserMappingRetriever4");

        checkClientServiceBeanConfigurationException();
    }

    /**
     * Test for <code>createClient</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateClient_InvalidArguments1() throws Throwable {
        try {
            bean.createClient(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>createClient</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateClient_InvalidStates1() throws Throwable {
        FailureTestHelper.setField(bean, "clientManager", null);

        try {
            bean.createClient(new Client());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>createClient</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateClient_InvalidStates2() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetriever", null);

        try {
            bean.createClient(new Client());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>createClient</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateClient_InvalidAuthorizations1()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        FailureTestHelper.setField(bean, "clientAndProjectUserRole", "");

        try {
            bean.createClient(new Client());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>createClient</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ClientServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateClient_Errors1() throws Throwable {
        MockClientManager.throwIAE(true);

        try {
            bean.createClient(new Client());
            fail("ClientServiceException should be thrown.");
        } catch (ClientServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>createClient</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ClientServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateClient_Errors2() throws Throwable {
        MockClientManager.throwCME(true);

        try {
            bean.createClient(new Client());
            fail("ClientServiceException should be thrown.");
        } catch (ClientServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateClient</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateClient_InvalidArguments1() throws Throwable {
        try {
            bean.updateClient(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateClient</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateClient_InvalidStates1() throws Throwable {
        FailureTestHelper.setField(bean, "clientManager", null);

        try {
            bean.updateClient(new Client());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateClient</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateClient_InvalidStates2() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetriever", null);

        try {
            bean.updateClient(new Client());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateClient</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateClient_InvalidAuthorizations1()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        FailureTestHelper.setField(bean, "clientAndProjectUserRole", "");

        try {
            bean.updateClient(new Client());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateClient</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateClient_InvalidAuthorizations2()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");

        try {
            bean.updateClient(new Client());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateClient</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateClient_InvalidAuthorizations3()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        MockUserMappingRetriever.setFail(true);

        try {
            bean.updateClient(new Client());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateClient</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ClientServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateClient_Errors1() throws Throwable {
        MockClientManager.throwIAE(true);

        try {
            bean.updateClient(new Client());
            fail("ClientServiceException should be thrown.");
        } catch (ClientServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateClient</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ClientServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateClient_Errors2() throws Throwable {
        MockClientManager.throwCME(true);

        try {
            bean.updateClient(new Client());
            fail("ClientServiceException should be thrown.");
        } catch (ClientServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteClient</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteClient_InvalidArguments1() throws Throwable {
        try {
            bean.deleteClient(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteClient</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteClient_InvalidStates1() throws Throwable {
        FailureTestHelper.setField(bean, "clientManager", null);

        try {
            bean.deleteClient(new Client());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteClient</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteClient_InvalidStates2() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetriever", null);

        try {
            bean.deleteClient(new Client());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteClient</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteClient_InvalidAuthorizations1()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        FailureTestHelper.setField(bean, "clientAndProjectUserRole", "");

        try {
            bean.deleteClient(new Client());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteClient</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteClient_InvalidAuthorizations2()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");

        try {
            bean.deleteClient(new Client());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteClient</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteClient_InvalidAuthorizations3()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        MockUserMappingRetriever.setFail(true);

        try {
            bean.deleteClient(new Client());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteClient</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ClientServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteClient_Errors1() throws Throwable {
        MockClientManager.throwIAE(true);

        try {
            bean.deleteClient(new Client());
            fail("ClientServiceException should be thrown.");
        } catch (ClientServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteClient</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ClientServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteClient_Errors2() throws Throwable {
        MockClientManager.throwCME(true);

        try {
            bean.deleteClient(new Client());
            fail("ClientServiceException should be thrown.");
        } catch (ClientServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientCodeName</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientCodeName_InvalidArguments1()
        throws Throwable {
        try {
            bean.setClientCodeName(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientCodeName</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientCodeName_InvalidArguments2()
        throws Throwable {
        try {
            bean.setClientCodeName(new Client(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientCodeName</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientCodeName_InvalidArguments3()
        throws Throwable {
        try {
            bean.setClientCodeName(new Client(), " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientCodeName</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientCodeName_InvalidStates1()
        throws Throwable {
        FailureTestHelper.setField(bean, "clientManager", null);

        try {
            bean.setClientCodeName(new Client(), "name");
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientCodeName</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientCodeName_InvalidStates2()
        throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetriever", null);

        try {
            bean.setClientCodeName(new Client(), "name");
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientCodeName</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientCodeName_InvalidAuthorizations1()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        FailureTestHelper.setField(bean, "clientAndProjectUserRole", "");

        try {
            bean.setClientCodeName(new Client(), "name");
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientCodeName</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientCodeName_InvalidAuthorizations2()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");

        try {
            bean.setClientCodeName(new Client(), "name");
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientCodeName</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientCodeName_InvalidAuthorizations3()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        MockUserMappingRetriever.setFail(true);

        try {
            bean.setClientCodeName(new Client(), "name");
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientCodeName</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ClientServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientCodeName_Errors1() throws Throwable {
        MockClientManager.throwIAE(true);

        try {
            bean.setClientCodeName(new Client(), "name");
            fail("ClientServiceException should be thrown.");
        } catch (ClientServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientCodeName</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ClientServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientCodeName_Errors2() throws Throwable {
        MockClientManager.throwCME(true);

        try {
            bean.setClientCodeName(new Client(), "name");
            fail("ClientServiceException should be thrown.");
        } catch (ClientServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientStatus_InvalidArguments1()
        throws Throwable {
        try {
            bean.setClientStatus(null, new ClientStatus());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientStatus_InvalidArguments2()
        throws Throwable {
        try {
            bean.setClientStatus(new Client(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientStatus_InvalidStates1() throws Throwable {
        FailureTestHelper.setField(bean, "clientManager", null);

        try {
            bean.setClientStatus(new Client(), new ClientStatus());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientStatus_InvalidStates2() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetriever", null);

        try {
            bean.setClientStatus(new Client(), new ClientStatus());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientStatus_InvalidAuthorizations1()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        FailureTestHelper.setField(bean, "clientAndProjectUserRole", "");

        try {
            bean.setClientStatus(new Client(), new ClientStatus());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientStatus_InvalidAuthorizations2()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");

        try {
            bean.setClientStatus(new Client(), new ClientStatus());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientStatus_InvalidAuthorizations3()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        MockUserMappingRetriever.setFail(true);

        try {
            bean.setClientStatus(new Client(), new ClientStatus());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientStatus</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ClientServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientStatus_Errors1() throws Throwable {
        MockClientManager.throwIAE(true);

        try {
            bean.setClientStatus(new Client(), new ClientStatus());
            fail("ClientServiceException should be thrown.");
        } catch (ClientServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>setClientStatus</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ClientServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetClientStatus_Errors2() throws Throwable {
        MockClientManager.throwCME(true);

        try {
            bean.setClientStatus(new Client(), new ClientStatus());
            fail("ClientServiceException should be thrown.");
        } catch (ClientServiceException ex) {
            // success
        }
    }
}
