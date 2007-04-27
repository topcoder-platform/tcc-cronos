/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * This class is a helper class used in stress tests.
 *
 * @author myxgyy
 * @version 1.0
 */
final class StressTestHelper {

    /**
     * The run time used in stress test.
     */
    static final int RUN_NUMBER = 5;

    /**
     * The record number used in stress test.
     */
    static final int RECORD_NUMBER = 50;

    /**
     * Represents the SQL statement to insert records into "id_sequences" table.
     */
    private static final String INS_ID_SEQUENCE = "INSERT INTO id_sequences (name,"
    	+ "next_block_start,block_size,exhausted) VALUES('com.topcoder.timetracker.entry.fixedbilling',"
    	+ "100,20,0)";

    /**
     * Represents the SQL statement to insert records into "invoice_status" table.
     */
    private static final String INS_INVOICE_STATUS = "insert into invoice_status (invoice_status_id,description,"
        + "creation_date,creation_user,modification_date,modification_user) values (?,'DESC',CURRENT,'',CURRENT,'')";

    /**
     * Represents the SQL statement to insert records into "company" table.
     */
    private static final String INS_COMPANY = "insert into company (company_id,name,passcode,creation_date,"
        + "creation_user,modification_date,modification_user) values (?,'NAME','PASS',CURRENT,'',CURRENT,'')";

    /**
     * Represents the SQL statement to insert records into "fix_bill_status" table.
     */
    private static final String INS_FIX_BILL_STATUS = "insert into fix_bill_status(fix_bill_status_id, description,"
        + "creation_date,creation_user,modification_date,modification_user) values (?, ?, CURRENT, ?, CURRENT, ?)";

    /**
     * Represents the SQL statement to insert records into "payment_terms" table.
     */
    private static final String INS_PAYMENT_TERMS = "insert into payment_terms (payment_terms_id,description,"
        + "creation_date,creation_user,modification_date,modification_user,active,term) values "
        + "(?,'DESC',CURRENT,'',CURRENT,'',1,1)";

    /**
     * Represents the SQL statement to insert records into "reject_reason" table.
     */
    private static final String INS_REJECT_REASON = "insert into reject_reason (reject_reason_id,description,"
        + "creation_date,creation_user,modification_date,modification_user,active) values "
        + "(?,'DESC',CURRENT,'',CURRENT,'',1)";

    /**
     * Represents the SQL statement to insert records into "project" table.
     */
    private static final String INS_PROJECT = "insert into project (project_id,name,company_id,description,"
        + "start_date,end_date,creation_date,creation_user,modification_date,modification_user) values "
        + "(?,?,?,?,?,?,CURRENT,'',CURRENT,'')";

    /**
     * Represents the SQL statement to insert records into "invoice" table.
     */
    private static final String INS_INVOICE = "insert into invoice (invoice_id,project_id,creation_date,creation_user,"
        + "modification_date,modification_user,salesTax,payment_terms_id,invoice_number,po_number,invoice_date,"
        + "due_date,paid,company_id,invoice_status_id) values (?,?,CURRENT,'',CURRENT,'',0,?,'','',?,?,0,?,?)";

    /**
     * Represents the SQL statement to insert records into "fix_bill_entry" table.
     */
    private static final String INSERT_ENTRY_SQL = "insert into fix_bill_entry(fix_bill_entry_id, company_id, "
        + "invoice_id, fix_bill_status_id, description, entry_date, amount, creation_date, creation_user, "
        + "modification_date, modification_user) values (?,?,?,?,?,CURRENT,?,CURRENT,'',CURRENT,'')";

    /**
     * Represents the SQL statement to insert records into "fb_reject_reason" table.
     */
    private static final String INSERT_REASON_SQL = "insert into fb_reject_reason(fix_bill_entry_id, "
        + "reject_reason_id, creation_date, creation_user,modification_date,modification_user) values (?,?,"
        + "CURRENT,'',CURRENT,'')";

    /**
     * The <code>DBConnectionFactory</code> instance used in stress test.
     */
    private static DBConnectionFactory factory = null;

    /**
     * The <code>Date</code> instance used in the test to record the time.
     */
    private static Date timer = null;

    /**
     * The private constructor prevents creation.
     */
    private StressTestHelper() {
    }

    /**
     * Tear down the database.
     *
     * @throws Exception
     *             if any error occurred when tear down
     */
    static void tearDownDatabase() throws Exception {
        Connection connection = createConnection();
        Statement stat = null;
        try {
            stat = connection.createStatement();
            stat.execute("delete from fb_reject_reason");
            stat.execute("delete from fix_bill_entry");
            stat.execute("delete from invoice");
            stat.execute("delete from project");
            stat.execute("delete from company where company_id=41000");
            stat.execute("delete from invoice_status");
            stat.execute("delete from payment_terms");
            stat.execute("delete from fb_reject_reason where reject_reason_id in (1,2,3,4,5,6,7,8,9,10," +
            		"11,12,13,14,15,16,17,18,19,20," +
            		"21,22,23,24,25,26,27,28,29,30," +
            		"31,32,33,34,35,36,37,38,39,40," +
            		"41,42,43,44,45,46,47,48,49,50)");
            stat.execute("delete from reject_reason where reject_reason_id in (1,2,3,4,5,6,7,8,9,10," +
            		"11,12,13,14,15,16,17,18,19,20," +
            		"21,22,23,24,25,26,27,28,29,30," +
            		"31,32,33,34,35,36,37,38,39,40," +
            		"41,42,43,44,45,46,47,48,49,50)");
            stat.execute("delete from fix_bill_status");
//            stat.execute("delete from id_sequences where name='com.topcoder.timetracker.entry.fixedbilling'");
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Setup the database used in stress test.
     *
     * @throws Exception
     *             if any error occurred when setup
     */
    static void setUpDatabase() throws Exception {
//    	insertIdSequences();
    	insertCompany();
    	insertProject();
    	insertInvoiceStatus();
    	insertPaymentTerms();
    	insertInvoice();
        insertFixBillingStatus();
        insertRejectReason();
        insertFixBillEntry();
        insertFbRejectReason();
    }

    /**
     * Inserts records into "project" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertFbRejectReason() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INSERT_REASON_SQL);
            for (int i = 0; i < 50; i++) {
                stat.setInt(1, 41001);
                stat.setInt(2, i + 1);
                stat.execute();
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Inserts records into "id_sequences" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertIdSequences() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INS_ID_SEQUENCE);
            stat.execute();
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Inserts records into "project" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertProject() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INS_PROJECT);
            for (int i = 0; i < 5; i++) {
                stat.setInt(1, 41000 + i);
                stat.setString(2, "name" + i);
                stat.setInt(3, 41000);
                stat.setString(4, "description" + i);
                stat.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                stat.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                stat.execute();
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Inserts records into "project" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertFixBillEntry() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INSERT_ENTRY_SQL);
            for (int i = 0; i < 50; i++) {
                stat.setInt(1, 41000 + i);
                stat.setInt(2, 41000);
                stat.setInt(3, 41000);
                stat.setInt(4, 40001 + i);
                stat.setString(5, "description[" + i + "]");
                stat.setDouble(6, 50.0);
                stat.execute();
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Inserts records into "invoice_status" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertInvoiceStatus() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INS_INVOICE_STATUS);
            stat.setInt(1, 41000);
            stat.execute();
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Inserts records into "invoice" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertInvoice() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INS_INVOICE);
            for (int i = 0; i < 5; i++) {
                stat.setInt(1, 41000 + i);
                stat.setInt(2, 41000 + i);
                stat.setInt(3, 41000);
                stat.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                stat.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                stat.setInt(6, 41000);
                stat.setInt(7, 41000);
                stat.execute();
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Inserts records into "company" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertCompany() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INS_COMPANY);
            stat.setInt(1, 41000);
            stat.execute();
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Inserts records into "FIX_BILL_STATUS" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertFixBillingStatus() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
        	stat = connection.prepareStatement(INS_FIX_BILL_STATUS);
        	for (int i = 0; i < 50; i++) {
                stat.setInt(1, 40001 + i);
                stat.setString(2, "statuses[" + i + "]");
                stat.setString(3, "stresstester");
                stat.setString(4, "stresstester");
                stat.execute();
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Inserts records into "payment_terms" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertPaymentTerms() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INS_PAYMENT_TERMS);
            stat.setInt(1, 41000);
            stat.execute();
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Inserts records into "reject_reason" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertRejectReason() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INS_REJECT_REASON);
            for (int i = 0; i < 50; i++) {
            	stat.setInt(1, i + 1);
            	stat.execute();
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Starts the timer. Simply sets the timer to the current time.
     */
    static void startTimer() {
        timer = new Date();
    }

    /**
     * Prints the test result.
     *
     * @param methodName
     *            the name of the target method
     * @param runNumber
     *            the number the target method is called
     */
    static void printResultMulTimes(String methodName, int runNumber) {
        System.out.println("Calling " + methodName + " for " + runNumber + " times costs "
                + (new Date().getTime() - timer.getTime()) + " milliseconds.");
    }

    /**
     * Loads the configuration.
     *
     * @throws Exception
     *             throws to JUnit
     */
    static void loadConfig() throws Exception {
        ConfigManager.getInstance().add("stresstests/Config.xml");
    }

    /**
     * Clears the configuration.
     *
     * @throws Exception
     *             throws to JUnit
     */
    static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Creates a connection for test.
     *
     * @return the created connection
     * @throws Exception
     *             if any error occurs when creating the connection
     */
    private static Connection createConnection() throws Exception {
        if (factory == null) {
            factory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        }
        return factory.createConnection();
    }

    /**
     * Delete the table.
     *
     * @param tableName the table name to be delete.
     * @throws Exception throws to JUnit
     */
    static void clearTable(String tableName) throws Exception {
    	Connection connection = createConnection();
        Statement stat = null;
        try {
            stat = connection.createStatement();
            stat.execute("delete from " + tableName);
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }
}
