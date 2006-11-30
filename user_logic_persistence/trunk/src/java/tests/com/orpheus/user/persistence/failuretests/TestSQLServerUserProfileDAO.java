/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.failuretests;

import org.apache.cactus.ServletTestCase;

import com.orpheus.user.persistence.EntryNotFoundException;
import com.orpheus.user.persistence.ObjectInstantiationException;
import com.orpheus.user.persistence.impl.SQLServerUserProfileDAO;


/**
 * Unit tests for SQLServerUserProfileDAO class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestSQLServerUserProfileDAO extends ServletTestCase {
    /** SQLServerUserProfileDAO used to test. */
    private SQLServerUserProfileDAO dao;

    /**
     * Setup the environment for each testcase.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        Helper.clearNamespaces();
        Helper.loadConfig("test_conf/failure/simplecache.xml");
        Helper.loadConfig("test_conf/failure/dbconnectionfactory.xml");
        Helper.loadConfig("test_conf/failure/DAO.xml");
        dao = new SQLServerUserProfileDAO("SQLServerPendingConfirmationDAO.valid");
    }

    /**
     * Setup the environment for each testcase.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        Helper.clearNamespaces();
    }

    /**
     * Tests SQLServerUserProfileDAO(String namespace) method with null String namespace, Expected
     * IllegalArgumentException.
     */
    public void testSQLServerUserProfileDAO_NullNamespace() {
        try {
            new SQLServerUserProfileDAO(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerUserProfileDAO(String namespace) method with empty String namespace, Expected
     * IllegalArgumentException.
     */
    public void testSQLServerUserProfileDAO_EmptyNamespace() {
        try {
            new SQLServerUserProfileDAO(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerUserProfileDAO(String namespace) method with invalid configurable file,
     * Expected ObjectInstantiationException.
     */
    public void testSQLServerUserProfileDAO_LostSpecNamespace() {
        try {
            new SQLServerUserProfileDAO("SQLServerUserProfileDAO.fail1");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerUserProfileDAO(String namespace) method with invalid configurable file,
     * Expected ObjectInstantiationException.
     */
    public void testSQLServerUserProfileDAO_EmptySpecNamespace() {
        try {
            new SQLServerUserProfileDAO("SQLServerUserProfileDAO.fail2");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerUserProfileDAO(String namespace) method with invalid configurable file,
     * Expected ObjectInstantiationException.
     */
    public void testSQLServerUserProfileDAO_LostFactoryKey() {
        try {
            new SQLServerUserProfileDAO("SQLServerUserProfileDAO.fail3");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerUserProfileDAO(String namespace) method with invalid configurable file,
     * Expected ObjectInstantiationException.
     */
    public void testSQLServerUserProfileDAO_EmptyFactoryKey() {
        try {
            new SQLServerUserProfileDAO("SQLServerUserProfileDAO.fail4");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerUserProfileDAO(String namespace) method with invalid configurable file,
     * Expected ObjectInstantiationException.
     */
    public void testSQLServerUserProfileDAO_LostCacheKey() {
        try {
            new SQLServerUserProfileDAO("SQLServerUserProfileDAO.fail5");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerUserProfileDAO(String namespace) method with invalid configurable file,
     * Expected ObjectInstantiationException.
     */
    public void testSQLServerUserProfileDAO_EmptyCacheKey() {
        try {
            new SQLServerUserProfileDAO("SQLServerUserProfileDAO.fail6");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests insertProfile(UserProfileDTO profile) method with null UserProfileDTO profile,
     * Expected IllegalArgumentException.
     */
    public void testInsertProfile_NullProfile() {
        try {
            dao.insertProfile(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests updateProfile(UserProfileDTO profile) method with null UserProfileDTO profile,
     * Expected IllegalArgumentException.
     */
    public void testUpdateProfile_NullProfile() {
        try {
            dao.updateProfile(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests retrieveProfile(long id) method with not exist long id, Expected
     * EntryNotFoundException.
     */
    public void testRetrieveProfile_NotExistId() {
        try {
            dao.retrieveProfile(998989899);
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests deleteProfile(long id) method with not exist long id, Expected EntryNotFoundException.
     */
    public void testDeleteProfile_NullId() {
        try {
            dao.deleteProfile(99989898);
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests findProfiles(Map criteria) method with null Map criteria, Expected
     * IllegalArgumentException.
     */
    public void testFindProfiles_NullCriteria() {
        try {
            dao.findProfiles(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
