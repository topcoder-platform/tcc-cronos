/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.failuretests;

import org.apache.cactus.ServletTestCase;

import com.orpheus.user.persistence.EntryNotFoundException;
import com.orpheus.user.persistence.ObjectInstantiationException;
import com.orpheus.user.persistence.impl.SQLServerPendingConfirmationDAO;


/**
 * Unit tests for SQLServerPendingConfirmationDAO class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestSQLServerPendingConfirmationDAO extends ServletTestCase {
    /** SQLServerPendingConfirmationDAO used to test. */
    private SQLServerPendingConfirmationDAO dao;

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
        dao = new SQLServerPendingConfirmationDAO("SQLServerPendingConfirmationDAO.valid");
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
     * Tests SQLServerPendingConfirmationDAO(String namespace) method with null String namespace,
     * Expected IllegalArgumentException.
     */
    public void testSQLServerPendingConfirmationDAO_NullNamespace() {
        try {
            new SQLServerPendingConfirmationDAO(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerPendingConfirmationDAO(String namespace) method with empty String namespace,
     * Expected IllegalArgumentException.
     */
    public void testSQLServerPendingConfirmationDAO_EmptyNamespace() {
        try {
            new SQLServerPendingConfirmationDAO(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerPendingConfirmationDAO(String namespace) method with invalid configurable
     * file, Expected ObjectInstantiationException.
     */
    public void testSQLServerPendingConfirmationDAO_LostSpecNamespace() {
        try {
            new SQLServerPendingConfirmationDAO("SQLServerPendingConfirmationDAO.fail1");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerPendingConfirmationDAO(String namespace) method with invalid configurable
     * file, Expected ObjectInstantiationException.
     */
    public void testSQLServerPendingConfirmationDAO_EmptySpecNamespace() {
        try {
            new SQLServerPendingConfirmationDAO("SQLServerPendingConfirmationDAO.fail2");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerPendingConfirmationDAO(String namespace) method with invalid configurable
     * file, Expected ObjectInstantiationException.
     */
    public void testSQLServerPendingConfirmationDAO_LostFactoryKey() {
        try {
            new SQLServerPendingConfirmationDAO("SQLServerPendingConfirmationDAO.fail3");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerPendingConfirmationDAO(String namespace) method with invalid configurable
     * file, Expected ObjectInstantiationException.
     */
    public void testSQLServerPendingConfirmationDAO_EmptyFactoryKey() {
        try {
            new SQLServerPendingConfirmationDAO("SQLServerPendingConfirmationDAO.fail4");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerPendingConfirmationDAO(String namespace) method with invalid configurable
     * file, Expected ObjectInstantiationException.
     */
    public void testSQLServerPendingConfirmationDAO_LostCacheKey() {
        try {
            new SQLServerPendingConfirmationDAO("SQLServerPendingConfirmationDAO.fail5");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerPendingConfirmationDAO(String namespace) method with invalid configurable
     * file, Expected ObjectInstantiationException.
     */
    public void testSQLServerPendingConfirmationDAO_EmptyCacheKey() {
        try {
            new SQLServerPendingConfirmationDAO("SQLServerPendingConfirmationDAO.fail6");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests store(ConfirmationMessageDTO message) method with null ConfirmationMessageDTO message,
     * Expected IllegalArgumentException.
     */
    public void testStore_NullMessage() {
        try {
            dao.store(null);
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
            dao.contains(null);
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
            dao.contains(" ");
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
            dao.retrieve(null);
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
            dao.retrieve(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests retrieve(String address) method with not exist String address, Expected
     * EntryNotFoundException.
     */
    public void testRetrieve_NotExistAddress() {
        try {
            dao.retrieve("not-exist_address");
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
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
            dao.delete(null);
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
            dao.delete(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests delete(String address) method with not exist String address, Expected
     * EntryNotFoundException.
     */
    public void testDelete_NotExistAddress() {
        try {
            dao.delete("invalid-address");
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
