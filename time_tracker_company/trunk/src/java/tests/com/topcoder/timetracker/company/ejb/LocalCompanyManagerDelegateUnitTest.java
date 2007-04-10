/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.ejb;

import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanySearchBuilder;
import com.topcoder.timetracker.company.UnitTestHelper;

import junit.framework.TestCase;

import java.sql.Connection;


/**
 * <p>
 * Tests functionality and error cases of <code>LocalCompanyManagerDelegate</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class LocalCompanyManagerDelegateUnitTest extends TestCase {
    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the user to do the audit for testing. */
    private String user = "user";

    /** Represents the <code>LocalCompanyManagerDelegate</code> instance used for testing. */
    private LocalCompanyManagerDelegate delegate = null;

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

        // create delegate
        UnitTestHelper.deployEJB();
        delegate = new LocalCompanyManagerDelegate(LocalCompanyManagerDelegate.class.getName());
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
     * <p>
     * Test the constructor <code>LocalCompanyManagerDelegate(String namespace)</code> when the namespace is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testLocalCompanyManagerDelegate_NullNamespace() throws Exception {
        try {
            new LocalCompanyManagerDelegate(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>LocalCompanyManagerDelegate(String namespace)</code> when the namespace is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testLocalCompanyManagerDelegate_EmptyNamespace() throws Exception {
        try {
            new LocalCompanyManagerDelegate(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>LocalCompanyManagerDelegate(String namespace)</code> when the namespace does not
     * exist, InstantiationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testLocalCompanyManagerDelegate_NotExistNamespace() throws Exception {
        try {
            new LocalCompanyManagerDelegate("NotExist");
            fail("InstantiationException should be thrown.");
        } catch (InstantiationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>LocalCompanyManagerDelegate(String namespace)</code> when the property value for
     * JndiReference is missing, InstantiationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testLocalCompanyManagerDelegate_MissingJndiReference() throws Exception {
        try {
            new LocalCompanyManagerDelegate(LocalCompanyManagerDelegate.class.getName() + ".MissingJndiReference");
            fail("InstantiationException should be thrown.");
        } catch (InstantiationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>LocalCompanyManagerDelegate(String namespace)</code> when the property value for
     * JndiReference is empty, InstantiationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testLocalCompanyManagerDelegate_EmptyJndiReference() throws Exception {
        try {
            new LocalCompanyManagerDelegate(LocalCompanyManagerDelegate.class.getName() + ".EmptyJndiReference");
            fail("InstantiationException should be thrown.");
        } catch (InstantiationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>LocalCompanyManagerDelegate(String namespace)</code> when the property value for
     * JndiReference is invalid, InstantiationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testLocalCompanyManagerDelegate_InvalidJndiReference() throws Exception {
        try {
            new LocalCompanyManagerDelegate(LocalCompanyManagerDelegate.class.getName() + ".InvalidJndiReference");
            fail("InstantiationException should be thrown.");
        } catch (InstantiationException e) {
            // good
        }
    }

    /**
     * Accuracy test for the constructor <code>LocalCompanyManagerDelegate(String namespace)</code>. No exception is
     * expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testLocalCompanyManagerDelegate_Accuracy() throws Exception {
        assertNotNull("The LocalCompanyManagerDelegate instance should be created.", delegate);
        assertNotNull("The localEJB should be created.",
            UnitTestHelper.getPrivateField(delegate.getClass(), delegate, "localEJB"));
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
        company = delegate.createCompany(company, user, true);

        // retrieve it and check it
        Company persisted = delegate.retrieveCompany(company.getId());
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
        company = delegate.createCompany(company, user, true);

        // retrieve it and check it
        Company persisted = delegate.retrieveCompany(1);
        UnitTestHelper.assertEquals(company, persisted);
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
        assertNull("No company should be returned.", delegate.retrieveCompany(1));
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
        delegate.createCompany(company, user, false);

        // change and update it
        company.setCompanyName("updatedCompanyName");
        company.setPassCode("updatedPassCode");
        delegate.updateCompany(company, user, true);

        // retrieve it and check it
        Company persisted = delegate.retrieveCompany(company.getId());
        UnitTestHelper.assertEquals(company, persisted);
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the user is
     * null and doAudit is false, no IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_NullUser() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        delegate.createCompany(company, user, false);
        delegate.deleteCompany(company, false, null);
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the user is
     * empty string and doAudit is false, no IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_EmptyUser() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        delegate.createCompany(company, user, false);
        delegate.deleteCompany(company, false, " ");
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
        delegate.createCompany(company, user, false);

        // delete it and check it
        delegate.deleteCompany(company, true, user);

        Company persisted = delegate.retrieveCompany(company.getId());
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
            delegate.createCompany(companies[i], user, false);
        }

        // list it and check it
        Company[] persist = delegate.listCompanies();
        UnitTestHelper.assertEquals(companies, persist);
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
            delegate.createCompany(companies[i], user, false);
        }

        // search it and check it
        CompanySearchBuilder build = new CompanySearchBuilder();
        build.createdByUser(user);

        Company[] persist = delegate.search(build.buildFilter());

        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the accuracy of method <code>createCompanies(Company[] companies, String user, boolean atomicBatchMode,
     * boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_AtomicAccuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
        }

        delegate.createCompanies(companies, user, true, false);

        // list it and check it
        Company[] persist = delegate.listCompanies();
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the accuracy of method <code>createCompanies(Company[] companies, String user, boolean atomicBatchMode,
     * boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NonAtomicAccuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
        }

        delegate.createCompanies(companies, user, false, false);

        // list it and check it
        Company[] persist = delegate.listCompanies();
        UnitTestHelper.assertEquals(companies, persist);
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
            delegate.createCompany(companies[i], user, false);
            ids[i] = companies[i].getId();
        }

        // list it and check it
        Company[] persist = delegate.retrieveCompanies(ids);
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the accuracy of method <code>updateCompanies(Company[] companies, String user, boolean atomicBatchMode,
     * boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_AtomicAccuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
            delegate.createCompany(companies[i], user, false);
        }

        // change and update it
        for (int i = 0; i < companies.length; i++) {
            companies[i].setCompanyName("updatedCompanyName" + i);
            companies[i].setPassCode("updatedCassCode" + i);
        }

        delegate.updateCompanies(companies, user, true, false);

        // list it and check it
        Company[] persist = delegate.listCompanies();
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the accuracy of method <code>updateCompanies(Company[] companies, String user, boolean atomicBatchMode,
     * boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NonAtomicAccuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
            delegate.createCompany(companies[i], user, false);
        }

        // change and update it
        for (int i = 0; i < companies.length; i++) {
            companies[i].setCompanyName("updatedCompanyName" + i);
            companies[i].setPassCode("updatedCassCode" + i);
        }

        delegate.updateCompanies(companies, user, false, false);

        // list it and check it
        Company[] persist = delegate.listCompanies();
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the accuracy of method <code>deleteCompanies(Company[] companies, boolean atomicBatchMode, boolean doAudit,
     * String user)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_AtomicAccuracy() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        delegate.createCompanies(companies, user, true, false);

        // delete it and check it
        delegate.deleteCompanies(companies, true, true, user);

        Company[] persisted = delegate.listCompanies();
        assertEquals("The companies should be removed.", 0, persisted.length);
    }

    /**
     * <p>
     * Test the accuracy of method <code>deleteCompanies(Company[] companies, boolean atomicBatchMode, boolean doAudit,
     * String user)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_NonAtomicAccuracy() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        delegate.createCompanies(companies, user, true, false);

        // delete it and check it
        delegate.deleteCompanies(companies, false, true, user);

        Company[] persisted = delegate.listCompanies();
        assertEquals("The companies should be removed.", 0, persisted.length);
    }
}
