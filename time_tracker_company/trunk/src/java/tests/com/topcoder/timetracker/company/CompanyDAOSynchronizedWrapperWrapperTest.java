/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality of <code>CompanyDAOSynchronizedWrapper</code> class to check whether all the functionalities are
 * delegated to the company dao properly.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class CompanyDAOSynchronizedWrapperWrapperTest extends TestCase {
    /** Represents the <code>CompanyDAO</code> instance used for testing. */
    private MockCompanyDAO dao = null;

    /** Represents the <code>CompanyDAOSynchronizedWrapper</code> instance used for testing. */
    private CompanyDAOSynchronizedWrapper wrapper = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        dao = new MockCompanyDAO();
        wrapper = new CompanyDAOSynchronizedWrapper(dao);
    }

    /**
     * <p>
     * Test the accuracy of method <code>createCompany(Company company, String user, boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_Accuracy() throws Exception {
        wrapper.createCompany(null, null, true);
        assertTrue("Should be properly delegated.", dao.isCreateCompanyCalled());
    }

    /**
     * <p>
     * Test the accuracy of method <code>retrieveCompany(long id)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompany_Accuracy() throws Exception {
        wrapper.retrieveCompany(1);
        assertTrue("Should be properly delegated.", dao.isRetrieveCompanyCalled());
    }

    /**
     * <p>
     * Test the accuracy of method <code>updateCompany(Company company, String user, boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_Accuracy() throws Exception {
        wrapper.updateCompany(null, null, true);
        assertTrue("Should be properly delegated.", dao.isUpdateCompanyCalled());
    }

    /**
     * <p>
     * Test the accuracy of method <code>deleteCompany(Company company, boolean doAudit, String user)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_Accuracy() throws Exception {
        wrapper.deleteCompany(null, true, null);
        assertTrue("Should be properly delegated.", dao.isDeleteCompanyCalled());
    }

    /**
     * <p>
     * Test the accuracy of method <code>listCompanies()</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testListCompanies_Accuracy() throws Exception {
        wrapper.listCompanies();
        assertTrue("Should be properly delegated.", dao.isListCompaniesCalled());
    }

    /**
     * <p>
     * Test the accuracy of method <code>search(Filter filter)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearch_Accuracy() throws Exception {
        wrapper.search(null);
        assertTrue("Should be properly delegated.", dao.isSearchCalled());
    }

    /**
     * <p>
     * Test the accuracy of method <code>createCompanies(Company[] companies, String user, boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_Accuracy() throws Exception {
        wrapper.createCompanies(null, null, true);
        assertTrue("Should be properly delegated.", dao.isCreateCompaniesCalled());
    }

    /**
     * <p>
     * Test the accuracy of method <code>retrieveCompanies(long[] ids)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompanies_Accuracy() throws Exception {
        wrapper.retrieveCompanies(null);
        assertTrue("Should be properly delegated.", dao.isRetrieveCompaniesCalled());
    }

    /**
     * <p>
     * Test the accuracy of method <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_Accuracy() throws Exception {
        wrapper.updateCompanies(null, null, true);
        assertTrue("Should be properly delegated.", dao.isUpdateCompaniesCalled());
    }

    /**
     * <p>
     * Test the accuracy of method <code>deleteCompanies(Company[] companies, boolean doAudit, String user)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_Accuracy() throws Exception {
        wrapper.deleteCompanies(null, true, null);
        assertTrue("Should be properly delegated.", dao.isDeleteCompaniesCalled());
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
        wrapper.createCompaniesNonAtomically(null, null, true);
        assertTrue("Should be properly delegated.", dao.isCreateCompaniesNonAtomicallyCalled());
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
        wrapper.updateCompaniesNonAtomically(null, null, true);
        assertTrue("Should be properly delegated.", dao.isUpdateCompaniesNonAtomicallyCalled());
    }

    /**
     * <p>
     * Test the accuracy of method <code>deleteCompaniesNonAtomically(Company[] companies, boolean doAudit, String
     * user)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompaniesNonAtomically_Accuracy() throws Exception {
        wrapper.deleteCompaniesNonAtomically(null, true, null);
        assertTrue("Should be properly delegated.", dao.isDeleteCompaniesNonAtomicallyCalled());
    }
}
