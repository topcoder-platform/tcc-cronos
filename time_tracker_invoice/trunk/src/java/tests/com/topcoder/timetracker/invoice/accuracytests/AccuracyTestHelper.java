/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.accuracytests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import junit.framework.Assert;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * A helper class to perform those common operations which are helpful for the test.
 * </p>
 *
 * @author enefem21
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
     * Asserts the given two <code>Invoice</code> should be equals.
     * </p>
     *
     * @param expected the expected <code>Invoice</code> instance
     * @param actual the actual <code>Invoice</code> instance
     */
    public static void assertInvoices(Invoice expected, Invoice actual) {
        Assert.assertEquals("The invoice ids are not equals.", expected.getId(), actual.getId());
        Assert.assertEquals("The invoice company ids are not equals.", expected.getCompanyId(), actual.getCompanyId());
        Assert.assertEquals("The invoice payment term ids are not equals.", expected.getPaymentTerm().getId(),
            actual.getPaymentTerm().getId());
        Assert.assertEquals("The invoice creation users are not equals.", expected.getCreationUser(),
            actual.getCreationUser());
        Assert.assertEquals("The invoice modification users are not equals.", expected.getModificationUser(),
            actual.getModificationUser());
        Assert.assertEquals("The invoice numbers are not equals.", expected.getInvoiceNumber(),
            actual.getInvoiceNumber());
        Assert.assertEquals("The invoice purchase order numbers are not equals.", expected.getPurchaseOrderNumber(),
            actual.getPurchaseOrderNumber());
        Assert.assertEquals("The invoice status ids are not equals.", expected.getInvoiceStatus().getId(),
            actual.getInvoiceStatus().getId());
        Assert.assertEquals("The invoice expense entries are not equals.", expected.getExpenseEntries().length,
            actual.getExpenseEntries().length);
        Assert.assertEquals("The invoice fixed billing entries are not equals.",
            expected.getFixedBillingEntries().length, actual.getFixedBillingEntries().length);
    }

    /**
     * <p>
     * Creates Invoice for testing.
     * </p>
     *
     * @return the Invoice instance
     */
    public static Invoice createInvoice() {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setInvoiceDate(new Date());
        invoice.setDueDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(11, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        return invoice;
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
