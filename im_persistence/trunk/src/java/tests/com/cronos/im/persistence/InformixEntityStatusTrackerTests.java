/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.EntityKey;
import com.topcoder.database.statustracker.EntityStatus;
import com.topcoder.database.statustracker.Status;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>InformixEntityStatusTracker</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class InformixEntityStatusTrackerTests extends TestCase {
    /**
     * The configuration manager.
     */
    private static final ConfigManager MANAGER = ConfigManager.getInstance();

    /**
     * An entity type to use for the tests.
     */
    private static final Entity ENTITY = new Entity(1, "entity", new String[] {"column"},
                                                    new Status[] {new Status(1), new Status(2), new Status(3)});

    /**
     * An entity tracker instance to use for the test. This is initialized in {@link #setUp setUp} to be a new
     * instance for each test.
     */
    private InformixEntityStatusTracker tracker;

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
        tracker = new InformixEntityStatusTracker();
        connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").
            createConnection();

        if (firstTime) {
            firstTime = false;
            clearAllTables();
        }

        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO entity_status (entity_status_id, name, description, create_date, "
                                        + "create_user, modify_date, modify_user) VALUES (?, 'name', 'description', "
                                        + " CURRENT, USER, CURRENT, USER)");

        for (long i = 1; i <= 3; ++i) {
            statement.setLong(1, i);
            statement.executeUpdate();
        }

        statement.close();
    }

    /**
     * Per-test cleanup: clears the tables, closes the connection, and resets the config manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void tearDown() throws Exception {
        tracker = null;

        removeAllNamespaces();
        clearAllTables();

        connection.close();
        connection = null;
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
        connection.prepareStatement("DELETE FROM entity_status_history").executeUpdate();
        connection.prepareStatement("DELETE FROM entity_status").executeUpdate();
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_null_arg1() throws Exception {
        try {
            new InformixEntityStatusTracker(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed an empty namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_empty_arg1() throws Exception {
        try {
            new InformixEntityStatusTracker("  ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a bad namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_bad_namespace() throws Exception {
        try {
            new InformixEntityStatusTracker("bad.namespace");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing a missing specification namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_missing_spec_namespace() throws Exception {
        try {
            new InformixEntityStatusTracker("missing.spec.namespace");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing a missing connection factory.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_missing_connection_factory() throws Exception {
        try {
            new InformixEntityStatusTracker("missing.connection.factory");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing an invalid connection factory.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_bad_connection_factory() throws Exception {
        try {
            new InformixEntityStatusTracker("bad.connection.factory");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing a missing connection name.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_missing_connection_name() throws Exception {
        try {
            new InformixEntityStatusTracker("missing.connection.name");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing a invalid object validator.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_bad_validator() throws Exception {
        try {
            new InformixEntityStatusTracker("bad.validator");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests the first constructor.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1() throws Exception {
        new InformixEntityStatusTracker(InformixEntityStatusTracker.DEFAULT_NAMESPACE);
        // should succeed
    }

    /**
     * Tests that the second constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> connection factory.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor2_null_arg1() throws Exception {
        try {
            new InformixEntityStatusTracker(null, "name", null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the second constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> connection name.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor2_null_arg2() throws Exception {
        try {
            new InformixEntityStatusTracker(new DBConnectionFactoryImpl(), null, null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the second constructor throws <code>IllegalArgumentException</code> when passed an empty
     * connection name.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor2_empty_arg2() throws Exception {
        try {
            new InformixEntityStatusTracker(new DBConnectionFactoryImpl(), "  ", null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the second constructor.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor2() throws Exception {
        new InformixEntityStatusTracker(new DBConnectionFactoryImpl(), "name", null);
        // should succeed
    }

    // the default constructor is used by many of the other tests, so there is no need for a special test case

    /**
     * Tests that the <code>setStatus</code> method throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> key.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_setStatus_null_arg1() throws Exception {
        try {
            tracker.setStatus(null, new Status(1), "who");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>setStatus</code> method throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> status.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_setStatus_null_arg2() throws Exception {
        try {
            tracker.setStatus(new EntityKey(ENTITY, "value"), null, "who");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>setStatus</code> method throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> user.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_setStatus_null_arg3() throws Exception {
        try {
            tracker.setStatus(new EntityKey(ENTITY, "value"), new Status(1), null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>setStatus</code> method throws <code>IllegalArgumentException</code> when passed an
     * empty user.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_setStatus_empty_arg3() throws Exception {
        try {
            tracker.setStatus(new EntityKey(ENTITY, "value"), new Status(1), "  ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>setStatus</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_setStatus() throws Exception {
        EntityKey key = new EntityKey(ENTITY, "value");
        tracker.setStatus(key, new Status(1), "user");

        PreparedStatement statement =
            connection.prepareStatement("SELECT * FROM entity_status_history WHERE entity_status_id = ?");
        statement.setLong(1, 1);

        ResultSet results = statement.executeQuery();
        assertTrue("a new row should be inserted into the entity_status_history_table", results.next());
        assertNull("the row should have no end date", results.getDate("end_date"));
        results.close();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            // shouldn't happen
        }

        tracker.setStatus(key, new Status(2), "user");

        results = statement.executeQuery();
        assertTrue("row should still exist", results.next());
        assertNotNull("the row should now have an end date", results.getDate("end_date"));
        results.close();

        statement.setLong(1, 2);
        results = statement.executeQuery();
        assertTrue("a new row should be inserted into the entity_status_history_table", results.next());
        assertNull("the row should have no end date", results.getDate("end_date"));
        results.close();

        statement.close();
    }

    /**
     * Tests that the <code>getCurrentStatus</code> method throws <code>IllegalArgumentException</code> when passed
     * a <code>null</code> key.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getCurrentStatus_null_arg1() throws Exception {
        try {
            tracker.getCurrentStatus(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>getCurrentStatus</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getCurrentStatus() throws Exception {
        EntityKey key = new EntityKey(ENTITY, "value");
        Status status = new Status(2);
        tracker.setStatus(key, status, "user");

        EntityStatus newStatus = tracker.getCurrentStatus(key);
        assertEquals("the returned status should equal the set status", status, newStatus.getStatus());
    }

    /**
     * Tests that the <code>getStatusHistory</code> method throws <code>IllegalArgumentException</code> when passed
     * a <code>null</code> entity key.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getStatusHistory_null_arg1() throws Exception {
        try {
            tracker.getStatusHistory(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>getStatusHistory</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getStatusHistory() throws Exception {
        EntityKey key = new EntityKey(ENTITY, "value");
        Status one = new Status(1);
        Status two = new Status(2);
        tracker.setStatus(key, one, "user");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            // shouldn't happen
        }

        tracker.setStatus(key, two, "user");

        EntityStatus[] history = tracker.getStatusHistory(key);
        assertEquals("there should be two items in the history", 2, history.length);
        assertEquals("the first item should be status 1", one, history[0].getStatus());
        assertEquals("the second item should be status 2", two, history[1].getStatus());
    }

    /**
     * Tests that the <code>findByStatus</code> method throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> type.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_findByStatus_null_arg1() throws Exception {
        try {
            tracker.findByStatus(null, new Status[0]);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>findByStatus</code> method throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> status array.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_findByStatus_null_arg2() throws Exception {
        try {
            tracker.findByStatus(ENTITY, null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>findByStatus</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> array element.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_findByStatus_arg2_null_element() throws Exception {
        try {
            tracker.findByStatus(ENTITY, new Status[] {null});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>findByStatus</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_findByStatus() throws Exception {
        EntityKey key = new EntityKey(ENTITY, "value");
        Status one = new Status(1);
        Status two = new Status(2);
        tracker.setStatus(key, one, "user");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            // shouldn't happen
        }
        tracker.setStatus(key, two, "user");

        EntityStatus[] status = tracker.findByStatus(ENTITY, new Status[] {one, two});
        assertEquals("there should be one status returned", 1, status.length);
        assertEquals("the status should be status two (the current status)", two, status[0].getStatus());
    }
}
