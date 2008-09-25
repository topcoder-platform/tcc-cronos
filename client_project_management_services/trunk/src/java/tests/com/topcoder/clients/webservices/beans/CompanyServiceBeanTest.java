/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import com.topcoder.clients.manager.dao.DAOCompanyManager;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.webservices.CompanyServiceException;
import com.topcoder.clients.webservices.mock.MockCompanyDAO;
import com.topcoder.clients.webservices.mock.MockSessionContext;
import com.topcoder.clients.webservices.mock.TestBase;
import com.topcoder.clients.webservices.mock.TestHelper;

/**
 * Unit test for {@link CompanyServiceBean}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class CompanyServiceBeanTest extends TestBase {

    /**
     * Represents IAE fail message.
     */
    private static final String T_IAE = "IllegalArgumentException is expected to be thrown.";

    /**
     * Represents ISE fail message.
     */
    private static final String T_ISE = "IllegalStateException is expected to be thrown.";

    /**
     * Represents CSE fail message.
     */
    private static final String T_CSE = "CompanyServiceException is expected to be thrown.";

    /**
     * Represents CompanyServiceBean instance.
     */
    private CompanyServiceBean beanLocal;

    /**
     * Represents Session context instance.
     */
    private MockSessionContext session;

    /**
     * Setup environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        session = new MockSessionContext();
        beanLocal = new CompanyServiceBean();

        TestHelper.injectPropertyValue(CompanyServiceBean.class,
                "companyManagerFile", beanLocal, "companyManagerFileLocal.properties");
        TestHelper.injectPropertyValue(CompanyServiceBean.class,
                "companyManagerNamespace", beanLocal, "companyManagerNamespace");
        TestHelper.injectPropertyValue(CompanyServiceBean.class,
                "sessionContext", beanLocal, session);
        beanLocal.initialize();

        session.addRole("admin");
        session.setPrincipal(TestHelper.createPrincipal(1, "dev"));
    }

    /**
     * Teardown environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void tearDown() throws Exception {
        session.clearRoles();

        session = null;
        beanLocal = null;
        super.tearDown();
    }

    /**
     * Test for {@link CompanyServiceBean#CompanyServiceBean()}.
     */
    public void testCompanyServiceBean() {
        assertNotNull("Fail create new instance.", beanLocal);
    }

    /**
     * Test for {@link CompanyServiceBean#initialize()}.
     */
    public void testInitialize() {
        assertNotNull("Fail initialize bean.", TestHelper.getPrivateField(CompanyServiceBean.class,
                beanLocal, "companyManager"));
        assertNotNull("Fail initialize bean.", TestHelper.getPrivateField(CompanyServiceBean.class,
                beanLocal, "log"));
    }

    /**
     * Test for {@link CompanyServiceBean#initialize()}.
     *
     * Caused by: invalid configuration.
     * Expected : {@link ProjectServiceBeanConfigurationException}.
     */
    public void testInitialize_WithInvalidConfiguration() {
        TestHelper.injectPropertyValue(CompanyServiceBean.class,
                "companyManagerFile", beanLocal, "\t \n");

        try {
            beanLocal.initialize();
            fail("CompanyServiceBeanConfigurationException is expected to be thrown.");
        } catch (CompanyServiceBeanConfigurationException e) {
            // OK.
        }
    }

    /**
     * Test for {@link CompanyServiceBean#setVerboseLogging(boolean)}.
     */
    public void testSetVerboseLogging() {
        assertFalse("Fail setup property.", beanLocal.isVerboseLogging());
        beanLocal.setVerboseLogging(true);
        assertTrue("Fail setup property", beanLocal.isVerboseLogging());
    }

    /**
     * Test for {@link CompanyServiceBean#setLog(com.topcoder.util.log.Log)}.
     */
    public void testSetLog() {
        assertNotNull("Fail setup property", beanLocal.getLog());
        beanLocal.setLog(null);
        assertNull("Fail setup property", beanLocal.getLog());
    }

    /**
     * Test for {@link CompanyServiceBean#createCompany(Company)}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateCompany() throws Exception {
        Company company = TestHelper.createCompany(0);
        assertEquals("Initial value is invalid", 0, company.getId());

        company = beanLocal.createCompany(company);
        assertTrue("Fail create company.", company.getId() > 0);
    }

    /**
     * Test for {@link CompanyServiceBean#createCompany(Company)}.
     *
     * Caused by: Null company
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateCompany_WithNullCompany() throws Exception {
        try {
            beanLocal.createCompany(null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link CompanyServiceBean#createCompany(Company)}.
     *
     * Caused by: null manager.
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateCompany_WithNullManager() throws Exception {
        Company company = TestHelper.createCompany(0);

        try {
            TestHelper.injectPropertyValue(CompanyServiceBean.class,
                    "companyManager", beanLocal, null);
            beanLocal.createCompany(company);
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.}
        }
    }

    /**
     * Test for {@link CompanyServiceBean#createCompany(Company)}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateCompany_WithFailService() throws Exception {
        Company company = TestHelper.createCompany(0);

        try {
            getCompanyDAO().setFlag(true);
            beanLocal.createCompany(company);
            fail(T_CSE);
        } catch (CompanyServiceException e) {
            // OK.
        }
    }

    /**
     * Test for {@link CompanyServiceBean#updateCompany(Company)}.
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateCompany() throws Exception {
        Company company = beanLocal.createCompany(TestHelper.createCompany(0));

        String name = "new name";
        assertFalse("Initial value is invalid.", name.equals(company.getName()));
        company.setName(name);
        company = beanLocal.updateCompany(company);
        assertTrue("Fail update company.", name.equals(company.getName()));
    }

    /**
     * Test for {@link CompanyServiceBean#updateCompany(Company)}.
     *
     * Caused by: Null company.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateCompany_WithNullCompany() throws Exception {
        try {
            beanLocal.updateCompany(null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link CompanyServiceBean#updateCompany(Company)}.
     *
     * Caused by: Null manager.
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateCompany_WithNullManager() throws Exception {
        Company company = beanLocal.createCompany(TestHelper.createCompany(0));

        String name = "new name";
        assertFalse("Initial value is invalid.", name.equals(company.getName()));
        company.setName(name);

        TestHelper.injectPropertyValue(CompanyServiceBean.class,
                "companyManager", beanLocal, null);
        try {
            beanLocal.updateCompany(company);
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link CompanyServiceBean#updateCompany(Company)}.
     *
     * Caused by: Fail services.
     * Expected : {@link CompanyServiceException}.
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateCompany_WithFailService() throws Exception {
        Company company = beanLocal.createCompany(TestHelper.createCompany(0));

        String name = "new name";
        assertFalse("Initial value is invalid.", name.equals(company.getName()));
        company.setName(name);

        try {
            getCompanyDAO().setFlag(true);
            beanLocal.updateCompany(company);
            fail(T_CSE);
        } catch (CompanyServiceException e) {
            // OK.
        }
    }

    /**
     * Test for {@link CompanyServiceBean#deleteCompany(Company)}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteCompany() throws Exception {
        beanLocal.setVerboseLogging(true);
        Company company = beanLocal.createCompany(TestHelper.createCompany(0));

        assertFalse("Initial vaue is invalid.", company.isDeleted());
        company = beanLocal.deleteCompany(company);
        assertTrue("Fail delete company", company.isDeleted());
    }

    /**
     * Test for {@link CompanyServiceBean#deleteCompany(Company)}.
     *
     * Caused by: Null company.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteCompany_WithNullCompany() throws Exception {
        beanLocal.setVerboseLogging(true);
        try {
            beanLocal.deleteCompany(null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link CompanyServiceBean#deleteCompany(Company)}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteCompany_WithNullManager() throws Exception {
        beanLocal.setVerboseLogging(true);
        Company company = beanLocal.createCompany(TestHelper.createCompany(0));

        assertFalse("Initial vaue is invalid.", company.isDeleted());

        TestHelper.injectPropertyValue(CompanyServiceBean.class,
                "companyManager", beanLocal, null);
        try {
            beanLocal.deleteCompany(company);
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link CompanyServiceBean#deleteCompany(Company)}.
     *
     * Caused by: Fail service.
     * Expected : {@link CompanyServiceException}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteCompany_WithFailService() throws Exception {
        beanLocal.setVerboseLogging(true);
        Company company = beanLocal.createCompany(TestHelper.createCompany(0));

        assertFalse("Initial vaue is invalid.", company.isDeleted());

        try {
            getCompanyDAO().setFlag(true);
            beanLocal.deleteCompany(company);
            fail(T_CSE);
        } catch (CompanyServiceException e) {
            // OK.
        }
    }

    /**
     * Simple getter for Company DAO.
     *
     * @return company DAO instance.
     * @throws Exception into JUnit.
     */
    private MockCompanyDAO getCompanyDAO() throws Exception {
        DAOCompanyManager manager = (DAOCompanyManager) TestHelper.getPrivateField(CompanyServiceBean.class,
                beanLocal, "companyManager");
        return (MockCompanyDAO) TestHelper.getPrivateField(DAOCompanyManager.class, manager, "companyDAO");
    }
}
