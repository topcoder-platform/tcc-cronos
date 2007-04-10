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
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanySearchBuilder;
import com.topcoder.timetracker.company.daoimplementation.DbCompanyDAO;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.ContactManager;

/**
 * Accuracy test for <code>{@link com.topcoder.timetracker.company.daoimplementation.DbCompanyDAO}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class DbCompanyDAOTest extends TestCase {
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
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory,String,String,String,String,String)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testConstructor_accuracy_1() throws Exception {
        DbCompanyDAO dao = new DbCompanyDAO(connectionFactory, "informix_connect", "company_accuracy",
                "contact_manager", "address_manager", "audit_manager");
        assertNotNull("failed to create DbCompanyDAO", dao);
    }

    /**
     * Accuracy test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory,String,String,ContactManager,AddressManager,AuditManager)}</code>.
     */
    public void testConstructor_accuracy_2() {
        assertNotNull("failed to create DbCompanyDAO", companyDAO);
    }

    /**
     * Accuracy test for <code>{@link DbCompanyDAO#createCompanies(Company[],String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodCreateCompanies_CompanyArray_String_boolean() throws Exception {
        Company[] companies = new Company[3];
        for (int i = 0; i < companies.length; i++) {
            companies[i] = AccuracyTestHelper.createCompanyBean();
            companies[i].setCompanyName("company - " + i);
            companies[i].setPassCode("RSA - " + i);
        }
        companies = companyDAO.createCompanies(companies, "accuracy_user", true);

        Company[] actual = companyDAO.listCompanies();
        for (int i = 0; i < actual.length; i++) {
            assertEquals("failed to create companies properly", companies[i].getCompanyName(), actual[i]
                    .getCompanyName());
            assertEquals("failed to create companies properly", companies[i].getPassCode(), actual[i]
                    .getPassCode());
        }

        // Check ContactManager
        assertEquals("failed to create companies properly", contactManager.getAllContacts().length, 1);
        // Check AddressManager
        assertEquals("failed to create companies properly", addressManager.getAllAddresses().length, 1);
        // Check AuditManager
        assertEquals("failed to create companies properly, audit not correct",
                auditManager.listRecords().length, 3);

        assertEquals("failed to create companies properly, audit not correct", auditManager.listRecords()[0]
                .getDetails().length, 7);
    }

    /**
     * Accuracy test for <code>{@link DbCompanyDAO#updateCompanies(Company[],String,boolean)}</code>.
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
        companies = companyDAO.createCompanies(companies, "accuracy_user", true);

        companies = companyDAO.listCompanies();

        for (int i = 0; i < companies.length; i++) {
            companies[i].setPassCode("NEW_RSA - " + i);
        }
        companyDAO.updateCompanies(companies, "accuracy_user", true);
        Company[] actual = companyDAO.listCompanies();
        for (int i = 0; i < actual.length; i++) {
            assertEquals("failed to update companies properly", companies[i].getPassCode(), actual[i]
                    .getPassCode());
        }
        // Check ContactManager
        assertEquals("failed to update companies properly", contactManager.getAllContacts().length, 1);
        // Check AddressManager
        assertEquals("failed to update companies properly", addressManager.getAllAddresses().length, 1);
        // Check AuditManager
        assertEquals("failed to update companies properly, audit not correct",
                auditManager.listRecords().length, 6); // including creation
    }

    /**
     * Accuracy test for <code>{@link DbCompanyDAO#deleteCompanies(Company[],boolean,String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteCompanies_CompanyArray_boolean_String() throws Exception {
        Company[] companies = new Company[3];
        for (int i = 0; i < companies.length; i++) {
            companies[i] = AccuracyTestHelper.createCompanyBean();
            companies[i].setCompanyName("company - " + i);
            companies[i].setPassCode("RSA - " + i);
            companies[i].getAddress().setId(i+1);
            companies[i].getContact().setId(i+1);
        }
        companies = companyDAO.createCompanies(companies, "accuracy_user", true);

        companies = companyDAO.listCompanies();

        companyDAO.deleteCompanies(companies, true, "accuracy_user");

        Company[] actual = companyDAO.listCompanies();
        assertEquals("failed to delete companies", 0, actual.length);

        // Check AuditManager
        assertEquals("failed to delete companies properly, audit not correct",
                auditManager.listRecords().length, 6); // including creation

        assertEquals("failed to delete companies properly, audit not correct", auditManager.listRecords()[3]
                .getDetails().length, 7);

    }

    /**
     * Accuracy test for <code>{@link DbCompanyDAO#createCompany(Company,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodCreateCompany_Company_String_boolean() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = companyDAO.createCompany(company, "accuracy_user", true);

        Company actual = companyDAO.retrieveCompany(company.getId());
        assertEquals("failed to create company", company.getCompanyName(), actual.getCompanyName());

        // Check ContactManager
        assertEquals("failed to create company properly", contactManager.getAllContacts().length, 1);
        // Check AddressManager
        assertEquals("failed to create company properly", addressManager.getAllAddresses().length, 1);
        // Check AuditManager
        assertEquals("failed to create company properly, audit not correct",
                auditManager.listRecords().length, 1);

        assertEquals("failed to create company properly, audit not correct", auditManager.listRecords()[0]
                .getDetails().length, 7);

    }

    /**
     * Accuracy test for <code>{@link DbCompanyDAO#updateCompany(Company,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateCompany_Company_String_boolean() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = companyDAO.createCompany(company, "accuracy_user", true);

        company.setPassCode("NEW_RSA");

        companyDAO.updateCompany(company, "accuracy_user", true);

        Company actual = companyDAO.retrieveCompany(company.getId());
        assertEquals("failed to update company", company.getPassCode(), actual.getPassCode());

        // Check ContactManager
        assertEquals("failed to update company properly", contactManager.getAllContacts().length, 1);
        // Check AddressManager
        assertEquals("failed to update company properly", addressManager.getAllAddresses().length, 1);
        // Check AuditManager
        assertEquals("failed to update company properly, audit not correct",
                auditManager.listRecords().length, 2); // including create
    }

    /**
     * Accuracy test for <code>{@link DbCompanyDAO#deleteCompany(Company,boolean,String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteCompany_Company_boolean_String() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = companyDAO.createCompany(company, "accuracy_user", true);

        companyDAO.deleteCompany(company, true, "accuracy_user");

        Company actual = companyDAO.retrieveCompany(company.getId());
        assertNull("failed to delete company", actual);

        // Check AuditManager
        assertEquals("failed to update company properly, audit not correct",
                auditManager.listRecords().length, 2); // including create

        assertEquals("failed to create company properly, audit not correct", auditManager.listRecords()[1]
                .getDetails().length, 7);

    }

    /**
     * Accuracy test for <code>{@link DbCompanyDAO#search(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearch_Filter() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company.setId(1);
        company = companyDAO.createCompany(company, "accuracy_user", true);
        CompanySearchBuilder builder = new CompanySearchBuilder();
        builder.createdByUser("accuracy_user");
        Company[] actual = companyDAO.search(builder.buildFilter());
        assertEquals("failed to search using filter", 1, actual.length);
        assertEquals("failed to search using filter", company.getCompanyName(), actual[0].getCompanyName());
    }

    /**
     * Accuracy test for <code>{@link DbCompanyDAO#retrieveCompany(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRetrieveCompany_long() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = companyDAO.createCompany(company, "accuracy_user", true);
        Company actual = companyDAO.retrieveCompany(company.getId());
        assertEquals("failed to retrieve the company", company.getCompanyName(), actual.getCompanyName());
    }

    /**
     * Accuracy test for <code>{@link DbCompanyDAO#retrieveCompanies(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRetrieveCompanies_longArray() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = companyDAO.createCompany(company, "accuracy_user", true);
        Company[] actual = companyDAO.retrieveCompanies(new long[] { company.getId() });
        assertEquals("failed to retrieve the companies", company.getCompanyName(), actual[0].getCompanyName());

    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbCompanyDAOTest.class);
    }
}
