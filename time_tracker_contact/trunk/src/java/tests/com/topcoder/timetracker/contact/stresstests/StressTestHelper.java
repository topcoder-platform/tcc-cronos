/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.stresstests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.State;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * The helper class for stress tests.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class StressTestHelper {

    /**
     * DB Connection factory namespace.
     */
    private static final String TEST_DB_FACTORY = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * Private Constructor.
     */
    private StressTestHelper() {
    }

    /**
     * Loads the given configuration file.
     *
     * @param file the file to load
     */
    public static final void loadConfig(String file) {
        ConfigManager cm = ConfigManager.getInstance();
        try {
            cm.add(file);
        } catch (ConfigManagerException e) {
            throw new RuntimeException("Error initializing test config.", e);
        }
    }

    /**
     * Initializes the test by loading valid configurations.
     */
    public static final void initConfig() {
        ConfigManager cm = ConfigManager.getInstance();
        try {
            cm.add("stresstests/config.xml");
        } catch (ConfigManagerException e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing test config.", e);
        }
    }

    /**
     * Clears ConfigManager.
     */
    public static final void clearConfig() {
        try {
            ConfigManager cm = ConfigManager.getInstance();
            for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
                String namespace = (String) iter.next();
                cm.removeNamespace(namespace);
            }
        } catch (UnknownNamespaceException e) {
            throw new RuntimeException("Error cleaning up test config.", e);
        }
    }

    /**
     * Initializes the test data.
     */
    public static final void initDB() {
        try {
            clearDB();
            executeScript(getScript("test_files/stresstests/load.sql"));
        } catch (Exception e) {
            throw new RuntimeException("Error initializing test db.", e);
        }
    }

    /**
     * Cleans up the test data.
     */
    public static final void clearDB() {
        try {
            executeScript(getScript("test_files/stresstests/clean.sql"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error cleaning up test db.");
        }
    }

    /**
     * Loads the commands from a file.
     *
     * @param filename the file containing the queries.
     * @return array of queries to execute
     * @throws IOException if the script cannot be loaded
     */
    private static String[] getScript(String filename) throws IOException {
        File f = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String input = null;
        StringBuffer sb = new StringBuffer();
        while ((input = reader.readLine()) != null) {
            if (!input.trim().startsWith("--") && input.trim().length() > 0) {
                sb.append(input);
            }
        }
        return sb.toString().split(";");
    }

    /**
     * Executes the statements sequentially.
     *
     * @param script list of commands
     * @throws Exception if DB problems occur
     */
    private static void executeScript(String[] script) throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(TEST_DB_FACTORY);
        Connection conn = factory.createConnection();
        try {
            conn.setAutoCommit(false);
            for (int i = 0; i < script.length; i++) {
                if (script[i].trim().length() == 0) {
                    continue;
                }

                Statement stmt = conn.createStatement();
                // System.out.println("executing: " + script[i]);
                stmt.executeUpdate(script[i]);
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Creates a new test <code>Address</code>.
     *
     * @return test address
     */
    public static Address createAddress1() {
        Address address = new Address();
        address.setAddressType(AddressType.COMPANY);
        address.setCity("New York");
        Country c = new Country();
        c.setId(1);
        address.setCountry(c);
        c.setName("United States");
        State s = new State();
        s.setId(1);
        s.setName("New York");
        address.setState(s);
        address.setLine1("Street");
        address.setCreationUser("TCSDEVELOPER");
        address.setModificationUser("TCSDEVELOPER");
        address.setPostalCode("10000");
        return address;
    }

    /**
     * Creates a new test <code>Contact</code>.
     *
     * @return test Contact
     */
    public static Contact createContact1() {
        Contact contact = new Contact();
        contact.setContactType(ContactType.COMPANY);
        contact.setFirstName("John");
        contact.setLastName("Doe");
        contact.setEmailAddress("email@test.com");
        contact.setPhoneNumber("1234567");
        contact.setCreationUser("TCSDEVELOPER");
        contact.setModificationUser("TCSDEVELOPER");
        return contact;
    }
}
