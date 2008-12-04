/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.jws.WebService;

import junit.framework.TestCase;

import com.topcoder.configuration.persistence.ConfigurationPersistenceException;
import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreatedAction;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceAuthenticationFailedException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceConfigurationException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceConnectionException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceNotAuthorizedException;
import com.topcoder.confluence.webservice.ConfluenceManagerImplMock;
import com.topcoder.confluence.webservice.TestHelper;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.SpecificationFactoryException;

/**
 * <p>
 * UnitTest cases of the <code>ConfluenceManagementServiceBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagementServiceBeanTests extends TestCase {

    /**
     * <p>
     * Represents the <code>ConfluenceManagementServiceBean</code> instance used for test.
     * </p>
     */
    private ConfluenceManagementServiceBean bean;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        bean = new ConfluenceManagementServiceBean();
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
        bean = null;
    }

    /**
     * <p>
     * Verify that <code>ConfluenceManagementServiceBean</code> contains Stateless annotation and WebService and
     * TransactionManagement.
     * </p>
     */
    public void testClassAnnotation() {
        TestHelper.assertClassAnnotation(ConfluenceManagementServiceBean.class, Stateless.class);
        TestHelper.assertClassAnnotation(ConfluenceManagementServiceBean.class, WebService.class);
        TestHelper.assertClassAnnotation(ConfluenceManagementServiceBean.class, TransactionManagement.class);
    }

    /**
     * <p>
     * Verify that initialize contains PostConstruct annotation.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_initialize_annotation() throws Exception {
        TestHelper.assertMethodAnnotation(ConfluenceManagementServiceBean.class.getDeclaredMethod("initialize"),
            PostConstruct.class);
    }

    /**
     * <p>
     * Verify that all public methods contains TransactionAttribute annotation.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_all_public_methods_contain_TransactionAttribute_annotation() throws Exception {
        TestHelper.assertMethodAnnotation(ConfluenceManagementServiceBean.class.getMethod("login", String.class,
            String.class), TransactionAttribute.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementServiceBean.class.getMethod("logout", String.class),
            TransactionAttribute.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementServiceBean.class.getMethod("createPage",
            String.class, String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class,
            ComponentType.class), TransactionAttribute.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementServiceBean.class.getMethod("createPage",
            String.class, String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class,
            String.class), TransactionAttribute.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementServiceBean.class.getMethod("createPage",
            String.class, Page.class), TransactionAttribute.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementServiceBean.class.getMethod("retrievePage",
            String.class, String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class,
            ComponentType.class), TransactionAttribute.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementServiceBean.class.getMethod("createPage",
            String.class, String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class,
            String.class), TransactionAttribute.class);
    }

    /**
     * <p>
     * Verify that fields contains Resource annotation.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_Resource_annotation() throws Exception {
        TestHelper.assertFieldAnnotation(ConfluenceManagementServiceBean.class
            .getDeclaredField("confluenceManagerFile"), Resource.class);

        TestHelper.assertFieldAnnotation(ConfluenceManagementServiceBean.class
            .getDeclaredField("confluenceManagerNamespace"), Resource.class);
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluenceManagementServiceBean#ConfluenceManagementServiceBean()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_Ctor_Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", bean);
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluenceManagementServiceBean#initialize()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_initialize_Accuracy() throws Exception {
        bean.initialize();
        assertNotNull("log should be initialized.", TestHelper.getPrivateField(bean, "log"));
        assertNotNull("confluenceManager should be initialized.", TestHelper.getPrivateField(bean,
            "confluenceManager"));
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#initialize()}.Because the namespace injected
     * is empty,so throw <code>ConfluenceManagementServiceConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_initialize_Failure1() throws Exception {
        try {
            TestHelper.setPrivateField(bean, "confluenceManagerNamespace", "");
            bean.initialize();
            fail("ConfluenceManagementServiceConfigurationException should be thrown.");
        } catch (ConfluenceManagementServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#initialize()}.Because the configuration
     * file-empty_bean_config.xml is empty,so throw <code>ConfluenceManagementServiceConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_initialize_Failure2() throws Exception {
        TestHelper.setPrivateField(bean, "confluenceManagerFile",
            "com/topcoder/confluence/webservice/bean/ConfluenceManagementServiceBean.properties");
        TestHelper.setPrivateField(bean, "confluenceManagerNamespace", "empty.bean.config");
        try {
            bean.initialize();
            fail("ConfluenceManagementServiceConfigurationException should be thrown.");
        } catch (ConfluenceManagementServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#initialize()}.Because configuration
     * file(wrong_type_confluenceManager_bean_config.xml) is not ConfluenceManager type ,so throw
     * <code>ConfluenceManagementServiceConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_initialize_Failure3() throws Exception {
        TestHelper.setPrivateField(bean, "confluenceManagerNamespace", "wrong.type.confluenceManager");
        try {
            bean.initialize();
            fail("ConfluenceManagementServiceConfigurationException should be thrown.");
        } catch (ConfluenceManagementServiceConfigurationException e) {
            assertTrue("Should be ClassCastException.", e.getCause() instanceof ClassCastException);
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#initialize()}.Because the
     * confluence_manager_key value in configuration file(empty.confluence_manager_key.value_bean_config.xml) is
     * empty ,so throw <code>ConfluenceManagementServiceConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_initialize_Failure4() throws Exception {
        TestHelper.setPrivateField(bean, "confluenceManagerNamespace", "empty.confluence_manager_key.value");
        try {
            bean.initialize();
            fail("ConfluenceManagementServiceConfigurationException should be thrown.");
        } catch (ConfluenceManagementServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#initialize()}.Because the confluenceManager
     * created from object factory will throw <code>InvalidClassSpecificationException</code>,so throw
     * <code>ConfluenceManagementServiceConfigurationException</code> wraps InvalidClassSpecificationException
     * from initialize method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_initialize_Failure5() throws Exception {
        TestHelper.setPrivateField(bean, "confluenceManagerNamespace",
            "InvalidClassSpecificationException.namespace");
        try {
            bean.initialize();
            fail("ConfluenceManagementServiceConfigurationException should be thrown.");
        } catch (ConfluenceManagementServiceConfigurationException e) {
            assertTrue("Should be InvalidClassSpecificationException.",
                e.getCause() instanceof InvalidClassSpecificationException);
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#initialize()}.Because the configuration file
     * is not recognized,so throw <code>ConfluenceManagementServiceConfigurationException</code> wraps
     * ConfigurationPersistenceException from initialize method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_initialize_Failure6() throws Exception {
        TestHelper.setPrivateField(bean, "confluenceManagerFile", "unrecognized");
        try {
            bean.initialize();
            fail("ConfluenceManagementServiceConfigurationException should be thrown.");
        } catch (ConfluenceManagementServiceConfigurationException e) {
            assertTrue("Should be ConfigurationPersistenceException.",
                e.getCause() instanceof ConfigurationPersistenceException);
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#initialize()}.Because the specification is not
     * valid for configuration file(SpecificationFactoryException_bean_config.xml),so throw
     * <code>ConfluenceManagementServiceConfigurationException</code> wraps SpecificationFactoryException from
     * initialize method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_initialize_Failure7() throws Exception {
        TestHelper.setPrivateField(bean, "confluenceManagerNamespace", "SpecificationFactoryException.namespace");
        try {
            bean.initialize();
            fail("ConfluenceManagementServiceConfigurationException should be thrown.");
        } catch (ConfluenceManagementServiceConfigurationException e) {
            assertTrue("Should be SpecificationFactoryException.",
                e.getCause() instanceof SpecificationFactoryException);
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#initialize()}.Because the configuration file
     * does not exist,so throw <code>ConfluenceManagementServiceConfigurationException</code> wraps IOException
     * from initialize method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_initialize_Failure8() throws Exception {
        TestHelper.setPrivateField(bean, "confluenceManagerFile", "notexistfilepath.xml");
        try {
            bean.initialize();
            fail("ConfluenceManagementServiceConfigurationException should be thrown.");
        } catch (ConfluenceManagementServiceConfigurationException e) {
            assertTrue("Should be IOException.", e.getCause() instanceof IOException);
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#initialize()}.verify that
     * ConfluenceManagementServiceConfigurationException wraps LogException thrown from initialize method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_initialize_Failure9() throws Exception {
        LogManager.setLogFactory(new LogFactory() {
            public Log createLog(String arg0) throws LogException {
                throw new LogException("error message.");
            }
        });
        try {
            bean.initialize();
            fail("ConfluenceManagementServiceConfigurationException should be thrown.");
        } catch (ConfluenceManagementServiceConfigurationException e) {
            assertTrue("Should be LogException.", e.getCause() instanceof LogException);
            LogManager.setLogFactory(new BasicLogFactory());
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluenceManagementServiceBean#login(String, String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Accuracy() throws Exception {
        bean.initialize();

        assertEquals("The value should be 'tokentoken'", "tokentoken", bean.login("userName", "password"));
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#login(String, String)}.userName is null,so
     * throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure1() throws Exception {
        bean.initialize();
        try {
            bean.login(null, "password");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#login(String, String)}.userName is empty,so
     * throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure2() throws Exception {
        bean.initialize();
        try {
            bean.login("  ", "password");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#login(String, String)}.password is null,so
     * throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure3() throws Exception {
        bean.initialize();
        try {
            bean.login("userName", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#login(String, String)}.password is empty,so
     * throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure4() throws Exception {
        bean.initialize();
        try {
            bean.login("userName", "  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#login(String, String)}.confluenceManager will
     * throw <code>ConfluenceAuthenticationFailedException</code>,so the method will throw
     * <code>ConfluenceManagementServiceAuthenticationFailedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure5() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceAuthenticationFailedException = true;
        try {
            bean.login("userName", "password");
            fail("ConfluenceManagementServiceAuthenticationFailedException should be thrown.");
        } catch (ConfluenceManagementServiceAuthenticationFailedException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceAuthenticationFailedException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#login(String, String)}.confluenceManager will
     * throw <code>ConfluenceConnectionException</code>,so the method will throw
     * <code>ConfluenceManagementServiceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure6() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            bean.login("userName", "password");
            fail("ConfluenceManagementServiceConnectionException should be thrown.");
        } catch (ConfluenceManagementServiceConnectionException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#login(String, String)}.confluenceManager will
     * throw <code>ConfluenceManagerException</code>,so the method will throw
     * <code>ConfluenceManagementServiceException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure7() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            bean.login("userName", "password");
            fail("ConfluenceManagementServiceException should be thrown.");
        } catch (ConfluenceManagementServiceException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluenceManagementServiceBean#logout(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_Accuracy() throws Exception {
        try {
            bean.initialize();
            bean.logout("  ");
        } catch (Exception e) {
            fail("Should not be here.");
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#logout(String)}.token is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_Failure1() throws Exception {
        bean.initialize();
        try {
            bean.logout(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#logout(String)}.confluenceManager will throw
     * <code>ConfluenceConnectionException</code>,so the method will throw
     * <code>ConfluenceManagementServiceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_Failure2() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            bean.logout("token");
            fail("ConfluenceManagementServiceConnectionException should be thrown.");
        } catch (ConfluenceManagementServiceConnectionException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#logout(String)}.confluenceManager will throw
     * <code>ConfluenceManagerException</code>,so the method will throw
     * <code>ConfluenceManagementServiceException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_Failure3() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            bean.logout("token");
            fail("ConfluenceManagementServiceException should be thrown.");
        } catch (ConfluenceManagementServiceException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#logout(String)}.confluenceManager will throw
     * <code>ConfluenceNotAuthorizedException</code>,so the method will throw
     * <code>ConfluenceManagementServiceNotAuthorizedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_Failure4() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = true;
        try {
            bean.logout("token");
            fail("ConfluenceManagementServiceNotAuthorizedException should be thrown.");
        } catch (ConfluenceManagementServiceNotAuthorizedException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = false;
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluenceManagementServiceBean#createPage(String, Page)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_With_Page_Accuracy() throws Exception {
        Page page = new Page();
        bean.initialize();
        ConfluencePageCreationResult result = bean.createPage("  ", page);
        assertTrue("actionToken should be BASE_PAGE_AND_VERSION_CREATED.",
            ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED == result.getActionTaken());
        assertTrue("Should be equale to page.", page == result.getPage());
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#createPage(String, Page)}.token is null,so
     * throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_With_Page_Failure1() throws Exception {
        bean.initialize();
        try {
            bean.createPage(null, new Page());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#createPage(String, Page)}.page is null,so
     * throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_With_Page_Failure2() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#createPage(String, Page)}.confluenceManager
     * will throw <code>ConfluenceConnectionException</code>,so the method will throw
     * <code>ConfluenceManagementServiceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_With_Page_Failure3() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            bean.createPage("  ", new Page());
            fail("ConfluenceManagementServiceConnectionException should be thrown.");
        } catch (ConfluenceManagementServiceConnectionException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#createPage(String, Page)}.confluenceManager
     * will throw <code>ConfluenceManagerException</code>,so the method will throw
     * <code>ConfluenceManagementServiceException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_With_Page_Failure4() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            bean.createPage("  ", new Page());
            fail("ConfluenceManagementServiceException should be thrown.");
        } catch (ConfluenceManagementServiceException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceBean#createPage(String, Page)}.confluenceManager
     * will throw <code>ConfluenceNotAuthorizedException</code>,so the method will throw
     * <code>ConfluenceManagementServiceNotAuthorizedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_With_Page_Failure5() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = true;
        try {
            bean.createPage("  ", new Page());
            fail("ConfluenceManagementServiceNotAuthorizedException should be thrown.");
        } catch (ConfluenceManagementServiceNotAuthorizedException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = false;
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Accuracy() throws Exception {
        bean.initialize();
        ConfluencePageCreationResult result =
            bean.createPage(" ", "pageName", "1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
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
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).token is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure1() throws Exception {
        bean.initialize();
        try {
            bean.createPage(null, "page name", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).pageName is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure2() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", null, "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).pageName is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure3() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "  ", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).version is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure4() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).version is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure5() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "  ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).assetType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure6() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "2", null, ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).catalog is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure7() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING, null,
                ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).componentType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure8() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING,
                ConfluenceCatalog.JAVA, (ComponentType) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).confluenceManager will throw
     * <code>ConfluenceConnectionException</code>,so the method will throw
     * <code>ConfluenceManagementServiceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure9() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            bean.createPage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("ConfluenceManagementServiceConnectionException should be thrown.");
        } catch (ConfluenceManagementServiceConnectionException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).confluenceManager will throw
     * <code>ConfluenceManagerException</code>,so the method will throw
     * <code>ConfluenceManagementServiceException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure10() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            bean.createPage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("ConfluenceManagementServiceException should be thrown.");
        } catch (ConfluenceManagementServiceException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).confluenceManager will throw
     * <code>ConfluenceNotAuthorizedException</code>,so the method will throw
     * <code>ConfluenceManagementServiceNotAuthorizedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_ComponentType_Failure11() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = true;
        try {
            bean.createPage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("ConfluenceManagementServiceNotAuthorizedException should be thrown.");
        } catch (ConfluenceManagementServiceNotAuthorizedException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = false;
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Accuracy() throws Exception {
        bean.initialize();
        ConfluencePageCreationResult result =
            bean.createPage(" ", "pageName", "1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
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
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).token is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure1() throws Exception {
        bean.initialize();
        try {
            bean.createPage(null, "page name", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).pageName is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure2() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", null, "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).pageName is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure3() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "  ", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).version is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure4() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).version is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure5() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "  ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).assetType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure6() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "2", null, ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).catalog is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure7() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING, null, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).applicationCode is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure8() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING,
                ConfluenceCatalog.JAVA, (String) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).applicationCode is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure9() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING,
                ConfluenceCatalog.JAVA, "  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).confluenceManager will throw
     * <code>ConfluenceConnectionException</code>,so the method will throw
     * <code>ConfluenceManagementServiceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure10() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            bean.createPage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
            fail("ConfluenceManagementServiceConnectionException should be thrown.");
        } catch (ConfluenceManagementServiceConnectionException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).confluenceManager will throw
     * <code>ConfluenceManagerException</code>,so the method will throw
     * <code>ConfluenceManagementServiceException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure11() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            bean.createPage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
            fail("ConfluenceManagementServiceException should be thrown.");
        } catch (ConfluenceManagementServiceException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#createPage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).confluenceManager will throw
     * <code>ConfluenceNotAuthorizedException</code>,so the method will throw
     * <code>ConfluenceManagementServiceNotAuthorizedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage_with_applicationCode_Failure12() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = true;
        try {
            bean.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
            fail("ConfluenceManagementServiceNotAuthorizedException should be thrown.");
        } catch (ConfluenceManagementServiceNotAuthorizedException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = false;
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Accuracy() throws Exception {
        bean.initialize();
        Page page =
            bean.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
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
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).token is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure1() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage(null, "page name", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).pageName is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure2() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("  ", null, "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).pageName is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure3() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("  ", "  ", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).version is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure4() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("  ", "page name", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).version is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure5() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("  ", "page name", "  ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).assetType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure6() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("  ", "page name", "2", null, ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).catalog is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure7() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING, null,
                ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).componentType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure8() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING,
                ConfluenceCatalog.JAVA, (ComponentType) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).confluenceManager will throw
     * <code>ConfluenceConnectionException</code>,so the method will throw
     * <code>ConfluenceManagementServiceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure9() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            bean.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("ConfluenceManagementServiceConnectionException should be thrown.");
        } catch (ConfluenceManagementServiceConnectionException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).confluenceManager will throw
     * <code>ConfluenceManagerException</code>,so the method will throw
     * <code>ConfluenceManagementServiceException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure10() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            bean.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("ConfluenceManagementServiceException should be thrown.");
        } catch (ConfluenceManagementServiceException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, ComponentType).confluenceManager will throw
     * <code>ConfluenceNotAuthorizedException</code>,so the method will throw
     * <code>ConfluenceManagementServiceNotAuthorizedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ComponentType_Failure11() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = true;
        try {
            bean.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("ConfluenceManagementServiceNotAuthorizedException should be thrown.");
        } catch (ConfluenceManagementServiceNotAuthorizedException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = false;
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_ApplicationCode_Accuracy() throws Exception {
        bean.initialize();
        Page page =
            bean.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
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
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).token is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure1() throws Exception {
        bean.initialize();
        try {
            bean.createPage(null, "page name", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).pageName is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure2() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", null, "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).pageName is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure3() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "  ", "3", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).version is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure4() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).version is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure5() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "  ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).assetType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure6() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "2", null, ConfluenceCatalog.DOT_NET, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).catalog is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure7() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING, null, "code");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).applicationCode is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure8() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING,
                ConfluenceCatalog.JAVA, (String) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).applicationCode is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure9() throws Exception {
        bean.initialize();
        try {
            bean.createPage("  ", "page name", "2", ConfluenceAssetType.APPLICATION_TESTING,
                ConfluenceCatalog.JAVA, "  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).confluenceManager will throw
     * <code>ConfluenceConnectionException</code>,so the method will throw
     * <code>ConfluenceManagementServiceConnectionException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure10() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceConnectionException = true;
        try {
            bean.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
            fail("ConfluenceManagementServiceConnectionException should be thrown.");
        } catch (ConfluenceManagementServiceConnectionException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceConnectionException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).confluenceManager will throw
     * <code>ConfluenceManagerException</code>,so the method will throw
     * <code>ConfluenceManagementServiceException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure11() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceManagerException = true;
        try {
            bean.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
            fail("ConfluenceManagementServiceException should be thrown.");
        } catch (ConfluenceManagementServiceException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceManagerException = false;
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceBean#retrievePage(String, String, String,
     * ConfluenceAssetType, ConfluenceCatalog, String).confluenceManager will throw
     * <code>ConfluenceNotAuthorizedException</code>,so the method will throw
     * <code>ConfluenceManagementServiceNotAuthorizedException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_with_applicationCode_Failure12() throws Exception {
        bean.initialize();
        ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = true;
        try {
            bean.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
            fail("ConfluenceManagementServiceNotAuthorizedException should be thrown.");
        } catch (ConfluenceManagementServiceNotAuthorizedException e) {
            assertNull("cause should be null.not wrap the underlying exception.", e.getCause());
            ConfluenceManagerImplMock.isThrowConfluenceNotAuthorizedException = false;
            // pass
        }
    }
}