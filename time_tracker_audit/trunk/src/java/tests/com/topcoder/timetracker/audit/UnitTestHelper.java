/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Iterator;


/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class UnitTestHelper {
    /**
     * <p>
     * Creates a new instance of <code>UnitTestHelper</code> class. The private constructor prevents the creation of a
     * new instance.
     * </p>
     */
    private UnitTestHelper() {
    }

    /**
     * <p>
     * Add the valid config files for testing.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    public static void addConfig() throws Exception {
        clearConfig();

        ConfigManager configManager = ConfigManager.getInstance();

        configManager.add("DBConnectionFactory_Config.xml");
        configManager.add("InformixAuditPersistence_Config.xml");
        configManager.add("SearchBuilder_Config.xml");
        configManager.add("Logging_Wrapper.xml");
        configManager.add("AuditDelegate_Config.xml");
        configManager.add("AuditSessionBean_Config.xml");
        configManager.add("DefaultValuesContainer_Config.xml");
        configManager.add("com.topcoder.naming.jndiutility", "JNDIUtils.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);
    }

    /**
     * <p>
     * clear the config.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    public static void clearConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Gets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance.
     * </p>
     *
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be retrieved.
     *
     * @return the value of the private field or <code>null</code> if any error occurs.
     */
    public static Object getPrivateField(Class type, Object instance, String name) {
        Field field = null;
        Object obj = null;

        try {
            // Get the reflection of the field and get the value
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            obj = field.get(instance);
        } catch (IllegalAccessException e) {
            // ignore
        } catch (NoSuchFieldException e) {
            // ignore
        } finally {
            if (field != null) {
                // Reset the accessibility
                field.setAccessible(false);
            }
        }

        return obj;
    }

    /**
     * Sets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance.
     *
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be setted.
     * @param value the value be given to the field.
     */
    public static void setPrivateField(Class type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // Get the reflection of the field and get the value
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            // ignore
        } catch (NoSuchFieldException e) {
            // ignore
        } finally {
            if (field != null) {
                // Reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Get the Connection from DBConnectionFactory.
     * </p>
     *
     * @return the connection created from DBConnectionFactory.
     *
     * @throws Exception any exception during the create connection process.
     */
    public static Connection getConnection() throws Exception {
        // get the Connection instance
        DBConnectionFactory factory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        return factory.createConnection();
    }

    /**
     * <p>
     * Prepare data for test.
     * </p>
     *
     * @param connection database connection.
     *
     * @throws SQLException error occurs when access the database.
     */
    public static void prepareData(Connection connection) throws SQLException {
        clearDatabase(connection);
        executeScript(connection, "sql/prepare.sql");
    }

    /**
     * <p>
     * Executes the sql script from the given file.
     * </p>
     *
     * @param connection database connection.
     * @param fileName the file name.
     *
     * @throws SQLException error occurs when access the database.
     */
    private static void executeScript(Connection connection, String fileName) throws SQLException {
        Statement statement = connection.createStatement();

        try {
            InputStream input = UnitTestHelper.class.getClassLoader().getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if ((line.length() != 0) && !line.startsWith("--")) {
                    statement.executeUpdate(line);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            statement.close();
        }
    }

    /**
     * <p>
     * Clears the content in database.
     * </p>
     *
     * @param connection the database connection used to access database.
     *
     * @throws SQLException error occurs when access the database.
     */
    public static void clearDatabase(Connection connection) throws SQLException {
        executeScript(connection, "sql/clear.sql");
    }

    /**
     * Get the number of records in audit table.
     *
     * @param connection the connection to the database
     *
     * @return the number of records in audit table.
     *
     * @throws Exception error occurs when access the database.
     */
    public static int getAuditRecords(Connection connection) throws Exception {
        return getTableRecords(connection, "audit");
    }

    /**
     * Get the number of records in audit_detail table.
     *
     * @param connection the connection to the database
     *
     * @return the number of records in audit_detail table.
     *
     * @throws Exception error occurs when access the database.
     */
    public static int getAuditDetailRecords(Connection connection) throws Exception {
        return getTableRecords(connection, "audit_detail");
    }

    /**
     * Get the number of records in the special table.
     *
     * @param connection the connection to the database
     * @param tableName table name
     *
     * @return the record number in the special table.
     *
     * @throws Exception error occurs when access the database.
     */
    private static int getTableRecords(Connection connection, String tableName) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet rs = null;

        try {
            rs = statement.executeQuery("select count(*) FROM " + tableName);
            rs.next();

            return rs.getInt(1);
        } finally {
            rs.close();
            statement.close();
        }
    }

    /**
     * <p>
     * Asserts the two given AuditHeader instances to be equals.
     * </p>
     *
     * @param expected the expected AuditHeader instance.
     * @param actual the actual AuditHeader instance.
     */
    public static void assertEquals(AuditHeader expected, AuditHeader actual) {
        Assert.assertEquals("The id should be correct.", expected.getId(), actual.getId());
        Assert.assertEquals("The entityId should be correct.", expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals("The creationDate should be correct.",
            expected.getCreationDate(), actual.getCreationDate());
        Assert.assertEquals("The tableName should be correct.", expected.getTableName(), actual.getTableName());
        Assert.assertEquals("The companyId should be correct.", expected.getCompanyId(), actual.getCompanyId());
        Assert.assertEquals("The creationUser should be correct.",
            expected.getCreationUser(), actual.getCreationUser());
        Assert.assertEquals("The actionType should be correct.", expected.getActionType(), actual.getActionType());
        Assert.assertEquals("The clientId should be correct.", expected.getClientId(), actual.getClientId());
        Assert.assertEquals("The projectId should be correct.", expected.getProjectId(), actual.getProjectId());
        Assert.assertEquals("The resourceId should be correct.", expected.getResourceId(), actual.getResourceId());
        Assert.assertEquals("The clientName should be correct.", expected.getClientName(), actual.getClientName());
        Assert.assertEquals("The projectName should be correct.", expected.getProjectName(), actual.getProjectName());
        Assert.assertEquals("The applicationArea should be correct.", expected.getApplicationArea(),
            actual.getApplicationArea());
        Assert.assertEquals("The persisted should be correct.", expected.isPersisted(), actual.isPersisted());

        Assert.assertEquals("The details' count should be correct.", expected.getDetails().length,
            actual.getDetails().length);

        for (int i = 0; i < expected.getDetails().length; i++) {
            assertEquals(expected.getDetails()[i], actual.getDetails()[i]);
        }
    }

    /**
     * <p>
     * Asserts the two given AuditDetail instances to be equals.
     * </p>
     *
     * @param expected the expected AuditDetail instance.
     * @param actual the actual AuditDetail instance.
     */
    public static void assertEquals(AuditDetail expected, AuditDetail actual) {
        Assert.assertEquals("The id should be correct.", expected.getId(), actual.getId());
        Assert.assertEquals("The columnName should be correct.", expected.getColumnName(), actual.getColumnName());
        Assert.assertEquals("The newValue should be correct.", expected.getNewValue(), actual.getNewValue());
        Assert.assertEquals("The oldValue should be correct.", expected.getOldValue(), actual.getOldValue());
        Assert.assertEquals("The persisted should be correct.", expected.isPersisted(), actual.isPersisted());
    }

    /**
     * Build an AuditHeader for testing.
     *
     * @param detailNum the number of the details hold by this audit.
     *
     * @return an AuditHeader instance.
     */
    public static AuditHeader buildAuditHeader(int detailNum) {
        AuditHeader header = new AuditHeader();
        header.setActionType(1);
        header.setApplicationArea(ApplicationArea.TT_COMPANY);
        header.setClientId(2);
        header.setClientName("client2");
        header.setCompanyId(3);
        header.setCreationDate(new Timestamp((detailNum + 1) * 1000));
        header.setCreationUser("createUser");

        header.setDetails(buildAuditDetails(detailNum));
        header.setEntityId(4);
        header.setProjectId(5);
        header.setProjectName("project5");
        header.setResourceId(6);
        header.setTableName("tableName");

        return header;
    }

    /**
     * Build an array of AuditDetail with special length.
     *
     * @param detailNum the number of the AuditDetail to build.
     *
     * @return array of AuditDetail instance, empty array when detailNum is zero.
     */
    public static AuditDetail[] buildAuditDetails(int detailNum) {
        AuditDetail[] details = new AuditDetail[detailNum];

        for (int i = 0; i < details.length; i++) {
            details[i] = new AuditDetail();
            details[i].setColumnName("columnName" + i);
            details[i].setNewValue("newValue" + i);
            details[i].setOldValue("oldValue" + i);
        }

        return details;
    }
}
