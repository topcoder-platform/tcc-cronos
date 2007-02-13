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
import com.topcoder.timetracker.user.DbUserAuthenticator;
import com.topcoder.timetracker.user.DbUserDAO;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserManager;
import com.topcoder.timetracker.user.UserSearchBuilder;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authenticationfactory.AbstractAuthenticator;
import com.topcoder.security.authenticationfactory.Principal;
import com.topcoder.security.authenticationfactory.Response;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.security.authorization.persistence.GeneralSecurityRole;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>UserManager</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestUserManagerAccuracy extends TestCase {

    /**
     * Represents the UserManager instance for test.
     */
    private UserManager manager = null;

    /**
     * Represents the DbUserDAO instance for test.
     */
    private DbUserDAO db = null;

    /**
     * The AbstractAuthenticator used in tests.
     */
    private AbstractAuthenticator authenticator = null;

    /**
     * The AuthorizationPersistence used in tests.
     */
    private AuthorizationPersistence authzPersistence = null;

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
        cm.add("accuracytests/authorization.xml");
        cm.add("accuracytests/config.xml");

        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        EncryptionRepository.getInstance().registerAlgorithm("simple", new SimpleAlgorithm());

        // insert three role.
        Util.insertRecordToTableRole(1, "topcoder");
        Util.insertRecordToTableRole(2, "reviewer");
        Util.insertRecordToTableRole(3, "accuracy");

        Util.insertRecordsIntoState_NameTable(100);

        Util.insertRecordsIntoCompanyTable(1);
        Util.insertRecordsIntoCompanyTable(100);

        db = new DbUserDAO(factory, connectName, alg, idName);

        authenticator = new DbUserAuthenticator("com.topcoder.timetracker.user.DbUserAuthenticator");
        authzPersistence = new SQLAuthorizationPersistence("SQLAuthorizationPersistence");

        manager = new UserManager(db, authenticator, authzPersistence);
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

        db = null;

        authenticator = null;

        authzPersistence = null;

        manager = null;

    }

    /**
     * Test constructor.
     *
     */
    public void testUserManager() {
        assertNotNull("Should not be null.", manager);
    }

    /**
     * Test method <code>User createUser(User user, String username) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateUser() throws Exception {
        User user = createUser(1);

        manager.createUser(user, "tc");

        User ret = manager.retrieveUser(user.getId());

        assertEquals("Equal is expected.", user, ret);
    }

    /**
     * Test method <code>User retrieveUser(long id) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRetrieveUser() throws Exception {

        User user = createUser(100);

        manager.createUser(user, "reviewer");

        User ret = manager.retrieveUser(user.getId());

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
        manager.createUser(user, "topcoder");

        user.setEmailAddress("fishing@yahoo.com");

        manager.updateUser(user, "topcoder");

        User ret = manager.retrieveUser(user.getId());

        assertEquals("Equal is expected.", "fishing@yahoo.com", ret.getEmailAddress());
    }

    /**
     * Test method <code>void deleteUser(User user) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteUser() throws Exception {
        User user = createUser(1);
        manager.createUser(user, "topcoder");

        manager.deleteUser(user);

        User ret = manager.retrieveUser(user.getId());

        assertNull("Should be null.", ret);
    }

    /**
     * Test method <code>User[] listUsers() </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testListUsers() throws Exception {

        User user = createUser(1);
        manager.createUser(user, "topcoder");

        User[] ret = manager.listUsers();
        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method <code> User[] searchUsers(Filter filter) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchUsers_1() throws Exception {

        User user = createUser(1);

        manager.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        Date start = new Date(System.currentTimeMillis() - 1000000000);
        Date end = new Date(System.currentTimeMillis() + 1000000000);

        builder.createdWithinDateRange(start, end);

        Filter filter = builder.buildFilter();

        User[] ret = manager.searchUsers(filter);

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

        manager.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        Date start = new Date(System.currentTimeMillis() - 1000000000);
        Date end = new Date(System.currentTimeMillis() - 1000000);

        builder.createdWithinDateRange(start, end);

        Filter filter = builder.buildFilter();

        User[] ret = manager.searchUsers(filter);

        // no user will match.
        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * manager method <code> User[] searchUsers(Filter filter) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchUsers_3() throws Exception {

        User user = createUser(1);

        manager.createUser(user, "topcoder");

        UserSearchBuilder builder = new UserSearchBuilder("simple");

        Date start = new Date(System.currentTimeMillis() + 100000);
        Date end = new Date(System.currentTimeMillis() + 100000000);

        builder.createdWithinDateRange(start, end);

        Filter filter = builder.buildFilter();

        User[] ret = manager.searchUsers(filter);

        // no user will match.
        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method <code>Response authenticateUser(String username, String password)  </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testAuthenticateUser_1() throws Exception {
        Principal principal = new Principal("1");
        principal.addMapping(DbUserAuthenticator.USERNAME_KEY, "user");
        principal.addMapping(DbUserAuthenticator.PASSWORD_KEY, "psw");

        Response response = manager.authenticateUser("user", "psw");

        // no password would be retrieved from the database. so false returned.
        assertFalse("False is expected.", response.isSuccessful());
    }

    /**
     * Test method <code>Response authenticateUser(String username, String password)  </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testAuthenticateUser_2() throws Exception {
        Principal principal = new Principal("1");
        principal.addMapping(DbUserAuthenticator.USERNAME_KEY, "user");
        principal.addMapping(DbUserAuthenticator.PASSWORD_KEY, "psw");

        Util.insertIntoTable_User_Account(1, 1, "user", "psw");

        Response response = manager.authenticateUser("user", "psw");

        // password would be retrieved from the database. so true returned.
        assertTrue("True is expected.", response.isSuccessful());
    }

    /**
     * Test method <code>Response authenticateUser(String username, String password)  </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testAuthenticateUser_3() throws Exception {
        Principal principal = new Principal("1");
        principal.addMapping(DbUserAuthenticator.USERNAME_KEY, "user");
        principal.addMapping(DbUserAuthenticator.PASSWORD_KEY, "psw");

        Util.insertIntoTable_User_Account(1, 1, "user", "123456");

        Response response = manager.authenticateUser("user", "psw");

        // password failed for match.
        assertFalse("False is expected.", response.isSuccessful());
    }

    /**
     * Test method <code>void addRoleForUser(User user, SecurityRole role) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddRoleForUser() throws Exception {
        // create a SecurityRole instance.
        SecurityRole role = new GeneralSecurityRole(1, "topcoder");

        User user = createUser(1);
        // create user in the database.
        manager.createUser(user, "uesr");

        // add role.
        manager.addRoleForUser(user, role);

        SecurityRole[] ret = manager.listRolesForUser(user);
        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method <code>void addRolesForUsers(User[] users, SecurityRole[] roles) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddRolesForUsers() throws Exception {
        SecurityRole[] roles = new SecurityRole[2];
        roles[0] = new GeneralSecurityRole(1, "topcoder");
        roles[1] = new GeneralSecurityRole(2, "reviewer");

        User[] users = new User[2];
        users[0] = createUser(1);
        users[1] = createUser(100);

        manager.createUser(users[0], "user");
        manager.createUser(users[1], "user_1");

        manager.addRolesForUsers(users, roles);

        SecurityRole[] ret = manager.listRolesForUser(users[0]);
        assertEquals("Equal is expected.", 2, ret.length);

        ret = manager.listRolesForUser(users[1]);
        assertEquals("Equal is expected.", 2, ret.length);
    }

    /**
     * Test method <code>void removeRoleForUser(User user, SecurityRole role) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRemoveRoleForUser() throws Exception {
        // create a SecurityRole instance.
        SecurityRole role = new GeneralSecurityRole(1, "topcoder");

        User user = createUser(1);
        // create user in the database.
        manager.createUser(user, "uesr");

        // add role.
        manager.addRoleForUser(user, role);

        SecurityRole[] ret = manager.listRolesForUser(user);
        assertEquals("Equal is expected.", 1, ret.length);

        // remove role;

        manager.removeRoleForUser(user, role);

        ret = manager.listRolesForUser(user);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method <code> void removeRolesForUsers(User[] users, SecurityRole[] roles) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRemoveRolesForUsers() throws Exception {

        SecurityRole[] roles = new SecurityRole[2];
        roles[0] = new GeneralSecurityRole(1, "topcoder");
        roles[1] = new GeneralSecurityRole(2, "reviewer");

        User[] users = new User[2];
        users[0] = createUser(1);
        users[1] = createUser(100);

        manager.createUser(users[0], "user");
        manager.createUser(users[1], "user_1");

        manager.addRolesForUsers(users, roles);

        SecurityRole[] ret = manager.listRolesForUser(users[0]);
        assertEquals("Equal is expected.", 2, ret.length);

        ret = manager.listRolesForUser(users[1]);
        assertEquals("Equal is expected.", 2, ret.length);

        // remove roles.
        manager.removeRolesForUsers(users, roles);

        ret = manager.listRolesForUser(users[0]);

        assertEquals("Equal is expected.", 0, ret.length);

        ret = manager.listRolesForUser(users[1]);
        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method <code>void clearRolesForUser(User user) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testClearRolesForUser() throws Exception {
        // create a SecurityRole instance.
        SecurityRole role = new GeneralSecurityRole(1, "topcoder");

        SecurityRole role2 = new GeneralSecurityRole(2, "reviewer");

        User user = createUser(1);
        // create user in the database.
        manager.createUser(user, "uesr");

        // add role.
        manager.addRoleForUser(user, role);
        manager.addRoleForUser(user, role2);

        SecurityRole[] ret = manager.listRolesForUser(user);
        assertEquals("Equal is expected.", 2, ret.length);

        // clear.
        manager.clearRolesForUser(user);

        ret = manager.listRolesForUser(user);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method <code>void clearRolesForUsers(User[] users) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testClearRolesForUsers() throws Exception {
        SecurityRole[] roles = new SecurityRole[2];
        roles[0] = new GeneralSecurityRole(1, "topcoder");
        roles[1] = new GeneralSecurityRole(2, "reviewer");

        User[] users = new User[2];
        users[0] = createUser(1);
        users[1] = createUser(100);

        manager.createUser(users[0], "user");
        manager.createUser(users[1], "user_1");

        manager.addRolesForUsers(users, roles);

        SecurityRole[] ret = manager.listRolesForUser(users[0]);
        assertEquals("Equal is expected.", 2, ret.length);

        ret = manager.listRolesForUser(users[1]);
        assertEquals("Equal is expected.", 2, ret.length);

        // clear roles.
        manager.clearRolesForUsers(users);

        ret = manager.listRolesForUser(users[0]);
        assertEquals("Equal is expected.", 0, ret.length);

        ret = manager.listRolesForUser(users[1]);
        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method <code>SecurityRole[] listRolesForUser(User user) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testListRolesForUser() throws Exception {
        // create a SecurityRole instance.
        SecurityRole role = new GeneralSecurityRole(1, "topcoder");

        SecurityRole role2 = new GeneralSecurityRole(2, "reviewer");

        User user = createUser(1);
        // create user in the database.
        manager.createUser(user, "uesr");

        // add role.
        manager.addRoleForUser(user, role);
        manager.addRoleForUser(user, role2);

        SecurityRole[] ret = manager.listRolesForUser(user);
        assertEquals("Equal is expected.", 2, ret.length);
    }

    /**
     * Test method <code>void createUsers(User[] users, String username, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateUsers_1() throws Exception {
        User[] users = new User[2];
        users[0] = createUser(1);
        users[1] = createUser(100);

        manager.createUsers(users, "user", true);

        User[] ret = manager.listUsers();
        assertEquals("Equal is expected.", 2, ret.length);
    }

    /**
     * Test method <code>void createUsers(User[] users, String username, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateUsers_2() throws Exception {
        User[] users = new User[2];
        users[0] = createUser(1);
        users[1] = createUser(100);

        manager.createUsers(users, "user", false);

        User[] ret = manager.listUsers();
        assertEquals("Equal is expected.", 2, ret.length);
    }

    /**
     * Test method <code>User[] retrieveUsers(long[] ids) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRetrieveUsers_1() throws Exception {
        User[] users = new User[2];
        users[0] = createUser(1);
        users[1] = createUser(100);

        manager.createUsers(users, "user", false);

        long[] ids = new long[2];
        ids[0] = users[0].getId();
        ids[1] = users[1].getId();

        User[] ret = manager.retrieveUsers(ids);
        assertEquals("Equal is expected.", users[0], ret[0]);
        assertEquals("Equal is expected.", users[1], ret[1]);
    }

    /**
     * Test method <code>void updateUsers(User[] users, String username, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateUsers() throws Exception {
        User[] users = new User[2];
        users[0] = createUser(1);
        users[1] = createUser(100);

        manager.createUsers(users, "topcoder", true);

        users[0].setEmailAddress("fly@topcoder.com");
        users[1].setEmailAddress("f@topcoder.com");

        manager.updateUsers(users, "topcoder", true);

        long[] ids = new long[2];
        ids[0] = users[0].getId();
        ids[1] = users[1].getId();

        User[] ret = manager.retrieveUsers(ids);

        assertEquals("Equal is expected.", "fly@topcoder.com", ret[0].getEmailAddress());
        assertEquals("Equal is expected.", "f@topcoder.com", ret[1].getEmailAddress());
    }

    /**
     * Test method <code>void updateUsers(User[] users, String username, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateUsers_2() throws Exception {
        User[] users = new User[2];
        users[0] = createUser(1);
        users[1] = createUser(100);

        manager.createUsers(users, "topcoder", true);

        users[0].setEmailAddress("fly@topcoder.com");
        users[1].setEmailAddress("f@topcoder.com");

        manager.updateUsers(users, "topcoder", false);

        long[] ids = new long[2];
        ids[0] = users[0].getId();
        ids[1] = users[1].getId();

        User[] ret = manager.retrieveUsers(ids);

        assertEquals("Equal is expected.", "fly@topcoder.com", ret[0].getEmailAddress());
        assertEquals("Equal is expected.", "f@topcoder.com", ret[1].getEmailAddress());
    }

    /**
     * Test method <Code>void deleteUsers(User[] users, boolean atomicBatchMode)</code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteUsers_1() throws Exception {
        User[] users = new User[2];
        users[0] = createUser(1);
        users[1] = createUser(100);

        manager.createUsers(users, "topcoder", true);

        // delete.
        manager.deleteUsers(users, true);

        User[] ret = manager.listUsers();
        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method <code>convertUserToPrincipal(User user) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testConvertUserToPrincipal() throws Exception {
        User user = createUser(1);

        manager.createUser(user, "user");

        // convert the User to Principal
        Principal p = manager.convertUserToPrincipal(user);

        // convert the result Principal back
        User ret = manager.convertPrincipalToUser(p);

        assertEquals("Equal is expected.", user, ret);
    }

    /**
     * Test method
     * <code>User convertPrincipalToUser(com.topcoder.security.authenticationfactory.Principal principal)</code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testConvertPrincipalToUser() throws Exception {
        User user = createUser(100);

        manager.createUser(user, "user");

        // convert the User to Principal
        Principal p = manager.convertUserToPrincipal(user);

        // convert the result Principal back
        User ret = manager.convertPrincipalToUser(p);

        assertEquals("Equal is expected.", user, ret);
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
