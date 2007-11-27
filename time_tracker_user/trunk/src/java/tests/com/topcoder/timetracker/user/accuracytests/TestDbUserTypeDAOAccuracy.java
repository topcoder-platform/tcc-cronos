/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.user.db.DbUserTypeDAO;

import junit.framework.TestCase;

/**
 * Accuracy test cases for class <code>DbUserTypeDAO </code>.
 */
public class TestDbUserTypeDAOAccuracy extends TestCase {
    /**
     * Represents the DbUserTypeDAO instance for testing.
     */
    private DbUserTypeDAO dao = null;

    /**
     * Set up.
     * 
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.setUpDataBase();

        DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(AccuracyTestHelper.DB_FACTORY_NAMESPACE);

        dao = new DbUserTypeDAO(dbFactory, "tt_user",
                "com.topcoder.timetracker.user.UserType", "com.topcoder.search.builder", "UserTypeSearchBundle");
    }
    
    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        dao = null;

        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * Test the constructor.
     *
     */
    public void testDbUserTypeDAO() {
        assertNotNull(dao);
    }

    public void testAddUserTypes() {
    }

    public void testUpdateUserTypes() {
    }

    public void testRemoveUserTypes() {
    }

    public void testGetUserTypes() {
    }

    public void testSearchUserTypes() {
    }

    public void testGetAllUserTypes() {
    }

}