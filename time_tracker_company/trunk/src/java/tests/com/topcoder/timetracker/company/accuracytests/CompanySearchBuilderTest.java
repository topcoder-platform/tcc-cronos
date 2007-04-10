/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.accuracytests;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanySearchBuilder;
import com.topcoder.timetracker.company.daoimplementation.DbCompanyDAO;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.ContactManager;

/**
 * Accuracy test for <code>{@link com.topcoder.timetracker.company.CompanySearchBuilder}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class CompanySearchBuilderTest extends TestCase {
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
     * Represents the company search builder.
     * </p>
     */
    private CompanySearchBuilder builder;

    /**
     * <p>
     * Represents the companies.
     * </p>
     */
    private Company[] companies;

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

        AuditManager auditManager = new MockAuditManager();
        AddressManager addressManager = new MockAddressManager();
        ContactManager contactManager = new MockContactManager();

        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        companyDAO = new DbCompanyDAO(connectionFactory, "informix_connect", "company_accuracy",
                contactManager, addressManager, auditManager);

        companies = new Company[3];
        for (int i = 0; i < companies.length; i++) {
            companies[i] = AccuracyTestHelper.createCompanyBean();
            companies[i].setId(i + 1);
            companies[i].setCompanyName("company - " + i);
            companies[i].setPassCode("RSA - " + i);
        }
        companies = companyDAO.createCompanies(companies, "accuracy_user", false);

        builder = new CompanySearchBuilder();
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
     * Accuracy test for <code>{@link CompanySearchBuilder#CompanySearchBuilder()}</code>.
     */
    public void testConstructor() {
        assertNotNull("Failed to create CompanySearchBuilder", builder);
    }

    /**
     * Accuracy test for <code>{@link CompanySearchBuilder#hasState(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodHasState_String() throws Exception {
        builder.hasState("OHIO");
        Company[] actual = companyDAO.search(builder.buildFilter());
        assertEquals("failed to create filter hasState", actual.length, 1);
    }

    /**
     * Accuracy test for <code>{@link CompanySearchBuilder#hasZipCode(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodHasZipCode_String() throws Exception {
        builder.hasZipCode("42134");
        Company[] actual = companyDAO.search(builder.buildFilter());
        assertEquals("failed to create filter hasZipCode", actual.length, 1);
    }

    /**
     * Accuracy test for <code>{@link CompanySearchBuilder#hasContactFirstName(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodHasContactFirstName_String() throws Exception {
        builder.hasContactFirstName("sams");
        Company[] actual = companyDAO.search(builder.buildFilter());
        assertEquals("failed to create filter hasContactFirstName", actual.length, 1);
    }

    /**
     * Accuracy test for <code>{@link CompanySearchBuilder#createdByUser(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodCreatedByUser_String() throws Exception {
        builder.createdByUser("accuracy_user");
        Company[] actual = companyDAO.search(builder.buildFilter());
        assertEquals("failed to create filter createdByUser", actual.length, 1);
    }

    /**
     * Accuracy test for <code>{@link CompanySearchBuilder#hasContactEmail(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodHasContactEmail_String() throws Exception {
        builder.hasContactEmail("sams@aol.com");
        Company[] actual = companyDAO.search(builder.buildFilter());
        assertEquals("failed to create filter hasContactEmail", actual.length, 1);
    }

    /**
     * Accuracy test for <code>{@link CompanySearchBuilder#hasContactLastName(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodHasContactLastName_String() throws Exception {
        builder.hasContactLastName("john");
        Company[] actual = companyDAO.search(builder.buildFilter());
        assertEquals("failed to create filter hasContactLastName", actual.length, 1);
    }

    /**
     * Accuracy test for <code>{@link CompanySearchBuilder#hasContactPhoneNumber(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodHasContactPhoneNumber_String() throws Exception {
        builder.hasContactPhoneNumber("614-376-2342");
        Company[] actual = companyDAO.search(builder.buildFilter());
        assertEquals("failed to create filter hasContactPhoneNumber", actual.length, 1);
    }

    /**
     * Accuracy test for <code>{@link CompanySearchBuilder#modifiedWithinDateRange(Date,Date)}</code>. //
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodModifiedWithinDateRange_Date_Date() throws Exception {
        builder.modifiedWithinDateRange(new Timestamp(new Date().getTime() - 20000), new Timestamp(new Date()
                .getTime() + 20000));
        Company[] actual = companyDAO.search(builder.buildFilter());
        assertEquals("failed to create filter modifiedWithinDateRange", actual.length, 1);
    }

    /**
     * Accuracy test for <code>{@link CompanySearchBuilder#createdWithinDateRange(Date,Date)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreatedWithinDateRange_Date_Date() throws Exception {
        builder.createdWithinDateRange(new Timestamp(new Date().getTime() - 20000), new Timestamp(new Date()
                .getTime() + 20000));
        Company[] actual = companyDAO.search(builder.buildFilter());
        assertEquals("failed to create filter modifiedWithinDateRange", actual.length, 1);

    }

    /**
     * Accuracy test for <code>{@link CompanySearchBuilder#modifiedByUser(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodModifiedByUser_String() throws Exception {
        builder.modifiedByUser("accuracy_user");
        Company[] actual = companyDAO.search(builder.buildFilter());
        assertEquals("failed to create filter modifiedWithinDateRange", actual.length, 1);

    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(CompanySearchBuilderTest.class);
    }
}
