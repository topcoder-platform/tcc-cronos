/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.cronos.im.persistence.rolecategories.Category;
import com.cronos.im.persistence.rolecategories.InformixRoleCategoryPersistence;
import com.cronos.im.persistence.rolecategories.RoleCategoryPersistence;
import com.cronos.im.persistence.rolecategories.User;

import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.chat.user.profile.ChatUserProfilePersistence;
import com.topcoder.chat.user.profile.ProfileKey;
import com.topcoder.chat.user.profile.ProfileKeyManager;

import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.EntityKey;
import com.topcoder.database.statustracker.EntityStatus;
import com.topcoder.database.statustracker.Status;

import com.topcoder.database.statustracker.persistence.EntityStatusTracker;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Demonstrates the use of the <i>IM Persistence</i> component.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class Demo extends TestCase {
    /**
     * The configuration manager.
     */
    private static final ConfigManager MANAGER = ConfigManager.getInstance();

    /**
     * An database connection to use for the test. This is initialized in {@link #setUp setUp} to be a new instance
     * for each test.
     */
    private Connection connection;

    /**
     * An indication of whether this is the first test that is run.
     */
    private boolean firstTime = true;

    /**
     * Pre-test setup: loads the configuration, populates the tables, and creates the per-test variables.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void setUp() throws Exception {
        if (firstTime) {
            // clean up in case a previous test left things in a bad state
            removeAllNamespaces();
        }

        MANAGER.add("test.xml");

        connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").
            createConnection();

        if (firstTime) {
            firstTime = false;
            clearAllTables();
        }
    }

    /**
     * Per-test cleanup: clears the tables, closes the connection, and resets the config manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void tearDown() throws Exception {
        removeAllNamespaces();
        clearAllTables();

        connection.close();
        connection = null;
    }

    /**
     * Demonstrates the use of the <code>InformixProfileKeyManager</code> class.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ProfileKeyManager() throws Exception {
        setupProfileKeyManager();

        // instantiate the component with the namespace constructor
        ProfileKeyManager manager = new InformixProfileKeyManager("TestConfig");

        // the administration may begin with creating new profiles, and
        // attempting to add them to the manager.
        ProfileKey userA = new ProfileKey("drake", "Registered");
        ProfileKey userB = new ProfileKey("jonah", "Unregistered");
        ProfileKey userC = new ProfileKey("Scumbag", "Registered");
        ProfileKey userD = new ProfileKey("yearner", "Wrong");

        ProfileKey userAA = manager.createProfileKey(userA);
        ProfileKey userBB = manager.createProfileKey(userB);
        ProfileKey userCC = manager.createProfileKey(userC);
        try {
            ProfileKey userDD = manager.createProfileKey(userD);
        } catch (IllegalArgumentException ex) {
            // should throw IllegalArgumentException due to having the wrong type
        }

        // userA and userB would be added. userC and userD would not be added
        // because userC contains an obscene word that is banned by the
        // configured validator, and userD contains an illegal type.

        // further administration would involve removing some users. In this
        // case, we wish to remove user "drake".
        manager.deleteProfileKey(1);

        // As part of some front-end application usage, the profile key table can
        // be queried. All queries would be performed against the all_user
        // table in the above state. Note that the resulting profiles will be
        // referred to by their usernames: For example, jonah will refer to the
        // profile key with username "jonah".

        // a query using a user ID
        ProfileKey userQ11 = manager.getProfileKey(2); // retrieves AleaActaEst
        ProfileKey userQ12 = manager.getProfileKey(1); // retrieves null
        // The getProfileKey(String,String) method would work in the same manner

        // a query using multiple user IDs
        long[] userIDs = {1, 2, 3};
        ProfileKey[] usersQ21 = manager.getProfileKeys(userIDs);
        // usersQ21 will have length of 3, just like the input, and contain null,
        // AleaActaEst, and argolite profiles
        // The getProfileKeys(String[],String) method would work in the same
        // manner

        // a query using a type
        ProfileKey[] usersQ31 = manager.getProfileKeys("Registered");
        // usersQ21 will have length of 2, representing the two registered users
        // in the table: Wendell and drake.
        // The getProfileKeys(String[]) method would work in the same
        // manner
    }

    /**
     * Demonstrates the use of the <code>RegisteredChatUserProfileInformixPersistence</code> class.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_RegisteredChatUserProfileInformixPersistence() throws Exception {
        setupRegisteredChatUserProfileInformixPersistence();

        // instantiate the component with the namespace constructor
        ChatUserProfilePersistence persistence = new RegisteredChatUserProfileInformixPersistence("TestConfig");

        // This persistence implementation only supports retrieval
        // the two getter methods can be used to access info about users

        // a query using an existing username
        ChatUserProfile profile = persistence.getProfile("evirn");
        // retrieves evirn data, including
        // - First Name = Ender
        // - Last Name = Virn
        // - Company = MicroSoft
        // - Title = coder
        // - Email = evirn@bread.com
        // - Name = evirn

        // a query using several usernames: evirn, blah, crack
        String[] usernames = {"evirn", "blah", "crack"};
        ChatUserProfile[] profiles = persistence.getProfiles(usernames);
        // retrieves evirn data in first spot, including
        // - First Name = Ender
        // - Last Name = Virn
        // - Company = MicroSoft
        // - Title = coder
        // - Email = evirn@bread.com
        // - Name = evirn
        // retrieves null in second spot
        // retrieves crack data in last spot, including
        // - First Name = Chris
        // - Last Name = Rack
        // - Company = Sun
        // - Title = architect
        // - Email = crack@bread.com
        // - Name = crack
        // Note that for user crack, we retrieved only one company and title. No
        // special ordering was used to select this company.

        // The searches can be quite complex. Here's a simple example of
        // finding all employed in Nabisco company. We will restrict this to the
        // given users: evrin, crack, jmann
        Map criteria = new HashMap();
        String[] registered = {"evirn", "jmann", "crack"};
        criteria.put("Company", "Nabisco");
        ChatUserProfile[] profiles2 = persistence.searchProfiles(criteria, registered);
        // retrieves crack data in only spot, including
        // - First Name = Chris
        // - Last Name = Rack
        // - Company = nabisco
        // - Title = Project Manager
        // - Email = crack@bread.com
        // - Name = crack

        // A more complex search example would involve searching on multiple
        // items in a category. We reuse the array of registered users.
        List titles = new ArrayList();
        titles.add("coder");
        titles.add("architect");
        criteria.clear();
        criteria.put("Title", " titles");
        ChatUserProfile[] profiles3 = persistence.searchProfiles(criteria, registered);
        // retrieves evirn data in first spot, including
        // - First Name = Ender
        // - Last Name = Virn
        // - Company = MicroSoft
        // - Title = coder
        // - Email = evirn@bread.com
        // - Name = evirn
        // retrieves crack data in second spot, including
        // - First Name = Chris
        // - Last Name = Rack
        // - Company = Sun
        // - Title = architect
        // - Email = crack@bread.com
        // - Name = crack
    }

    /**
     * Demonstrates the use of the <code>UnregisteredChatUserProfileInformixPersistence</code> class.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_UnregisteredChatUserProfileInformixPersistence() throws Exception {
        setupUnregisteredChatUserProfileInformixPersistence();

        // instantiate the component with the namespace constructor
        ChatUserProfilePersistence persistence = new UnregisteredChatUserProfileInformixPersistence("TestConfig");

        // the administration may begin with creating new profiles, and
        // attempting to add them to the manager.
        ChatUserProfile profile1 = new ChatUserProfile("5", "Unregistered");
        profile1.setProperty(UserDefinedAttributeNames.FIRST_NAME, "Jen");
        profile1.setProperty(UserDefinedAttributeNames.LAST_NAME, "Jones");
        profile1.setProperty(UserDefinedAttributeNames.EMAIL, "jjones@topcoder.com");
        profile1.setProperty(UserDefinedAttributeNames.TITLE, "Project Manager");
        profile1.setProperty(UserDefinedAttributeNames.COMPANY, "TopCoder");
        persistence.createProfile(profile1);

        // retrieval and search operations can also be performed

        // a query using an existing username (i.e. client_id)
        ChatUserProfile profile = persistence.getProfile("1");
        // retrieves data for client 1, including
        // - First Name = Joe
        // - Last Name = Pesci
        // - Company = Nabisco
        // - Title = coder
        // - Email = jpesci@me.com
        // - Name = JoePesci

        // a query using several usernames: 1, 7, 5
        String[] usernames = {"1", "7", "5"};
        ChatUserProfile[] profiles = persistence.getProfiles(usernames);
        // retrieves client 1 data in first spot, including
        // - First Name = Joe
        // - Last Name = Pesci
        // - Company = Nabisco
        // - Title = coder
        // - Email = jpesci@me.com
        // - Name = JoePesci
        // retrieves null in second spot
        // retrieves client 5 data in last spot, including
        // - First Name = Jen
        // - Last Name = Jones
        // - Company = TopCoder
        // - Title = Project Manager
        // - Email = jjones@topcoder.com
        // - Name = JenJones

        // The searches can be quite complex. Here's a simple example of
        // finding all employed in Nabisco company. We will restrict this to the
        // given users: 1, 2, 3, 4, 5
        Map criteria = new HashMap();
        String[] registered = {"1", "2", "3", "4", "5"};
        criteria.put("Company", "Nabisco");
        ChatUserProfile[] profiles2 = persistence.searchProfiles(criteria, registered);
        // retrieves one row array data for client 1, including
        // - First Name = Joe
        // - Last Name = Pesci
        // - Company = Nabisco
        // - Title = coder
        // - Email = jpesci@me.com
        // - Name = JoePesci

        // A more complex search example would involve searching on multiple
        // items in a category. We reuse the array of registered users.
        List titles = new ArrayList();
        titles.add("coder");
        titles.add("architect");
        criteria.clear();
        criteria.put("Title", " titles");
        ChatUserProfile[] profiles3 = persistence.searchProfiles(criteria, registered);
        // retrieves data for client 1, including
        // - First Name = Joe
        // - Last Name = Pesci
        // - Company = Nabisco
        // - Title = coder
        // - Email = jpesci@me.com
        // - Name = JoePesci
        // retrieves data for client 2, including
        // - First Name = Kim
        // - Last Name = John
        // - Company = MicorSoft
        // - Title = coder
        // - Email = kjohn@me.com
        // - Name = KimJohn
        // retrieves data for client 3, including
        // - First Name = Alain
        // - Last Name = Rock
        // - Company = Sun
        // - Title = architect
        // - Email = arock@me.com
        // - Name = AlainRock
    }

    /**
     * Demonstrates the use of the <code>InformixRoleCategoryPersistence</code> class.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_InformixRoleCategoryPersistence() throws Exception {
        setupInformixRoleCategoryPersistence();

        // instantiate the component with the namespace constructor
        RoleCategoryPersistence persistence = new InformixRoleCategoryPersistence("TestConfig");

        // This persistence implementation supports many getter functions:

        // a query to get all users with a given role
        User[] users = persistence.getUsers("coder");
        // retrieves users evirn and cales

        // a query to get all categories
        Category[] allCategories = persistence.getAllCategories();
        // retrieves all three categories

        // a query to get categories that are chattable
        allCategories = persistence.getCategories(true);
        // retrieves categories coding and modeling

        // a query to get categories for a given manager
        allCategories = persistence.getCategories("evirn");
        // retrieves categories coding and management

        // we can also change the categories for a user
        Category[] updateCategories = new Category[] {
            new Category(1, "coding", "Coding category", true),
            new Category(3, "modling", "UML modling category", true)
        };

        persistence.updateCategories("evirn", updateCategories);
        // The manager_category table will become as below, with the new
        // association for evirn in blue highlight, and the previous association
        // of evirn to management is removed.
    }

    /**
     * Demonstrates the use of the <code>InformixEntityStatusTracker</code> class.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_InformixEntityStatusTracker() throws Exception {
        setupInformixEntityStatusTracker();

        Entity type = new Entity(1, "entity", new String[] {"column"},
                                 new Status[] {new Status(1), new Status(2), new Status(3)});

        // instantiate the component with the namespace constructor
        EntityStatusTracker persistence = new InformixEntityStatusTracker("TestConfig");

        // This persistence implementation supports many getter functions:

        // a query to get current status for entity 1
        Map m = new HashMap();
        m.put("column", new Long(12345));
        EntityKey instance = new EntityKey(type, m);

        // set the status
        persistence.setStatus(instance, new Status(1), "user");

        EntityStatus currStatus = persistence.getCurrentStatus(instance);
        // retrieves status of "deal"

        // a query to get all statuses for entity 1
        EntityStatus[] allStatuses = persistence.getStatusHistory(instance);
        // retrieves all two statuses: "init" that started on March 1, 2007 and
        // ended on March 2, 2007, and "deal" which started on March 2, 2007 and
        // is current (no end date).

        // a query to get all current entities in "init"
        Status[] statuses = new Status[] {new Status(1)};
        EntityStatus[] allInitStatuses = persistence.findByStatus(type, statuses);
        // retrieves one status: "init" that started on March 7, 2007 and
        // is for entity 2

        Thread.sleep(1500);

        // we can also set the status of entity 1
        Status newStatus = new Status(3);
        persistence.setStatus(instance, newStatus, "evirn");
        // The entity_status_history is updated as below.
        // There is a new entry for entity 1, and the previous current status is
        // ended. This can be seen in blue highlight, and the previous
        // of evirn to management is removed. Not seen is the modify_entity column
        // row value set to "evirn"
    }

    /**
     * Prepares the database for the <code>test_InformixEntityStatusTracker</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void setupInformixEntityStatusTracker() throws Exception {
        addEntityStatus(1, "init", "Init status");
        addEntityStatus(2, "deal", "Deal status");
        addEntityStatus(3, "closing", "Closing status");

        addEntityStatusHistory(1, 1, true);
        addEntityStatusHistory(2, 2, false);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            // shouldn't happen
        }
        addEntityStatusHistory(1, 3, false);
        addEntityStatusHistory(2, 1, false);
    }

    /**
     * Adds a new entry to the <i>entity_status</i> table.
     *
     * @param id the ID
     * @param name the name
     * @param description the description
     * @throws Exception if an unexpected exception occurs
     */
    private void addEntityStatus(long id, String name, String description) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO entity_status (entity_status_id, name, description, "
                                        + "create_date, create_user, modify_date, modify_user) VALUES (?, ?, ?, "
                                        + "CURRENT, USER, CURRENT, USER)");

        statement.setLong(1, id);
        statement.setString(2, name);
        statement.setString(3, description);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds a new entry to the <i>entity_status_history</i> table.
     *
     * @param statusId the status ID
     * @param id the entity ID
     * @param ended an indication of whether the status should have an end date
     * @throws Exception if an unexpected exception occurs
     */
    private void addEntityStatusHistory(long statusId, long id, boolean ended) throws Exception {
        StringBuffer query = new StringBuffer();
        query.append("INSERT INTO entity_status_history (entity_id, start_date, entity_status_id, create_date, "
                     + "create_user, modify_date, modify_user");
        if (ended) {
            query.append(", end_date");
        }

        query.append(") VALUES (?, CURRENT, ?, CURRENT, USER, CURRENT, USER");

        if (ended) {
            query.append(", CURRENT");
        }

        query.append(")");

        PreparedStatement statement = connection.prepareStatement(query.toString());

        statement.setLong(1, id);
        statement.setLong(2, statusId);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Prepares the database for the <code>test_InformixRoleCategoryPersistence</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void setupInformixRoleCategoryPersistence() throws Exception {
        addPrincipal(1, "evirn");
        addPrincipal(2, "moss");
        addPrincipal(3, "cales");

        addRole(1, "admin");
        addRole(2, "coder");

        addPrincipalRole(1, 1);
        addPrincipalRole(1, 2);
        addPrincipalRole(2, 1);
        addPrincipalRole(3, 2);

        addCategory(1, "config", "Coding category", true);
        addCategory(2, "management", "Management category", false);
        addCategory(3, "modeling", "UML modeling category", true);

        addManagerCategory(1, 1);
        addManagerCategory(1, 2);
        addManagerCategory(3, 1);
    }

    /**
     * Prepares the database for the <code>test_UnregisteredChatUserProfileInformixPersistence</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void setupUnregisteredChatUserProfileInformixPersistence() throws Exception {
        addClient(1, "Joe", "Pesci", "Nabisco", "coder", "jpesci@me.com");
        addClient(2, "Kim", "John", "Microsoft", "coder", "kjohn@me.com");
        addClient(3, "Alain", "Rock", "Sun", "architecht", "arock@me.com");
        addClient(4, "Mary", "Magden", "Bits", "designer", "mmagden@me.com");
    }

    /**
     * Adds an entry to the <i>user</i> table.
     *
     * @param id the ID
     * @param first the first name
     * @param last the last name
     * @param handle the handle
     * @throws Exception if an unexpected exception occurs
     */
    private void addUser(long id, String first, String last, String handle) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO user (user_id, first_name, last_name, handle) "
                                        + "VALUES (?, ?, ?, ?)");

        statement.setLong(1, id);
        statement.setString(2, first);
        statement.setString(3, last);
        statement.setString(4, handle);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds an entry to the <code>email</code> table.
     *
     * @param id the user ID
     * @param email the e-mail address
     * @throws Exception if an unexpected exception occurs
     */
    private void addEmail(long id, String email) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO email (user_id, address) VALUES (?, ?)");

        statement.setLong(1, id);
        statement.setString(2, email);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds an entry to the <i>company</i> table.
     *
     * @param id the company ID
     * @param name the company name
     * @throws Exception if an unexpected exception occurs
     */
    private void addCompany(long id, String name) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO company (company_id, company_name) VALUES (?, ?)");

        statement.setLong(1, id);
        statement.setString(2, name);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds an entry to the <code>contact</code> table.
     *
     * @param user the user ID
     * @param company the company ID
     * @param title the title
     * @throws Exception if an unexpected exception occurs
     */
    private void addContact(long user, long company, String title) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO contact (contact_id, company_id, title) VALUES (?, ?, ?)");

        statement.setLong(1, user);
        statement.setLong(2, company);
        statement.setString(3, title);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Initializes the database for the <code>test_RegisteredChatUserProfileInformixPersistence</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void setupRegisteredChatUserProfileInformixPersistence() throws Exception {
        addUser(1, "Ender", "Virn", "evirn");
        addUser(2, "Jim", "Mann", "jmann");
        addUser(3, "Chris", "Rack", "crack");

        addEmail(1, "eviron@bread.com");
        addEmail(2, "jmann@bread.com");
        addEmail(3, "crack@bread.com");

        addCompany(1, "MicroSoft");
        addCompany(2, "TopCoder");
        addCompany(3, "Sun");
        addCompany(4, "Nabisco");

        addContact(1, 1, "coder");
        addContact(2, 2, "designer");
        addContact(3, 3, "architecht");
        addContact(3, 4, "Project Manager");
    }

    /**
     * Removes all namespaces from the config manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void removeAllNamespaces() throws Exception {
        for (Iterator it = MANAGER.getAllNamespaces(); it.hasNext();) {
            MANAGER.removeNamespace((String) it.next());
        }
    }

    /**
     * Clears all tables used by this test suite.
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void clearAllTables() throws Exception {
        connection.prepareStatement("DELETE FROM all_user").executeUpdate();
        connection.prepareStatement("DELETE FROM contact").executeUpdate();
        connection.prepareStatement("DELETE FROM company").executeUpdate();
        connection.prepareStatement("DELETE FROM email").executeUpdate();
        connection.prepareStatement("DELETE FROM user").executeUpdate();
        connection.prepareStatement("DELETE FROM client").executeUpdate();
        connection.prepareStatement("DELETE FROM principal_role").executeUpdate();
        connection.prepareStatement("DELETE FROM manager_category").executeUpdate();
        connection.prepareStatement("DELETE FROM principal").executeUpdate();
        connection.prepareStatement("DELETE FROM role").executeUpdate();
        connection.prepareStatement("DELETE FROM category").executeUpdate();
        connection.prepareStatement("DELETE FROM entity_status_history").executeUpdate();
        connection.prepareStatement("DELETE FROM entity_status").executeUpdate();
    }

    /**
     * Initializes the database prior to the <code>test_ProfileKeyManager</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void setupProfileKeyManager() throws Exception {
        addUser(1, "evirn", true);
        addUser(2, "AleaActaEst", false);
        addUser(3, "argolite", false);
        addUser(4, "Wendell", true);
    }

    /**
     * Adds an entry to the <code>all_user</code> table.
     *
     * @param id the user ID
     * @param name the user name
     * @param registered the registered flag
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void addUser(long id, String name, boolean registered) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO all_user (user_id, registered_flag, username, create_date, "
                                        + "create_user, modify_date, modify_user) VALUES (?, ?, ?, CURRENT, USER, "
                                        + "CURRENT, USER)");

        statement.setLong(1, id);
        statement.setString(2, registered ? "Y" : "N");
        statement.setString(3, name);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds a row to the <i>client</i> table.
     *
     * @param id the client ID
     * @param first the first name
     * @param last the last name
     * @param company the company
     * @param title the title
     * @param email the e-mail address
     * @throws Exception if an unexpected exception occurs
     */
    private void addClient(long id, String first, String last, String company, String title, String email)
        throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO client (client_id, first_name, last_name, company, title, "
                                        + "email, create_date, create_user, modify_date, modify_user) VALUES (?, ?, "
                                        + "?, ?, ?, ?, CURRENT, USER, CURRENT, USER)");

        statement.setLong(1, id);
        statement.setString(2, first);
        statement.setString(3, last);
        statement.setString(4, company);
        statement.setString(5, title);
        statement.setString(6, email);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds a new row to the <i>princial</i> table.
     *
     * @param id the principal ID
     * @param name the principal name
     * @throws Exception if an unexpected exception occurs
     */
    private void addPrincipal(long id, String name) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO principal (principal_id, principal_name) VALUES (?, ?)");

        statement.setLong(1, id);
        statement.setString(2, name);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds a new row to the <i>role</i> table.
     *
     * @param id the role ID
     * @param name the role name
     * @throws Exception if an unexpected exception occurs
     */
    private void addRole(long id, String name) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO role (role_id, role_name) VALUES (?, ?)");

        statement.setLong(1, id);
        statement.setString(2, name);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds a new row to the <i>principal_role</i> table.
     *
     * @param principal the principal ID
     * @param role the role ID
     * @throws Exception if an unexpected exception occurs
     */
    private void addPrincipalRole(long principal, long role) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO principal_role (principal_id, role_id) VALUES (?, ?)");

        statement.setLong(1, principal);
        statement.setLong(2, role);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds a new row to the <i>category</i> table.
     *
     * @param id the category ID
     * @param name the category name
     * @param description the description
     * @param chattable the chattable flag
     * @throws Exception if an unexpected exception occurs
     */
    private void addCategory(long id, String name, String description, boolean chattable) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO category (category_id, name, description, chattable_flag, "
                                        + "create_date, create_user, modify_date, modify_user) VALUES (?, ?, ?, ?, "
                                        + " CURRENT, USER, CURRENT, USER)");

        statement.setLong(1, id);
        statement.setString(2, name);
        statement.setString(3, description);
        statement.setString(4, chattable ? "Y" : "N");
        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds a new row to the <i>manager_category</i> table.
     *
     * @param manager the manager ID
     * @param category the category ID
     * @throws Exception if an unexpected exception occurs
     */
    private void addManagerCategory(long manager, long category) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO manager_category (manager_id, category_id, create_date, "
                                        + "create_user, modify_date, modify_user) VALUES (?, ?, CURRENT, USER, "
                                        + "CURRENT, USER)");

        statement.setLong(1, manager);
        statement.setLong(2, category);
        statement.executeUpdate();
        statement.close();
    }
}
