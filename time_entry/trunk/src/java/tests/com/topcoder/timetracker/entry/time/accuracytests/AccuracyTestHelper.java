/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import junit.framework.Assert;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.ejb.AuditLocalHome;
import com.topcoder.timetracker.audit.ejb.AuditLocalObject;
import com.topcoder.timetracker.audit.ejb.AuditSessionBean;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.ejb.TaskTypeManagerLocal;
import com.topcoder.timetracker.entry.time.ejb.TaskTypeManagerLocalHome;
import com.topcoder.timetracker.entry.time.ejb.TaskTypeManagerSessionBean;
import com.topcoder.timetracker.entry.time.ejb.TimeEntryManagerLocal;
import com.topcoder.timetracker.entry.time.ejb.TimeEntryManagerLocalHome;
import com.topcoder.timetracker.entry.time.ejb.TimeEntryManagerSessionBean;
import com.topcoder.timetracker.entry.time.ejb.TimeStatusManagerLocal;
import com.topcoder.timetracker.entry.time.ejb.TimeStatusManagerLocalHome;
import com.topcoder.timetracker.entry.time.ejb.TimeStatusManagerSessionBean;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * A helper class to perform those common operations which are helpful for the test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AccuracyTestHelper {
    /**
     * <p>
     * Represents the namespace for DB Connection Factory component.
     * </p>
     */
    public static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * The sample configuration file for this component.
     * </p>
     */
    public static final String CONFIG_FILE = "test_files" + File.separator + "accuracytests" + File.separator
        + "config.xml";

    /**
     * <p>
     * The audit configuration file for audit component.
     * </p>
     */
    public static final String AUDIT_CONFIG_FILE = "test_files" + File.separator + "accuracytests" + File.separator
        + "audit_manager_config.xml";

    /**
     * <p>
     * The constant represents the namespace for search builder component.
     * </p>
     */
    public static final String SEARCH_NAMESPACE = "com.topcoder.search.builder.database.DatabaseSearchStrategy";

    /**
     * <p>
     * The constant represents the namespace for audit component.
     * </p>
     */
    public static final String AUDIT_NAMESPACE = "com.topcoder.timetracker.audit.ejb.AuditDelegate";

    /**
     * <p>
     * The constant represents the connection name for this component.
     * </p>
     */
    public static final String CONNECTION_NAME = "tt_time_entry";

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private AccuracyTestHelper() {
    }

    /**
     * <p>
     * Use the given file to config the given namespace the format of the config file is
     * ConfigManager.CONFIG_XML_FORMAT.
     * </p>
     *
     * @param namespace use the namespace to load config information to ConfigManager
     * @param fileName config file to set up environment
     *
     * @throws Exception when any exception occurs
     */
    public static void loadSingleXMLConfig(String namespace, String fileName) throws Exception {
        //set up environment
        ConfigManager config = ConfigManager.getInstance();
        File file = new File(fileName);

        //config namespace
        if (config.existsNamespace(namespace)) {
            config.removeNamespace(namespace);
        }

        config.add(namespace, file.getCanonicalPath(), ConfigManager.CONFIG_XML_FORMAT);
    }

    /**
     * <p>
     * Remove the given namespace in the ConfigManager.
     * </p>
     *
     * @param namespace namespace use to remove the config information in ConfigManager
     *
     * @throws Exception when any exception occurs
     */
    public static void clearConfigFile(String namespace) throws Exception {
        ConfigManager config = ConfigManager.getInstance();

        //clear the environment
        if (config.existsNamespace(namespace)) {
            config.removeNamespace(namespace);
        }
    }

    /**
     * <p>
     * Uses the given file to config the configuration manager.
     * </p>
     *
     * @param fileName config file to set up environment
     *
     * @throws Exception when any exception occurs
     */
    public static void loadXMLConfig(String fileName) throws Exception {
        //set up environment
        ConfigManager config = ConfigManager.getInstance();
        File file = new File(fileName);

        config.add(file.getCanonicalPath());
    }

    /**
     * <p>
     * Clears all the namespaces from the configuration manager.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }

    /**
     * <p>
     * This method sets up the EJB environment for testing.
     * </p>
     *
     * @param taskTypeBean the task type bean to be set up
     * @param timeEntryBean the time entry bean to be set up
     * @param timeStatusBean the time status bean to be set up
     *
     * @throws Exception to JUnit.
     */
    public static void setUpEJBEnvironment(TaskTypeManagerSessionBean taskTypeBean,
        TimeEntryManagerSessionBean timeEntryBean, TimeStatusManagerSessionBean timeStatusBean) throws Exception {
        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // we use MockTransaction outside of the app server
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("java:comp/env/ejb/audit/AuditEJB",
            AuditLocalHome.class, AuditLocalObject.class, new AuditSessionBean());

        mockContainer.deploy(sampleServiceDescriptor);

        if (taskTypeBean != null) {
            //  for task type bean
            sampleServiceDescriptor = new SessionBeanDescriptor("taskTypeDelegateLocalHome",
                TaskTypeManagerLocalHome.class, TaskTypeManagerLocal.class, taskTypeBean);
            mockContainer.deploy(sampleServiceDescriptor);
        }

        if (timeEntryBean != null) {
            // for time entry bean
            sampleServiceDescriptor = new SessionBeanDescriptor("timeEntryDelegateLocalHome",
                TimeEntryManagerLocalHome.class, TimeEntryManagerLocal.class, timeEntryBean);
            mockContainer.deploy(sampleServiceDescriptor);
        }

        if (timeStatusBean != null) {
            // for time status bean
            sampleServiceDescriptor = new SessionBeanDescriptor("timeStatusDelegateLocalHome",
                TimeStatusManagerLocalHome.class, TimeStatusManagerLocal.class, timeStatusBean);
            mockContainer.deploy(sampleServiceDescriptor);
        }
    }

    /**
     * <p>
     * Verifies whether the given two time entries are equals or not.
     * </p>
     *
     * @param expectedTimeEntry the expected time entry
     * @param actualTimeEntry the actual time entry
     */
    public static void assertTimeEntryEquals(TimeEntry expectedTimeEntry, TimeEntry actualTimeEntry) {
        Assert.assertEquals("The hours are not equals.", expectedTimeEntry.getHours(), actualTimeEntry.getHours(), 0.01);
        Assert.assertEquals("The task types are not equals.", expectedTimeEntry.getTaskType().getId(),
            actualTimeEntry.getTaskType().getId());
        Assert.assertEquals("The time statuses are not equals.", expectedTimeEntry.getStatus().getId(),
            actualTimeEntry.getStatus().getId());
        Assert.assertEquals("The billable states are not equals.", expectedTimeEntry.getBillable(),
            actualTimeEntry.getBillable());
        Assert.assertEquals("The invoice ids are not equals.", expectedTimeEntry.getInvoiceId(),
            actualTimeEntry.getInvoiceId());
        Assert.assertEquals("The company ids are not equals.", expectedTimeEntry.getCompanyId(),
            actualTimeEntry.getCompanyId());
        Assert.assertEquals("The descriptions are not equals.", expectedTimeEntry.getDescription(),
            actualTimeEntry.getDescription());
        Assert.assertEquals("The creation users are not equals.", expectedTimeEntry.getCreationUser(),
            actualTimeEntry.getCreationUser());
        Assert.assertEquals("The modification users are not equals.", expectedTimeEntry.getModificationUser(),
            actualTimeEntry.getModificationUser());
    }

    /**
     * <p>
     * Verifies whether the given two time entries are equals or not.
     * </p>
     *
     * @param expectedTimeStatus the expected time entry
     * @param actualTimeStatus the actual time entry
     */
    public static void assertTimeStatusEquals(TimeStatus expectedTimeStatus, TimeStatus actualTimeStatus) {
        Assert.assertEquals("The names are not equals.", expectedTimeStatus.getDescription(),
            actualTimeStatus.getDescription());

        Assert.assertEquals("The creation users are not equals.", expectedTimeStatus.getCreationUser(),
            actualTimeStatus.getCreationUser());
        Assert.assertEquals("The modification users are not equals.", expectedTimeStatus.getModificationUser(),
            actualTimeStatus.getModificationUser());
    }

    /**
     * <p>
     * Verifies whether the given two time entries are equals or not.
     * </p>
     *
     * @param expectedTaskType the expected time entry
     * @param actualTaskType the actual time entry
     */
    public static void assertTaskTypeEquals(TaskType expectedTaskType, TaskType actualTaskType) {
        Assert.assertEquals("The active states are not equals.", expectedTaskType.getActive(),
            actualTaskType.getActive());
        Assert.assertEquals("The company ids are not equals.", expectedTaskType.getCompanyId(),
            actualTaskType.getCompanyId());
        Assert.assertEquals("The descriptions are not equals.", expectedTaskType.getDescription(),
            actualTaskType.getDescription());
        Assert.assertEquals("The creation users are not equals.", expectedTaskType.getCreationUser(),
            actualTaskType.getCreationUser());
        Assert.assertEquals("The modification users are not equals.", expectedTaskType.getModificationUser(),
            actualTaskType.getModificationUser());
    }

    /**
     * <p>
     * This method creates a time entry instance for testing purpose.
     * </p>
     *
     * @param missingField the field name to missing
     *
     * @return a time entry instance for testing purpose.
     */
    public static TimeEntry createTimeEntry(String missingField) {
        TimeEntry timeEntry = new TimeEntry();

        timeEntry.setInvoiceId(1);
        timeEntry.setCompanyId(1);
        timeEntry.setClientId(1);
        timeEntry.setProjectId(1);
        timeEntry.setHours(13.2);
        timeEntry.setBillable(true);

        if (!"TaskType".equals(missingField)) {
            timeEntry.setTaskType(createTaskType(null));
        }

        if (!"Date".equals(missingField)) {
            timeEntry.setDate(new Date());
        }

        if (!"TimeStatus".equals(missingField)) {
            timeEntry.setStatus(createTimeStatus(null));
        }

        if (!"CreationDate".equals(missingField)) {
            timeEntry.setCreationDate(new Date());
        }

        if (!"CreationUser".equals(missingField)) {
            timeEntry.setCreationUser("tc");
        }

        if (!"ModificationDate".equals(missingField)) {
            timeEntry.setModificationDate(new Date());
        }

        if (!"ModificationUser".equals(missingField)) {
            timeEntry.setModificationUser("tc");
        }

        return timeEntry;
    }

    /**
     * <p>
     * This method creates a task type instance for testing purpose.
     * </p>
     *
     * @param missingField the field name to missing
     *
     * @return a task type instance for testing purpose.
     */
    public static TaskType createTaskType(String missingField) {
        TaskType taskType = new TaskType();

        taskType.setCompanyId(1);
        taskType.setActive(true);

        if (!"CreationDate".equals(missingField)) {
            taskType.setCreationDate(new Date());
        }
        if (!"CreationUser".equals(missingField)) {
            taskType.setCreationUser("tc");
        }
        if (!"ModificationDate".equals(missingField)) {
            taskType.setModificationDate(new Date());
        }
        if (!"ModificationUser".equals(missingField)) {
            taskType.setModificationUser("tc");
        }

        return taskType;
    }

    /**
     * <p>
     * This method creates a project worker instance for testing purpose.
     * </p>
     *
     * @param missingField the field name to missing
     *
     * @return a project worker instance for testing purpose.
     */
    public static TimeStatus createTimeStatus(String missingField) {
        TimeStatus timeStatus = new TimeStatus();

        if (!"CreationDate".equals(missingField)) {
            timeStatus.setCreationDate(new Date());
        }

        if (!"CreationUser".equals(missingField)) {
            timeStatus.setCreationUser("tc");
        }

        if (!"ModificationDate".equals(missingField)) {
            timeStatus.setModificationDate(new Date());
        }

        if (!"ModificationUser".equals(missingField)) {
            timeStatus.setModificationUser("tc");
        }

        return timeStatus;
    }

    /**
     * <p>
     * Inserts some data to the tables which this component depends on.
     * </p>
     *
     * <p>
     * This is used to simplify the testing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public static void setUpDataBase() throws Exception {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            executeSqlFile(connection, "test_files" + File.separator + "accuracytests" + File.separator
                + "clear_data.sql");
            executeSqlFile(connection, "test_files" + File.separator + "accuracytests" + File.separator
                + "add_data.sql");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Clears all the data from the tables using by this component.
     * </p>
     *
     * <p>
     * This is used to simplify the testing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public static void tearDownDataBase() throws Exception {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            executeSqlFile(connection, "test_files" + File.separator + "accuracytests" + File.separator
                + "clear_data.sql");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Executes the sql scripts in the given sql file.
     * </p>
     *
     * @param connection Connection instance to access the database
     * @param sqlPath the path of the sql file to execute
     *
     * @throws SQLException if exception occurs during database operation
     * @throws IOException if fails to read the sql file
     */
    private static void executeSqlFile(Connection connection, String sqlPath) throws SQLException, IOException {
        String[] sqlStatements = loadSqlFile(sqlPath);

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            for (int i = 0; i < sqlStatements.length; i++) {
                if (sqlStatements[i].trim().length() != 0) {
                    stmt.executeUpdate(sqlStatements[i]);
                }
            }
        } finally {
            closeStatement(stmt);
        }
    }

    /**
     * <p>
     * This method return the sql scripts from the given sql file.
     * </p>
     *
     * @param path the path of the sql file
     * @return the sql scripts
     *
     * @throws IOException if fails to read the sql file
     */
    private static String[] loadSqlFile(String path) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        try {
            String line = reader.readLine();
            while (line != null) {
                line = line.trim();
                if (line.length() != 0 && !line.startsWith("--")) {
                    sb.append(line);
                }

                line = reader.readLine();
            }

            return sb.toString().split(";");
        } finally {
            reader.close();
        }
    }

    /**
     * <p>
     * Returns a new connection to be used for persistence.
     * </p>
     *
     * @return the connection instance for database operation
     *
     * @throws ConfigurationException to JUnit
     * @throws DBConnectionException to JUnit
     */
    static Connection getConnection() throws DBConnectionException, ConfigurationException {
        return new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE).createConnection();
    }

    /**
     * <p>
     * Closes the given Connection instance.
     * </p>
     *
     * @param con the given Connection instance to close.
     */
    static void closeConnection(Connection con) {
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
     * Closes the given PreparedStatement instance.
     * </p>
     *
     * @param state the given Statement instance to close.
     */
    static void closeStatement(Statement state) {
        try {
            if (state != null) {
                state.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }
}
