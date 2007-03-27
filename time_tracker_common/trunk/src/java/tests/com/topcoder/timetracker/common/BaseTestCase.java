/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>This is the base test case which abstracts the common behavior.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
 */
public class BaseTestCase extends BaseBaseTestCase {
    /**
     * <p>
     * The formatter to format the <code>Date</code> to pattern "yyyy_MM_dd hh:mm:ss".
     * </p>
     *
     * <p>
     * Validate from year to second because the Informix type of creation date and modification date
     * is DATETIME YEAR TO SECOND.
     * </p>
     */
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy_MM_dd hh:mm:ss");

    /**
     * <p>
     * Represents the connection factory that is used for performing CRUD operations.
     * </p>
     */
    private static DBConnectionFactory connectionFactory = null;

    /**
     * <p>
     * Sql to run to insert required information into data store.
     * </p>
     */
    private static String insertSql = null;

    /**
     * <p>
     * Indicate whether DB has been setup.
     * </p>
     */
    private static boolean setup = false;

    /**
     * <p>
     * The creation and modification date of <code>PaymentTerm</code>.
     * </p>
     */
    private static Date date = null;

    /**
     * <p>
     * Closes the given Statement instance, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param statement the given Statement instance to close.
     */
    protected void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Closes the given Connection instance, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param con the given Connection instance to close.
     */
    protected void closeConnection(Connection con) {
        try {
            if ((con != null) && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Roll back the current connection if any error occurs while updating the persistence.
     * </p>
     *
     * <p>
     * Note, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param con the given Connection instance to roll back
     */
    protected void rollback(Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            // Just ignore
        }
    }

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        if (!setup) {
            super.setUp();
            this.setupDB();
            setup = true;
        }
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        //super.tearDown();
    }

    /**
     * <p>
     * Assert two <code>PaymentTerm</code> equal.
     * </p>
     *
     * @param one One <code>PaymentTerm</code>.
     * @param another Another <code>PaymentTerm</code>.
     */
    protected void assertPaymentTermEquals(PaymentTerm one, PaymentTerm another) {
        assertEquals("The id should equal", one.getId(), another.getId());
        assertEquals("The description should equal", one.getDescription(), another.getDescription());
        assertEquals("The creation date should equal",
                FORMATTER.format(one.getCreationDate()), FORMATTER.format(another.getCreationDate()));
        assertEquals("The creation user should equal", one.getCreationUser(), another.getCreationUser());
        assertEquals("The modification date should equal",
                FORMATTER.format(one.getModificationDate()), FORMATTER.format(another.getModificationDate()));
        assertEquals("The modification user should equal", one.getModificationUser(), another.getModificationUser());
        assertEquals("The active status should equal", one.isActive(), another.isActive());
        assertEquals("The term should equal", one.getTerm(), another.getTerm());
        assertEquals("The changed status should equal", one.isChanged(), another.isChanged());
    }

    /**
     * <p>
     * Assert the <code>PaymentTerm</code> which is inserted into data store previously.
     * This method will be called by testRetrieveXXX methods, so the changed status of
     * all the <code>PaymentTerm</code>s should be false after retrieving.
     * </p>
     *
     * <p>
     * See data.sql for details.
     * </p>
     *
     * @param paymentTerm The <code>PaymentTerm</code> to assert.
     * @param id The id of given <code>PaymentTerm</code>.
     */
    protected void assertPaymentTerm(PaymentTerm paymentTerm, long id) {

        assertEquals("The id should be " + id, id, paymentTerm.getId());
        assertEquals("The description should be " + "desc" + id, "desc" + id, paymentTerm.getDescription());

        //Assert the creation date, allow for 3 seconds error
        assertTrue("The creation date should be " + paymentTerm.getCreationDate(),
                this.getSetupDBDate().getTime() - paymentTerm.getCreationDate().getTime() >= (id - 1) *  ONEDAY
                && this.getSetupDBDate().getTime() - paymentTerm.getCreationDate().getTime()
                <= (id - 1) * ONEDAY + 3000);
        assertEquals("The creation user should be " + "creationUser" + id,
                "creationUser" + id, paymentTerm.getCreationUser());
        assertEquals("The modification date should equal creation date", paymentTerm.getCreationDate(),
                paymentTerm.getModificationDate());
        assertEquals("The modification user should be " + "modificationUser" + id,
                "modificationUser" + id, paymentTerm.getModificationUser());
        boolean active = id % 2 == 0 ? false : true;
        assertEquals("The active status should be " + active, active, paymentTerm.isActive());
        assertEquals("The term should be " + id, id, paymentTerm.getTerm());

        //The changed status should be false after retrieving
        assertFalse("The changed status should be false", paymentTerm.isChanged());
    }

    /**
     * <p>
     * The the date with seconds precision when the <code>PaymentTerm</code>s are inserted into data store.
     * </p>
     *
     * <p>
     * May be null if we can't determine the date with seconds precision.
     * </p>
     *
     * @return Date.
     */
    protected Date getSetupDBDate() {
        return date;
    }

    /**
     * <p>Get connection.</p>
     *
     * @return Connection
     *
     * @throws Exception If error occurred while retrieving connection.
     */
    protected Connection getConnection() throws Exception {
        if (connectionFactory == null) {
            this.initialConfigManager();
            connectionFactory = new DBConnectionFactoryImpl(DBCONNECTION_FACTORY_NAMESPACE);
        }

        Connection con = connectionFactory.createConnection();
        con.setAutoCommit(false);

        return con;
    }

    /**
     * <p>
     * Update IDGenerator with given next block start.
     * </p>
     *
     * @param nextBlockStart The value of next block start.
     *
     * @throws Exception to JUnit
     */
    protected void updateIDGenerator(long nextBlockStart) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.getConnection();
            ps = con.prepareStatement(
                "update id_sequences set next_block_start = ? where name = 'PaymentTermGenerator'");
            ps.setLong(1, nextBlockStart);
            ps.executeUpdate();
            con.commit();
        } finally {
            this.closeStatement(ps);
            this.closeConnection(con);
        }
    }

    /**
     * <p>Remove db information.</p>
     *
     * @throws Exception to JUnit
     */
    protected void wrapDB() throws Exception {
        Connection conn = this.getConnection();

        Statement ps = conn.createStatement();

        try {
            ps.addBatch("delete from payment_terms");
            ps.addBatch("delete from id_sequences where name = 'PaymentTermGenerator'");
            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            closeStatement(ps);
            closeConnection(conn);
        }
    }

    /**
     * <p>Set up required db information to run Unit tests.</p>
     *
     * @throws Exception to JUnit
     */
    protected void setupDB() throws Exception {
        Statement ps = null;
        Connection conn = null;

        try {
            wrapDB();

            if (insertSql == null) {
                insertSql = "";

                InputStream is =
                    this.getClass().getResourceAsStream("/UnitTests/data.sql");
                BufferedReader in = new BufferedReader(new InputStreamReader(is));

                String s = in.readLine();

                while (s != null) {
                    insertSql += s;
                    s = in.readLine();
                }

                in.close();
            }

            conn = getConnection();

            StringTokenizer st = new StringTokenizer(insertSql, ";");

            ps = conn.createStatement();

            while (st.hasMoreTokens()) {
                ps.addBatch(st.nextToken());
            }

            ps.executeBatch();
            date = new Date();
            conn.commit();

        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            closeStatement(ps);
            closeConnection(conn);
        }
    }
}
