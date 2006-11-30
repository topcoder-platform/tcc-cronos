/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.failuretests;

import junit.framework.TestCase;


/**
 * Unit tests for OrpheusPendingConfirmationStorage class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestOrpheusPendingConfirmationStorage extends TestCase {
    /**
     * OrpheusPendingConfirmationStorage used to test.
     */
    private DummyOrpheusPendingConfirmationStorage storage;
    
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
        storage = new DummyOrpheusPendingConfirmationStorage("OrpheusPendingConfirmationStorage.valid");
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
     * Tests store(ConfirmationMessage message) method with null ConfirmationMessage message,
     * Expected IllegalArgumentException.
     */
    public void testStore_NullMessage() {
        try {
            storage.store(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests contains(String address) method with null String address, Expected
     * IllegalArgumentException.
     */
    public void testContains_NullAddress() {
        try {
            storage.contains(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests contains(String address) method with empty String address, Expected
     * IllegalArgumentException.
     */
    public void testContains_EmptyAddress() {
        try {
            storage.contains(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests retrieve(String address) method with null String address, Expected
     * IllegalArgumentException.
     */
    public void testRetrieve_NullAddress() {
        try {
            storage.retrieve(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests retrieve(String address) method with empty String address, Expected
     * IllegalArgumentException.
     */
    public void testRetrieve_EmptyAddress() {
        try {
            storage.retrieve(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests delete(String address) method with null String address, Expected
     * IllegalArgumentException.
     */
    public void testDelete_NullAddress() {
        try {
            storage.delete(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests delete(String address) method with empty String address, Expected
     * IllegalArgumentException.
     */
    public void testDelete_EmptyAddress() {
        try {
            storage.delete(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
