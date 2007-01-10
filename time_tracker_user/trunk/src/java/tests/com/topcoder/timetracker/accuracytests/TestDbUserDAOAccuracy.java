/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.accuracytests;

import java.util.Date;

import com.topcoder.timetracker.common.Address;
import com.topcoder.timetracker.common.Contact;
import com.topcoder.timetracker.common.EncryptionRepository;
import com.topcoder.timetracker.common.State;
import com.topcoder.timetracker.user.AccountStatus;
import com.topcoder.timetracker.user.DbUserDAO;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserSearchBuilder;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>DbUserDAO</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestDbUserDAOAccuracy extends TestCase {

    /**
     * Represents the DbUserDAO instance for test.
     */
    private DbUserDAO test = null;

    /**
     * Represents the connectName.
     */
    private static final String connectName = "sysuser";

    /**
     * Represents the id name.
     */
    private static final String idName = "sysuser";

    /**
     * The algorithm name.
     */
    private static final String alg = "simple";

    /**
     * Set up the enviroment. Create DbUserDAO instance.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        Util.clearNamespace();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add("accuracytests/DBConnectionFactory.xml");
        cm.add("accuracytests/Logging.xml");

        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        EncryptionRepository.getInstance().registerAlgorithm("simple", new SimpleAlgorithm());

        Util.insertRecordsIntoState_NameTable(100);

        Util.insertRecordsIntoCompanyTable(1);
        Util.insertRecordsIntoCompanyTable(100);

        test = new DbUserDAO(factory, connectName, alg, idName);
    }

    /**
     * Tear down the enviroment.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {

        Util.clearTables();

        Util.clearNamespace();

        test = null;
    }

    /**
     * Test constructor.
     *
     */
    public void testDbUserDAO() {
        assertNotNull("Should not be null.", test);
    }

    /**
     * Test method <code>User createUser(User user, String username) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateUser() throws Exception {
        User user = createUser(1);

        User ret = test.createUser(user, "topcoder");

        assertEquals("Equal is expected.", user, ret);
    }

    /**
     * Test method <code>User retrieveUser(long id) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testRetrieveUser() throws Exception {
        User user = createUser(1);

        test.createUser(user, "topcoder");
        User ret = test.retrieveUser(user.getId());

        assertEquals("Equal is expected.", user, ret);
    }

    /**
     * Test method <code>void updateUser(User user, String username) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateUser() throws Exception {
        User user = createUser(1);

        test.createUser(user, "topcoder");

        user.setEmailAddress("ivern@topcoder.com");

        // update.
        test.updateUser(user, "tc");

        User ret = test.retrieveUser(user.getId());

        assertEquals("Equal is expected.", "ivern@topcoder.com", ret.getEmailAddress());
    }

    /**
     * Test method <code>void deleteUser(User user) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteUser() throws Exception {
        User user = createUser(1);

        User ret = test.createUser(user, "topcoder");
        assertNotNull("Should not be null.", ret);

        // delete the user.

        test.deleteUser(user);

        ret = test.retrieveUser(user.getId());

        assertNull("Should be null.", ret);
    }

    /**
     * Test method <code> User[] listUsers() </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testListUsers() throws Exception {
        User user = createUser(1);

        User ret = test.createUser(user, "topcoder");

        User[] users = test.listUsers();

        assertEquals("Equal is expected.", 1, users.length);

        assertEquals("Equal is expected.", user, users[0]);
    }

    /**
     * Test method <code> User[] searchUsers(Filter filter) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchUsers_1() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        Date start = new Date(System.currentTimeMillis() - 1000000000);
        Date end = new Date(System.currentTimeMillis() + 1000000000);

        builder.createdWithinDateRange(start, end);

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        // one user will match.
        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method <code> User[] searchUsers(Filter filter) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchUsers_2() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        Date start = new Date(System.currentTimeMillis() - 1000000000);
        Date end = new Date(System.currentTimeMillis() - 1000000);

        builder.createdWithinDateRange(start, end);

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        // no user will match.
        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method <code> User[] searchUsers(Filter filter) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchUsers_3() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        Date start = new Date(System.currentTimeMillis() + 100000);
        Date end = new Date(System.currentTimeMillis() + 100000000);

        builder.createdWithinDateRange(start, end);

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        // no user will match.
        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method <code>User[] createUsers(User[] users, String username, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateUsers_1() throws Exception {
        User[] users = new User[2];
        users[0] = createUser(1);

        users[1] = createUser(100);

        User[] ret = test.createUsers(users, "topcoder", true);

        assertEquals("Equal is expected.", 2, ret.length);
    }

    /**
     * Test method <code>User[] createUsers(User[] users, String username, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateUsers_2() throws Exception {
        User[] users = new User[2];
        users[0] = createUser(1);

        users[1] = createUser(100);

        User[] ret = test.createUsers(users, "topcoder", false);

        assertEquals("Equal is expected.", 2, ret.length);
    }

    /**
     * Test method <code>User[] retrieveUsers(long[] ids) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testRetrieveUsers_1() throws Exception {
        User[] users = new User[2];
        users[0] = createUser(1);

        users[1] = createUser(100);

        User[] ret = test.createUsers(users, "topcoder", false);

        long[] ids = new long[2];
        ids[0] = users[0].getId();
        ids[1] = users[1].getId();

        ret = test.retrieveUsers(ids);

        assertEquals("Equal is expected.", 2, ret.length);

        assertEquals("Equal is expected.", users[0], ret[0]);
        assertEquals("Equal is expected.", users[1], ret[1]);
    }

    /**
     * Test method <code>User[] retrieveUsers(long[] ids) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testRetrieveUsers_2() throws Exception {
        User[] users = new User[2];
        users[0] = createUser(1);

        users[1] = createUser(100);

        User[] ret = test.createUsers(users, "topcoder", true);

        long[] ids = new long[2];
        ids[0] = users[0].getId();
        ids[1] = users[1].getId();

        ret = test.retrieveUsers(ids);

        assertEquals("Equal is expected.", 2, ret.length);

        assertEquals("Equal is expected.", users[0], ret[0]);
        assertEquals("Equal is expected.", users[1], ret[1]);
    }

    /**
     * Test method <code>void updateUsers(User[] users, String username, boolean atomicBatchMode)</code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testUpdateUsers_1() throws Exception {
        User[] users = new User[2];
        users[0] = createUser(1);

        users[1] = createUser(100);

        User[] ret = test.createUsers(users, "topcoder", true);

        users[0].setEmailAddress("fish@yahoo.com");

        users[1].setEmailAddress("bigfish@yahoo.com");

        // update.
        test.updateUsers(users, "reviewer", true);

        long[] ids = new long[2];
        ids[0] = users[0].getId();
        ids[1] = users[1].getId();

        // get back the users.
        ret = test.retrieveUsers(ids);

        assertEquals("Equal is expected.", 2, ret.length);
        assertEquals("Equal is expected.", "fish@yahoo.com", ret[0].getEmailAddress());
        assertEquals("Equal is expected.", "bigfish@yahoo.com", ret[1].getEmailAddress());

    }

    /**
     * Test method <code>void updateUsers(User[] users, String username, boolean atomicBatchMode)</code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testUpdateUsers_2() throws Exception {
        User[] users = new User[2];
        users[0] = createUser(1);

        users[1] = createUser(100);

        User[] ret = test.createUsers(users, "topcoder", false);

        users[0].setEmailAddress("fish@yahoo.com");

        users[1].setEmailAddress("bigfish@yahoo.com");

        // update.
        test.updateUsers(users, "reviewer", true);

        long[] ids = new long[2];
        ids[0] = users[0].getId();
        ids[1] = users[1].getId();

        // get back the users.
        ret = test.retrieveUsers(ids);

        assertEquals("Equal is expected.", 2, ret.length);
        assertEquals("Equal is expected.", "fish@yahoo.com", ret[0].getEmailAddress());
        assertEquals("Equal is expected.", "bigfish@yahoo.com", ret[1].getEmailAddress());

    }

    /**
     * Test method <code>void deleteUsers(User[] users, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteUsers_1() throws Exception {

        User[] users = new User[2];
        users[0] = createUser(1);

        users[1] = createUser(100);

        User[] ret = test.createUsers(users, "topcoder", false);

        assertNotNull("Should not be null.", ret);

        // delete
        test.deleteUsers(users, true);

        long[] ids = new long[2];
        ids[0] = users[0].getId();
        ids[1] = users[1].getId();

        ret = test.retrieveUsers(ids);
        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method <code>void deleteUsers(User[] users, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteUsers_2() throws Exception {

        User[] users = new User[2];
        users[0] = createUser(1);

        users[1] = createUser(100);

        User[] ret = test.createUsers(users, "topcoder", true);

        assertNotNull("Should not be null.", ret);

        // delete
        test.deleteUsers(users, true);

        long[] ids = new long[2];
        ids[0] = users[0].getId();
        ids[1] = users[1].getId();

        ret = test.retrieveUsers(ids);
        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Creates the User object with all required fields set.
     *
     * @param companyId
     *            the company id to be used.
     * @return the User instance.
     */
    private User createUser(long companyId) {
        AccountStatus status = new AccountStatus();
        status.setId(1);

        Address address = createAddress(companyId);

        Contact contact = createContact();

        User user = new User();
        user.setAccountStatus(status);
        user.setCompanyId(companyId);
        user.setContactInfo(contact);
        user.setAddress(address);
        user.setAlgorithmName("simple");

        user.setPassword("password");

        user.setUsername("tc_reviewer");

        return user;
    }

    /**
     * Creates the Contact object with the values set.
     *
     * @return the Contact object.
     */
    private Contact createContact() {
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
     * @param id
     *            the address id.
     * @return the Address object.
     */
    private Address createAddress(long id) {
        Address address = new Address();
        address.setId(id);
        address.setCity("NY");
        address.setLine1("line1");
        address.setZipCode("510275");

        address.setState(createState());
        return address;
    }

    /**
     * Creates the State object with the values set.
     *
     * @return the State object.
     */
    private State createState() {
        State state = new State();
        state.setId(100);
        state.setAbbreviation("NY");
        state.setName("New York");
        state.setChanged(false);
        return state;
    }
}
