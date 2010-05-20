/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl.componentmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.excel.Row;
import com.topcoder.excel.Sheet;
import com.topcoder.excel.Workbook;
import com.topcoder.excel.impl.ExcelFileFormatException;
import com.topcoder.excel.impl.ExcelWorkbook;
import com.topcoder.management.contest.coo.Component;
import com.topcoder.management.contest.coo.ComponentManager;
import com.topcoder.management.contest.coo.ComponentManagerException;
import com.topcoder.management.contest.coo.Helper;
import com.topcoder.management.contest.coo.impl.BaseDBConnector;
import com.topcoder.management.contest.coo.impl.ConfigurationException;

/**
 * <p>
 * This class represents <code>DBComponentManager</code>. It implements
 * <code>ComponentManager</code> interface. It stores and retrieves the third
 * party list data from the database.
 * </p>
 * <p>
 * It uses Excel Utility component to read Excel file in
 * <code>addComponents</code> method. This class extends from
 * <code>BaseDBConnector</code> to provide database related functionalities.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * //create DBComponentManager from configuration
 * DBComponentManager manager = new DBComponentManager(configuration);
 *
 * //retrieve component with name and version from database.
 * Component component = manager.retrieveComponent(&quot;COO generation&quot;, &quot;1.0.0&quot;);
 *
 * //add component from Excel file to database.
 * manager.addComponents(&quot;third_part_component.xls&quot;);
 * </pre>
 *
 * </p>
 * <p>
 * <strong> Thread safety:</strong> This class is thread safe since it is
 * immutable. The logging used is thread safe.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public class DBComponentManager extends BaseDBConnector implements
        ComponentManager {

    /** SQL query sentence to retrieve component from database. */
    private static final String COMPONENT_QUERY = "SELECT url,license,usage_comments,description, version "
            + "FROM third_party_library WHERE LOWER(name)=LOWER(?) AND version=?";

    /** SQL query sentence to retrieve component from database. */
    private static final String COMPONENT_BY_NAME_QUERY = "SELECT url,license,usage_comments,description, version, "
            + "(select count(*) from third_party_library WHERE LOWER(name)=LOWER(?)) as count "
            + "FROM third_party_library WHERE LOWER(name)=LOWER(?)";

    /** SQL update sentence to insert component to database. */
    private static final String UPDATE_SQL = "INSERT INTO third_party_library "
            + "(name,version,url,license,usage_comments,path,alias,notes,category) VALUES (?,?,?,?,?,?,?,?,?)";

    /**
     * <p>
     * Constructor.
     * </p>
     *
     * @param configuration The configuration object. must not be null.
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ConfigurationException if any error in configuration/missing
     *             value/etc
     *
     */
    public DBComponentManager(ConfigurationObject configuration)
        throws ConfigurationException {
        // delegate check to super class
        super(configuration);
    }

    /**
     * <p>
     * Retrieve the component information from the given component name and
     * version.
     * </p>
     *
     * @param name The component name. must not be null/empty.
     * @param version The component version. must not be null/empty.
     * @return The corresponding component instance. It will be fully populated
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ComponentManagerException if there is error in performing this
     *             method
     *
     */
    public Component retrieveComponent(String name, String version)
        throws ComponentManagerException {
        // signature for logging.
        final String signature = "DBComponentManager#retrieveComponent";
        Helper.logEnter(getLogger(), signature);
        Helper.checkString(getLogger(), "name", name);
        //Helper.checkString(getLogger(), "version", version);
        // retrieve component information from database.
        Connection connection = null;
        PreparedStatement select = null;
        ResultSet result = null;
        try {
            connection = getDbConnectionFactory().createConnection(getConnectionName());
            select = connection.prepareStatement(COMPONENT_QUERY);

            select.setString(1, name);
            select.setString(2, version);
            result = select.executeQuery();
            Component component = new Component();
            if (!result.next()) {
                select = connection.prepareStatement(COMPONENT_BY_NAME_QUERY);
                select.setString(1, name);
                select.setString(2, name);
                result = select.executeQuery();
                if (!result.next() || result.getInt(6) != 1) {
                    return null;
                }
            }

            // get component from database.
            component.setUrl(result.getString(1));
            component.setLicense(result.getString(2));
            component.setDescription(result.getString(3));
            component.setFullName(result.getString(4));
            component.setVersion(result.getString(5));

            if (component.getFullName() == null || component.getFullName().trim().length() == 0) {
                component.setFullName(name);
            }

            // set the properties
            component.setName(name);
            //component.setVersion(version);

            // log component information retrieved from database.
            Helper.logInfo(getLogger(), MessageFormat.format("retrieve Component from DB: name[{0}],version[{1}],"
                    + " url[{2}], license[{3}],description[{4}].", name, version, component.getUrl(),
                    component.getLicense(), component.getDescription()));

            return component;
        } catch (SQLException e) {
            throw Helper.logError(getLogger(), new ComponentManagerException("fail to execute SQL sentence.", e));
        } catch (DBConnectionException e) {
            throw Helper.logError(getLogger(), new ComponentManagerException("fail to connection database.", e));
        } finally {
            Helper.releaseDBResource(connection, select, result);
            Helper.logExit(getLogger(), signature);
        }
    }

    /**
     * Add the components listed in the given input stream to the list of
     * available components. For this class, the input file must represent an
     * Excel File.
     *
     * @param filename The input filename representing the list of components to
     *            be added. must not be null/empty
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ComponentManagerException if there is error in performing this
     *             method
     *
     */
    public void addComponents(String filename) throws ComponentManagerException {
        // signature for logging.
        final String signature = "DBComponentManager#addComponents";
        Helper.logEnter(getLogger(), signature);
        Helper.checkString(getLogger(), "filename", filename);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // open the work book
            Workbook book = new ExcelWorkbook(new File(filename));

            connection = this.getDbConnectionFactory().createConnection(getConnectionName());
            connection.setAutoCommit(false);

            // process each sheet
            Sheet[] sheets = book.getSheets();
            for (int i = 0; i < sheets.length; i++) {
                Sheet sheet = sheets[i];
                String category = sheet.getName();
                // process row
                for (int j = 2; j <= sheet.getLastRow(); j++) {
                    Row row = sheet.getRow(j);
                    executeUpdateForEachRow(row, connection, statement, category);
                }
            }
            // commit update
            connection.commit();
        } catch (FileNotFoundException e) {
            rollBack(connection);
            throw Helper.logError(getLogger(), new ComponentManagerException("fail to find file["
                    + filename + "].", e));
        } catch (ExcelFileFormatException e) {
            rollBack(connection);
            throw Helper.logError(getLogger(), new ComponentManagerException("invalid excel file format.", e));
        } catch (DBConnectionException e) {
            rollBack(connection);
            throw Helper.logError(getLogger(), new ComponentManagerException("fail to connection database.", e));
        } catch (SQLException e) {
            rollBack(connection);
            throw Helper.logError(getLogger(), new ComponentManagerException("fail to execute SQL sentence.", e));
        } finally {
            Helper.releaseDBResource(connection, statement, null);
            Helper.logExit(getLogger(), signature);
        }
    }

    /**
     * <p>
     * execute update for each row in excel sheet.
     * </p>
     *
     * @param row in excel work sheet.
     * @param connection the database connection
     * @param statement prepared statement.
     * @param category component belong to.
     * @throws SQLException if fail to update,delegate dealing to caller method.
     */

    private void executeUpdateForEachRow(Row row, Connection connection,
            PreparedStatement statement, String category) throws SQLException {
        String name = row.getCell(1).getStringValue();
        String version = row.getCell(2).getStringValue();
        String url = row.getCell(3).getStringValue();
        String license = row.getCell(4).getStringValue();
        String usageComments = row.getCell(5).getStringValue();
        String path = row.getCell(6).getStringValue();
        String alias = row.getCell(7).getStringValue();
        String notes = "";
        if (row.getLastColumn() >= 8) {
            notes = row.getCell(8).getStringValue();
        }
        // log component information retrieved from excel file.
        Helper.logInfo(getLogger(), MessageFormat.format("add Component from excel:"
                + " name[{0}],version[{1}], url[{2}], license[{3}],usage_comments[{4}],"
                + " path[{5}],alias[{6}],notes[{7}],category[{8}].", name, version, url,
                license, usageComments, path, alias, notes, category));
        statement = connection.prepareStatement(UPDATE_SQL);
        // set value
        statement.setString(1, name);
        statement.setString(2, version);
        statement.setString(3, url);
        statement.setString(4, license);
        statement.setString(5, usageComments);
        statement.setString(6, path);
        statement.setString(7, alias);
        statement.setString(8, notes);
        statement.setString(9, category);
        statement.executeUpdate();
    }

    /**
     * roll back if any error occur when updating.
     *
     * @param conn database connection.
     */
    private void rollBack(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            // ignore
        }
    }
}
