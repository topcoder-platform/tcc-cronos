/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.accuracytests;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.DbUserStore;
import com.topcoder.timetracker.user.UserStore;
import com.topcoder.timetracker.user.UserStoreManager;
import com.topcoder.timetracker.user.UserStoreManagerImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * Accuracy test for class <code>UserStoreManagerImpl</code>.
 * @author fuyun
 * @version 1.0
 */
public class UserStoreManagerImplAccuracyTest extends TestCase {

    /**
     * <p>
     * the <code>UserStoreManagerImpl</code> instance used for test.
     * </p>
     */
    private UserStoreManager manager;

    /**
     * <p>
     * the namespace used for create <code>UserPersistenceImpl</code> instance.
     * </p>
     */
    private final String namespace = "com.topcoder.timetracker.user";

    /**
     * <p>
     * Represents the DB Connection factory's config namespace.
     * </p>
     */
    private final String dbFactoryNamespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * Represents the Logging Wrapper's config namespace.
     * </p>
     */
    private final String logNamespace = "com.topcoder.util.log";

    /**
     * <p>
     * The setUp method used for initializing testing environment.
     * </p>
     * <p>
     * The environment includes the namespace configuration, tested instance creating and database
     * data preparing.
     * </p>
     * @throws Exception if any problem occurs.
     */
    protected void setUp() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();
        if (configManager.existsNamespace(namespace)) {
            configManager.removeNamespace(namespace);
            configManager.removeNamespace(dbFactoryNamespace);
            configManager.removeNamespace(logNamespace);
        }

        configManager.add("Accuracy/SampleConfig.xml");
        configManager.add("Accuracy/DBFactory.xml");
        configManager.add(logNamespace, "Accuracy/Logging.xml", ConfigManager.CONFIG_XML_FORMAT);

        manager = new UserStoreManagerImpl(namespace);
    }

    /**
     * <p>
     * The tearDown method used for cleaning the testing environment.
     * </p>
     * <p>
     * The task includes cleaning the namespace, set tested instance <code>null</code> and clean
     * database data.
     * </p>
     * @throws Exception if any problem occurs.
     */
    protected void tearDown() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();
        if (configManager.existsNamespace(namespace)) {
            configManager.removeNamespace(namespace);
            configManager.removeNamespace(dbFactoryNamespace);
            configManager.removeNamespace(logNamespace);
        }
        manager = null;
    }

    /**
     * <p>
     * Test constructor UserStoreManagerImpl(String).
     * </p>
     * <p>
     * Assert that the <code>UserStoreManagerImpl</code> can be created and the private field
     * <code>userStores</code> is initialized properly.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testUserStoreManagerImpl() throws Exception {
        assertNotNull("fail to create UserStoreManagerImpl instance.", manager);
        // get the private field userStores by reflection.
        Field userStoresField = UserStoreManagerImpl.class.getDeclaredField("userStores");
        userStoresField.setAccessible(true);
        Map map = (Map) userStoresField.get(manager);
        // there should be 2 UserStores according the confi file Accuracy/SampleConfig.xml.
        assertEquals("the map should contains 2 entries.", 2, map.size());
    }

    /**
     * <p>
     * Test method contains(String name).
     * </p>
     * <p>
     * Assert that the method can return properly value.
     * </p>
     */
    public void testContains() {
        // the record with name user1 should be in database
        assertTrue("the database should contain the user1", manager.contains("DbUserStore1"));
        // the record with name user4 should be in database
        assertFalse("the database should not contain the user4", manager.contains("DbUserStore3"));
    }

    /**
     * <p>
     * Test method add(String name, UserStore userStore).
     * </p>
     * <p>
     * Assert that the <code>UserStore</code> instance can be added successfully. If the
     * <code>UserStore</code> has been added before accoding the name, the instance can not be
     * added again.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testAdd() throws Exception {
        UserStore userStore = new DbUserStore();
        // add the userStore which does not exist
        assertTrue("fail to add UserStore", manager.add("DbUserStore3", userStore));
        // add the userStore which exist.
        assertFalse("fail to add UserStore", manager.add("DbUserStore2", userStore));
        // now there are 3 userstores.
        assertEquals("there should be three UserStores now", 3, manager.getUserStoreNames().size());
    }

    /**
     * <p>
     * Test method remove(String name).
     * </p>
     * <p>
     * Assert that the userStore can be removed.
     * </p>
     */
    public void testRemove() {
        assertTrue("fail to remove UserStore", manager.remove("DbUserStore1"));
        assertFalse("fail to remove UserStore", manager.remove("DbUserStore3"));
        assertEquals("there should be one UserStores now", 1, manager.getUserStoreNames().size());
    }

    /**
     * Test method for getUserStoreNames().
     */
    public void testGetUserStoreNames() {
        Collection names = manager.getUserStoreNames();
        assertTrue("fail to get the user store name", names.contains("DbUserStore1"));
        assertTrue("fail to get the user store name", names.contains("DbUserStore2"));
        assertEquals("there should be two names", 2, names.size());
    }

    /**
     * Test method getUserStore(String name).
     * @throws Exception if any problem occurs.
     */
    public void testGetUserStore() throws Exception {
        assertNotNull("fail to get user store", manager.getUserStore("DbUserStore1"));
    }

}
