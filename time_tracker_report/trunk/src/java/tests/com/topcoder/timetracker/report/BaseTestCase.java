/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Date;
import java.util.StringTokenizer;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.user.User;

import com.topcoder.timetracker.user.Status;

import com.topcoder.timetracker.report.informix.ReportEntryBean;

import com.topcoder.timetracker.report.informix.FixedBillingEntryReport;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;

import com.topcoder.timetracker.report.informix.ExpenseEntryReport;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.ExpenseType;

import com.topcoder.timetracker.report.informix.TimeEntryReport;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.TaskType;

import junit.framework.TestCase;

/**
 * <p>
 * This is the base test case which abstracts the common behaviors of other test cases.
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class BaseTestCase extends TestCase {

    /**
     * <p>
     * Represents the location of the sample configuration file to test the Config Manager.
     * </p>
     */
    public static final String CONFIGURATION_FILE_LOCATION = "config.xml";

    /**
     * <p>
     * Represents the location of the sample configuration file for failure tests which need wrong
     * configuration in the config file. This file contains the information for DB connection
     * factory only.
     * </p>
     */
    public static final String ERROR_CONFIGURATION_FILE_2_LOCATION = "config_error2.xml";

    /**
     * <p>
     * Represents the location of the sample configuration file for failure tests which need wrong
     * configuration in the config file.
     * </p>
     */
    public static final String ERROR_CONFIGURATION_FILE_LOCATION = "config_error.xml";

    /**
     * <p>
     * Represents the location of the sample data file.
     * </p>
     */
    public static final String DATA_FILE_LOCATION = "/Time_Tracker_Report_v_3_1_add_data.sql";

    /**
     * <p>
     * The connection name used for unit tests.
     * </p>
     */
    public static final String CONNECTION_NAME = "Informix";

    /**
     * <p>
     * The namespace for DB Connection Factory component.
     * </p>
     */
    public static final String DB_CONNECTION_FACTORY_NAMESPACE =
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * The milliseconds of one day(24 hours).
     * </p>
     */
    public static final long ONEDAY = 1000 * 60 * 60 * 24;

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
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    public static final String ERROR_MESSAGE = "error message";

    /**
     * <p>
     * An exception instance used as the cause of error.
     * </p>
     */
    public static final Exception CAUSE = new Exception();

    /**
     * <p>
     * Represents the connection factory that is used for performing database operations.
     * </p>
     */
    private static DBConnectionFactory dbConnectionFactory = null;

    /**
     * <p>
     * This data object is used to store the timestamp when the database is set up. This is used to
     * validate the create_date and modification_date of cutoff time records.
     * </p>
     */
    private static Date dBsetupDate = null;

    /**
     * <p>
     * An auto-increasing number. It is increased by 1 each time it is used.
     * </p>
     */
    private static long autoNumber = 1000;

    /**
     * <p>
     * Setup the test case. Initial the <code>ConfigManager</code> with the configuration files
     * stored in test_files folder.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.removeConfigManagerNS();
        this.initConfigManager();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        this.removeConfigManagerNS();
        super.tearDown();
    }

    /**
     * <p>
     * Asserts if the 2 dates are different only 10 seconds (at most).
     * </p>
     *
     * @param msg the error message
     * @param date1 the first date
     * @param date2 the second date
     */

    protected void assertDateDiff10Seconds(String msg, Date date1, Date date2) {
        long difference = Math.abs(date1.getTime() - date2.getTime());
        assertTrue(msg, (0 <= difference && difference <= 100000));
    }

    /**
     * <p>
     * Retrieves a number. It is guaranteed to be unique.
     * </p>
     *
     * @return next auto number
     */
    protected long getAutoNumber() {
        return autoNumber++;
    }

    /**
     * <p>
     * Initial the <code>ConfigManager</code> with the configuration files in the test_files.
     * </p>
     *
     * @return initialized ConfigManager instance
     *
     * @throws ConfigManagerException to JUnit
     */
    protected ConfigManager initConfigManager() throws ConfigManagerException {
        // remove all previously stored namespaces
        removeConfigManagerNS();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add(CONFIGURATION_FILE_LOCATION);

        return cm;
    }

    /**
     * <p>
     * Remove all namespaces from config manager.
     * </p>
     *
     * @throws ConfigManagerException to JUnit
     */
    protected void removeConfigManagerNS() throws ConfigManagerException {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>
     * Retrieves a connection to the database.
     * </p>
     *
     * @return A not-null connection to the database.
     *
     * @throws Exception if connection creation failed.
     */
    protected Connection getConnection() throws Exception {
        if (dbConnectionFactory == null) {
            this.initConfigManager();
            dbConnectionFactory = new DBConnectionFactoryImpl(DB_CONNECTION_FACTORY_NAMESPACE);
        }
        Connection conn = dbConnectionFactory.createConnection();
        conn.setAutoCommit(false);

        return conn;
    }

    /**
     * <p>
     * Closes the given Connection instance, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param conn the given Connection instance to close.
     */
    protected void closeConnection(Connection conn) {
        try {
            if ((conn != null) && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            // Ignore
        }
    }

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
        } catch (SQLException ex) {
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
     * @param conn the given Connection instance to roll back
     */
    protected void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException ex) {
            // Just ignore
        }
    }

    /**
     * <p>
     * Remove information stored in the database.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void clearDatabase() throws Exception {
        Connection conn = this.getConnection();

        Statement statement = conn.createStatement();

        try {
            // general
            statement.addBatch("delete from client");
            statement.addBatch("delete from client_project");
            statement.addBatch("delete from project");
            statement.addBatch("delete from user_account");
            statement.addBatch("delete from project_worker");

            // fixedbilling entry
            statement.addBatch("delete from project_fix_bill");
            statement.addBatch("delete from fix_bill_entry");
            statement.addBatch("delete from fix_bill_status");
            statement.addBatch("delete from fix_bill_type");

            // expense entry
            statement.addBatch("delete from project_expense");
            statement.addBatch("delete from expense_entry");
            statement.addBatch("delete from expense_status");
            statement.addBatch("delete from expense_type");

            // time entry
            statement.addBatch("delete from project_time");
            statement.addBatch("delete from time_entry");
            statement.addBatch("delete from time_status");
            statement.addBatch("delete from task_type");

            // clear the all tables
            statement.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Set up a clean testing database information to run Unit tests.
     * </p>
     *
     * @param datafile the name of the data file.
     *
     * @throws Exception to JUnit
     */
    protected void setupDatabase(String datafile) throws Exception {
        Statement statement = null;
        Connection conn = null;

        try {
            clearDatabase();

            String queries = "";

            // read SQL queries from file
            InputStream is = this.getClass().getResourceAsStream(datafile);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            do {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                if (line.trim().length() == 0) {
                    continue;
                }
                queries += line;
            } while (true);
            in.close();

            conn = getConnection();
            statement = conn.createStatement();

            StringTokenizer st = new StringTokenizer(queries, ";");
            while (st.hasMoreTokens()) {
                statement.addBatch(st.nextToken());
            }

            statement.executeBatch();
            conn.commit();

            dBsetupDate = new Date(); // saved the time the database was set up
        } catch (SQLException ex) {
            rollback(conn);
            throw ex;
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves the time when the database information was set up for test cases.
     * </p>
     *
     * @return the time when database information was set up.
     */
    protected Date getDBSetupDate() {
        return dBsetupDate;
    }

    /**
     * <p>
     * Get value of given <code>Field</code> of given <code>Object</code>.
     * </p>
     *
     * @param object instance to get field from
     * @param fieldName name of field
     *
     * @return value of field
     *
     * @throws Exception to JUnit
     */
    protected Object getField(Object object, String fieldName) throws Exception {
        Field f = object.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(object);
    }

    /**
     * <p>
     * Get value of given <code>Field</code> of given <code>Object</code>. The field is not
     * declared in the class of the object, but in it superclass.
     * </p>
     *
     * @param object instance to get field from
     * @param fieldName name of field of superclass of object
     *
     * @return value of field
     *
     * @throws Exception to JUnit
     */
    protected Object getFieldOfSuperClass(Object object, String fieldName) throws Exception {
        Field f = object.getClass().getSuperclass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(object);
    }

    /**
     * <p>
     * Convert the object to some chosen string representation.
     * </p>
     *
     * @param obj the object to be converted.
     * @return the formatted string of obj
     */
    protected String formatObject(Object obj) {
        if (obj instanceof Date) {
            return FORMATTER.format((Date) obj);
        }
        if (obj instanceof Long) {
            return "" + obj;
        }
        if (obj instanceof String) {
            return "" + obj;
        }
        return null;
    }

    /**
     * <p>
     * Validate the Client object using the provide <code>id</code>. From the <code>id</code>
     * we can find out how the entry should look like in the database. Please see readme.txt
     * </p>
     *
     * @param client the Client object to be checked.
     * @param id fix_bill_status_id of the expected record in the database.
     */
    protected void assertClient(Client client, long id) {
        // id can be 1 or 2
        if (id < 1 || id > 2) {
            return;
        }

        // check all fields
        assertEquals("client.id should be " + id, id, client.getId());
        assertEquals("client.name should be 'client" + id + "'.", "client" + id, client.getName());
        assertEquals("client.companyId should be " + (100 + id), (100 + id), client.getCompanyId());
        assertEquals(
                "client.creationUser should be 'creationUser" + id + "'.",
                "creationUser" + id,
                client.getCreationUser());
        assertEquals("client.modificationUser should be 'modificationUser" + id + "'.", "modificationUser"
                + id, client.getModificationUser());

        Date setupDate = this.getDBSetupDate();

        assertDateDiff10Seconds("client.creationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), client.getCreationDate());
        assertDateDiff10Seconds("client.modificationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), client.getModificationDate());
    }

    /**
     * <p>
     * Validate the Project object using the provide <code>id</code>. From the <code>id</code>
     * we can find out how the entry should look like in the database. Please see readme.txt
     * </p>
     *
     * @param project the Project object to be checked.
     * @param id fix_bill_status_id of the expected record in the database.
     */
    protected void assertProject(Project project, long id) {
        // id can be 1 to 3
        if (id < 1 || id > 3) {
            return;
        }

        // check all fields
        assertEquals("project.id should be " + id, id, project.getId());
        assertEquals("project.name should be 'project" + id + "'.", "project" + id, project.getName());
        assertEquals("project.companyId should be " + (100 + id), (100 + id), project.getCompanyId());
        assertEquals(
                "project.description should be 'projectDesc" + id + "'.",
                "projectDesc" + id,
                project.getDescription());

        assertEquals(
                "project.creationUser should be 'creationUser" + id + "'.",
                "creationUser" + id,
                project.getCreationUser());
        assertEquals("project.modificationUser should be 'modificationUser" + id + "'.", "modificationUser"
                + id, project.getModificationUser());

        Date setupDate = this.getDBSetupDate();
        assertDateDiff10Seconds("project.startDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), project.getStartDate());
        assertDateDiff10Seconds(
                "project.endDate is not correct.",
                new Date(setupDate.getTime() + id * ONEDAY),
                project.getEndDate());

        assertDateDiff10Seconds("project.creationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), project.getCreationDate());
        assertDateDiff10Seconds("project.modificationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), project.getModificationDate());
    }

    /**
     * <p>
     * Validate the User object using the provide <code>id</code>. From the <code>id</code> we
     * can find out how the entry should look like in the database. Please see readme.txt
     * </p>
     *
     * @param user the User object to be checked.
     * @param id fix_bill_status_id of the expected record in the database.
     */
    protected void assertUser(User user, long id) {
        // id can be 1 to 5
        if (id < 1 || id > 5) {
            return;
        }

        // check all fields
        assertEquals("user.id should be " + id, id, user.getId());
        assertEquals("user.companyId should be " + (100 + id), (100 + id), user.getCompanyId());
        assertEquals(
                "user.userName should be 'creationUser" + id + "'.",
                "creationUser" + id,
                user.getUsername());
        assertEquals("user.password should be 'password" + id + "'.", "password" + id, user.getPassword());
        assertEquals(
                "user.creationUser should be 'creationUser" + id + "'.",
                "creationUser" + id,
                user.getCreationUser());
        assertEquals(
                "user.modificationUser should be 'modificationUser" + id + "'.",
                "modificationUser" + id,
                user.getModificationUser());

        Date setupDate = this.getDBSetupDate();

        assertDateDiff10Seconds("user.creationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), user.getCreationDate());
        assertDateDiff10Seconds("user.modificationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), user.getModificationDate());

        // check the status
        Status status = user.getStatus();
        assertNotNull("user.status should not be null.", status);
        if (id == 1 || id == 4 || id == 5) {
            assertEquals("The status should be " + Status.ACTIVE, Status.ACTIVE, status);
        } else if (id == 2) {
            assertEquals("The status should be " + Status.INACTIVE, Status.INACTIVE, status);
        } else if (id == 3) {
            assertEquals("The status should be " + Status.LOCKED, Status.LOCKED, status);
        }
    }

    /**
     * <p>
     * Validate the ProjectWorker object using the provide <code>id</code>. From the
     * <code>id</code> we can find out how the entry should look like in the database. Please see
     * readme.txt
     * </p>
     *
     * @param worker the ProjectWorker object to be checked.
     * @param id fix_bill_status_id of the expected record in the database.
     *
     * @throws Exception to JUnit
     */
    protected void assertProjectWorker(ProjectWorker worker, long id) throws Exception {

        // id can be 1 to 5
        if (id < 1 || id > 5) {
            return;
        }

        long projectId = (id - 1) / 2 + 1;

        // check all fields
        assertEquals("worker.project_id should be " + projectId, projectId, worker.getProjectId());
        assertEquals("worker.user_account_id should be " + id, id, worker.getUserId());
        assertEquals("worker.pay_rate should be " + (id * 1.0), (id * 1.0), worker.getPayRate(), 1e-5);

        assertDateDiff10Seconds("worker.start_date is not correct.", new Date(FORMATTER.parse(
                "2006-12-31 00:00:00").getTime()
                - id
                * ONEDAY), worker.getStartDate());

        assertDateDiff10Seconds("worker.end_date is not correct.", new Date(FORMATTER.parse(
                "2006-12-31 00:00:00").getTime()
                + id
                * ONEDAY), worker.getEndDate());

        assertEquals(
                "worker.creationUser should be 'creationUser" + id + "'.",
                "creationUser" + id,
                worker.getCreationUser());
        assertEquals("worker.modificationUser should be 'modificationUser" + id + "'.", "modificationUser"
                + id, worker.getModificationUser());

        Date setupDate = this.getDBSetupDate();
        assertDateDiff10Seconds("worker.creationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), worker.getCreationDate());
        assertDateDiff10Seconds("worker.modificationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), worker.getModificationDate());
    }

    /**
     * <p>
     * Validate the FixedBillingEntry object using the provide <code>id</code>. From the
     * <code>id</code> we can find out how the entry should look like in the database. Please see
     * readme.txt
     * </p>
     *
     * @param entry the FixedBillingEntry object to be checked.
     * @param id fix_bill_entry_id of the expected record in the database.
     *
     * @throws Exception to JUnit
     */
    protected void assertFixedBillingEntry(FixedBillingEntry entry, long id) throws Exception {
        // id can be from 1 to 5 only.
        if (id < 1 || id > 5) {
            return;
        }

        assertEquals("entry.id should be " + id, id, entry.getId());
        assertEquals("entry.companyId should be " + (100 + id), (100 + id), entry.getCompanyId());
        assertEquals("entry.invoiceId should be " + id, id, entry.getInvoiceId());
        assertEquals("entry.amount should be " + (double) id, (double) id, entry.getAmount(), 1e-5);

        assertEquals(
                "entry.description should be 'fixbillentryDesc" + id + "'.",
                "fixbillentryDesc" + id,
                entry.getDescription());
        assertEquals(
                "entry.creationUser should be 'creationUser" + id + "'.",
                "creationUser" + id,
                entry.getCreationUser());
        assertEquals("entry.modificationUser should be 'modificationUser" + id + "'.", "modificationUser"
                + id, entry.getModificationUser());

        assertDateDiff10Seconds("entry.entry_date is not correct.", new Date(FORMATTER.parse(
                "2006-12-31 00:00:00").getTime()
                - id
                * ONEDAY), entry.getDate());
        Date setupDate = this.getDBSetupDate();
        assertDateDiff10Seconds("entry.creationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), entry.getCreationDate());
        assertDateDiff10Seconds("entry.modificationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), entry.getModificationDate());

        FixedBillingStatus status = entry.getFixedBillingStatus();
        assertNotNull("entry.fixedBillingStatus should not be null", status);
        assertEquals("entry.fixedBillingStatus.id should be " + id, id, status.getId());
        this.assertFixedBillingStatus(status, id);
    }

    /**
     * <p>
     * Validate the FixedBillingStatus object using the provide <code>id</code>. From the
     * <code>id</code> we can find out how the entry should look like in the database. Please see
     * readme.txt
     * </p>
     *
     * @param status the FixedBillingStatus object to be checked.
     * @param id fix_bill_status_id of the expected record in the database.
     */
    protected void assertFixedBillingStatus(FixedBillingStatus status, long id) {
        // id can be from 1 to 5 only.
        if (id < 1 || id > 5) {
            return;
        }

        assertEquals("status.id should be " + id, id, status.getId());

        assertEquals(
                "status.description should be 'fixbillstatusDesc" + id + "'.",
                "fixbillstatusDesc" + id,
                status.getDescription());
        assertEquals(
                "status.creationUser should be 'creationUser" + id + "'.",
                "creationUser" + id,
                status.getCreationUser());
        assertEquals("status.modificationUser should be 'modificationUser" + id + "'.", "modificationUser"
                + id, status.getModificationUser());

        Date setupDate = this.getDBSetupDate();

        assertDateDiff10Seconds("status.creationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), status.getCreationDate());
        assertDateDiff10Seconds("status.modificationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), status.getModificationDate());
    }

    /**
     * <p>
     * Validate whether the FixedBillingEntryReport record reflects the one in the database.
     * </p>
     *
     * @param report the FixedBillingEntryReport object to be checked.
     *
     * @throws Exception to JUnit
     */
    protected void assertFixedBillingEntryReport(FixedBillingEntryReport report) throws Exception {
        // assert all components of report should not be null
        Client client = report.getClient();
        Project project = report.getProject();
        User user = report.getUser();
        FixedBillingEntry fbEntry = report.getFixedBillingEntry();
        FixedBillingStatus fbStatus = report.getFixedBillingStatus();

        assertNotNull("Client object should not be null. ", client);
        assertNotNull("Project object should not be null. ", project);
        assertNotNull("User object should not be null. ", user);
        assertNotNull("FixedBillingEntry object should not be null. ", fbEntry);
        assertNotNull("FixedBillingStatus object should not be null. ", fbStatus);

        // get the fix_bill_entry#fix_bill_entry_id
        long id = fbEntry.getId();
        long projectId = (id - 1) / 2 + 1;
        long clientId = (projectId - 1) / 2 + 1;

        // from this id, we can find out all the expected values
        this.assertFixedBillingEntry(fbEntry, id);
        this.assertFixedBillingStatus(fbStatus, id);

        this.assertProject(project, projectId);
        this.assertClient(client, clientId);
        this.assertUser(user, id);
    }

    /**
     * <p>
     * Validate whether the FixedBillingEntryReport record reflects the one in the database.
     * </p>
     *
     * @param reports the array of FixedBillingEntryReport to check.
     * @param expectedLength the expected length of the array ( as calculated from the sample data).
     *        expectedLength is always >=0.
     *
     * @throws Exception to JUnit
     */
    protected void assertArrayFixedBillingEntryReport(FixedBillingEntryReport[] reports, int expectedLength)
        throws Exception {

        assertNotNull("Array reports should not be null.", reports);

        // check the length
        assertEquals(
                "There should be " + expectedLength + " reports returned",
                expectedLength,
                reports.length);

        // validate the FixedBillingEntryReport records.
        int[] checked = new int[5];
        for (int i = 0; i < expectedLength; i++) {
            assertNotNull("reports[" + i + "] should not be null", reports[i]);
            this.assertFixedBillingEntryReport((FixedBillingEntryReport) reports[i]);
            checked[(int) reports[i].getFixedBillingEntry().getId() - 1]++;
        }

        // check if there was a duplicate (or missing) record
        for (int i = 0; i < expectedLength; i++) {
            if (checked[i] > 1) {
                fail("The return report array contains duplicate records.");
            }
        }
    }

    /**
     * <p>
     * Validate whether the FixedBillingEntryReport record reflects the one in the database.
     * </p>
     *
     * @param reports the array of ReportEntryBean to check. All elements have to be instance of
     *        FixedBillingEntryReport.
     * @param expectedLength the expected length of the array ( as calculated from the sample data).
     *        expectedLength is always >=0.
     *
     * @throws Exception to JUnit
     */
    protected void assertArrayFixedBillingEntryReport(ReportEntryBean[] reports, int expectedLength)
        throws Exception {

        assertNotNull("Array reports should not be null.", reports);

        // check the length
        assertEquals(
                "There should be " + expectedLength + " reports returned",
                expectedLength,
                reports.length);
        FixedBillingEntryReport[] fbReporst = new FixedBillingEntryReport[expectedLength];

        for (int i = 0; i < expectedLength; i++) {
            assertNotNull("reports[" + i + "] should not be null", reports[i]);
            assertTrue(
                    "reports[" + i + "] should be object of type FixedBillingEntryReport",
                    reports[i] instanceof FixedBillingEntryReport);
            fbReporst[i] = (FixedBillingEntryReport) reports[i];
        }

        // pass the appropriate checker
        this.assertArrayFixedBillingEntryReport(fbReporst, expectedLength);
    }

    /**
     * <p>
     * Validate the ExpenseEntry object using the provide <code>id</code>. From the
     * <code>id</code> we can find out how the entry should look like in the database. Please see
     * readme.txt
     * </p>
     *
     * @param entry the ExpenseEntry object to be checked.
     * @param id expense_entry_id of the expected record in the database.
     *
     * @throws Exception to JUnit
     */
    protected void assertExpenseEntry(ExpenseEntry entry, long id) throws Exception {
        // id can be from 1 to 5 only.
        if (id < 1 || id > 5) {
            return;
        }

        assertEquals("entry.id should be " + id, id, entry.getId());
        assertEquals("entry.companyId should be " + (100 + id), (100 + id), entry.getCompanyId());
        // assertEquals("entry.invoiceId should be " + id, id, entry.getInvoiceId());
        assertEquals(
                "entry.amount should be " + (double) id,
                (double) id,
                entry.getAmount().doubleValue(),
                1e-5);

        assertEquals(
                "entry.description should be 'expenseentryDesc" + id + "'.",
                "expenseentryDesc" + id,
                entry.getDescription());
        assertEquals(
                "entry.creationUser should be 'creationUser" + id + "'.",
                "creationUser" + id,
                entry.getCreationUser());
        assertEquals("entry.modificationUser should be 'modificationUser" + id + "'.", "modificationUser"
                + id, entry.getModificationUser());

        assertDateDiff10Seconds("entry.entry_date is not correct.", new Date(FORMATTER.parse(
                "2006-12-31 00:00:00").getTime()
                - id
                * ONEDAY), entry.getDate());
        Date setupDate = this.getDBSetupDate();
        assertDateDiff10Seconds("entry.creationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), entry.getCreationDate());
        assertDateDiff10Seconds("entry.modificationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), entry.getModificationDate());

        ExpenseStatus status = entry.getStatus();
        assertNotNull("entry.ExpenseStatus should not be null", status);
        assertEquals("entry.ExpenseStatus.id should be " + id, id, status.getId());
        this.assertExpenseStatus(status, id);

        ExpenseType type = entry.getExpenseType();
        assertNotNull("entry.ExpenseType should not be null", type);
        assertEquals("entry.ExpenseTpe.id should be " + id, id, type.getId());
        this.assertExpenseType(type, id);
    }

    /**
     * <p>
     * Validate the ExpenseStatus object using the provide <code>id</code>. From the
     * <code>id</code> we can find out how the entry should look like in the database. Please see
     * readme.txt
     * </p>
     *
     * @param status the ExpenseStatus object to be checked.
     * @param id expense_status_id of the expected record in the database.
     */
    protected void assertExpenseStatus(ExpenseStatus status, long id) {
        // id can be from 1 to 5 only.
        if (id < 1 || id > 5) {
            return;
        }

        assertEquals("status.id should be " + id, id, status.getId());

        assertEquals(
                "status.description should be 'expensestatusDesc" + id + "'.",
                "expensestatusDesc" + id,
                status.getDescription());
        assertEquals(
                "status.creationUser should be 'creationUser" + id + "'.",
                "creationUser" + id,
                status.getCreationUser());
        assertEquals("status.modificationUser should be 'modificationUser" + id + "'.", "modificationUser"
                + id, status.getModificationUser());

        Date setupDate = this.getDBSetupDate();

        assertDateDiff10Seconds("status.creationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), status.getCreationDate());
        assertDateDiff10Seconds("status.modificationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), status.getModificationDate());
    }

    /**
     * <p>
     * Validate the ExpenseType object using the provide <code>id</code>. From the
     * <code>id</code> we can find out how the entry should look like in the database. Please see
     * readme.txt
     * </p>
     *
     * @param type the ExpenseType object to be checked.
     * @param id expense_type_id of the expected record in the database.
     */
    protected void assertExpenseType(ExpenseType type, long id) {
        // id can be from 1 to 5 only.
        if (id < 1 || id > 5) {
            return;
        }

        assertEquals("status.id should be " + id, id, type.getId());

        assertEquals(
                "status.description should be 'expensetypeDesc" + id + "'.",
                "expensetypeDesc" + id,
                type.getDescription());
        assertEquals(
                "status.creationUser should be 'creationUser" + id + "'.",
                "creationUser" + id,
                type.getCreationUser());
        assertEquals("status.modificationUser should be 'modificationUser" + id + "'.", "modificationUser"
                + id, type.getModificationUser());

        Date setupDate = this.getDBSetupDate();

        assertDateDiff10Seconds("status.creationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), type.getCreationDate());
        assertDateDiff10Seconds("status.modificationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), type.getModificationDate());
    }

    /**
     * <p>
     * Validate whether the ExpenseEntryReport record reflects the one in the database.
     * </p>
     *
     * @param report the ExpenseEntryReport object to be checked.
     *
     * @throws Exception to JUnit
     */
    protected void assertExpenseEntryReport(ExpenseEntryReport report) throws Exception {
        // assert all components of report should not be null
        Client client = report.getClient();
        Project project = report.getProject();
        User user = report.getUser();
        ExpenseEntry fbEntry = report.getExpenseEntry();
        ExpenseStatus fbStatus = report.getExpenseStatus();

        assertNotNull("Client object should not be null. ", client);
        assertNotNull("Project object should not be null. ", project);
        assertNotNull("User object should not be null. ", user);
        assertNotNull("ExpenseEntry object should not be null. ", fbEntry);
        assertNotNull("ExpenseStatus object should not be null. ", fbStatus);

        // get the fix_bill_entry#fix_bill_entry_id
        long id = fbEntry.getId();
        long projectId = (id - 1) / 2 + 1;
        long clientId = (projectId - 1) / 2 + 1;

        // from this id, we can find out all the expected values
        this.assertExpenseEntry(fbEntry, id);
        this.assertExpenseStatus(fbStatus, id);

        this.assertProject(project, projectId);
        this.assertClient(client, clientId);
        this.assertUser(user, id);
    }

    /**
     * <p>
     * Validate whether the ExpenseEntryReport record reflects the one in the database.
     * </p>
     *
     * @param reports the array of ExpenseEntryReport to check.
     * @param expectedLength the expected length of the array ( as calculated from the sample data).
     *        expectedLength is always >=0.
     *
     * @throws Exception to JUnit
     */
    protected void assertArrayExpenseEntryReport(ExpenseEntryReport[] reports, int expectedLength)
        throws Exception {

        assertNotNull("Array reports should not be null.", reports);

        // check the length
        assertEquals(
                "There should be " + expectedLength + " reports returned",
                expectedLength,
                reports.length);

        // validate the ExpenseEntryReport records.
        int[] checked = new int[5];
        for (int i = 0; i < expectedLength; i++) {
            assertNotNull("reports[" + i + "] should not be null", reports[i]);
            this.assertExpenseEntryReport((ExpenseEntryReport) reports[i]);
            checked[(int) reports[i].getExpenseEntry().getId() - 1]++;
        }

        // check if there was a duplicate (or missing) record
        for (int i = 0; i < expectedLength; i++) {
            if (checked[i] > 1) {
                fail("The return report array contains duplicate records.");
            }
        }
    }

    /**
     * <p>
     * Validate whether the ExpenseEntryReport record reflects the one in the database.
     * </p>
     *
     * @param reports the array of ReportEntryBean to check. All elements have to be instance of
     *        ExpenseEntryReport.
     * @param expectedLength the expected length of the array ( as calculated from the sample data).
     *        expectedLength is always >=0.
     *
     * @throws Exception to JUnit
     */
    protected void assertArrayExpenseEntryReport(ReportEntryBean[] reports, int expectedLength)
        throws Exception {

        assertNotNull("Array reports should not be null.", reports);

        // check the length
        assertEquals(
                "There should be " + expectedLength + " reports returned",
                expectedLength,
                reports.length);
        ExpenseEntryReport[] fbReporst = new ExpenseEntryReport[expectedLength];

        for (int i = 0; i < expectedLength; i++) {
            assertNotNull("reports[" + i + "] should not be null", reports[i]);
            assertTrue(
                    "reports[" + i + "] should be object of type ExpenseEntryReport",
                    reports[i] instanceof ExpenseEntryReport);
            fbReporst[i] = (ExpenseEntryReport) reports[i];
        }

        // pass the appropriate checker
        this.assertArrayExpenseEntryReport(fbReporst, expectedLength);
    }

    /**
     * <p>
     * Validate the TimeEntry object using the provide <code>id</code>. From the <code>id</code>
     * we can find out how the entry should look like in the database. Please see readme.txt
     * </p>
     *
     * @param entry the TimeEntry object to be checked.
     * @param id Time_entry_id of the expected record in the database.
     *
     * @throws Exception to JUnit
     */
    protected void assertTimeEntry(TimeEntry entry, long id) throws Exception {
        // id can be from 1 to 5 only.
        if (id < 1 || id > 5) {
            return;
        }

        assertEquals("entry.id should be " + id, id, entry.getId());
        assertEquals("entry.companyId should be " + (100 + id), (100 + id), entry.getCompanyId());
        // assertEquals("entry.invoiceId should be " + id, id, entry.getInvoiceId());
        // assertEquals("entry.amount should be " + (double) id, (double) id, entry.getAmount(),
        // 1e-5);

        assertEquals(
                "entry.description should be 'timeentryDesc" + id + "'.",
                "timeentryDesc" + id,
                entry.getDescription());
        assertEquals(
                "entry.creationUser should be 'creationUser" + id + "'.",
                "creationUser" + id,
                entry.getCreationUser());
        assertEquals("entry.modificationUser should be 'modificationUser" + id + "'.", "modificationUser"
                + id, entry.getModificationUser());

        assertDateDiff10Seconds("entry.entry_date is not correct.", new Date(FORMATTER.parse(
                "2006-12-31 00:00:00").getTime()
                - id
                * ONEDAY), entry.getDate());
        Date setupDate = this.getDBSetupDate();
        assertDateDiff10Seconds("entry.creationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), entry.getCreationDate());
        assertDateDiff10Seconds("entry.modificationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), entry.getModificationDate());

        TimeStatus status = entry.getStatus();
        assertNotNull("entry.TimeStatus should not be null", status);
        assertEquals("entry.TimeStatus.id should be " + id, id, status.getId());
        this.assertTimeStatus(status, id);

        TaskType type = entry.getTaskType();
        assertNotNull("type.TaskType should not be null", type);
        assertEquals("type.TaskType.id should be " + id, id, type.getId());
        this.assertTaskType(type, id);
    }

    /**
     * <p>
     * Validate the TimeStatus object using the provide <code>id</code>. From the <code>id</code>
     * we can find out how the entry should look like in the database. Please see readme.txt
     * </p>
     *
     * @param status the TimeStatus object to be checked.
     * @param id Time_status_id of the expected record in the database.
     */
    protected void assertTimeStatus(TimeStatus status, long id) {
        // id can be from 1 to 5 only.
        if (id < 1 || id > 5) {
            return;
        }

        assertEquals("status.id should be " + id, id, status.getId());

        assertEquals(
                "status.description should be 'timestatusDesc" + id + "'.",
                "timestatusDesc" + id,
                status.getDescription());
        assertEquals(
                "status.creationUser should be 'creationUser" + id + "'.",
                "creationUser" + id,
                status.getCreationUser());
        assertEquals("status.modificationUser should be 'modificationUser" + id + "'.", "modificationUser"
                + id, status.getModificationUser());

        Date setupDate = this.getDBSetupDate();

        assertDateDiff10Seconds("status.creationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), status.getCreationDate());
        assertDateDiff10Seconds("status.modificationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), status.getModificationDate());
    }

    /**
     * <p>
     * Validate the TaskType object using the provide <code>id</code>. From the <code>id</code>
     * we can find out how the entry should look like in the database. Please see readme.txt
     * </p>
     *
     * @param type the TaskType object to be checked.
     * @param id Time_type_id of the expected record in the database.
     */
    protected void assertTaskType(TaskType type, long id) {
        // id can be from 1 to 5 only.
        if (id < 1 || id > 5) {
            return;
        }

        assertEquals("status.id should be " + id, id, type.getId());

        assertEquals(
                "status.description should be 'tasktypeDesc" + id + "'.",
                "tasktypeDesc" + id,
                type.getDescription());
        assertEquals(
                "status.creationUser should be 'creationUser" + id + "'.",
                "creationUser" + id,
                type.getCreationUser());
        assertEquals("status.modificationUser should be 'modificationUser" + id + "'.", "modificationUser"
                + id, type.getModificationUser());

        Date setupDate = this.getDBSetupDate();

        assertDateDiff10Seconds("status.creationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), type.getCreationDate());
        assertDateDiff10Seconds("status.modificationDate is not correct.", new Date(setupDate.getTime()
                - id
                * ONEDAY), type.getModificationDate());
    }

    /**
     * <p>
     * Validate whether the TimeEntryReport record reflects the one in the database.
     * </p>
     *
     * @param report the TimeEntryReport object to be checked.
     *
     * @throws Exception to JUnit
     */
    protected void assertTimeEntryReport(TimeEntryReport report) throws Exception {
        // assert all components of report should not be null
        Client client = report.getClient();
        Project project = report.getProject();
        User user = report.getUser();
        ProjectWorker worker = report.getProjectWorker();
        TimeEntry fbEntry = report.getTimeEntry();
        TimeStatus fbStatus = report.getTimeStatus();

        assertNotNull("Client object should not be null. ", client);
        assertNotNull("Project object should not be null. ", project);
        assertNotNull("User object should not be null. ", user);
        assertNotNull("ProjectWorker object should not be null. ", worker);
        assertNotNull("TimeEntry object should not be null. ", fbEntry);
        assertNotNull("TimeStatus object should not be null. ", fbStatus);

        // get the fix_bill_entry#fix_bill_entry_id
        long id = fbEntry.getId();
        long projectId = (id - 1) / 2 + 1;
        long clientId = (projectId - 1) / 2 + 1;

        // from this id, we can find out all the expected values
        this.assertTimeEntry(fbEntry, id);
        this.assertTimeStatus(fbStatus, id);

        this.assertProject(project, projectId);
        this.assertClient(client, clientId);
        this.assertUser(user, id);
        this.assertProjectWorker(worker, id);
    }

    /**
     * <p>
     * Validate whether the TimeEntryReport record reflects the one in the database.
     * </p>
     *
     * @param reports the array of TimeEntryReport to check.
     * @param expectedLength the expected length of the array ( as calculated from the sample data).
     *        expectedLength is always >=0.
     *
     * @throws Exception to JUnit
     */
    protected void assertArrayTimeEntryReport(TimeEntryReport[] reports, int expectedLength) throws Exception {

        assertNotNull("Array reports should not be null.", reports);

        // check the length
        assertEquals(
                "There should be " + expectedLength + " reports returned",
                expectedLength,
                reports.length);

        // validate the TimeEntryReport records.
        int[] checked = new int[5];
        for (int i = 0; i < expectedLength; i++) {
            assertNotNull("reports[" + i + "] should not be null", reports[i]);
            this.assertTimeEntryReport((TimeEntryReport) reports[i]);
            checked[(int) reports[i].getTimeEntry().getId() - 1]++;
        }

        // check if there was a duplicate (or missing) record
        for (int i = 0; i < expectedLength; i++) {
            if (checked[i] > 1) {
                fail("The return report array contains duplicate records.");
            }
        }
    }

    /**
     * <p>
     * Validate whether the TimeEntryReport record reflects the one in the database.
     * </p>
     *
     * @param reports the array of ReportEntryBean to check. All elements have to be instance of
     *        TimeEntryReport.
     * @param expectedLength the expected length of the array ( as calculated from the sample data).
     *        expectedLength is always >=0.
     *
     * @throws Exception to JUnit
     */
    protected void assertArrayTimeEntryReport(ReportEntryBean[] reports, int expectedLength) throws Exception {

        assertNotNull("Array reports should not be null.", reports);

        // check the length
        assertEquals(
                "There should be " + expectedLength + " reports returned",
                expectedLength,
                reports.length);
        TimeEntryReport[] fbReporst = new TimeEntryReport[expectedLength];

        for (int i = 0; i < expectedLength; i++) {
            assertNotNull("reports[" + i + "] should not be null", reports[i]);
            assertTrue(
                    "reports[" + i + "] should be object of type TimeEntryReport",
                    reports[i] instanceof TimeEntryReport);
            fbReporst[i] = (TimeEntryReport) reports[i];
        }

        // pass the appropriate checker
        this.assertArrayTimeEntryReport(fbReporst, expectedLength);
    }
}
