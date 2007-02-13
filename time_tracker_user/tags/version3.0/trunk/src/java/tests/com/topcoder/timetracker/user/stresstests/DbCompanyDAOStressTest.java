package com.topcoder.timetracker.user.stresstests;

import junit.framework.TestCase;

import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.DbCompanyDAO;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * Stress test for <code>DbCompanyDAO</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class DbCompanyDAOStressTest extends TestCase {

    /**
     * Represents the user to create the email.
     */
    private static final String USER = "user";

    /**
     * The DbRejectCompanyDAO instance to test on.
     */
    private DbCompanyDAO dao = null;

    /**
     * The factory used in this case.
     */
    private DBConnectionFactory factory = null;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        //StressTestHelper.loadConfigAndData();
        factory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        StressTestHelper.clearData(factory);
        StressTestHelper.loadConfigAndData();
        dao = new DbCompanyDAO(factory,
                StressTestHelper.CONNECTION_NAME,
                StressTestHelper.ALGO_NAME,
                StressTestHelper.IDGEN_NAME);
    }

    /**
     * Clean up test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        StressTestHelper.clearData(factory);
        StressTestHelper.unloadConfig();
    }

    /**
     * Test the method <code>CreateCompany</code> with first level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testCreateCompanyFirstLevel() throws Exception {

        StressTestHelper.startRecord();

        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            Company company = StressTestHelper.createCompany(i);

            // create email instance
            dao.createCompany(company, USER);
        }

        StressTestHelper.endRecordAndPrintResult("createCompany", StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test the method <code>CreateCompany</code> with second level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testCreateCompanySecondLevel() throws Exception {

        StressTestHelper.startRecord();

        for (int i = 0; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            // create an email for rejection
            Company company = StressTestHelper.createCompany(i);

            // create email instance
            dao.createCompany(company, USER);
        }

        StressTestHelper.endRecordAndPrintResult("createCompany",
                StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test the method <code>CreateCompany</code> with third level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testCreateCompanyThirdLevel() throws Exception {

        StressTestHelper.startRecord();

        for (int i = 0; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            // create an email for rejection
            Company company = StressTestHelper.createCompany(i);

            // create email instance
            dao.createCompany(company, USER);
        }

        StressTestHelper.endRecordAndPrintResult("createCompany",
                StressTestHelper.THIRD_LEVEL_LOAD);
    }

    /**
     * Test the method <code>retrieveCompany</code> with first level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testretrieveCompanyFirstLevel() throws Exception {

        long id = 0;

        // test retrieve 1 from 5000
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            Company company = StressTestHelper.createCompany(i);

            // create email instance
            dao.createCompany(company, USER);
            id = company.getId();
        }


        StressTestHelper.startRecord();

        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            dao.retrieveCompany(id);
        }

        StressTestHelper.endRecordAndPrintResult("retrieveCompany",
                StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test the method <code>retrieveCompany</code> with second level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testretrieveCompanySecondLevel() throws Exception {

        long id = 0;

        // test retrieve 1 from 5000
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            Company company = StressTestHelper.createCompany(i);

            // create email instance
            dao.createCompany(company, USER);
            id = company.getId();
        }


        StressTestHelper.startRecord();

        for (int i = 0; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            dao.retrieveCompany(id);
        }

        StressTestHelper.endRecordAndPrintResult("retrieveCompany",
                StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test the method <code>retrieveCompany</code> with third level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testretrieveCompanyThirdLevel() throws Exception {

        long id = 0;

        // test retrieve 1 from 5000
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            Company company = StressTestHelper.createCompany(i);

            // create email instance
            dao.createCompany(company, USER);
            id = company.getId();
        }


        StressTestHelper.startRecord();

        for (int i = 0; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            dao.retrieveCompany(id);
        }

        StressTestHelper.endRecordAndPrintResult("retrieveCompany",
                StressTestHelper.THIRD_LEVEL_LOAD);
    }

    /**
     * Test the method <code>updateCompany</code> with first level load.
     *
     * @throws Exception to JUnit
     */
    public void testupdateCompanyFirstLevel() throws Exception {
        Company company = null;

        // test update 1 from 5000
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            company = StressTestHelper.createCompany(i);

            // create email instance
            dao.createCompany(company, USER);
        }

        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            dao.updateCompany(company, USER);
        }
        StressTestHelper.endRecordAndPrintResult("updateCompany",
                StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test the method <code>updateCompany</code> with second level load.
     *
     * @throws Exception to JUnit
     */
    public void testupdateCompanySecondLevel() throws Exception {
        Company company = null;

        // test update 1 from 5000
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            company = StressTestHelper.createCompany(i);

            // create email instance
            dao.createCompany(company, USER);
        }

        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            dao.updateCompany(company, USER);
        }
        StressTestHelper.endRecordAndPrintResult("updateCompany",
                StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test the method <code>updateCompany</code> with third level load.
     *
     * @throws Exception to JUnit
     */
    public void testupdateCompanyThirdLevel() throws Exception {
        Company company = null;

        // test update 1 from 5000
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            company = StressTestHelper.createCompany(i);

            // create email instance
            dao.createCompany(company, USER);
        }

        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            dao.updateCompany(company, USER);
        }
        StressTestHelper.endRecordAndPrintResult("updateCompany",
                StressTestHelper.THIRD_LEVEL_LOAD);
    }

    /**
     * Test the method <code>deleteCompany</code> with first level load.
     *
     * @throws Exception throw Exception to JUnit
     */
    public void testdeleteCompanyFirstLevel() throws Exception {
        Company company = null;

        // test delete 1 from 5000
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            company = StressTestHelper.createCompany(i);

            // create company instance
            dao.createCompany(company, USER);
        }

        StressTestHelper.startRecord();
        dao.deleteCompany(company);
        StressTestHelper.endRecordAndPrintResultFrom("deleteCompany",
                StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test the method <code>deleteCompany</code> with first level load.
     *
     * @throws Exception throw Exception to JUnit
     */
    public void testdeleteCompanySecondLevel() throws Exception {
        Company company = null;

        // test delete 1 from 5000
        for (int i = 0; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            company = StressTestHelper.createCompany(i);

            // create company instance
            dao.createCompany(company, USER);
        }

        StressTestHelper.startRecord();
        dao.deleteCompany(company);
        StressTestHelper.endRecordAndPrintResultFrom("deleteCompany",
                StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test the method <code>deleteCompany</code> with first level load.
     *
     * @throws Exception throw Exception to JUnit
     */
    public void testdeleteCompanyThirdLevel() throws Exception {
        Company company = null;

        // test delete 1 from 5000
        for (int i = 0; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            company = StressTestHelper.createCompany(i);

            // create company instance
            dao.createCompany(company, USER);
        }

        StressTestHelper.startRecord();
        dao.deleteCompany(company);
        StressTestHelper.endRecordAndPrintResultFrom("deleteCompany",
                StressTestHelper.THIRD_LEVEL_LOAD);
    }

}
