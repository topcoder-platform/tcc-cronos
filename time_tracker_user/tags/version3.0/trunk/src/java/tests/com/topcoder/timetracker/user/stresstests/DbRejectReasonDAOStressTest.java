package com.topcoder.timetracker.user.stresstests;

import junit.framework.TestCase;

import com.topcoder.timetracker.common.DbRejectReasonDAO;
import com.topcoder.timetracker.common.RejectReason;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * Stress test for <code>DbRejectReasonDAO</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class DbRejectReasonDAOStressTest extends TestCase {

    /**
     * Represents the user to create the email.
     */
    private static final String USER = "user";

    /**
     * The DbRejectReasonDAO instance to test on.
     */
    private DbRejectReasonDAO dao = null;

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
        dao = new DbRejectReasonDAO(factory,
                StressTestHelper.CONNECTION_NAME,
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
     * Test the method <code>createRejectEmail</code> with first level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testCreateRejectEmailFirstLevel() throws Exception {

        StressTestHelper.startRecord();

        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            RejectReason reason = new RejectReason();
            reason.setActive(true);
            reason.setDescription("test");
            reason.setCompanyId(1);

            // create email instance
            dao.createRejectReason(reason, USER);
        }

        StressTestHelper.endRecordAndPrintResult("createRejectReason", StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test the method <code>createRejectEmail</code> with second level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testCreateRejectEmailSecondLevel() throws Exception {

        StressTestHelper.startRecord();

        for (int i = 0; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            // create an email for rejection
            // create an email for rejection
            RejectReason reason = new RejectReason();
            reason.setActive(true);
            reason.setDescription("test");
            reason.setCompanyId(1);

            // create email instance
            dao.createRejectReason(reason, USER);
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

        for (int i = 0; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            // create an email for rejection
            // create an email for rejection
            RejectReason reason = new RejectReason();
            reason.setActive(true);
            reason.setDescription("test");
            reason.setCompanyId(1);

            // create email instance
            dao.createRejectReason(reason, USER);
        }

        StressTestHelper.endRecordAndPrintResult("createRejectEmail",
                StressTestHelper.THIRD_LEVEL_LOAD);
    }

    /**
     * Test the method <code>retrieveRejectReason</code> with first level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testRetrieveRejectEmailFirstLevel() throws Exception {

        long id = 0;

        // test retrieve 1 from 5000
        for (int i = 1; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            RejectReason reason = new RejectReason();
            reason.setActive(true);
            reason.setDescription("test");
            reason.setCompanyId(1);

            // create email instance
            dao.createRejectReason(reason, USER);

            id = reason.getId();
        }


        StressTestHelper.startRecord();

        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            dao.retrieveRejectReason(id);
        }

        StressTestHelper.endRecordAndPrintResult("retrieveRejectReason",
                StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test the method <code>retrieveRejectReason</code> with second level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testRetrieveRejectEmailSecondLevel() throws Exception {

        long id = 0;

        // test retrieve 1 from 5000
        for (int i = 1; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            RejectReason reason = new RejectReason();
            reason.setActive(true);
            reason.setDescription("test");
            reason.setCompanyId(1);

            // create email instance
            dao.createRejectReason(reason, USER);
            id = reason.getId();
        }


        StressTestHelper.startRecord();

        for (int i = 0; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            dao.retrieveRejectReason(id);
        }

        StressTestHelper.endRecordAndPrintResult("retrieveRejectReason",
                StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test the method <code>retrieveRejectReason</code> with third level load.
     *
     * @throws Exception throw any exception to JUnit
     */
    public void testRetrieveRejectEmailThirdLevel() throws Exception {

        long id = 0;

        // test retrieve 1 from 5000
        for (int i = 1; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            RejectReason reason = new RejectReason();
            reason.setActive(true);
            reason.setDescription("test");
            reason.setCompanyId(1);

            // create email instance
            dao.createRejectReason(reason, USER);
            id = reason.getId();
        }


        StressTestHelper.startRecord();

        for (int i = 0; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            dao.retrieveRejectReason(id);
        }

        StressTestHelper.endRecordAndPrintResult("retrieveRejectReason",
                StressTestHelper.THIRD_LEVEL_LOAD);
    }

    /**
     * Test the method <code>updateRejectEmail</code> with first level load.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRejectEmailFirstLevel() throws Exception {
        RejectReason reason = null;

        // test update 1 from 5000
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            reason = new RejectReason();
            reason.setActive(true);
            reason.setDescription("test");
            reason.setCompanyId(1);

            // create email instance
            dao.createRejectReason(reason, USER);
        }

        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            dao.updateRejectReason(reason, USER);
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
        RejectReason reason = null;

        // test update 1 from 5000
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            reason = new RejectReason();
            reason.setActive(true);
            reason.setDescription("test");
            reason.setCompanyId(1);

            // create email instance
            dao.createRejectReason(reason, USER);
        }

        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            dao.updateRejectReason(reason, USER);
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
        RejectReason reason = null;

        // test update 1 from 5000
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            reason = new RejectReason();
            reason.setActive(true);
            reason.setDescription("test");
            reason.setCompanyId(1);

            // create email instance
            dao.createRejectReason(reason, USER);
        }

        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            dao.updateRejectReason(reason, USER);
        }
        StressTestHelper.endRecordAndPrintResult("updateRejectEmail",
                StressTestHelper.THIRD_LEVEL_LOAD);
    }

    /**
     * Test the method <code>deleteRejectReason</code> with first level load.
     *
     * @throws Exception throw Exception to JUnit
     */
    public void testDeleteRejectEmailFirstLevel() throws Exception {
        RejectReason reason = null;

        // test delete 1 from 5000
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            // create an email for rejection
            reason = new RejectReason();
            reason.setActive(true);
            reason.setDescription("test");
            reason.setCompanyId(1);

            // create email instance
            dao.createRejectReason(reason, USER);
        }

        StressTestHelper.startRecord();
        dao.deleteRejectReason(reason);
        StressTestHelper.endRecordAndPrintResultFrom("deleteRejectReason",
                StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test the method <code>deleteRejectReason</code> with first level load.
     *
     * @throws Exception throw Exception to JUnit
     */
    public void testDeleteRejectEmailSecondLevel() throws Exception {
        RejectReason reason = null;

        // test delete 1 from 5000
        for (int i = 0; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            // create an email for rejection
            reason = new RejectReason();
            reason.setActive(true);
            reason.setDescription("test");
            reason.setCompanyId(1);

            // create email instance
            dao.createRejectReason(reason, USER);
        }

        StressTestHelper.startRecord();
        dao.deleteRejectReason(reason);
        StressTestHelper.endRecordAndPrintResultFrom("deleteRejectEmail",
                StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test the method <code>deleteRejectEmail</code> with first level load.
     *
     * @throws Exception throw Exception to JUnit
     */
    public void testDeleteRejectEmailThirdLevel() throws Exception {
        RejectReason reason = null;

        // test delete 1 from 5000
        for (int i = 0; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            // create an email for rejection
            reason = new RejectReason();
            reason.setActive(true);
            reason.setDescription("test");
            reason.setCompanyId(1);

            // create email instance
            dao.createRejectReason(reason, USER);
        }

        StressTestHelper.startRecord();
        dao.deleteRejectReason(reason);
        StressTestHelper.endRecordAndPrintResultFrom("deleteRejectEmail",
                StressTestHelper.THIRD_LEVEL_LOAD);
    }


}
