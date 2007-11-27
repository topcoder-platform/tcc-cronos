/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.user.BatchOperationException;
import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.TestHelper;
import com.topcoder.timetracker.user.UnrecognizedEntityException;
import com.topcoder.timetracker.user.UserStatus;
import com.topcoder.timetracker.user.db.DbUtil.SqlType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>DbUtil</code>.
 *
 * @author enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class DbUtilTest extends TestCase {

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory dbFactory;

    /**
     * <p>
     * Returns the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(DbUtilTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE_3_2_1);

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        super.tearDown();
    }

    /**
     * Test <code>getConnection</code> for accuracy. Condition: normal. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetConnectionAccuracy1() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);
        assertNotNull("The connection can't be created", connection);
        connection.close();
    }

    /**
     * Test <code>getConnection</code> for accuracy. Condition: normal. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetConnectionAccuracy2() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, "tt_user", false);
        assertNotNull("The connection can't be created", connection);
        connection.close();
    }

    /**
     * Test <code>getConnection</code> for accuracy. Condition: normal. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetConnectionAccuracy3() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, "tt_user", true);
        assertNotNull("The connection can't be created", connection);
        connection.close();
    }

    /**
     * Test <code>getConnection</code> for failure. Condition: connFactory is null. Expect:
     * <code>NullPointerException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetConnectionFactoryNull() throws Exception {
        try {
            DbUtil.getConnection(null, null, false);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * Test <code>getConnection</code> for failure. Condition: no connection name is the configuration. Expect:
     * <code>DataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetConnectionFactoryNoConnectionName() throws Exception {
        try {
            DbUtil.getConnection(dbFactory, "noConnectionName", false);
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>closeConnection</code> for accuracy. Condition: normal. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCloseConnectionAccuracy() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);
        DbUtil.closeConnection(connection);
    }

    /**
     * Test <code>closeConnection</code> for accuracy. Condition: connection is null. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCloseConnectionNull() throws Exception {
        DbUtil.closeConnection(null);
    }

    /**
     * Test <code>closeConnection</code> for accuracy. Condition: connection is already closed. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCloseConnectionAlreadyClosed() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);

        connection.close();

        DbUtil.closeConnection(connection);
    }

    /**
     * Test <code>fillPreparedStatement</code> for accuracy. Condition: normal. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testFillPreparedStatementAccuracy1() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);
        PreparedStatement prepareStatement = connection.prepareStatement("delete from user_status");

        List params = new ArrayList();

        DbUtil.fillPreparedStatement(prepareStatement, params);

        prepareStatement.close();
        connection.close();
    }

    /**
     * Test <code>fillPreparedStatement</code> for accuracy. Condition: normal. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testFillPreparedStatementAccuracy2() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);
        PreparedStatement prepareStatement =
            connection.prepareStatement("delete from user_status where user_status_id=? and "
                + "description=? and active=? and company_id=?");

        List params = new ArrayList();
        params.add(new SqlType(Types.INTEGER));
        params.add("set");
        params.add(new Date());
        params.add(new Long(100));

        DbUtil.fillPreparedStatement(prepareStatement, params);

        prepareStatement.close();
        connection.close();
    }

    /**
     * Test <code>fillPreparedStatement</code> for failure. Condition: too many parameters. Expect:
     * <code>SQLException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testFillPreparedStatementTooMany() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);
        PreparedStatement prepareStatement = connection.prepareStatement("delete from user_status");

        List params = new ArrayList();
        params.add("Test");
        try {
            DbUtil.fillPreparedStatement(prepareStatement, params);
            fail("Should throw SQLException");
        } catch (SQLException e) {
            // expected
        }

        prepareStatement.close();
        connection.close();
    }

    /**
     * Test <code>closeStatement</code> for accuracy. Condition: normal. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCloseStatementAccuracy() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);

        Statement stat = connection.createStatement();

        DbUtil.closeStatement(stat);

        DbUtil.closeConnection(connection);
    }

    /**
     * Test <code>closeStatement</code> for accuracy. Condition: statement is null. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCloseStatementNull() throws Exception {
        DbUtil.closeStatement(null);
    }

    /**
     * Test <code>closeStatement</code> for accuracy. Condition: statement is already closed. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCloseStatementAlreadyClosed() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);

        Statement stat = connection.createStatement();

        stat.close();

        DbUtil.closeStatement(stat);

        DbUtil.closeConnection(connection);
    }

    /**
     * Test <code>closeStatement</code> for accuracy. Condition: connection is already closed. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCloseStatementConnectionAlreadyClosed() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);

        Statement stat = connection.createStatement();
        DbUtil.closeConnection(connection);

        DbUtil.closeStatement(stat);
    }

    /**
     * Test <code>closeResultSet</code> for accuracy. Condition: normal. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCloseResultSetAccuracy() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);

        Statement stat = connection.createStatement();

        ResultSet rs = stat.executeQuery("select * from user_status");
        DbUtil.closeResultSet(rs);

        DbUtil.closeStatement(stat);

        DbUtil.closeConnection(connection);
    }

    /**
     * Test <code>closeResultSet</code> for accuracy. Condition: resultSet is null. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCloseResultSetNull() throws Exception {
        DbUtil.closeResultSet(null);
    }

    /**
     * Test <code>closeResultSet</code> for accuracy. Condition: result set is already closed. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCloseResultSetAlreadyClosed() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);

        Statement stat = connection.createStatement();

        ResultSet rs = stat.executeQuery("select * from user_status");
        rs.close();
        DbUtil.closeResultSet(rs);

        DbUtil.closeStatement(stat);

        DbUtil.closeConnection(connection);
    }

    /**
     * Test <code>finishBatchOperation</code> for accuracy. Condition: with transaction. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testFinishBatchOperationAccuracy1() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);
        connection.setAutoCommit(false);
        DbUtil.finishBatchOperation(connection, new Throwable[0], true, "nothing", true);

        DbUtil.closeConnection(connection);
    }

    /**
     * Test <code>finishBatchOperation</code> for accuracy. Condition: without transaction. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testFinishBatchOperationAccuracy2() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);

        DbUtil.finishBatchOperation(connection, new Throwable[0], true, "nothing", false);

        DbUtil.closeConnection(connection);
    }

    /**
     * Test <code>finishBatchOperation</code> for failure. Condition: there is an error. Expect:
     * <code>BatchOperationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testFinishBatchOperationError() throws Exception {
        Connection connection = DbUtil.getConnection(dbFactory, null, false);
        try {
            DbUtil.finishBatchOperation(connection, new Throwable[0], false, "nothing", true);
            fail("Should throw IllegalArgumentException");
        } catch (BatchOperationException e) {
            // expected
        }

        DbUtil.closeConnection(connection);
    }

    /**
     * Test <code>prepareTimeTrackerBeanParams</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testPrepareTimeTrackerBeanParamsAccuracy() {
        TimeTrackerBean bean = new UserStatus();
        List params = new ArrayList();

        DbUtil.prepareTimeTrackerBeanParams(params, bean);
    }

    /**
     * Test <code>prepareTimeTrackerBeanParams</code> for failure. Condition: params is null. Expect:
     * <code>NullPointerException</code>.
     */
    public void testPrepareTimeTrackerBeanParamsParamsNull() {
        TimeTrackerBean bean = new UserStatus();
        try {
            DbUtil.prepareTimeTrackerBeanParams(null, bean);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

    }

    /**
     * Test <code>prepareTimeTrackerBeanParams</code> for failure. Condition: bean is null. Expect:
     * <code>NullPointerException</code>.
     */
    public void testPrepareTimeTrackerBeanParamsBeanNull() {
        List params = new ArrayList();

        try {
            DbUtil.prepareTimeTrackerBeanParams(params, null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * Test <code>prepareSearchBundle</code> for accuracy. Condition: normal. Expect: search bundle is returned
     * properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPrepareSearchBundleAccuracy() throws Exception {
        assertNotNull("The search bundle can't be retrieved properly", DbUtil.prepareSearchBundle(
            "com.topcoder.search.builder", "userStatusSearchBundle"));
    }

    /**
     * Test <code>prepareSearchBundle</code> for failure. Condition: searchBundleManagerNamespace is null.
     * Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPrepareSearchBundleNullNamespace() throws Exception {
        try {
            DbUtil.prepareSearchBundle(null, "userStatusSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>prepareSearchBundle</code> for failure. Condition: searchBundleManagerNamespace is empty.
     * Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPrepareSearchBundleEmptyNamespace() throws Exception {
        try {
            DbUtil.prepareSearchBundle("  ", "userStatusSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

    }

    /**
     * Test <code>prepareSearchBundle</code> for failure. Condition: searchBundleName is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPrepareSearchBundleNullName() throws Exception {
        try {
            DbUtil.prepareSearchBundle("com.topcoder.search.builder", null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>prepareSearchBundle</code> for failure. Condition: searchBundleName is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPrepareSearchBundleEmptyName() throws Exception {
        try {
            DbUtil.prepareSearchBundle("com.topcoder.search.builder", "  \t");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>prepareSearchBundle</code> for failure. Condition: no configuration is available. Expect:
     * <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPrepareSearchBundleNoConfig() throws Exception {
        TestHelper.clearConfig();
        try {
            DbUtil.prepareSearchBundle("com.topcoder.search.builder", "userStatusSearchBundle");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test <code>createIDGenerator</code> for accuracy. Condition: normal. Expect: id generator is created
     * properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateIDGeneratorAccuracy() throws Exception {
        TestHelper.setUpDataBase();
        DbUtil.createIDGenerator("com.topcoder.timetracker.user.User");
        TestHelper.tearDownDataBase();
    }

    /**
     * Test <code>createIDGenerator</code> for failure. Condition: key is null. Expect:
     * <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateIDGeneratorNull() throws Exception {
        try {
            DbUtil.createIDGenerator(null);
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test <code>createIDGenerator</code> for failure. Condition: key is not known. Expect:
     * <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateIDGeneratorNotKnown() throws Exception {
        try {
            DbUtil.createIDGenerator("notKnown");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test <code>checkTimeTrackerBean</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testCheckTimeTrackerBeanAccuracy() {
        TimeTrackerBean bean = new UserStatus();

        bean.setCreationDate(new Date());
        bean.setCreationUser("user");
        bean.setModificationDate(new Date());
        bean.setModificationUser("user");

        DbUtil.checkTimeTrackerBean(bean);
    }

    /**
     * Test <code>checkTimeTrackerBean</code> for failure. Condition: creationDate is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCheckTimeTrackerBeanCreationDateNull() {
        TimeTrackerBean bean = new UserStatus();

        bean.setCreationUser("user");
        bean.setModificationDate(new Date());
        bean.setModificationUser("user");

        try {
            DbUtil.checkTimeTrackerBean(bean);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>checkTimeTrackerBean</code> for failure. Condition: creationUser is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCheckTimeTrackerBeanCreationUserNull() {
        TimeTrackerBean bean = new UserStatus();

        bean.setCreationDate(new Date());
        bean.setModificationDate(new Date());
        bean.setModificationUser("user");

        try {
            DbUtil.checkTimeTrackerBean(bean);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>checkTimeTrackerBean</code> for failure. Condition: modificationDate is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCheckTimeTrackerBeanModificationDateNull() {
        TimeTrackerBean bean = new UserStatus();

        bean.setCreationDate(new Date());
        bean.setCreationUser("user");
        bean.setModificationUser("user");

        try {
            DbUtil.checkTimeTrackerBean(bean);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>checkTimeTrackerBean</code> for failure. Condition: modificationUser is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCheckTimeTrackerBeanModificationUserNull() {
        TimeTrackerBean bean = new UserStatus();

        bean.setCreationDate(new Date());
        bean.setCreationUser("user");
        bean.setModificationDate(new Date());

        try {
            DbUtil.checkTimeTrackerBean(bean);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
