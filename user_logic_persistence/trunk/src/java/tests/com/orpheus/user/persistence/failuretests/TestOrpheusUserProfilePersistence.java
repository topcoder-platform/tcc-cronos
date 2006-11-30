/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.failuretests;

import junit.framework.TestCase;


/**
 * Unit tests for OrpheusUserProfilePersistence class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestOrpheusUserProfilePersistence extends TestCase {
    /**
     * OrpheusUserProfilePersistence used to test.
     */
    private DummyOrpheusUserProfilePersistence persist;
    
    /**
     * Setup the environment for each testcase.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        Helper.clearNamespaces();
        Helper.loadConfig("test_conf/failure/logging.xml");
        Helper.loadConfig("test_conf/failure/simplecache.xml");
        Helper.loadConfig("test_conf/failure/DAOFactory.xml");
        persist = new DummyOrpheusUserProfilePersistence("OrpheusPendingConfirmationStorage.valid");
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
     * Tests OrpheusUserProfilePersistence(String namespace) method with null String namespace,
     * Expected IllegalArgumentException.
     */
    public void testOrpheusUserProfilePersistence_NullNamespace() {
        try {
            new DummyOrpheusUserProfilePersistence(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests OrpheusUserProfilePersistence(String namespace) method with empty String namespace,
     * Expected IllegalArgumentException.
     */
    public void testOrpheusUserProfilePersistence_EmptyNamespace() {
        try {
            new DummyOrpheusUserProfilePersistence(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests insertProfile(UserProfile profile) method with null UserProfile profile, Expected
     * IllegalArgumentException.
     */
    public void testInsertProfile_NullProfile() {
        try {
            persist.insertProfile(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests updateProfile(UserProfile profile) method with null UserProfile profile, Expected
     * IllegalArgumentException.
     */
    public void testUpdateProfile_NullProfile() {
        try {
            persist.updateProfile(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
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
            persist.findProfiles(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
