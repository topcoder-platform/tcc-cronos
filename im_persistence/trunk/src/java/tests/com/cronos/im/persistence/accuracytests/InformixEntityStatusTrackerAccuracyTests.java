/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.cronos.im.persistence.InformixEntityStatusTracker;
import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.EntityKey;
import com.topcoder.database.statustracker.EntityStatus;
import com.topcoder.database.statustracker.Status;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.datavalidator.NullValidator;
import com.topcoder.util.datavalidator.ObjectValidator;

/**
 * <p>
 * Accuracy test for <code>{@link InformixEntityStatusTracker}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class InformixEntityStatusTrackerAccuracyTests extends BaseTestCase {

    /**
     * <p>
     * Represents the InformixEntityStatusTracker instance used in tests.
     * </p>
     */
    private InformixEntityStatusTracker informixEntityStatusTracker;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        informixEntityStatusTracker = new InformixEntityStatusTracker();
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixEntityStatusTracker#InformixEntityStatusTracker()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixEntityStatusTracker1Accuracy() throws Exception {
        informixEntityStatusTracker = new InformixEntityStatusTracker();

        assertNotNull(informixEntityStatusTracker);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixEntityStatusTracker#InformixEntityStatusTracker(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixEntityStatusTracker2Accuracy() throws Exception {
        informixEntityStatusTracker = new InformixEntityStatusTracker(InformixEntityStatusTracker.DEFAULT_NAMESPACE);

        assertNotNull(informixEntityStatusTracker);
    }

    /**
     * <p>
     * Accuracy test for
     * <code>{@link InformixEntityStatusTracker#InformixEntityStatusTracker(DBConnectionFactory, String, ObjectValidator)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixEntityStatusTracker3Accuracy() throws Exception {
        DBConnectionFactory dbConnectionFactory = new DBConnectionFactoryImpl();

        // case 1
        informixEntityStatusTracker = new InformixEntityStatusTracker(dbConnectionFactory, "Informix", null);
        assertNotNull(informixEntityStatusTracker);

        // case 2
        informixEntityStatusTracker = new InformixEntityStatusTracker(dbConnectionFactory, "Informix",
            new NullValidator());
        assertNotNull(informixEntityStatusTracker);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixEntityStatusTracker#setStatus(EntityKey, Status, String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetStatusAccuracy() throws Exception {
        // type
        Entity entity = new Entity(2, "entity", new String[] {"column"}, new Status[] {new Status(1), new Status(2),
            new Status(3)});
        EntityKey entityKey = new EntityKey(entity, "12345");

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO entity_status (entity_status_id, name, description, create_date, "
            + "create_user, modify_date, modify_user) VALUES (1, 'name', 'description', "
            + " CURRENT, USER, CURRENT, USER)");

        stmt.close();

        informixEntityStatusTracker.setStatus(entityKey, new Status(1), "user");

        stmt =  conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM entity_status_history WHERE entity_id = 12345");
        assertTrue("a new row should be inserted into the entity_status_history_table", results.next());
        assertNull("the row should have no end date", results.getDate("end_date"));

        results.close();
        stmt.close();
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixEntityStatusTracker#getCurrentStatus(EntityKey)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCurrentStatusAccuracy() throws Exception {
        // type
        Entity entity = new Entity(2, "entity", new String[] {"column"}, new Status[] {new Status(1), new Status(2),
            new Status(3)});
        EntityKey entityKey = new EntityKey(entity, "12345");

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO entity_status (entity_status_id, name, description, create_date, "
            + "create_user, modify_date, modify_user) VALUES (1, 'name', 'description', "
            + " CURRENT, USER, CURRENT, USER)");

        stmt.executeUpdate("INSERT INTO entity_status (entity_status_id, name, description, create_date, "
            + "create_user, modify_date, modify_user) VALUES (2, 'name', 'description', "
            + " CURRENT, USER, CURRENT, USER)");

        stmt.executeUpdate("INSERT INTO entity_status_history (entity_id, entity_status_id, "
            + "start_date, end_date, create_date, modify_date, create_user, modify_user) "
            + "VALUES (12345, 1, CURRENT, CURRENT, CURRENT, CURRENT, USER, USER)");

        Thread.sleep(1000);

        stmt.executeUpdate("INSERT INTO entity_status_history (entity_id, entity_status_id, "
            + "start_date, create_date, modify_date, create_user, modify_user) "
            + "VALUES (12345, 2, CURRENT, CURRENT, CURRENT, USER, USER)");
        Thread.sleep(1000);
        stmt.executeUpdate("INSERT INTO entity_status_history (entity_id, entity_status_id, "
            + "start_date, create_date, modify_date, create_user, modify_user) "
            + "VALUES (1, 1, CURRENT, CURRENT, CURRENT, USER, USER)");

        EntityStatus entityStatus = informixEntityStatusTracker.getCurrentStatus(entityKey);

        assertNotNull(entityStatus);

        assertEquals("incorrect status.", 2, entityStatus.getStatus().getId());
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixEntityStatusTracker#getStatusHistory(EntityKey)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetStatusHistoryAccuracy() throws Exception {
        // type
        Entity entity = new Entity(1, "entity", new String[] {"column"}, new Status[] {new Status(1), new Status(2),
            new Status(3)});
        EntityKey entityKey = new EntityKey(entity, "12345");

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        for (int i = 1; i <= 3; i++) {
            stmt.executeUpdate("INSERT INTO entity_status (entity_status_id, name, description, create_date, "
                + "create_user, modify_date, modify_user) VALUES (" + i + ", 'name', 'description', "
                + " CURRENT, USER, CURRENT, USER)");
        }

        for (int i = 1; i <= 2; i++) {
            stmt.executeUpdate("INSERT INTO entity_status_history (entity_id, entity_status_id, "
                + "start_date, end_date, create_date, modify_date, create_user, modify_user) " + "VALUES (12345, " + i
                + ", CURRENT, CURRENT, CURRENT, CURRENT, USER, USER)");

            Thread.sleep(1000);
        }

        stmt.executeUpdate("INSERT INTO entity_status_history (entity_id, entity_status_id, "
            + "start_date, create_date, modify_date, create_user, modify_user) "
            + "VALUES (12345, 3, CURRENT, CURRENT, CURRENT, USER, USER)");

        Thread.sleep(1000);

        EntityStatus[] statusHistorys = informixEntityStatusTracker.getStatusHistory(entityKey);

        assertNotNull(statusHistorys);
        assertEquals("the history size is incorrect.", 3, statusHistorys.length);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixEntityStatusTracker#findByStatus(Entity, Status[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testFindByStatusAccuracy() throws Exception {
        // type
        Entity entity = new Entity(1, "entity", new String[] {"column"}, new Status[] {new Status(1), new Status(2),
            new Status(3)});

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        for (int i = 1; i <= 3; i++) {
            stmt.executeUpdate("INSERT INTO entity_status (entity_status_id, name, description, create_date, "
                + "create_user, modify_date, modify_user) VALUES (" + i + ", 'name', 'description', "
                + " CURRENT, USER, CURRENT, USER)");
        }

        for (int i = 1; i <= 2; i++) {
            stmt.executeUpdate("INSERT INTO entity_status_history (entity_id, entity_status_id, "
                + "start_date, end_date, create_date, modify_date, create_user, modify_user) " + "VALUES (1, " + i
                + ", CURRENT, CURRENT, CURRENT, CURRENT, USER, USER)");

            Thread.sleep(1000);
        }

        stmt.executeUpdate("INSERT INTO entity_status_history (entity_id, entity_status_id, "
            + "start_date, create_date, modify_date, create_user, modify_user) "
            + "VALUES (1, 2, CURRENT, CURRENT, CURRENT, USER, USER)");

        Thread.sleep(1000);

        stmt.executeUpdate("INSERT INTO entity_status_history (entity_id, entity_status_id, "
            + "start_date, create_date, modify_date, create_user, modify_user) "
            + "VALUES (2, 1, CURRENT, CURRENT, CURRENT, USER, USER)");

        EntityStatus[] statusHistorys = informixEntityStatusTracker.findByStatus(entity, new Status[] {new Status(3)});

        assertNotNull(statusHistorys);
        assertEquals("the history size is incorrect.", 0, statusHistorys.length);

        statusHistorys = informixEntityStatusTracker.findByStatus(entity, new Status[] {new Status(1), new Status(2)});

        assertNotNull(statusHistorys);
        assertEquals("the history size is incorrect.", 2, statusHistorys.length);
    }
}