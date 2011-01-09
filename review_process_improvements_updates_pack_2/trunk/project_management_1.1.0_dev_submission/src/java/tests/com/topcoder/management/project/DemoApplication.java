/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Demo test for ReviewApplication classes.
 * </p>
 *
 * @author pvmagacho
 * @version 1.1
 * @since 1.1
 */
public class DemoApplication extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DemoApplication.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // load config.xml
        ConfigManager.getInstance().add("config.xml");

        cleanDatabase();
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        cleanDatabase();

        // remove all namespace.
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator<String> iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace(iter.next());
        }
    }

    /**
     * Demo test for ApplicationsManagerImpl class.
     *
     * @throws Exception for JUnit
     */
    public void testDemo() throws Exception {
        // create an instance of ReviewerStatisticsManagerImpl
        ApplicationsManagerImpl manager = new ApplicationsManagerImpl(ApplicationsManagerImpl.class.getName());
        ReviewApplication reviewA = new ReviewApplication();

        // set properties of ReviewApplication
        reviewA.setApplicationDate(new Date());
        reviewA.setReviewerId(19);
        reviewA.setProjectId(1);
        reviewA.setAcceptPrimary(true);

        // create ReviewApplication
        reviewA = manager.create(reviewA);
        // reviewA has new ID provided by review_application_id_seq + 1

        // create another
        ReviewApplication reviewB = new ReviewApplication();
        reviewB.setApplicationDate(reviewA.getApplicationDate());
        reviewB.setReviewerId(20);
        reviewB.setAcceptPrimary(reviewA.isAcceptPrimary());
        reviewB.setProjectId(reviewA.getProjectId());
        reviewB = manager.create(reviewB);
        // reviewB has new ID reviewA.id plus 1
        assertEquals(reviewB.getId(), reviewA.getId() + 1);

        // update ReviewApplication
        reviewB.setAcceptPrimary(false);
        reviewB = manager.update(reviewB);

        // retrieve ReviewApplication by id
        ReviewApplication rc = manager.retrieve(reviewB.getId());

        // rc should be the same as reviewB
        assertEquals(reviewB.getId(), rc.getId());

        // delete ReviewApplication
        assertTrue(manager.delete(reviewB.getId()));
        // reviewB is deleted

        // re-create reviewB
        reviewB = manager.create(reviewB);
        // reviewB has new ID reviewA.id plus 2
        assertEquals(reviewB.getId(), reviewA.getId() + 2);

        // get primary applications
        ReviewApplication[] primaryApps = manager.getPrimaryApplications(1);
        // an array containing reviewA should be returned
        assertEquals(reviewA.getId(), primaryApps[0].getId());

        // get secondary applications
        ReviewApplication[] secondaryApps = manager.getSecondaryApplications(1);
        // an array containing reviewB should be returned
        assertEquals(reviewB.getId(), secondaryApps[0].getId());

        // get all applications
        ReviewApplication[] allApps = manager.getAllApplications(1);
        // an array containing reviewA and reviewB should be returned.
        // an array containing reviewA should be returned
        assertEquals(reviewA.getId(), allApps[0].getId());
        assertEquals(reviewB.getId(), allApps[1].getId());

    }

    /**
     * Clean database.
     *
     * @throws Exception throw exception to JUnit.
     */
    private void cleanDatabase() throws Exception {
        // get db connection
        Connection connection = new DBConnectionFactoryImpl(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").createConnection();
        Statement statement = connection.createStatement();
        statement.addBatch("DELETE from review_applications");
        statement.executeBatch();
    }
}
