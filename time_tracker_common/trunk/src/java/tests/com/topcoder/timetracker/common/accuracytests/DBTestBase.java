/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common.accuracytests;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.util.config.ConfigManager;

/**
 * DBTestBase is a helper class for Database unit tests.
 * @author kinfkong
 * @version 3.1
 */
public class DBTestBase extends TestCase {

    /**
     * Represents the configuration file of DB Connection factory.
     */
    private static final String DBCONNECTIONCONFIG = "DBConnectionFactory.xml";

    /**
     * Represents the configuration file for the component.
     */
    private static final String CONFIG = "config.xml";

    /**
     * Represents the root path of the configuration files.
     */
    private static final String ROOTPATH = "test_files" + File.separator + "AccuracyTests" + File.separator;

    /**
     * Sets up the test environment.
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        clearNamespaces();
        // load the config files
        loadConfigFile(CONFIG);
        ConfigManager.getInstance().add(DBCONNECTIONCONFIG);
        
        removeTables();

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        statement.execute("insert into id_sequences (name, next_block_start, block_size, exhausted) values ('ids', 1, 1, 0)");
        conn.close();
        
        
    }

    /**
     * Clears the test environment.
     * @throws Exception
     *             to JUnit
     */
    public void tearDown() throws Exception {
        removeTables();
        clearNamespaces();
    }

    /**
     * <p>
     * Clear namespaces.
     * </p>
     * @throws Exception
     */
    private void clearNamespaces() throws Exception {
        // clear all the namespaces
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String namespace = (String) it.next();
            if (cm.existsNamespace(namespace)) {
                cm.removeNamespace(namespace);
            }
        }
    }

    /**
     * Removes all the tables in the database.
     * @throws Exception
     *             to JUnit
     */
    private void removeTables() throws Exception {
        String[] tables = new String[] {"payment_terms", "id_sequences"};

        Connection conn = getConnection();
        String sql = "DELETE FROM ";
        Statement statement = conn.createStatement();
        for (int i = 0; i < tables.length; i++) {
            statement.execute(sql + tables[i]);
        }
        
        conn.close();
    }

    /**
     * Loads the configuration files to ConfigManager;
     * @param fileName
     *            the file to load
     * @throws Exception
     *             to JUnit
     */
    private void loadConfigFile(String fileName) throws Exception {
        File file = new File(ROOTPATH, fileName);
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(file.getCanonicalPath());
    }

    /**
     * Checks if the payment exists in the database
     * @param paymentTerm
     *            the payment term
     * @return true if exists, otherwise false
     */
    protected boolean existsRecord(PaymentTerm paymentTerm) throws Exception {
        // get the fields
        long id = paymentTerm.getId();
        String description = paymentTerm.getDescription();
        // Date creationDate = paymentTerm.getCreationDate();
        // Date modificationDate = paymentTerm.getModificationDate();
        String creationUser = paymentTerm.getCreationUser();
        String modificationUser = paymentTerm.getModificationUser();
        int term = paymentTerm.getTerm();
        boolean active = paymentTerm.isActive();

        // construct the SQL
        String sql = "SELECT * FROM payment_terms WHERE" + " payment_terms_id = ? AND description = ? AND "
            + " creation_user = ?  " + " AND modification_user = ? AND term = ? AND active = ?";

        // get the connection
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        // set the fields
        ps.setLong(1, id);
        ps.setObject(2, description);
        ps.setString(3, creationUser);
        ps.setString(4, modificationUser);
        ps.setInt(5, term);
        ps.setBoolean(6, active);

        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    /**
     * Gets a connection to database.
     * @return the connection
     * @throws Exception
     *             to JUnit
     */
    protected Connection getConnection() throws Exception {
        return new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
    }

    /**
     * Checks if the two payment iterms are equal.
     * @param term1
     *            the first term
     * @param term2
     *            the second term
     * @return true if they are equal, false otherwise
     */
    protected boolean equalPayments(PaymentTerm term1, PaymentTerm term2) {
        if (term1.getId() != term2.getId()) {
            return false;
        }

        if (!equalDates(term1.getCreationDate(), term2.getCreationDate())) {
            return false;
        }

        if (!equalDates(term1.getModificationDate(), term2.getModificationDate())) {
            return false;
        }

        if (!term1.getCreationUser().equals(term2.getCreationUser())) {
            return false;
        }

        if (!term1.getModificationUser().equals(term2.getModificationUser())) {
            return false;
        }

        if (!term1.getDescription().equals(term2.getDescription())) {
            return false;
        }
        if (term1.getTerm() != term2.getTerm()) {
            return false;
        }
        if (term1.isActive() != term2.isActive()) {
            return false;
        }
        return true;

    }

    /**
     * Checks if two dates are equal.
     * @param date1
     *            the first date
     * @param date2
     *            the second date
     * @return true if two days equal, false otherwise
     */
    private boolean equalDates(Date date1, Date date2) {
        return Math.abs(date1.getTime() - date2.getTime()) < 1000;
    }
}
