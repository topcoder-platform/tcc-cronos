/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import java.util.Date;

import com.cronos.timetracker.common.Address;
import com.cronos.timetracker.common.Contact;
import com.cronos.timetracker.common.EncryptionRepository;
import com.cronos.timetracker.common.State;
import com.cronos.timetracker.user.AccountStatus;
import com.cronos.timetracker.user.DbUserDAO;
import com.cronos.timetracker.user.User;
import com.cronos.timetracker.user.UserSearchBuilder;
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
public class TestSearchUser extends TestCase {

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
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_1() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        Date start = new Date(System.currentTimeMillis() - 1000000000);
        Date end = new Date(System.currentTimeMillis() + 1000000000);

        builder.modifiedWithinDateRange(start, end);

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        // one user will match.
        assertEquals("Equal is expected.", 1, ret.length);

    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_2() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.createdByUser("topcoder");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_3() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.createdByUser("tc");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_4() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.modifiedByUser("topcoder");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_5() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.modifiedByUser("tc");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_6() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasCity("NY");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_7() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasCity("new York");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_8() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasEmail("not exist email");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_9() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasEmail("email");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_10() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasFirstName("jan");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_11() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasFirstName("john");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_12() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasLastName("lastName");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_13() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasLastName("marian");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_14() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasPassword("password");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_15() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasPassword("psw");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_16() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasUsername("tc_reviewer");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_17() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasUsername("user_name");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_18() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasZipCode("510275");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_19() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasZipCode("123456");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchUser.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchUsers_20() throws Exception {

        User user = createUser(1);

        test.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        builder.hasState("not exist");

        Filter filter = builder.buildFilter();

        User[] ret = test.searchUsers(filter);

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
