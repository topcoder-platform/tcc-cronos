/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.timetracker.company.daoimplementation.DbCompanyDAO;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.Timestamp;

import java.util.Date;


/**
 * <p>
 * Tests functionality and error cases of <code>CompanySearchBuilder</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class CompanySearchBuilderUnitTest extends TestCase {
    /** Represents the <code>CompanySearchBuilder</code> instance used for testing. */
    private CompanySearchBuilder builder = null;

    /** Represents the <code>Company</code> instances used for testing. */
    private Company[] companies = null;

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the <code>DbCompanyDAO</code> instance used for testing. */
    private DbCompanyDAO dao = null;

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

        // create the testing instance
        builder = new CompanySearchBuilder();

        // add some testing data
        DBConnectionFactory connFactory = UnitTestHelper.getDBConnectionFactory();
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME,
                new MockContactManager(false), new MockAddressManager(false), new MockAuditManager(false));
        companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setId(i + 1);
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("contact" + i);
            dao.createCompany(companies[i], "user" + i, false);
        }
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
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>CompanySearchBuilder()</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testCompanySearchBuilder_Accuracy() throws Exception {
        assertNotNull("The CompanySearchBuilder instance should be created.", builder);
    }

    /**
     * <p>
     * Test the method of <code>hasCompanyName(String name)</code> when the name is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasCompanyName_NullName() throws Exception {
        try {
            builder.hasCompanyName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>hasCompanyName(String name)</code> when the name is empty, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasCompanyName_EmptyName() throws Exception {
        try {
            builder.hasCompanyName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasCompanyName(String name)</code>, no record found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasCompanyName_Accuracy1() throws Exception {
        builder.hasCompanyName("notExist");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasCompanyName(String name)</code>, some records found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasCompanyName_Accuracy2() throws Exception {
        builder.hasCompanyName("companyName1");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[] {this.companies[1]}, ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasCompanyName(String name)</code>, all records found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasCompanyName_Accuracy3() throws Exception {
        builder.hasCompanyName("companyName");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the method of <code>hasContactFirstName(String firstName)</code> when the firstName is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactFirstName_NullFirstName() throws Exception {
        try {
            builder.hasContactFirstName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>hasContactFirstName(String firstName)</code> when the firstName is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactFirstName_EmptyFirstName() throws Exception {
        try {
            builder.hasContactFirstName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasContactFirstName(String firstName)</code>, no record found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactFirstName_Accuracy1() throws Exception {
        builder.hasContactFirstName("notExist");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasContactFirstName(String firstName)</code>, all records found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactFirstName_Accuracy2() throws Exception {
        builder.hasContactFirstName("first_name");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the method of <code>hasContactLastName(String lastName)</code> when the lastName is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactLastName_NullLastName() throws Exception {
        try {
            builder.hasContactLastName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>hasContactLastName(String lastName)</code> when the lastName is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactLastName_EmptyLastName() throws Exception {
        try {
            builder.hasContactLastName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasContactLastName(String lastName)</code>, no record found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactLastName_Accuracy1() throws Exception {
        builder.hasContactLastName("notExist");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasContactLastName(String lastName)</code>, all records found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactLastName_Accuracy2() throws Exception {
        builder.hasContactLastName("last_name");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the method of <code>hasContactPhoneNumber(String phoneNumber)</code> when the phoneNumber is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactPhoneNumber_NullPhoneNumber() throws Exception {
        try {
            builder.hasContactPhoneNumber(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>hasContactPhoneNumber(String phoneNumber)</code> when the phoneNumber is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactPhoneNumber_EmptyPhoneNumber() throws Exception {
        try {
            builder.hasContactPhoneNumber(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasContactPhoneNumber(String phoneNumber)</code>, no record found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactPhoneNumber_Accuracy1() throws Exception {
        builder.hasContactPhoneNumber("notExist");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasContactPhoneNumber(String phoneNumber)</code>, all records found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactPhoneNumber_Accuracy2() throws Exception {
        builder.hasContactPhoneNumber("phone2");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the method of <code>hasContactEmail(String email)</code> when the email is null, IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactEmail_NullEmail() throws Exception {
        try {
            builder.hasContactEmail(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>hasContactEmail(String email)</code> when the email is empty, IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactEmail_EmptyEmail() throws Exception {
        try {
            builder.hasContactEmail(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasContactEmail(String email)</code>, no record found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactEmail_Accuracy1() throws Exception {
        builder.hasContactEmail("notExist");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasContactEmail(String email)</code>, all records found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasContactEmail_Accuracy2() throws Exception {
        builder.hasContactEmail("email2");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the method of <code>hasStreetAddress(String streetAddress)</code> when the streetAddress is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasStreetAddress_NullStreetAddress() throws Exception {
        try {
            builder.hasStreetAddress(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>hasStreetAddress(String streetAddress)</code> when the streetAddress is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasStreetAddress_EmptyStreetAddress() throws Exception {
        try {
            builder.hasStreetAddress(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasStreetAddress(String streetAddress)</code>, no record found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasStreetAddress_Accuracy1() throws Exception {
        builder.hasStreetAddress("notExist");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasStreetAddress(String streetAddress)</code>, all records found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasStreetAddress_Accuracy2() throws Exception {
        builder.hasStreetAddress("line11");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the method of <code>hasCity(String city)</code> when the city is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasCity_NullCity() throws Exception {
        try {
            builder.hasCity(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>hasCity(String city)</code> when the city is empty, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasCity_EmptyCity() throws Exception {
        try {
            builder.hasCity(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasCity(String city)</code>, no record found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasCity_Accuracy1() throws Exception {
        builder.hasCity("notExist");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasCity(String city)</code>, all records found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasCity_Accuracy2() throws Exception {
        builder.hasCity("city1");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the method of <code>hasState(String state)</code> when the state is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasState_NullState() throws Exception {
        try {
            builder.hasState(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>hasState(String state)</code> when the state is empty, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasState_EmptyState() throws Exception {
        try {
            builder.hasState(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasState(String state)</code>, no record found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasState_Accuracy1() throws Exception {
        builder.hasState("notExist");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasState(String state)</code>, all records found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasState_Accuracy2() throws Exception {
        builder.hasState("stateName1");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the method of <code>hasZipCode(String zipCode)</code> when the zipCode is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasZipCode_NullZipCode() throws Exception {
        try {
            builder.hasZipCode(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>hasZipCode(String zipCode)</code> when the zipCode is empty, IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasZipCode_EmptyZipCode() throws Exception {
        try {
            builder.hasZipCode(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasZipCode(String zipCode)</code>, no record found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasZipCode_Accuracy1() throws Exception {
        builder.hasZipCode("notExist");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>hasZipCode(String zipCode)</code>, all records found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testHasZipCode_Accuracy2() throws Exception {
        builder.hasZipCode("zip_code1");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the method of <code>createdWithinDateRange(Date startDate, Date endDate)</code> when both the startDate and
     * endDate are null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreatedWithinDateRange_NullStartDateNullEndDate() throws Exception {
        try {
            builder.createdWithinDateRange(null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>createdWithinDateRange(Date startDate, Date endDate)</code> when the startDate
     * is null.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreatedWithinDateRange_NullStartDate1() throws Exception {
        builder.createdWithinDateRange(null, new Timestamp(new Date().getTime() + 30000));

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>createdWithinDateRange(Date startDate, Date endDate)</code> when the startDate
     * is null.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreatedWithinDateRange_NullStartDate2() throws Exception {
        builder.createdWithinDateRange(null, new Timestamp(new Date().getTime() - 30000));

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>createdWithinDateRange(Date startDate, Date endDate)</code> when the startDate
     * is null.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreatedWithinDateRange_NullEndDate1() throws Exception {
        builder.createdWithinDateRange(new Timestamp(new Date().getTime() + 30000), null);

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>createdWithinDateRange(Date startDate, Date endDate)</code> when the startDate
     * is null.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreatedWithinDateRange_NullEndDate2() throws Exception {
        builder.createdWithinDateRange(new Timestamp(new Date().getTime() - 30000), null);

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>createdWithinDateRange(Date startDate, Date endDate)</code> when both are not
     * null.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreatedWithinDateRange_Accuracy1() throws Exception {
        builder.createdWithinDateRange(new Timestamp(new Date().getTime() - 30000),
            new Timestamp(new Date().getTime() + 30000));

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>createdWithinDateRange(Date startDate, Date endDate)</code> when both are not
     * null.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreatedWithinDateRange_Accuracy2() throws Exception {
        builder.createdWithinDateRange(new Timestamp(new Date().getTime() + 30000),
            new Timestamp(new Date().getTime() + 30000));

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>createdWithinDateRange(Date startDate, Date endDate)</code> when both are not
     * null and start is after end date, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreatedWithinDateRange_StartAfterEnd() throws Exception {
        try {
            builder.createdWithinDateRange(new Timestamp(new Date().getTime() + 30000),
                new Timestamp(new Date().getTime() - 30000));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createdByUser(String username)</code> when the username is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreatedByUser_NullName() throws Exception {
        try {
            builder.createdByUser(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createdByUser(String username)</code> when the username is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreatedByUser_EmptyName() throws Exception {
        try {
            builder.createdByUser(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>createdByUser(String username)</code>, no record found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreatedByUser_Accuracy1() throws Exception {
        builder.createdByUser("notExist");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>createdByUser(String username)</code>, all records found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreatedByUser_Accuracy2() throws Exception {
        builder.createdByUser("user1");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[] {this.companies[1]}, ret);
    }

    /**
     * <p>
     * Test the method of <code>modifiedWithinDateRange(Date startDate, Date endDate)</code> when both the startDate
     * and endDate are null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testModifiedWithinDateRange_NullStartDateNullEndDate() throws Exception {
        try {
            builder.modifiedWithinDateRange(null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>modifiedWithinDateRange(Date startDate, Date endDate)</code> when the
     * startDate is null.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testModifiedWithinDateRange_NullStartDate1() throws Exception {
        builder.modifiedWithinDateRange(null, new Timestamp(new Date().getTime() + 30000));

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>modifiedWithinDateRange(Date startDate, Date endDate)</code> when the
     * startDate is null.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testModifiedWithinDateRange_NullStartDate2() throws Exception {
        builder.modifiedWithinDateRange(null, new Timestamp(new Date().getTime() - 30000));

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>modifiedWithinDateRange(Date startDate, Date endDate)</code> when the
     * startDate is null.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testModifiedWithinDateRange_NullEndDate1() throws Exception {
        builder.modifiedWithinDateRange(new Timestamp(new Date().getTime() + 30000), null);

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>modifiedWithinDateRange(Date startDate, Date endDate)</code> when the
     * startDate is null.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testModifiedWithinDateRange_NullEndDate2() throws Exception {
        builder.modifiedWithinDateRange(new Timestamp(new Date().getTime() - 30000), null);

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>modifiedWithinDateRange(Date startDate, Date endDate)</code> when both are not
     * null.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testModifiedWithinDateRange_Accuracy1() throws Exception {
        builder.modifiedWithinDateRange(new Timestamp(new Date().getTime() - 30000),
            new Timestamp(new Date().getTime() + 30000));

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(this.companies, ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>modifiedWithinDateRange(Date startDate, Date endDate)</code> when both are not
     * null.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testModifiedWithinDateRange_Accuracy2() throws Exception {
        builder.modifiedWithinDateRange(new Timestamp(new Date().getTime() + 30000),
            new Timestamp(new Date().getTime() + 30000));

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>modifiedWithinDateRange(Date startDate, Date endDate)</code> when both are not
     * null and start is after end date, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testModifiedWithinDateRange_StartAfterEnd() throws Exception {
        try {
            builder.modifiedWithinDateRange(new Timestamp(new Date().getTime() + 30000),
                new Timestamp(new Date().getTime() - 30000));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>modifiedByUser(String username)</code> when the username is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testModifiedByUser_NullName() throws Exception {
        try {
            builder.modifiedByUser(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>modifiedByUser(String username)</code> when the username is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testModifiedByUser_EmptyName() throws Exception {
        try {
            builder.modifiedByUser(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>modifiedByUser(String username)</code>, no record found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testModifiedByUser_Accuracy1() throws Exception {
        builder.modifiedByUser("notExist");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[0], ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>modifiedByUser(String username)</code>, all records found.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testModifiedByUser_Accuracy2() throws Exception {
        builder.modifiedByUser("user1");

        Company[] ret = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[] {this.companies[1]}, ret);
    }

    /**
     * <p>
     * Test the accuracy of method <code>buildFilter()</code>, the ret should be shallow copied.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testBuildFilter_Accuracy() throws Exception {
        builder.modifiedByUser("user1");

        assertNotSame("Should be shallow copied.", builder.buildFilter(), builder.buildFilter());
    }

    /**
     * <p>
     * Test the method <code>buildFilter()</code> when no criteria has yet been set, IllegalStateException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testBuildFilter_IllegalState() throws Exception {
        try {
            builder.buildFilter();
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>reset()</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testReset_Accuracy() throws Exception {
        builder.modifiedByUser("user1");

        builder.reset();

        assertEquals("Should be reset.", null,
                UnitTestHelper.getPrivateField(builder.getClass(), builder, "andFilter"));
    }
}
