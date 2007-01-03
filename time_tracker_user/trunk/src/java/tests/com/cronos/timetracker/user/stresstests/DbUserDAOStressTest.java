package com.cronos.timetracker.user.stresstests;

import junit.framework.TestCase;

import com.cronos.timetracker.user.DbUserDAO;
import com.cronos.timetracker.user.User;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * Stress test for <code>DbUserDAO</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class DbUserDAOStressTest extends TestCase {

    /**
     * Represents the user to create the email.
     */
    private static final String USER = "user";

    /**
     * The DbRejectCompanyDAO instance to test on.
     */
    private DbUserDAO dao = null;

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
        dao = new DbUserDAO(factory,
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
     * Test method <code>createUser()</code> for first level.
     *
     * @throws Exception to JUnit
     */
    public void testCreateUserFirstLevel() throws Exception {
        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            User user = StressTestHelper.createUser(i);
            dao.createUser(user, USER);
        }
        StressTestHelper.endRecordAndPrintResult("createUser", StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test method <code>createUser()</code> for first level.
     *
     * @throws Exception to JUnit
     */
    public void testCreateUserSecondLevel() throws Exception {
        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            User user = StressTestHelper.createUser(i);
            dao.createUser(user, USER);
        }
        StressTestHelper.endRecordAndPrintResult("createUser", StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test method <code>createUser()</code> for first level.
     *
     * @throws Exception to JUnit
     */
    public void testCreateUserThirdLevel() throws Exception {
        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            User user = StressTestHelper.createUser(i);
            dao.createUser(user, USER);
        }
        StressTestHelper.endRecordAndPrintResult("createUser", StressTestHelper.THIRD_LEVEL_LOAD);
    }

    /**
     * Test method <code>retrieveUser()</code> for first level.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveUserFirstLevel() throws Exception {
        long id = 0;
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            User uesr = StressTestHelper.createUser(i);
            dao.createUser(uesr, USER);
            id = uesr.getId();
        }

        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            dao.retrieveUser(id);
        }
        StressTestHelper.endRecordAndPrintResult("retrieveUser", StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test method <code>retrieveUser()</code> for first level.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveUserSecondLevel() throws Exception {
        long id = 0;
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            User uesr = StressTestHelper.createUser(i);
            dao.createUser(uesr, USER);
            id = uesr.getId();
        }

        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            dao.retrieveUser(id);
        }
        StressTestHelper.endRecordAndPrintResult("retrieveUser", StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test method <code>retrieveUser()</code> for first level.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveUserThirdLevel() throws Exception {
        long id = 0;
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            User uesr = StressTestHelper.createUser(i);
            dao.createUser(uesr, USER);
            id = uesr.getId();
        }

        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            dao.retrieveUser(id);
        }
        StressTestHelper.endRecordAndPrintResult("retrieveUser", StressTestHelper.THIRD_LEVEL_LOAD);
    }

    /**
     * Test method <code>updateUser()</code> for first level.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateUserFirstLevel() throws Exception {
        User user = null;
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            user = StressTestHelper.createUser(i);
            dao.createUser(user, USER);
        }

        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            dao.updateUser(user, USER);
        }
        StressTestHelper.endRecordAndPrintResult("updateUser", StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test method <code>updateUser()</code> for second level.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateUserSecondLevel() throws Exception {
        User user = null;
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            user = StressTestHelper.createUser(i);
            dao.createUser(user, USER);
        }

        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            dao.updateUser(user, USER);
        }
        StressTestHelper.endRecordAndPrintResult("updateUser", StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test method <code>updateUser()</code> for third level.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateUserThirdLevel() throws Exception {
        User user = null;
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            user = StressTestHelper.createUser(i);
            dao.createUser(user, USER);
        }

        StressTestHelper.startRecord();
        for (int i = 0; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            dao.updateUser(user, USER);
        }
        StressTestHelper.endRecordAndPrintResult("updateUser", StressTestHelper.THIRD_LEVEL_LOAD);
    }

    /**
     * Test method <code>deleteUser()</code> for first level.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteUserFirstLevel() throws Exception {
        User user = null;
        for (int i = 0; i < StressTestHelper.FIRST_LEVEL_LOAD; i++) {
            user = StressTestHelper.createUser(i);
            dao.createUser(user, USER);
        }

        StressTestHelper.startRecord();
        dao.deleteUser(user);
        StressTestHelper.endRecordAndPrintResultFrom("deleteUser",
                StressTestHelper.FIRST_LEVEL_LOAD);
    }

    /**
     * Test method <code>deleteUser()</code> for second level.
     *
     * @throws Exception to JUnit
     */
    public void testdeleteUserSecondLevel() throws Exception {
        User user = null;
        for (int i = 0; i < StressTestHelper.SECOND_LEVEL_LOAD; i++) {
            user = StressTestHelper.createUser(i);
            dao.createUser(user, USER);
        }

        StressTestHelper.startRecord();
        dao.deleteUser(user);
        StressTestHelper.endRecordAndPrintResultFrom("deleteUser",
                StressTestHelper.SECOND_LEVEL_LOAD);
    }

    /**
     * Test method <code>deleteUser()</code> for third level.
     *
     * @throws Exception to JUnit
     */
    public void testdeleteUserThirdLevel() throws Exception {
        User user = null;
        for (int i = 0; i < StressTestHelper.THIRD_LEVEL_LOAD; i++) {
            user = StressTestHelper.createUser(i);
            dao.createUser(user, USER);
        }

        StressTestHelper.startRecord();
        dao.deleteUser(user);
        StressTestHelper.endRecordAndPrintResultFrom("deleteUser",
                StressTestHelper.THIRD_LEVEL_LOAD);
    }

}
