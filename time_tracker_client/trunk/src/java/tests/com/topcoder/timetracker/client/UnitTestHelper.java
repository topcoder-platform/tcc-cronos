/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.project.Project;
import com.topcoder.util.config.ConfigManager;


/**
 * Defines helper methods used in unit tests of this component.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
final public class UnitTestHelper {
    /** Represents the date format for date comparison. */
    private static final DateFormat DATEFORMAT = new SimpleDateFormat("yyMMddHH");

    /** Represents the db factory namespace. */
    public static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * This private constructor prevents the creation of a new instance.
     */
    private UnitTestHelper() {
    }

    /**
     * Gets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance. If the instance is null, the field is a static field. If any error occurs, null is
     * returned.
     *
     * @param type the class which the private field belongs to
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be retrieved
     *
     * @return the value of the private field
     */
    static Object getPrivateField(Class type, Object instance, String name) {
        Field field = null;
        Object obj = null;

        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }

        return obj;
    }

    /**
     * Sets the value of a private field in the given class.
     *
     * @param type the class which the private field belongs to
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be set
     * @param value the value to set
     */
    static void setPrivateField(Class type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
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
        // set up environment
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
     * Setup the data of the database.
     *
     * @param dbFactory the db factory
     * @param connName the connection name
     *
     * @throws Exception any exception to JUnit
     */
    public static void setUpDatabase(DBConnectionFactory dbFactory, String connName)
        throws Exception {
        Statement stmt = null;
        Connection conn = null;

        conn = createConnection(dbFactory, connName, true);

        stmt = conn.createStatement();

        clearDatabase(dbFactory, connName);
        stmt.executeUpdate("insert into company values (2, 'company2', 'passcode2', CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate("insert into company values (1, 'company1', 'passcode1', CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate("insert into project values (1, 'project1', 1, 'passcode1', CURRENT, CURRENT, CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate("insert into project values (2, 'project2', 1, 'passcode2', CURRENT, CURRENT, CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate("insert into project values (3, 'project3', 1, 'passcode3', CURRENT, CURRENT, CURRENT, USER, CURRENT, USER);");
        stmt.executeUpdate("insert into project values (4, 'project4', 1, 'passcode4', CURRENT, CURRENT, CURRENT, USER, CURRENT, USER);");

        conn.close();
    }

    /**
     * Clear the data of the database.
     *
     * @param dbFactory the db factory
     * @param connName the connection name
     *
     * @throws Exception any exception to JUnit
     */
    public static void clearDatabase(DBConnectionFactory dbFactory, String connName)
        throws Exception {
        Statement stmt = null;
        Connection conn = null;

        conn = createConnection(dbFactory, connName, true);

        stmt = conn.createStatement();

        stmt.executeUpdate("delete from client_project");
        stmt.executeUpdate("delete from project");
        stmt.executeUpdate("delete from client");
        stmt.executeUpdate("delete from company");
        

        conn.close();
    }

    /**
     * <p>
     * Get database connection from the db connection factory. It will set the connection to auto commit as required.
     * </p>
     *
     * @param dbFactory db factory used to connect
     * @param connName connection name
     * @param autoCommit auto commit flag
     *
     * @return A database connection.
     *
     * @throws Exception If can't get connection or fails to set the auto commit value.
     */
    public static Connection createConnection(DBConnectionFactory dbFactory, String connName, boolean autoCommit)
        throws Exception {
        // create a DB connection
        Connection conn = dbFactory.createConnection(connName);

        // Begin transaction.
        conn.setAutoCommit(autoCommit);

        return conn;
    }

    /**
     * Create the client used for test with the specified client id.
     *
     * @param id the id
     *
     * @return the client
     */
    public static Client createCient(long id) {
        Client client = new Client();

        client.setActive(true);
        client.setAddress(new Address());
        client.setChanged(true);
        client.setCompanyId(id);
        client.setContact(new Contact());
        client.setCreationDate(new Date());
        client.setCreationUser("creationUser");
        client.setEndDate(new Date());
        client.setId(id);
        client.setModificationDate(new Date());
        client.setModificationUser("modificationUser");
        client.setName("userName" + id);
        client.setGreekName("greekName" + id);

        PaymentTerm term = new PaymentTerm();
        term.setId(id);
        client.setPaymentTerm(term);
        client.setProjects(new Project[0]);
        client.setSalesTax(0.1);
        client.setStartDate(new Date());

        Contact contact = new Contact();
        contact.setId(id);
        client.setContact(contact);

        Address address = new Address();
        address.setId(id);
        client.setId(id);
        client.setAddress(address);

        Project project1 = new Project();
        project1.setId(id);

        Project project2 = new Project();
        project2.setId(id * 2);

        client.setProjects(new Project[] {project1, project2});

        return client;
    }

    /**
     * <p>
     * Compare two client for equality.
     * </p>
     *
     * @param first the first client
     * @param second the second client
     *
     * @return true if first client is equal to the second client
     */
    public static boolean isClientEquals(Client first, Client second) {
        if ((first.getId() != second.getId()) || (first.getCompanyId() != second.getCompanyId())
                || !first.getCreationUser().equals(second.getCreationUser())
                || !first.getModificationUser().equals(second.getModificationUser())
                || (first.getPaymentTerm().getId() != second.getPaymentTerm().getId())
                || !DATEFORMAT.format(first.getCreationDate()).equals(DATEFORMAT.format(second.getCreationDate()))
                || !DATEFORMAT.format(first.getModificationDate())
                                  .equals(DATEFORMAT.format(second.getModificationDate()))
                || !first.getName().equals(second.getName())
                || (first.getGreekName() != null && !first.getGreekName().equals(second.getGreekName()))
                || !DATEFORMAT.format(first.getStartDate()).equals(DATEFORMAT.format(second.getStartDate()))
                || !DATEFORMAT.format(first.getEndDate()).equals(DATEFORMAT.format(second.getEndDate()))) {
            return false;
        }

        return true;
    }
}
