/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.stresstests;

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
 * @author vividmxx
 * @version 3.2
 */
final class StressTestHelper {

    /**
     * The run time used in stress test.
     */
    static final int RUN_NUMBER = 10;

    /**
     * The record number used in stress test.
     */
    static final int RECORD_NUMBER = 100;

    /**
     * Represents the SQL statement to insert records into "company" table.
     */
    private static final String INS_COMPANY = "insert into company (company_id,name,passcode,creation_date,"
        + "creation_user,modification_date,modification_user) values (?,'NAME','PASS',CURRENT,'',CURRENT,'')";

    /**
     * Represents the SQL statement to insert records into "invoice_status" table.
     */
    private static final String INS_INVOICE_STATUS = "insert into invoice_status (invoice_status_id,description,"
        + "creation_date,creation_user,modification_date,modification_user) values (?,'DESC',CURRENT,'',CURRENT,'')";

    /**
     * Represents the SQL statement to insert records into "payment_terms" table.
     */
    private static final String INS_PAYMENT_TERMS = "insert into payment_terms (payment_terms_id,description,"
        + "creation_date,creation_user,modification_date,modification_user,active,term) values "
        + "(?,'DESC',CURRENT,'',CURRENT,'',1,1)";

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
     * Represents the SQL statement to insert records into "time_status" table.
     */
    private static final String INS_TIME_STATUS = "insert into time_status (time_status_id,description,creation_date,"
        + "creation_user,modification_date,modification_user) values (?,'DESC',CURRENT,'',CURRENT,'')";

    /**
     * Represents the SQL statement to insert records into "task_type" table.
     */
    private static final String INS_TASK_TYPE = "insert into task_type (task_type_id,description,active,creation_date,"
        + "creation_user,modification_date,modification_user) values (?,'DESC',1,CURRENT,'',CURRENT,'')";

    /**
     * Represents the SQL statement to insert records into "time_entry" table.
     */
    private static final String INS_TIME_ENTRY = "insert into time_entry (time_entry_id,company_id,invoice_id,"
        + "time_status_id,task_type_id,description,entry_date,hours,billable,creation_date,creation_user,"
        + "modification_date,modification_user) values (?,?,?,?,?,?,?,?,1,CURRENT,'',CURRENT,'')";

    /**
     * Represents the SQL statement to insert records into "service_details" table.
     */
    private static final String INS_SD = "insert into service_details (service_detail_id,time_entry_id,invoice_id,"
        + "rate,amount,creation_date,creation_user,modification_date,modification_user) values "
        + "(?,?,?,?,?,CURRENT,'',CURRENT,'')";

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
            stat.execute("delete from service_details");
            stat.execute("delete from time_entry where time_entry_id >= 41000 and time_entry_id < 41200");
            stat.execute("delete from task_type where task_type_id = 41000");
            stat.execute("delete from time_status where time_status_id = 41000");
            stat.execute("delete from invoice where invoice_id >= 41000 and invoice_id < 41010");
            stat.execute("delete from project where project_id >= 41000 and project_id < 41010");
            stat.execute("delete from company where company_id = 41000");
            stat.execute("delete from invoice_status where invoice_status_id = 41000");
            stat.execute("delete from payment_terms where payment_terms_id = 41000");
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
        insertCompany();
        insertInvoiceStatus();
        insertPaymentTerms();
        insertProject();
        insertInvoice();
        insertTimeStatus();
        insertTaskType();
        insertTimeEntry();
        insertServiceDetails();
    }

    /**
     * Inserts records into "service_details" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertServiceDetails() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INS_SD);
            for (int i = 0; i < 100; i++) {
                stat.setInt(1, 41000 + i);
                stat.setInt(2, 41000 + i);
                stat.setInt(3, 41000 + (i / 20));
                stat.setInt(4, 1);
                stat.setInt(5, 100);
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
     * Inserts records into "time_entry" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertTimeEntry() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INS_TIME_ENTRY);
            for (int i = 0; i < 200; i++) {
                stat.setInt(1, 41000 + i);
                stat.setInt(2, 41000);
                stat.setInt(3, 41000 + (i / 20));
                stat.setInt(4, 41000);
                stat.setInt(5, 41000);
                stat.setString(6, "description" + i);
                stat.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                stat.setInt(8, i + 1);
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
     * Inserts records into "time_status" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertTimeStatus() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INS_TIME_STATUS);
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
     * Inserts records into "task_type" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertTaskType() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INS_TASK_TYPE);
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
            for (int i = 0; i < 10; i++) {
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
            for (int i = 0; i < 10; i++) {
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
     * Prints the test result.
     *
     * @param methodName
     *            the name of the target method
     * @param threadNumber
     *            the number the thread
     */
    static void printResultMulThreads(String methodName, int threadNumber) {
        System.out.println("Calling " + methodName + " with " + threadNumber + " threads costs "
                + (new Date().getTime() - timer.getTime()) + " milliseconds.");
    }

    /**
     * Loads the configuration.
     *
     * @throws Exception
     *             throws to JUnit
     */
    static void loadConfig() throws Exception {
        ConfigManager.getInstance().add("stresstests/config.xml");
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
}
