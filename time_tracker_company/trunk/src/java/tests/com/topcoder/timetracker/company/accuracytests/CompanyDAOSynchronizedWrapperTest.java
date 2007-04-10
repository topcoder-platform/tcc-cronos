/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.accuracytests;

import java.sql.Connection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanyDAO;
import com.topcoder.timetracker.company.CompanyDAOSynchronizedWrapper;
import com.topcoder.timetracker.company.CompanySearchBuilder;
import com.topcoder.timetracker.company.daoimplementation.DbCompanyDAO;

/**
 * Accuracy test for <code>{@link com.topcoder.timetracker.company.CompanyDAOSynchronizedWrapper}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class CompanyDAOSynchronizedWrapperTest extends TestCase {
    /**
     * <p>
     * Represents the CompanyDAO for testing.
     * </p>
     */
    private DbCompanyDAO companyDAO;

    /**
     * <p>
     * Database connection for test data setup.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * The connection factory for creating the DbCompanyDAO.
     * </p>
     */
    private DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Represents the MockAuditManager for testing.
     * </p>
     */
    private MockAuditManager auditManager;

    /**
     * <p>
     * Represents the MockAddressManager for testing.
     * </p>
     */
    private MockAddressManager addressManager;

    /**
     * <p>
     * Represents the MockContactManager for testing.
     * </p>
     */
    private MockContactManager contactManager;

    /**
     * <p>
     * Represents the CompanyDAOSyncWrapper for testing.
     * </p>
     */
    private CompanyDAOSynchronizedWrapper wrapper;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadConfigs();
        connection = AccuracyTestHelper.getConnection();
        AccuracyTestHelper.prepareData(connection);

        auditManager = new MockAuditManager();
        addressManager = new MockAddressManager();
        contactManager = new MockContactManager();

        connectionFactory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        companyDAO = new DbCompanyDAO(connectionFactory, "informix_connect", "company_accuracy",
                contactManager, addressManager, auditManager);

        wrapper = new CompanyDAOSynchronizedWrapper(companyDAO);
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.clearDatabase(connection);
        AccuracyTestHelper.releaseConfigs();
    }

    /**
     * Accuracy test for
     * <code>{@link CompanyDAOSynchronizedWrapper#CompanyDAOSynchronizedWrapper(CompanyDAO)}</code>.
     */
    public void testConstructor_CompanyDAO() {
        assertNotNull("Failed to create CompanyDAOSynchronizedWrapper", wrapper);
    }

    /**
     * Accuracy test for
     * <code>{@link CompanyDAOSynchronizedWrapper#createCompanies(Company[],String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateCompanies_CompanyArray_String_boolean() throws Exception {
        Company[] companies = new Company[3];
        for (int i = 0; i < companies.length; i++) {
            companies[i] = AccuracyTestHelper.createCompanyBean();
            companies[i].setCompanyName("company - " + i);
            companies[i].setPassCode("RSA - " + i);
        }
        companies = wrapper.createCompanies(companies, "accuracy_user", false);

        Company[] actual = wrapper.listCompanies();
        for (int i = 0; i < actual.length; i++) {
            assertEquals("failed to create companies properly", companies[i].getCompanyName(), actual[i]
                    .getCompanyName());
            assertEquals("failed to create companies properly", companies[i].getPassCode(), actual[i]
                    .getPassCode());
        }
    }

    /**
     * Accuracy test for
     * <code>{@link CompanyDAOSynchronizedWrapper#updateCompanies(Company[],String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateCompanies_CompanyArray_String_boolean() throws Exception {
        Company[] companies = new Company[3];
        for (int i = 0; i < companies.length; i++) {
            companies[i] = AccuracyTestHelper.createCompanyBean();
            companies[i].setCompanyName("company - " + i);
            companies[i].setPassCode("RSA - " + i);
        }
        companies = wrapper.createCompanies(companies, "accuracy_user", false);

        companies = wrapper.listCompanies();

        for (int i = 0; i < companies.length; i++) {
            companies[i].setPassCode("NEW_RSA - " + i);
        }
        wrapper.updateCompanies(companies, "accuracy_user", true);
        Company[] actual = wrapper.listCompanies();
        for (int i = 0; i < actual.length; i++) {
            assertEquals("failed to update companies properly", companies[i].getPassCode(), actual[i]
                    .getPassCode());
        }

    }

    /**
     * Accuracy test for
     * <code>{@link CompanyDAOSynchronizedWrapper#deleteCompanies(Company[],boolean,String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteCompanies_CompanyArray_boolean_String() throws Exception {
        Company[] companies = new Company[3];
        for (int i = 0; i < companies.length; i++) {
            companies[i] = AccuracyTestHelper.createCompanyBean();
            companies[i].setCompanyName("company - " + i);
            companies[i].getAddress().setId(i+1);
            companies[i].getContact().setId(i+1);
            companies[i].setPassCode("RSA - " + i);
        }
        companies = wrapper.createCompanies(companies, "accuracy_user", true);

        companies = wrapper.listCompanies();

        wrapper.deleteCompanies(companies, true, "accuracy_user");

        Company[] actual = wrapper.listCompanies();
        assertEquals("failed to delete companies", 0, actual.length);

    }

    /**
     * Accuracy test for
     * <code>{@link CompanyDAOSynchronizedWrapper#createCompany(Company,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateCompany_Company_String_boolean() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = wrapper.createCompany(company, "accuracy_user", true);

        Company actual = wrapper.retrieveCompany(company.getId());
        assertEquals("failed to create company", company.getCompanyName(), actual.getCompanyName());
    }

    /**
     * Accuracy test for
     * <code>{@link CompanyDAOSynchronizedWrapper#updateCompany(Company,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateCompany_Company_String_boolean() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = wrapper.createCompany(company, "accuracy_user", true);

        company.setPassCode("NEW_RSA");

        wrapper.updateCompany(company, "accuracy_user", true);

        Company actual = wrapper.retrieveCompany(company.getId());
        assertEquals("failed to update company", company.getPassCode(), actual.getPassCode());

    }

    /**
     * Accuracy test for
     * <code>{@link CompanyDAOSynchronizedWrapper#deleteCompany(Company,boolean,String)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteCompany_Company_boolean_String() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = wrapper.createCompany(company, "accuracy_user", true);

        wrapper.deleteCompany(company, true, "accuracy_user");

        Company actual = wrapper.retrieveCompany(company.getId());
        assertNull("failed to delete company", actual);

    }

    /**
     * Accuracy test for <code>{@link CompanyDAOSynchronizedWrapper#search(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearch_Filter() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company.setId(1);
        company = wrapper.createCompany(company, "accuracy_user", true);
        CompanySearchBuilder builder = new CompanySearchBuilder();
        builder.createdByUser("accuracy_user");
        Company[] actual = wrapper.search(builder.buildFilter());
        assertEquals("failed to search using filter", 1, actual.length);
        assertEquals("failed to search using filter", company.getCompanyName(), actual[0].getCompanyName());
    }

    /**
     * Accuracy test for <code>{@link CompanyDAOSynchronizedWrapper#retrieveCompany(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRetrieveCompany_long() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = wrapper.createCompany(company, "accuracy_user", true);
        Company actual = wrapper.retrieveCompany(company.getId());
        assertEquals("failed to retrieve the company", company.getCompanyName(), actual.getCompanyName());
    }

    /**
     * Accuracy test for <code>{@link CompanyDAOSynchronizedWrapper#retrieveCompanies(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRetrieveCompanies_longArray() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = wrapper.createCompany(company, "accuracy_user", true);
        Company[] actual = wrapper.retrieveCompanies(new long[] { company.getId() });
        assertEquals("failed to retrieve the companies", company.getCompanyName(), actual[0].getCompanyName());

    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(CompanyDAOSynchronizedWrapperTest.class);
    }
}
