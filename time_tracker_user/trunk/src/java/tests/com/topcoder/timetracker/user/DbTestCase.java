/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import com.topcoder.timetracker.common.Address;
import com.topcoder.timetracker.common.Contact;
import com.topcoder.timetracker.common.RejectEmail;
import com.topcoder.timetracker.common.RejectReason;
import com.topcoder.timetracker.common.State;
import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.company.Company;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * Base class for all database tests. It contains common test helper methods.
 *
 * @author kr00tki
 * @version 2.0
 */
public class DbTestCase extends MyTestCase {
    /**
     * The ID Generator name used in tests.
     */
    protected static final String IDGEN_NAME = "ttu2";

    /**
     * Database connection name.
     */
    protected static final String CONNECTION_NAME = "ttu2";

    /**
     * The creation user constant.
     */
    protected static final String CREATION_USER = "admin_create";

    /**
     * The modification user constant.
     */
    protected static final String MODIFICATION_USER = "admin_update";

    /**
     * Db Connection Factory namespace.
     */
    private static final String DB_CONN_FACTORY_NAMESPACE = DBConnectionFactoryImpl.class.getName();

    /**
     * Db Connection Factory config file.
     */
    private static final String DB_CONN_FACTORY_CONF = "db_conf.xml";

    /**
     * The DBManager used to manipulate on database.
     */
    protected DbManager testDBManager = null;

    /**
     * The DBConnectionFactory instance used in tests.
     */
    protected DBConnectionFactory factory = null;

    /**
     * Db connection.
     */
    private Connection connection = null;

    /**
     * ResultSet instance.
     */
    private ResultSet resultSet = null;

    /**
     * Statement instance.
     */
    private Statement statement = null;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        ConfigManager.getInstance().add(DB_CONN_FACTORY_CONF);
        ConfigManager.getInstance().add("db_manager_conf.xml");
        ConfigManager.getInstance().add("Logging.xml");
        testDBManager = new DbManager();
        testDBManager.clearTables();
        testDBManager.loadDataSet("test_files/dataset.xml");
        factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        //clearTables();
    }

    /**
     * Clears after test.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        //clearTables();
        testDBManager.clearTables();
        testDBManager.release();

        if (resultSet != null) {
            resultSet.close();
            resultSet = null;
        }

        if (statement != null) {
            statement.close();
            statement = null;
        }

        if (connection != null) {
            connection.close();
            connection = null;
        }
        super.tearDown();
    }

    /**
     * Creates db connection.
     *
     * @return db connection.
     * @throws Exception to JUnit.
     */
    protected Connection getConnection() throws Exception {
        if (connection == null) {
            connection = new DBConnectionFactoryImpl(DB_CONN_FACTORY_NAMESPACE).createConnection(CONNECTION_NAME);
        }

        return connection;
    }

    /**
     * Returns ResultSet for given query.
     *
     * @param query db query.
     * @return query resul
     * @throws Exception to JUnit.
     */
    protected ResultSet getResultSet(String query) throws Exception {
        Connection conn = getConnection();
        statement = conn.createStatement();
        resultSet = statement.executeQuery(query);
        return resultSet;
    }

    /**
     * Asserts test by query.
     *
     * @param assertionMessage assertion message.
     * @param binarySQLQuery SQL query that should return something or not.
     * @param expected expected true or false.
     * @throws Exception to JUnit.
     */
    protected void assertByQuery(String assertionMessage, String binarySQLQuery, boolean expected)
        throws Exception {
        ResultSet rs = getResultSet(binarySQLQuery);
        assertEquals(assertionMessage, expected, rs.next());
    }

    /**
     * Helper method to execute SQL command.
     *
     * @param sqlQuery query.
     * @throws Exception to JUnit.
     */
    protected void execute(String sqlQuery) throws Exception {
        statement = getConnection().createStatement();
        statement.execute(sqlQuery);
    }

    /**
     * Creates the User object with all required fields set.
     *
     * @param companyId the company id to be used.
     * @return the User instance.
     */
    protected User createUser(long companyId) {
        AccountStatus status = new AccountStatus();
        status.setId(1);
        Address address = createAddress();
        Contact contact = createContact();

        User user = new User();
        user.setAccountStatus(status);
        user.setCompanyId(companyId);
        user.setContactInfo(contact);
        user.setAddress(address);
        user.setAlgorithmName(ALGORITHM_NAME);
        user.setPassword("test");
        user.setUsername("username");

        return user;
    }

    /**
     * Creates the Contact object with the values set.
     *
     * @return the Contact object.
     */
    protected Contact createContact() {
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
    protected Address createAddress() {
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
    protected State createState() {
        State state = new State();
        state.setId(1);
        state.setAbbreviation("NC");
        state.setName("North Carolina");
        state.setChanged(false);
        return state;
    }

    /**
     * Checks if given user instances are equal. It compares all the fields.
     *
     * @param message the error message.
     * @param expected the expected result.
     * @param actual the actual result.
     */
    protected void assertEquals(String message, User expected, User actual) {
        assertEquals(message + " Id.", expected.getId(), actual.getId());
        assertEquals(message + " company.", expected.getCompanyId(), actual.getCompanyId());
        assertEquals(message + " username.", expected.getUsername(), actual.getUsername());
        assertEquals(message + " password.", expected.getPassword(), actual.getPassword());

        assertEquals(message + " Address.", expected.getAddress(), actual.getAddress());
        assertEquals(message + " Account status.", expected.getAccountStatus().getId(),
                actual.getAccountStatus().getId());
        assertEquals(message + " Contact.", expected.getContactInfo(), actual.getContactInfo());
        assertEquals(message, (TimeTrackerBean) expected, (TimeTrackerBean) actual);
    }

    /**
     * Checks if given reject reasons instances are equal. It compares all the fields.
     *
     * @param message the error message.
     * @param expected the expected result.
     * @param actual the actual result.
     */
    protected void assertEquals(String message, RejectReason expected, RejectReason actual) {
        assertEquals(message + " Id.", expected.getId(), actual.getId());
        assertEquals(message + " description.", expected.getDescription(), actual.getDescription());
        assertEquals(message + " company.", expected.getCompanyId(), actual.getCompanyId());
        assertEquals(message + " active.", expected.isActive(), actual.isActive());

        assertEquals(message, (TimeTrackerBean) expected, (TimeTrackerBean) actual);
    }

    /**
     * Checks if given reject email instances are equal. It compares all the fields.
     *
     * @param message the error message.
     * @param expected the expected result.
     * @param actual the actual result.
     */
    protected void assertEquals(String message, RejectEmail expected, RejectEmail actual) {
        assertEquals(message + " Id.", expected.getId(), actual.getId());
        assertEquals(message + " body.", expected.getBody(), actual.getBody());
        assertEquals(message + " company.", expected.getCompanyId(), actual.getCompanyId());

        assertEquals(message, (TimeTrackerBean) expected, (TimeTrackerBean) actual);
    }

    /**
     * Checks if given companies instances are equal. It compares all the fields.
     *
     * @param message the error message.
     * @param expected the expected result.
     * @param actual the actual result.
     */
    protected void assertEquals(String message, Company expected, Company actual) {
        assertEquals(message + " Id.", expected.getId(), actual.getId());
        assertEquals(message + " name.", expected.getCompanyName(), actual.getCompanyName());
        assertEquals(message + " passcode.", expected.getPasscode(), actual.getPasscode());

        assertEquals(message + " Address.", expected.getAddress(), actual.getAddress());
        assertEquals(message + " Contact.", expected.getContact(), actual.getContact());
        assertEquals(message, (TimeTrackerBean) expected, (TimeTrackerBean) actual);
    }

    /**
     * Checks if given contact instances are equal. It compares all the fields.
     *
     * @param message the error message.
     * @param expected the expected result.
     * @param actual the actual result.
     */
    protected void assertEquals(String message, Contact expected, Contact actual) {
        assertEquals(message + " Id.", expected.getId(), actual.getId());
        assertEquals(message + " first name.", expected.getFirstName(), actual.getFirstName());
        assertEquals(message + " last name.", expected.getLastName(), actual.getLastName());
        assertEquals(message + " phone.", expected.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(message + " email.", expected.getEmailAddress(), actual.getEmailAddress());

        assertEquals(message, (TimeTrackerBean) expected, (TimeTrackerBean) actual);
    }

    /**
     * Checks if given addresses instances are equal. It compares all the fields.
     *
     * @param message the error message.
     * @param expected the expected result.
     * @param actual the actual result.
     */
    protected void assertEquals(String message, Address expected, Address actual) {
        assertEquals(message + " Id.", expected.getId(), actual.getId());
        assertEquals(message + " line1.", expected.getLine1(), actual.getLine1());
        assertEquals(message + " line2.", expected.getLine2(), actual.getLine2());
        assertEquals(message + " city.", expected.getCity(), actual.getCity());
        assertEquals(message + " zipcode.", expected.getZipCode(), actual.getZipCode());

        assertEquals(message + " State.", expected.getState(), actual.getState());
        assertEquals(message, (TimeTrackerBean) expected, (TimeTrackerBean) actual);
    }

    /**
     * Checks if given states instances are equal. It compares all the fields.
     *
     * @param message the error message.
     * @param expected the expected result.
     * @param actual the actual result.
     */
    protected void assertEquals(String message, State expected, State actual) {
        assertEquals(message + " Id.", expected.getId(), actual.getId());
        assertEquals(message + " name.", expected.getName(), actual.getName());
        assertEquals(message + " abb.", expected.getAbbreviation(), actual.getAbbreviation());
    }

    /**
     * Checks if given bease instances are equal. It compares the users and dates (up to seconds).
     *
     * @param message the error message.
     * @param expected the expected result.
     * @param actual the actual result.
     */
    protected void assertEquals(String message, TimeTrackerBean expected, TimeTrackerBean actual) {
        assertEquals(message + " creation user.", expected.getCreationUser(), actual.getCreationUser());
        assertEquals(message + " modification user.", expected.getModificationUser(), actual.getModificationUser());
        assertDateToSec(message + ". mod date.", expected.getModificationDate(), actual.getModificationDate());
        assertDateToSec(message + ". creation date.", expected.getCreationDate(), actual.getCreationDate());
    }

    /**
     * Compares the given dates up to the secounds.
     *
     * @param message the error message.
     * @param expected the expected result.
     * @param actual the actual result.
     */
    protected void assertDateToSec(String message, Date expected, Date actual) {
        Calendar calExp = Calendar.getInstance();
        calExp.setTime(expected);
        calExp.set(Calendar.MILLISECOND, 0);

        Calendar calAct = Calendar.getInstance();
        calAct.setTime(actual);
        calAct.set(Calendar.MILLISECOND, 0);

        assertEquals(message, calExp.getTime().getTime(), calAct.getTime().getTime());

    }

}
