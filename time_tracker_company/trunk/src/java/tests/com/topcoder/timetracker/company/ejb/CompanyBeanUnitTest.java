/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.ejb;

import com.topcoder.timetracker.company.BatchCompanyDAOException;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanyDAOException;
import com.topcoder.timetracker.company.CompanyNotFoundException;
import com.topcoder.timetracker.company.CompanySearchBuilder;
import com.topcoder.timetracker.company.UnitTestHelper;

import junit.framework.TestCase;

import java.security.Identity;
import java.security.Principal;

import java.sql.Connection;

import java.util.Properties;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.SessionContext;
import javax.ejb.TimerService;

import javax.transaction.UserTransaction;

import javax.xml.rpc.handler.MessageContext;


/**
 * <p>
 * Tests functionality and error cases of <code>CompanyBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class CompanyBeanUnitTest extends TestCase {
    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the user to do the audit for testing. */
    private String user = "user";

    /** Represents the <code>SessionContext</code> instance used for testing. */
    private SessionContext context;

    /** Represents the <code>CompanyBean</code> instance used for testing. */
    private CompanyBean bean = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();
        connection = UnitTestHelper.getConnection();
        UnitTestHelper.prepareData(connection);

        // create bean
        UnitTestHelper.deployEJB();
        context = new MockSessionContext();
        bean = new CompanyBean();
        bean.ejbCreate();
        bean.setSessionContext(context);
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        try {
            UnitTestHelper.clearConfig();
            UnitTestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        UnitTestHelper.undeployEJB();
    }

    /**
     * Accuracy test for the constructor <code>AuditSessionBean()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAuditSessionBean_Accuracy() throws Exception {
        assertNotNull("The CompanyBean instance should be created.", bean);
    }

    /**
     * <p>
     * Accuracy test for the method <code>ejbCreate()</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbCreate_Accuracy() throws Exception {
        assertNotNull("The dao should be created successfully.",
            UnitTestHelper.getPrivateField(bean.getClass(), bean, "companyDAO"));
    }

    /**
     * Accuracy test for the method <code>ejbRemove()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbRemove_Accuracy() throws Exception {
        bean.ejbRemove();
    }

    /**
     * Accuracy test for the method <code>ejbActivate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbActivate_Accuracy() throws Exception {
        bean.ejbActivate();
    }

    /**
     * Accuracy test for the method <code>ejbPassivate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbPassivate_Accuracy() throws Exception {
        bean.ejbPassivate();
    }

    /**
     * Accuracy test for the method <code>setSessionContext(SessionContext)</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSetSessionContext_Accuracy() throws Exception {
        bean.setSessionContext(context);
        assertEquals("The context should be set properly.", context,
            UnitTestHelper.getPrivateField(bean.getClass(), bean, "sessionContext"));
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the company's
     * companyName is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_NullCompanyName() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "companyName", null);

        try {
            bean.createCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the company's
     * passCode is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_NullPassCode() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "passCode", null);

        try {
            bean.createCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the company's
     * contact is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_NullContact() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "contact", null);

        try {
            bean.createCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the company's
     * address is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_NullAddress() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "address", null);

        try {
            bean.createCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the user is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_NullUser() throws Exception {
        Company company = UnitTestHelper.buildCompany();

        try {
            bean.createCompany(company, null, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the user is
     * empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_EmptyUser() throws Exception {
        Company company = UnitTestHelper.buildCompany();

        try {
            bean.createCompany(company, " ", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the company is
     * duplicated, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_DuplicateCompany() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        bean.createCompany(company, user, true);

        try {
            bean.createCompany(company, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>createCompany(Company company, String user, boolean doAudit)</code>. The id
     * will be auto generated.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_Accuracy1() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        company = bean.createCompany(company, user, true);

        // retrieve it and check it
        Company persisted = bean.retrieveCompany(company.getId());
        UnitTestHelper.assertEquals(company, persisted);
    }

    /**
     * <p>
     * Test the accuracy of method <code>createCompany(Company company, String user, boolean doAudit)</code>. The id
     * will be pre-defined.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_Accuracy2() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        company.setId(1);
        company = bean.createCompany(company, user, true);

        // retrieve it and check it
        Company persisted = bean.retrieveCompany(1);
        UnitTestHelper.assertEquals(company, persisted);
    }

    /**
     * <p>
     * Test the method of <code>retrieveCompany(long id)</code> when the id is not positive, IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompany_InvalidId() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        bean.createCompany(company, user, true);

        try {
            bean.retrieveCompany(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>retrieveCompany(long id)</code> when company with the given company does not exist,
     * null will be returned.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompany_NotExistCompany() throws Exception {
        assertNull("No company should be returned.", bean.retrieveCompany(1));
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the company is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NullCompany() throws Exception {
        try {
            bean.updateCompany(null, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the company's
     * companyName is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NullCompanyName() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "companyName", null);

        try {
            bean.updateCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the company's
     * passCode is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NullPassCode() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "passCode", null);

        try {
            bean.updateCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the company's
     * contact is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NullContact() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "contact", null);

        try {
            bean.updateCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the company's
     * address is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NullAddress() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "address", null);

        try {
            bean.updateCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the user is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NullUser() throws Exception {
        Company company = UnitTestHelper.buildCompany();

        try {
            bean.updateCompany(company, null, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the user is
     * empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_EmptyUser() throws Exception {
        Company company = UnitTestHelper.buildCompany();

        try {
            bean.updateCompany(company, " ", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the company
     * does not exist, CompanyNotFoundException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NotExistCompany() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        company.setId(1);

        try {
            bean.updateCompany(company, user, true);
            fail("CompanyNotFoundException should be thrown.");
        } catch (CompanyNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>updateCompany(Company company, String user, boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_Accuracy1() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        bean.createCompany(company, user, false);

        // change and update it
        company.setCompanyName("updatedCompanyName");
        company.setPassCode("updatedPassCode");
        bean.updateCompany(company, user, true);

        // retrieve it and check it
        Company persisted = bean.retrieveCompany(company.getId());
        UnitTestHelper.assertEquals(company, persisted);
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the company is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_NullCompany() throws Exception {
        try {
            bean.deleteCompany(null, true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the company's
     * id is not positive, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_InvalidCompanyId() throws Exception {
        Company company = UnitTestHelper.buildCompany();

        try {
            bean.deleteCompany(company, true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the company's
     * id does not exist, CompanyNotFoundException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_NotExistCompanyId() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        company.setId(1);

        try {
            bean.deleteCompany(company, true, user);
            fail("CompanyNotFoundException should be thrown.");
        } catch (CompanyNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the user is
     * null and doAudit is true, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_NullUser1() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        bean.createCompany(company, user, false);

        try {
            bean.deleteCompany(company, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the user is
     * empty string and doAudit is true, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_EmptyUser1() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        bean.createCompany(company, user, false);

        try {
            bean.deleteCompany(company, true, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the user is
     * null and doAudit is false, no IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_NullUser2() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        bean.createCompany(company, user, false);
        bean.deleteCompany(company, false, null);
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the user is
     * empty string and doAudit is false, no IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_EmptyUser2() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        bean.createCompany(company, user, false);
        bean.deleteCompany(company, false, " ");
    }

    /**
     * <p>
     * Test the accuracy of method <code>deleteCompany(Company company, boolean doAudit, String user)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_Accuracy1() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        bean.createCompany(company, user, false);

        // delete it and check it
        bean.deleteCompany(company, true, user);

        Company persisted = bean.retrieveCompany(company.getId());
        assertNull("The company should be removed.", persisted);
    }

    /**
     * <p>
     * Test the accuracy of method <code>listCompanies()</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testListCompanies_Accuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
            bean.createCompany(companies[i], user, false);
        }

        // list it and check it
        Company[] persist = bean.listCompanies();
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the method of <code>search(Filter filter)</code> when the filter is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearch_NullFilter() throws Exception {
        try {
            bean.search(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>search(Filter filter)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearch_Accuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
            companies[i].setId(i + 1);
            bean.createCompany(companies[i], user, false);
        }

        // search it and check it
        CompanySearchBuilder build = new CompanySearchBuilder();
        build.createdByUser(user);

        Company[] persist = bean.search(build.buildFilter());

        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * companies is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullCompanies() throws Exception {
        try {
            bean.createCompanies(null, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * companies contains null element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullCompany() throws Exception {
        try {
            bean.createCompanies(new Company[1], user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's companiesName is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullCompanyName() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "companyName", null);

        try {
            bean.createCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's passCode is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullPassCode() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "passCode", null);

        try {
            bean.createCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's contact is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullContact() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "contact", null);

        try {
            bean.createCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's address is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullAddress() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "address", null);

        try {
            bean.createCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company is duplicated, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_DuplicateCompany() throws Exception {
        Company companies1 = UnitTestHelper.buildCompany();
        Company companies2 = UnitTestHelper.buildCompany();
        companies1.setId(1);
        companies2.setId(1);

        try {
            bean.createCompanies(new Company[] {companies1, companies2}, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the user
     * is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullUser() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};

        try {
            bean.createCompanies(companies, null, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the user
     * is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_EmptyUser() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};

        try {
            bean.createCompanies(companies, " ", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>createCompanies(Company[] companies, String user, boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_Accuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
        }

        bean.createCompanies(companies, user, false);

        // list it and check it
        Company[] persist = bean.listCompanies();
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the method of <code>retrieveCompanies(long[] ids)</code> when the ids is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompanies_NullIds() throws Exception {
        try {
            bean.retrieveCompanies(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>retrieveCompanies(long[] ids)</code> when the ids contains non-positive id,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompanies_InvalidId() throws Exception {
        try {
            bean.retrieveCompanies(new long[1]);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>retrieveCompanies(long[] ids)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompanies_Accuracy() throws Exception {
        Company[] companies = new Company[5];
        long[] ids = new long[companies.length];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
            bean.createCompany(companies[i], user, false);
            ids[i] = companies[i].getId();
        }

        // list it and check it
        Company[] persist = bean.retrieveCompanies(ids);
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the accuracy of method <code>retrieveCompanies(long[] ids)</code>, when the records do not exist.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompanies_NotExistAccuracy() throws Exception {
        long[] ids = new long[5];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = i + 1;
        }

        // list it and check it
        Company[] persist = bean.retrieveCompanies(ids);
        UnitTestHelper.assertEquals(new Company[ids.length], persist);
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * companies is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullCompanies() throws Exception {
        try {
            bean.updateCompanies(null, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * companies contains null element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullCompany() throws Exception {
        try {
            bean.updateCompanies(new Company[1], user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's companiesName is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullCompanyName() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "companyName", null);

        try {
            bean.updateCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's passCode is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullPassCode() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "passCode", null);

        try {
            bean.updateCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's contact is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullContact() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "contact", null);

        try {
            bean.updateCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's address is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullAddress() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "address", null);

        try {
            bean.updateCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the user
     * is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullUser() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};

        try {
            bean.updateCompanies(companies, null, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the user
     * is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_EmptyUser() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};

        try {
            bean.updateCompanies(companies, " ", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when
     * the some of the company does not exist, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_CompanyNotExist() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        companies[0].setId(1);

        try {
            bean.updateCompanies(companies, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_Accuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
            bean.createCompany(companies[i], user, false);
        }

        // change and update it
        for (int i = 0; i < companies.length; i++) {
            companies[i].setCompanyName("updatedCompanyName" + i);
            companies[i].setPassCode("updatedCassCode" + i);
        }

        bean.updateCompanies(companies, user, false);

        // list it and check it
        Company[] persist = bean.listCompanies();
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the
     * companies is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_NullCompanies() throws Exception {
        try {
            bean.deleteCompanies(null, true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the
     * companies contains null element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_NullCompany() throws Exception {
        try {
            bean.deleteCompanies(new Company[1], true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the
     * company's id is not positive, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_InvalidCompanyId() throws Exception {
        Company company = UnitTestHelper.buildCompany();

        try {
            bean.deleteCompanies(new Company[] {company}, true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the
     * company's id is duplicated, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_DuplicateCompanyId() throws Exception {
        Company companies1 = UnitTestHelper.buildCompany();
        Company companies2 = UnitTestHelper.buildCompany();
        companies1.setId(1);
        companies2.setId(1);

        try {
            bean.deleteCompanies(new Company[] {companies1, companies2}, true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the user
     * is null and doAudit is true, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_NullUser1() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        bean.createCompanies(companies, user, false);

        try {
            bean.deleteCompanies(companies, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the user
     * is empty string and doAudit is true, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_EmptyUser1() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        bean.createCompanies(companies, user, false);

        try {
            bean.deleteCompanies(companies, true, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the user
     * is null and doAudit is false, no IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_NullUser2() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        bean.createCompanies(companies, user, false);
        bean.deleteCompanies(companies, false, null);
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the user
     * is empty string and doAudit is false, no IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_EmptyUser2() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        bean.createCompanies(companies, user, false);
        bean.deleteCompanies(companies, false, " ");
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when
     * the some of the company does not exist, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_CompanyNotExist() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        companies[0].setId(1);

        try {
            bean.deleteCompanies(companies, true, user);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>deleteCompanies(Company companies, boolean doAudit, String user)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_Accuracy() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        bean.createCompanies(companies, user, false);

        // delete it and check it
        bean.deleteCompanies(companies, true, user);

        Company[] persisted = bean.listCompanies();
        assertEquals("The companies should be removed.", 0, persisted.length);
    }

    /**
     * <p>
     * Test the method of <code>createCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the companies is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompaniesNonAtomically_NullCompanies() throws Exception {
        try {
            bean.createCompaniesNonAtomically(null, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the companies contains null element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompaniesNonAtomically_NullCompany() throws Exception {
        try {
            bean.createCompaniesNonAtomically(new Company[1], user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the company's companiesName is null, BatchCompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompaniesNonAtomically_NullCompanyName() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "companyName", null);

        try {
            bean.createCompaniesNonAtomically(companies, user, true);
            fail("BatchCompanyDAOException should be thrown.");
        } catch (BatchCompanyDAOException e) {
            assertEquals("The count of the problem companies should be correct.", 1, e.getProblemCompanies().length);
            assertEquals("The problem companies should be correct.", companies[0], e.getProblemCompanies()[0]);
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the company's passCode is null, BatchCompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompaniesNonAtomically_NullPassCode() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "passCode", null);

        try {
            bean.createCompaniesNonAtomically(companies, user, true);
            fail("BatchCompanyDAOException should be thrown.");
        } catch (BatchCompanyDAOException e) {
            assertEquals("The count of the problem companies should be correct.", 1, e.getProblemCompanies().length);
            assertEquals("The problem companies should be correct.", companies[0], e.getProblemCompanies()[0]);
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the company's contact is null, BatchCompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompaniesNonAtomically_NullContact() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "contact", null);

        try {
            bean.createCompaniesNonAtomically(companies, user, true);
            fail("BatchCompanyDAOException should be thrown.");
        } catch (BatchCompanyDAOException e) {
            assertEquals("The count of the problem companies should be correct.", 1, e.getProblemCompanies().length);
            assertEquals("The problem companies should be correct.", companies[0], e.getProblemCompanies()[0]);
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the company's address is null, BatchCompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompaniesNonAtomically_NullAddress() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "address", null);

        try {
            bean.createCompaniesNonAtomically(companies, user, true);
            fail("BatchCompanyDAOException should be thrown.");
        } catch (BatchCompanyDAOException e) {
            assertEquals("The count of the problem companies should be correct.", 1, e.getProblemCompanies().length);
            assertEquals("The problem companies should be correct.", companies[0], e.getProblemCompanies()[0]);
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the company is duplicated, BatchCompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompaniesNonAtomically_DuplicateCompanies() throws Exception {
        Company companies1 = UnitTestHelper.buildCompany();
        Company companies2 = UnitTestHelper.buildCompany();
        companies1.setId(1);
        companies2.setId(1);

        try {
            bean.createCompaniesNonAtomically(new Company[] {companies1, companies2}, user, true);
            fail("BatchCompanyDAOException should be thrown.");
        } catch (BatchCompanyDAOException e) {
            assertEquals("The count of the problem companies should be correct.", 1, e.getProblemCompanies().length);
            assertEquals("The problem companies should be correct.", companies2, e.getProblemCompanies()[0]);
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the user is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompaniesNonAtomically_NullUser() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};

        try {
            bean.createCompaniesNonAtomically(companies, null, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the user is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompaniesNonAtomically_EmptyUser() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};

        try {
            bean.createCompanies(companies, " ", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>createCompaniesNonAtomically(Company[] companies, String user, boolean
     * doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompaniesNonAtomically_Accuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
        }

        bean.createCompaniesNonAtomically(companies, user, false);

        // list it and check it
        Company[] persist = bean.listCompanies();
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the method of <code>updateCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the companies is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompaniesNonAtomically_NullCompanies() throws Exception {
        try {
            bean.updateCompaniesNonAtomically(null, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the companies contains null element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompaniesNonAtomically_NullCompany() throws Exception {
        try {
            bean.updateCompaniesNonAtomically(new Company[1], user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the company's companiesName is null, BatchCompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompaniesNonAtomically_NullCompanyName() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "companyName", null);

        try {
            bean.updateCompaniesNonAtomically(companies, user, true);
            fail("BatchCompanyDAOException should be thrown.");
        } catch (BatchCompanyDAOException e) {
            assertEquals("The count of the problem companies should be correct.", 1, e.getProblemCompanies().length);
            assertEquals("The problem companies should be correct.", companies[0], e.getProblemCompanies()[0]);
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the company's passCode is null, BatchCompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompaniesNonAtomically_NullPassCode() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "passCode", null);

        try {
            bean.updateCompaniesNonAtomically(companies, user, true);
            fail("BatchCompanyDAOException should be thrown.");
        } catch (BatchCompanyDAOException e) {
            assertEquals("The count of the problem companies should be correct.", 1, e.getProblemCompanies().length);
            assertEquals("The problem companies should be correct.", companies[0], e.getProblemCompanies()[0]);
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the company's contact is null, BatchCompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompaniesNonAtomically_NullContact() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "contact", null);

        try {
            bean.updateCompaniesNonAtomically(companies, user, true);
            fail("BatchCompanyDAOException should be thrown.");
        } catch (BatchCompanyDAOException e) {
            assertEquals("The count of the problem companies should be correct.", 1, e.getProblemCompanies().length);
            assertEquals("The problem companies should be correct.", companies[0], e.getProblemCompanies()[0]);
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the company's address is null, BatchCompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompaniesNonAtomically_NullAddress() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "address", null);

        try {
            bean.updateCompaniesNonAtomically(companies, user, true);
            fail("BatchCompanyDAOException should be thrown.");
        } catch (BatchCompanyDAOException e) {
            assertEquals("The count of the problem companies should be correct.", 1, e.getProblemCompanies().length);
            assertEquals("The problem companies should be correct.", companies[0], e.getProblemCompanies()[0]);
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the user is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompaniesNonAtomically_NullUser() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};

        try {
            bean.updateCompaniesNonAtomically(companies, null, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the user is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompaniesNonAtomically_EmptyUser() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};

        try {
            bean.updateCompaniesNonAtomically(companies, " ", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)</code>
     * when the some of the company does not exist, BatchCompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompaniesNonAtomically_CompanyNotExist() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        companies[0].setId(1);

        try {
            bean.updateCompaniesNonAtomically(companies, user, true);
            fail("BatchCompanyDAOException should be thrown.");
        } catch (BatchCompanyDAOException e) {
            assertEquals("The count of the problem companies should be correct.", 1, e.getProblemCompanies().length);
            assertEquals("The problem companies should be correct.", companies[0], e.getProblemCompanies()[0]);
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>updateCompaniesNonAtomically(Company[] companies, String user, boolean
     * doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompaniesNonAtomically_Accuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
            bean.createCompany(companies[i], user, false);
        }

        // change and update it
        for (int i = 0; i < companies.length; i++) {
            companies[i].setCompanyName("updatedCompanyName" + i);
            companies[i].setPassCode("updatedCassCode" + i);
        }

        bean.updateCompaniesNonAtomically(companies, user, false);

        // list it and check it
        Company[] persist = bean.listCompanies();
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the method of <code>deleteCompaniesNonAtomically(Company companies, boolean doAudit, String user)</code>
     * when the companies is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompaniesNonAtomically_NullCompanies() throws Exception {
        try {
            bean.deleteCompaniesNonAtomically(null, true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompaniesNonAtomically(Company companies, boolean doAudit, String user)</code>
     * when the companies contains null element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompaniesNonAtomically_NullCompany() throws Exception {
        try {
            bean.deleteCompaniesNonAtomically(new Company[1], true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompaniesNonAtomically(Company companies, boolean doAudit, String user)</code>
     * when the company's id is not positive, BatchCompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompaniesNonAtomically_InvalidCompanyId() throws Exception {
        Company company = UnitTestHelper.buildCompany();

        try {
            bean.deleteCompaniesNonAtomically(new Company[] {company}, true, user);
            fail("BatchCompanyDAOException should be thrown.");
        } catch (BatchCompanyDAOException e) {
            assertEquals("The count of the problem companies should be correct.", 1, e.getProblemCompanies().length);
            assertEquals("The problem companies should be correct.", company, e.getProblemCompanies()[0]);
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompaniesNonAtomically(Company companies, boolean doAudit, String user)</code>
     * when the company's id is duplicated, BatchCompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompaniesNonAtomically_DuplicateCompanyId() throws Exception {
        Company companies1 = UnitTestHelper.buildCompany();
        Company companies2 = UnitTestHelper.buildCompany();
        companies1.setId(1);
        bean.createCompany(companies1, user, false);
        companies2.setId(1);

        try {
            bean.deleteCompaniesNonAtomically(new Company[] {companies1, companies2}, true, user);
            fail("BatchCompanyDAOException should be thrown.");
        } catch (BatchCompanyDAOException e) {
            assertEquals("The count of the problem companies should be correct.", 1, e.getProblemCompanies().length);
            assertEquals("The problem companies should be correct.", companies2, e.getProblemCompanies()[0]);
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompaniesNonAtomically(Company companies, boolean doAudit, String user)</code>
     * when the user is null and doAudit is true, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompaniesNonAtomically_NullUser1() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        bean.createCompanies(companies, user, false);

        try {
            bean.deleteCompaniesNonAtomically(companies, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompaniesNonAtomically(Company companies, boolean doAudit, String user)</code>
     * when the user is empty string and doAudit is true, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompaniesNonAtomically_EmptyUser1() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        bean.createCompanies(companies, user, false);

        try {
            bean.deleteCompaniesNonAtomically(companies, true, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompaniesNonAtomically(Company companies, boolean doAudit, String user)</code>
     * when the user is null and doAudit is false, no IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompaniesNonAtomically_NullUser2() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        bean.createCompanies(companies, user, false);
        bean.deleteCompaniesNonAtomically(companies, false, null);
    }

    /**
     * <p>
     * Test the method of <code>deleteCompaniesNonAtomically(Company companies, boolean doAudit, String user)</code>
     * when the user is empty string and doAudit is false, no IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompaniesNonAtomically_EmptyUser2() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        bean.createCompanies(companies, user, false);
        bean.deleteCompaniesNonAtomically(companies, false, " ");
    }

    /**
     * <p>
     * Test the method of <code>deleteCompaniesNonAtomically(Company companies, boolean doAudit, String user)</code>
     * when the some of the company does not exist, BatchCompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompaniesNonAtomically_CompanyNotExist() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        companies[0].setId(1);

        try {
            bean.deleteCompaniesNonAtomically(companies, true, user);
            fail("BatchCompanyDAOException should be thrown.");
        } catch (BatchCompanyDAOException e) {
            assertEquals("The count of the problem companies should be correct.", 1, e.getProblemCompanies().length);
            assertEquals("The problem companies should be correct.", companies[0], e.getProblemCompanies()[0]);
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>deleteCompaniesNonAtomically(Company companies, boolean doAudit, String
     * user)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompaniesNonAtomically_Accuracy() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        bean.createCompanies(companies, user, false);

        // delete it and check it
        bean.deleteCompaniesNonAtomically(companies, true, user);

        Company[] persisted = bean.listCompanies();
        assertEquals("The companies should be removed.", 0, persisted.length);
    }

    /**
     * <p>
     * A mock class which extends SessionContext for testing.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 3.2
     */
    private class MockSessionContext implements SessionContext {
        /** Represents the RollbackOnly value. */
        private boolean rollbackOnly = false;

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getEJBLocalObject()
         */
        public EJBLocalObject getEJBLocalObject() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getEJBObject()
         */
        public EJBObject getEJBObject() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getMessageContext()
         */
        public MessageContext getMessageContext() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @param arg0 arg
         *
         * @return null.
         *
         * @see SessionContext#getBusinessObject(java.lang.Class)
         */
        public Object getBusinessObject(Class arg0) {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getEJBHome()
         */
        public EJBHome getEJBHome() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getEJBLocalHome()
         */
        public EJBLocalHome getEJBLocalHome() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getEnvironment()
         */
        public Properties getEnvironment() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getCallerIdentity()
         * @deprecated
         */
        public Identity getCallerIdentity() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getCallerPrincipal()
         */
        public Principal getCallerPrincipal() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @param arg0 arg
         *
         * @return null.
         *
         * @see javax.ejb.SessionContext#isCallerInRole(Identity)
         * @deprecated
         */
        public boolean isCallerInRole(Identity arg0) {
            return false;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @param arg0 arg
         *
         * @return null.
         *
         * @see javax.ejb.SessionContext#isCallerInRole(String)
         */
        public boolean isCallerInRole(String arg0) {
            return false;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getUserTransaction()
         */
        public UserTransaction getUserTransaction() {
            return null;
        }

        /**
         * @see SessionContext#setRollbackOnly()
         */
        public void setRollbackOnly() {
            rollbackOnly = true;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getRollbackOnly()
         */
        public boolean getRollbackOnly() {
            return rollbackOnly;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getTimerService()
         */
        public TimerService getTimerService() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @param arg0 arg.
         *
         * @return null.
         *
         * @see SessionContext#lookup(String)
         */
        public Object lookup(String arg0) {
            return null;
        }
    }
}
