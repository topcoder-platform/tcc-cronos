/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.TransactionManager;
import org.mockejb.TransactionPolicy;
import org.mockejb.interceptor.AspectSystemFactory;
import org.mockejb.interceptor.ClassPointcut;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditLocalHome;
import com.topcoder.timetracker.audit.ejb.AuditLocalObject;
import com.topcoder.timetracker.audit.ejb.AuditSessionBean;
import com.topcoder.timetracker.contact.ejb.AddressBean;
import com.topcoder.timetracker.contact.ejb.AddressLocal;
import com.topcoder.timetracker.contact.ejb.AddressLocalHome;
import com.topcoder.timetracker.contact.ejb.ContactBean;
import com.topcoder.timetracker.contact.ejb.ContactHomeLocal;
import com.topcoder.timetracker.contact.ejb.ContactLocal;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>This is the base test case which abstracts the common behavior.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class BaseTestCase extends TestCase {
    /**
     * <p>
     * The formatter to format the <code>Date</code> to pattern "yyyy_MM_dd hh:mm:ss".
     * </p>
     *
     * <p>
     * Validate from year to second because the Informix type of creation date and modification date
     * is DATETIME YEAR TO SECOND.
     * </p>
     */
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy_MM_dd hh:mm:ss");

    /**
     * <p>
     * The default namespace for DB Connection Factory component.
     * </p>
     */
    public static final String DBCONNECTION_FACTORY_NAMESPACE
        = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * Represents the location of the configuration file for DB Connection Factory component.
     * </p>
     */
    public static final String DB_CONFIG_LOCATION = "UnitTests" + File.separator + "DBConnectionFactory.xml";

    /**
     * <p>
     * Represents the location of the error configuration file for DB Connection Factory component.
     * </p>
     */
    public static final String DB_ERROR_CONFIG_LOCATION =
        "UnitTests" + File.separator + "DBConnectionFactory_Error.xml";

    /**
     * <p>
     * Represents the location of configuration file for Object Factory component.
     * </p>
     */
    public static final String OF_CONFIG_LOCATION = "UnitTests" + File.separator + "ObjectFactory.xml";

    /**
     * <p>
     * Represents the location of error configuration file for Object Factory component.
     * </p>
     */
    public static final String OF_ERROR_CONFIG_LOCATION = "UnitTests" + File.separator + "ObjectFactory_Error.xml";

    /**
     * <p>
     * Represents the location of configuration file for Search Builder component.
     * </p>
     */
    public static final String SB_CONFIG_LOCATION = "UnitTests" + File.separator + "SearchBundle.xml";

    /**
     * <p>
     * Represents the location of error configuration file for Search Builder component.
     * </p>
     */
    public static final String SB_ERROR_CONFIG_LOCATION = "UnitTests" + File.separator + "SearchBundle_Error.xml";

    /**
     * <p>
     * Represents the location of configuration file for DAO.
     * </p>
     */
    public static final String DAO_CONFIG_LOCATION = "UnitTests" + File.separator + "DAO.xml";

    /**
     * <p>
     * Represents the location of error configuration file for DAO.
     * </p>
     */
    public static final String DAO_ERROR_CONFIG_LOCATION = "UnitTests" + File.separator + "DAO_Error.xml";

    /**
     * <p>
     * Represents the location of configuration file for DAO.
     * </p>
     */
    public static final String MANAGER_CONFIG_LOCATION = "UnitTests" + File.separator + "Manager.xml";

    /**
     * <p>
     * Represents the location of error configuration file for DAO.
     * </p>
     */
    public static final String MANAGER_ERROR_CONFIG_LOCATION = "UnitTests" + File.separator + "Manager_Error.xml";
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    public static final String ERROR_MESSAGE = "error message";

    /**
     * <p>
     * An exception instance used as the cause exception.
     * </p>
     */
    public static final Exception CAUSE = new Exception();

    /**
     * <p>
     * A string with length greater than 100.
     * </p>
     */
    private static String sb = null;

    /**
     * <p>
     * Represents the connection factory that is used for performing CRUD operations.
     * </p>
     */
    private static DBConnectionFactory connectionFactory = null;

    /**
     * <p>
     * Sql to run to insert required information into data store.
     * </p>
     */
    private static String insertSql = null;

    /**
     * <p>Host of Informix.</p>
     */
    private static String host;

    /**
     * <p>Port number of Informix.</p>
     */
    private static int port;

    /**
     * <p>Database name of Informix.</p>
     */
    private static String database;

    /**
     * <p>Server instance name of Informix.</p>
     */
    private static String server;

    /**
     * <p>user name.</p>
     */
    private static String user;

    /**
     * <p>password.</p>
     */
    private static String password;

    /**
     * <p>
     * Whether <code>setUp()</code> has been called.
     * </p>
     */
    private static boolean setup = false;

    /**
     * <p>Naming context used to lookup data source and EJB in UnitTests.</p>
     */
    private static Context context;

    /**
     * <p>Mock EJB container.</p>
     */
    private static MockContainer mockContainer;

    static {
        try {
            Properties prop = new Properties();
            prop.load(BaseTestCase.class.getResourceAsStream("/UnitTests/Informix.properties"));
            host = prop.getProperty("host");
            port = Integer.parseInt(prop.getProperty("port"));
            database = prop.getProperty("database");
            server = prop.getProperty("server");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace(System.err);
        } catch (SecurityException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * <p>
     * Bind mock transaction to context.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void bindTransaction() throws Exception {
        MockTransaction mockTransaction = new MockTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
    }

    /**
     * <p>
     * Get transaction currently binded.
     * </p>
     *
     * @return An instance of <code>MockTransaction</code>.
     *
     * @throws NamingException to JUnit
     */
    protected MockTransaction getTransaction() throws NamingException {
        return (MockTransaction) context.lookup("javax.transaction.UserTransaction");
    }
    /**
     * <p>
     * Get DataSource currently binded.
     * </p>
     *
     * @return An instance of <code>MockDataSource</code>.
     *
     * @throws NamingException to JUnit
     */
    protected MockDataSource getDataSource() throws NamingException {
        return (MockDataSource) context.lookup("java:comp/env/jdbc/informix");
    }
    /**
     * <p>
     * Close the last JDBC connection created by the binded mock data source.
     * </p>
     */
    protected void closeLastJDBCConnections() {
        try {
            MockDataSource datasource = this.getDataSource();
            datasource.closeLastJDBCConnections();
        } catch (NamingException  e) {
            e.printStackTrace(System.err);
        }
    }
    /**
     * <p>
     * Get <code>AddressLocal</code>.
     * </p>
     *
     * @return <code>AddressLocal</code>.
     *
     * @throws Exception to JUnit.
     */
    protected AddressLocal getAddressLocal() throws Exception {
        this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS");
        this.bind("java:comp/env/AddressDAOKey", "AddressDAO");
        AddressLocalHome localHome = (AddressLocalHome) new InitialContext().lookup("java:comp/env/ejb/AddressLocal");
        return localHome.create();
    }
    /**
     * <p>
     * Get <code>ContactLocal</code>.
     * </p>
     *
     * @return <code>ContactLocal</code>.
     *
     * @throws Exception to JUnit.
     */
    protected ContactLocal getContactLocal() throws Exception {
        this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS");
        this.bind("java:comp/env/ContactDAOKey", "ContactDAO");
        ContactHomeLocal localHome = (ContactHomeLocal) new InitialContext().lookup("java:comp/env/ejb/ContactLocal");
        return localHome.create();
    }
    /**
     * <p>deploy SLSB to the mock EJB container and add transaction interceptor.</p>
     *
     * @param jndi the JNDI name to deploy
     * @param home the class of home interface
     * @param ejbObject the class of EJB object
     * @param bean the class of bean implementation
     *
     * @throws Exception to JUnit
     */
    protected void deploySessionBean(String jndi, Class home, Class ejbObject, Class bean)
        throws Exception {
        SessionBeanDescriptor descriptor = new SessionBeanDescriptor(jndi, home, ejbObject, bean);
        mockContainer.deploy(descriptor);
        AspectSystemFactory.getAspectSystem().addFirst(new ClassPointcut(bean, false),
            new TransactionManager(TransactionPolicy.REQUIRED));
    }

    /**
     * <p>bind Informix data source.</p>
     *
     * @param jndi JNDI name to bind
     * @param hostIP host IP
     * @param serverName server name
     * @param portNumber port number
     * @param userName user name
     * @param pass password
     * @param dbName database name
     *
     * @throws Exception to JUnit
     */
    private void bindDataSource(String jndi, String hostIP, String serverName, int portNumber,
        String userName, String pass, String dbName)
        throws Exception {
        MockDataSource ds = new MockDataSource();
        ds.setIfxIFXHOST(hostIP);
        ds.setServerName(serverName);
        ds.setPortNumber(portNumber);
        ds.setDatabaseName(dbName);
        ds.setUser(userName);
        ds.setPassword(pass);
        this.bind(jndi, ds);
    }

    /**
     * <p>
     * Bind given value to given JNDI name.
     * </p>
     *
     * @param name JNDI name
     * @param value value binded
     *
     * @throws Exception to JUnit
     */
    protected void bind(String name, Object value) throws Exception {
        context.rebind(name, value);
    }
    /**
     * <p>
     * Unbind given JNDI name.
     * </p>
     *
     * @param name JNDI name
     *
     * @throws Exception to JUnit
     */
    protected void unBind(String name) throws Exception {
        context.unbind(name);
    }
    /**
     * <p>
     * Execute an update clause.
     * </p>
     * @param sql An update sql clause
     *
     * @throws Exception to JUnit
     */
    protected void executeUpdate(String sql) throws Exception {
        Connection con = null;
        PreparedStatement ps1 = null;
        try {
            con = getJDBCConnection();
            ps1 = con.prepareStatement(sql);
            ps1.executeUpdate();
            con.commit();
        } finally {
            this.closeStatement(ps1);
            this.closeConnection(con);
        }
    }
    /**
     * <p>
     * Update <code>IDGenerator</code> with given next block start and set exhausted to false.
     * </p>
     *
     * @param name The name of <code>IDGenerator</code> to be updated.
     * @param nextBlockStart The value of next block start.
     *
     * @throws Exception to JUnit
     */
    protected void updateIDGenerator(String name, long nextBlockStart) throws Exception {
        Connection con = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            con = getJDBCConnection();
            ps1 = con.prepareStatement(
                "update id_sequences set next_block_start = ? where name = '" + name + "'");
            ps1.setLong(1, nextBlockStart);
            ps1.executeUpdate();
            ps2 = con.prepareStatement(
                    "update id_sequences set exhausted = 0 where name = '" + name + "'");
            ps2.executeUpdate();
            con.commit();
        } finally {
            this.closeStatement(ps1);
            this.closeStatement(ps2);
            this.closeConnection(con);
        }
    }
    /**
     * <p>
     * Exhaust given <code>IDGenerator</code>.
     * </p>
     *
     * @param name The name of <code>IDGenerator</code> to be exhausted.
     * @param exhause 0 or 1
     *
     * @throws Exception to JUnit
     */
    protected void exhaustIDGenerator(String name, int exhause) throws Exception {
        this.executeUpdate("update id_sequences set exhausted = " + exhause + " where name = '" + name + "'");
    }
    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        if (!setup) {
            setup = true;
            super.setUp();
            this.initialConfigManager();
            MockContextFactory.setAsInitial();
            context = new InitialContext();
            mockContainer = new MockContainer(context);
            this.bindTransaction();
            this.bind("java:comp/env/jdbc/wrong", new WrongDataSource());
            this.bindDataSource("java:comp/env/jdbc/informix", host, server, port, user, password, database);
            this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS");
            this.bind("java:comp/env/AddressDAOKey", "AddressDAO");
            this.bind("java:comp/env/ContactDAOKey", "ContactDAO");
            deploySessionBean(
                "java:comp/env/ejb/AddressLocal",
                AddressLocalHome.class,
                AddressLocal.class,
                AddressBean.class);
            deploySessionBean(
                "java:comp/env/ejb/ContactLocal",
                ContactHomeLocal.class,
                ContactLocal.class,
                ContactBean.class);
            deploySessionBean(
                    "java:comp/env/ejb/AuditLocal",
                    AuditLocalHome.class,
                    AuditLocalObject.class,
                    AuditSessionBean.class);
            this.setupDB();
        } else {
            super.setUp();
        }
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Create a new instance of <code>Address</code>.
     * The returned address will have type set as {@link AddressType#PROJECT}.
     * </p>
     *
     * @return A new instance of <code>Address</code> created
     */
    protected Address getAddress() {
        Address address = new Address();
        address.setAddressType(AddressType.PROJECT);
        address.setCity("HangZhou");
        long id = 1;
        Country country = new Country();
        country.setId(id);
        State state = new State();
        state.setId(id);
        address.setState(state);
        address.setCountry(country);
        address.setPostalCode("123456");
        address.setLine1("line1");
        address.setLine2("line2");
        address.setCreationUser("user");
        address.setModificationUser("user");
        return address;
    }

    /**
     * <p>
     * Get a string with length greater than 100.
     * </p>
     *
     * @return A string with length greater than 100.
     */
    protected String getStringWithLengthGreaterThan100() {
        if (sb != null) {
            return sb;
        }
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            b.append("12345678901");
        }
        sb = b.toString();
        return sb;
    }

    /**
     * <p>
     * Create a new instance of <code>Contact</code>.
     * The returned contact will have type set as {@link ContactType#PROJECT}.
     * </p>
     *
     * @return A new instance of <code>Contact</code> created
     */
    protected Contact getContact() {
        Contact contact = new Contact();
        contact.setFirstName("firstname");
        contact.setLastName("lastname");
        contact.setEmailAddress("email");
        contact.setPhoneNumber("111111");
        contact.setContactType(ContactType.PROJECT);
        contact.setCreationUser("user");
        contact.setModificationUser("user");
        return contact;
    }
    /**
     * <p>
     * Get count of entries in <em>address</em> table.
     * </p>
     * @return count of entries in <em>address</em> table.
     * @throws Exception to JUnit
     */
    protected int getAddressRecordsCount() throws Exception {
        Connection con = null;
        Statement ps = null;
        ResultSet rs = null;
        try {
            con = getJDBCConnection();
            ps = con.createStatement();
            rs = ps.executeQuery("select count(*) from address");
            rs.next();
            return rs.getInt(1);
        } finally {
            this.closeStatement(ps);
            this.closeConnection(con);
        }
    }
    /**
     * <p>
     * Get count of entries in <em>contact</em> table.
     * </p>
     * @return count of entries in <em>contact</em> table.
     * @throws Exception to JUnit
     */
    protected int getContactRecordsCount() throws Exception {
        Connection con = null;
        Statement ps = null;
        ResultSet rs = null;
        try {
            con = getJDBCConnection();
            ps = con.createStatement();
            rs = ps.executeQuery("select count(*) from contact");
            rs.next();
            return rs.getInt(1);
        } finally {
            this.closeStatement(ps);
            this.closeConnection(con);
        }
    }
    /**
     * <p>
     * Get count of entries in <em>audit</em> table.
     * </p>
     * @return count of entries in <em>audit</em> table.
     * @throws Exception to JUnit
     */
    protected int getAuditRecordsCount() throws Exception {
        Connection con = null;
        Statement ps = null;
        ResultSet rs = null;
        try {
            con = getJDBCConnection();
            ps = con.createStatement();
            rs = ps.executeQuery("select count(*) from audit");
            rs.next();
            return rs.getInt(1);
        } finally {
            this.closeStatement(ps);
            this.closeConnection(con);
        }
    }

    /**
     * <p>
     * Assert given <code>AuditHeader</code>.
     * </p>
     *
     * @param header <code>AuditHeader</code> to assert
     * @param area Desired application area
     * @param type Desired aciont type
     * @param detailsLength Desired length of details
     * @param projectId Desired project id
     * @param clientId Desired client id
     * @param companyId Desired company id
     * @param userId Desired user id
     */
    protected void assertAuditHeader(AuditHeader header, ApplicationArea area, int type, int detailsLength,
            long projectId, long clientId, long companyId, long userId) {
        assertEquals(area, header.getApplicationArea());
        assertEquals(type, header.getActionType());
        assertEquals(detailsLength, header.getDetails().length);
        assertEquals(projectId, header.getProjectId());
        assertEquals(clientId, header.getClientId());
        assertEquals(companyId, header.getCompanyId());
        assertEquals(userId, header.getResourceId());
    }

    /**
     * <p>
     * Get audit records by table name, entity id and action type.
     * </p>
     *
     * @param auditManager <code>AuditManager</code> used to get audit records
     * @param tableName table name
     * @param entityId entity id
     * @param type action type
     *
     * @return An array of <code>AuditHeader</code>
     *
     * @throws Exception to JUnit
     */
    protected AuditHeader[] getAuditRecord(AuditManager auditManager, String tableName, long entityId, int type)
        throws Exception {
        Filter filter1 = new EqualToFilter("entity_id", new Long(entityId));
        Filter filter2 = new EqualToFilter("table_name", tableName);
        Filter filter3 = new EqualToFilter("action_type", new Integer(type));
        return auditManager.searchAudit(new AndFilter(filter3, new AndFilter(filter1, filter2)));
    }

    /**
     * <p>
     * Get count of entries in <em>audit_detail</em> table.
     * </p>
     * @return count of entries in <em>audit_detail</em> table.
     * @throws Exception to JUnit
     */
    protected int getAuditDetailRecordsCount() throws Exception {
        Connection con = null;
        Statement ps = null;
        ResultSet rs = null;
        try {
            con = getJDBCConnection();
            ps = con.createStatement();
            rs = ps.executeQuery("select count(*) from audit_detail");
            rs.next();
            return rs.getInt(1);
        } finally {
            this.closeStatement(ps);
            this.closeConnection(con);
        }
    }
    /**
     * <p>
     * Closes the given Statement instance, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param statement the given Statement instance to close.
     */
    protected void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * <p>
     * Closes the given Connection instance, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param con the given Connection instance to close.
     */
    protected void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * <p>
     * Roll back the current connection if any error occurs while updating the persistence.
     * </p>
     *
     * <p>
     * Note, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param con the given Connection instance to roll back
     */
    protected void rollback(Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            // Just ignore
        }
    }

    /**
     * <p>
     * Assert the error message is properly propagated.
     * </p>
     *
     * @param be The <code>BaseException</code> to assert
     */
    protected void assertErrorMessagePropagated(BaseException be) {
        assertTrue("Error message is properly propagated to super class.", be.getMessage().indexOf(ERROR_MESSAGE) >= 0);
    }

    /**
     * <p>
     * Assert the error cause is properly propagated.
     * </p>
     *
     * @param be The <code>BaseException</code> to assert
     */
    protected void assertErrorCausePropagated(BaseException be) {
        assertEquals("The inner exception should match.", CAUSE, be.getCause());
    }
    /**
     * <p>
     * Assert two contacts equal.
     * </p>
     *
     * @param contact1 one contact
     * @param contact2 another contact
     */
    protected void assertContactEquals(Contact contact1, Contact contact2) {
        assertEquals(contact1.getId(), contact2.getId());
        assertEquals(contact1.getEmailAddress(), contact2.getEmailAddress());
        assertEquals(contact1.getFirstName(), contact2.getFirstName());
        assertEquals(contact1.getLastName(), contact2.getLastName());
        assertEquals(contact1.getPhoneNumber(), contact2.getPhoneNumber());
        assertEquals(contact1.getCreationUser(), contact2.getCreationUser());
        assertEquals(contact1.getModificationUser(), contact2.getModificationUser());
        assertEquals(FORMATTER.format(contact1.getModificationDate()),
                FORMATTER.format(contact2.getModificationDate()));
        assertEquals(FORMATTER.format(contact1.getCreationDate()), FORMATTER.format(contact2.getCreationDate()));
    }
    /**
     * <p>
     * Assert two addresses equal.
     * </p>
     *
     * @param address1 one address
     * @param address2 another address
     */
    protected void assertAddressEquals(Address address1, Address address2) {
        assertEquals(address1.getId(), address2.getId());
        assertEquals(address1.getCity(), address2.getCity());
        assertEquals(address1.getCountry(), address2.getCountry());
        assertEquals(address1.getState(), address2.getState());
        assertEquals(address1.getLine1(), address2.getLine1());
        assertEquals(address1.getLine2(), address2.getLine2());
        assertEquals(address1.getPostalCode(), address2.getPostalCode());
        assertEquals(FORMATTER.format(address1.getCreationDate()), FORMATTER.format(address2.getCreationDate()));
        assertEquals(address1.getCreationUser(), address2.getCreationUser());
        assertEquals(FORMATTER.format(address1.getModificationDate()),
            FORMATTER.format(address2.getModificationDate()));
        assertEquals(address1.getModificationUser(), address2.getModificationUser());
    }

    /**
     * <p>
     * Assert the transaction was commited.
     * </p>
     *
     * @param transaction to assert
     */
    protected void assertTransactionCommited(MockTransaction transaction) {
        assertTrue("Transaction should be commited", transaction.wasCommitCalled());
    }

    /**
     * <p>
     * Assert the transaction was rolled back.
     * </p>
     *
     * @param transaction to assert
     */
    protected void assertTransactionRollbacked(MockTransaction transaction) {
        assertTrue("Transaction should be rolled back", transaction.wasRollbackOnlyCalled());
    }

    /**
     * <p>
     * Assert the count of audit records.
     * </p>
     *
     * @param preAuditCount count of audit records previously.
     * @param increase the desired increased count.
     *
     * @return new count of audit records.
     *
     * @throws Exception to JUnit
     */
    protected int assertAuditRecordsCount(int preAuditCount, int increase) throws Exception {
        int newCount = this.getAuditRecordsCount();
        assertEquals(increase, newCount - preAuditCount);
        return newCount;
    }
    /**
     * <p>
     * Assert the count of address records.
     * </p>
     *
     * @param preAddressCount count of address records previously.
     * @param increase the desired increased count.
     *
     * @return new count of address records.
     *
     * @throws Exception to JUnit
     */
    protected int assertAddressRecordsCount(int preAddressCount, int increase) throws Exception {
        int newCount = this.getAddressRecordsCount();
        assertEquals(increase, newCount - preAddressCount);
        return newCount;
    }
    /**
     * <p>
     * Assert the count of contact records.
     * </p>
     *
     * @param preContactCount count of contact records previously.
     * @param increase the desired increased count.
     *
     * @return new count of contact records.
     *
     * @throws Exception to JUnit
     */
    protected int assertContactRecordsCount(int preContactCount, int increase) throws Exception {
        int newCount = this.getContactRecordsCount();
        assertEquals(increase, newCount - preContactCount);
        return newCount;
    }
    /**
     * <p>
     * Assert the count of audit detail records.
     * </p>
     *
     * @param preCount count of audit detail records previously.
     * @param increase the desired increased count.
     *
     * @return new count of audit detail records.
     *
     * @throws Exception to JUnit
     */
    protected int assertAuditDetailRecordsCount(int preCount, int increase) throws Exception {
        int newCount = this.getAuditDetailRecordsCount();
        assertEquals(increase, newCount - preCount);
        return newCount;
    }

    /**
     * <p>
     * Get value of given <code>Field</code> of given <code>Object</code>.
     * </p>
     *
     * @param object instance to get field from
     * @param fieldName name of field
     *
     * @return value of field
     *
     * @throws Exception to JUnit
     */
    protected Object getField(Object object, String fieldName) throws Exception {
        Field f = object.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(object);
    }

    /**
     * <p>
     * Initial the <code>ConfigManager</code> with the configuration files in the test_files.
     * </p>
     *
     * @return initialized ConfigManager instance
     *
     * @throws ConfigManagerException to JUnit
     */
    protected ConfigManager initialConfigManager()
        throws ConfigManagerException {
        removeConfigManagerNS();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add(DB_CONFIG_LOCATION);
        cm.add(DB_ERROR_CONFIG_LOCATION);
        cm.add(OF_CONFIG_LOCATION);
        cm.add(OF_ERROR_CONFIG_LOCATION);
        cm.add(SB_CONFIG_LOCATION);
        cm.add(SB_ERROR_CONFIG_LOCATION);
        cm.add(DAO_CONFIG_LOCATION);
        cm.add(DAO_ERROR_CONFIG_LOCATION);
        cm.add(MANAGER_CONFIG_LOCATION);
        cm.add(MANAGER_ERROR_CONFIG_LOCATION);

        //Add Audit Config
        String prefix = "UnitTests" + File.separator + "AuditConfig" + File.separator;
        cm.add(prefix + "AuditDelegate_Config.xml");
        cm.add(prefix + "AuditSessionBean_Config.xml");
        cm.add(prefix + "DefaultValuesContainer_Config.xml");
        cm.add(prefix + "InformixAuditPersistence_Config.xml");
        cm.add(prefix + "Logging_Wrapper.xml");
        cm.add(prefix + "SearchBuilder_Config.xml");
        cm.add("com.topcoder.naming.jndiutility", prefix + "JNDIUtils.properties",
                ConfigManager.CONFIG_PROPERTIES_FORMAT);
        return cm;
    }

    /**
     * <p>Remove all namespaces from config manager.</p>
     *
     * @throws ConfigManagerException to JUnit
     */
    protected void removeConfigManagerNS()
        throws ConfigManagerException {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>Get JDBC connection.</p>
     *
     * @return Connection
     *
     * @throws Exception If error occurred while retrieving connection.
     */
    protected Connection getJDBCConnection() throws Exception {
        try {
            if (connectionFactory == null) {
                connectionFactory = new DBConnectionFactoryImpl(DBCONNECTION_FACTORY_NAMESPACE);
            }

            Connection con = connectionFactory.createConnection("InformixJDBCConnection");
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            return con;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw e;
        }
    }

    /**
     * <p>Get JNDI connection.</p>
     *
     * @return Connection
     *
     * @throws Exception If error occurred while retrieving connection.
     */
    protected Connection getJNDIConnection() throws Exception {
        if (connectionFactory == null) {
            connectionFactory = new DBConnectionFactoryImpl(DBCONNECTION_FACTORY_NAMESPACE);
        }

        Connection con = connectionFactory.createConnection("InformixJNDIConnection");

        return con;
    }
    /**
     * <p>Remove db information.</p>
     *
     * @throws Exception to JUnit
     */
    protected void wrapDB() throws Exception {
        Connection conn = getJDBCConnection();

        Statement ps = conn.createStatement();

        try {
            //wrap audit related data
            ps.addBatch("delete from audit_detail");
            ps.addBatch("delete from audit");
            ps.addBatch("delete from application_area");
            ps.addBatch("delete from project");
            ps.addBatch("delete from client");
            ps.addBatch("delete from user_account");
            ps.addBatch("delete from account_status");
            ps.addBatch("delete from company");

            //wrap address related date
            ps.addBatch("delete from address_relation");
            ps.addBatch("delete from address");
            ps.addBatch("delete from address_type");
            ps.addBatch("delete from country_name");
            ps.addBatch("delete from state_name");

            //wrap contact related data
            ps.addBatch("delete from contact_relation");
            ps.addBatch("delete from contact_type");
            ps.addBatch("delete from contact");

            //wrap id sequences used in test case
            ps.addBatch("delete from id_sequences where name = 'AddressIDGenerator'");
            ps.addBatch("delete from id_sequences where name = 'ContactIDGenerator'");
            ps.addBatch("delete from id_sequences where name = 'AuditIDGenerator'");
            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            closeStatement(ps);
            closeConnection(conn);
        }
    }

    /**
     * <p>Set up required db information to run Unit tests.</p>
     *
     * @throws Exception to JUnit
     */
    protected void setupDB() throws Exception {
        Statement ps = null;
        Connection conn = null;

        try {
            wrapDB();

            if (insertSql == null) {
                insertSql = "";

                InputStream is =
                    this.getClass().getResourceAsStream("/UnitTests/data.sql");
                BufferedReader in = new BufferedReader(new InputStreamReader(is));

                String s = in.readLine();

                while (s != null) {
                    insertSql += s;
                    s = in.readLine();
                }

                in.close();
            }

            conn = getJDBCConnection();

            StringTokenizer st = new StringTokenizer(insertSql, ";");

            ps = conn.createStatement();

            while (st.hasMoreTokens()) {
                ps.addBatch(st.nextToken());
            }

            ps.executeBatch();
            conn.commit();

        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            closeStatement(ps);
            closeConnection(conn);
        }
    }

}
