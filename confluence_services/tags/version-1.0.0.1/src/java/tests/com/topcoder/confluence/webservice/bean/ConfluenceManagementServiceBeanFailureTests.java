/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.bean;

import junit.framework.TestCase;

import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.Page;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceConfigurationException;
import com.topcoder.confluence.webservice.failuretests.FailureTestHelper;

/**
 * Failure tests for class ConfluenceManagementServiceBean.
 *
 * @author extra
 * @version 1.0
 */
public class ConfluenceManagementServiceBeanFailureTests extends TestCase {

    /**
     * The ConfluenceManagementServiceBean instance for test.
     */
    private ConfluenceManagementServiceBean bean;

    /**
     * Sets up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        bean = new ConfluenceManagementServiceBean();
    }

    /**
     * Failure test for method initialize(). If the configuration file is invalid,
     * ConfluenceManagementServiceConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeInvalidFile() throws Exception {
        FailureTestHelper.setPrivateField(bean, "confluenceManagerFile", "invalid");
        try {
            bean.initialize();
            fail("ConfluenceManagementServiceConfigurationException expected.");
        } catch (ConfluenceManagementServiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method initialize(). If the configuration file does not exist,
     * ConfluenceManagementServiceConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeNotExist() throws Exception {
        FailureTestHelper.setPrivateField(bean, "confluenceManagerFile", "notexist.xml");
        try {
            bean.initialize();
            fail("ConfluenceManagementServiceConfigurationException expected.");
        } catch (ConfluenceManagementServiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method login(String, String). If userName is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testLoginNullUserName() throws Exception {
        bean.initialize();
        try {
            bean.login(null, "password");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method login(String, String). If userName is empty string, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testLoginEmptyUserName() throws Exception {
        bean.initialize();
        try {
            bean.login(" ", "password");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method login(String, String). If password is null string, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testLoginNullPassword() throws Exception {
        bean.initialize();
        try {
            bean.login("userName", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method login(String, String). If password is empty string, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testLoginEmptyPassword() throws Exception {
        bean.initialize();
        try {
            bean.login("userName", " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method logout(String). If token is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testLogoutNullToken() throws Exception {
        bean.initialize();
        try {
            bean.logout(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, Page). If token is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePageNullToken() throws Exception {
        bean.initialize();
        try {
            bean.createPage(null, new Page());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, Page). If page is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePageNullPage() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If token is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage2NullToken() throws Exception {
        bean.initialize();
        try {
            bean.createPage(null, "pageName", "1.1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If pageName is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage2NullPageName() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", null, "1.1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If pageName is empty string, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage2EmptyPageName() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", " ", "1.1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If version is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage2NullVersion() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", "pageName", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If version is empty string, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage2EmptyVersion() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", "pageName", " ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If assertType is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage2NullAssertType() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", "pageName", "1.1", null, ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If catalog is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage2NullCatalog() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", "pageName", "1.1", ConfluenceAssetType.APPLICATION_TESTING, null,
                    ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If componentType is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage2NullComponentType() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", "pageName", "1.1", ConfluenceAssetType.APPLICATION_TESTING,
                    ConfluenceCatalog.DOT_NET, (ComponentType) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * token is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage3NullToken() throws Exception {
        bean.initialize();
        try {
            bean.createPage(null, "pageName", "1.1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * pageName is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage3NullPageName() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", null, "1.1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * pageName is empty string, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage3EmptyPageName() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", " ", "1.1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * version is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage3NullVersion() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", "pageName", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * version is empty string, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage3EmptyVersion() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", "pageName", " ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * assertType is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage3NullAssertType() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", "pageName", "1.1", null, ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * catalog is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage3NullCatalog() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", "pageName", "1.1", ConfluenceAssetType.APPLICATION_TESTING, null,
                    "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * componentType is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage3NullString() throws Exception {
        bean.initialize();
        try {
            bean.createPage("token", "pageName", "1.1", ConfluenceAssetType.APPLICATION_TESTING,
                    ConfluenceCatalog.DOT_NET, (String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If token is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePageNullToken() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage(null, "pageName", "1.1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If pageName is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePageNullPageName() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", null, "1.1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If pageName is empty string, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePageEmptyPageName() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", " ", "1.1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If version is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePageNullVersion() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", "pageName", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If version is empty string, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePageEmptyVersion() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", "pageName", " ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If assertType is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePageNullAssertType() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", "pageName", "1.1", null, ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If catalog is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePageNullCatalog() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", "pageName", "1.1", ConfluenceAssetType.APPLICATION_TESTING, null,
                    ComponentType.CUSTOM);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType). If componentType is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePageNullComponentType() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", "pageName", "1.1", ConfluenceAssetType.APPLICATION_TESTING,
                    ConfluenceCatalog.DOT_NET, (ComponentType) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * token is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage2NullToken() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage(null, "pageName", "1.1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * pageName is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage2NullPageName() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", null, "1.1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * pageName is empty string, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage2EmptyPageName() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", " ", "1.1", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * version is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage2NullVersion() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", "pageName", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * version is empty string, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage2EmptyVersion() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", "pageName", " ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                    ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * assertType is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage2NullAssertType() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", "pageName", "1.1", null, ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * catalog is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage2NullCatalog() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", "pageName", "1.1", ConfluenceAssetType.APPLICATION_TESTING, null,
                    "applicationCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog, String). If
     * componentType is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage2NullString() throws Exception {
        bean.initialize();
        try {
            bean.retrievePage("token", "pageName", "1.1", ConfluenceAssetType.APPLICATION_TESTING,
                    ConfluenceCatalog.DOT_NET, (String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}