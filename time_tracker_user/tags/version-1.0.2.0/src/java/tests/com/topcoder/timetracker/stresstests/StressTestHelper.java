/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Random;

import com.cronos.timetracker.common.Address;
import com.cronos.timetracker.common.Contact;
import com.cronos.timetracker.common.EncryptionRepository;
import com.cronos.timetracker.common.State;
import com.cronos.timetracker.company.Company;
import com.cronos.timetracker.user.AccountStatus;
import com.cronos.timetracker.user.User;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;


/**
 * The help class of the stress test.
 *
 * @author assistant
 * @version 1.0
 */
public class StressTestHelper {
    /**
     * The first level load of the stress test.
     */
    public static final int FIRST_LEVEL_LOAD = 10;

    /**
     * The second level load of the stress test.
     */
    public static final int SECOND_LEVEL_LOAD = 100;

    /**
     * The third level load of the stress test.
     */
    public static final int THIRD_LEVEL_LOAD = 500;

    /**
     * Represents the connection name used in stress tests.
     */
    public static final String CONNECTION_NAME = "stress";

    /**
     * Represents the id generator name used in stress tests.
     */
    public static final String IDGEN_NAME = "ttu2";

    /**
     * Represents the sql query to truncate the tables.
     */
    private static final String[] TRUNCATE_TABLES = {
        "DELETE FROM comp_reject_email;",
        "DELETE FROM reject_email;",
        "DELETE FROM comp_rej_reason;",
        "DELETE FROM company_contact;",
        "DELETE FROM user_contact;",
        "DELETE FROM contact;",
        "DELETE FROM user_address;",
        "DELETE FROM user_account;",
        "DELETE FROM company_address;",
        "DELETE FROM company;",
        "DELETE FROM address;",
        "DELETE FROM reject_reason;",
        "DELETE FROM state_name;",
        "DELETE FROM account_status;"
    };

    /**
     * Represents the name of algorithm used.
     */
    public static final String ALGO_NAME = "stress";

    /**
     * Represents the time used to record.
     */
    private static long time;

    /**
     * Load configurations.
     *
     * @throws Exception to JUnit
     */
    public static void loadConfigAndData() throws Exception {
        // FINAL REVIEWER (agh) FIX: remove existing namespace before loading
        unloadConfig();
        ConfigManager.getInstance().add("stress/stress.xml");

        EncryptionRepository.getInstance().registerAlgorithm(ALGO_NAME, new MockAlgorithm());

        DBConnectionFactory factory = new DBConnectionFactoryImpl(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        // FINAL REVIEWER (agh) FIX: clear data before insertion
        clearData(factory);                        


        Connection conn = factory.createConnection("stress");

        // FINAL REVIEWER (agh) FIX : use preapared statement to insert date values
        PreparedStatement insertState = null;
        PreparedStatement insertCompany = null;
        PreparedStatement insertStatus = null;
        Statement stat = null;
        try {
            insertState = conn.prepareStatement("insert into state_name (state_name_id, name, abbreviation, "
                    + "creation_date, creation_user, modification_date, modification_user) "
                    + "values (1, 'NC', 'NC', ?, 'user', ?, 'user')");
            insertCompany = conn.prepareStatement("insert into company (company_id, name, passcode, "
                    + "creation_date, creation_user, modification_date, modification_user) "
                    + "values (1, 'TopCoder', 'passcode1', ?, 'user', ?, 'user')");
            insertStatus = conn.prepareStatement("insert into account_status (account_status_id, description, "
                    + "creation_date, creation_user, modification_date, modification_user) "
                    + "values (1, 'Active', ?, 'user', ?, 'user')");

            Timestamp t = new Timestamp((new GregorianCalendar(2006, 06, 05)).getTimeInMillis());

            insertState.setTimestamp(1, t);
            insertState.setTimestamp(2, t);
            insertState.executeUpdate();

            insertCompany.setTimestamp(1, t);
            insertCompany.setTimestamp(2, t);
            insertCompany.executeUpdate();

            insertStatus.setTimestamp(1, t);
            insertStatus.setTimestamp(2, t);
            insertStatus.executeUpdate();
        } finally {
            try {
                if (insertState != null) {
                    insertState.close();
                }
                if (insertCompany != null) {
                    insertCompany.close();
                }
                if (insertStatus != null) {
                    insertStatus.close();
                }    
            } finally {
                conn.close();
            }
        }
    }

    /**
     * Unload the configurations.
     *
     * @throws Exception to JUnit
     */
    public static void unloadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            String ns = (String) it.next();
            cm.removeNamespace(ns);
        }
    }

    /**
     * Start record the time.
     */
    public static void startRecord() {
        time = System.currentTimeMillis();
    }

    /**
     * End the record and print the result.
     *
     * @param method the method name to test
     * @param load the load of this test
     */
    public static void endRecordAndPrintResult(String method, int load) {
        time = System.currentTimeMillis() - time;
        System.out.println("Test " + method + " " + load + " times used : " + time + " ms");
    }

    /**
     * Clear the database.
     *
     * @param factory the factory used to create connection
     *
     * @throws Exception to JUnit
     */
    public static void clearData(DBConnectionFactory factory) throws Exception {
        // clear the databases
        Connection conn = factory.createConnection("stress");        

        try {
            for (int i = 0; i < TRUNCATE_TABLES.length; i++) {
                Statement stmt = null;
                try {
                    stmt = conn.createStatement();
                    stmt.execute(TRUNCATE_TABLES[i]);
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }

            }
        } finally {
            conn.close();
        }        
    }

    /**
     * Creates the Contact object with the values set.
     *
     * @return the Contact object.
     */
    public static Contact createContact() {
        Contact contact = new Contact();
        contact.setFirstName("jan");
        contact.setLastName("marian");
        contact.setPhoneNumber("phon");
        contact.setEmailAddress("email");
        return contact;
    }

    /**
     * Creates the Address object with the values set.
     *
     * @return the Address object.
     */
    public static Address createAddress() {
        Address address = new Address();
        address.setCity("NY");
        address.setLine1("line1");
        address.setZipCode("123");

        address.setState(createState());
        return address;
    }

    /**
     * Creates the State object with the values set.
     *
     * @return the State object.
     */
    public static State createState() {
        State state = new State();
        state.setId(1);
        state.setAbbreviation("NC");
        state.setName("North Carolina");
        state.setChanged(false);
        return state;
    }

    /**
     * Create a company from a number.
     *
     * @param num the number for creating the company
     * @return the created company
     */
    public static Company createCompany(int num) {
        Company company = new Company();
        company.setAlgorithmName(ALGO_NAME);
        company.setPasscode("Passcode" + num + "p" + new Random().nextInt(1000));
        company.setCompanyName("TopCoder");
        
        company.setAddress(createAddress());
        company.setContact(createContact());
        return company;
    }

    /**
     * Create a user from a number.
     *
     * @param num the num
     * @return the created user
     */
    public static User createUser(int num) {

        AccountStatus status = new AccountStatus();
        status.setId(1);

        Address address = createAddress();
        Contact contact = createContact();

        User user = new User();
        user.setAccountStatus(status);
        user.setCompanyId(1);
        user.setContactInfo(contact);
        user.setAddress(address);
        user.setAlgorithmName(ALGO_NAME);
        user.setPassword("test");
        user.setUsername("username");

        return user;   
    }

    /**
     * End the record and print the result.
     *
     * @param method the method name to test
     * @param load the load of this test
     */
    public static void endRecordAndPrintResultFrom(String method, int load) {
        time = System.currentTimeMillis() - time;
        System.out.println("Test " + method + " from " + load + " entries used : "
                + time + " ms");
    }
}
