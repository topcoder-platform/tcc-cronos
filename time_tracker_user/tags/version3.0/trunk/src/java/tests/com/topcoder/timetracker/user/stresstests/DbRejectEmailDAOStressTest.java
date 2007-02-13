package com.topcoder.timetracker.user.stresstests;

import junit.framework.TestCase;

import com.topcoder.timetracker.common.DbRejectEmailDAO;
import com.topcoder.timetracker.common.RejectEmail;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * Stress test for <code>DbRejectEmailDAO</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class DbRejectEmailDAOStressTest extends TestCase {

    /**
     * Represents the user to create the email.
     */
    private static final String USER = "user";

    /**
     * Represents the body for reject email.
     */
    private static final String TEST_BODY = "test body";

    /**
     * The DbRejectEmailDAO instance to test on.
     */
    private DbRejectEmailDAO emailDAO = null;

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

        StressTestHelper.loadConfigAndData();
        factory = new DBConnectionFactoryImpl(
        "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        emailDAO = new DbRejectEmailDAO(factory,
                StressTestHelper.CONNECTION_NAME,
                StressTestHelper.IDGEN_NAME);

        StressTestHelper.clearData(factory);
        StressTestHelper.unloadConfig();
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
     * Test the method <code>createRejectEmail</code> with first level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testCreateRejectEmailFirstLevel() throws Exception {

        StressTestHelper.startRecord();

        for (int i = 1; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            RejectEmail email = new RejectEmail();
            email.setBody(TEST_BODY);
            email.setId(i);
            email.setCompanyId(1);

            // create email instance
            emailDAO.createRejectEmail(email, USER);
        }

        StressTestHelper.endRecordAndPrintResult("createRejectEmail", StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test the method <code>createRejectEmail</code> with second level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testCreateRejectEmailSecondLevel() throws Exception {

        StressTestHelper.startRecord();

        for (int i = 1; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            // create an email for rejection
            RejectEmail email = new RejectEmail();
            email.setBody(TEST_BODY);
            email.setId(i);
            email.setCompanyId(1);

            // create email instance
            emailDAO.createRejectEmail(email, USER);
        }

        StressTestHelper.endRecordAndPrintResult("createRejectEmail",
                StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test the method <code>createRejectEmail</code> with third level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testCreateRejectEmailThirdLevel() throws Exception {

        StressTestHelper.startRecord();

        for (int i = 1; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            // create an email for rejection
            RejectEmail email = new RejectEmail();
            email.setBody(TEST_BODY);
            email.setId(i);
            email.setCompanyId(1);

            // create email instance
            emailDAO.createRejectEmail(email, USER);
        }

        StressTestHelper.endRecordAndPrintResult("createRejectEmail",
                StressTestHelper.THIRD_LEVEL_LOAD);
    }

    /**
     * Test the method <code>retrieveRejectEmail</code> with first level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testRetrieveRejectEmailFirstLevel() throws Exception {

        long id = 0;

        // test retrieve 1 from 5000
        for (int i = 1; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            RejectEmail email = new RejectEmail();
            email.setBody(TEST_BODY);
            email.setId(i);
            email.setCompanyId(1);

            // create email instance
            emailDAO.createRejectEmail(email, USER);
            id = email.getId();
        }


        StressTestHelper.startRecord();

        for (int i = 1; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            emailDAO.retrieveRejectEmail(id);
        }

        StressTestHelper.endRecordAndPrintResult("retrieveRejectEmail",
                StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test the method <code>retrieveRejectEmail</code> with second level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testRetrieveRejectEmailSecondLevel() throws Exception {

        long id = 0;

        // test retrieve 1 from 5000
        for (int i = 1; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            RejectEmail email = new RejectEmail();
            email.setBody(TEST_BODY);
            email.setId(i);
            email.setCompanyId(1);

            // create email instance
            emailDAO.createRejectEmail(email, USER);
            id = email.getId();
        }


        StressTestHelper.startRecord();

        for (int i = 1; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            emailDAO.retrieveRejectEmail(id);
        }

        StressTestHelper.endRecordAndPrintResult("retrieveRejectEmail",
                StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test the method <code>retrieveRejectEmail</code> with third level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testRetrieveRejectEmailThirdLevel() throws Exception {

        long id = 0;

        // test retrieve 1 from 5000
        for (int i = 1; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            RejectEmail email = new RejectEmail();
            email.setBody(TEST_BODY);
            email.setId(i);
            email.setCompanyId(1);

            // create email instance
            emailDAO.createRejectEmail(email, USER);
            id = email.getId();
        }


        StressTestHelper.startRecord();

        for (int i = 1; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            emailDAO.retrieveRejectEmail(id);
        }

        StressTestHelper.endRecordAndPrintResult("retrieveRejectEmail",
                StressTestHelper.THIRD_LEVEL_LOAD);
    }

    /**
     * Test the method <code>updateRejectEmail</code> with first level load.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRejectEmailFirstLevel() throws Exception {
        RejectEmail email = null;

        // test update 1 from 5000
        for (int i = 1; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            email = new RejectEmail();
            email.setBody(TEST_BODY);
            email.setId(i);
            email.setCompanyId(1);

            // create email instance
            emailDAO.createRejectEmail(email, USER);
        }

        StressTestHelper.startRecord();
        for (int i = 1; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            emailDAO.updateRejectEmail(email, USER);
        }
        StressTestHelper.endRecordAndPrintResult("updateRejectEmail",
                StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test the method <code>updateRejectEmail</code> with second level load.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRejectEmailSecondLevel() throws Exception {
        RejectEmail email = null;

        // test update 1 from 5000
        for (int i = 1; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            email = new RejectEmail();
            email.setBody(TEST_BODY);
            email.setId(i);
            email.setCompanyId(1);

            // create email instance
            emailDAO.createRejectEmail(email, USER);
        }

        StressTestHelper.startRecord();
        for (int i = 1; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            emailDAO.updateRejectEmail(email, USER);
        }
        StressTestHelper.endRecordAndPrintResult("updateRejectEmail",
                StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test the method <code>updateRejectEmail</code> with third level load.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRejectEmailThirdLevel() throws Exception {
        RejectEmail email = null;

        // test update 1 from 5000
        for (int i = 1; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            email = new RejectEmail();
            email.setBody(TEST_BODY);
            email.setId(i);
            email.setCompanyId(1);

            // create email instance
            emailDAO.createRejectEmail(email, USER);
        }

        StressTestHelper.startRecord();
        for (int i = 1; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            emailDAO.updateRejectEmail(email, USER);
        }
        StressTestHelper.endRecordAndPrintResult("updateRejectEmail",
                StressTestHelper.THIRD_LEVEL_LOAD);
    }

    /**
     * Test the method <code>deleteRejectEmail</code> with first level load.
     *
     * @throws Exception throw Exception to JUnit
     */
    public void testDeleteRejectEmailFirstLevel() throws Exception {
        RejectEmail email = null;

        // test delete 1 from 5000
        for (int i = 1; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            email = new RejectEmail();
            email.setBody(TEST_BODY);
            email.setId(i);
            email.setCompanyId(1);

            // create email instance
            emailDAO.createRejectEmail(email, USER);
        }

        StressTestHelper.startRecord();
        emailDAO.deleteRejectEmail(email);
        StressTestHelper.endRecordAndPrintResultFrom("deleteRejectEmail",
                StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test the method <code>deleteRejectEmail</code> with first level load.
     *
     * @throws Exception throw Exception to JUnit
     */
    public void testDeleteRejectEmailSecondLevel() throws Exception {
        RejectEmail email = null;

        // test delete 1 from 5000
        for (int i = 1; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            // create an email for rejection
            email = new RejectEmail();
            email.setBody(TEST_BODY);
            email.setId(i);
            email.setCompanyId(1);

            // create email instance
            emailDAO.createRejectEmail(email, USER);
        }

        StressTestHelper.startRecord();
        emailDAO.deleteRejectEmail(email);
        StressTestHelper.endRecordAndPrintResultFrom("deleteRejectEmail",
                StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test the method <code>deleteRejectEmail</code> with first level load.
     *
     * @throws Exception throw Exception to JUnit
     */
    public void testDeleteRejectEmailThirdLevel() throws Exception {
        RejectEmail email = null;

        // test delete 1 from 5000
        for (int i = 1; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            // create an email for rejection
            email = new RejectEmail();
            email.setBody(TEST_BODY);
            email.setId(i);
            email.setCompanyId(1);

            // create email instance
            emailDAO.createRejectEmail(email, USER);
        }

        StressTestHelper.startRecord();
        emailDAO.deleteRejectEmail(email);
        StressTestHelper.endRecordAndPrintResultFrom("deleteRejectEmail",
                StressTestHelper.THIRD_LEVEL_LOAD);
    }

}
