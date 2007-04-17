/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.accuracytests;

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

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.ejb.AuditLocalHome;
import com.topcoder.timetracker.audit.ejb.AuditLocalObject;
import com.topcoder.timetracker.audit.ejb.AuditSessionBean;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.State;
import com.topcoder.timetracker.contact.ejb.AddressBean;
import com.topcoder.timetracker.contact.ejb.AddressLocal;
import com.topcoder.timetracker.contact.ejb.AddressLocalHome;
import com.topcoder.timetracker.contact.ejb.ContactBean;
import com.topcoder.timetracker.contact.ejb.ContactHomeLocal;
import com.topcoder.timetracker.contact.ejb.ContactLocal;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * A helper class to perform those common operations which are helpful for the test.
 * </p>
 *
 * @author victorsam
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
     * The audit configuration file for this component.
     * </p>
     */
    public static final String AUDIT_CONFIG_FILE = "test_files" + File.separator + "accuracytests" + File.separator
        + "audit_config.xml";

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private AccuracyTestHelper() {
    }

    /**
     * <p>
     * This method creates an address instance for testing purpose.
     * </p>
     *
     * @return an address instance for testing purpose.
     */
    public static Address createAddress() {
        Address address = new Address();
        address.setAddressType(AddressType.USER);
        address.setLine1("street");
        address.setLine2("appt");
        address.setCity("city");
        State state = new State();
        state.setId(1);
        address.setState(state);
        Country country = new Country();
        country.setId(1);
        address.setCountry(country);
        address.setPostalCode("10028");
        address.setCreationDate(new Date());
        address.setCreationUser("tc");
        address.setModificationDate(new Date());
        address.setModificationUser("tc");

        return address;
    }

    /**
     * <p>
     * Verifies whether the given two addresses are equals or not.
     * </p>
     *
     * @param expectedAddress the expected address
     * @param actualAddress the actual address
     */
    public static void assertAddressEquals(Address expectedAddress, Address actualAddress) {
        Assert.assertEquals("The line1 are not equals.", expectedAddress.getLine1(), actualAddress.getLine1());
        Assert.assertEquals("The line2 are not equals.", expectedAddress.getLine2(), actualAddress.getLine2());
        Assert.assertEquals("The city are not equals.", expectedAddress.getCity(), actualAddress.getCity());
        Assert.assertEquals("The state are not equals.", expectedAddress.getState().getId(),
            actualAddress.getState().getId());
        Assert.assertEquals("The country are not equals.", expectedAddress.getCountry().getId(),
            actualAddress.getCountry().getId());
        Assert.assertEquals("The creation users are not equals.", expectedAddress.getCreationUser(),
            actualAddress.getCreationUser());
        Assert.assertEquals("The modification users are not equals.", expectedAddress.getModificationUser(),
            actualAddress.getModificationUser());
    }

    /**
     * <p>
     * This method creates a contact instance for testing purpose.
     * </p>
     *
     * @return a contact instance for testing purpose.
     */
    public static Contact createContact() {
        Contact contact = new Contact();
        contact.setContactType(ContactType.USER);
        contact.setFirstName("contact");
        contact.setLastName("person");
        contact.setPhoneNumber("1234567890");
        contact.setEmailAddress("contact@person.net");
        contact.setCreationDate(new Date());
        contact.setCreationUser("tc");
        contact.setModificationDate(new Date());
        contact.setModificationUser("tc");

        return contact;
    }

    /**
     * <p>
     * Verifies whether the given two contact are equals or not.
     * </p>
     *
     * @param expectedContact the expected contact
     * @param actualContact the actual contact
     */
    public static void assertContactEquals(Contact expectedContact, Contact actualContact) {
        Assert.assertEquals("The first name are not equals.", expectedContact.getFirstName(),
            actualContact.getFirstName());
        Assert.assertEquals("The last name are not equals.", expectedContact.getLastName(),
            actualContact.getLastName());
        Assert.assertEquals("The phone number are not equals.", expectedContact.getPhoneNumber(),
            actualContact.getPhoneNumber());
        Assert.assertEquals("The email address are not equals.", expectedContact.getEmailAddress(),
            actualContact.getEmailAddress());
        Assert.assertEquals("The creation users are not equals.", expectedContact.getCreationUser(),
            actualContact.getCreationUser());
        Assert.assertEquals("The modification users are not equals.", expectedContact.getModificationUser(),
            actualContact.getModificationUser());
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
     * Configures the DJB environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public static void eJBConfig() throws Exception {
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

        context.rebind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS");
        context.rebind("java:comp/env/AddressDAOKey", "AddressDAO");
        context.rebind("java:comp/env/ContactDAOKey", "ContactDAO");

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("java:comp/env/ejb/AuditLocal",
            AuditLocalHome.class, AuditLocalObject.class, AuditSessionBean.class);

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        //  for address bean
        sampleServiceDescriptor = new SessionBeanDescriptor("java:comp/env/ejb/AddressLocal", AddressLocalHome.class,
            AddressLocal.class, AddressBean.class);
        mockContainer.deploy(sampleServiceDescriptor);

        // for contact bean
        sampleServiceDescriptor = new SessionBeanDescriptor("java:comp/env/ejb/ContactLocal", ContactHomeLocal.class,
            ContactLocal.class, ContactBean.class);
        mockContainer.deploy(sampleServiceDescriptor);
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
     * Asserts the given two <code>long</code> arrays should be equals.
     * </p>
     *
     * @param msg the message to use when the two arrays are not equal
     * @param expected the expected <code>long</code> array instance
     * @param actual the actual <code>long</code> array instance
     */
    public static void assertLongArray(String msg, long[] expected, long[] actual) {
        Assert.assertEquals(msg, expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(msg, expected[i], actual[i]);
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