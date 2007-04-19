/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.Assert;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.database.AndFragmentBuilder;
import com.topcoder.search.builder.database.DatabaseSearchStrategy;
import com.topcoder.search.builder.database.EqualsFragmentBuilder;
import com.topcoder.search.builder.database.InFragmentBuilder;
import com.topcoder.search.builder.database.LikeFragmentBuilder;
import com.topcoder.search.builder.database.NotFragmentBuilder;
import com.topcoder.search.builder.database.NullFragmentBuilder;
import com.topcoder.search.builder.database.OrFragmentBuilder;
import com.topcoder.search.builder.database.RangeFragmentBuilder;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.LessThanFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.timetracker.company.ejb.CompanyBean;
import com.topcoder.timetracker.company.ejb.CompanyHomeLocal;
import com.topcoder.timetracker.company.ejb.CompanyLocal;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class UnitTestHelper {
    /** Represents the connection name for testing. */
    public static final String CONN_NAME = "informix";

    /** Represents the id generator name for testing. */
    public static final String IDGEN_NAME = "DbCompanyDAO";

    /** Represents whether the mock ejb to be deployed. */
    private static boolean deployed = false;

    /** Represents the db connection factory for testing. */
    private static DBConnectionFactory connFactory;

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

        configManager.add("LocalCompanyManagerDelegate_Config.xml");
        configManager.add("CompanyBean_Config.xml");
        configManager.add("DBConnectionFactory_Config.xml");
        configManager.add("DbCompanyDAO_Config.xml");
        configManager.add("SearchBuilder_Config.xml");
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
            e.printStackTrace();

            // ignore
        } catch (NoSuchFieldException e) {
            e.printStackTrace();

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
            e.printStackTrace();

            // ignore
        } catch (NoSuchFieldException e) {
            e.printStackTrace();

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
     * Get the DBConnectionFactory instance.
     * </p>
     *
     * @return the DBConnectionFactory instance.
     *
     * @throws Exception any exception during the create DBConnectionFactory process.
     */
    public static DBConnectionFactory getDBConnectionFactory() throws Exception {
        if (connFactory == null) {
            connFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        }

        return connFactory;
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
        DBConnectionFactory factory = getDBConnectionFactory();

        return factory.createConnection();
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
    public static int getCompanyRecords(Connection connection) throws Exception {
        return getTableRecords(connection, "company");
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
     * Build a Company instance for testing.
     *
     * @return a Company instance.
     */
    public static Company buildCompany() {
        Company company = new Company();
        Address address = new Address();
        address.setId(1);
        address.setChanged(true);

        Contact contact = new Contact();
        contact.setId(2);
        contact.setChanged(true);
        company.setAddress(address);
        company.setContact(contact);
        company.setCompanyName("companyName");
        company.setPassCode("passCode");
        company.setChanged(true);

        return company;
    }

    /**
     * <p>
     * Asserts the two given Company instances to be equals.
     * </p>
     *
     * @param expected the expected Company instance.
     * @param actual the actual Company instance.
     */
    public static void assertEquals(Company expected, Company actual) {
        if (expected == null) {
            Assert.assertNull("The actual company should be null.", actual);
            return;
        }
        Assert.assertEquals("The id should be correct.", expected.getId(), actual.getId());
        Assert.assertEquals("The companyName should be correct.", expected.getCompanyName(), actual.getCompanyName());
        Assert.assertEquals("The passCode should be correct.", expected.getPassCode(), actual.getPassCode());
        Assert.assertEquals("The changed should be correct.", expected.isChanged(), actual.isChanged());
        Assert.assertEquals("The address should be correct.", expected.getAddress(), actual.getAddress());
        Assert.assertEquals("The contact should be correct.", expected.getContact(), actual.getContact());
        Assert.assertEquals("The creationDate should be correct.", expected.getCreationDate().getTime() / 1000,
            actual.getCreationDate().getTime() / 1000);
        Assert.assertEquals("The creationUser should be correct.",
                expected.getCreationUser(), actual.getCreationUser());
        Assert.assertEquals("The modificationDate should be correct.", expected.getModificationDate().getTime() / 1000,
            actual.getModificationDate().getTime() / 1000);
        Assert.assertEquals("The modificationUser should be correct.", expected.getModificationUser(),
            actual.getModificationUser());
    }

    /**
     * <p>
     * Asserts the search result of the filter is the same as the expected result.
     * </p>
     *
     * @param expected the expected result.
     * @param actual the actual result.
     */
    public static void assertEquals(Company[] expected, Company[] actual) {
        Assert.assertEquals("The return length should be correct.", expected.length, actual.length);

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * <p>
     * Deploys the mock ejb for testing purpose.
     * </p>
     *
     * @throws Exception exception to junit.
     */
    public static void deployEJB() throws Exception {
        if (!deployed) {
            Map env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, MockContextFactory.class.getName());

            InitialContext context = new InitialContext((Hashtable) env);

            /*
             * Deploy EJBs to the MockContainer if we run outside of the app server In cactus mode all but one
             * EJB are deployed by the app server, so we don't need to do it.
             */
            MockContextFactory.setAsInitial();

            // Create an instance of the MockContainer and pass the JNDI context that
            // it will use to bind EJBs.
            context.bind("java:comp/env/SpecificationNamespace", "com.topcoder.timetracker.company.ejb.objectfactory");
            context.bind("java:comp/env/CompanyDAOKey", "DbCompanyDAO");

            MockContainer mockContainer = new MockContainer(context);

            /*
             * Create the deployment descriptor of the bean. Stateless and Stateful beans both use
             * SessionBeanDescriptor.
             */
            SessionBeanDescriptor beanDescriptor = new SessionBeanDescriptor("java:comp/env/ejb/company/CompanyEJB",
                    CompanyHomeLocal.class, CompanyLocal.class, CompanyBean.class);

            mockContainer.deploy(beanDescriptor);
            deployed = true;
        }
    }

    /**
     * <p>
     * Undeploys the mock ejb.
     * </p>
     */
    public static void undeployEJB() {
        if (deployed) {
            MockContextFactory.revertSetAsInitial();
            deployed = false;
        }
    }

    /**
     * <p>
     * Create the SearchBundle. The context, searchable fields,
     * alias and associations will also be hard-coded here.
     * </p>
     *
     * @return the created SearchBundle instance.
     *
     * @throws Exception any exception during the create DBConnectionFactory process.
     */
    public static SearchBundle createSearchBundle() throws Exception {
        String context = "SELECT"
            + "   company.company_id, company.name, company.passcode, company.creation_date, company.creation_user,"
            + "   company.modification_date, company.modification_user,"
            + "   C.first_name, C.last_name, C.phone, C.email,"
            + "   ADR.line1, ADR.line2, ADR.city, ADR.zip_code, SN.name"
            + " FROM company"
            + " INNER JOIN contact_relation CR"
            + "   ON CR.entity_id = company.company_id AND CR.contact_type_id = " + ContactType.COMPANY.getId()
            + " INNER JOIN contact AS C"
            + "   ON C.contact_id = CR.contact_id"
            + " INNER JOIN address_relation AR"
            + "   ON AR.entity_id = company.company_id AND AR.address_type_id = " + AddressType.COMPANY.getId()
            + " INNER JOIN address AS ADR"
            + "   ON ADR.address_id = AR.address_id"
            + " INNER JOIN state_name SN"
            + "   ON SN.state_name_id = ADR.state_name_id WHERE";
        Map alias = new HashMap();
        alias.put(CompanyDAO.SEARCH_COMPANY_NAME, "company.name");
        alias.put(CompanyDAO.SEARCH_CREATED_DATE, "company.creation_date");
        alias.put(CompanyDAO.SEARCH_CREATED_USER, "company.creation_user");
        alias.put(CompanyDAO.SEARCH_MODIFICATION_DATE, "company.modification_date");
        alias.put(CompanyDAO.SEARCH_MODIFICATION_USER, "company.modification_user");
        alias.put(CompanyDAO.SEARCH_CONTACT_FIRST_NAME, "C.first_name");
        alias.put(CompanyDAO.SEARCH_CONTACT_LAST_NAME, "C.last_name");
        alias.put(CompanyDAO.SEARCH_CONTACT_PHONE, "C.phone");
        alias.put(CompanyDAO.SEARCH_CONTACT_EMAIL, "C.email");
        alias.put(CompanyDAO.SEARCH_STREET_ADDRESS1, "ADR.line1");
        alias.put(CompanyDAO.SEARCH_STREET_ADDRESS2, "ADR.line2");
        alias.put(CompanyDAO.SEARCH_CITY, "ADR.city");
        alias.put(CompanyDAO.SEARCH_ZIP_CODE, "ADR.zip_code");
        alias.put(CompanyDAO.SEARCH_STATE, "SN.name");

        Map fields = new HashMap();

        for (Iterator iter = alias.keySet().iterator(); iter.hasNext();) {
            fields.put(iter.next(), null);
        }

        Map associations = new HashMap();
        associations.put(AndFilter.class, new AndFragmentBuilder());
        associations.put(OrFilter.class, new OrFragmentBuilder());
        associations.put(LikeFilter.class, new LikeFragmentBuilder());
        associations.put(NotFilter.class, new NotFragmentBuilder());
        associations.put(EqualToFilter.class, new EqualsFragmentBuilder());
        associations.put(InFilter.class, new InFragmentBuilder());
        associations.put(NullFilter.class, new NullFragmentBuilder());
        associations.put(GreaterThanFilter.class, new RangeFragmentBuilder());
        associations.put(GreaterThanOrEqualToFilter.class, new RangeFragmentBuilder());
        associations.put(BetweenFilter.class, new RangeFragmentBuilder());
        associations.put(LessThanOrEqualToFilter.class, new RangeFragmentBuilder());
        associations.put(LessThanFilter.class, new RangeFragmentBuilder());

        DatabaseSearchStrategy stategy = new DatabaseSearchStrategy(getDBConnectionFactory(), CONN_NAME, associations);

        return new SearchBundle("DbCompanyDAO", fields, alias, context, stategy);
    }
}
