/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.accuracytests;

import java.sql.Connection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanySearchBuilder;
import com.topcoder.timetracker.company.ejb.LocalCompanyManagerDelegate;

/**
 * Accuracy test for <code>{@link com.topcoder.timetracker.company.ejb.LocalCompanyManagerDelegate}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class LocalCompanyManagerDelegateTest extends TestCase {
    /**
     * <p>
     * Database connection for test data setup.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represents the ejb delegate.
     * </p>
     */
    private LocalCompanyManagerDelegate delegate;

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
        AccuracyTestHelper.deployEJB();
        delegate = new LocalCompanyManagerDelegate("com.topcoder.timetracker.company.ejb.accuracy");
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
     * Accuracy test for <code>{@link LocalCompanyManagerDelegate#LocalCompanyManagerDelegate(String)}</code>.
     */
    public void testConstructor_String() {
        assertNotNull("failed to create LocalCompanyManagerDelegate", delegate);
    }

    /**
     * Accuracy test for
     * <code>{@link LocalCompanyManagerDelegate#createCompanies(Company[],String,boolean,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodCreateCompanies_CompanyArray_String_boolean_boolean_1() throws Exception {
        Company[] companies = new Company[3];
        for (int i = 0; i < companies.length; i++) {
            companies[i] = AccuracyTestHelper.createCompanyBean();
            companies[i].setCompanyName("company - " + i);
            companies[i].setPassCode("RSA - " + i);
        }
        companies = delegate.createCompanies(companies, "accuracy_user", false, false);

        Company[] actual = delegate.listCompanies();
        for (int i = 0; i < actual.length; i++) {
            assertEquals("failed to create companies properly", companies[i].getCompanyName(), actual[i]
                    .getCompanyName());
            assertEquals("failed to create companies properly", companies[i].getPassCode(), actual[i]
                    .getPassCode());
        }

    }

    /**
     * Accuracy test for
     * <code>{@link LocalCompanyManagerDelegate#createCompanies(Company[],String,boolean,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodCreateCompanies_CompanyArray_String_boolean_boolean_2() throws Exception {
        Company[] companies = new Company[3];
        for (int i = 0; i < companies.length; i++) {
            companies[i] = AccuracyTestHelper.createCompanyBean();
            companies[i].setCompanyName("company - " + i);
            companies[i].setPassCode("RSA - " + i);
        }
        companies = delegate.createCompanies(companies, "accuracy_user", true, false);

        Company[] actual = delegate.listCompanies();
        for (int i = 0; i < actual.length; i++) {
            assertEquals("failed to create companies properly", companies[i].getCompanyName(), actual[i]
                    .getCompanyName());
            assertEquals("failed to create companies properly", companies[i].getPassCode(), actual[i]
                    .getPassCode());
        }

    }

    /**
     * Accuracy test for
     * <code>{@link LocalCompanyManagerDelegate#updateCompanies(Company[],String,boolean,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateCompanies_CompanyArray_String_boolean_boolean_1() throws Exception {
        Company[] companies = new Company[3];
        for (int i = 0; i < companies.length; i++) {
            companies[i] = AccuracyTestHelper.createCompanyBean();
            companies[i].setCompanyName("company - " + i);
            companies[i].setPassCode("RSA - " + i);
        }
        companies = delegate.createCompanies(companies, "accuracy_user", false, false);

        companies = delegate.listCompanies();

        for (int i = 0; i < companies.length; i++) {
            companies[i].setPassCode("NEW_RSA - " + i);
        }
        delegate.updateCompanies(companies, "accuracy_user", false, false);
        Company[] actual = delegate.listCompanies();
        for (int i = 0; i < actual.length; i++) {
            assertEquals("failed to update companies properly", companies[i].getPassCode(), actual[i]
                    .getPassCode());
        }
    }

    /**
     * Accuracy test for
     * <code>{@link LocalCompanyManagerDelegate#updateCompanies(Company[],String,boolean,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateCompanies_CompanyArray_String_boolean_boolean_2() throws Exception {
        Company[] companies = new Company[3];
        for (int i = 0; i < companies.length; i++) {
            companies[i] = AccuracyTestHelper.createCompanyBean();
            companies[i].setCompanyName("company - " + i);
            companies[i].setPassCode("RSA - " + i);
        }
        companies = delegate.createCompanies(companies, "accuracy_user", false, false);

        companies = delegate.listCompanies();

        for (int i = 0; i < companies.length; i++) {
            companies[i].setPassCode("NEW_RSA - " + i);
        }
        delegate.updateCompanies(companies, "accuracy_user", true, false);
        Company[] actual = delegate.listCompanies();
        for (int i = 0; i < actual.length; i++) {
            assertEquals("failed to update companies properly", companies[i].getPassCode(), actual[i]
                    .getPassCode());
        }
    }

    /**
     * Accuracy test for
     * <code>{@link LocalCompanyManagerDelegate#deleteCompanies(Company[],boolean,boolean,String)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteCompanies_CompanyArray_boolean_boolean_String_1() throws Exception {
        Company[] companies = new Company[3];
        for (int i = 0; i < companies.length; i++) {
            companies[i] = AccuracyTestHelper.createCompanyBean();
            companies[i].setCompanyName("company - " + i);
            companies[i].setPassCode("RSA - " + i);
            companies[i].getAddress().setId(i+1);
            companies[i].getContact().setId(i+1);
        }
        companies = delegate.createCompanies(companies, "accuracy_user", false, false);

        companies = delegate.listCompanies();

        delegate.deleteCompanies(companies, false, false, "accuracy_user");
        Company[] actual = delegate.listCompanies();
        assertEquals("failed to delete companies", 0, actual.length);

    }

    /**
     * Accuracy test for
     * <code>{@link LocalCompanyManagerDelegate#deleteCompanies(Company[],boolean,boolean,String)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteCompanies_CompanyArray_boolean_boolean_String_2() throws Exception {
        Company[] companies = new Company[3];
        for (int i = 0; i < companies.length; i++) {
            companies[i] = AccuracyTestHelper.createCompanyBean();
            companies[i].setCompanyName("company - " + i);
            companies[i].setPassCode("RSA - " + i);
            companies[i].getAddress().setId(i+1);
            companies[i].getContact().setId(i+1);
        }
        companies = delegate.createCompanies(companies, "accuracy_user", true, false);

        companies = delegate.listCompanies();

        delegate.deleteCompanies(companies, false, false, "accuracy_user");
        Company[] actual = delegate.listCompanies();
        assertEquals("failed to delete companies", 0, actual.length);

    }

    /**
     * Accuracy test for
     * <code>{@link LocalCompanyManagerDelegate#createCompany(Company,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateCompany_Company_String_boolean() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = delegate.createCompany(company, "accuracy_user", false);

        Company actual = delegate.retrieveCompany(company.getId());
        assertEquals("failed to create company", company.getCompanyName(), actual.getCompanyName());

    }

    /**
     * Accuracy test for
     * <code>{@link LocalCompanyManagerDelegate#updateCompany(Company,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateCompany_Company_String_boolean() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = delegate.createCompany(company, "accuracy_user", false);

        company = delegate.retrieveCompany(company.getId());
        company.setPassCode("NEW_RSA");
        delegate.updateCompany(company, "accuracy_user", false);
        Company actual = delegate.retrieveCompany(company.getId());
        assertEquals("failed to update company", company.getPassCode(), actual.getPassCode());

    }

    /**
     * Accuracy test for
     * <code>{@link LocalCompanyManagerDelegate#deleteCompany(Company,boolean,String)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteCompany_Company_boolean_String() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = delegate.createCompany(company, "accuracy_user", true);

        delegate.deleteCompany(company, true, "accuracy_user");

        Company actual = delegate.retrieveCompany(company.getId());
        assertNull("failed to delete company", actual);

    }

    /**
     * Accuracy test for <code>{@link LocalCompanyManagerDelegate#search(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearch_Filter() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company.setId(1);
        company = delegate.createCompany(company, "accuracy_user", true);
        CompanySearchBuilder builder = new CompanySearchBuilder();
        builder.createdByUser("accuracy_user");
        Company[] actual = delegate.search(builder.buildFilter());
        assertEquals("failed to search using filter", 1, actual.length);
        assertEquals("failed to search using filter", company.getCompanyName(), actual[0].getCompanyName());

    }

    /**
     * Accuracy test for <code>{@link LocalCompanyManagerDelegate#retrieveCompany(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRetrieveCompany_long() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = delegate.createCompany(company, "accuracy_user", true);
        Company actual = delegate.retrieveCompany(company.getId());
        assertEquals("failed to retrieve the company", company.getCompanyName(), actual.getCompanyName());

    }

    /**
     * Accuracy test for <code>{@link LocalCompanyManagerDelegate#retrieveCompanies(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRetrieveCompanies_longArray() throws Exception {
        Company company = AccuracyTestHelper.createCompanyBean();
        company = delegate.createCompany(company, "accuracy_user", true);
        Company[] actual = delegate.retrieveCompanies(new long[] { company.getId() });
        assertEquals("failed to retrieve the companies", company.getCompanyName(), actual[0].getCompanyName());
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(LocalCompanyManagerDelegateTest.class);
    }
}
