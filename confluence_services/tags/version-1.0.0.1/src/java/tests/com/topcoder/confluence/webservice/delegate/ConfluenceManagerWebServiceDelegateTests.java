/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.delegate;

import java.io.IOException;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationPersistenceException;
import com.topcoder.confluence.ConfluenceAuthenticationFailedException;
import com.topcoder.confluence.ConfluenceConfigurationException;
import com.topcoder.confluence.ConfluenceConnectionException;
import com.topcoder.confluence.ConfluenceManagerException;
import com.topcoder.confluence.ConfluenceNotAuthorizedException;
import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreatedAction;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceAuthenticationFailedException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceClientMock;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceConnectionException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceNotAuthorizedException;
import com.topcoder.confluence.webservice.ConfluenceManagerImplMock;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.SpecificationFactoryException;

/**
 * <p>
 * UnitTest cases of the <code>ConfluenceManagerWebServiceDelegate</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagerWebServiceDelegateTests extends TestCase {

    /**
     * <p>
     * Represents the <code>ConfluenceManagerWebServiceDelegate</code> instance used for test.
     * </p>
     */
    private ConfluenceManagerWebServiceDelegate delegate;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        delegate = new ConfluenceManagerWebServiceDelegate();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        delegate = null;
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor1_Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", delegate);
    }

    /**
     * <p>
     * Accuracy test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(String, String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagerWebServiceDelegate(
            ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
            ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE));
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(String, String)}.filename is
     * null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Failure1() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(null, ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(String, String)}.filename is
     * empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Failure2() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate("  ", ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(String, String)}.namespace
     * is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Failure3() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(String, String)}.namespace
     * is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Failure4() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH, "  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(String, String)}.Because the
     * configuration file-empty_delegate_config.xml is empty,so throw <code>ConfluenceConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Failure5() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                "empty.bean.config");
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(String, String)}.Because
     * configuration file(wrong_type_ConfluenceManagementServiceClient_delegate_config.xml) is not
     * ConfluenceManagementServiceClient type,so throw <code>ConfluenceConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Failure6() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                "wrong.type.ConfluenceManagementServiceClient");
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            assertTrue("Should be ClassCastException.", e.getCause() instanceof ClassCastException);
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(String, String)}.Because the
     * ConfluenceManagementServiceClient created from object factory will throw
     * <code>InvalidClassSpecificationException</code>,so throw <code>ConfluenceConfigurationException</code>
     * wraps InvalidClassSpecificationException from initialize method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Failure7() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                "InvalidClassSpecificationException.namespace");
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            assertTrue("Should be InvalidClassSpecificationException.",
                e.getCause() instanceof InvalidClassSpecificationException);
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(String, String)}.Because the
     * specification is not valid for configuration file(SpecificationFactoryException_delegate_config.xml),so
     * throw <code>ConfluenceConfigurationException</code> wraps SpecificationFactoryException from initialize
     * method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Failure8() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                "SpecificationFactoryException.namespace");
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            assertTrue("Should be SpecificationFactoryException.",
                e.getCause() instanceof SpecificationFactoryException);
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(String, String)}.Because the
     * configuration file is not recognized,so throw <code>ConfluenceConfigurationException</code> wraps
     * ConfigurationPersistenceException from initialize method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Failure9() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate("unrecognized",
                ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            assertTrue("Should be ConfigurationPersistenceException.",
                e.getCause() instanceof ConfigurationPersistenceException);
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(String, String)}.Because the
     * configuration file does not exist,so throw <code>ConfluenceConfigurationException</code> wraps IOException
     * from initialize method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Failure10() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate("notexistfilepath.xml",
                ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            assertTrue("Should be IOException.", e.getCause() instanceof IOException);
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(String, String)}.verify that
     * ConfluenceConfigurationException wraps LogException thrown from initialize method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Failure11() throws Exception {
        LogManager.setLogFactory(new LogFactory() {
            public Log createLog(String arg0) throws LogException {
                throw new LogException("error message.");
            }
        });
        try {
            new ConfluenceManagerWebServiceDelegate(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            assertTrue("Should be LogException.", e.getCause() instanceof LogException);
            LogManager.setLogFactory(new BasicLogFactory());
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(String, String)}.Because the
     * service_client_key value in configuration file(empty.confluence_manager_key.value_delegate_config.xml) is
     * empty ,so throw <code>ConfluenceConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Failure12() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                "empty.delegate_key.value");
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for
     * {@link ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(ConfigurationObject)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor3_Accuracy() throws Exception {
        ConfigurationFileManager manager =
            new ConfigurationFileManager(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH);

        ConfigurationObject root = manager.getConfiguration(ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE);

        ConfigurationObject delegateConfObj = root.getChild(ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE);
        delegateConfObj.setPropertyValue("log_name", " ");// not provide
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagerWebServiceDelegate(
            delegateConfObj));
    }

    /**
     * <p>
     * Failure test case for
     * ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(ConfigurationObject).ConfigurationObject
     * is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor3_Failure() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(ConfigurationObject).ConfigurationObject
     * contain wrong type for log_name value,so throw ConfluenceConfigurationException wraps ClassCastException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor3_Failure1() throws Exception {
        ConfigurationFileManager manager =
            new ConfigurationFileManager(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH);

        ConfigurationObject root = manager.getConfiguration(ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE);

        ConfigurationObject delegateConfObj = root.getChild(ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE);

        delegateConfObj.setPropertyValue("log_name", new Integer(3));
        try {
            new ConfluenceManagerWebServiceDelegate(delegateConfObj);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            assertTrue("should be ClassCastException.", e.getCause() instanceof ClassCastException);
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for
     * ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(ConfluenceManagementServiceClient,
     * Log).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor4_Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagerWebServiceDelegate(
            new ConfluenceManagementServiceClientMock(
                "http://127.0.0.1:8080/confluence_services-confluence_services-ejb"
                    + "/ConfluenceManagementServiceBean?wsdl"), null));
    }

    /**
     * <p>
     * Accuracy test case for
     * ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(ConfluenceManagementServiceClient,
     * Log).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor4_Accuracy1() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagerWebServiceDelegate(
            new ConfluenceManagementServiceClientMock(
                "http://127.0.0.1:8080/confluence_services-confluence_services-ejb"
                    + "/ConfluenceManagementServiceBean?wsdl"), LogManager.getLog()));
    }

    /**
     * <p>
     * Failure test case for
     * ConfluenceManagerWebServiceDelegate#ConfluenceManagerWebServiceDelegate(ConfluenceManagementServiceClient,
     * Log).serviceclient is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor4_Failure1() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(null, LogManager.getLog());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluenceManagerWebServiceDelegate#login(String, String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Accuracy() throws Exception {
        assertEquals("The value should be 'tokentoken'", "tokentoken", delegate.login("userName", "password"));
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagerWebServiceDelegate#login(String, String)}.userName is null,so
     * throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure1() throws Exception {
        try {
            delegate.login(null, "password");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagerWebServiceDelegate#login(String, String)}.userName is
     * empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure2() throws Exception {
        try {
            delegate.login("  ", "password");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagerWebServiceDelegate#login(String, String)}.password is null,so
     * throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure3() throws Exception {
        try {
            delegate.login("userName", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagerWebServiceDelegate#login(String, String)}.password is
     * empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure4() throws Exception {
        try {
            delegate.login("userName", "  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#login(String, String).ConfluenceManagementService
     * will throw <code>ConfluenceManagementServiceAuthenticationFailedException</code>,so the method will throw
     * <code>ConfluenceAuthenticationFailedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure5() throws Exception {
        ConfluenceManagerImplMock.isThrowConfluenceAuthenticationFailedException = true;
        try {
            delegate.login("userName", "password");
            fail("ConfluenceAuthenticationFailedException should be thrown.");
        } catch (ConfluenceAuthenticationFailedException e) {
            assertTrue("Cause should be ",
                e.getCause() instanceof ConfluenceManagementServiceAuthenticationFailedException);
            ConfluenceManagerImplMock.isThrowConfluenceAuthenticationFailedException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#login(String, String).ConfluenceManagementService
     * will throw <code>ConfluenceManagementServiceConnectionException</code>,so the method will throw
     * <code>ConfluenceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure6() throws Exception {
        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            delegate.login("userName", "password");
            fail("ConfluenceConnectionException should be thrown.");
        } catch (ConfluenceConnectionException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceConnectionException);
            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#login(String, String).ConfluenceManagementService
     * will throw <code>ConfluenceManagementServiceException</code>,so the method will throw
     * <code>ConfluenceManagerException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure7() throws Exception {
        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            delegate.login("userName", "password");
            fail("ConfluenceManagerException should be thrown.");
        } catch (ConfluenceManagerException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceException);
            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluenceManagerWebServiceDelegate#logout(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_Accuracy() throws Exception {
        try {
            delegate.logout("  ");
        } catch (Exception e) {
            fail("Should not be here.");
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagerWebServiceDelegate#logout(String)}.token is null,so throw
     * IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_Failure1() throws Exception {
        try {
            delegate.logout(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#logout(String).ConfluenceManagementService will
     * throw <code>ConfluenceManagementServiceConnectionException</code>,so the method will throw
     * <code>ConfluenceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_Failure2() throws Exception {
        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            delegate.logout("  ");
            fail("ConfluenceConnectionException should be thrown.");
        } catch (ConfluenceConnectionException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceConnectionException);
            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#logout(String).ConfluenceManagementService will
     * throw <code>ConfluenceManagementServiceException</code>,so the method will throw
     * <code>ConfluenceManagerException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_Failure3() throws Exception {
        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            delegate.logout("token");
            fail("ConfluenceManagerException should be thrown.");
        } catch (ConfluenceManagerException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceException);
            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#logout(String).ConfluenceManagementService will
     * throw <code>ConfluenceManagementServiceNotAuthorizedException</code>,so the method will throw
     * <code>ConfluenceNotAuthorizedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_Failure4() throws Exception {
        ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = true;
        try {
            delegate.logout("token");
            fail("ConfluenceNotAuthorizedException should be thrown.");
        } catch (ConfluenceNotAuthorizedException e) {
            assertTrue("Cause should be ",
                e.getCause() instanceof ConfluenceManagementServiceNotAuthorizedException);
            ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = false;
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluenceManagerWebServiceDelegate#createPage(String, Page)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_With_Page_Accuracy() throws Exception {
        Page page = new Page();
        ConfluencePageCreationResult result = delegate.createPage("  ", page);
        assertTrue("actionToken should be BASE_PAGE_AND_VERSION_CREATED.",
            ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED == result.getActionTaken());
        assertTrue("Should be equale to page.", page == result.getPage());
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagerWebServiceDelegate#createPage(String, Page)}.token is null,so
     * throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_With_Page_Failure1() throws Exception {
        try {
            delegate.createPage(null, new Page());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagerWebServiceDelegate#createPage(String, Page)}.page is null,so
     * throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_With_Page_Failure2() throws Exception {
        try {
            delegate.createPage("  ", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagerWebServiceDelegate#createPage(String, Page)}.confluenceManager
     * will throw <code>ConfluenceManagementServiceConnectionException</code>,so the method will throw
     * <code>ConfluenceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_With_Page_Failure3() throws Exception {
        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            delegate.createPage("  ", new Page());
            fail("ConfluenceConnectionException should be thrown.");
        } catch (ConfluenceConnectionException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceConnectionException);
            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagerWebServiceDelegate#createPage(String, Page)}.confluenceManager
     * will throw <code>ConfluenceManagementServiceException</code>,so the method will throw
     * <code>ConfluenceManagerException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_With_Page_Failure4() throws Exception {
        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            delegate.createPage("  ", new Page());
            fail("ConfluenceManagerException should be thrown.");
        } catch (ConfluenceManagerException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceException);
            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagerWebServiceDelegate#createPage(String, Page)}.confluenceManager
     * will throw <code>ConfluenceManagementServiceNotAuthorizedException</code>,so the method will throw
     * <code>ConfluenceNotAuthorizedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_With_Page_Failure5() throws Exception {
        ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = true;
        try {
            delegate.createPage("  ", new Page());
            fail("ConfluenceNotAuthorizedException should be thrown.");
        } catch (ConfluenceNotAuthorizedException e) {
            assertTrue("Cause should be ",
                e.getCause() instanceof ConfluenceManagementServiceNotAuthorizedException);
            ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = false;
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Accuracy() throws Exception {
        ConfluencePageCreationResult result =
            delegate.createPage(" ", "pageName", "1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);

        assertEquals("BasePageUrl should be pageNameUrl", "pageNameUrl", result.getBasePageUrl());
        assertNotNull("Page should not be null.", result.getPage());
        assertTrue("actionToken should be BASE_PAGE_AND_VERSION_CREATED.",
            ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED == result.getActionTaken());
        assertTrue("Version should be 1.", "1".equals(result.getPage().getVersion()));
        assertTrue("AssetType should be ConfluenceAssetType.APPLICATION_ARCHITECTURE.",
            ConfluenceAssetType.APPLICATION_ARCHITECTURE == result.getPage().getAssetType());

        assertTrue("ComponentType should be CUSTOM.", ComponentType.CUSTOM == result.getPage().getComponentType());
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).token is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure1() throws Exception {
        try {
            delegate.createPage(null, "page name", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).pageName is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure2() throws Exception {
        try {
            delegate.createPage("  ", null, "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).pageName is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure3() throws Exception {
        try {
            delegate.createPage("  ", "  ", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).version is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure4() throws Exception {
        try {
            delegate.createPage("  ", "page name", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).version is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure5() throws Exception {
        try {
            delegate.createPage("  ", "page name", "  ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).assetType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure6() throws Exception {
        try {
            delegate.createPage("  ", "page name", "2", null, ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).catalog is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure7() throws Exception {
        try {
            delegate.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING, null,
                ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).componentType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure8() throws Exception {
        try {
            delegate.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING,
                ConfluenceCatalog.JAVA, (ComponentType) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).confluenceManager will throw
     * <code>ConfluenceManagementServiceConnectionException</code>,so the method will throw
     * <code>ConfluenceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure9() throws Exception {
        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            delegate.createPage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("ConfluenceConnectionException should be thrown.");
        } catch (ConfluenceConnectionException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceConnectionException);
            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).confluenceManager will throw
     * <code>ConfluenceManagementServiceException</code>,so the method will throw
     * <code>ConfluenceManagerException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure10() throws Exception {
        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            delegate.createPage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("ConfluenceManagerException should be thrown.");
        } catch (ConfluenceManagerException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceException);
            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).confluenceManager will throw
     * <code>ConfluenceManagementServiceNotAuthorizedException</code>,so the method will throw
     * <code>ConfluenceNotAuthorizedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure11() throws Exception {
        ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = true;
        try {
            delegate.createPage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("ConfluenceNotAuthorizedException should be thrown.");
        } catch (ConfluenceNotAuthorizedException e) {
            assertTrue("Cause should be ",
                e.getCause() instanceof ConfluenceManagementServiceNotAuthorizedException);
            ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = false;
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Accuracy() throws Exception {
        ConfluencePageCreationResult result =
            delegate.createPage(" ", "pageName", "1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "applicationCode");

        assertEquals("BasePageUrl should be pageNameUrl", "pageNameUrl", result.getBasePageUrl());
        assertNotNull("Page should not be null.", result.getPage());
        assertTrue("actionToken should be BASE_PAGE_AND_VERSION_CREATED.",
            ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED == result.getActionTaken());
        assertTrue("Version should be 1.", "1".equals(result.getPage().getVersion()));
        assertTrue("AssetType should be ConfluenceAssetType.APPLICATION_ARCHITECTURE.",
            ConfluenceAssetType.APPLICATION_ARCHITECTURE == result.getPage().getAssetType());
        assertEquals("applicationCode should be applicationCode", "applicationCode", result.getPage()
            .getApplicationCode());
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).token is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure1() throws Exception {
        try {
            delegate.createPage(null, "page name", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).pageName is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure2() throws Exception {
        try {
            delegate.createPage("  ", null, "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).pageName is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure3() throws Exception {
        try {
            delegate.createPage("  ", "  ", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).version is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure4() throws Exception {
        try {
            delegate.createPage("  ", "page name", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).version is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure5() throws Exception {
        try {
            delegate.createPage("  ", "page name", "  ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).assetType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure6() throws Exception {
        try {
            delegate.createPage("  ", "page name", "2", null, ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).catalog is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure7() throws Exception {
        try {
            delegate.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING, null, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).applicationCode is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure8() throws Exception {

        try {
            delegate.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING,
                ConfluenceCatalog.JAVA, (String) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).applicationCode is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure9() throws Exception {

        try {
            delegate.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING,
                ConfluenceCatalog.JAVA, "  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).confluenceManager will throw
     * <code>ConfluenceManagementServiceConnectionException</code>,so the method will throw
     * <code>ConfluenceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure10() throws Exception {

        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            delegate.createPage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
            fail("ConfluenceConnectionException should be thrown.");
        } catch (ConfluenceConnectionException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceConnectionException);

            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).confluenceManager will throw
     * <code>ConfluenceManagementServiceException</code>,so the method will throw
     * <code>ConfluenceManagerException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure11() throws Exception {

        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            delegate.createPage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
            fail("ConfluenceManagerException should be thrown.");
        } catch (ConfluenceManagerException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceException);
            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).confluenceManager will throw
     * <code>ConfluenceManagementServiceNotAuthorizedException</code>,so the method will throw
     * <code>ConfluenceNotAuthorizedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure12() throws Exception {

        ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = true;
        try {
            delegate.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
            fail("ConfluenceNotAuthorizedException should be thrown.");
        } catch (ConfluenceNotAuthorizedException e) {
            assertTrue("Cause should be ",
                e.getCause() instanceof ConfluenceManagementServiceNotAuthorizedException);

            ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = false;
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Accuracy() throws Exception {

        Page page =
            delegate.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
        assertTrue("ConfluenceAssetType should be APPLICATION_ARCHITECTURE.",
            ConfluenceAssetType.APPLICATION_ARCHITECTURE == page.getAssetType());
        assertTrue("version should be 2.", "2".equals(page.getVersion()));
        assertTrue("basePageUrl should be 'page nameUrl'.", "page nameUrl".equals(page.getBasePageUrl()));
        assertTrue("ConfluenceCatalog should be DOT_NET.", page.getCatalog() == ConfluenceCatalog.DOT_NET);
        assertTrue("ComponentType should be CUSTOM.", page.getComponentType() == ComponentType.CUSTOM);
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).token is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure1() throws Exception {

        try {
            delegate.retrievePage(null, "page name", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).pageName is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure2() throws Exception {

        try {
            delegate.retrievePage("  ", null, "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).pageName is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure3() throws Exception {

        try {
            delegate.retrievePage("  ", "  ", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).version is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure4() throws Exception {

        try {
            delegate.retrievePage("  ", "page name", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).version is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure5() throws Exception {

        try {
            delegate.retrievePage("  ", "page name", "  ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).assetType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure6() throws Exception {

        try {
            delegate.retrievePage("  ", "page name", "2", null, ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).catalog is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure7() throws Exception {

        try {
            delegate.retrievePage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING, null,
                ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).componentType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure8() throws Exception {

        try {
            delegate.retrievePage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING,
                ConfluenceCatalog.JAVA, (ComponentType) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).confluenceManager will throw
     * <code>ConfluenceManagementServiceConnectionException</code>,so the method will throw
     * <code>ConfluenceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure9() throws Exception {

        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            delegate.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("ConfluenceConnectionException should be thrown.");
        } catch (ConfluenceConnectionException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceConnectionException);

            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).confluenceManager will throw
     * <code>ConfluenceManagementServiceException</code>,so the method will throw
     * <code>ConfluenceManagerException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure10() throws Exception {

        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            delegate.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("ConfluenceManagerException should be thrown.");
        } catch (ConfluenceManagerException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceException);

            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).confluenceManager will throw
     * <code>ConfluenceManagementServiceNotAuthorizedException</code>,so the method will throw
     * <code>ConfluenceNotAuthorizedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure11() throws Exception {

        ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = true;
        try {
            delegate.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("ConfluenceNotAuthorizedException should be thrown.");
        } catch (ConfluenceNotAuthorizedException e) {
            assertTrue("Cause should be ",
                e.getCause() instanceof ConfluenceManagementServiceNotAuthorizedException);

            ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = false;
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ApplicationCode_Accuracy() throws Exception {

        Page page =
            delegate.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
        assertTrue("ConfluenceAssetType should be APPLICATION_ARCHITECTURE.",
            ConfluenceAssetType.APPLICATION_ARCHITECTURE == page.getAssetType());
        assertTrue("version should be 2.", "2".equals(page.getVersion()));
        assertTrue("basePageUrl should be 'page nameUrl'.", "page nameUrl".equals(page.getBasePageUrl()));
        assertTrue("ConfluenceCatalog should be DOT_NET.", page.getCatalog() == ConfluenceCatalog.DOT_NET);
        assertTrue("applicationCode should be 'application code'.", page.getApplicationCode().equals(
            "application code"));
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).token is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure1() throws Exception {

        try {
            delegate.createPage(null, "page name", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).pageName is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure2() throws Exception {

        try {
            delegate.createPage("  ", null, "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).pageName is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure3() throws Exception {

        try {
            delegate.createPage("  ", "  ", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).version is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure4() throws Exception {

        try {
            delegate.createPage("  ", "page name", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).version is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure5() throws Exception {

        try {
            delegate.createPage("  ", "page name", "  ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).assetType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure6() throws Exception {

        try {
            delegate.createPage("  ", "page name", "2", null, ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).catalog is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure7() throws Exception {

        try {
            delegate.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING, null, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).applicationCode is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure8() throws Exception {

        try {
            delegate.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING,
                ConfluenceCatalog.JAVA, (String) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).applicationCode is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure9() throws Exception {

        try {
            delegate.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING,
                ConfluenceCatalog.JAVA, "  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).confluenceManager will throw
     * <code>ConfluenceManagementServiceConnectionException</code>,so the method will throw
     * <code>ConfluenceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure10() throws Exception {

        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            delegate.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
            fail("ConfluenceConnectionException should be thrown.");
        } catch (ConfluenceConnectionException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceConnectionException);

            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).confluenceManager will throw
     * <code>ConfluenceManagementServiceException</code>,so the method will throw
     * <code>ConfluenceManagerException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure11() throws Exception {

        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            delegate.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
            fail("ConfluenceManagerException should be thrown.");
        } catch (ConfluenceManagerException e) {
            assertTrue("Cause should be ", e.getCause() instanceof ConfluenceManagementServiceException);

            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagerWebServiceDelegate#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).confluenceManager will throw
     * <code>ConfluenceManagementServiceNotAuthorizedException</code>,so the method will throw
     * <code>ConfluenceNotAuthorizedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure12() throws Exception {

        ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = true;
        try {
            delegate.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
            fail("ConfluenceNotAuthorizedException should be thrown.");
        } catch (ConfluenceNotAuthorizedException e) {
            assertTrue("Cause should be ",
                e.getCause() instanceof ConfluenceManagementServiceNotAuthorizedException);

            ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = false;
            // pass
        }
    }
}